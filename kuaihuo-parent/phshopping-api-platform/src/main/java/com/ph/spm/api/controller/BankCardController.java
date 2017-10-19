package com.ph.spm.api.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.ph.base.BaseController;
import com.ph.shopping.common.core.dto.CheckDTO;
import com.ph.shopping.common.core.other.BankCardCheckService;
import com.ph.shopping.common.core.other.sms.handle.ISmsDataService;
import com.ph.shopping.common.core.util.SmsUtil;
import com.ph.shopping.common.util.core.RespCode;
import com.ph.shopping.common.util.core.ResultUtil;
import com.ph.shopping.common.util.http.IPUtil;
import com.ph.shopping.common.util.other.bankcard.BankCardUtil;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.member.menum.MemberResultEnum;
import com.ph.shopping.facade.member.service.IBankCodenameDataService;
import com.ph.shopping.facade.member.vo.BankCodenameDataVO;
import com.ph.shopping.facade.permission.vo.SessionUserVO;
import com.ph.shopping.facade.spm.dto.ManageBankCardDTO;
import com.ph.shopping.facade.spm.entity.ManageBankCardInfo;
import com.ph.shopping.facade.spm.exception.UserAccountExceptionEnum;
import com.ph.shopping.facade.spm.service.IManageBankCardService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * 
 * @ClassName: BankCardController
 * @Description:银行卡Controller
 * @author 王强
 * @date 2017年6月16日 上午10:00:12
 */
@Controller
@RequestMapping("web/bankcard")
public class BankCardController extends BaseController {

	@Reference(version = "1.0.0")
	private IManageBankCardService iBankCardService;

	@Reference(version = "1.0.0")
	private IBankCodenameDataService iBankCodenameDataService;

	@Autowired
	private ISmsDataService iSmsDataService;

	@Autowired
	private BankCardCheckService bankCardCheckService;

	@Autowired
	private SmsUtil smsUtil;

