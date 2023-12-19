package com.lundong.k3wise.util;

import com.lundong.k3wise.entity.*;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
	public static <T>String combinFormString(T p, String employeeId) {
		if (PurchaseRequisition.class.isAssignableFrom(p.getClass())) {
			// 采购申请单
			PurchaseRequisition temp = (PurchaseRequisition) p;
			String json = "[{\"id\":\"f1\",\"type\":\"input\",\"value\":\"申请人\"},{\"id\":\"f2\",\"type\":\"input\",\"value\":\"使用部门\"},{\"id\":\"f4\",\"type\":\"input\",\"value\":\"日期\"},{\"id\":\"f5\",\"type\":\"input\",\"value\":\"备注\"},{\"id\":\"f6\",\"type\":\"input\",\"value\":\"单据编号\"},{\"id\":\"detail1\",\"type\":\"fieldList\",\"ext\":[],\"value\":[detailJson]}]";

			// 构建明细
			StringBuilder detailResultJson = new StringBuilder();
			for (PurchaseRequisitionDetail detail : temp.getDetail()) {
				String detailJson = "[{\"id\":\"d2\",\"type\":\"input\",\"value\":\"物料代码\"},{\"id\":\"d3\",\"type\":\"input\",\"value\":\"物料名称\"},{\"id\":\"d4\",\"type\":\"input\",\"value\":\"规格型号\"},{\"id\":\"d5\",\"type\":\"input\",\"value\":\"辅助属性\"},{\"id\":\"d6\",\"type\":\"input\",\"value\":\"单位\"},{\"id\":\"d7\",\"type\":\"number\",\"value\":采购数量},{\"id\":\"d10\",\"type\":\"input\",\"value\":\"建议采购日期\"},{\"id\":\"d11\",\"type\":\"input\",\"value\":\"到货日期\"},{\"id\":\"d13\",\"type\":\"input\",\"value\":\"提前期\"},{\"id\":\"d14\",\"type\":\"input\",\"value\":\"用途\"},{\"id\":\"d15\",\"type\":\"input\",\"value\":\"源单单号\"},{\"id\":\"d16\",\"type\":\"input\",\"value\":\"请购人\"},{\"id\":\"d17\",\"type\":\"input\",\"value\":\"申请理由\"},{\"id\":\"d18\",\"type\":\"input\",\"value\":\"项目名称\"},{\"id\":\"d19\",\"type\":\"input\",\"value\":\"需求部门\"},{\"id\":\"d20\",\"type\":\"input\",\"value\":\"物料类别\"},{\"id\":\"d21\",\"type\":\"input\",\"value\":\"是否预算内\"},{\"id\":\"d22\",\"type\":\"input\",\"value\":\"预算一类\"},{\"id\":\"d23\",\"type\":\"input\",\"value\":\"预算二类\"},{\"id\":\"d24\",\"type\":\"input\",\"value\":\"在建工程编号\"}]";
				detailJson = detailJson
						.replace("物料代码", nullIsEmpty(detail.getItemId().getNumber()))
						.replace("物料名称", nullIsEmpty(detail.getItemId().getName()))
						.replace("规格型号", nullIsEmpty(detail.getItemModel()))
						.replace("辅助属性", nullIsEmpty(detail.getAuxPropId().getName()))
						.replace("单位", nullIsEmpty(detail.getUnitId().getName()))
						.replace("采购数量", nullIsZero(detail.getFauxqty()))
						.replace("建议采购日期", DateTimeFormatter.ofPattern("yyyy年MM月dd日").format(detail.getAPurchTime()))
						.replace("到货日期", DateTimeFormatter.ofPattern("yyyy年MM月dd日").format(detail.getFetchTime()))
//						.replace("BOM编号", nullIsEmpty(detail.getBomInterId().getName()))
						.replace("提前期", nullIsEmpty(detail.getLeadTime()))
						.replace("用途", nullIsEmpty(detail.getUse()))
						.replace("源单单号", nullIsEmpty(detail.getSourceBillNo()))
						.replace("请购人", nullIsEmpty(detail.getEntrySelfP0143().getName()))
						.replace("申请理由", nullIsEmpty(detail.getEntrySelfP0140()))
						.replace("项目名称", nullIsEmpty(detail.getEntrySelfP0135().getName()))
						.replace("需求部门", nullIsEmpty(detail.getEntrySelfP0136().getName()))
						.replace("物料类别", nullIsEmpty(detail.getEntrySelfP0137().getName()))
						.replace("是否预算内", nullIsEmpty(detail.getEntrySelfP0142().getName()))
						.replace("预算一类", nullIsEmpty(detail.getEntrySelfP0138().getName()))
						.replace("预算二类", nullIsEmpty(detail.getEntrySelfP0139().getName()))
						.replace("在建工程编号", nullIsEmpty(detail.getEntrySelfP0145().getName()));
				detailJson += ",";
				detailResultJson.append(detailJson);
			}
			if (temp.getDetail().size() > 0) {
				detailResultJson = new StringBuilder(detailResultJson.substring(0, detailResultJson.length() - 1));
			}
			json = json
					.replace("申请人", nullIsEmpty(temp.getRequesterId().getName()))
					.replace("使用部门", nullIsEmpty(temp.getDeptId().getName()))
					.replace("日期", DateTimeFormatter.ofPattern("yyyy年MM月dd日").format(temp.getDate()))
					.replace("备注", nullIsEmpty(temp.getNote()))
					.replace("单据编号", nullIsEmpty(temp.getBillNo()))
					.replace("detailJson", detailResultJson.toString());
			return json;
		} else if (PurchaseOrder.class.isAssignableFrom(p.getClass())) {
			// 采购订单
			PurchaseOrder temp = (PurchaseOrder) p;
			String json = "[{\"id\":\"f1\",\"type\":\"input\",\"value\":\"供应商代码\"},{\"id\":\"f2\",\"type\":\"input\",\"value\":\"供应商\"},{\"id\":\"f3\",\"type\":\"input\",\"value\":\"日期\"},{\"id\":\"f4\",\"type\":\"input\",\"value\":\"编号\"},{\"id\":\"f5\",\"type\":\"input\",\"value\":\"结算方式\"},{\"id\":\"f6\",\"type\":\"input\",\"value\":\"币别\"},{\"id\":\"f7\",\"type\":\"input\",\"value\":\"汇率\"},{\"id\":\"f8\",\"type\":\"input\",\"value\":\"部门测\"},{\"id\":\"f10\",\"type\":\"input\",\"value\":\"摘要\"},{\"id\":\"f11\",\"type\":\"input\",\"value\":\"项目汇总金额\"},{\"id\":\"f12\",\"type\":\"input\",\"value\":\"需求部门汇总金额\"},{\"id\":\"f13\",\"type\":\"input\",\"value\":\"在建工程汇总金额\"},{\"id\":\"f99\",\"type\":\"contact\",\"value\":[\"业务员字段\"]},{\"id\":\"detail1\",\"type\":\"fieldList\",\"ext\":[],\"value\":[detailJson]}]";
			// 构建明细
			StringBuilder detailResultJson = new StringBuilder();
			// 汇总三字段：项目名称/需求部门/在建工程编号
			List<PurchaseOrderDetail> purchaseOrderDetailList = temp.getDetail();
			for (PurchaseOrderDetail purchaseOrderDetailDetail : purchaseOrderDetailList) {
				purchaseOrderDetailDetail.setProjectName(purchaseOrderDetailDetail.getEntrySelfP0272().getName());
				purchaseOrderDetailDetail.setDemandDepartmentName(purchaseOrderDetailDetail.getEntrySelfP0271().getName());
				purchaseOrderDetailDetail.setConstructProjectNumberName(purchaseOrderDetailDetail.getEntrySelfP0283().getName());
			}
			Map<String, List<PurchaseOrderDetail>> projectNameMap = purchaseOrderDetailList.stream()
					.collect(Collectors.groupingBy(PurchaseOrderDetail::getProjectName));
			Map<String, List<PurchaseOrderDetail>> demandDepartmentNameMap = purchaseOrderDetailList.stream()
					.collect(Collectors.groupingBy(PurchaseOrderDetail::getDemandDepartmentName));
			Map<String, List<PurchaseOrderDetail>> constructProjectNumberNameMap = purchaseOrderDetailList.stream()
					.collect(Collectors.groupingBy(PurchaseOrderDetail::getConstructProjectNumberName));
			String projectName = summaryPurchaseOrder(projectNameMap);
			String demandDepartmentName = summaryPurchaseOrder(demandDepartmentNameMap);
			String constructProjectNumberName = summaryPurchaseOrder(constructProjectNumberNameMap);
			for (PurchaseOrderDetail detail : temp.getDetail()) {
				String detailJson = "[{\"id\":\"d2\",\"type\":\"input\",\"value\":\"物料代码\"},{\"id\":\"d3\",\"type\":\"input\",\"value\":\"物料名称\"},{\"id\":\"d4\",\"type\":\"input\",\"value\":\"规格型号\"},{\"id\":\"d5\",\"type\":\"input\",\"value\":\"辅助属性\"},{\"id\":\"d6\",\"type\":\"input\",\"value\":\"单位\"},{\"id\":\"d7\",\"type\":\"number\",\"value\":数量},{\"id\":\"d8\",\"type\":\"input\",\"value\":\"含税单价\"},{\"id\":\"d9\",\"type\":\"number\",\"value\":价税合计},{\"id\":\"d10\",\"type\":\"input\",\"value\":\"交货日期\"},{\"id\":\"d11\",\"type\":\"input\",\"value\":\"税率\"},{\"id\":\"d12\",\"type\":\"input\",\"value\":\"备注\"},{\"id\":\"d13\",\"type\":\"input\",\"value\":\"源单单号\"},{\"id\":\"d14\",\"type\":\"input\",\"value\":\"请购人\"},{\"id\":\"d15\",\"type\":\"input\",\"value\":\"申请理由\"},{\"id\":\"d16\",\"type\":\"input\",\"value\":\"项目名称\"},{\"id\":\"d17\",\"type\":\"input\",\"value\":\"需求部门\"},{\"id\":\"d18\",\"type\":\"input\",\"value\":\"物料类别\"},{\"id\":\"d19\",\"type\":\"input\",\"value\":\"是否预算内\"},{\"id\":\"d20\",\"type\":\"input\",\"value\":\"预算一类\"},{\"id\":\"d21\",\"type\":\"input\",\"value\":\"预算二类\"},{\"id\":\"d22\",\"type\":\"input\",\"value\":\"采购申请备注\"},{\"id\":\"d23\",\"type\":\"input\",\"value\":\"在建工程编号\"},{\"id\":\"d99\",\"type\":\"input\",\"value\":\"物料归属\"}]";
				detailJson = detailJson
						.replace("物料代码", nullIsEmpty(detail.getItemId().getNumber()))
						.replace("物料名称", nullIsEmpty(detail.getItemId().getName()))
						.replace("规格型号", nullIsEmpty(detail.getItemModel()))
						.replace("辅助属性", nullIsEmpty(detail.getAuxPropId().getName()))
						.replace("单位", nullIsEmpty(detail.getUnitId().getName()))
						.replace("数量", nullIsZero(detail.getFauxqty()))
						.replace("含税单价", nullIsEmpty(detail.getAuxTaxPrice()))
						.replace("价税合计", nullIsZero(detail.getAllAmount()))
						.replace("交货日期", DateTimeFormatter.ofPattern("yyyy年MM月dd日").format(detail.getDate()))
						.replace("税率", nullIsEmpty(detail.getCess()))
						.replace("备注", nullIsEmpty(detail.getNote()))
						.replace("源单单号", nullIsEmpty(detail.getSourceBillNo()))
						.replace("请购人", nullIsEmpty(detail.getEntrySelfP0278().getName()))
						.replace("申请理由", nullIsEmpty(detail.getEntrySelfP0277()))
						.replace("项目名称", nullIsEmpty(detail.getEntrySelfP0272().getName()))
						.replace("需求部门", nullIsEmpty(detail.getEntrySelfP0271().getName()))
						.replace("物料类别", nullIsEmpty(detail.getEntrySelfP0274().getName()))
						.replace("是否预算内", nullIsEmpty(detail.getEntrySelfP0280().getName()))
						.replace("物料归属", nullIsEmpty(detail.getEntrySelfP0273().getName()))
						.replace("预算一类", nullIsEmpty(detail.getEntrySelfP0275().getName()))
						.replace("预算二类", nullIsEmpty(detail.getEntrySelfP0276().getName()))
						.replace("采购申请备注", nullIsEmpty(detail.getEntrySelfP0279()))
						.replace("在建工程编号", nullIsEmpty(detail.getEntrySelfP0283().getName()));

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
					.replace("部门测", nullIsEmpty(temp.getDeptId().getName()))
					.replace("业务员字段", nullIsEmpty(employeeId))
					.replace("摘要", nullIsEmpty(temp.getExplanation()))
					.replace("detailJson", detailResultJson.toString())
					.replace("项目汇总金额", nullIsEmpty(projectName))
					.replace("需求部门汇总金额", nullIsEmpty(demandDepartmentName))
					.replace("在建工程汇总金额", nullIsEmpty(constructProjectNumberName));
			return json;
		} else if (PaymentRequest.class.isAssignableFrom(p.getClass())) {
			// 付款申请单
			PaymentRequest temp = (PaymentRequest) p;
			String json = "[{\"id\":\"f1\",\"type\":\"input\",\"value\":\"单据日期\"},{\"id\":\"f2\",\"type\":\"input\",\"value\":\"单据号\"},{\"id\":\"f3\",\"type\":\"input\",\"value\":\"付款类型\"},{\"id\":\"f4\",\"type\":\"input\",\"value\":\"核算项目类别\"},{\"id\":\"f5\",\"type\":\"input\",\"value\":\"核算项目名称\"},{\"id\":\"f6\",\"type\":\"input\",\"value\":\"币别\"},{\"id\":\"f7\",\"type\":\"input\",\"value\":\"金额文本\"},{\"id\":\"f8\",\"type\":\"input\",\"value\":\"核算项目开户银行\"},{\"id\":\"f9\",\"type\":\"input\",\"value\":\"核算项目银行账号\"},{\"id\":\"f10\",\"type\":\"input\",\"value\":\"核算项目户名\"},{\"id\":\"f11\",\"type\":\"input\",\"value\":\"付款理由\"},{\"id\":\"f12\",\"type\":\"input\",\"value\":\"备注\"},{\"id\":\"f13\",\"type\":\"input\",\"value\":\"项目汇总金额\"},{\"id\":\"f14\",\"type\":\"input\",\"value\":\"需求部门汇总金额\"},{\"id\":\"f15\",\"type\":\"input\",\"value\":\"在建工程汇总金额\"},{\"id\":\"f16\",\"type\":\"input\",\"value\":\"业务员\"},{\"id\":\"detail1\",\"type\":\"fieldList\",\"ext\":[],\"value\":[detailJson]}]";
			// 构建明细
			StringBuilder detailResultJson = new StringBuilder();
			// 汇总三字段：项目名称/需求部门/在建工程编号
			List<PaymentRequestDetail> paymentRequestDetailList = temp.getDetail();
			for (PaymentRequestDetail paymentRequestDetail : paymentRequestDetailList) {
				paymentRequestDetail.setProjectName(paymentRequestDetail.getBase4().getName());
				paymentRequestDetail.setDemandDepartmentName(paymentRequestDetail.getBase3().getName());
				paymentRequestDetail.setConstructProjectNumberName(paymentRequestDetail.getBase10().getName());
			}
			Map<String, List<PaymentRequestDetail>> projectNameMap = paymentRequestDetailList.stream()
					.collect(Collectors.groupingBy(PaymentRequestDetail::getProjectName));
			Map<String, List<PaymentRequestDetail>> demandDepartmentNameMap = paymentRequestDetailList.stream()
					.collect(Collectors.groupingBy(PaymentRequestDetail::getDemandDepartmentName));
			Map<String, List<PaymentRequestDetail>> constructProjectNumberNameMap = paymentRequestDetailList.stream()
					.collect(Collectors.groupingBy(PaymentRequestDetail::getConstructProjectNumberName));
			String projectName = summaryPaymentRequest(projectNameMap);
			String demandDepartmentName = summaryPaymentRequest(demandDepartmentNameMap);
			String constructProjectNumberName = summaryPaymentRequest(constructProjectNumberNameMap);

			for (PaymentRequestDetail detail : temp.getDetail()) {
				String detailJson = "[{\"id\":\"d2\",\"type\":\"input\",\"value\":\"源单类型\"},{\"id\":\"d3\",\"type\":\"input\",\"value\":\"源单单号\"},{\"id\":\"d4\",\"type\":\"input\",\"value\":\"合同号\"},{\"id\":\"d5\",\"type\":\"input\",\"value\":\"订单单号\"},{\"id\":\"d6\",\"type\":\"input\",\"value\":\"申请付款数量\"},{\"id\":\"d7\",\"type\":\"number\",\"value\":申请付款金额},{\"id\":\"d8\",\"type\":\"input\",\"value\":\"产品代码\"},{\"id\":\"d9\",\"type\":\"input\",\"value\":\"产品名称\"},{\"id\":\"d10\",\"type\":\"input\",\"value\":\"规格型号\"},{\"id\":\"d11\",\"type\":\"input\",\"value\":\"计量单位\"},{\"id\":\"d12\",\"type\":\"input\",\"value\":\"总数量\"},{\"id\":\"d13\",\"type\":\"input\",\"value\":\"含税单价\"},{\"id\":\"d14\",\"type\":\"number\",\"value\":选单单据金额},{\"id\":\"d15\",\"type\":\"input\",\"value\":\"费用项目代码\"},{\"id\":\"d16\",\"type\":\"input\",\"value\":\"费用项目名称\"},{\"id\":\"d17\",\"type\":\"input\",\"value\":\"请购人\"},{\"id\":\"d18\",\"type\":\"input\",\"value\":\"申请理由\"},{\"id\":\"d19\",\"type\":\"input\",\"value\":\"项目名称\"},{\"id\":\"d20\",\"type\":\"input\",\"value\":\"需求部门\"},{\"id\":\"d21\",\"type\":\"input\",\"value\":\"物料类别\"},{\"id\":\"d22\",\"type\":\"input\",\"value\":\"是否预算内\"},{\"id\":\"d23\",\"type\":\"input\",\"value\":\"预算一类\"},{\"id\":\"d24\",\"type\":\"input\",\"value\":\"预算二类\"},{\"id\":\"d25\",\"type\":\"input\",\"value\":\"采购申请备注\"},{\"id\":\"d26\",\"type\":\"input\",\"value\":\"在建工程编号\"}]";
				detailJson = detailJson
						.replace("源单类型", nullIsEmpty(detail.getClassIdSrc().getName()))
						.replace("源单单号", nullIsEmpty(detail.getBillNoSrc()))
						.replace("合同号", nullIsEmpty(detail.getContractNo()))
						.replace("订单单号", nullIsEmpty(detail.getOrderNo()))
						.replace("申请付款数量", nullIsEmpty(detail.getApplyQuantity()))
						.replace("申请付款金额", nullIsZero(detail.getApplyAmountFor()))
						.replace("产品代码", nullIsEmpty(detail.getItemId().getNumber()))
						.replace("产品名称", nullIsEmpty(detail.getItemId().getName()))
						.replace("规格型号", nullIsEmpty(detail.getBaseProperty1()))
						.replace("计量单位", nullIsEmpty(detail.getBase2().getName()))
						.replace("总数量", nullIsEmpty(detail.getQuantity()))
						.replace("含税单价", nullIsZero(detail.getAuxTaxPrice()))
						.replace("选单单据金额", nullIsZero(detail.getAmountForSrc()))
						.replace("费用项目代码", nullIsEmpty(detail.getFeeObjId().getNumber()))
						.replace("费用项目名称", nullIsEmpty(detail.getFeeObjId().getName()))
						.replace("请购人", nullIsEmpty(detail.getBase9().getName()))
						.replace("申请理由", nullIsEmpty(detail.getText2()))
						.replace("项目名称", nullIsEmpty(detail.getBase4().getName()))
						.replace("需求部门", nullIsEmpty(detail.getBase3().getName()))
						.replace("物料类别", nullIsEmpty(detail.getBase5().getName()))
						.replace("是否预算内", nullIsEmpty(detail.getBase8().getName()))
						.replace("预算一类", nullIsEmpty(detail.getBase6().getName()))
						.replace("预算二类", nullIsEmpty(detail.getBase7().getName()))
						.replace("采购申请备注", nullIsEmpty(detail.getText3()))
						.replace("在建工程编号", nullIsEmpty(detail.getBase10().getName()));

				detailJson += ",";
				detailResultJson.append(detailJson);
			}

			if (temp.getDetail().size() > 0) {
				detailResultJson = new StringBuilder(detailResultJson.substring(0, detailResultJson.length() - 1));
			}

			json = json
					.replace("单据日期", DateTimeFormatter.ofPattern("yyyy年MM月dd日").format(temp.getDate()))
					.replace("单据号", nullIsEmpty(temp.getNumber()))
					.replace("付款类型", nullIsEmpty(paymentTypeCovert(temp.getPayType())))
					.replace("核算项目类别", nullIsEmpty(temp.getItemClassId().getName()))
					.replace("核算项目名称", nullIsEmpty(temp.getCustomer().getName()))
					.replace("币别", nullIsEmpty(temp.getCurrencyId().getName()))
					.replace("金额文本", nullIsEmpty(temp.getAmountFor()))
					.replace("核算项目开户银行", nullIsEmpty(temp.getRpBank()))
					.replace("核算项目银行账号", nullIsEmpty(temp.getBankAcct()))
					.replace("核算项目户名", nullIsEmpty(temp.getBankAcctName()))
					.replace("付款理由", nullIsEmpty(temp.getExplanation()))
					.replace("备注", nullIsEmpty(temp.getText()))
					.replace("detailJson", detailResultJson.toString())
					.replace("项目汇总金额", nullIsEmpty(projectName))
					.replace("需求部门汇总金额", nullIsEmpty(demandDepartmentName))
					.replace("在建工程汇总金额", nullIsEmpty(constructProjectNumberName))
					.replace("业务员", nullIsEmpty(temp.getEmployee().getName()));
			return json;
		} else if (PurchaseContract.class.isAssignableFrom(p.getClass())) {
			// 采购合同/合同(应付)
			PurchaseContract temp = (PurchaseContract) p;
			String json = "[{\"id\":\"f1\",\"type\":\"input\",\"value\":\"合同种类\"},{\"id\":\"f2\",\"type\":\"input\",\"value\":\"合同日期\"},{\"id\":\"f3\",\"type\":\"input\",\"value\":\"合同名称\"},{\"id\":\"f4\",\"type\":\"input\",\"value\":\"合同号\"},{\"id\":\"f5\",\"type\":\"input\",\"value\":\"币别\"},{\"id\":\"f7\",\"type\":\"input\",\"value\":\"核算项目\"},{\"id\":\"f8\",\"type\":\"input\",\"value\":\"总金额测\"},{\"id\":\"f9\",\"type\":\"input\",\"value\":\"附注\"},{\"id\":\"f11\",\"type\":\"input\",\"value\":\"摘要\"},{\"id\":\"f12\",\"type\":\"input\",\"value\":\"项目汇总金额\"},{\"id\":\"f13\",\"type\":\"input\",\"value\":\"需求部门汇总金额\"},{\"id\":\"f14\",\"type\":\"input\",\"value\":\"在建工程汇总金额\"},{\"id\":\"f99\",\"type\":\"contact\",\"value\":[\"业务员字段\"]},{\"id\":\"detail1\",\"type\":\"fieldList\",\"ext\":[],\"value\":[detailJson]}]";
			// 构建明细
			StringBuilder detailResultJson = new StringBuilder();
			// 汇总三字段：项目名称/需求部门/在建工程编号
			List<PurchaseContractDetail> paymentRequestDetailList = temp.getDetail();
			for (PurchaseContractDetail paymentRequestDetail : paymentRequestDetailList) {
				paymentRequestDetail.setProjectName(paymentRequestDetail.getBase1().getName());
				paymentRequestDetail.setDemandDepartmentName(paymentRequestDetail.getBase().getName());
				paymentRequestDetail.setConstructProjectNumberName(paymentRequestDetail.getBase7().getName());
			}
			Map<String, List<PurchaseContractDetail>> projectNameMap = paymentRequestDetailList.stream()
					.collect(Collectors.groupingBy(PurchaseContractDetail::getProjectName));
			Map<String, List<PurchaseContractDetail>> demandDepartmentNameMap = paymentRequestDetailList.stream()
					.collect(Collectors.groupingBy(PurchaseContractDetail::getDemandDepartmentName));
			Map<String, List<PurchaseContractDetail>> constructProjectNumberNameMap = paymentRequestDetailList.stream()
					.collect(Collectors.groupingBy(PurchaseContractDetail::getConstructProjectNumberName));
			String projectName = summaryPurchaseContract(projectNameMap);
			String demandDepartmentName = summaryPurchaseContract(demandDepartmentNameMap);
			String constructProjectNumberName = summaryPurchaseContract(constructProjectNumberNameMap);
			for (PurchaseContractDetail detail : temp.getDetail()) {
				String detailJson = "[{\"id\":\"d2\",\"type\":\"input\",\"value\":\"产品代码\"},{\"id\":\"d3\",\"type\":\"input\",\"value\":\"产品名称\"},{\"id\":\"d4\",\"type\":\"input\",\"value\":\"规格型号\"},{\"id\":\"d5\",\"type\":\"input\",\"value\":\"辅助属性\"},{\"id\":\"d6\",\"type\":\"input\",\"value\":\"计量单位\"},{\"id\":\"d7\",\"type\":\"number\",\"value\":数量},{\"id\":\"d8\",\"type\":\"input\",\"value\":\"已含税单价\"},{\"id\":\"d9\",\"type\":\"input\",\"value\":\"不含税单价\"},{\"id\":\"d10\",\"type\":\"number\",\"value\":价税合计},{\"id\":\"d11\",\"type\":\"input\",\"value\":\"税率\"},{\"id\":\"d12\",\"type\":\"number\",\"value\":税额},{\"id\":\"d13\",\"type\":\"number\",\"value\":金额},{\"id\":\"d14\",\"type\":\"input\",\"value\":\"备注\"},{\"id\":\"d15\",\"type\":\"input\",\"value\":\"请购人\"},{\"id\":\"d16\",\"type\":\"input\",\"value\":\"申请理由\"},{\"id\":\"d17\",\"type\":\"input\",\"value\":\"项目名称\"},{\"id\":\"d18\",\"type\":\"input\",\"value\":\"需求部门\"},{\"id\":\"d19\",\"type\":\"input\",\"value\":\"物料类别\"},{\"id\":\"d20\",\"type\":\"input\",\"value\":\"是否预算内\"},{\"id\":\"d21\",\"type\":\"input\",\"value\":\"预算一类\"},{\"id\":\"d22\",\"type\":\"input\",\"value\":\"预算二类\"},{\"id\":\"d23\",\"type\":\"input\",\"value\":\"采购申请备注\"},{\"id\":\"d24\",\"type\":\"input\",\"value\":\"在建工程编号\"}]";
				detailJson = detailJson
						.replace("产品代码", nullIsEmpty(detail.getProductId2().getNumber()))
						.replace("产品名称", nullIsEmpty(detail.getItemId40828()))
						.replace("规格型号", nullIsEmpty(detail.getItemId40891()))
						.replace("辅助属性", nullIsEmpty(detail.getAuxPropId().getName()))
						.replace("计量单位", nullIsEmpty(detail.getUnitId().getName()))
						.replace("数量", nullIsZero(detail.getQuantity()))
						.replace("已含税单价", nullIsEmpty(detail.getTaxPriceFor()))
						.replace("不含税单价", nullIsEmpty(detail.getPriceFor()))
						.replace("价税合计", nullIsZero(detail.getAmountIncludeTaxFor()))
						.replace("税率", nullIsEmpty(detail.getTaxRate()))
						.replace("税额", nullIsZero(detail.getTaxFor()))
						.replace("金额", nullIsZero(detail.getAmountFor3()))
						.replace("备注", nullIsEmpty(detail.getExplanation()))
						.replace("请购人", nullIsEmpty(detail.getBase2().getName()))
						.replace("申请理由", nullIsEmpty(detail.getText1()))
						.replace("项目名称", nullIsEmpty(detail.getBase1().getName()))
						.replace("需求部门", nullIsEmpty(detail.getBase().getName()))
						.replace("物料类别", nullIsEmpty(detail.getBase3().getName()))
						.replace("是否预算内", nullIsEmpty(detail.getBase6().getName()))
						.replace("预算一类", nullIsEmpty(detail.getBase4().getName()))
						.replace("预算二类", nullIsEmpty(detail.getBase5().getName()))
						.replace("采购申请备注", nullIsEmpty(detail.getText3()))
						.replace("在建工程编号", nullIsEmpty(detail.getBase7().getName()));
				detailJson += ",";
				detailResultJson.append(detailJson);
			}

			if (temp.getDetail().size() > 0) {
				detailResultJson = new StringBuilder(detailResultJson.substring(0, detailResultJson.length() - 1));
			}

			json = json
					.replace("合同种类", nullIsEmpty(contractTypeCovert(temp.getContractTypeId())))
					.replace("合同日期", DateTimeFormatter.ofPattern("yyyy年MM月dd日").format(temp.getDate()))
					.replace("合同名称", nullIsEmpty(temp.getContractName()))
					.replace("合同号", nullIsEmpty(temp.getContractNo()))
					.replace("币别", nullIsEmpty(temp.getCurrencyId().getName()))
//					.replace("付款条件", nullIsEmpty(temp.getPayCondition().getName()))
					.replace("核算项目", nullIsEmpty(temp.getCustomer().getName()))
					.replace("总金额测", nullIsEmpty(temp.getTotalAmountFor()))
					.replace("附注", nullIsEmpty(temp.getText()))
					.replace("业务员字段", nullIsEmpty(employeeId))
					.replace("摘要", nullIsEmpty(temp.getExplanation()))
					.replace("detailJson", detailResultJson.toString())
					.replace("项目汇总金额", nullIsEmpty(projectName))
					.replace("需求部门汇总金额", nullIsEmpty(demandDepartmentName))
					.replace("在建工程汇总金额", nullIsEmpty(constructProjectNumberName));
			return json;
		} else if (OutsourcingOrder.class.isAssignableFrom(p.getClass())) {
			// 委外订单
			OutsourcingOrder temp = (OutsourcingOrder) p;
			String json = "[{\"id\":\"f1\",\"type\":\"input\",\"value\":\"供应商\"},{\"id\":\"f2\",\"type\":\"input\",\"value\":\"日期\"},{\"id\":\"f3\",\"type\":\"input\",\"value\":\"单据编号\"},{\"id\":\"f4\",\"type\":\"input\",\"value\":\"结算方式\"},{\"id\":\"f5\",\"type\":\"input\",\"value\":\"币别\"},{\"id\":\"f6\",\"type\":\"input\",\"value\":\"部门测\"},{\"id\":\"f8\",\"type\":\"input\",\"value\":\"汇率\"},{\"id\":\"f9\",\"type\":\"input\",\"value\":\"摘要\"},{\"id\":\"f10\",\"type\":\"input\",\"value\":\"项目汇总金额\"},{\"id\":\"f11\",\"type\":\"input\",\"value\":\"需求部门汇总金额\"},{\"id\":\"f12\",\"type\":\"input\",\"value\":\"在建工程汇总金额\"},{\"id\":\"f99\",\"type\":\"contact\",\"value\":[\"业务员字段\"]},{\"id\":\"detail1\",\"type\":\"fieldList\",\"ext\":[],\"value\":[detailJson]}]";
			// 构建明细
			StringBuilder detailResultJson = new StringBuilder();
			// 汇总三字段：项目名称/需求部门/在建工程编号
			List<OutsourcingOrderDetail> paymentRequestDetailList = temp.getDetail();
			for (OutsourcingOrderDetail paymentRequestDetail : paymentRequestDetailList) {
				paymentRequestDetail.setProjectName(paymentRequestDetail.getBase2().getName());
				paymentRequestDetail.setDemandDepartmentName(paymentRequestDetail.getBase1().getName());
				paymentRequestDetail.setConstructProjectNumberName(paymentRequestDetail.getBase9().getName());
			}
			Map<String, List<OutsourcingOrderDetail>> projectNameMap = paymentRequestDetailList.stream()
					.collect(Collectors.groupingBy(OutsourcingOrderDetail::getProjectName));
			Map<String, List<OutsourcingOrderDetail>> demandDepartmentNameMap = paymentRequestDetailList.stream()
					.collect(Collectors.groupingBy(OutsourcingOrderDetail::getDemandDepartmentName));
			Map<String, List<OutsourcingOrderDetail>> constructProjectNumberNameMap = paymentRequestDetailList.stream()
					.collect(Collectors.groupingBy(OutsourcingOrderDetail::getConstructProjectNumberName));
			String projectName = summaryOutsourcingOrder(projectNameMap);
			String demandDepartmentName = summaryOutsourcingOrder(demandDepartmentNameMap);
			String constructProjectNumberName = summaryOutsourcingOrder(constructProjectNumberNameMap);

			for (OutsourcingOrderDetail detail : temp.getDetail()) {
				String detailJson = "[{\"id\":\"d2\",\"type\":\"input\",\"value\":\"物料代码\"},{\"id\":\"d3\",\"type\":\"input\",\"value\":\"物料名称\"},{\"id\":\"d4\",\"type\":\"input\",\"value\":\"规格型号\"},{\"id\":\"d5\",\"type\":\"input\",\"value\":\"辅助属性\"},{\"id\":\"d6\",\"type\":\"input\",\"value\":\"单位\"},{\"id\":\"d7\",\"type\":\"number\",\"value\":总数量},{\"id\":\"d9\",\"type\":\"input\",\"value\":\"含税单价\"},{\"id\":\"d10\",\"type\":\"number\",\"value\":价税合计},{\"id\":\"d11\",\"type\":\"input\",\"value\":\"交货日期\"},{\"id\":\"d12\",\"type\":\"input\",\"value\":\"税率\"},{\"id\":\"d13\",\"type\":\"input\",\"value\":\"BOM编号\"},{\"id\":\"d14\",\"type\":\"input\",\"value\":\"源单单号\"},{\"id\":\"d15\",\"type\":\"input\",\"value\":\"备注\"},{\"id\":\"d16\",\"type\":\"input\",\"value\":\"请购人\"},{\"id\":\"d17\",\"type\":\"input\",\"value\":\"申请理由\"},{\"id\":\"d18\",\"type\":\"input\",\"value\":\"项目名称\"},{\"id\":\"d19\",\"type\":\"input\",\"value\":\"需求部门\"},{\"id\":\"d20\",\"type\":\"input\",\"value\":\"物料类别\"},{\"id\":\"d21\",\"type\":\"input\",\"value\":\"是否预算内\"},{\"id\":\"d22\",\"type\":\"input\",\"value\":\"预算一类\"},{\"id\":\"d23\",\"type\":\"input\",\"value\":\"预算二类\"},{\"id\":\"d24\",\"type\":\"input\",\"value\":\"采购申请备注\"},{\"id\":\"d25\",\"type\":\"input\",\"value\":\"在建工程编号\"},{\"id\":\"d26\",\"type\":\"input\",\"value\":\"建议发料日期\"},{\"id\":\"d99\",\"type\":\"input\",\"value\":\"物料归属\"}]";
				detailJson = detailJson
						.replace("物料代码", nullIsEmpty(detail.getItemId().getNumber()))
						.replace("物料名称", nullIsEmpty(detail.getItemName()))
						.replace("规格型号", nullIsEmpty(detail.getSpecification()))
						.replace("辅助属性", nullIsEmpty(detail.getAuxPropId().getName()))
						.replace("单位", nullIsEmpty(detail.getUnitId().getName()))
						.replace("总数量", nullIsZero(detail.getFauxqty()))
//						.replace("库存数量", nullIsZero(detail.getStockQtyOnlyForShow()))
						.replace("含税单价", nullIsEmpty(detail.getAuxTaxPrice()))
						.replace("价税合计", nullIsZero(detail.getAllAmount()))
						.replace("交货日期", DateTimeFormatter.ofPattern("yyyy年MM月dd日").format(detail.getFetchDate()))
						.replace("建议发料日期", DateTimeFormatter.ofPattern("yyyy年MM月dd日").format(detail.getPayShipDate()))
						.replace("税率", nullIsEmpty(detail.getTaxRate()))
						.replace("BOM编号", nullIsEmpty(detail.getBomInterId().getNumber()))
						.replace("源单单号", nullIsEmpty(detail.getBillNoSrc()))
						.replace("备注", nullIsEmpty(detail.getNote()))
						.replace("请购人", nullIsEmpty(detail.getBase7().getName()))
						.replace("申请理由", nullIsEmpty(detail.getText()))
						.replace("项目名称", nullIsEmpty(detail.getBase2().getName()))
						.replace("需求部门", nullIsEmpty(detail.getBase1().getName()))
						.replace("物料类别", nullIsEmpty(detail.getBase8().getName()))
						.replace("是否预算内", nullIsEmpty(detail.getBase6().getName()))
						.replace("物料归属", nullIsEmpty(detail.getBase3().getName()))
						.replace("预算一类", nullIsEmpty(detail.getBase4().getName()))
						.replace("预算二类", nullIsEmpty(detail.getBase5().getName()))
						.replace("采购申请备注", nullIsEmpty(detail.getText2()))
						.replace("在建工程编号", nullIsEmpty(detail.getBase9().getName()));
				detailJson += ",";
				detailResultJson.append(detailJson);
			}

			if (temp.getDetail().size() > 0) {
				detailResultJson = new StringBuilder(detailResultJson.substring(0, detailResultJson.length() - 1));
			}

			json = json
					.replace("供应商", nullIsEmpty(temp.getSupplyId().getName()))
					.replace("日期", DateTimeFormatter.ofPattern("yyyy年MM月dd日").format(temp.getDate()))
					.replace("单据编号", nullIsEmpty(temp.getBillNo()))
					.replace("结算方式", nullIsEmpty(temp.getSettleStyle().getName()))
					.replace("币别", nullIsEmpty(temp.getCurrencyId().getName()))
					.replace("部门测", nullIsEmpty(temp.getDepartment().getName()))
					.replace("业务员字段", nullIsEmpty(employeeId))
					.replace("汇率", nullIsEmpty(temp.getExchangeRate()))
					.replace("摘要", nullIsEmpty(temp.getSummary()))
					.replace("detailJson", detailResultJson.toString())
					.replace("项目汇总金额", nullIsEmpty(projectName))
					.replace("需求部门汇总金额", nullIsEmpty(demandDepartmentName))
					.replace("在建工程汇总金额", nullIsEmpty(constructProjectNumberName));
			return json;
		}
		return null;
	}

	/**
	 * 转换合同种类
	 *
	 * @param contractTypeId
	 * @return
	 */
	private static String contractTypeCovert(Integer contractTypeId) {
		switch (contractTypeId) {
			case 1:
				return "销售合同";
			case 2:
				return "采购合同";
			default:
				return "";
		}
	}

	/**
	 * 转换 付款申请单—付款类型
	 *
	 * @param paymentTypeId
	 * @return
	 */
	private static String paymentTypeCovert(Integer paymentTypeId) {
		switch (paymentTypeId) {
			case 1:
				return "购货款";
			case 2:
				return "预付款";
			case 6:
				return "其他付款";
			default:
				return "";
		}
	}

	/**
	 * null转为空（String）
	 *
	 * @param str
	 * @return
	 */
	public static String nullIsEmpty(String str) {
		if (str == null) {
			return " ";
		} else {
			if (str.isEmpty()) {
				return " ";
			} else {
				if (str.endsWith("\r\n")) {
					str = str.substring(0, str.length() - 2);
				}
				return escapeHtml(str);
			}
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
	 * null转为空（Integer）
	 *
	 * @param d
	 * @return
	 */
	private static CharSequence nullIsEmpty(Integer d) {
		if (d == null) {
			return "";
		} else {
			return String.valueOf(d);
		}
	}

	/**
	 * null转为0（Double）
	 *
	 * @param d
	 * @return
	 */
	public static String nullIsZero(Double d) {
		if (d == null) {
			return "0";
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

	/**
	 * 付款申请单汇总
	 *
	 * @param projectNameMap
	 * @return
	 */
	public static String summaryPaymentRequest(Map<String, List<PaymentRequestDetail>> projectNameMap) {
		String projectName = "";
		if (projectNameMap == null || projectNameMap.keySet().isEmpty()) {
			return "";
		}
		for (String s : projectNameMap.keySet()) {
			if (s == null || s.equals("")) {
				continue;
			}
			String tempText = "";
			List<PaymentRequestDetail> paymentRequestDetails = projectNameMap.get(s);
			double value = paymentRequestDetails.stream()
					.mapToDouble(PaymentRequestDetail::getApplyAmountFor)
					.sum();
			tempText = s + ":" + value + " | ";
			projectName += tempText;
		}
		if (projectName.endsWith(" | ")) {
			projectName = projectName.substring(0, projectName.length() - 3);
		}
		return projectName;
	}

	/**
	 * 采购订单汇总
	 *
	 * @param projectNameMap
	 * @return
	 */
	public static String summaryPurchaseOrder(Map<String, List<PurchaseOrderDetail>> projectNameMap) {
		String projectName = "";
		if (projectNameMap == null || projectNameMap.keySet().isEmpty()) {
			return "";
		}
		for (String s : projectNameMap.keySet()) {
			if (s == null || s.equals("")) {
				continue;
			}
			String tempText = "";
			List<PurchaseOrderDetail> paymentRequestDetails = projectNameMap.get(s);
			double value = paymentRequestDetails.stream()
					.mapToDouble(PurchaseOrderDetail::getAllAmount)
					.sum();
			tempText = s + ":" + value + " | ";
			projectName += tempText;
		}
		if (projectName.endsWith(" | ")) {
			projectName = projectName.substring(0, projectName.length() - 3);
		}
		return projectName;
	}

	/**
	 * 采购合同汇总
	 *
	 * @param projectNameMap
	 * @return
	 */
	public static String summaryPurchaseContract(Map<String, List<PurchaseContractDetail>> projectNameMap) {
		String projectName = "";
		if (projectNameMap == null || projectNameMap.keySet().isEmpty()) {
			return "";
		}
		for (String s : projectNameMap.keySet()) {
			if (s == null || s.equals("")) {
				continue;
			}
			String tempText = "";
			List<PurchaseContractDetail> purchaseContractDetails = projectNameMap.get(s);
			double value = purchaseContractDetails.stream()
					.mapToDouble(PurchaseContractDetail::getAmountIncludeTaxFor)
					.sum();
			tempText = s + ":" + value + " | ";
			projectName += tempText;
		}
		if (projectName.endsWith(" | ")) {
			projectName = projectName.substring(0, projectName.length() - 3);
		}
		return projectName;
	}

	/**
	 * 委外订单汇总
	 *
	 * @param projectNameMap
	 * @return
	 */
	public static String summaryOutsourcingOrder(Map<String, List<OutsourcingOrderDetail>> projectNameMap) {
		String projectName = "";
		if (projectNameMap == null || projectNameMap.keySet().isEmpty()) {
			return "";
		}
		for (String s : projectNameMap.keySet()) {
			if (s == null || s.equals("")) {
				continue;
			}
			String tempText = "";
			List<OutsourcingOrderDetail> outsourcingOrderDetails = projectNameMap.get(s);
			double value = outsourcingOrderDetails.stream()
					.mapToDouble(OutsourcingOrderDetail::getAllAmount)
					.sum();
			tempText = s + ":" + value + " | ";
			projectName += tempText;
		}
		if (projectName.endsWith(" | ")) {
			projectName = projectName.substring(0, projectName.length() - 3);
		}
		return projectName;
	}

	/**
	 * 去重特殊字符
	 *
	 * @param json
	 * @return
	 */
	public static String clearSpecialCharacter(String json) {
		if (!StringUtil.isEmpty(json)) {
			json = json.replaceAll("\\\\", "\\\\\\\\");
		}
		return json;
	}

	public static String escapeHtml(String input) {
		if (input == null) {
			return null;
		}

		StringBuilder escaped = new StringBuilder();
		for (char c : input.toCharArray()) {
			switch (c) {
				case '"':
					escaped.append("\\\"");
					break;
				case '\\':
					escaped.append("\\\\");
					break;
				case '	':
					escaped.append("    ");
					break;
				case '\r':
                case '\n':
                    break;
                default:
					escaped.append(c);
			}
		}
		return escaped.toString();
	}
}
