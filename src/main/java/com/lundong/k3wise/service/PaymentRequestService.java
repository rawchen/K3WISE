package com.lundong.k3wise.service;

import com.lundong.k3wise.entity.PaymentRequest;

import java.util.List;

/**
 * 付款申请单
 *
 * @author RawChen
 * @date 2023-08-23 11:25
 */
public interface PaymentRequestService {

    void syncPaymentRequest();

    List<PaymentRequest> paymentRequestList();

}
