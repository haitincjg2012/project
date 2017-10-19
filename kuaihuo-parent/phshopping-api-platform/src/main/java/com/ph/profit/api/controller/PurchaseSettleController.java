package com.ph.profit.api.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ph.base.BaseController;
import com.ph.shopping.common.util.excel.ExcelBean;
import com.ph.shopping.common.util.excel.ExcelUtil;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.profit.dto.PurchaseSettleDTO;
import com.ph.shopping.facade.profit.service.IPurchaseSettleService;
import com.ph.shopping.facade.profit.vo.PurchaseSettleVO;

/**
 * 供应链结算
* @ClassName: PurchaseSettleController
* @Description: TODO(这里用一句话描述这个类的作用)
* @author Mr.Dong
* @date 2017年6月8日 下午5:17:31
 */
@Controller
@RequestMapping("web/purchaseSettle")
public class PurchaseSettleController extends BaseController {

	@Reference(version = "1.0.0")
	private IPurchaseSettleService iPurchaseSettleService;
	
	
	/**
	 * 跳转供应链结算页面
	* @Title: purchaseSettlePage
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @author Mr.Dong
	* @date  2017年6月9日 下午3:31:52
	* @return
	 */
	@RequestMapping(value = "purchaseSettlePage", method = RequestMethod.GET)
	public String purchaseSettlePage() {
		return "finance/finance_supply";
	}

	
	/**
	 * 供应链结算list
	* @Title: getPurchaseSettleList
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @author Mr.Dong
	* @date  2017年6月9日 下午3:33:02
	* @param purchaseSettleDTO
	* @return
	 */
	@RequestMapping(value = "/getPurchaseSettleList", method = RequestMethod.GET)
	@ResponseBody
	public Result getPurchaseSettleList(PurchaseSettleDTO purchaseSettleDTO){
		return iPurchaseSettleService.getPurchaseSettleList(purchaseSettleDTO);
	}
	
	
	/**
	 * 导出供应链结算EXCEL
	* @Title: getPurchaseSettleEXCEL
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @author Mr.Dong
	* @date  2017年6月9日 下午3:47:22
	* @param purchaseSettleDTO
	* @param request
	* @param response
	* @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getPurchaseSettleEXCEL", method = RequestMethod.GET)
	public Object getPurchaseSettleEXCEL(PurchaseSettleDTO purchaseSettleDTO,HttpServletRequest request, HttpServletResponse response){
		try {
			Result purchaseSettleEXCEL = iPurchaseSettleService.getPurchaseSettleEXCEL(purchaseSettleDTO);
			List<PurchaseSettleVO> data = (List<PurchaseSettleVO>) purchaseSettleEXCEL
					.getData();
			
			List<Object[]> data2 = new ArrayList<Object[]>();
			
			for (PurchaseSettleVO profit : data) {
				Object[] standard = new Object[12];
				standard[0] = profit.getOrderNo();
				SimpleDateFormat  df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				if(profit.getOrderTime() != null){
					standard[1] = df.format(profit.getOrderTime());
				}else{
					standard[1] ="无";
				}
				
				if(profit.getOrderTime() != null){
					standard[2] = df.format(profit.getReceiptTime());
				}else{
					standard[2] ="无";
				}
				
				if(profit.getSettleTime() != null){
					standard[3] = df.format(profit.getSettleTime());
				}else{
					standard[3] ="无";
				}
				standard[4] = profit.getOrderMoney1();
				standard[5] = profit.getSettleMoney1();
				
				if(profit.getPayType() != null){
					if(profit.getPayType() == 0){
						standard[6] = "余额支付";
					}else if(profit.getPayType() == 1){
						standard[6] = "第三方支付";
					}
				}else{
					standard[6] = "未支付";
				}
				
				standard[7] = profit.getPurchaserTel();
				standard[8] = profit.getPurchaserName();
				standard[9] = profit.getSenderTel();
				standard[10] = profit.getSenderName();
				if(profit.getIsSettle() == 0){
					standard[11] = "未结算";
				}else if(profit.getIsSettle() == 1){
					standard[11] = "已经结算";
				}
				data2.add(standard);
			}
			ExcelBean excelbean = new ExcelBean();
			String[] title = new String[] {"订单号", "订单时间","收货时间", "结算时间", "订单金额", "结算金额", "支付方式","采购商账号", "采购商企业",
					"供货商账号", "供货商企业", "结算状态" };
			excelbean.setTableHeader(title);
			excelbean.setName("供应链结算表");
			excelbean.setSheetData(data2);
			ExcelUtil.download(request, response, excelbean);
		} catch (Exception e) {
			logger.error("导出异常", e);
		}
		return null;
	}
	
}
