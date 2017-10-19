/**
 * @Title:  AccountSecurityController.java
 * @Package com.phshopping.api.controller.personal.account
 * @Description:    TODO(用一句话描述该文件做什么)
 * @author: 李杰
 * @date:   2017年5月10日 下午2:54:28
 * @version V1.0
 * @Copyright: 2017
 */
package com.phshopping.api.controller.personal.account;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.ph.shopping.common.core.customenum.CacheKeyTypeEnum;
import com.ph.shopping.common.core.customenum.SmsCodeType;
import com.ph.shopping.common.core.customenum.SmsSourceEnum;
import com.ph.shopping.common.core.dto.CheckDTO;
import com.ph.shopping.common.core.other.annotation.Token;
import com.ph.shopping.common.core.other.sms.data.SmsSendData;
import com.ph.shopping.common.core.other.sms.handle.ISmsDataService;
import com.ph.shopping.common.core.util.SmsUtil;
import com.ph.shopping.common.util.core.RespCode;
import com.ph.shopping.common.util.core.ResultUtil;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.member.dto.*;
import com.ph.shopping.facade.member.entity.CertificatesAuth;
import com.ph.shopping.facade.member.menum.certificatesauth.MemberCertificatesTypeEnum;
import com.ph.shopping.facade.member.menum.paypwd.PayPwdEnum;
import com.ph.shopping.facade.member.service.IMemberCertificationService;
import com.ph.shopping.facade.member.service.IMemberService;
import com.ph.shopping.facade.member.service.IPayPasswordService;
import com.phshopping.api.appenum.AppResultEnum;
import com.phshopping.api.controller.dto.*;
import com.phshopping.api.controller.vo.AuthDetailVO;
import com.phshopping.api.entity.MemberInfo;
import com.phshopping.api.uitl.PasswordUtil;
import com.phshopping.api.uitl.UserCacheUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * @ClassName: AccountSecurityController
 * @Description:个人中心相关操作
 * @author: 李杰
 * @date: 2017年6月7日 下午5:10:32
 * @Copyright: 2017
 */
@RestController
@RequestMapping("api/personal/account")
public class AccountSecurityController {

	/** 日志对象 */
	private static final Logger logger = LoggerFactory.getLogger(AccountSecurityController.class);
	/** 会员相关服务 */
	@Reference(version = "1.0.0", retries = 0, timeout = 30000)
	private IMemberService memberService;
	/** 支付密码相关服务 */
	@Reference(version = "1.0.0", retries = 0)
	private IPayPasswordService payPasswordService;
	/** 实名认证相关服务 */
	@Reference(version = "1.0.0", retries = 0, timeout = 30000)
	private IMemberCertificationService cretificateAuthService;
	/** 短信发送服务 */
	@Autowired
	private ISmsDataService smsDataService;
	/** 数据服务 */
	private final AccountSecurityDataService dataService = new AccountSecurityDataService();
	@Autowired
	private SmsUtil smsUtil;

	/**
	 *
	 * @Title: updatePwdSubmit @Description: 修改登录密码 @param: @param
	 *         request @param: @param vo @param: @return @return:
	 *         Result @author：李杰 @throws
	 */
	@Token(key = "token", isCache = true, cacheType = CacheKeyTypeEnum.HASH)
	@RequestMapping(value = "/updateLoginPwd", method = RequestMethod.POST)
	@ResponseBody
	public Result updateLoginPwd(HttpServletRequest request,AppMemberPasswordDTO dto) {
		logger.info("修改登录密码处理参数：MemberPasswordDTO = {} ", JSON.toJSONString(dto));
		Result result = UserCacheUtil.getUser(dto.getToken());
		if (result.isSuccess()) {
			if (!PasswordUtil.verifyPwd(dto.getNewPassword())) {
				return ResultUtil.getResult(AppResultEnum.PASSWORD_FORMAT_FAIL);
			}
			// 短信验证
			result = yzSmsCode(dto.getTelPhone(),dto.getSmsCode());
			if (!result.isSuccess()) {
				return result;
			}
			MemberPasswordDTO rdto = new MemberPasswordDTO();
			BeanUtils.copyProperties(dto, rdto);
			result = memberService.updateMemberPassword(rdto);
			UserCacheUtil.removeUser(dto.getToken());
		}
		logger.info("修改登录密码处理返回数据：Result = {} ", JSON.toJSONString(result));
		return result;
	}

