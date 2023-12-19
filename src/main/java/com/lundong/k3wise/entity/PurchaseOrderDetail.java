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

	/**
	 * 物料归属
	 */
	@JSONField(name = "FEntrySelfP0273")
	private NumberAndNameType entrySelfP0273;


	/**
	 * 请购人
	 */
	@JSONField(name = "FEntrySelfP0278")
	private NumberAndNameType entrySelfP0278;

	/**
	 * 申请理由
	 */
	@JSONField(name = "FEntrySelfP0277")
	private String entrySelfP0277;

	/**
	 * 项目名称
	 */
	@JSONField(name = "FEntrySelfP0272")
	private NumberAndNameType entrySelfP0272;

	/**
	 * 需求部门
	 */
	@JSONField(name = "FEntrySelfP0271")
	private NumberAndNameType entrySelfP0271;

	/**
	 * 物料类别
	 */
	@JSONField(name = "FEntrySelfP0274")
	private NumberAndNameType entrySelfP0274;

	/**
	 * 是否预算内
	 */
	@JSONField(name = "FEntrySelfP0280")
	private NumberAndNameType entrySelfP0280;

	/**
	 * 预算一类
	 */
	@JSONField(name = "FEntrySelfP0275")
	private NumberAndNameType entrySelfP0275;

	/**
	 * 预算二类
	 */
	@JSONField(name = "FEntrySelfP0276")
	private NumberAndNameType entrySelfP0276;

	/**
	 * 采购申请备注
	 */
	@JSONField(name = "FEntrySelfP0279")
	private String entrySelfP0279;

	/**
	 * 在建工程编号
	 */
	@JSONField(name = "FEntrySelfP0283")
	private NumberAndNameType entrySelfP0283;

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
