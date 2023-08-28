package com.lundong.k3wise.service.impl;

import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lundong.k3wise.config.Constants;
import com.lundong.k3wise.entity.NumberAndNameType;
import com.lundong.k3wise.entity.PaymentRequest;
import com.lundong.k3wise.entity.PaymentRequestDetail;
import com.lundong.k3wise.enums.DataTypeEnum;
import com.lundong.k3wise.service.PaymentRequestService;
import com.lundong.k3wise.util.DataUtil;
import com.lundong.k3wise.util.SignUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 付款申请单
 *
 * @author RawChen
 * @date 2023-08-23 11:27
 */
@Slf4j
@Service
public class PaymentRequestServiceImpl implements PaymentRequestService {

	/**
	 * 定时同步付款申请单
	 *
	 * @return
	 */
	@Scheduled(cron = "0 0 4 ? * *")
	@Override
	public void syncPaymentRequest() {
		List<PaymentRequest> paymentRequestList = paymentRequestList();
		log.info("状态为4（启动状态）的付款申请单数量：{}", paymentRequestList.size());

		// 过滤掉存储中的ids
		List<String> paymentRequestLists = DataUtil.getIdsByFileName(DataTypeEnum.PAYMENT_REQUEST.getType());
		paymentRequestList = paymentRequestList.stream()
				.filter(p -> !paymentRequestLists.contains(p.getNumber())).collect(Collectors.toList());

		List<String> billNumbers = new ArrayList<>();
		for (PaymentRequest po : paymentRequestList) {
			// 获取业务员
			NumberAndNameType requester = po.getEmployee();

//			// 通过名称获取审核人明细中的手机号或邮箱
//			String userId = SignUtil.getUserIdByName(requester.getName());
			// 通过编号获取手机号，再根据手机号获取userId，如果手机号匹配为空就姓名匹配
			String userId = SignUtil.getUserIdByEmployee(requester);
			// 生成审批实例
			String instanceCode = SignUtil.generateApprovalInstance(po, Constants.PAYMENT_REQUEST_APPROVAL_CODE, userId);
			if (instanceCode != null) {
				billNumbers.add(String.valueOf(po.getNumber()));
			}
		}
		// 本地文本记录已同步的id
		DataUtil.setFormIds(billNumbers, DataTypeEnum.PAYMENT_REQUEST.getType());
	}

	/**
	 * 获取K3WISE状态为4（启动状态）的付款申请单列表
	 *
	 * @return
	 */
	@Override
	public List<PaymentRequest> paymentRequestList() {
		List<PaymentRequest> prList = new ArrayList<>();
		String paramJson = "{\"Data\": {\"Fields\": \"FNumber,Fdate,FMultiCheckStatus,FItemClassID,FPayBillID_CN\",\"Top\": \"100\",\"PageSize\": \"10000\",\"PageIndex\": \"1\",\"Filter\": \"FMultiCheckStatus='4'\",\"OrderBy\": \"\",\"SelectPage\": \"2\"}}";
		String resultStr = HttpRequest.post(Constants.K3API + Constants.PAYMENT_REQUEST + Constants.GET_LIST + SignUtil.getToken())
				.body(paramJson)
				.timeout(2000)
				.execute().body();

		if (StringUtils.isNotEmpty(resultStr)) {
			JSONObject resultObject = (JSONObject) JSON.parse(resultStr);
			if (resultObject.getInteger("StatusCode") == 200) {
				JSONObject data = resultObject.getJSONObject("Data");
				if (data != null) {
					JSONArray dataList = data.getJSONArray("DATA");
					List<String> billNoList = new ArrayList<>();
					for (int i = 0; i < dataList.size(); i++) {
						billNoList.add(dataList.getJSONObject(i).getString("FNumber"));
					}
					billNoList = billNoList.stream().distinct().collect(Collectors.toList());

					for (String billNo : billNoList) {
						// 调用单据查询接口获取明细字段
						String paramDetailJson = "{\"Data\":{\"FBillNo\":\"" + billNo + "\"},\"GetProperty\":false}";
						String resultDetailStr = HttpRequest.post(Constants.K3API + Constants.PAYMENT_REQUEST + Constants.GET_DETAIL + SignUtil.getToken())
								.body(paramDetailJson)
								.timeout(2000)
								.execute().body();
						if (StringUtils.isNotEmpty(resultDetailStr)) {
							JSONObject resultDetailObject = (JSONObject) JSON.parse(resultDetailStr);
							if (resultDetailObject.getInteger("StatusCode") == 200) {
								JSONObject dataDetail = resultDetailObject.getJSONObject("Data");
								if (dataDetail != null) {
									JSONObject dataForm = dataDetail.getJSONArray("Page1").getJSONObject(0);
									PaymentRequest pr = dataForm.toJavaObject(PaymentRequest.class);
									JSONArray dataFormDetails = dataDetail.getJSONArray("Page2");
									List<PaymentRequestDetail> paymentRequestDetails = dataFormDetails.toJavaList(PaymentRequestDetail.class);
									pr.setDetail(paymentRequestDetails);
									prList.add(pr);
								}
							}
						}
					}
					return prList;
				}
			}
		}
		return Collections.emptyList();
	}
}
