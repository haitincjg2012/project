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
import com.ph.base.BaseController;
import com.ph.shopping.common.util.excel.ExcelBean;
import com.ph.shopping.common.util.excel.ExcelUtil;
import com.ph.shopping.common.util.page.PageBean;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.profit.dto.OnlineOrderProfitDTO;
import com.ph.shopping.facade.profit.service.IOnlineOrderProfitService;
import com.ph.shopping.facade.profit.vo.OnlineOrderProfitVO;

/**
 * @项目：phshopping-api-platform
 * 
 * @描述：线上订单分润
 * 
 * @作者：Mr.Dong
 * 
 * @创建时间：2017年5月9日
 * 
 * @Copyright @2017 by  Mr.Dong
 */
@Controller
@RequestMapping("web/onLineOrderProfit")
public class OnLineOrderProfitController extends BaseController {

	@Reference(version = "1.0.0")
	private IOnlineOrderProfitService iOnlineOrderProfitService;
	
	
	/**
	 * 
	 * @Title: onLineOrderProfitPage
	 * @Description: 跳转线上订单分润list
	 * @author Mr.Dong
	 * @date 2017年5月9日 下午4:03:42
	 * @return
	 */
	@RequestMapping(value = "/onLineOrderProfitPage", method = RequestMethod.GET)
	public String onLineOrderProfitPage() {
		return "profit/profit_online";
	}
	
	/**
	 * 
	 * @Title: getOnLineOrderProfitList
	 * @Description: 线上订单分润list
	 * @author Mr.Dong
	 * @date 2017年5月9日 下午4:03:42
	 * @param unlineSupplyChainProfit
	 * @param pagebean
	 * @return
	 */
	@RequestMapping(value = "/onLineOrderProfitList", method = RequestMethod.POST)
	@ResponseBody
	public Object getOnLineOrderProfitList(
			@ModelAttribute OnlineOrderProfitDTO onlineOrderProfitDTO, PageBean pagebean) {
		return iOnlineOrderProfitService.getOnLineOrderProfitList(onlineOrderProfitDTO,
				pagebean);
	}
	
	/**
	 * 
	* @Title: getOnLineOrderProfitEXCEL
	* @Description: TODO(线上订单导出Excel)
	* @author Mr.Dong
	* @date  2017年5月31日 上午11:07:05
	* @param request
	* @param response
	* @param onlineOrderProfitDTO
	* @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getOnLineOrderProfitEXCEL", method = RequestMethod.GET)
	public Object getOnLineOrderProfitEXCEL(HttpServletRequest request,
			HttpServletResponse response,OnlineOrderProfitDTO onlineOrderProfitDTO) {
		 Result onLineOrderProfitEXCEL = iOnlineOrderProfitService.getOnLineOrderProfitEXCEL(onlineOrderProfitDTO);
		 List<OnlineOrderProfitVO> data = (List<OnlineOrderProfitVO>) onLineOrderProfitEXCEL
				.getData();
		List<Object[]> data2 = new ArrayList<Object[]>();
		for (OnlineOrderProfitVO profit : data) {
			Object[] standard = new Object[36];
			standard[0] = profit.getOrderNo();
			standard[1] = profit.getProductMoney1();
			standard[2] = profit.getSettlementPrice1();
			standard[3] = profit.getFreight1();
			standard[4] = profit.getChargeFee1();
			standard[5] = profit.getPhChargeFee1();
			standard[6] = profit.getYstChargeFee1();
			standard[7] = profit.getCityAgentName();
			standard[8] = profit.getCityAgentProfit1();
			standard[9] = profit.getCityAgentChainProfit1();
			standard[10] = profit.getCountyAgentName();
			standard[11] = profit.getCountyAgentProfit1();
			standard[12] = profit.getCountyAgentChainProfit1();
			standard[13] = profit.getTownAgentName();
			standard[14] = profit.getTownAgentProfit1();
			standard[15] = profit.getTownAgentChainProfit1();//社区代理供应链分润
			standard[16] = profit.getCityPromoterName();
			standard[17] = profit.getCityPromoterProfit1();
			standard[18] = profit.getCountyPromoterName();
			standard[19] = profit.getCountyPromoterProfit1();
			standard[20] = profit.getTownPromoterName();
			standard[21] = profit.getTownPromoterProfit1();
			standard[22] = profit.getMerchantPromoterName();
			standard[23] = profit.getMerchantPromoterProfit1();
			standard[24] = profit.getMerchantPromoterName();
			standard[25] = profit.getMerchantPromoterProfit1();
			standard[26] = profit.getChargeProfitTotal1();
			standard[27] = profit.getChargeProfitRemain1();
			standard[28] = profit.getMerchantChainProfit1();
			standard[29] = profit.getChainProfit1();
			standard[30] = profit.getPhIncome1();
			standard[31] = profit.getYstIncome1();
			standard[32] = profit.getChainTotal1();
			standard[33] = profit.getChainRemain1();
			standard[34] = profit.getOrderTime();
			standard[35] = profit.getCreateTime();
			data2.add(standard);
		}
		ExcelBean excelbean = new ExcelBean();
		excelbean.setTableHeader(new String[] { "订单号", "商品金额","结算成本", "物流费用","管理费", "普惠管理费",
				"易商通管理费", "市代名称", "市级代理分润","市代供应链分润" ,"县代名称", "县级代理分润","县代供应链分润","社区代名称", 
				"社区代理分润","社代供应链分润","市代推广师名称",
				"推广师推市级代理分润", "县代推广师名称", "推广师推县级代理分润", "社区代推广师名称", "推广师推社区代理分润", "商户推广师名称",
				"推广师推商户分润", "管理费支出合计", "管理费剩余",
				
				"门店费用","供应链利润","普惠供应链收入","易商通供应链收入","供应链支出合计","供应链剩余",
				"易商通总余","普惠总余",
				
				"订单时间","分润时间"});
		excelbean.setSheetData(data2);
		excelbean.setName("线上订单分润表");
		try {
			ExcelUtil.download(request, response, excelbean);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
}
