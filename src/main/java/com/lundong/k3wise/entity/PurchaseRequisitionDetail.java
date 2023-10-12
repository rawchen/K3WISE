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

//	/**
//	 * 客户BOM
//	 */
//	@JSONField(name = "FBomInterID")
//	private NumberAndNameType bomInterId;

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

	/**
	 * 请购人
	 */
	@JSONField(name = "FEntrySelfP0143")
	private NumberAndNameType entrySelfP0143;

	/**
	 * 申请理由
	 */
	@JSONField(name = "FEntrySelfP0140")
	private String entrySelfP0140;

	/**
	 * 项目名称
	 */
	@JSONField(name = "FEntrySelfP0135")
	private NumberAndNameType entrySelfP0135;

	/**
	 * 需求部门
	 */
	@JSONField(name = "FEntrySelfP0136")
	private NumberAndNameType entrySelfP0136;

	/**
	 * 物料类别
	 */
	@JSONField(name = "FEntrySelfP0137")
	private NumberAndNameType entrySelfP0137;

	/**
	 * 是否预算内
	 */
	@JSONField(name = "FEntrySelfP0142")
	private NumberAndNameType entrySelfP0142;

	/**
	 * 预算一类
	 */
	@JSONField(name = "FEntrySelfP0138")
	private NumberAndNameType entrySelfP0138;

	/**
	 * 预算二类
	 */
	@JSONField(name = "FEntrySelfP0139")
	private NumberAndNameType entrySelfP0139;

	/**
	 * 在建工程编号
	 */
	@JSONField(name = "FEntrySelfP0145")
	private NumberAndNameType entrySelfP0145;
}
