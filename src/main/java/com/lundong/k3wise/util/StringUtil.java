package com.lundong.k3wise.util;

import com.lundong.k3wise.entity.PurchaseRequisition;
import com.lundong.k3wise.entity.PurchaseRequisitionDetail;

import java.time.format.DateTimeFormatter;

/**
 * @author RawChen
 * @date 2023-06-26 10:21
 */
public class StringUtil {

	/**
	 * 构造创建审批实例参数JSON中的form
	 *
	 * @param pr
	 * @return
	 */
	public static String combinFormString(PurchaseRequisition pr) {
		String json = "[{\"id\":\"f1\",\"type\":\"input\",\"value\":\"单据号\"},{\"id\":\"f2\",\"type\":\"input\",\"value\":\"项目名称\"},{\"id\":\"f3\",\"type\":\"input\",\"value\":\"申请日期\"},{\"id\":\"f4\",\"type\":\"input\",\"value\":\"申请人\"},{\"id\":\"f5\",\"type\":\"input\",\"value\":\"使用部门\"},{\"id\":\"f6\",\"type\":\"input\",\"value\":\"备注\"},{\"id\":\"detail1\",\"type\":\"fieldList\",\"ext\":[],\"value\":[detailJson]}]";

		// 构建明细
		StringBuilder detailResultJson = new StringBuilder();
		for (PurchaseRequisitionDetail detail : pr.getDetail()) {
			String detailJson = "[{\"id\":\"d1\",\"type\":\"input\",\"value\":\"物料代码\"},{\"id\":\"d2\",\"type\":\"input\",\"value\":\"物料名称\"},{\"id\":\"d3\",\"type\":\"input\",\"value\":\"规格型号\"},{\"id\":\"d4\",\"type\":\"input\",\"value\":\"单位\"},{\"id\":\"d5\",\"type\":\"input\",\"value\":\"供应商\"},{\"id\":\"d6\",\"type\":\"input\",\"value\":\"计划模式\"},{\"id\":\"d7\",\"type\":\"input\",\"value\":\"数量\"},{\"id\":\"d8\",\"type\":\"input\",\"value\":\"到货日期\"},{\"id\":\"d9\",\"type\":\"input\",\"value\":\"用途\"}]";
			detailJson = detailJson.replace("物料代码", nullIsEmpty(detail.getItemId().getNumber()))
					.replace("物料名称", nullIsEmpty(detail.getItemId().getName()))
					.replace("规格型号", nullIsEmpty(detail.getItemModel()))
					.replace("单位", nullIsEmpty(detail.getUnitId().getName()))
					.replace("供应商", nullIsEmpty(detail.getSupplyId().getName()))
					.replace("计划模式", nullIsEmpty(detail.getPlanMode().getName()))
					.replace("数量", String.valueOf(detail.getFauxqty()))
					.replace("用途", String.valueOf(detail.getUse()))
					.replace("到货日期", DateTimeFormatter.ofPattern("yyyy年MM月dd日").format(detail.getFetchTime()));
			detailJson += ",";
			detailResultJson.append(detailJson);
		}

		if (pr.getDetail().size() > 0) {
			detailResultJson = new StringBuilder(detailResultJson.substring(0, detailResultJson.length() - 1));
		}

		json = json.replace("单据号", nullIsEmpty(pr.getBillNo()))
				.replace("项目名称", nullIsEmpty(pr.getItemName()))
				.replace("申请日期", DateTimeFormatter.ofPattern("yyyy年MM月dd日").format(pr.getDate()))
				.replace("申请人", nullIsEmpty(pr.getRequesterId().getName()))
				.replace("使用部门", nullIsEmpty(pr.getDeptId().getName()))
				.replace("备注", nullIsEmpty(pr.getNote()))
				.replace("detailJson", detailResultJson.toString());
		return json;
	}

	/**
	 * null转为空
	 *
	 * @param str
	 * @return
	 */
	private static String nullIsEmpty(String str) {
		if (str == null) {
			return "";
		} else {
			return str;
		}
	}
}
