package com.lundong.k3wise.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 付款申请单
 *
 * @author RawChen
 * @date 2023-08-23 11:42
 */
@Data
public class PaymentRequest {

	@JSONField(name = "FPayBillID_CN")
	private Integer payBillId;

	/**
	 * 单据日期
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JSONField(name = "FDate")
	private LocalDateTime date;

	/**
	 * 单据号
	 */
	@JSONField(name = "FNumber")
	private String number;

	/**
	 * 付款类型
	 */
	@JSONField(name = "FPayType")
	private Integer payType;

	/**
	 * 核算项目类别
	 */
	@JSONField(name = "FItemClassID")
	private NumberAndNameType itemClassId;

	/**
	 * 核算项目
	 */
	@JSONField(name = "FCustomer")
	private NumberAndNameType customer;

	/**
	 * 币别
	 */
	@JSONField(name = "FCurrencyID")
	private NumberAndNameType currencyId;

	/**
	 * 金额
	 */
	@JSONField(name = "FAmountFor")
	private Double amountFor;

	/**
	 * 核算项目开户银行
	 */
	@JSONField(name = "FRPBank")
	private String rpBank;

	/**
	 * 核算项目银行账号
	 */
	@JSONField(name = "FBankAcct")
	private String bankAcct;

	/**
	 * 核算项目户名
	 */
	@JSONField(name = "FBankAcctName")
	private String bankAcctName;

	/**
	 * 付款理由
	 */
	@JSONField(name = "FExplanation")
	private String explanation;


	/**
	 * 业务员
	 */
	@JSONField(name = "FEmployee")
	private NumberAndNameType employee;

	/**
	 * 明细
	 */
	@JSONField(name = "detail")
	private List<PaymentRequestDetail> detail;

	/**
	 * 备注
	 */
	@JSONField(name = "FText")
	private String text;

	/**
	 * 结算方式
	 */
	@JSONField(name = "FBase12")
	private NumberAndNameType base12;
}
