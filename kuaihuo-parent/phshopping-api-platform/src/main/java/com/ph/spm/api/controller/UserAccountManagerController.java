package com.ph.spm.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ph.base.BaseController;
import com.ph.shopping.common.core.config.pay.PayConfiguration;
import com.ph.shopping.common.util.core.ResultUtil;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.common.util.transform.MoneyTransUtil;
import com.ph.shopping.common.util.verifycode.VerifyUtil;
import com.ph.shopping.facade.permission.vo.SessionUserVO;
import com.ph.shopping.facade.spm.constant.ChargeConstant;
import com.ph.shopping.facade.spm.dto.UserBalanceTradeDTO;
import com.ph.shopping.facade.spm.dto.UserChargeDTO;
import com.ph.shopping.facade.spm.dto.UserDrawCashDTO;
import com.ph.shopping.facade.spm.entity.ManageBankCardInfo;
import com.ph.shopping.facade.spm.entity.UserCharge;
import com.ph.shopping.facade.spm.exception.UserAccountExceptionEnum;
import com.ph.shopping.facade.spm.service.IManageBankCardService;
import com.ph.shopping.facade.spm.service.IUserBalanceService;
import com.ph.shopping.facade.spm.service.IUserChargeService;
import com.ph.shopping.facade.spm.service.IUserDrawCashService;
import com.ph.shopping.facade.spm.vo.BalanceVO;
import com.ph.shopping.facade.spm.vo.TransCodeVO;

/**
 * @ClassName: UserChargeRecordController
 * @Description:后台用户充值Controller
 * @author 王强
 * @date 2017年5月16日 下午5:52:50
 */

/**
 * 
 * @ClassName: UserAccountManagerController
 * @Description: 用户余额
 * @author 王强
 * @date 2017年5月22日 上午11:41:39
 */
@Controller
@RequestMapping("web/useraccountmanager")
public class UserAccountManagerController extends BaseController {

	@Reference(version = "1.0.0")
	private IUserChargeService iUserChargeRecordService;

	@Reference(version = "1.0.0")
	private IUserDrawCashService iUserDrawCashService;

	@Reference(version = "1.0.0")
	private IManageBankCardService iBankCardService;

	@Reference(version = "1.0.0")
	private IUserBalanceService iUserBalance;

	@Autowired
	private PayConfiguration payConfiguration;

	/**
	 * 
	 * @Title: toOnLineCharge
	 * @Description: 跳转到在线充值页面
	 * @author 王强
	 * @date 2017年5月26日 上午10:18:23
	 * @return
	 */
	@RequestMapping(value = "toonlinecharge", method = RequestMethod.GET)
	public String toOnLineCharge() {
		return "account/account_pay";
	}

	@RequestMapping(value = "toecopaycharge", method = RequestMethod.POST)
	public ModelAndView toEcoPayCharge(String amount) {
		ModelAndView mv = new ModelAndView("account/account_payEco");
		mv.addObject("amount", amount);
		return mv;
	}
	
	/**
	 * 
	 * @Title: toDrawCash
	 * @Description: 跳转用户提现页面
	 * @author 王强
	 * @date 2017年5月26日 下午4:17:56
	 * @return
	 */
	@RequestMapping(value = "todrawcash", method = RequestMethod.GET)
	public ModelAndView toDrawCash() {
		ModelAndView mv = new ModelAndView("account/account_carry");
		try {
			ManageBankCardInfo bankCardInfo = new ManageBankCardInfo();
			SessionUserVO userVO = getLoginUser();

			Result result = new Result();
			bankCardInfo.setUserId(userVO.getId());
			bankCardInfo.setTelPhone(getLoginUser().getTelphone());
			result = iBankCardService.getBindCardInfo(bankCardInfo);
			Result result2 = iUserBalance.getUserBalance(userVO.getId());
			BalanceVO balanceVO = (BalanceVO) result2.getData();
			if (result.getData() == null) {
				mv.addObject("bindCardInfo", bankCardInfo);
			} else {
				mv.addObject("bindCardInfo", result.getData());
			}

			if (balanceVO != null) {
				mv.addObject("userBalance", MoneyTransUtil.stringFormat(balanceVO.getScore()) + " 元");
			} else {
				mv.addObject("userBalance", "0.00 元");
			}
			return mv;
		} catch (Exception e) {
			logger.error("跳转提现页面异常", e);
			return mv;
		}
	}

