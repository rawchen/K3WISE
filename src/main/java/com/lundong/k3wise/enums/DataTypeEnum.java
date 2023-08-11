package com.lundong.k3wise.enums;

import com.lundong.k3wise.config.Constants;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 单据类型枚举
 *
 * @author RawChen
 * @date 2023-08-02 15:12
 */
@Getter
@AllArgsConstructor
public enum DataTypeEnum {
	PURCHASE_REQUISITION	("PURCHASE_REQUISITION",	"采购申请", Constants.PURCHASE_REQUISITION_APPROVAL_CODE),
	PURCHASE_ORDER			("PURCHASE_ORDER", 			"采购订单", Constants.PURCHASE_ORDER_APPROVAL_CODE),
	PAYMENT_REQUEST			("PAYMENT_REQUEST", 		"付款申请", Constants.PAYMENT_REQUEST_APPROVAL_CODE),
	PURCHASE_CONTRACT		("PURCHASE_CONTRACT", 		"采购合同", Constants.PURCHASE_CONTRACT_APPROVAL_CODE),
	OUTSOURCING_ORDER		("OUTSOURCING_ORDER", 		"委外订单", Constants.OUTSOURCING_ORDER_APPROVAL_CODE);

	private String type;
	private String desc;
	private String code;

	public static DataTypeEnum getType(String dataTypeCode) {
		for (DataTypeEnum enums : DataTypeEnum.values()) {
			if (enums.getType().equals(dataTypeCode)) {
				return enums;
			}
		}
		return null;
	}
}
