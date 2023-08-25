package com.lundong.k3wise.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 委外订单
 *
 * @author RawChen
 * @date 2023-08-25 16:42
 */
@Data
public class OutsourcingOrder {

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
	@JSONField(name = "FDate")
	private LocalDateTime date;

	/**
	 * 单据编号
	 */
	@JSONField(name = "FBillNo")
	private String billNo;

	/**
	 * 结算方式
	 */
	@JSONField(name = "FSettleStyle")
	private NumberAndNameType settleStyle;

	/**
	 * 币别
	 */
	@JSONField(name = "FCurrencyID")
	private NumberAndNameType currencyId;

	/**
	 * 部门
	 */
	@JSONField(name = "FDepartment")
	private NumberAndNameType department;

	/**
	 * 业务员
	 */
	@JSONField(name = "FEmployee")
	private NumberAndNameType employee;

	/**
	 * 汇率
	 */
	@JSONField(name = "FExchangeRate")
	private Double exchangeRate;

	/**
	 * 摘要
	 */
	@JSONField(name = "FSummary")
	private String summary;

	/**
	 * 明细
	 */
	@JSONField(name = "detail")
	private List<OutsourcingOrderDetail> detail;
}
