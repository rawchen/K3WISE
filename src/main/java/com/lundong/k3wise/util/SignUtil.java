package com.lundong.k3wise.util;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lundong.k3wise.config.Constants;
import com.lundong.k3wise.entity.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author RawChen
 * @date 2023-06-25 14:33
 */
@Slf4j
@Component
public class SignUtil {

	/**
	 * 飞书自建应用获取tenant_access_token
	 */
	public static String getAccessToken(String appId, String appSecret) {

		JSONObject object = new JSONObject();
		object.put("app_id", appId);
		object.put("app_secret", appSecret);
		String resultStr = "";
		JSONObject resultObject = null;
		for (int i = 0; i < 3; i++) {
			try {
				HttpResponse execute = HttpRequest.post("https://open.feishu.cn/open-apis/auth/v3/tenant_access_token/internal")
						.form(object)
						.execute();
				resultStr = execute.body();
				execute.close();
				if (StringUtils.isNotEmpty(resultStr)) {
					resultObject = JSON.parseObject(resultStr);
					if (resultObject.getInteger("code") != 0) {
						log.error("获取tenant_access_token失败，重试 {} 次, body: {}", i + 1, resultStr);
						try {
							Thread.sleep(2000);
						} catch (InterruptedException ecp) {
							log.error("sleep异常", ecp);
						}
					}
				}
			} catch (Exception e) {
				log.error("获取tenant_access_token异常，重试 {} 次, message: {}, body: {}", i + 1, e.getMessage(), resultStr);
				try {
					Thread.sleep(2000);
				} catch (InterruptedException ecp) {
					log.error("sleep异常", ecp);
				}
			}
			if (resultObject != null && resultObject.getInteger("code") == 0) {
				break;
			}
		}
		// 重试完检测
		if (resultObject == null || resultObject.getInteger("code") != 0) {
			log.error("重试3次获取tenant_access_token后都失败");
			return "";
		} else {
			String tenantAccessToken = resultObject.getString("tenant_access_token");
			if (tenantAccessToken != null) {
				return tenantAccessToken;
			}
		}
		log.error("access_token获取不成功: {}", resultStr);
		return "";
	}

	/**
	 * 金蝶K3WISE通过授权码生成Token
	 *
	 * @param authorityCode
	 * @return
	 */
	public static String getToken(String authorityCode) {
		JSONObject object = new JSONObject();
		object.put("authorityCode", authorityCode);
		String resultStr = HttpRequest.get(Constants.K3API + "/Token/Create")
				.form(object)
				.execute().body();
		if (StringUtils.isNotEmpty(resultStr)) {
			JSONObject resultObject = (JSONObject) JSON.parse(resultStr);
			if (resultObject.getInteger("StatusCode") == 200) {
				JSONObject data = resultObject.getJSONObject("Data");
				if (data != null) {
					return data.getString("Token");
				}
			}
		}
		return "";
	}

	/**
	 * 金蝶K3WISE通过授权码生成Token
	 *
	 * @return
	 */
	public static String getToken() {
		return getToken(Constants.AUTHORITY_CODE);
	}

	/**
	 * 审核（启动审核、审核、驳回）
	 *
	 * @param formCode   单据标识
	 * @param formNumber 单据编号
	 * @param status     审核动作
	 */
	public static void checkBill(String formCode, String formNumber, String status) {
		String paramDetailJson = "{\"Data\": {\"FBillNo\": \"" + formNumber + "\",\"FChecker\": \"" + Constants.APPROVAL_NAME + "\",\"FCheckDirection\": \"" + status + "\",\"FDealComment\": \"\"}}";
		String resultDetailStr = HttpRequest.post(Constants.K3API + File.separator + formCode + Constants.CHECK_BILL + getToken())
				.body(paramDetailJson)
				.execute().body();
		if (StringUtils.isNotEmpty(resultDetailStr)) {
			JSONObject resultDetailObject = (JSONObject) JSON.parse(resultDetailStr);
			if (resultDetailObject.getInteger("StatusCode") == 200) {
				log.info("FBillNo: {},formCode: {},status: {} 启动审核/审核/驳回动作成功", formNumber, formCode, status);
			} else {
				log.error("FBillNo: {},formCode: {},status: {} 启动审核/审核/驳回动作失败：{}", formNumber, formCode, status, resultDetailStr);
			}
		}
	}

