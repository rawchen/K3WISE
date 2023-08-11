package com.lundong.k3wise.util;

import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lundong.k3wise.config.Constants;
import com.lundong.k3wise.entity.ApprovalInstance;
import com.lundong.k3wise.entity.PurchaseRequisition;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * @author RawChen
 * @date 2023-06-25 14:33
 */
@Slf4j
public class SignUtil {

	/**
	 * 飞书自建应用获取tenant_access_token
	 */
	public static String getAccessToken(String appId, String appSecret) {
		JSONObject object = new JSONObject();
		object.put("app_id", appId);
		object.put("app_secret", appSecret);
		String resultStr = HttpRequest.post("https://open.feishu.cn/open-apis/auth/v3/tenant_access_token/internal")
				.form(object)
				.timeout(2000)
				.execute().body();
		if (StringUtils.isNotEmpty(resultStr)) {
			JSONObject resultObject = (JSONObject) JSON.parse(resultStr);
			if (!"0".equals(resultObject.getString("code"))) {
				return "";
			} else {
				String tenantAccessToken = resultObject.getString("tenant_access_token");
				if (tenantAccessToken != null) {
					return tenantAccessToken;
				}
			}
		}
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
//		System.out.println(resultStr);
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
	 * @param formCode		单据标识
	 * @param formNumber	单据编号
	 * @param status		审核动作
	 */
	public static void checkBill(String formCode, String formNumber, String status) {
		String paramDetailJson = "{\"Data\": {\"FBillNo\": \"" + formNumber + "\",\"FChecker\": \"administrator\",\"FCheckDirection\": \"" + status + "\",\"FDealComment\": \"\"}}";
		String resultDetailStr = HttpRequest.post(Constants.K3API + formCode + Constants.CHECK_BILL + getToken())
				.body(paramDetailJson)
				.timeout(2000)
				.execute().body();
		if (StringUtils.isNotEmpty(resultDetailStr)) {
			JSONObject resultDetailObject = (JSONObject) JSON.parse(resultDetailStr);
			if (resultDetailObject.getInteger("StatusCode") == 200) {
				log.info("FBillNo: {},formCode: {},status: {} 启动审核/审核/驳回动作成功", formNumber, formCode, status);
			} else {
				log.info("FBillNo: {},formCode: {},status: {} 启动审核/审核/驳回动作失败：{}", formNumber, formCode, status, resultDetailObject.getString("Message"));
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
	public static String generateApprovalInstance(String accessToken, PurchaseRequisition pr, String approvalCode, String userId) {
		JSONObject object = new JSONObject();
		object.put("approval_code", approvalCode);
		object.put("user_id", userId);
		object.put("form", StringUtil.combinFormString(pr));
		String resultStr = HttpRequest.post("https://open.feishu.cn/open-apis/approval/v4/instances")
				.header("Authorization", "Bearer " + accessToken)
				.form(object)
				.timeout(2000)
				.execute().body();
		System.out.println(resultStr);
		System.out.println(StringUtil.combinFormString(pr));
		if (StringUtils.isNotEmpty(resultStr)) {
			JSONObject resultObject = JSON.parseObject(resultStr);
			if (resultObject.getInteger("code") == 0) {
				return resultObject.getJSONObject("data").getString("instance_code");
			}
		}
		return "";
	}

	/**
	 * 创建审批实例（生成采购申请单审批）
	 *
	 * @param pr
	 * @param approvalCode
	 * @param userId
	 * @return
	 */
	public static String generateApprovalInstance(PurchaseRequisition pr, String approvalCode, String userId) {
		String accessToken = getAccessToken(Constants.APP_ID_FEISHU, Constants.APP_SECRET_FEISHU);
		return generateApprovalInstance(accessToken, pr, approvalCode, userId);
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
		String resultStr = HttpRequest.get("https://open.feishu.cn/open-apis/approval/v4/instances/" + instanceId)
				.header("Authorization", "Bearer " + accessToken)
				.form(object)
				.timeout(2000)
				.execute().body();
		System.out.println(resultStr);
		if (StringUtils.isNotEmpty(resultStr)) {
			JSONObject resultObject = JSON.parseObject(resultStr);
			if (resultObject.getInteger("code") == 0) {
				return resultObject.getJSONObject("data").toJavaObject(ApprovalInstance.class);
			}
		}
		return null;
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
}