	/**
	 *
	 * @Title: setUpPayPwdSubmit @Description: 设置 支付密码 @param: @param
	 *         request @param: @param dto @param: @return @return:
	 *         Result @author：李杰 @throws
	 */
	@Token(key = "token", isCache = true, cacheType = CacheKeyTypeEnum.HASH)
	@RequestMapping(value = "/setUpPayPwdSubmit", method = RequestMethod.POST)
	@ResponseBody
	public Result setUpPayPwdSubmit(HttpServletRequest request,  AppPayPasswordDTO dto) {
		logger.info("设置支付密码处理参数：PayPasswordDTO = {} ", JSON.toJSONString(dto));
		Result result = UserCacheUtil.getUser(dto.getToken());
		if (result.isSuccess()) {
			MemberInfo memberInfo = (MemberInfo) result.getData();
			if (!PasswordUtil.verifyPwd(dto.getNewPassword())) {
				return ResultUtil.getResult(AppResultEnum.PASSWORD_FORMAT_FAIL);
			}
			// 短信验证

			result = yzSmsCode(dto.getTelPhone(),dto.getSmsCode());
			//result = verifySmsCode(dto.getTelPhone(), dto.getSmsCode(), SmsCodeType.SET_PAYPWD_VC);
			if (!result.isSuccess()) {
				return result;
			}
			PayPasswordAddDTO pdto = new PayPasswordAddDTO();
			pdto.setCreaterId(memberInfo.getId());
			pdto.setCustomerType(PayPwdEnum.MEMBER_PAY_PWD.getCode());
			pdto.setNewPassword(dto.getNewPassword());
			pdto.setTelPhone(dto.getTelPhone());
			pdto.setUserId(memberInfo.getId());
			result = payPasswordService.addPayPassword(pdto);
		}
		logger.info("设置支付密码处理返回数据：Result = {} ", JSON.toJSONString(result));
		return result;
	}

	/**
	 *
	 * @Title: updatePayPwdSubmit @Description: 修改支付密码 @param: @param
	 *         request @param: @param dto @param: @return @return:
	 *         Result @author：李杰 @throws
	 */
	@Token(key = "token", isCache = true, cacheType = CacheKeyTypeEnum.HASH)
	@RequestMapping(value = "/updatePayPwdSubmit", method = RequestMethod.POST)
	@ResponseBody
	public Result updatePayPwdSubmit(HttpServletRequest request,  AppPayPasswordDTO dto) {
		logger.info("修改支付密码处理参数：AppPasswordDTO = {} ", JSON.toJSONString(dto));
		Result result = UserCacheUtil.getUser(dto.getToken());
		if (result.isSuccess()) {
			MemberInfo memberInfo = (MemberInfo) result.getData();
			if (!PasswordUtil.verifyPwd(dto.getNewPassword())) {
				return ResultUtil.getResult(AppResultEnum.PASSWORD_FORMAT_FAIL);
			}

			// 短信验证
			result = yzSmsCode(dto.getTelPhone(),dto.getSmsCode());
			//result = verifySmsCode(dto.getTelPhone(), dto.getSmsCode(), SmsCodeType.UPDATE_PAYPWD_VC);
			if (!result.isSuccess()) {
				return result;
			}
			PayPasswordUpdateDTO pdto = new PayPasswordUpdateDTO();
			pdto.setCustomerType(PayPwdEnum.MEMBER_PAY_PWD.getCode());
			pdto.setNewPassword(dto.getNewPassword());
			pdto.setTelPhone(dto.getTelPhone());
			pdto.setUserId(memberInfo.getId());
			pdto.setUpdaterId(memberInfo.getId());
			result = payPasswordService.updatePayPassword(pdto);
		}
		logger.info("修改支付密码处理返回数据：Result = {} ", JSON.toJSONString(result));
		return result;
	}

	/**
	 *
	 * @Title: realNameAuth @Description: 实名认证 @param: @param
	 *         request @param: @param dto @param: @return @return:
	 *         Result @author：李杰 @throws
	 */
	@Token(key = "token", isCache = true, cacheType = CacheKeyTypeEnum.HASH)
	@RequestMapping(value = "/realNameAuth", method = RequestMethod.POST)
	public Result realNameAuth(HttpServletRequest request, AppCertificateAuthDTO dto) {
		logger.info("实名认证处理参数：CertificatesAuthDTO = {} ", JSON.toJSONString(dto));
		Result result = UserCacheUtil.getUser(dto.getToken());
		if (result.isSuccess()) {
			MemberInfo memberInfo = (MemberInfo) result.getData();
			CertificatesAuthDTO rdto = new CertificatesAuthDTO();
			rdto.setCertificatesCode(dto.getIdCode());
			rdto.setCertificatesName(dto.getName());
			rdto.setCertificatesType(MemberCertificatesTypeEnum.ID_CARD.getCode());
			rdto.setLoginUserId(memberInfo.getId());
			rdto.setUserId(dto.getUserId());
			result = cretificateAuthService.authentication(rdto);
			// 实名认证成功修改缓存数据
			if (result.isSuccess()) {
				UserCacheUtil.updateUser(dto.getToken());
			}
		}
		logger.info("实名认证返回数据：Result = {} ", JSON.toJSONString(result));
		return result;
	}

	/**
	 *
	 * @Title: getMemberInfo @Description:获取会员信息 @param: @param
	 *         request @param: @param token @param: @return @return:
	 *         Result @author：李杰 @throws
	 */
	@RequestMapping(value = "/getMemberInfo/{token}", method = RequestMethod.GET)
	public Result getMemberInfo(HttpServletRequest request, @PathVariable String token) {
		logger.info("获取会员信息 参数：token = " + token);
		return UserCacheUtil.getUser(token);
	}

