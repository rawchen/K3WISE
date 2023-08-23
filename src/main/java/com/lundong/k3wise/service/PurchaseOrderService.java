package com.lundong.k3wise.service;

import com.lundong.k3wise.entity.PurchaseOrder;

import java.util.List;

/**
 * 采购订单
 *
 * @author RawChen
 * @date 2023-06-25 14:02
 */
public interface PurchaseOrderService {

    void syncPurchaseOrder();

    List<PurchaseOrder> purchaseOrderList();

}
