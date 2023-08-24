package com.lundong.k3wise.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 采购订单
 *
 * @author RawChen
 * @date 2023-06-25 15:13
 */
@Data
public class PurchaseOrder {

	@JSONField(name = "FInterID")
	private Integer interId;

	@JSONField(name = "FTranType")
	private Integer tranType;

	@JSONField(name = "FNumber")
	private String number;

	/**
	 * 供应商
	 */
	@JSONField(name = "FSupplyID")
	private NumberAndNameType supplyId;

	/**
	 * 日期
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JSONField(name = "Fdate")
	private LocalDateTime date;

	/**
	 * 单据号
	 */
	@JSONField(name = "FBillNo")
	private String billNo;

	/**
	 * 结算方式
	 */
	@JSONField(name = "FSettleID")
	private NumberAndNameType settleId;

	/**
	 * 币别
	 */
	@JSONField(name = "FCurrencyID")
	private NumberAndNameType currencyId;

	/**
	 * 汇率
	 */
	@JSONField(name = "FExchangeRate")
	private Double exchangeRate;


	/**
	 * 项目名称
	 */
	@JSONField(name = "FItemName")
	private String itemName;

	/**
	 * 部门
	 */
	@JSONField(name = "FDeptID")
	private NumberAndNameType deptId;

	/**
	 * 业务员
	 */
	@JSONField(name = "FEmpID")
	private NumberAndNameType empId;

	/**
	 * 明细
	 */
	@JSONField(name = "detail")
	private List<PurchaseOrderDetail> detail;
}
