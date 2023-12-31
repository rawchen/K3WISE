package com.lundong.k3wise.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lundong.k3wise.config.Constants;
import com.lundong.k3wise.entity.NumberAndNameType;
import com.lundong.k3wise.entity.OutsourcingOrder;
import com.lundong.k3wise.entity.OutsourcingOrderDetail;
import com.lundong.k3wise.enums.DataTypeEnum;
import com.lundong.k3wise.service.OutsourcingOrderService;
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
 * 委外订单
 *
 * @author RawChen
 * @date 2023-08-25 16:32
 */
@Slf4j
@Service
public class OutsourcingOrderServiceImpl implements OutsourcingOrderService {

    /**
     * 定时同步委外订单
     *
     * @return
     */
//	@Scheduled(cron = "0 0 5 ? * *")
//  @Scheduled(fixedDelay = 1, cron = "0 0/10 * * * ?")
    @Scheduled(initialDelay = 240000, fixedRate = 600000)
    @Override
    public void syncOutsourcingOrder() {
        List<OutsourcingOrder> outsourcingOrderList = outsourcingOrderList();
        log.info("状态为4（启动状态）的委外订单数量：{}", outsourcingOrderList.size());

        // 过滤掉存储中的ids
        List<String> purchaseOrderLists = DataUtil.getIdsByFileNameFilterInstanceId(DataTypeEnum.OUTSOURCING_ORDER.getType());
        outsourcingOrderList = outsourcingOrderList.stream()
                .filter(p -> !purchaseOrderLists.contains(p.getBillNo())).collect(Collectors.toList());

        List<String> billNumbers = new ArrayList<>();
        for (OutsourcingOrder pr : outsourcingOrderList) {
            // 获取制单人
            NumberAndNameType requester = pr.getBillerId();

//			// 通过名称获取审核人明细中的手机号或邮箱
//			String userId = SignUtil.getUserIdByName(requester.getName());
            // 通过编号获取手机号，再根据手机号获取userId，如果手机号匹配为空就姓名匹配
            String userId = SignUtil.getUserIdByEmployee(requester);
            String employeeId = SignUtil.getUserIdByEmployee(pr.getEmployee());
            // 生成审批实例
            String instanceCode = SignUtil.generateApprovalInstance(pr, Constants.OUTSOURCING_ORDER_APPROVAL_CODE, userId, employeeId);
            if (!StrUtil.isEmpty(instanceCode)) {
                billNumbers.add(pr.getBillNo() + "_" + instanceCode);
            }
        }
        // 本地文本记录已同步的id
        DataUtil.setFormIds(billNumbers, DataTypeEnum.OUTSOURCING_ORDER.getType());
    }

    /**
     * 获取K3WISE状态为4（启动状态）的委外订单列表
     *
     * @return
     */
    public List<OutsourcingOrder> outsourcingOrderList() {
        List<OutsourcingOrder> prList = new ArrayList<>();
        String paramJson = "{\"Data\": {\"Fields\": \"FBillNo,Fdate,FItemName,FMultiCheckStatus\",\"Top\": \"100\",\"PageSize\": \"10000\",\"PageIndex\": \"1\",\"Filter\": \"FMultiCheckStatus='4'\",\"OrderBy\": \"\",\"SelectPage\": \"2\"}}";
        String resultStr = HttpRequest.post(Constants.K3API + Constants.OUTSOURCING_ORDER + Constants.GET_LIST + SignUtil.getToken())
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

                    for (String billNo : billNoList) {
                        // 调用单据查询接口获取明细字段
                        String paramDetailJson = "{\"Data\":{\"FBillNo\":\"" + billNo + "\"},\"GetProperty\":false}";
                        String resultDetailStr = HttpRequest.post(Constants.K3API + Constants.OUTSOURCING_ORDER + Constants.GET_DETAIL + SignUtil.getToken())
                                .body(paramDetailJson)
                                .timeout(2000)
                                .execute().body();
                        if (StringUtils.isNotEmpty(resultDetailStr)) {
                            JSONObject resultDetailObject = (JSONObject) JSON.parse(resultDetailStr);
                            if (resultDetailObject.getInteger("StatusCode") == 200) {
                                JSONObject dataDetail = resultDetailObject.getJSONObject("Data");
                                if (dataDetail != null) {
                                    JSONObject dataForm = dataDetail.getJSONArray("Page1").getJSONObject(0);
                                    OutsourcingOrder pr = dataForm.toJavaObject(OutsourcingOrder.class);
                                    JSONArray dataFormDetails = dataDetail.getJSONArray("Page2");
                                    List<OutsourcingOrderDetail> outsourcingOrderDetails = dataFormDetails.toJavaList(OutsourcingOrderDetail.class);
                                    pr.setDetail(outsourcingOrderDetails);
                                    prList.add(pr);
                                }
                            }
                        }
                    }
                    return prList;
                }
            } else {
                log.error("获取K3WISE状态为4（启动状态）的委外订单列表接口结果：{}", resultStr);
            }
        }
        return Collections.emptyList();
    }

}
