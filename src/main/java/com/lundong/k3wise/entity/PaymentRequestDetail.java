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

	/**
	 * 项目名称代码
	 */
	@JSONField(name = "FBase")
	private NumberAndNameType base;

	/**
	 * 项目
	 */
	@JSONField(name = "FBase1")
	private NumberAndNameType base1;

	/**
	 * 需求部门
	 */
	@JSONField(name = "FBase3")
	private NumberAndNameType base3;

	/**
	 * 项目名称
	 */
	@JSONField(name = "FBase4")
	private NumberAndNameType base4;

	/**
	 * 物料类别
	 */
	@JSONField(name = "FBase5")
	private NumberAndNameType base5;

	/**
	 * 预算一类
	 */
	@JSONField(name = "FBase6")
	private NumberAndNameType base6;

	/**
	 * 预算二类
	 */
	@JSONField(name = "FBase7")
	private NumberAndNameType base7;

	/**
	 * 是否预算内
	 */
	@JSONField(name = "FBase8")
	private NumberAndNameType base8;

	/**
	 * 请购人
	 */
	@JSONField(name = "FBase9")
	private NumberAndNameType base9;

	/**
	 * 在建工程编号
	 */
	@JSONField(name = "FBase10")
	private NumberAndNameType base10;

	/**
	 * 请购用途
	 */
	@JSONField(name = "FText1")
	private String text1;

	/**
	 * 申请理由
	 */
	@JSONField(name = "FText2")
	private String text2;

	/**
	 * 采购申请备注
	 */
	@JSONField(name = "FText3")
	private String text3;

	/**
	 * 项目名称NAME
	 */
	private String projectName;

	/**
	 * 需求部门NAME
	 */
	private String demandDepartmentName;

	/**
	 * 在建工程编号NAME
	 */
	private String constructProjectNumberName;

}
