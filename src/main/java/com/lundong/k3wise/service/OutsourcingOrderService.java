package com.lundong.k3wise.service;

import com.lundong.k3wise.entity.OutsourcingOrder;

import java.util.List;

/**
 * 委外订单
 *
 * @author RawChen
 * @date 2023-08-25 16:29
 */
public interface OutsourcingOrderService {

    void syncOutsourcingOrder();

    List<OutsourcingOrder> outsourcingOrderList();

}
