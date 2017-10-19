package com.ph.profit.api.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ph.base.BaseController;
import com.ph.shopping.common.util.excel.ExcelBean;
import com.ph.shopping.common.util.excel.ExcelUtil;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.common.util.verifycode.VerifyUtil;
import com.ph.shopping.facade.profit.dto.FrozenDTO;
import com.ph.shopping.facade.profit.dto.SupplyChainBalanceDTO;
import com.ph.shopping.facade.profit.service.ISupplyChainBalanceServce;
import com.ph.shopping.facade.profit.vo.SupplyChainBalanceVO;
import com.ph.shopping.facade.spm.service.IUserBalanceService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: SupplyChainBalanceController
 * @Description: 供链余额Controller
 * @author 王强
 * @date 2017年6月8日 下午4:07:18
 */

/**
 * 
* @ClassName: SupplyChainBalanceController
* @Description: 供链余额Controller
* @author 王强
* @date 2017年6月14日 下午2:12:21
 */
@Controller
@RequestMapping("web/supplychainbalance")
public class SupplyChainBalanceController extends BaseController {
	
	@Reference(version="1.0.0")
	private ISupplyChainBalanceServce iSupplyChainBalanceServce;
	
	@Reference(version="1.0.0")
	private IUserBalanceService iUserBalance;
	
	/**
	 * 
	 * @Title: toBalanceDetail
	 * @Description: 跳转到供链余额页面
	 * @author 王强
	 * @date 2017年6月8日 上午10:49:02
	 * @return
	 */
	@RequestMapping(value = "tosuppychainbalance", method=RequestMethod.GET)
	public String toSuppyChainBalancePage() {
		return "finance/finance_balance";
	}
	
	/**
	 * 
	* @Title: getSupplyChainBalance
	* @Description:查询供链余额列表
	* @author 王强
	* @date  2017年6月9日 下午4:10:50
	* @param supplyChainBalanceDTO
	* @return
	 */
	@RequestMapping(value="getsupplychainbalance", method=RequestMethod.GET)
	@ResponseBody
	public Result getSupplyChainBalance(SupplyChainBalanceDTO supplyChainBalanceDTO) {
		return iSupplyChainBalanceServce.getSupplyChainBalanceList(supplyChainBalanceDTO);
	}
	
	/**
	 * 
	 * @Title: exportExcel
	 * @Description: 导出excel
	 * @author 王强
	 * @date 2017年6月7日 下午8:09:38
	 * @param request
	 * @param response
	 * @param
	 * @return
	 */
	@RequestMapping(value = "exprotexcel", method = RequestMethod.POST)
	public Object exportExcel(HttpServletRequest request, HttpServletResponse response, SupplyChainBalanceDTO supplyChainBalanceDTO) {
		List<SupplyChainBalanceVO> supplyChainBalanceVOs = iSupplyChainBalanceServce.getExportData(supplyChainBalanceDTO);
		return exportExcel(request, response, supplyChainBalanceVOs);
	}

	/**
	 * 
	 * @Title: exportExcel
	 * @Description: 导出
	 * @author 王强
	 * @date 2017年6月7日 下午3:00:29
	 * @param request
	 * @param response
	 * @param
	 * @return
	 */
	private Object exportExcel(HttpServletRequest request, HttpServletResponse response, List<?> list) {
		try {
			if (!VerifyUtil.listIsNotNull(list)) {
				return null;
			}
			List<Object[]> data2 = new ArrayList<Object[]>();
			int size = list.size();
			for (int j = 0; j < size; j++) {
				Object obj = list.get(j);
				Field[] field = obj.getClass().getDeclaredFields();
				int len = field.length;
				Object[] standard = null;
				List<Object> values = new ArrayList<Object>();
				int n = 0;
				for (int i = 0; i < len; i++) {
					Field caseField = field[i];
					caseField.setAccessible(true);
					if (i== 0 || caseField.getGenericType().toString().equals("class java.lang.Long")
							|| caseField.getGenericType().toString().equals("class java.lang.Byte")) {
						n++;
						continue;
					}
					values.add(caseField.get(obj));
				}
				standard = new Object[len - n];
				standard = values.toArray();
				data2.add(standard);
			}

			ExcelBean excelbean = new ExcelBean();
			String[] title = new String[] { "供链账号", "供链类型", "账户余额","在线充值", "余额提现", "线下订单-服务费用", "线下订单-订单结算","线下订单-管理分润","线上订单-订单结算","线上订单-管理分润","线上订单-供链分润","供链订单-订单结算",
				"供链订单-余额支付","批发商订单-管理分润","批发商订单-供链分润","供链订单-订单退款","平衡差","状态"};
			excelbean.setTableHeader(title);
			excelbean.setSheetData(data2);
			excelbean.setName("供链余额");
			ExcelUtil.download(request, response, excelbean);

		} catch (Exception e) {
			logger.error("导出异常", e);
		}
		return null;
	}
	
	/**
	 * 
	* @Title: doFrozen
	* @Description: 冻结/解冻余额
	* @author 王强
	* @date  2017年6月9日 下午10:33:22
	* @param
	* @return
	 */
	@RequestMapping(value="dofrozen", method=RequestMethod.POST)
	@ResponseBody
	public Result doFrozen(FrozenDTO frozenDTO) {
		
		return iUserBalance.updateBalanceStatus(frozenDTO.getBalanceId(), frozenDTO.getFlag());
	}
}
