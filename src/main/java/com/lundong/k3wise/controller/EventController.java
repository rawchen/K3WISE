package com.lundong.k3wise.controller;

import com.alibaba.fastjson.JSONArray;
import com.lark.oapi.core.utils.Jsons;
import com.lark.oapi.service.contact.v3.ContactService;
import com.lark.oapi.service.contact.v3.model.P2UserUpdatedV3;
import com.lundong.k3wise.config.Constants;
import com.lundong.k3wise.entity.ApprovalInstance;
import com.lundong.k3wise.entity.ApprovalInstanceForm;
import com.lundong.k3wise.enums.ApprovalInstanceEnum;
import com.lundong.k3wise.enums.CheckBillStatusEnum;
import com.lundong.k3wise.enums.DataTypeEnum;
import com.lundong.k3wise.event.ApprovalInstanceStatusUpdatedEvent;
import com.lundong.k3wise.event.ApprovalInstanceStatusUpdatedV1Handler;
import com.lundong.k3wise.event.CustomEventDispatcher;
import com.lundong.k3wise.event.CustomServletAdapter;
import com.lundong.k3wise.util.DataUtil;
import com.lundong.k3wise.util.SignUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

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
						String instanceCode = event.getEvent().getInstanceCode();
						String status = event.getEvent().getStatus();
						// 判断通过的审批code是否为系统的五个审批单
						if (!Constants.PURCHASE_REQUISITION_APPROVAL_CODE.equals(approvalCode)
								&& !Constants.PURCHASE_ORDER_APPROVAL_CODE.equals(approvalCode)
								&& !Constants.PAYMENT_REQUEST_APPROVAL_CODE.equals(approvalCode)
								&& !Constants.PURCHASE_CONTRACT_APPROVAL_CODE.equals(approvalCode)
								&& !Constants.OUTSOURCING_ORDER_APPROVAL_CODE.equals(approvalCode)) {
							return;
						}
						String formCode = "";
						String formCodeName = "";
						String dataTableName = "";
						switch (approvalCode) {
							case Constants.PURCHASE_REQUISITION_APPROVAL_CODE:
								formCode = Constants.PURCHASE_REQUISITION;
								formCodeName = "单据编号";
								dataTableName = DataTypeEnum.PURCHASE_REQUISITION.getType();
								break;
							case Constants.PURCHASE_ORDER_APPROVAL_CODE:
								formCode = Constants.PURCHASE_ORDER;
								formCodeName = "编号";
								dataTableName = DataTypeEnum.PURCHASE_ORDER.getType();
								break;
							case Constants.PAYMENT_REQUEST_APPROVAL_CODE:
								formCode = Constants.PAYMENT_REQUEST;
								formCodeName = "单据号";
								dataTableName = DataTypeEnum.PAYMENT_REQUEST.getType();
								break;
							case Constants.PURCHASE_CONTRACT_APPROVAL_CODE:
								formCode = Constants.PURCHASE_CONTRACT;
								formCodeName = "合同号";
								dataTableName = DataTypeEnum.PURCHASE_CONTRACT.getType();
								break;
							case Constants.OUTSOURCING_ORDER_APPROVAL_CODE:
								formCode = Constants.OUTSOURCING_ORDER;
								formCodeName = "单据编号";
								dataTableName = DataTypeEnum.OUTSOURCING_ORDER.getType();
								break;
						}

						if (ApprovalInstanceEnum.APPROVED.getType().equals(status)
								|| ApprovalInstanceEnum.CANCELED.getType().equals(status)
								|| ApprovalInstanceEnum.REJECTED.getType().equals(status)) {
							// 根据审批实例ID查询审批单
							ApprovalInstance approvalInstance = SignUtil.approvalInstanceDetail(instanceCode);
							if (approvalInstance == null) {
								return;
							}
							// 审批实例的单据号
							String formNumber = "";
							List<ApprovalInstanceForm> approvalInstanceForms =
									JSONArray.parseArray(approvalInstance.getForm(), ApprovalInstanceForm.class);
							for (ApprovalInstanceForm approvalInstanceForm : approvalInstanceForms) {
								if (formCodeName.equals(approvalInstanceForm.getName())) {
									formNumber = approvalInstanceForm.getValue();
									break;
								}
							}
							// 审批通过
							if (ApprovalInstanceEnum.APPROVED.getType().equals(status)) {
								// 修改金蝶中该单据状态为审核
								SignUtil.checkBill(formCode, formNumber, CheckBillStatusEnum.AUDIT.getCode());
							} else if (ApprovalInstanceEnum.CANCELED.getType().equals(status)) {
								// 审批已撤回
								// if在同步列表里面出现过，取消已同步单据标识
								List<String> list = DataUtil.getIdsByFileName(dataTableName);
								for (String s : list) {
									String temp = formNumber + "_" + instanceCode;
									if (s.equals(temp)) {
										DataUtil.removeByFileName(temp, dataTableName);
										// 退回单据状态为保存状态（驳回）
										SignUtil.checkBill(formCode, formNumber, CheckBillStatusEnum.REJECT.getCode());
										break;
									}
								}
							} else if (ApprovalInstanceEnum.REJECTED.getType().equals(status)) {
								// 审批已拒绝
								// if在同步列表里面出现过，取消已同步单据标识
								List<String> list = DataUtil.getIdsByFileName(dataTableName);
								for (String s : list) {
									String temp = formNumber + "_" + instanceCode;
									if (s.equals(temp)) {
										DataUtil.removeByFileName(temp, dataTableName);
										// 退回单据状态为保存状态（驳回）
										SignUtil.checkBill(formCode, formNumber, CheckBillStatusEnum.REJECT.getCode());
										break;
									}
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