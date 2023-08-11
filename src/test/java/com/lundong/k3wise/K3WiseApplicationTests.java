package com.lundong.k3wise;

import com.alibaba.fastjson.JSONObject;
import com.lundong.k3wise.entity.ApprovalInstance;
import com.lundong.k3wise.entity.ApprovalInstanceForm;
import com.lundong.k3wise.enums.DataTypeEnum;
import com.lundong.k3wise.service.SystemService;
import com.lundong.k3wise.util.DataUtil;
import com.lundong.k3wise.util.SignUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class K3WiseApplicationTests {

	@Autowired
	private SystemService systemService;

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
		String s = systemService.syncPurchaseRequisition();
		System.out.println(s);
	}
}
