/**  
 * @Title:  LoginAndRegController.java   
 * @Package com.phshopping.api.controller.regandlogin   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 李杰    
 * @date:   2017年5月9日 下午3:19:25   
 * @version V1.0 
 * @Copyright: 2017
 */
package com.phshopping.api.controller.regandlogin;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.ph.shopping.common.core.customenum.SmsCodeType;
import com.ph.shopping.common.core.customenum.SmsSourceEnum;
import com.ph.shopping.common.core.dto.CheckDTO;
import com.ph.shopping.common.core.other.annotation.Token;
import com.ph.shopping.common.core.other.sms.data.SmsSendData;
import com.ph.shopping.common.core.other.sms.handle.ISmsDataService;
import com.ph.shopping.common.core.util.SmsUtil;
import com.ph.shopping.common.util.core.RespCode;
import com.ph.shopping.common.util.core.ResultUtil;
import com.ph.shopping.common.util.other.MobileCheckUtil;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.member.dto.MemberLoginDTO;
import com.ph.shopping.facade.member.dto.MemberPasswordDTO;
import com.ph.shopping.facade.member.dto.MemberRegisterDTO;
import com.ph.shopping.facade.member.service.IMemberService;
import com.phshopping.api.appenum.AppResultEnum;
import com.phshopping.api.controller.dto.AppMemberPasswordDTO;
import com.phshopping.api.uitl.PasswordUtil;
import com.phshopping.api.uitl.UserCacheUtil;

/**
 * @ClassName: LoginAndRegController
 * @Description:App登录注册
 * @author: 李杰
 * @date: 2017年5月9日 下午3:19:25
 * @Copyright: 2017
 */
@RestController
@RequestMapping("api")
public class LoginAndRegController {

	//日志
	private static final Logger logger = LoggerFactory.getLogger(LoginAndRegController.class);
	
	//会员接口
	@Reference(version = "1.0.0")
	private IMemberService memberService;

	//短信接口
	@Autowired
	private ISmsDataService smsDataService;
	@Autowired
	private SmsUtil smsUtil;
	/**
	 * 
	 * @Title: login   
	 * @Description:登录 
	 * @param: @param request
	 * @param: @param dto
	 * @param: @return      
	 * @return: Result
	 * @author：李杰      
	 * @throws
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public Result login(HttpServletRequest request, MemberLoginDTO dto) {
		logger.info("app登录处理参数：MemberLoginDTO = {} ", JSON.toJSONString(dto));
		Result result = ResultUtil.getResult(RespCode.Code.REQUEST_DATA_ERROR);
		if (StringUtils.isBlank(dto.getEquipmentId())) {
			result.setMessage("[设备ID]不能为空");
			return result;
		}
		// 2.登陆认证
		result = memberService.memberLogin(dto);
		logger.info("app登录返回数据：Result = {} ", JSON.toJSONString(result));
		// 3.缓存用户信息
		if (result.isSuccess()) {
			result.setData(UserCacheUtil.addUser(dto.getTelPhone()));
		}
		return result;
	}
	/**
	 * 
	 * @Title: logout   
	 * @Description: 注销   
	 * @param: @param request
	 * @param: @param token
	 * @param: @return      
	 * @return: Result
	 * @author：李杰      
	 * @throws
	 */
	@RequestMapping(value = "/logout/{token}", method = RequestMethod.GET)
	public Result logout(HttpServletRequest request, @PathVariable String token) {
		logger.info("注销参数 token ： " + token);
		Result result = ResultUtil.setResult(true, "注销成功");
		if (StringUtils.isBlank(token)) {
			return ResultUtil.setResult(result, RespCode.Code.REQUEST_DATA_ERROR);
		}
		UserCacheUtil.removeUser(token);
		return result;
	}
	/**
	 * 
	 * @Title: registersubmit   
	 * @Description: 注册   
	 * @param: @param request
	 * @param: @param dto
	 * @param: @return      
	 * @return: Result
	 * @author：李杰      
	 * @throws
	 */
	@Token(key = "telPhone")
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public Result registersubmit(HttpServletRequest request, MemberRegisterDTO dto) {
		logger.info("app注册处理参数：MemberRegisterDTO = {} ", JSON.toJSONString(dto));
		Result result = ResultUtil.getResult(RespCode.Code.REQUEST_DATA_ERROR);
		if (StringUtils.isBlank(dto.getEquipmentId())) {
			result.setMessage("[设备ID]不能为空");
			return result;
		}
		if (!PasswordUtil.verifyPwd(dto.getMemberPwd())) {
			return ResultUtil.setResult(result, AppResultEnum.PASSWORD_FORMAT_FAIL2);
		}
		// 短信验证
		//result = verifySmsCode(dto.getTelPhone(), dto.getSmsCode(), SmsCodeType.MER_REGISTER_FR);
		//短信验证
		result=yzSmsCode(dto.getTelPhone(), dto.getSmsCode());
		if (!result.isSuccess()) {
			return result;
		}
		result = memberService.registerMember(dto);
		logger.info("app注册返回数据：Result = {} ", JSON.toJSONString(result));
		return result;
	}

