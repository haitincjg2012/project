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

import com.alibaba.dubbo.config.annotation.Reference;
import com.ph.base.BaseController;
import com.ph.shopping.common.util.excel.ExcelBean;
import com.ph.shopping.common.util.excel.ExcelUtil;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.common.util.verifycode.VerifyUtil;
import com.ph.shopping.facade.profit.dto.OnLineSettleDTO;
import com.ph.shopping.facade.profit.service.IOnLineSettleService;
import com.ph.shopping.facade.profit.vo.OnLineSettleVO;

/**
 * 
 * @ClassName: OnLineSettleController
 * @Description: 线上结算Controller
 * @author 王强
 * @date 2017年6月6日 上午11:23:31
 */
@Controller
@RequestMapping("web/onlinesettle")
public class OnLineSettleController extends BaseController {

	@Reference(version = "1.0.0")
	private IOnLineSettleService iOnLineSettleService;

	/**
	 * 
	 * @Title: toOnLineSettlePage
	 * @Description: 跳转到线上结算列表页面
	 * @author 王强
	 * @date 2017年6月6日 上午11:26:53
	 * @return
	 */
	@RequestMapping(value = "toonlinesettle", method = RequestMethod.GET)
	public String toOnLineSettlePage() {
		return "finance/finance_online";
	}

	/**
	 * 
	* @Title: queryOnLineSettle
	* @Description: 查询线上结算列表
	* @author 王强
	* @date  2017年6月9日 下午5:21:04
	* @param lineSettleDTO
	* @param request
	* @param response
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "queryonlinesettle", method = RequestMethod.GET)
	@ResponseBody
	public Result queryOnLineSettle(OnLineSettleDTO lineSettleDTO) throws Exception {
		Result result = iOnLineSettleService.queryOnlineSettles(lineSettleDTO);
		return result;
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
	public Object exportExcel(HttpServletRequest request, HttpServletResponse response, OnLineSettleDTO lineSettleDTO) {
		List<OnLineSettleVO> lineSettleVOs = iOnLineSettleService.getExportData(lineSettleDTO);
		return exportExcel(request, response, lineSettleVOs);
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
			String[] title = new String[] { "订单号", "订单时间", "收货时间", "结算时间", "订单金额", "结算金额", "支付方式", "会员账号", "会员姓名",
					"供应商账号", "供应商企业名称", "结算状态" };
			excelbean.setTableHeader(title);
			excelbean.setSheetData(data2);
			excelbean.setName("线上结算");
			ExcelUtil.download(request, response, excelbean);

		} catch (Exception e) {
			logger.error("导出异常", e);
		}
		return null;
	}
}