	/**
	 * 创建审批实例（生成采购申请单审批）
	 *
	 * @param accessToken
	 * @param pr
	 * @param approvalCode
	 * @param userId
	 * @return
	 */
	public static <T> String generateApprovalInstance(String accessToken, T pr, String approvalCode, String userId, String employeeId) {
		JSONObject object = new JSONObject();
		object.put("approval_code", approvalCode);
		object.put("user_id", userId);
		object.put("form", StringUtil.combinFormString(pr, employeeId));
		log.info("combinFormString: {}", StringUtil.combinFormString(pr, employeeId));

		String resultStr = "";
		JSONObject resultObject = null;
		for (int i = 0; i < 3; i++) {
			try {
				resultStr = HttpRequest.post("https://open.feishu.cn/open-apis/approval/v4/instances")
						.header("Authorization", "Bearer " + accessToken)
						.form(object)
						.timeout(2000)
						.execute().body();
				log.info("generateApprovalInstance(): {}", resultStr);
				if (StringUtils.isNotEmpty(resultStr)) {
					resultObject = JSON.parseObject(resultStr);
					if (resultObject.getInteger("code") != 0) {
						log.error("创建审批实例失败，重试 {} 次, body: {}", i + 1, resultStr);
						try {
							Thread.sleep(2000);
						} catch (InterruptedException ecp) {
							log.error("sleep异常", ecp);
						}
					}
				}
			} catch (Exception e) {
				log.error("创建审批实例异常，重试 {} 次, message: {}, body: {}", i + 1, e.getMessage(), resultStr);
				try {
					Thread.sleep(2000);
				} catch (InterruptedException ecp) {
					log.error("sleep异常", ecp);
				}
			}
			if (resultObject != null && resultObject.getInteger("code") == 0) {
				break;
			}
		}
		// 重试完检测
		if (resultObject == null || resultObject.getInteger("code") != 0) {
			log.error("重试3次创建审批实例后都失败");
			return "";
		} else {
			return resultObject.getJSONObject("data").getString("instance_code");
		}
	}

	/**
	 * 创建审批实例测试
	 *
	 * @param accessToken
	 * @param form
	 * @param approvalCode
	 * @param userId
	 * @return
	 */
	public static <T> String generateApprovalInstanceTest(String accessToken, String approvalCode, String userId, String form) {
		JSONObject object = new JSONObject();
		object.put("approval_code", approvalCode);
		object.put("user_id", userId);
		object.put("form", form);
		String resultStr = HttpRequest.post("https://open.feishu.cn/open-apis/approval/v4/instances")
				.header("Authorization", "Bearer " + accessToken)
				.form(object)
				.timeout(2000)
				.execute().body();
		log.info("generateApprovalInstance(): {}", resultStr);
		if (StringUtils.isNotEmpty(resultStr)) {
			JSONObject resultObject = JSON.parseObject(resultStr);
			if (resultObject.getInteger("code") == 0) {
				return resultObject.getJSONObject("data").getString("instance_code");
			} else {
				log.error("生成审批实例失败: {}", resultStr);
			}
		}
		return null;
	}

	/**
	 * 创建审批实例（生成采购申请单审批）
	 *
	 * @param pr
	 * @param approvalCode
	 * @param userId
	 * @return
	 */
	public static <T> String generateApprovalInstance(T pr, String approvalCode, String userId, String employeeId) {
		String accessToken = getAccessToken(Constants.APP_ID_FEISHU, Constants.APP_SECRET_FEISHU);
		return generateApprovalInstance(accessToken, pr, approvalCode, userId, employeeId);
	}

