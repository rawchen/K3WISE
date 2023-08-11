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
	 * 计划模式
	 */
	@JSONField(name = "FPlanMode")
	private NumberAndNameType planMode;


	/**
	 * 数量
	 */
	@JSONField(name = "Fauxqty")
	private Integer fauxqty;

	/**
	 * 到货日期
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JSONField(name = "FFetchTime")
	private LocalDateTime fetchTime;

	/**
	 * 用途
	 */
	@JSONField(name = "Fuse")
	private String use;

}