	/**
	 * 
	 * @Title: addUserChargeRescord
	 * @Description:新增用户充值记录
	 * @author 王强
	 * @date 2017年5月17日 下午1:51:54
	 * @param userChargeDTO
	 * @return
	 */
	@RequestMapping(value = "insertuserchargerecord", method = RequestMethod.POST)
	@ResponseBody
	public Result addUserChargeRecord(UserChargeDTO userChargeDTO) throws Exception {
		SessionUserVO userVO = getLoginUser();
		UserCharge userCharge = new UserCharge();
		userCharge.setChargeStatus((byte) ChargeConstant.CHARGING);
		userCharge.setUserId(userVO.getId());
		userCharge.setCreaterId(userChargeDTO.getUserId());
		//易联支付路径
		//userCharge.setPayUrl(payConfiguration.getEcoPay());
		//北京通用支付路径
		userCharge.setPayUrl(payConfiguration.getBjcompay());
		if (VerifyUtil.listIsNotNull(userVO.getSessionRoleVo())) {
			userCharge.setUserType(userVO.getSessionRoleVo().get(0).getRoleCode());
		}
		userCharge.setScoreTrans(userChargeDTO.getScore());
		userCharge.setScore(MoneyTransUtil.transDoubleToBigDecimal(userChargeDTO.getScore()));
		userCharge.setCreaterId(userVO.getId());
		//打印封装json结果
		Result result = iUserChargeRecordService.addUserChargeRecord(userCharge);
		return iUserChargeRecordService.addUserChargeRecord(userCharge);
	}

	/**
	 * 
	 * @Title: addUserDrawCashRecord
	 * @Description: 新增提现记录x
	 * @author 王强
	 * @date 2017年5月19日 上午9:25:26
	 * @param userDrawCashDTO
	 * @return
	 */
	@RequestMapping(value = "adduserdrawcashrecord", method = RequestMethod.POST)
	@ResponseBody
	public Result addUserDrawCashRecord(UserDrawCashDTO userDrawCashDTO) {
		try {
			if (userDrawCashDTO.getDrawCashScore() instanceof Long) {
				SessionUserVO userVO = getLoginUser();

				userDrawCashDTO.setHandingCharge(5l);// 手续费
				// 比如输入100,实际提取95
				userDrawCashDTO.setScore(userDrawCashDTO.getDrawCashScore() - userDrawCashDTO.getHandingCharge());// 实际所得金额
				userDrawCashDTO.setUserId(userVO.getId());
				if (VerifyUtil.listIsNotNull(userVO.getSessionRoleVo())) {
					userDrawCashDTO.setUserType(userVO.getSessionRoleVo().get(0).getRoleCode());
				}
				return iUserDrawCashService.addDrawCashRecord(userDrawCashDTO);
			} else {
				return ResultUtil.getResult(UserAccountExceptionEnum.BALANCE_INTEGER);
			}
		} catch (Exception e) {
			logger.error("提现失败", e);
			return ResultUtil.getResult(UserAccountExceptionEnum.DRAWCASH_EXCEPTION);
		}
	}

	/**
	 * 
	 * @Title: toUserBalanceTradePage
	 * @Description: 跳转到用户余额流水列表
	 * @author 王强
	 * @date 2017年6月2日 下午1:52:04
	 * @return
	 */
	@RequestMapping(value = "touserbalancetradepage", method = RequestMethod.GET)
	public ModelAndView toUserBalanceTradePage() {
		ModelAndView mv = new ModelAndView("account/account_list");
		SessionUserVO userVO = getLoginUser();
		Result res = iUserBalance.getUserBalance(userVO.getId());
		BalanceVO balanceVO = (BalanceVO) res.getData();
		if (balanceVO != null) {
			mv.addObject("userBalance", MoneyTransUtil.stringFormat(balanceVO.getScore()) + " 元");
		} else {
			mv.addObject("userBalance", "0.00" + " 元");
		}
		return mv;
	}

	/**
	 *
	 * @Title: toUserBalanceTradePage
	 * @Description: 跳转到用户余额流水列表(批发代理)
	 * @author gaoge
	 * @date 2017年6月2日 下午1:52:04
	 * @return
	 */
	@RequestMapping(value = "touserbalancetradepagepf", method = RequestMethod.GET)
	public ModelAndView toUserBalanceTradePagePF() {
		ModelAndView mv = new ModelAndView("account/account_list_pf");
		SessionUserVO userVO = getLoginUser();
		Result res = iUserBalance.getUserBalance(userVO.getId());
		BalanceVO balanceVO = (BalanceVO) res.getData();
		if (balanceVO != null) {
			mv.addObject("userBalance", MoneyTransUtil.stringFormat(balanceVO.getScore()) + " 元");
		} else {
			mv.addObject("userBalance", "0.00" + " 元");
		}
		return mv;
	}


	/**
	 * 
	 * @Title: getUserBalanceTradeList
	 * @Description: 查询余额流水列表
	 * @author 王强
	 * @date 2017年6月2日 下午1:40:41
	 * @param balanceTradeDTO
	 * @return
	 */
	@RequestMapping(value = "getuserbalancetradelist", method = RequestMethod.GET)
	@ResponseBody
	public Result getUserBalanceTradeList(UserBalanceTradeDTO balanceTradeDTO) {
		SessionUserVO userVO = getLoginUser();
		balanceTradeDTO.setUserId(userVO.getId());
		return iUserBalance.getUserBalanceTrade(balanceTradeDTO);
	}

	/**
	 * 
	 * @Title: getTransCode
	 * @Description: 查询交易码
	 * @author 王强
	 * @date 2017年6月2日 下午4:09:00
	 * @return
	 */
	@RequestMapping(value = "gettranscode", method = RequestMethod.GET)
	@ResponseBody
	public List<TransCodeVO> getTransCode() {
		return iUserBalance.getTransCodes();
	}
}