	/**
	 * 
	 * @Title: backPwdSubmit   
	 * @Description:找回密码 
	 * @param: @param request
	 * @param: @param dto
	 * @param: @return      
	 * @return: Result
	 * @author：李杰      
	 * @throws
	 */
	@RequestMapping(value = "/backPwdSubmit", method = RequestMethod.POST)
	public Result backPwdSubmit(HttpServletRequest request, AppMemberPasswordDTO dto) {
		logger.info("找回密码处理参数：PasswordVo = {} ", JSON.toJSONString(dto));
		Result result = ResultUtil.setResult(false, "找回密码失败");
		if (!PasswordUtil.verifyPwd(dto.getNewPassword())) {
			return ResultUtil.setResult(result, AppResultEnum.PASSWORD_FORMAT_FAIL);
		}
		// 短信验证
		result=yzSmsCode(dto.getTelPhone(), dto.getSmsCode());
		//result = verifySmsCode(dto.getTelPhone(), dto.getSmsCode(), SmsCodeType.MER_REGISTER_FR);
		if (!result.isSuccess()) {
			return result;
		}
		MemberPasswordDTO rdto = new MemberPasswordDTO();
		BeanUtils.copyProperties(dto, rdto);
		result = memberService.backMmberPassword(rdto);
		logger.info("找回密码返回数据：Result = {} ", JSON.toJSONString(result));
		return result;
	}
	/**
	 * 
	 * @Title: checkPhone   
	 * @Description:校验手机号是否存在
	 * @param: @param request
	 * @param: @param mobile
	 * @param: @return      
	 * @return: Result
	 * @author：李杰      
	 * @throws
	 */
	@RequestMapping(value = "/checkPhone/{phone}", method = RequestMethod.GET)
	public Result checkPhone(HttpServletRequest request, @PathVariable String phone) {
		logger.info("验证账号是否存在：phone = " + phone);
		Result result = ResultUtil.setResult(false, "手机号校验失败");
		if (!MobileCheckUtil.isPhoneLegal(phone)) {
			logger.warn("checkPhone 手机号格式错误");
			ResultUtil.setResult(result, false, "手机号格式错误！");
		} else {
			result = memberService.verifyPhoneIsExists(phone);
			logger.info("根据手机号获取会员信息返回数据：Result = {} ", JSON.toJSONString(result));
		}
		return result;
	}
	/**
	 * 
	 * @Title: verifySmsCode   
	 * @Description:校验短信验证码  
	 * @param: @param mobile
	 * @param: @param smsCode
	 * @param: @return      
	 * @return: boolean
	 * @author：李杰      
	 * @throws
	 */
	private Result verifySmsCode(String mobile, String smsCode, SmsCodeType type) {
		SmsSendData sendData = new SmsSendData();
		sendData.setMobile(mobile);
		sendData.setSmsCode(smsCode);
		sendData.setSourceEnum(SmsSourceEnum.MEMBER);
		sendData.setType(type);
		Result result = smsDataService.checkSmsCodeByMobile(sendData);
		logger.info("校验短信验证码返回数据 Result = {} ", JSON.toJSONString(result));
		return result;
	}
	
	@ResponseBody
	public Result yzSmsCode(String mobile, String smsCode) {
		Result result = ResultUtil.getResult(RespCode.Code.SUCCESS);
		CheckDTO checkDTO = new CheckDTO();
		checkDTO.setCode(smsCode);
		checkDTO.setCodeType("Fr170001");
		checkDTO.setPhone(mobile);
		Result check = smsUtil.check(checkDTO);
		if (!"1".equals(check.getCode())) {
			return ResultUtil.setResult(false,check.getMessage());
		}
		return result;
	}
	
	//测试验证码
	/*@RequestMapping(value="check")
	public Result verifySmsCode2(String mobile, String smsCode) {
		SmsSendData sendData = new SmsSendData();
		sendData.setMobile(mobile);
		sendData.setSmsCode(smsCode);
		sendData.setSourceEnum(SmsSourceEnum.MEMBER);
		Result result = smsDataService.checkSmsCodeByMobile(sendData);
		logger.info("校验短信验证码返回数据 Result = {} ", JSON.toJSONString(result));
		return result;
	}*/
}
