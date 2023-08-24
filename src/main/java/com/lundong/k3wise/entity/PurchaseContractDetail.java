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

}
