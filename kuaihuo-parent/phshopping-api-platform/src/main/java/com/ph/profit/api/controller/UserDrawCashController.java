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
import com.ph.shopping.common.util.core.ResultUtil;
import com.ph.shopping.common.util.excel.ExcelBean;
import com.ph.shopping.common.util.excel.ExcelUtil;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.common.util.verifycode.VerifyUtil;
import com.ph.shopping.facade.permission.constant.RoleIDEnum;
import com.ph.shopping.facade.permission.vo.SessionRoleVO;
import com.ph.shopping.facade.permission.vo.SessionUserVO;
import com.ph.shopping.facade.profit.constant.AuditEnum;
import com.ph.shopping.facade.profit.constant.AuditOperatorConstants;
import com.ph.shopping.facade.profit.dto.AuditDTO;
import com.ph.shopping.facade.profit.dto.UserDrawCashDTO;
import com.ph.shopping.facade.profit.exception.ProfitExceptionEnum;
import com.ph.shopping.facade.profit.service.IUserDrawCashService;
import com.ph.shopping.facade.profit.vo.UserDrawCashVO;

/**
 * 
 * @ClassName: UserDrawCashController
 * @Description: 供链提现Controller
 * @author 王强
 * @date 2017年6月12日 上午10:24:10
 */
@Controller
@RequestMapping("web/userdrawcash")
public class UserDrawCashController extends BaseController {

	@Reference(version = "1.0.0")
	private IUserDrawCashService iUserDrawCashService;

	/**
	 * 
	 * @Title: toUserDrawCashPage
	 * @Description: 跳转到提现页面
	 * @author 王强
	 * @date 2017年6月23日 上午9:30:12
	 * @return
	 */
	@RequestMapping(value = "touserdrawcash", method = RequestMethod.GET)
	public String toUserDrawCashPage() {
		return "finance/finance_drawals";
	}

	/**
	 * 
	 * @Title: getUserDrawCashes
	 * @Description: 获取用户提现列表
	 * @author 王强
	 * @date 2017年6月13日 上午10:02:37
	 * @param userDrawCashDTO
	 * @return
	 */
	@RequestMapping(value = "getuserdrawcashes", method = RequestMethod.POST)
	@ResponseBody
	public Result getUserDrawCashes(UserDrawCashDTO userDrawCashDTO) {
		return iUserDrawCashService.getUserDrawCahsList(userDrawCashDTO);
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
	public Object exportExcel(HttpServletRequest request, HttpServletResponse response,
			UserDrawCashDTO userDrawCashDTO) {
		List<UserDrawCashVO> userDrawCashVOs = iUserDrawCashService.getExportData(userDrawCashDTO);
		return exportExcel(request, response, userDrawCashVOs);
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
					if (i == 0 || caseField.getGenericType().toString().equals("class java.lang.Long")
							|| caseField.getGenericType().toString().equals("class java.util.Date")) {
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
			String[] title = new String[] { "订单号", "提交时间", "供链类型", "供链名称", "供链账号", "运营审核时间", "财务审批时间", "金额", "手续费",
					"审核状态", "提现状态", "平衡差" };
			excelbean.setTableHeader(title);
			excelbean.setSheetData(data2);
			excelbean.setName("供链提现");
			ExcelUtil.download(request, response, excelbean);

		} catch (Exception e) {
			logger.error("导出异常", e);
		}
		return null;
	}

	/**
	 * 
	 * @Title: auditPass
	 * @Description: 审核操作
	 * @author 王强
	 * @date 2017年6月13日 下午2:37:11
	 * @param auditDTO
	 * @return
	 */
	@RequestMapping(value = "auditpass", method = RequestMethod.POST)
	@ResponseBody
	public Result auditPass(AuditDTO auditDTO) {
		SessionUserVO userVO = getLoginUser();
		auditDTO.setUserId(userVO.getId());
		SessionRoleVO roleVO = userVO.getSessionRoleVo().get(0);
		Long roleId = roleVO.getId();
		auditDTO.setUserType(roleVO.getRoleCode());
		auditDTO.setAuditRgint(roleId);
		if (auditDTO.getAuditState() == getCode(AuditEnum.PASSED)) {
			return ResultUtil.getResult(ProfitExceptionEnum.HAVE_AUDITED);
		}

		if (auditDTO.getAuditState() == getCode(AuditEnum.OPERATOR_UNPASS) || auditDTO.getAuditState() == getCode(AuditEnum.TREASURER_UNPASS)) {
			return ResultUtil.getResult(ProfitExceptionEnum.INVALID_AUDIT_CODE);
		}

		// 7运营审核
		if (auditDTO.getAuditState() == getCode(AuditEnum.OPERATOR_PASSING) && roleId == RoleIDEnum.OPERATOR.getId()) {
			if (auditDTO.getType() == AuditOperatorConstants.PASS_OPER) {
				auditDTO.setAuditState(getCode(AuditEnum.TREASURER_PASSING));
			} else if (auditDTO.getType() == AuditOperatorConstants.UNPASS_OPER) {
				auditDTO.setAuditState(getCode(AuditEnum.OPERATOR_UNPASS));
			}
			return iUserDrawCashService.doAuditOperator(auditDTO);
		} else if (auditDTO.getAuditState() == getCode(AuditEnum.TREASURER_PASSING) && roleId == RoleIDEnum.TREASURER.getId()) {// 8财务审核
			if (auditDTO.getType() == AuditOperatorConstants.PASS_OPER) {
				auditDTO.setAuditState(getCode(AuditEnum.PASSED));
			} else if (auditDTO.getType() == AuditOperatorConstants.UNPASS_OPER) {
				auditDTO.setAuditState(getCode(AuditEnum.TREASURER_UNPASS));
			}
			return iUserDrawCashService.doAuditOperator(auditDTO);
		} else {
			return ResultUtil.getResult(ProfitExceptionEnum.HAVE_NO_RIGHT);
		}
	}

	/**
	 * 
	 * @Title: getCode
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @author 王强
	 * @date 2017年6月13日 下午4:41:28
	 * @param auditEnum
	 * @return
	 */
	private int getCode(AuditEnum auditEnum) {
		return auditEnum.getCode();
	}
}
