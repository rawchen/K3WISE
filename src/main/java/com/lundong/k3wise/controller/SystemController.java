package com.lundong.k3wise.controller;

import com.lundong.k3wise.service.PaymentRequestService;
import com.lundong.k3wise.service.PurchaseContractService;
import com.lundong.k3wise.service.PurchaseOrderService;
import com.lundong.k3wise.service.PurchaseRequisitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author RawChen
 * @date 2023-06-25 14:02
 */
@RestController
public class SystemController {

	@Autowired
	private PurchaseRequisitionService purchaseRequisitionService;

	@Autowired
	private PurchaseOrderService purchaseOrderService;

	@Autowired
	private PaymentRequestService paymentRequestService;

	@Autowired
	private PurchaseContractService purchaseContractService;

	/**
	 * 同步采购申请单
	 *
	 * @return
	 */
	@GetMapping("/t01")
	public void syncPurchaseRequisition() {
		purchaseRequisitionService.syncPurchaseRequisition();
	}

	/**
	 * 同步采购订单
	 *
	 * @return
	 */
	@GetMapping("/t02")
	public void syncPurchaseOrder() {
		purchaseOrderService.syncPurchaseOrder();
	}

	/**
	 * 同步付款申请单
	 *
	 * @return
	 */
	@GetMapping("/t03")
	public void syncPaymentRequest() {
		paymentRequestService.syncPaymentRequest();
	}

	/**
	 * 同步采购合同/合同(应付)
	 *
	 * @return
	 */
	@GetMapping("/t04")
	public void syncPurchaseContract() {
		purchaseContractService.syncPurchaseContract();
	}

}
