package com.lundong.k3wise.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * 委外订单明细
 *
 * @author RawChen
 * @date 2023-08-25 16:43
 */
@Data
public class OutsourcingOrderDetail {

	/**
	 * 物料代码
	 */
	@JSONField(name = "FItemID")
	private NumberAndNameType itemId;

	/**
	 * 物料名称
	 */
	@JSONField(name = "FItemName")
	private String itemName;

	/**
	 * 规格型号
	 */
	@JSONField(name = "FSpecification")
	private String specification;

	/**
	 * 辅助属性
	 */
	@JSONField(name = "FAuxPropID")
	private NumberAndNameType auxPropId;

	/**
	 * 单位
	 */
	@JSONField(name = "FUnitID")
	private NumberAndNameType unitId;

	/**
	 * 数量
	 */
	@JSONField(name = "Fauxqty")
	private Double fauxqty;

	/**
	 * 库存数量
	 */
	@JSONField(name = "FStockQtyOnlyForShow")
	private Double stockQtyOnlyForShow;

	/**
	 * 含税单价
	 */
	@JSONField(name = "FAuxTaxPrice")
	private Double auxTaxPrice;

	/**
	 * 价税合计
	 */
	@JSONField(name = "FAllAmount")
	private Double allAmount;

	/**
	 * 交货日期
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JSONField(name = "FFetchDate")
	private LocalDateTime fetchDate;

	/**
	 * 税率
	 */
	@JSONField(name = "FTaxRate")
	private Double taxRate;

	/**
	 * BOM编号
	 */
	@JSONField(name = "FBOMInterID")
	private NumberAndNameType bomInterId;

	/**
	 * 源单编号
	 */
	@JSONField(name = "FBillNo_SRC")
	private String billNoSrc;

	/**
	 * 备注
	 */
	@JSONField(name = "FNOTE")
	private String note;

}