	/**
	 * 获取单个审批实例详情
	 *
	 * @param accessToken
	 * @param instanceId
	 * @return
	 */
	public static ApprovalInstance approvalInstanceDetail(String accessToken, String instanceId) {
		JSONObject object = new JSONObject();
		object.put("user_id_type", "user_id");
		String resultStr = "";
		JSONObject resultObject = null;
		for (int i = 0; i < 3; i++) {
			try {
				resultStr = HttpRequest.get("https://open.feishu.cn/open-apis/approval/v4/instances/" + instanceId)
						.header("Authorization", "Bearer " + accessToken)
						.form(object)
						.execute().body();
				log.info("获取单个审批实例详情接口: {}", resultStr);
				if (StringUtils.isNotEmpty(resultStr)) {
					resultObject = JSON.parseObject(resultStr);
					if (resultObject.getInteger("code") != 0) {
						log.error("获取单个审批实例详情接口失败，重试 {} 次, body: {}", i + 1, resultStr);
						try {
							Thread.sleep(2000);
						} catch (InterruptedException ecp) {
							log.error("sleep异常", ecp);
						}
					}
				}
			} catch (Exception e) {
				log.error("获取单个审批实例详情接口异常，重试 {} 次, message: {}, body: {}", i + 1, e.getMessage(), resultStr);
				try {
					Thread.sleep(2000);
				} catch (InterruptedException ecp) {
					log.error("sleep异常", ecp);
				}
			}
			if (resultObject != null && resultObject.getInteger("code") == 0) {
				break;
			}
		}

		// 重试完检测
		if (resultObject == null || resultObject.getInteger("code") != 0) {
			log.error("重试3次获取tenant_access_token后都失败");
			return null;
		} else {
			return resultObject.getJSONObject("data").toJavaObject(ApprovalInstance.class);
		}
	}

	/**
	 * 获取单个审批实例详情
	 *
	 * @param instanceId
	 * @return
	 */
	public static ApprovalInstance approvalInstanceDetail(String instanceId) {
		String accessToken = getAccessToken(Constants.APP_ID_FEISHU, Constants.APP_SECRET_FEISHU);
		return approvalInstanceDetail(accessToken, instanceId);
	}

	/**
	 * 通过名称获取userId
	 *
	 * @param name
	 * @return
	 */
	public static String getUserIdByName(String accessToken, String name) {
		List<FeishuUser> users = new ArrayList<>();
		String pageToken = "";
		boolean hasMore = true;
		while (hasMore) {
			JSONObject jsonObject = null;
			String resultStr = "";
			for (int i = 0; i < 3; i++) {
				try {
					HttpResponse response = HttpRequest.get("https://open.feishu.cn/open-apis/ehr/v1/employees" +
									"?page_size=100&status=2&status=4&user_id_type=user_id&view=basic" +
									(!StringUtil.isEmpty(pageToken) ? "&page_token=" : "") + pageToken)
							.header("Authorization", "Bearer " + accessToken)
							.execute();

					resultStr = response.body();
					response.close();
					jsonObject = JSON.parseObject(resultStr);
				} catch (Exception e) {
					log.error("通过名称获取userId接口请求失败，重试 {} 次, message: {}, body: {}", i + 1, e.getMessage(), resultStr);
					try {
						Thread.sleep(2000);
					} catch (InterruptedException ecp) {
						log.error("sleep异常", ecp);
					}
				}
				if (jsonObject != null && jsonObject.getInteger("code") != 0) {
					log.error("通过名称获取userId接口请求失败，重试 {} 次, body: {}", i + 1, resultStr);
				} else if (jsonObject != null && jsonObject.getInteger("code") == 0) {
					break;
				}
			}

			if (jsonObject == null || jsonObject.getInteger("code") != 0) {
				log.error("通过名称获取userId接口调用失败: {}", resultStr);
				return "";
			}

			JSONObject data = (JSONObject) jsonObject.get("data");
			JSONArray items = (JSONArray) data.get("items");
			for (int i = 0; i < items.size(); i++) {
				// 构造飞书用户对象
				FeishuUser feishuUser = items.getJSONObject(i)
						.getJSONObject("system_fields")
						.toJavaObject(FeishuUser.class);
				feishuUser.setUserId(items.getJSONObject(i).getString("user_id"));
				if (items.getJSONObject(i).getJSONObject("system_fields")
						.getJSONObject("job") != null) {
					feishuUser.setJobTitle(items.getJSONObject(i)
							.getJSONObject("system_fields")
							.getJSONObject("job")
							.getString("name"));
				}
				users.add(feishuUser);
			}
			if ((boolean) data.get("has_more")) {
				pageToken = data.getString("page_token");
			} else {
				hasMore = false;
			}
		}
		for (FeishuUser user : users) {
			if (name.equals(user.getName())) {
				return user.getUserId();
			}
		}
		return "";
	}

