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
import com.ph.shopping.facade.profit.dto.ScoreDetailDTO;
import com.ph.shopping.facade.profit.service.IScoreDetailService;
import com.ph.shopping.facade.profit.vo.ScoreDetailVO;

/**
 * 
 * @ClassName: MemberScoreController
 * @Description: 会员积分明细，会员提现，会员余额
 * @author 王强
 * @date 2017年6月7日 下午3:16:16
 */
@Controller
@RequestMapping("web/scoredetail")
public class ScoreDetailController extends BaseController {

	@Reference(version = "1.0.0")
	private IScoreDetailService iScoreDetailService;

	/**
	 * 
	 * @Title: toScoreDetail
	 * @Description: 跳转到积分明细页面
	 * @author 王强
	 * @date 2017年6月7日 下午3:26:10
	 * @return
	 */
	@RequestMapping(value = "toscoredetail", method = RequestMethod.GET)
	public ModelAndView toScoreDetailPage(String memberId) {
		ModelAndView view = new ModelAndView("finance/finance_integral");
		view.addObject("memberId", memberId);
		return view;
	}

	/**
	 * 
	 * @Title: getScoreDetail
	 * @Description: 获取积分明细
	 * @author 王强
	 * @date 2017年6月7日 下午3:22:46
	 * @param scoreDetailDTO
	 * @return
	 */
	@RequestMapping(value = "getscoredetail", method = RequestMethod.GET)
	@ResponseBody
	public Result getScoreDetail(ScoreDetailDTO scoreDetailDTO, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Result result = iScoreDetailService.getScoreDetail(scoreDetailDTO);
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
	public Object exportExcel(HttpServletRequest request, HttpServletResponse response, ScoreDetailDTO scoreDetailDTO) {
		List<ScoreDetailVO> scoreDetailVOs = iScoreDetailService.getExportData(scoreDetailDTO);
		return exportExcel(request, response, scoreDetailVOs);
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
			String[] title = new String[] { "会员账号", "会员类型", "会员姓名", "来源", "操作时间", "积分数量", "手续费" };
			excelbean.setTableHeader(title);
			excelbean.setSheetData(data2);
			excelbean.setName("积分明细");
			ExcelUtil.download(request, response, excelbean);

		} catch (Exception e) {
			logger.error("导出异常", e);
		}
		return null;
	}

}
