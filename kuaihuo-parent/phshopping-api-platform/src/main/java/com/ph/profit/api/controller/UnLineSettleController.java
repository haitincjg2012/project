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
import com.ph.shopping.facade.profit.dto.UnLineSettleDTO;
import com.ph.shopping.facade.profit.service.IUnLineSettleService;
import com.ph.shopping.facade.profit.vo.UnLineSettleVO;

/**
 * 线下订单结算
* @ClassName: UnLineSettleController
* @Description: TODO(这里用一句话描述这个类的作用)
* @author Mr.Dong
* @date 2017年6月7日 上午11:11:32
 */
@Controller
@RequestMapping("web/unLineSettle")
public class UnLineSettleController extends BaseController {

	@Reference(version = "1.0.0")
	private IUnLineSettleService iUnLineSettleService;
	
	
	/**
	 * 跳转线下结算页面
	* @Title: toUnLineSettlePage
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @author Mr.Dong
	* @date  2017年6月7日 上午11:19:57
	* @return
	 */
	@RequestMapping(value = "toUnLineSettlePage", method = RequestMethod.GET)
	public String toUnLineSettlePage() {
		return "finance/finance_unline";
	}

	/**
	 * 线下结算list
	* @Title: getUnLineSettleList
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @author Mr.Dong
	* @date  2017年6月7日 下午2:18:36
	* @param unLineSettleDTO
	* @return
	 */
	@RequestMapping(value = "/getUnLineSettleList", method = RequestMethod.GET)
	@ResponseBody
	public Result getUnLineSettleList(UnLineSettleDTO unLineSettleDTO){
		return iUnLineSettleService.getUnLineSettleList(unLineSettleDTO);
	}
	
	/**
	 * 导出线下结算EXCEL
	* @Title: getUnLineSettleEXCEL
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @author Mr.Dong
	* @date  2017年6月7日 下午5:38:41
	* @param unLineSettleDTO
	* @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getUnLineSettleEXCEL", method = RequestMethod.GET)
	public Object getUnLineSettleEXCEL(UnLineSettleDTO unLineSettleDTO,HttpServletRequest request, HttpServletResponse response){
		try {
			Result unLineSettleEXCEL = iUnLineSettleService.getUnLineSettleEXCEL(unLineSettleDTO);
			List<UnLineSettleVO> data = (List<UnLineSettleVO>) unLineSettleEXCEL
					.getData();
			
			List<Object[]> data2 = new ArrayList<Object[]>();
			
			for (UnLineSettleVO profit : data) {
				Object[] standard = new Object[12];
				standard[0] = profit.getOrderNo();
				SimpleDateFormat  df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				if(profit.getOrderTime() != null){
					standard[1] = df.format(profit.getOrderTime());
				}else{
					standard[1] ="无";
				}
				
				if(profit.getSettleTime() != null){
					standard[2] = df.format(profit.getSettleTime());
				}else{
					standard[2] ="无";
				}
				
				standard[3] = profit.getOrderMoney1();
				standard[4] = profit.getSettleMoney1();
				standard[5] = profit.getMemberTel();
				standard[6] = profit.getMemberName();
				standard[7] = profit.getMerchantTel();
				standard[8] = profit.getMerchantCompanyName();
				standard[9] = profit.getMerchantName();
				
				if(profit.getPayType() == 0){
					standard[10] = "现金";
				}else if(profit.getPayType() == 1){
					standard[10] = "积分";
				}else if(profit.getPayType() == 2){
					standard[10] = "支付宝";
				}else if(profit.getPayType() == 3){
					standard[10] = "微信";
				}
				
				if(profit.getIsSettle() == 0){
					standard[11] = "未结算";
				}else if(profit.getIsSettle() == 1){
					standard[11] = "已经结算";
				}
				
				data2.add(standard);
			}
			ExcelBean excelbean = new ExcelBean();
			String[] title = new String[] {"订单号", "订单时间", "结算时间", "订单金额", "结算金额", "会员账号", "会员姓名",
					"商户账号", "商户企业名称","店铺名称", "支付方式", "结算状态" };
			excelbean.setTableHeader(title);
			excelbean.setName("线下结算表");
			excelbean.setSheetData(data2);
			ExcelUtil.download(request, response, excelbean);
		} catch (Exception e) {
			logger.error("导出异常", e);
		}
		return null;
	}
	
}
