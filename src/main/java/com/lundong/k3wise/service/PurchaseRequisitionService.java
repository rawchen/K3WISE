package com.lundong.k3wise.service;

import com.lundong.k3wise.entity.PurchaseRequisition;

import java.util.List;

/**
 * 采购申请单
 *
 * @author RawChen
 * @date 2023-06-25 14:02
 */
public interface PurchaseRequisitionService {

    void syncPurchaseRequisition();

    List<PurchaseRequisition> purchaseRequisitionList();

}
