package com.lundong.k3wise;

import com.alibaba.fastjson.JSONObject;
import com.lundong.k3wise.entity.*;
import com.lundong.k3wise.enums.DataTypeEnum;
import com.lundong.k3wise.service.PaymentRequestService;
import com.lundong.k3wise.service.PurchaseOrderService;
import com.lundong.k3wise.service.PurchaseRequisitionService;
import com.lundong.k3wise.util.DataUtil;
import com.lundong.k3wise.util.SignUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.lundong.k3wise.util.SignUtil.getUserIdByName;

@SpringBootTest
class K3WiseApplicationTests {

	@Autowired
	private PurchaseRequisitionService purchaseRequisitionService;

	@Autowired
	private PurchaseOrderService purchaseOrderService;

	@Autowired
	private PaymentRequestService paymentRequestService;

	@Test
	void contextLoads() {
	}

	@Test
	void t01() {
		SignUtil.getToken("cc7c2a733c0a29ecd55ad722a8fb42a18878ebb15f9710e3");
	}

	@Test
	void t02() {
		DataUtil.setFormId("1111", DataTypeEnum.PURCHASE_ORDER.getType());
		DataUtil.setFormId("2222", DataTypeEnum.PURCHASE_ORDER.getType());
		DataUtil.setFormId("3333", DataTypeEnum.PURCHASE_ORDER.getType());
		DataUtil.setFormId("4444", DataTypeEnum.PURCHASE_ORDER.getType());
	}

	@Test
	void t03() {
		List<String> purchaseOrderLists = DataUtil.getIdsByFileName(DataTypeEnum.PURCHASE_ORDER.getType());
		System.out.println("size: " + purchaseOrderLists.size());
		for (String s : purchaseOrderLists) {
			System.out.println(s);
		}
	}

	@Test
	void t04() {
		DataUtil.removeByFileName("2222", DataTypeEnum.PURCHASE_ORDER.getType());
	}

	@Test
	void t05() {
		ApprovalInstance approvalInstance = SignUtil.approvalInstanceDetail("FF0FC68D-1317-4218-A609-805085841717");
		System.out.println(approvalInstance);
	}

	@Test
	void t06() {
		List<ApprovalInstanceForm> approvalInstanceForms = JSONObject.parseArray("[{\"id\":\"widget16915506735380001\",\"name\":\"单行文本\",\"type\":\"input\",\"ext\":null,\"value\":\"hhgxdf\"}]", ApprovalInstanceForm.class);
		for (ApprovalInstanceForm approvalInstanceForm : approvalInstanceForms) {
			System.out.println(approvalInstanceForm);
		}
	}

	@Test
	void t07() {
		purchaseRequisitionService.syncPurchaseRequisition();
	}

	@Test
	void t08() {
		String r = getUserIdByName("陈双全");
		System.out.println(r);

	}

	@Test
	void t09() {
		List<PurchaseRequisition> purchaseRequisitionList = purchaseRequisitionService.purchaseRequisitionList();
		for (PurchaseRequisition purchaseRequisition : purchaseRequisitionList) {
			System.out.println(purchaseRequisition);
		}
	}

	@Test
	void t10() {
		List<PurchaseOrder> purchaseOrderList = purchaseOrderService.purchaseOrderList();
		for (PurchaseOrder order : purchaseOrderList) {
			System.out.println(order);
		}
	}

	@Test
	void t11() {
		List<PaymentRequest> paymentRequestList = paymentRequestService.paymentRequestList();
		for (PaymentRequest r : paymentRequestList) {
			System.out.println(r);
		}
	}

	@Test
	void t13() {
		NumberAndNameType t = new NumberAndNameType();
		t.setName("殷士海");
		t.setNumber("003");
		String r = SignUtil.getUserIdByEmployee(t);
		System.out.println(r);
	}

	@Test
	void t14() {
		List<PurchaseRequisition> purchaseRequisitionList = new ArrayList<>();
		PurchaseRequisition t = new PurchaseRequisition();
		t.setBillNo("POREQ000044");
		PurchaseRequisition t1 = new PurchaseRequisition();
		t1.setBillNo("POREQ000042");
		purchaseRequisitionList.add(t);
		purchaseRequisitionList.add(t1);
		List<String> purchaseOrderLists = DataUtil.getIdsByFileNameFilterInstanceId(DataTypeEnum.PURCHASE_REQUISITION.getType());
		purchaseRequisitionList = purchaseRequisitionList.stream().filter(p -> !purchaseOrderLists.contains(p.getBillNo())).collect(Collectors.toList());
		for (PurchaseRequisition purchaseRequisition : purchaseRequisitionList) {
			System.out.println(purchaseRequisition);
		}
	}
}
