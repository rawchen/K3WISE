package com.lundong.k3wise.service.impl;

import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lundong.k3wise.config.Constants;
import com.lundong.k3wise.entity.NumberAndNameType;
import com.lundong.k3wise.entity.PurchaseContract;
import com.lundong.k3wise.entity.PurchaseContractDetail;
import com.lundong.k3wise.enums.DataTypeEnum;
import com.lundong.k3wise.service.PurchaseContractService;
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
 * 采购合同/合同(应付)
 *
 * @author RawChen
 * @date 2023-08-23 17:48
 */
@Slf4j
@Service
public class PurchaseContractServiceImpl implements PurchaseContractService {

	/**
	 * 定时同步采购合同/合同(应付)
	 *
	 * @return
	 */
	@Scheduled(cron = "0 0 3 ? * *")
	@Override
	public void syncPurchaseContract() {
		List<PurchaseContract> purchaseContractList = purchaseContractList();
		log.info("状态为4（启动状态）的采购合同/合同(应付)数量：{}", purchaseContractList.size());

		// 过滤掉存储中的ids
		List<String> purchaseOrderLists = DataUtil.getIdsByFileName(DataTypeEnum.PURCHASE_CONTRACT.getType());
		purchaseContractList = purchaseContractList.stream()
				.filter(p -> !purchaseOrderLists.contains(p.getContractNo())).collect(Collectors.toList());

		List<String> billNumbers = new ArrayList<>();
		for (PurchaseContract pr : purchaseContractList) {
			// 获取业务员
			NumberAndNameType requester = pr.getEmployee();

//			// 通过名称获取审核人明细中的手机号或邮箱
//			String userId = SignUtil.getUserIdByName(requester.getName());
			// 通过编号获取手机号，再根据手机号获取userId，如果手机号匹配为空就姓名匹配
			String userId = SignUtil.getUserIdByEmployee(requester);
			// 生成审批实例
			String instanceCode = SignUtil.generateApprovalInstance(pr, Constants.PURCHASE_CONTRACT_APPROVAL_CODE, userId);
			if (instanceCode != null) {
				billNumbers.add(String.valueOf(pr.getContractNo()));
			}
		}
		// 本地文本记录已同步的id
		DataUtil.setFormIds(billNumbers, DataTypeEnum.PURCHASE_CONTRACT.getType());
	}

	/**
	 * 获取K3WISE状态为4（启动状态）的采购合同/合同(应付)
	 *
	 * @return
	 */
	public List<PurchaseContract> purchaseContractList() {
		List<PurchaseContract> prList = new ArrayList<>();
		String paramJson = "{\"Data\": {\"Fields\": \"FContractNo,FContractName,Fdate,FMultiCheckStatus\",\"Top\": \"100\",\"PageSize\": \"10000\",\"PageIndex\": \"1\",\"Filter\": \"FMultiCheckStatus='4'\",\"OrderBy\": \"\",\"SelectPage\": \"2\"}}";
		String resultStr = HttpRequest.post(Constants.K3API + Constants.PURCHASE_CONTRACT + Constants.GET_LIST + SignUtil.getToken())
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
						billNoList.add(dataList.getJSONObject(i).getString("FContractNo"));
					}
					billNoList = billNoList.stream().distinct().collect(Collectors.toList());

					for (String billNo : billNoList) {
						// 调用单据查询接口获取明细字段
						String paramDetailJson = "{\"Data\":{\"FBillNo\":\"" + billNo + "\"},\"GetProperty\":false}";
						String resultDetailStr = HttpRequest.post(Constants.K3API + Constants.PURCHASE_CONTRACT + Constants.GET_DETAIL + SignUtil.getToken())
								.body(paramDetailJson)
								.timeout(2000)
								.execute().body();
						if (StringUtils.isNotEmpty(resultDetailStr)) {
							JSONObject resultDetailObject = (JSONObject) JSON.parse(resultDetailStr);
							if (resultDetailObject.getInteger("StatusCode") == 200) {
								JSONObject dataDetail = resultDetailObject.getJSONObject("Data");
								if (dataDetail != null) {
									JSONObject dataForm = dataDetail.getJSONArray("Page1").getJSONObject(0);
									PurchaseContract pr = dataForm.toJavaObject(PurchaseContract.class);
									JSONArray dataFormDetails = dataDetail.getJSONArray("Page3");
									List<PurchaseContractDetail> purchaseContractDetails = dataFormDetails.toJavaList(PurchaseContractDetail.class);
									pr.setDetail(purchaseContractDetails);
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
