package com.lundong.k3wise.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * 采购合同/合同(应付)明细
 *
 * @author RawChen
 * @date 2023-08-23 17:55
 */
@Data
public class PurchaseContractDetail {

	/**
	 * 行号
	 */
	@JSONField(name = "FIndex3")
	private Integer index3;

	/**
	 * 产品代码
	 */
	@JSONField(name = "FProductID2")
	private NumberAndNameType productId2;

	/**
	 * 产品名称
	 */
	@JSONField(name = "FItemID40828")
	private String itemId40828;

	/**
	 * 规格型号
	 */
	@JSONField(name = "FItemID40891")
	private String itemId40891;

	/**
	 * 辅助属性
	 */
	@JSONField(name = "FAuxPropID")
	private NumberAndNameType auxPropId;

	/**
	 * 计量单位
	 */
	@JSONField(name = "FUnitID")
	private NumberAndNameType unitId;

	/**
	 * 数量
	 */
	@JSONField(name = "FQuantity")
	private Double quantity;

	/**
	 * 含税单价
	 */
	@JSONField(name = "FTaxPriceFor")
	private Double taxPriceFor;

	/**
	 * 不含税单价
	 */
	@JSONField(name = "FPriceFor")
	private Double priceFor;

	/**
	 * 价税合计
	 */
	@JSONField(name = "FAmountIncludeTaxFor")
	private Double amountIncludeTaxFor;

	/**
	 * 税率
	 */
	@JSONField(name = "FTaxRate")
	private Double taxRate;

	/**
	 * 税额
	 */
	@JSONField(name = "FTaxFor")
	private Double taxFor;

	/**
	 * 金额
	 */
	@JSONField(name = "FAmountFor3")
	private Double amountFor3;

	/**
	 * 备注
	 */
	@JSONField(name = "FExplanation_3")
	private String explanation;

	/**
	 * 需求部门
	 */
	@JSONField(name = "FBase")
	private NumberAndNameType base;

	/**
	 * 项目名称
	 */
	@JSONField(name = "FBase1")
	private NumberAndNameType base1;

	/**
	 * 请购人
	 */
	@JSONField(name = "FBase2")
	private NumberAndNameType base2;

	/**
	 * 物料类别
	 */
	@JSONField(name = "FBase3")
	private NumberAndNameType base3;

	/**
	 * 预算一类
	 */
	@JSONField(name = "FBase4")
	private NumberAndNameType base4;

	/**
	 * 预算二类
	 */
	@JSONField(name = "FBase5")
	private NumberAndNameType base5;

	/**
	 * 是否预算内
	 */
	@JSONField(name = "FBase6")
	private NumberAndNameType base6;

	/**
	 * 在建工程编号
	 */
	@JSONField(name = "FBase7")
	private NumberAndNameType base7;

	/**
	 * 申请理由
	 */
	@JSONField(name = "FText1")
	private String text1;

	/**
	 * 请购用途
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
