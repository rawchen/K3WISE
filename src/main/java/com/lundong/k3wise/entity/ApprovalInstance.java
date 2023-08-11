package com.lundong.k3wise.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.List;

/**
 * @author RawChen
 * @date 2023-08-09 10:35
 */
@Data
public class ApprovalInstance {

	@JSONField(name = "approval_name")
	private String approvalName;

	@JSONField(name = "start_time")
	private String startTime;

	@JSONField(name = "end_time")
	private String endTime;

	@JSONField(name = "user_id")
	private String userId;

	@JSONField(name = "open_id")
	private String openId;

	@JSONField(name = "serial_number")
	private String serialNumber;

	@JSONField(name = "department_id")
	private String departmentId;

	@JSONField(name = "status")
	private String status;

	@JSONField(name = "uuid")
	private String uuid;

	@JSONField(name = "form")
	private String form;

	@JSONField(name = "modified_instance_code")
	private String modifiedInstanceCode;

	@JSONField(name = "reverted_instance_code")
	private String revertedInstanceCode;

	@JSONField(name = "approval_code")
	private String approvalCode;

	@JSONField(name = "reverted")
	private boolean reverted;

	@JSONField(name = "task_list")
	private List<ApprovalTask> taskList;

}
