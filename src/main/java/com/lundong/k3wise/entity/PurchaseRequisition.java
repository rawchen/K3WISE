package com.lundong.k3wise.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 采购申请单
 *
 * @author RawChen
 * @date 2023-06-25 15:13
 */
@Data
public class PurchaseRequisition {

	@JSONField(name = "FInterID")
	private Integer interId;

	@JSONField(name = "FTranType")
	private Integer tranType;

	@JSONField(name = "FNumber")
	private String number;

	/**
	 * 单据号
	 */
	@JSONField(name = "FBillNo")
	private String billNo;

	/**
	 * 项目名称
	 */
	@JSONField(name = "FItemName")
	private String itemName;

	/**
	 * 日期
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JSONField(name = "Fdate")
	private LocalDateTime date;

	/**
	 * 申请人
	 */
	@JSONField(name = "FRequesterID")
	private NumberAndNameType requesterId;

	/**
	 * 使用部门
	 */
	@JSONField(name = "FDeptID")
	private NumberAndNameType deptId;

	/**
	 * 使用部门
	 */
	@JSONField(name = "Fnote")
	private String note;

	/**
	 * 采购申请单明细
	 */
	@JSONField(name = "detail")
	private List<PurchaseRequisitionDetail> detail;
}
