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
import com.ph.shopping.facade.profit.dto.PromoterProfitRecordDTO;
import com.ph.shopping.facade.profit.dto.PromoterProfitTotalDTO;
import com.ph.shopping.facade.profit.dto.SupplyProfitTotalDTO;
import com.ph.shopping.facade.profit.dto.PurchaseOrderProfitDTO;
import com.ph.shopping.facade.profit.dto.UnLineOrderProfitDTO;
import com.ph.shopping.facade.profit.service.IOnlineOrderProfitService;
import com.ph.shopping.facade.profit.service.IOrderProfitTotalService;
import com.ph.shopping.facade.profit.vo.OnlineOrderProfitVO;
import com.ph.shopping.facade.profit.vo.PromoterProfitTotalVO;
import com.ph.shopping.facade.profit.vo.PurchaseOrderProfitVO;
import com.ph.shopping.facade.profit.vo.SupplyProfitTotalVO;
import com.ph.shopping.facade.profit.vo.UnLineOrderProfitVO;
/**
 * 
* @ClassName: OrderProfitTotalController
* @Description: TODO(这里用一句话描述这个类的作用)
* @author Mr.Dong
* @date 2017年6月22日 下午9:58:03
 */
@Controller
@RequestMapping("web/orderProfitTotal")
public class OrderProfitTotalController extends BaseController {
	
	@Reference(version = "1.0.0")
	private  IOrderProfitTotalService iOrderProfitTotalService;
	
	/**
	 * 
	* @Title: purchaseProfitTotalPage
	* @Description: TODO(跳转供应链分润总表)
	* @author Mr.Dong
	* @date  2017年6月5日 上午11:47:57
	* @return
	 */
	@RequestMapping(value="supplyProfitTotalPage",method=RequestMethod.GET)
	public String supplyProfitTotalPage(){
		return "profit/profit_total_supply_chain";
	}
	
	
	/**
	* @Title: getSupplyProfitTotalList
	* @Description: TODO(供应链分润总表list)
	* @author Mr.Dong
	* @date  2017年6月5日 下午2:21:12
	* @param orderProfitTotalDTO
	* @param pagebean
	* @return
	 */
	@RequestMapping(value="getSupplyProfitTotalList",method=RequestMethod.POST)
	@ResponseBody
	public Result getSupplyProfitTotalList(SupplyProfitTotalDTO orderProfitTotalDTO, PageBean pagebean){
		return iOrderProfitTotalService.getSupplyProfitTotalList(orderProfitTotalDTO, pagebean);
	}
	
	/**
	 * 
	 * @Title: getSupplyProfitTotalEXCEL
	 * @Description: 导出供应链订单供应链利润分成表EXCEL
	 * @author Mr.Dong
	 * @date 2017年4月12日 下午4:03:42
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getSupplyProfitTotalEXCEL", method = RequestMethod.GET)
	public Object getSupplyProfitTotalEXCEL(HttpServletRequest request,
			HttpServletResponse response,SupplyProfitTotalDTO orderProfitTotalDT) {
		Result supplyProfitTotalEXCEL = iOrderProfitTotalService.getSupplyProfitTotalEXCEL(orderProfitTotalDT);
		List<SupplyProfitTotalVO> data = (List<SupplyProfitTotalVO>) supplyProfitTotalEXCEL
				.getData();
		List<Object[]> data2 = new ArrayList<Object[]>();
		
		for (SupplyProfitTotalVO profit : data) {
			Object[] standard = new Object[4];
			standard[0] = profit.getTelPhone();
			if(profit.getUserType() == 3){
				standard[1] = "市级代理";
			}else if(profit.getUserType() == 4){
				standard[1] = "县级代理";
			}else if(profit.getUserType() == 5){
				standard[1] = "社区代理";
			}else if(profit.getUserType() == 6){
				standard[1] = "商户";
			}
			standard[2] = profit.getUserName();
			standard[3] = profit.getProfited1();
			data2.add(standard);
		}
		ExcelBean excelbean = new ExcelBean();
		excelbean.setTableHeader(new String[] { "供应链账号","供应链类型", "供应链企业", "已分润"});
		excelbean.setSheetData(data2);
		excelbean.setName("供应链总表");
		try {
			ExcelUtil.download(request, response, excelbean);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/*------------------------------------上面是供应链总表下面是推广师分润总表-----------------------------------------------------------*/
	
	/**
	 * 
	* @Title: promoterProfitTotalPage
	* @Description: TODO(跳转推广师分润总表页面)
	* @author Mr.Dong
	* @date  2017年6月6日 上午10:52:48
	* @return
	 */
	@RequestMapping(value="promoterProfitTotalPage",method=RequestMethod.GET)
	public String promoterProfitTotalPage(){
		return "profit/profit_total_promotion_division";
	}
	
	/**
	 * 
	* @Title: getpromoterProfitTotalList   
	* @Description: TODO(推广师分润总表list)
	* @author Mr.Dong
	* @date  2017年6月6日 上午10:53:31
	* @param orderProfitTotalDTO
	* @param pagebean
	* @return
	 */
	@RequestMapping(value="getpromoterProfitTotalList",method=RequestMethod.POST)
	@ResponseBody
	public Result getpromoterProfitTotalList(PromoterProfitTotalDTO promoterProfitTotalDTO, PageBean pagebean){
		return iOrderProfitTotalService.getPromoterProfitTotalList(promoterProfitTotalDTO, pagebean);
	}
	
	/**
	 * 
	* @Title: getPromoterProfitTotalEXCEL
	* @Description: TODO(推广师分润总表)
	* @author Mr.Dong
	* @date  2017年6月6日 下午3:50:43
	* @param request
	* @param response
	* @param orderProfitTotalDT
	* @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getPromoterProfitTotalEXCEL", method = RequestMethod.GET)
	public Object getPromoterProfitTotalEXCEL(HttpServletRequest request,
			HttpServletResponse response,PromoterProfitTotalDTO promoterProfitTotalDTO) {
		Result promoterProfitTotalEXCEL = iOrderProfitTotalService.getPromoterProfitTotalEXCEL(promoterProfitTotalDTO);
		List<PromoterProfitTotalVO>  data = (List<PromoterProfitTotalVO>) promoterProfitTotalEXCEL
				.getData();
		List<Object[]> data2 = new ArrayList<Object[]>();
		
		for (PromoterProfitTotalVO profit : data) {
			Object[] standard = new Object[3];
			standard[0] = profit.getTelPhone();
			standard[1] = profit.getMemberName();
			standard[2] = profit.getProfited1();
			data2.add(standard);
		}
		ExcelBean excelbean = new ExcelBean();
		excelbean.setTableHeader(new String[] { "推广师账号","推广师姓名","已分润"});
		excelbean.setSheetData(data2);
		excelbean.setName("推广师总表");
		try {
			ExcelUtil.download(request, response, excelbean);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
