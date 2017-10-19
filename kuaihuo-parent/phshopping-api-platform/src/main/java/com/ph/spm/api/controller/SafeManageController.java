package com.ph.spm.api.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ph.base.BaseController;
import com.ph.shopping.common.core.customenum.SmsCodeType;
import com.ph.shopping.common.core.customenum.SmsSourceEnum;
import com.ph.shopping.common.core.other.sms.data.SmsSendData;
import com.ph.shopping.common.core.other.sms.handle.ISmsDataService;
import com.ph.shopping.common.util.core.ResultUtil;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.common.util.rsa.MD5;
import com.ph.shopping.facade.member.dto.PayPasswordAddDTO;
import com.ph.shopping.facade.member.dto.PayPasswordQueryDTO;
import com.ph.shopping.facade.member.dto.PayPasswordUpdateDTO;
import com.ph.shopping.facade.member.dto.TradersPasswordDTO;
import com.ph.shopping.facade.member.service.IPayPasswordService;
import com.ph.shopping.facade.permission.entity.User;
import com.ph.shopping.facade.permission.service.IUserService;
import com.ph.shopping.facade.permission.vo.SessionUserVO;
import com.ph.shopping.facade.spm.exception.UserAccountExceptionEnum;
import com.ph.shopping.facade.spm.service.ISafeManageService;

/**
 * 
 * @ClassName: SafeManageController
 * @Description: 安全管理
 * @author 王强
 * @date 2017年5月22日 上午10:39:12
 */
@Controller
@RequestMapping("web/safemanage")
public class SafeManageController extends BaseController {

	// 创建公共日志
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@Reference(version = "1.0.0")
	private IUserService iUserService;

	@Reference(version = "1.0.0")
	private IPayPasswordService iPayPasswordService;

	@Autowired
	private ISmsDataService iSmsDataService;

	@Reference(version = "1.0.0")
	private ISafeManageService iSafeManageService;

	/**
	 * ======================================页面跳转===============================
	 */
	/**
	 * 
	 * @Title: toSafeManagePage
	 * @Description: 跳转到安全管理页面
	 * @author 王强
	 * @date 2017年6月13日 下午4:49:00
	 * @return
	 */
	@RequestMapping(value = "/tosafemanagepage", method = RequestMethod.GET)
	public ModelAndView toSafeManagePage() {
		ModelAndView mv = new ModelAndView("account/account_safety");
		SessionUserVO userVO = getLoginUser();
		mv.addObject("roleCode", userVO.getSessionRoleVo().get(0).getRoleCode());
		mv.addObject("telPhone", userVO.getTelphone());
		return mv;
	}

	/**
	 * ======================================数据操作===============================
	 */

	/**
	 * 
	 * @Title: updatePass
	 * @Description: 修改登录密码
	 * @author 王强
	 * @date 2017年6月1日 下午4:49:22
	 * @param user
	 * @param newPwd
	 * @param verificationCode
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/updateloginpwd", method = RequestMethod.POST)
	@ResponseBody
	public Result updatePass(User user, String newPwd, String smsCode, HttpServletRequest request) {
		SessionUserVO sessionUserVo = getLoginUser();
		// MD5加密
		newPwd = MD5.getMD5Str(newPwd);
		// 校验验证码
		SmsSendData sendData = new SmsSendData();
		sendData.setMobile(getLoginUser().getTelphone());
		byte b = sessionUserVo.getSessionRoleVo().get(0).getRoleCode();
		if (b == (byte) 2) {
			sendData.setSourceEnum(SmsSourceEnum.SUPPLIER);
		} else if (b == (byte) 3 || b == (byte) 4 || b == (byte) 5) {
			sendData.setSourceEnum(SmsSourceEnum.AGENT);
		} else if (b == (byte) 6) {
			sendData.setSourceEnum(SmsSourceEnum.MERCHANT);
		}
		sendData.setType(SmsCodeType.UPDATE_PASSWORD_VC);
		sendData.setSmsCode(smsCode);
		Result result = iSmsDataService.checkSmsCodeByMobile(sendData);
		if (result.isSuccess()) {
			user.setId(sessionUserVo.getId());
			user.setPassword(newPwd);
			return iUserService.updatePassword(user);
		} else {
			return ResultUtil.getResult(UserAccountExceptionEnum.CHECKBIND_EXCEPTION);
		}
	}

	/**
	 * 
	 * @Title: updatePayPass
	 * @Description: 修改支付密码
	 * @author 王强
	 * @date 2017年6月1日 下午4:49:11
	 * @param tradersPassword
	 * @param verificationCode
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updatepaypwd", method = RequestMethod.POST)
	@ResponseBody
	public Result updatePayPass(TradersPasswordDTO tradersPasswordDTO, String smsCode, HttpServletRequest request)
			throws Exception {

		// 获取当前用户
		SessionUserVO sessionUserVo = getLoginUser();
		Long userId = sessionUserVo.getId();
		// MD5加密
		tradersPasswordDTO.setNewPwd(tradersPasswordDTO.getNewPwd());
		 

		// 校验验证码
		SmsSendData sendData = new SmsSendData();
		sendData.setMobile(getLoginUser().getTelphone());
		byte b = sessionUserVo.getSessionRoleVo().get(0).getRoleCode();
		if (b == (byte) 2) {
			sendData.setSourceEnum(SmsSourceEnum.SUPPLIER);
		} else if (b == (byte) 3 || b == (byte) 4 || b == (byte) 5) {
			sendData.setSourceEnum(SmsSourceEnum.AGENT);
		} else if (b == (byte) 6) {
			sendData.setSourceEnum(SmsSourceEnum.MERCHANT);
		}
		sendData.setType(SmsCodeType.UPDATE_PAYPWD_VC);
		sendData.setSmsCode(smsCode);
		Result result = iSmsDataService.checkSmsCodeByMobile(sendData);
		if (!result.isSuccess()) {
			return ResultUtil.getResult(UserAccountExceptionEnum.CHECKBIND_EXCEPTION);
		}

		// 判断页面传入的手机号是否是当前登录者
		if (sessionUserVo.getTelphone().equals(tradersPasswordDTO.getTelPhone())) {

			// 判断是否有支付密码
			PayPasswordQueryDTO payPasswordDTO = new PayPasswordQueryDTO();
			payPasswordDTO.setCustomerType((byte) 2);
			payPasswordDTO.setUserId(userId);
			Result result1 = iPayPasswordService.getPayPwdInfo(payPasswordDTO);

			if (result1.getData() != null) {

				// 修改
				PayPasswordUpdateDTO passwordUpdateDTO = new PayPasswordUpdateDTO();
				passwordUpdateDTO.setCustomerType(payPasswordDTO.getCustomerType());
				passwordUpdateDTO.setUserId(payPasswordDTO.getUserId());
				passwordUpdateDTO.setNewPassword(tradersPasswordDTO.getNewPwd());
				passwordUpdateDTO.setUpdaterId(userId);
				return iPayPasswordService.updatePayPassword(passwordUpdateDTO);
			} else {

				// 新增
				PayPasswordAddDTO passwordAddDTO = new PayPasswordAddDTO();
				passwordAddDTO.setUserId(userId);
				passwordAddDTO.setCustomerType((byte) 2);
				passwordAddDTO.setNewPassword(tradersPasswordDTO.getNewPwd());
				passwordAddDTO.setCreaterId(userId);
				return iPayPasswordService.addPayPassword(passwordAddDTO);
			}
		} else {
			return ResultUtil.getResult(UserAccountExceptionEnum.USER_ERROR);
		}
	}
}
