package com.ph.profit.api.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ph.shopping.common.util.excel.ExcelBean;
import com.ph.shopping.common.util.excel.ExcelUtil;
import com.ph.shopping.common.util.page.PageBean;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.profit.dto.PurchaseOrderProfitDTO;
import com.ph.shopping.facade.profit.service.IPurchaseOrderProfitService;
import com.ph.shopping.facade.profit.vo.PurchaseOrderProfitVO;

/**
 * @项目：phshopping-api-platform
 * 
 * @描述：供应链订单分润
 * 
 * @作者： Mr.Dong
 * 
 * @创建时间：2017年5月16日
 * 
 * @Copyright @2017 by Mr.Dong
 */
@Controller
@RequestMapping("web/purchaseOrderProfit")
public class PurchaseOrderProfitController {
	
	@Reference(version = "1.0.0")
	private IPurchaseOrderProfitService iPurchaseOrderProfitService;
	
	/**
	 * 
	 * @Title: supplyChainProfitPage
	 * @Description: 跳转供应链订单利润分成列表页面
	 * @author Mr.Dong
	 * @date 2017年4月12日 下午4:03:42
	 * @return
	 */
	@RequestMapping(value = "/purchaseOrderProfitPage", method = RequestMethod.GET)
	public String purchaseOrderProfitPage() {
		return "profit/profit_supply";
	}
	
	/**
	 * 
	 * @Title: getPurchaseOrderProfitList
	 * @Description: 获取供应链订单利润分成列表(分页)
	 * @author Mr.Dong
	 * @date 2017年4月12日 下午4:03:42
	 * @param unlineSupplyChainProfit
	 * @param pagebean
	 * @return
	 */
	@RequestMapping(value = "/purchaseOrderProfitList", method = RequestMethod.POST)
	@ResponseBody
	public Object getPurchaseOrderProfitList(
			@ModelAttribute PurchaseOrderProfitDTO purchaseOrderProfitDTO, PageBean pagebean) {
		return iPurchaseOrderProfitService.getPurchaseOrderProfitList(purchaseOrderProfitDTO,
				pagebean);
	}

	/**
	 * 
	 * @Title: getPurchaseOrderProfitEXCEL
	 * @Description: 导出供应链订单供应链利润分成表EXCEL
	 * @author Mr.Dong
	 * @date 2017年4月12日 下午4:03:42
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getPurchaseOrderProfitEXCEL", method = RequestMethod.GET)
	public Object getPurchaseOrderProfitEXCEL(HttpServletRequest request,
			HttpServletResponse response,PurchaseOrderProfitDTO purchaseOrderProfitDTO) {
		Result supplyChainProfitList = iPurchaseOrderProfitService.getPurchaseOrderProfitEXCEL(purchaseOrderProfitDTO);
		List<PurchaseOrderProfitVO> data = (List<PurchaseOrderProfitVO>) supplyChainProfitList
				.getData();
		List<Object[]> data2 = new ArrayList<Object[]>();
		for (PurchaseOrderProfitVO profit : data) {
			Object[] standard = new Object[19];
			standard[0] = profit.getOrderNo();
			standard[1] = profit.getRetailPrice1();
			standard[2] = profit.getSettlementPrice1();
			standard[3] = profit.getPurchasePrice1();
			standard[4] = profit.getLogisticsFee1();
			standard[5] = profit.getChainProfit1();
			standard[6] = profit.getPhIncome1();
			standard[7] = profit.getYstIncome1();
			standard[8] = profit.getCityAgentName();
			standard[9] = profit.getCityAgentProfit1();
			standard[10] = profit.getCountyAgentName();
			standard[11] = profit.getCountyAgentProfit1();
			standard[12] = profit.getTownAgentName();
			standard[13] = profit.getTownAgentProfit1();
			standard[14] = profit.getChainTotal();
			standard[15] = profit.getChainRemain();
			standard[16] = profit.getYstRemain();
			standard[17] = profit.getPhRemain();
			standard[18] = profit.getOrderTime();
			data2.add(standard);
		}
		ExcelBean excelbean = new ExcelBean();
		excelbean.setTableHeader(new String[] { "订单号","零售价", "结算成本", "门店进货价格", "物流费用", "供应链利润",
				"普惠供应链收入", "易商通供应链收入", "市代名称", "市级代理分润", "县代名称", "县级代理分润", "社区代名称", "社区代理分润",
				"供应链支出合计", "供应链剩余", "易商通总余", "普惠总余","订单时间"});
		excelbean.setSheetData(data2);
		excelbean.setName("供应链订单分润表");
		try {
			ExcelUtil.download(request, response, excelbean);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
