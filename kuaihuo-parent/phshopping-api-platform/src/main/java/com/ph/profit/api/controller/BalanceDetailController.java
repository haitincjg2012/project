package com.ph.profit.api.controller;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ph.base.BaseController;
import com.ph.shopping.common.util.excel.ExcelBean;
import com.ph.shopping.common.util.excel.ExcelUtil;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.common.util.verifycode.VerifyUtil;
import com.ph.shopping.facade.profit.dto.BalanceDetailDTO;
import com.ph.shopping.facade.profit.service.IBalanceDetailService;
import com.ph.shopping.facade.profit.vo.BalanceDetailVO;
/**
 * 
* @ClassName: SupplyChainBalanceController
* @Description: 余额明细Controller
* @author 王强
* @date 2017年6月8日 上午10:46:39
 */
@Controller
@RequestMapping("web/balancedetail")
public class BalanceDetailController extends BaseController {
	
	@Reference(version="1.0.0",timeout=600000)
	private IBalanceDetailService iBalanceDetailService;
	
	/**
	 * 
	 * @Title: toBalanceDetail
	 * @Description: 跳转到余额明细页面
	 * @author 王强
	 * @date 2017年6月8日 上午10:49:02
	 * @return
	 */
	@RequestMapping(value = "tobalancedetail", method=RequestMethod.GET)
	public ModelAndView toBalanceDetailPage(String userId) throws Exception {
		ModelAndView view = new ModelAndView("finance/finance_details");
		view.addObject("userId", userId);
		return view;
	}
	
	
	/**
	 * 
	* @Title: getBalanceDetailList
	* @Description: 获取余额明细列表
	* @author 王强
	* @date  2017年6月8日 下午2:51:48
	* @param balanceDetailDTO
	* @return
	 */
	@RequestMapping(value="getbalancedetaillist", method=RequestMethod.GET)
	@ResponseBody
	public Result getBalanceDetailList(BalanceDetailDTO balanceDetailDTO) {
		return iBalanceDetailService.getBalanceDetail(balanceDetailDTO);
	}
	
	/**
	 * 
	 * @Title: exportExcel
	 * @Description: 导出excel
	 * @author 王强
	 * @date 2017年6月7日 下午8:09:38
	 * @param request
	 * @param response
	 * @param scoreDetailDTO
	 * @return
	 */
	@RequestMapping(value = "exprotexcel", method = RequestMethod.POST)
	public Object exportExcel(HttpServletRequest request, HttpServletResponse response, BalanceDetailDTO balanceDetailDTO) {
		List<BalanceDetailVO> balanceDetailVOs = iBalanceDetailService.getExportData(balanceDetailDTO);
		return exportExcel(request, response, balanceDetailVOs);
	}

	/**
	 * 
	 * @Title: exportExcel
	 * @Description: 导出
	 * @author 王强
	 * @date 2017年6月7日 下午3:00:29
	 * @param request
	 * @param response
	 * @param result
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
					if (i== 0 || caseField.getGenericType().toString().equals("class java.lang.Long")) {
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
			String[] title = new String[] { "供链账号", "企业类型","企业名称", "来源","订单号", "操作时间", "金额", "手续费" };
			excelbean.setTableHeader(title);
			excelbean.setSheetData(data2);
			excelbean.setName("余额明细");
			ExcelUtil.download(request, response, excelbean);

		} catch (Exception e) {
			logger.error("导出异常", e);
		}
		return null;
	}
	
}
