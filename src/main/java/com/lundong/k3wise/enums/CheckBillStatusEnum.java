package com.lundong.k3wise.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 审核动作类型枚举
 *
 * @author RawChen
 * @date 2023-08-11 18:05
 */
@Getter
@AllArgsConstructor
public enum CheckBillStatusEnum {
	START_AUDIT		("1", "启动审核"),
	AUDIT			("2", "审核"),
	REJECT			("4", "驳回");

	private String code;
	private String desc;

	public static CheckBillStatusEnum getType(String code) {
		for (CheckBillStatusEnum enums : CheckBillStatusEnum.values()) {
			if (enums.getCode().equals(code)) {
				return enums;
			}
		}
		return null;
	}
}
