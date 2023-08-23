package com.lundong.k3wise.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * 付款申请单明细
 *
 * @author RawChen
 * @date 2023-06-25 17:15
 */
@Data
public class PaymentRequestDetail {

	@JSONField(name = "FBillID2")
	private Integer billId;

	/**
	 * 行号
	 */
	@JSONField(name = "Findex2")
	private String index;

	/**
	 * 源单类型
	 */
	@JSONField(name = "FClassID_SRC")
	private NumberAndNameType classIdSrc;

	/**
	 * 源单单号
	 */
	@JSONField(name = "FBillNo_SRC")
	private String billNoSrc;

	/**
	 * 合同号
	 */
	@JSONField(name = "FContractNo")
	private String contractNo;

	/**
	 * 订单单号
	 */
	@JSONField(name = "FOrderNo")
	private String orderNo;

	/**
	 * 申请付款数量
	 */
	@JSONField(name = "FApplyQuantity")
	private Double applyQuantity;

	/**
	 * 申请付款金额
	 */
	@JSONField(name = "FApplyAmountFor")
	private Double applyAmountFor;

	/**
	 * 产品代码
	 */
	@JSONField(name = "FItemID")
	private NumberAndNameType itemId;

	/**
	 * 产品名称
	 */
	@JSONField(name = "FBaseProperty")
	private String baseProperty;

	/**
	 * 规格型号
	 */
	@JSONField(name = "FBaseProperty1")
	private String baseProperty1;

	/**
	 * 计量单位
	 */
	@JSONField(name = "FBase2")
	private NumberAndNameType base2;

	/**
	 * 数量
	 */
	@JSONField(name = "FQuantity")
	private Double quantity;

	/**
	 * 含税单价
	 */
	@JSONField(name = "FAuxTaxPrice")
	private Double auxTaxPrice;

	/**
	 * 选单单据金额
	 */
	@JSONField(name = "FAmountFor_Src")
	private Double amountForSrc;

	/**
	 * 费用项目
	 */
	@JSONField(name = "FFeeObjID")
	private NumberAndNameType feeObjId;

}
