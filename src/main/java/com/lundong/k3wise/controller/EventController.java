package com.lundong.k3wise.controller;

import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lark.oapi.core.utils.Jsons;
import com.lark.oapi.service.contact.v3.ContactService;
import com.lark.oapi.service.contact.v3.model.P2UserUpdatedV3;
import com.lundong.k3wise.config.Constants;
import com.lundong.k3wise.entity.ApprovalInstance;
import com.lundong.k3wise.entity.ApprovalInstanceForm;
import com.lundong.k3wise.enums.ApprovalInstanceEnum;
import com.lundong.k3wise.event.ApprovalInstanceStatusUpdatedEvent;
import com.lundong.k3wise.event.ApprovalInstanceStatusUpdatedV1Handler;
import com.lundong.k3wise.event.CustomEventDispatcher;
import com.lundong.k3wise.event.CustomServletAdapter;
import com.lundong.k3wise.util.SignUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author RawChen
 * @date 2023-03-02 11:19
 */
@Slf4j
@RestController
@RequestMapping
public class EventController {

	@Autowired
	private CustomServletAdapter servletAdapter;

	// 注册消息处理器
	private final CustomEventDispatcher EVENT_DISPATCHER = CustomEventDispatcher
			.newBuilder(Constants.VERIFICATION_TOKEN, Constants.ENCRYPT_KEY)
			.onP2UserUpdatedV3(new ContactService.P2UserUpdatedV3Handler() {
				@Override
				public void handle(P2UserUpdatedV3 event) throws Exception {
					log.info("P2UserUpdatedV3: {}", Jsons.DEFAULT.toJson(event));
				}
			})
			.onApprovalInstanceStatusUpdatedV1(new ApprovalInstanceStatusUpdatedV1Handler() {
				@Override
				public void handle(ApprovalInstanceStatusUpdatedEvent event) throws Exception {
					log.info("ApprovalInstanceStatusUpdatedV1: {}", Jsons.DEFAULT.toJson(event));
					new Thread(() -> {
						// 获取审批实例code
						String approvalCode = event.getEvent().getApprovalCode();
						// 判断通过的审批code是否为系统的五个审批单
						if (!Constants.PURCHASE_REQUISITION_APPROVAL_CODE.equals(approvalCode)
								&& !Constants.PURCHASE_ORDER_APPROVAL_CODE.equals(approvalCode)
								&& !Constants.PAYMENT_REQUEST_APPROVAL_CODE.equals(approvalCode)
								&& !Constants.PURCHASE_CONTRACT_APPROVAL_CODE.equals(approvalCode)
								&& !Constants.OUTSOURCING_ORDER_APPROVAL_CODE.equals(approvalCode)) {
							return;
						}
						String instanceCode = event.getEvent().getInstanceCode();
						String status = event.getEvent().getStatus();
						if (ApprovalInstanceEnum.APPROVED.getType().equals(status)) {
							// 根据审批实例ID查询审批单
							ApprovalInstance approvalInstance = SignUtil.approvalInstanceDetail(instanceCode);
							// 审批实例的单据号
							ApprovalInstanceForm form = JSONObject.parseObject(approvalInstance.getForm(), ApprovalInstanceForm.class);

							// TODO 修改金蝶中该单据状态
							// TODO 获取单据编号
							// 审批单据
							String formCode = "";
							switch (approvalCode) {
								case Constants.PURCHASE_REQUISITION_APPROVAL_CODE:
									formCode = Constants.PURCHASE_REQUISITION;
									break;
								case Constants.PURCHASE_ORDER_APPROVAL_CODE:
									formCode = Constants.PURCHASE_ORDER;
									break;
								case Constants.PAYMENT_REQUEST_APPROVAL_CODE:
									formCode = Constants.PAYMENT_REQUEST;
									break;
								case Constants.PURCHASE_CONTRACT_APPROVAL_CODE:
									formCode = Constants.PURCHASE_CONTRACT;
									break;
								case Constants.OUTSOURCING_ORDER_APPROVAL_CODE:
									formCode = Constants.OUTSOURCING_ORDER;
									break;
							}
//							api.audit("BD_Department", "{\"Numbers\":\"" + formCode + "\"}");
							String paramDetailJson = "{\"Data\": {\"FBillNo\": \"" + "单据号" + "\",\"FChecker\": \"administrator\",\"FCheckDirection\": \"1\",\"FDealComment\": \"\"}}";
							String resultDetailStr = HttpRequest.post(Constants.K3API + formCode + Constants.CHECK_BILL + SignUtil.getToken())
									.body(paramDetailJson)
									.timeout(2000)
									.execute().body();
							if (StringUtils.isNotEmpty(resultDetailStr)) {
								JSONObject resultDetailObject = (JSONObject) JSON.parse(resultDetailStr);
								if (resultDetailObject.getInteger("StatusCode") == 200) {
									log.info("FBillNo: {},formCode: {} 审核成功", "单据号", formCode);
								} else {
									log.info("FBillNo: {},formCode: {} 审核失败：{}", "单据号", formCode, resultDetailObject.getString("Message"));
								}
							}
						}


					}).start();
				}
			})
			.build();

	/**
	 * 飞书订阅事件回调
	 *
	 * @param request
	 * @param response
	 * @throws Throwable
	 */
	@RequestMapping(value = "/feishu/webhook/event")
	public void event(HttpServletRequest request, HttpServletResponse response)
			throws Throwable {
		servletAdapter.handleEvent(request, response, EVENT_DISPATCHER);
	}
}