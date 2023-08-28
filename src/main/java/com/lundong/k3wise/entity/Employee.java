package com.lundong.k3wise.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @author RawChen
 * @date 2023-08-28 13:43
 */
@Data
public class Employee {

	@JSONField(name = "FNumber")
	private String number;

	@JSONField(name = "FName")
	private String name;

	@JSONField(name = "FMobilePhone")
	private String phone;

}