	/**
	 *
	 * @Title: getAuthDetail @Description: 获取认证详情 @param: @param
	 *         request @param: @return @return: String @author：李杰 @throws
	 */
	@RequestMapping(value = "/getAuthDetail/{token}", method = RequestMethod.GET)
	public Result getAuthDetail(HttpServletRequest request, @PathVariable String token) {
		logger.info("获取认证详情 参数：token = " + token);
		Result result = UserCacheUtil.getUser(token);
		if (result.isSuccess()) {
			MemberInfo memberInfo = (MemberInfo) result.getData();
			result = cretificateAuthService.getCertificatesAuthDetailByCodeAndType(memberInfo.getIdCardNo(),
					MemberCertificatesTypeEnum.ID_CARD);

			// 封装返回数据
			result.setData(dataService.getAuthDetailVO(result));
		}
		logger.info("获取认证详情返回参数：result = {} ", JSON.toJSONString(result));
		return result;
	}

	/**
	 *
	 * @Title: saveHeadImage @Description: TODO:保存头像地址 @param: @param
	 *         request @param: @param dto @param: @return @return:
	 *         Result @author：李杰 @throws
	 */
	@Token(key = "token", isCache = true, cacheType = CacheKeyTypeEnum.HASH)
	@RequestMapping(value = "/saveHeadImage", method = RequestMethod.POST)
	public Result saveHeadImage(HttpServletRequest request, AppHeadImageDTO dto) {
		logger.info("保存头像地址处理参数：HeadImageDTO = {} ", JSON.toJSONString(dto));
		Result result = UserCacheUtil.getUser(dto.getToken());
		if (result.isSuccess()) {
			MemberInfo memberInfo = (MemberInfo) result.getData();
			MemberUpdateDTO rdto = new MemberUpdateDTO();
			rdto.setId(memberInfo.getId());
			rdto.setHeadImage(dto.getHeadImgUrl());
			result = memberService.updateMember(rdto);
		}
		logger.info("保存头像地址返回参数：result = {} ", JSON.toJSONString(result));
		return result;
	}
	//用户修改昵称
	@Token(key="token",isCache = true, cacheType = CacheKeyTypeEnum.HASH)
	@RequestMapping(value = "/updatenikename",method = RequestMethod.POST)
	@ResponseBody
	public Result updateNikename(AppNikeNameDTO dto){
		Result result = UserCacheUtil.getUser(dto.getToken());
		if (result.isSuccess()) {
			result = memberService.updateNikeName(dto.getMemberid(),dto.getNikeName());
		}
		return result;
	}



	/**
	 *
	 * @Title: isExistsPayPwd @Description: 判断是否存在支付密码 @param: @param
	 *         request @param: @param token @param: @return @return:
	 *         Result @author：李杰 @throws
	 */
	@RequestMapping(value = "/isExistsPayPwd/{token}", method = RequestMethod.GET)
	public Result isExistsPayPwd(HttpServletRequest request, @PathVariable String token) {
		logger.info("判断是否存在支付密码 参数：token = " + token);
		Result result = UserCacheUtil.getUser(token);
		if (result.isSuccess()) {
			MemberInfo memberInfo = (MemberInfo) result.getData();
			PayPasswordQueryDTO dto = new PayPasswordQueryDTO();
			dto.setUserId(memberInfo.getId());
			dto.setCustomerType(PayPwdEnum.MEMBER_PAY_PWD.getCode());
			result = payPasswordService.verifyPayPwdIsExists(dto);
		}
		logger.info("判断是否存在支付密码返回参数：result = {} ", JSON.toJSONString(result));
		return result;
	}

	/**
	 *
	 * @Title: verifySmsCode @Description:校验短信验证码 @param: @param
	 *         mobile @param: @param smsCode @param: @return @return:
	 *         boolean @author：李杰 @throws
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
	/*
	 * gaoge
	 * 验证验证码，
	 */
	//TODO
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

	/**
	 *
	 * @ClassName: AccountSecurityDataService
	 * @Description:个人中心相关数据服务
	 * @author: 李杰
	 * @date: 2017年6月19日 下午5:06:36
	 * @Copyright: 2017
	 */
	protected class AccountSecurityDataService {
		/**
		 *
		 * @Title: getAuthDetailVO @Description: 封装认证信息返回数据 @param: @param
		 *         result @param: @return @return:
		 *         AuthDetailVO @author：李杰 @throws
		 */
		public AuthDetailVO getAuthDetailVO(Result result) {
			if (null != result && result.isSuccess()) {
				Object obj = result.getData();
				if (obj instanceof CertificatesAuth) {
					AuthDetailVO vo = new AuthDetailVO();
					BeanUtils.copyProperties(obj, vo);
					return vo;
				}
			}
			return null;
		}
	}


}
