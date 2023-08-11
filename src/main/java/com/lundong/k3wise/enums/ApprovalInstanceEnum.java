package com.lundong.k3wise.enums;

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
public enum ApprovalInstanceEnum {

	APPROVED				("APPROVED",			"已通过"),
	PENDING					("PENDING",				"进行中"),
	REJECTED				("REJECTED",			"已拒绝"),
	CANCELED				("CANCELED",			"已撤回"),
	DELETED					("DELETED",				"已删除"),
	REVERTED				("REVERTED",			"已撤销"),
	OVERTIME_CLOSE			("OVERTIME_CLOSE",		"超时关闭"),
	OVERTIME_RECOVER		("OVERTIME_RECOVER",	"超时恢复");

	private String type;
	private String desc;

	public static ApprovalInstanceEnum getType(String dataTypeCode) {
		for (ApprovalInstanceEnum enums : ApprovalInstanceEnum.values()) {
			if (enums.getType().equals(dataTypeCode)) {
				return enums;
			}
		}
		return null;
	}
}
