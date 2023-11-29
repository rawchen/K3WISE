package com.lundong.k3wise;

import com.alibaba.fastjson.JSONObject;
import com.lundong.k3wise.entity.*;
import com.lundong.k3wise.enums.DataTypeEnum;
import com.lundong.k3wise.service.OutsourcingOrderService;
import com.lundong.k3wise.service.PaymentRequestService;
import com.lundong.k3wise.service.PurchaseOrderService;
import com.lundong.k3wise.service.PurchaseRequisitionService;
import com.lundong.k3wise.util.DataUtil;
import com.lundong.k3wise.util.SignUtil;
import com.lundong.k3wise.util.StringUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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

	@Autowired
	private OutsourcingOrderService outsourcingOrderService;

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
		t.setName("覃xx");
		t.setNumber("E00000xx");
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

	@Test
	void t15() {
//		SignUtil.checkBill("/PO", "PO20231024003", "2");
//		SignUtil.checkBill("/Purchase_Requisition", "POREQ000961", "1");
//		SignUtil.checkBill("/Bill1000020", "CGHT000098", "2");
		SignUtil.checkBill("/Purchase_Requisition", "POREQ000961", "1");
		SignUtil.checkBill("/PO", "PO20231102002", "1");
	}

	@Test
	void t16() {
		String userIdByPhone = SignUtil.getUserIdByMobile("123");
		System.out.println(userIdByPhone);
	}

	@Test
	void t17() {
		PaymentRequestDetail p1 = new PaymentRequestDetail();
		p1.setProjectName("项目1");
		p1.setAmountForSrc(1212.21);
		PaymentRequestDetail p2 = new PaymentRequestDetail();
		p2.setProjectName("项目1");
		p2.setAmountForSrc(2.21);
		PaymentRequestDetail p3 = new PaymentRequestDetail();
		p3.setProjectName("项目2");
		p3.setAmountForSrc(2000.21);

		List<PaymentRequestDetail> paymentRequestDetailList = new ArrayList<>();
		paymentRequestDetailList.add(p1);
		paymentRequestDetailList.add(p2);
		paymentRequestDetailList.add(p3);

		Map<String, List<PaymentRequestDetail>> projectNameMap = paymentRequestDetailList.stream()
				.collect(Collectors.groupingBy(PaymentRequestDetail::getProjectName));

		String userIdByPhone = StringUtil.summaryPaymentRequest(projectNameMap);
		System.out.println(userIdByPhone);
	}

	@Test
	void t18() {
		List<Employee> employeeList = SignUtil.employeeList();
		for (Employee employee : employeeList) {
			System.out.println(employee);
		}
	}

	@Test
	void t19() {
		List<OutsourcingOrder> outsourcingOrders = outsourcingOrderService.outsourcingOrderList();
		for (OutsourcingOrder outsourcingOrder : outsourcingOrders) {
			System.out.println(outsourcingOrder);
		}
	}

	@Test
	void t20() {
		String a = "12345\"'\\67&8	/1<>?!";
		System.out.println(a);
		SignUtil.generateApprovalInstanceTest("t-g104btexMK22MZDW6UUJNXCSCDRBBGCUKEZK73YL", "4DBD1FFD-5AF5-4875-A0B9-A51C318EE5A2", "fa222fd1",
				"[{\"id\":\"f1\", \"type\": \"input\", \"value\":\"" + StringUtil.nullIsEmpty(a) + "\"}]");
	}
}
