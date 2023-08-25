package com.lundong.k3wise.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * 采购申请单明细
 *
 * @author RawChen
 * @date 2023-06-25 17:15
 */
@Data
public class PurchaseRequisitionDetail {

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
	 * 供应商
	 */
	@JSONField(name = "FSupplyID")
	private NumberAndNameType supplyId;

	/**
	 * 数量
	 */
	@JSONField(name = "Fauxqty")
	private Double fauxqty;

	/**
	 * 辅助数量
	 */
	@JSONField(name = "FSecQty")
	private Double secQty;

	/**
	 * 建议采购日期
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JSONField(name = "FAPurchTime")
	private LocalDateTime aPurchTime;

	/**
	 * 到货日期
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JSONField(name = "FFetchTime")
	private LocalDateTime fetchTime;

	/**
	 * 客户BOM
	 */
	@JSONField(name = "FBomInterID")
	private NumberAndNameType bomInterId;

	/**
	 * 提前期
	 */
	@JSONField(name = "FLeadTime")
	private Double leadTime;

	/**
	 * 用途
	 */
	@JSONField(name = "Fuse")
	private String use;

	/**
	 * 源单单号
	 */
	@JSONField(name = "FSourceBillNo")
	private String sourceBillNo;

	/**
	 * 计划模式
	 */
	@JSONField(name = "FPlanMode")
	private NumberAndNameType planMode;

	/**
	 * 计划跟踪号
	 */
	@JSONField(name = "FMTONo")
	private String mtono;

}
