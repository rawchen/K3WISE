package com.lundong.k3wise.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @author RawChen
 * @date 2023-06-25 14:02
 */
@EnableConfigurationProperties
public class Constants {

	// 飞书自建应用 App ID
	public final static String APP_ID_FEISHU = "cli_a440ac1caxxxxxxx";

	// 飞书自建应用 App Secret
	public final static String APP_SECRET_FEISHU = "9fAl8ikMsuy50SIBZuEIucJxxxxxxxxx";

	// 飞书自建应用订阅事件 Encrypt Key
	public final static String ENCRYPT_KEY = "";

	// 飞书自建应用订阅事件 Verification Token
	public final static String VERIFICATION_TOKEN = "Fj0GmhaD2q6oQ8L0Dl4xOgQhxxxxxxx";

	// 采购申请审批CODE
	public final static String PURCHASE_REQUISITION_APPROVAL_CODE = "CD87DF39-2C97-4EBA-9925-38BB796C712A";

	// 采购订单审批CODE
	public final static String PURCHASE_ORDER_APPROVAL_CODE = "305B79EE-913A-4EF4-AB9F-7D5C757842B1";

	// 付款申请审批CODE
	public final static String PAYMENT_REQUEST_APPROVAL_CODE = "A7087D6D-28BA-4382-A021-1765B250035C";

	// 采购合同审批CODE
	public final static String PURCHASE_CONTRACT_APPROVAL_CODE = "F82243A6-9093-459F-9515-36F73A904999";

	// 委外订单审批CODE
	public final static String OUTSOURCING_ORDER_APPROVAL_CODE = "97764BD0-25C6-4535-9AC6-A7C627994D2C";

	// 金蝶K3WISE授权码
	public final static String AUTHORITY_CODE = "cc7c2a733c0a29ecd55ad722a8fb42a18878ebb15f9710e3";

	// 金蝶K3WISE网址
	public final static String K3API = "http://192.168.110.223/K3API";

	// 增 Save
	public final static String SAVE = "/Save" + "?token=";

	// 改 Update
	public final static String UPDATE = "/Update" + "?token=";

	// 删 Delete
	public final static String DELETE = "/Delete" + "?token=";

	// 审核 CheckBill
	public final static String CHECK_BILL = "/CheckBill" + "?token=";

	// 单据查询 GetDetail
	public final static String GET_DETAIL = "/GetDetail" + "?token=";

	// 列表查询 GetList
	public final static String GET_LIST = "/GetList" + "?token=";

	// 采购申请单
	public final static String PURCHASE_REQUISITION = "/Purchase_Requisition";

	// 采购订单
	public final static String PURCHASE_ORDER = "/PO";

	// 付款申请
	public final static String PAYMENT_REQUEST = "/Bill1000040";

	// 采购合同/合同(应收)
	public final static String PURCHASE_CONTRACT = "/Bill1000019";

	// 委外订单
	public final static String OUTSOURCING_ORDER = "/Subcontract_Order";




}
