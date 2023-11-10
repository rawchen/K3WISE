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
	 * 建议发料日期
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JSONField(name = "FPayShipDate")
	private LocalDateTime payShipDate;

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

	/**
	 * 需求部门
	 */
	@JSONField(name = "FBase1")
	private NumberAndNameType base1;

	/**
	 * 项目名称
	 */
	@JSONField(name = "FBase2")
	private NumberAndNameType base2;

	/**
	 * 物料归属
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
	 * 请购人
	 */
	@JSONField(name = "FBase7")
	private NumberAndNameType base7;

	/**
	 * 物料类别
	 */
	@JSONField(name = "FBase8")
	private NumberAndNameType base8;

	/**
	 * 在建工程编号
	 */
	@JSONField(name = "FBase9")
	private NumberAndNameType base9;

	/**
	 * 申请理由
	 */
	@JSONField(name = "FText")
	private String text;

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
