package com.lundong.k3wise.service.impl;

import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lundong.k3wise.config.Constants;
import com.lundong.k3wise.entity.NumberAndNameType;
import com.lundong.k3wise.entity.PurchaseRequisition;
import com.lundong.k3wise.entity.PurchaseRequisitionDetail;
import com.lundong.k3wise.enums.DataTypeEnum;
import com.lundong.k3wise.service.SystemService;
import com.lundong.k3wise.util.DataUtil;
import com.lundong.k3wise.util.SignUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author RawChen
 * @date 2023-06-25 14:02
 */
@Slf4j
@Service
public class SystemServiceImpl implements SystemService {

	/**
	 * 定时同步采购申请单
	 *
	 * @return
	 */
	@Override
	public String syncPurchaseRequisition() {
		List<PurchaseRequisition> purchaseRequisitionList = purchaseRequisitionList();
//		purchaseRequisitionList = purchaseRequisitionList.stream().filter(pr -> "POREQ000005".equals(pr.getBillNo())).collect(Collectors.toList());

		// 过滤掉存储中的ids
		List<String> purchaseOrderLists = DataUtil.getIdsByFileName(DataTypeEnum.PURCHASE_ORDER.getType());
		purchaseRequisitionList = purchaseRequisitionList.stream()
				.filter(p -> !purchaseOrderLists.contains(p.getBillNo())).collect(Collectors.toList());

		List<String> detailIds = new ArrayList<>();
		for (PurchaseRequisition pr : purchaseRequisitionList) {
			detailIds.add(String.valueOf(pr.getBillNo()));
		}
		for (PurchaseRequisition pr : purchaseRequisitionList) {
			// 获取申请人
			NumberAndNameType requester = pr.getRequesterId();
			String requesterNumber = requester.getNumber();

			// 通过申请人去设置飞书审批人的
			String instanceCode = SignUtil.generateApprovalInstance(pr, Constants.PURCHASE_REQUISITION_APPROVAL_CODE, "9bd13a9d");
		}
		// 本地文本记录已同步的id
		DataUtil.setFormIds(detailIds, DataTypeEnum.PURCHASE_ORDER.getType());

		return "success";
	}

	/**
	 * 获取K3WISE状态为4（启动状态）的采购申请单列表
	 * @return
	 */
	public List<PurchaseRequisition> purchaseRequisitionList() {
		List<PurchaseRequisition> prList = new ArrayList<>();
		String paramJson = "{\"Data\": {\"Fields\": \"FBillNo,FItemName,FDetailID2\",\"Top\": \"100\",\"PageSize\": \"10000\",\"PageIndex\": \"1\",\"Filter\": \"FMultiCheckStatus='4'\",\"OrderBy\": \"\",\"SelectPage\": \"2\"}}";
		String resultStr = HttpRequest.post(Constants.K3API + Constants.PURCHASE_REQUISITION + Constants.GET_LIST + SignUtil.getToken())
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
						billNoList.add(dataList.getJSONObject(i).getString("FBillNo"));
					}
					billNoList = billNoList.stream().distinct().collect(Collectors.toList());

//					List<PurchaseRequisition> purchaseRequisitionList = dataList.toJavaList(PurchaseRequisition.class);
//					purchaseRequisitionList.stream().distinct().collect(Collectors.toList());

					for (String billNo : billNoList) {
						// 调用单据查询接口获取明细字段
						String paramDetailJson = "{\"Data\":{\"FBillNo\":\"" + billNo + "\"},\"GetProperty\":false}";
						String resultDetailStr = HttpRequest.post(Constants.K3API + Constants.PURCHASE_REQUISITION + Constants.GET_DETAIL + SignUtil.getToken())
								.body(paramDetailJson)
								.timeout(2000)
								.execute().body();
						if (StringUtils.isNotEmpty(resultDetailStr)) {
							JSONObject resultDetailObject = (JSONObject) JSON.parse(resultDetailStr);
							if (resultDetailObject.getInteger("StatusCode") == 200) {
								JSONObject dataDetail = resultDetailObject.getJSONObject("Data");
								if (dataDetail != null) {
									JSONObject dataForm = dataDetail.getJSONArray("Page1").getJSONObject(0);
									PurchaseRequisition pr = dataForm.toJavaObject(PurchaseRequisition.class);
									JSONArray dataFormDetails = dataDetail.getJSONArray("Page2");
									List<PurchaseRequisitionDetail> purchaseRequisitionDetails = dataFormDetails.toJavaList(PurchaseRequisitionDetail.class);
									pr.setDetail(purchaseRequisitionDetails);
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
