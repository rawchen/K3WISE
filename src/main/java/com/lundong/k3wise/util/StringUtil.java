package com.lundong.k3wise.util;

import com.lundong.k3wise.entity.PurchaseOrder;
import com.lundong.k3wise.entity.PurchaseOrderDetail;
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
	 * @param p
	 * @return
	 */
	public static <T>String combinFormString(T p) {
		if (PurchaseRequisition.class.isAssignableFrom(p.getClass())) {
			// 采购申请单
			PurchaseRequisition temp = (PurchaseRequisition) p;
			String json = "[{\"id\":\"f1\",\"type\":\"input\",\"value\":\"单据号\"},{\"id\":\"f2\",\"type\":\"input\",\"value\":\"项目名称\"},{\"id\":\"f3\",\"type\":\"input\",\"value\":\"申请日期\"},{\"id\":\"f4\",\"type\":\"input\",\"value\":\"申请人\"},{\"id\":\"f5\",\"type\":\"input\",\"value\":\"使用部门\"},{\"id\":\"f6\",\"type\":\"input\",\"value\":\"备注\"},{\"id\":\"detail1\",\"type\":\"fieldList\",\"ext\":[],\"value\":[detailJson]}]";

			// 构建明细
			StringBuilder detailResultJson = new StringBuilder();
			for (PurchaseRequisitionDetail detail : temp.getDetail()) {
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

			if (temp.getDetail().size() > 0) {
				detailResultJson = new StringBuilder(detailResultJson.substring(0, detailResultJson.length() - 1));
			}

			json = json.replace("单据号", nullIsEmpty(temp.getBillNo()))
					.replace("项目名称", nullIsEmpty(temp.getItemName()))
					.replace("申请日期", DateTimeFormatter.ofPattern("yyyy年MM月dd日").format(temp.getDate()))
					.replace("申请人", nullIsEmpty(temp.getRequesterId().getName()))
					.replace("使用部门", nullIsEmpty(temp.getDeptId().getName()))
					.replace("备注", nullIsEmpty(temp.getNote()))
					.replace("detailJson", detailResultJson.toString());
			return json;
		} else if (PurchaseOrder.class.isAssignableFrom(p.getClass())) {
			// 采购订单
			PurchaseOrder temp = (PurchaseOrder) p;
			String json = "[{\"id\":\"f1\",\"type\":\"input\",\"value\":\"供应商代码\"},{\"id\":\"f2\",\"type\":\"input\",\"value\":\"供应商\"},{\"id\":\"f3\",\"type\":\"input\",\"value\":\"日期\"},{\"id\":\"f4\",\"type\":\"input\",\"value\":\"编号\"},{\"id\":\"f5\",\"type\":\"input\",\"value\":\"结算方式\"},{\"id\":\"f6\",\"type\":\"input\",\"value\":\"币别\"},{\"id\":\"f7\",\"type\":\"input\",\"value\":\"汇率\"},{\"id\":\"f8\",\"type\":\"input\",\"value\":\"部门\"},{\"id\":\"f9\",\"type\":\"input\",\"value\":\"业务员\"},{\"id\":\"detail1\",\"type\":\"fieldList\",\"ext\":[],\"value\":[detailJson]}]";
			// 构建明细
			StringBuilder detailResultJson = new StringBuilder();
			for (PurchaseOrderDetail detail : temp.getDetail()) {
				String detailJson = "[{\"id\":\"d1\",\"type\":\"input\",\"value\":\"物料代码\"},{\"id\":\"d2\",\"type\":\"input\",\"value\":\"物料名称\"},{\"id\":\"d3\",\"type\":\"input\",\"value\":\"规格型号\"},{\"id\":\"d4\",\"type\":\"input\",\"value\":\"辅助属性\"},{\"id\":\"d5\",\"type\":\"input\",\"value\":\"单位\"},{\"id\":\"d6\",\"type\":\"input\",\"value\":\"数量\"},{\"id\":\"d7\",\"type\":\"input\",\"value\":\"含税单价\"},{\"id\":\"d8\",\"type\":\"input\",\"value\":\"价税合计\"},{\"id\":\"d9\",\"type\":\"input\",\"value\":\"交货日期\"},{\"id\":\"d10\",\"type\":\"input\",\"value\":\"税率\"},{\"id\":\"d11\",\"type\":\"input\",\"value\":\"备注\"},{\"id\":\"d12\",\"type\":\"input\",\"value\":\"源单单号\"}]";
				detailJson = detailJson
						.replace("物料代码", nullIsEmpty(detail.getItemId().getNumber()))
						.replace("物料名称", nullIsEmpty(detail.getItemId().getName()))
						.replace("规格型号", nullIsEmpty(detail.getItemModel()))
						.replace("辅助属性", nullIsEmpty(detail.getAuxPropId().getName()))
						.replace("单位", nullIsEmpty(detail.getUnitId().getName()))
						.replace("数量", String.valueOf(detail.getFauxqty()))
						.replace("含税单价", nullIsEmpty(detail.getAuxTaxPrice()))
						.replace("价税合计", nullIsEmpty(detail.getAllAmount()))
						.replace("交货日期", DateTimeFormatter.ofPattern("yyyy年MM月dd日").format(detail.getDate()))
						.replace("税率", nullIsEmpty(detail.getCess()))
						.replace("备注", nullIsEmpty(detail.getNote()))
						.replace("源单单号", nullIsEmpty(detail.getSourceBillNo()));

				detailJson += ",";
				detailResultJson.append(detailJson);
			}

			if (temp.getDetail().size() > 0) {
				detailResultJson = new StringBuilder(detailResultJson.substring(0, detailResultJson.length() - 1));
			}

			json = json
					.replace("供应商代码", nullIsEmpty(temp.getSupplyId().getNumber()))
					.replace("供应商", nullIsEmpty(temp.getSupplyId().getName()))
					.replace("日期", DateTimeFormatter.ofPattern("yyyy年MM月dd日").format(temp.getDate()))
					.replace("编号", nullIsEmpty(temp.getBillNo()))
					.replace("结算方式", nullIsEmpty(temp.getSettleId().getName()))
					.replace("币别", nullIsEmpty(temp.getCurrencyId().getName()))
					.replace("汇率", nullIsEmpty(temp.getExchangeRate().toString()))
					.replace("部门", nullIsEmpty(temp.getDeptId().getName()))
					.replace("业务员", nullIsEmpty(temp.getEmpId().getName()))
					.replace("detailJson", detailResultJson.toString());
			return json;
		} else if (PurchaseRequisition.class.isAssignableFrom(p.getClass())) {

		} else if (PurchaseRequisition.class.isAssignableFrom(p.getClass())) {

		} else if (PurchaseRequisition.class.isAssignableFrom(p.getClass())) {

		}
		return null;
	}

	/**
	 * null转为空（String）
	 *
	 * @param str
	 * @return
	 */
	public static String nullIsEmpty(String str) {
		if (str == null) {
			return "";
		} else {
			return str;
		}
	}

	/**
	 * null转为空（Double）
	 *
	 * @param d
	 * @return
	 */
	public static String nullIsEmpty(Double d) {
		if (d == null) {
			return "";
		} else {
			return String.valueOf(d);
		}
	}

	/**
	 * 是否为空
	 *
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		return str == null || "".equals(str);
	}
}
