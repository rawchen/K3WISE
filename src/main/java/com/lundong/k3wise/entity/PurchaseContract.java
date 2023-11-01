package com.lundong.k3wise.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 采购合同/合同(应付)
 *
 * @author RawChen
 * @date 2023-08-23 17:55
 */
@Data
public class PurchaseContract {

	@JSONField(name = "FContractID")
	private Integer contractId;

	/**
	 * 业务员
	 */
	@JSONField(name = "FEmployee")
	private NumberAndNameType employee;

	/**
	 * 摘要
	 */
	@JSONField(name = "FExplanation")
	private String explanation;

	/**
	 * 合同种类
	 */
	@JSONField(name = "FContractTypeID")
	private Integer contractTypeId;

	/**
	 * 合同日期
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JSONField(name = "FDate")
	private LocalDateTime date;

	/**
	 * 合同名称
	 */
	@JSONField(name = "FContractName")
	private String contractName;

	/**
	 * 合同号
	 */
	@JSONField(name = "FContractNo")
	private String contractNo;

	/**
	 * 币别
	 */
	@JSONField(name = "FCurrencyID")
	private NumberAndNameType currencyId;

	/**
	 * 付款条件
	 */
//	@JSONField(name = "FPayCondition")
//	private NumberAndNameType payCondition;

	/**
	 * 核算项目
	 */
	@JSONField(name = "FCustomer")
	private NumberAndNameType customer;

	/**
	 * 总金额
	 */
	@JSONField(name = "FTotalAmountFor")
	private Double totalAmountFor;

	/**
	 * 附注
	 */
	@JSONField(name = "FText")
	private String text;

	/**
	 * 明细
	 */
	@JSONField(name = "detail")
	private List<PurchaseContractDetail> detail;
}