	/**
	 * 通过名称获取userId
	 *
	 * @param name
	 * @return
	 */
	public static String getUserIdByName(String name) {
		String accessToken = getAccessToken(Constants.APP_ID_FEISHU, Constants.APP_SECRET_FEISHU);
		return getUserIdByName(accessToken, name);
	}

	/**
	 * 通过手机号获取userId
	 *
	 * @param mobile
	 * @return
	 */
	public static String getUserIdByMobile(String mobile) {
		String accessToken = getAccessToken(Constants.APP_ID_FEISHU, Constants.APP_SECRET_FEISHU);
		return getUserIdByMobile(accessToken, mobile);
	}

	/**
	 * 通过手机号获取userId
	 *
	 * @param mobile
	 * @return
	 */
	public static String getUserIdByMobile(String accessToken, String mobile) {
		String resultStr = HttpRequest.post("https://open.feishu.cn/open-apis/contact/v3/users/batch_get_id?user_id_type=user_id")
				.header("Authorization", "Bearer " + accessToken)
				.body("{\"mobiles\": [\"" + mobile + "\"]}")
				.execute()
				.body();
		JSONObject jsonObject = JSON.parseObject(resultStr);
		if (jsonObject.getInteger("code") != 0) {
			log.error("通过手机号获取userId错误: {}", resultStr);
			return "";
		}
		JSONObject data = (JSONObject) jsonObject.get("data");
		JSONArray items = (JSONArray) data.get("user_list");
		for (int i = 0; i < items.size(); i++) {
			String userId = items.getJSONObject(i).getString("user_id");
			if (userId != null) {
				return userId;
			}
		}
		return "";
	}

	/**
	 * 通过金蝶职员编号获取userId
	 *
	 * @param requester 职员
	 * @return
	 */
	public static String getUserIdByEmployee(NumberAndNameType requester) {
		String employeePhone = "";
		List<Employee> employeeList = employeeList();
		for (Employee employee : employeeList) {
			if (employee.getNumber() != null && requester.getNumber().equals(employee.getNumber())) {
				employeePhone = employee.getPhone();
				break;
			}
		}
		if (StrUtil.isEmpty(employeePhone)) {
			for (Employee employee : employeeList) {
				if (employee.getName() != null && requester.getName().equals(employee.getName())) {
					employeePhone = employee.getPhone();
					break;
				}
			}
		}
		// 如果手机号不是空的就用手机号匹配
		if (!StringUtil.isEmpty(employeePhone)) {
			log.info("金蝶获取手机号：{}", employeePhone);
			String result = getUserIdByMobile(employeePhone);
			log.info("手机号获取USERID：{}", result);
			if (StringUtil.isEmpty(result)) {
				log.info("姓名匹配：{}", requester.getName());
				String userIdByName = getUserIdByName(requester.getName());
				log.info("姓名获取USERID：{}", userIdByName);
				return userIdByName;
			} else {
				return result;
			}
		} else {
			// 如果手机号是空的就用名字匹配
			log.info("姓名匹配：{}", requester.getName());
			String userIdByName = getUserIdByName(requester.getName());
			log.info("姓名获取USERID：{}", userIdByName);
			return userIdByName;
		}
	}

	/**
	 * 金蝶职员列表
	 *
	 * @return
	 */
	public static List<Employee> employeeList() {
		List<OutsourcingOrder> prList = new ArrayList<>();
		String paramJson = "{\"Data\": {\"Fields\": \"FNumber,FName,FMobilePhone\",\"Top\": \"100\"," +
				"\"PageSize\": \"10000\",\"PageIndex\": \"1\",\"Filter\": \"\",\"OrderBy\": \"\",\"SelectPage\": \"2\"}}";
		String resultStr = HttpRequest.post(Constants.K3API + Constants.EMPLOYEE + Constants.GET_LIST + SignUtil.getToken())
				.body(paramJson)
				.timeout(2000)
				.execute().body();

		if (StringUtils.isNotEmpty(resultStr)) {
			JSONObject resultObject = (JSONObject) JSON.parse(resultStr);
			if (resultObject.getInteger("StatusCode") == 200) {
				JSONObject data = resultObject.getJSONObject("Data");
				if (data != null) {
					JSONArray dataList = data.getJSONArray("Data");
					if (dataList != null && !dataList.isEmpty()) {
						return dataList.toJavaList(Employee.class);
					}
				}
			}
		}
		return Collections.emptyList();
	}
}