	/**
	 * ======================================页面跳转===============================
	 */
	/**
	 * 
	 * @Title: toBankCardPage
	 * @Description: 跳转到银行卡页面
	 * @author 王强
	 * @date 2017年5月24日 下午5:11:49
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/tobankcardpage", method = RequestMethod.GET)
	public ModelAndView toBankCardPage() throws Exception {
		ModelAndView mv = new ModelAndView("account/account_bank");
		ManageBankCardInfo bankCardInfo = new ManageBankCardInfo();
		SessionUserVO userVO = getLoginUser();
		if (userVO != null) {
			bankCardInfo.setUserId(getLoginUser().getId());
		} else {
			throw new Exception("登录超时");
		}

		mv.addObject("roleCode", userVO.getSessionRoleVo().get(0).getRoleCode());
		mv.addObject("telPhone", userVO.getTelphone());
		Result res = iBankCardService.getBindCardInfo(bankCardInfo);
		if (res.getData() == null) {
			mv.addObject("bindCardInfo", bankCardInfo);
		} else {
			mv.addObject("bindCardInfo", res.getData());

		}

		return mv;
	}

	/**
	 * ======================================数据操作===============================
	 */
	/**
	 * 
	 * @Title: getBindCardInfo
	 * @Description: 获取用户绑定的银行卡信息
	 * @author 王强
	 * @date 2017年5月24日 下午5:12:11
	 * @param bankCardInfo
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getbindcardinfo", method = RequestMethod.GET)
	@ResponseBody
	public Result getBindCardInfo(@ModelAttribute ManageBankCardInfo bankCardInfo, HttpServletRequest request) {
		bankCardInfo.setUserId(getLoginUser().getId());
		bankCardInfo.setTelPhone(getLoginUser().getTelphone());
		return iBankCardService.getBindCardInfo(bankCardInfo);
	}

	/**
	 * 
	 * @Title: bindCard
	 * @Description: 绑定银行卡
	 * @author 王强
	 * @date 2017年5月24日 下午5:12:27
	 * @param bankCardDTO
	 * @return
	 */
	@RequestMapping(value = "/bindcard", method = RequestMethod.POST)
	@ResponseBody
	public Result bindCard(ManageBankCardDTO bankCardDTO, HttpServletRequest request) {

		SessionUserVO userVO = getLoginUser();
		
		String requestIP = IPUtil.getIpAddress(request);//获取ip地址

		// 三要素检验
		String idCardNo = bankCardDTO.getIdCardNo();
		String cardNo = bankCardDTO.getCardNo();
		String ownerName = bankCardDTO.getOwnerName();
		if (idCardNo == null || cardNo == null || ownerName == null) {
			return ResultUtil.getResult(UserAccountExceptionEnum.NEED_PARAMS);
		}
		//认证用户名，身份证，银行卡号
		if (idCardNo != null && cardNo != null && ownerName != null) {
//			BankCardBindInfoDTO bankCardBindInfoDTO = new BankCardBindInfoDTO();
//			bankCardBindInfoDTO.setUserId(userVO.getId());
//			bankCardBindInfoDTO.setBankCardNo(cardNo);
//			bankCardBindInfoDTO.setBindPhone(userVO.getTelphone());
//			bankCardBindInfoDTO.setBankCodenameDataId(bankCardDTO.getBankCodenameDataId());
//			bankCardBindInfoDTO.setOwnerName(ownerName);
//			bankCardBindInfoDTO.setIdCardNo(idCardNo);
//			bankCardBindInfoDTO.setCreateIp(requestIP);
//			bankCardBindInfoDTO.setLoginUserId(userVO.getId());
			if (!bankCardCheckService.bankCardAuth(idCardNo, cardNo, ownerName)) {
				return ResultUtil.getResult(UserAccountExceptionEnum.BANK_CHECK_FAIL);
			}
		}

		// 校验验证码
		CheckDTO checkDTO = new CheckDTO();
		checkDTO.setCodeType("Fr170002");
		checkDTO.setCode(bankCardDTO.getVerificationCode());
		checkDTO.setPhone(userVO.getTelphone());
		Result check = smsUtil.check(checkDTO);
		/*SmsSendData sendData = new SmsSendData();
		sendData.setMobile(userVO.getTelphone());
		byte b = userVO.getSessionRoleVo().get(0).getRoleCode();
		if (b == (byte) 2) {
			sendData.setSourceEnum(SmsSourceEnum.SUPPLIER);
		} else if (b == (byte) 3 || b == (byte) 4 || b == (byte) 5) {
			sendData.setSourceEnum(SmsSourceEnum.AGENT);
		} else if (b == (byte) 6) {
			sendData.setSourceEnum(SmsSourceEnum.MERCHANT);
		}
		sendData.setType(SmsCodeType.BIND_MEMBERBANK_VC);
		sendData.setSmsCode(bankCardDTO.getVerificationCode());
		Result result = iSmsDataService.checkSmsCodeByMobile(sendData);*/
		if ("1".equals(check.getCode())) {
			ManageBankCardInfo bankCardInfo = new ManageBankCardInfo();
			try {
				bankCardInfo.setCardNo(cardNo);
				bankCardInfo.setIdCardNo(idCardNo);
				bankCardInfo.setOwnerName(ownerName);
				bankCardInfo.setBankName(bankCardDTO.getBankName());
			} catch (Exception e) {
				logger.error("复制实体出错", e);
			}
			return iBankCardService.bindCard(bankCardInfo, userVO);
		} else {
			return ResultUtil.setResult(false,check.getMessage());
		}
	}

