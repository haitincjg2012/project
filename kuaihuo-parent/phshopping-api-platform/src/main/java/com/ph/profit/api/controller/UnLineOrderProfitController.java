package com.ph.profit.api.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ph.shopping.common.util.excel.ExcelBean;
import com.ph.shopping.common.util.excel.ExcelUtil;
import com.ph.shopping.common.util.page.PageBean;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.profit.dto.UnLineOrderProfitDTO;
import com.ph.shopping.facade.profit.service.IUnlineOrderProfitService;
import com.ph.shopping.facade.profit.vo.UnLineOrderProfitVO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @项目：phshopping-api-platform
 * 
 * @描述：线下订单分润
 * 
 * @作者： Mr.Dong
 * 
 * @创建时间：2017年5月22日
 * 
 * @Copyright @2017 by Mr.Dong
 */
@Controller
@RequestMapping("web/unLineOrderProfit")
public class UnLineOrderProfitController  {

	//线下订单分润接口
	@Reference(version = "1.0.0")
	private IUnlineOrderProfitService iUnlineOrderProfitService;


	/**
	 * 
	 * @Title: unLineOrderProfitListPage
	 * @Description: 跳转线下订单分润页面
	 * @author Mr.Dong
	 * @date 2017年5月22日 下午5:07:30
	 * @return
	 */
	@RequestMapping("unLineOrderProfitListPage")
	public String unLineOrderProfitListPage() {
		return "profit/profit_unline";
	}

	/**
	 * 
	 * @Title: unLineOrderProfitList
	 * @Description: 线下订单分润list
	 * @author 
	 * @date 2017年5月22日 下午4:16:44
	 * @param unLineOrderProfitDTO
	 * @param pageBean
	 * @return
	 */
	@RequestMapping("unLineOrderProfitList")
	@ResponseBody
	public Result unLineOrderProfitList(UnLineOrderProfitDTO unLineOrderProfitDTO,PageBean pageBean) {
		return iUnlineOrderProfitService.unLineOrderProfitList(unLineOrderProfitDTO,pageBean);
	}

	/**
	 * 
	 * @Title: getUnLineOrderProfitEXCEL
	 * @Description: 导出线下订单利润分成表EXCEL
	 * @author Mr.Dong
	 * @date 2017年5月22日 下午4:03:42
	 * @param request
	 * @param response
	 * @param unLineOrderProfitDTO
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getUnLineOrderProfitEXCEL", method = RequestMethod.GET)
	public Object getUnLineOrderProfitEXCEL(HttpServletRequest request,
			HttpServletResponse response,UnLineOrderProfitDTO unLineOrderProfitDTO) {
		Result unLineOrderProfitEXCEL = iUnlineOrderProfitService.getUnLineOrderProfitEXCEL(unLineOrderProfitDTO);
		List<UnLineOrderProfitVO> data = (List<UnLineOrderProfitVO>) unLineOrderProfitEXCEL
				.getData();
		List<Object[]> data2 = new ArrayList<Object[]>();
		for (UnLineOrderProfitVO profit : data) {
			Object[] standard = new Object[23];
			standard[0] = profit.getOrderNo();
			standard[1] = profit.getOrderMoney1();
			standard[2] = profit.getChargeFee1();
			standard[3] = profit.getPhChargeFee1();
			standard[4] = profit.getYstChargeFee1();
			standard[5] = profit.getCityAgentName();
			standard[6] = profit.getCityAgentProfit1();
			standard[7] = profit.getCountyAgentName();
			standard[8] = profit.getCountyAgentProfit1();
			standard[9] = profit.getTownAgentName();
			standard[10] = profit.getTownAgentProfit1();
			standard[11] = profit.getCityPromoterName();
			standard[12] = profit.getCityPromoterProfit1();
			standard[13] = profit.getCountyPromoterName();
			standard[14] = profit.getCountyPromoterProfit1();
			standard[15] = profit.getTownPromoterName();
			standard[16] = profit.getTownPromoterProfit1();
			standard[17] = profit.getMerchantPromoterName();
			standard[18] = profit.getMerchantPromoterProfit1();
			standard[19] = profit.getChargeProfitTotal1();
			standard[20] = profit.getChargeProfitRemain1();
			standard[21] = profit.getOrderTime();
			standard[22] = profit.getCreateTime();
			data2.add(standard);
		}
		ExcelBean excelbean = new ExcelBean();
		excelbean.setTableHeader(new String[] { "订单号", "订单金额", "管理费", "普惠管理费",
				"易商通管理费", "市代名称", "市级代理分润", "县代名称", "县级代理分润", "社区代名称", "社区代理分润", "市代推广师名称",
				"推广师推市级代理分润", "县代推广师名称", "推广师推县级代理分润", "社区代推广师名称", "推广师推社区代理分润", "商户推广师名称",
				"推广师推商户分润", "管理费支出合计", "管理费剩余","订单时间","分润时间"});
		excelbean.setSheetData(data2);
		excelbean.setName("线下订单分润表");
		try {
			ExcelUtil.download(request, response, excelbean);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


}
