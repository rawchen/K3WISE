package com.lundong.k3wise.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @author RawChen
 * @date 2023-06-25 17:49
 */
@Data
public class NumberAndNameType {

	@JSONField(name = "FNumber")
	private String number;

	@JSONField(name = "FName")
	private String name;
}