	/**
	 * 
	 * @Title: unBindCard
	 * @Description: 解绑银行卡
	 * @author 王强
	 * @date 2017年5月24日 下午5:12:39
	 * @param bankCardInfo
	 * @param verificationCode
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/unbindcard", method = RequestMethod.POST)
	@ResponseBody
	public Result unBindCard(@ModelAttribute ManageBankCardInfo bankCardInfo, String verificationCode,
			HttpServletRequest request) {
		SessionUserVO userVO = getLoginUser();
		bankCardInfo.setTelPhone(userVO.getTelphone());
		// 校验验证码
		/*SmsSendData sendData = new SmsSendData();
		sendData.setMobile(getLoginUser().getTelphone());
		byte b = userVO.getSessionRoleVo().get(0).getRoleCode();
		if (b == (byte) 2) {
			sendData.setSourceEnum(SmsSourceEnum.SUPPLIER);
		} else if (b == (byte) 3 || b == (byte) 4 || b == (byte) 5) {
			sendData.setSourceEnum(SmsSourceEnum.AGENT);
		} else if (b == (byte) 6) {
			sendData.setSourceEnum(SmsSourceEnum.MERCHANT);
		}
		sendData.setType(SmsCodeType.BIND_MEMBERBANK_VC);
		sendData.setSmsCode(verificationCode);*/
		//Result result = iSmsDataService.checkSmsCodeByMobile(sendData);
		// 校验验证码
		CheckDTO checkDTO = new CheckDTO();
		checkDTO.setCodeType("Fr170002");
		checkDTO.setCode(verificationCode);
		checkDTO.setPhone(userVO.getTelphone());
		Result check = smsUtil.check(checkDTO);
		if ("1".equals(check)) {
			return iBankCardService.unBindCard(bankCardInfo, userVO);
		} else {
			return ResultUtil.setResult(false,check.getMessage());
		}
		// return iBankCardService.unBindCard(bankCardInfo, userVO);
	}

	/**
	 * 
	 * @Title: getBankName
	 * @Description: 根据银行卡号自动获取银行名字
	 * @author 王强
	 * @date 2017年5月24日 下午5:12:50
	 * @param bankCardInfo
	 * @return
	 */
	@RequestMapping(value = "/getbankname", method = RequestMethod.POST)
	@ResponseBody
	public Result getBanCardName(HttpServletRequest request, ManageBankCardDTO bankCardDTO) {
		String cardNo = bankCardDTO.getCardNo();
		logger.debug("根据银行卡卡号获取银行卡名称参数 bankCardNo：" + cardNo);
		// JSONObject json = JSON.parseObject(cardNo);
		// Object param = json.get("bankCardNo");
		// cardNo = param == null ? null : param.toString();
		if (StringUtils.isNotBlank(cardNo)) {
			if (BankCardUtil.isNoexists(cardNo)) {
				return ResultUtil.getResult(MemberResultEnum.MEMBER_NO_DATA);
			}
			String cardName = BankCardUtil.getBankName(cardNo);
			if (StringUtils.isNotBlank(cardName)) {
				String[] codeNames = cardName.split(",");
				BankCodenameDataVO vo = new BankCodenameDataVO();
				vo.setId(Long.valueOf(codeNames[0]));
				vo.setBankName(codeNames[1]);
				return ResultUtil.getResult(RespCode.Code.SUCCESS, vo);

			}
			final Result dresult = iBankCodenameDataService.getBankCodenameDataDetailByCode(cardNo);
			logger.debug("根据银行卡卡号获取银行卡名称返回数据：Result = {} ", JSON.toJSONString(dresult));
			boolean flag = false;
			if (dresult.isSuccess()) {
				Object obj = dresult.getData();
				if ((flag = obj instanceof BankCodenameDataVO)) {
					BankCodenameDataVO vo = (BankCodenameDataVO) obj;
					BankCardUtil.setCardName(cardNo, vo.getBankName(), vo.getId());
					return ResultUtil.getResult(RespCode.Code.SUCCESS, vo);
				}
			}
			if (!flag) {
				BankCardUtil.putNoexists(cardNo);
			}
		}
		return ResultUtil.getResult(MemberResultEnum.MEMBER_NO_DATA);
	}

	/**
	 * 
	 * @Title: sendValidCode
	 * @Description: 发送验证码
	 * @author 王强
	 * @date 2017年5月24日 下午5:13:16
	 * @return
	 */
	// @RequestMapping(value = "/sendvalidcode", method = RequestMethod.GET)
	// @ResponseBody
	// public Result sendValidCode() {
	// SessionUserVO userVO = getLoginUser();
	// // 封装验证码数据
	// SmsSendData sendData = new SmsSendData();
	// sendData.setMobile(getLoginUser().getTelphone());
	// byte b = userVO.getSessionRoleVo().get(0).getRoleCode();
	// if(b == (byte) 2) {
	// sendData.setSourceEnum(SmsSourceEnum.SUPPLIER);
	// } else if(b == (byte) 3 || b == (byte) 4 || b == (byte) 5) {
	// sendData.setSourceEnum(SmsSourceEnum.AGENT);
	// } else if(b == (byte) 6) {
	// sendData.setSourceEnum(SmsSourceEnum.MERCHANT);
	// }
	// sendData.setType(SmsCodeType.BIND_MEMBERBANK_VC);
	// sendData.setSmsCode(SmsCodeUtil.getSmsCode());
	// return iSmsDataService.sendSmsCodeByMobile(sendData);
	// }
}
