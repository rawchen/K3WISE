package com.lundong.k3wise.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public class FeishuUser {
//    @JSONField(name = "union_id")
//    private String unionId;
//    @JSONField(name = "open_id")
//    private String openId;
    private String userId;
    @JSONField(name = "name")
    private String name;
    @JSONField(name = "en_name")
    private String enName;
//    @JSONField(name = "nickname")
//    private String nickName;
    @JSONField(name = "email")
    private String email;
    @JSONField(name = "mobile")
    private String mobile;
//    @JSONField(name = "mobile_visible")
//    private String mobileVisible;
    @JSONField(name = "gender")
    private String gender;
    @JSONField(name = "department_id")
    private String departmentId;
//    @JSONField(name = "leader_user_id")
//    private String leaderUserId;
//    @JSONField(name = "city")
//    private String city;
//    @JSONField(name = "country")
//    private String country;
//    @JSONField(name = "work_station")
//    private String workStation;
//    @JSONField(name = "join_time")
//    private String joinTime;
    @JSONField(name = "employee_no")
    private String employeeNo;
    @JSONField(name = "employee_type")
    private String employeeType;
    @JSONField(name = "personal_email")
    private String personal_email;
    private String jobTitle;
    private String deptName;
}
