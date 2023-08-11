package com.lundong.k3wise.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @author RawChen
 * @date 2023-08-09 10:57
 */
@Data
public class ApprovalTask {

	@JSONField(name = "id")
	private String id;

	@JSONField(name = "user_id")
	private String userId;

	@JSONField(name = "open_id")
	private String openId;

	@JSONField(name = "status")
	private String status;

	@JSONField(name = "node_id")
	private String nodeId;

	@JSONField(name = "node_name")
	private String nodeName;

	@JSONField(name = "custom_node_id")
	private String customNodeId;

	@JSONField(name = "type")
	private String type;

	@JSONField(name = "start_time")
	private String startTime;

	@JSONField(name = "end_time")
	private String endTime;
}
