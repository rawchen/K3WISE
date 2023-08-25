package com.lundong.k3wise.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * 采购订单明细
 *
 * @author RawChen
 * @date 2023-06-25 17:15
 */
@Data
public class PurchaseOrderDetail {

	@JSONField(name = "FDetailID2")
	private Integer detailId;

	/**
	 * 行号
	 */
	@JSONField(name = "FEntryID2")
	private Integer entryID2;

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
	@JSONField(name = "FItemModel")
	private String itemModel;

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
	 * 含税单价
	 */
	@JSONField(name = "FAuxTaxPrice")
	private Double auxTaxPrice;

	/**
	 * 实际含税单价
	 */
	@JSONField(name = "FAuxPriceDiscount")
	private Double auxPriceDiscount;

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
	@JSONField(name = "Fdate1")
	private LocalDateTime date;

	/**
	 * 税率
	 */
	@JSONField(name = "FCess")
	private Double cess;

	/**
	 * 备注
	 */
	@JSONField(name = "Fnote")
	private String note;

	/**
	 * 源单单号
	 */
	@JSONField(name = "FSourceBillNo")
	private String sourceBillNo;

}
