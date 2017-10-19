/**
 * @Title:  ScoreController.java
 * @Package com.phshopping.api.controller.personal.score
 * @Description:    TODO(用一句话描述该文件做什么)
 * @author: 李杰
 * @date:   2017年5月17日 上午11:03:21
 * @version V1.0
 * @Copyright: 2017
 */
package com.phshopping.api.controller.personal.score;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ph.shopping.common.core.config.PuhuiConfig;
import com.ph.shopping.common.core.customenum.CacheKeyTypeEnum;
import com.ph.shopping.common.core.customenum.SmsCodeType;
import com.ph.shopping.common.core.customenum.SmsSourceEnum;
import com.ph.shopping.common.core.dto.CheckDTO;
import com.ph.shopping.common.core.other.annotation.Token;
import com.ph.shopping.common.core.other.sms.data.SmsSendData;
import com.ph.shopping.common.core.other.sms.handle.ISmsDataService;
import com.ph.shopping.common.core.util.SmsUtil;
import com.ph.shopping.common.util.container.ContainerUtil;
import com.ph.shopping.common.util.core.RespCode;
import com.ph.shopping.common.util.core.ResultUtil;
import com.ph.shopping.common.util.date.DoubleUtils;
import com.ph.shopping.common.util.date.SyncDateUtil;
import com.ph.shopping.common.util.http.IPUtil;
import com.ph.shopping.common.util.page.PageBean;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.common.util.rsa.RSACommonUtils;
import com.ph.shopping.common.util.transform.MoneyTransUtil;
import com.ph.shopping.facade.member.dto.MemberCashDTO;
import com.ph.shopping.facade.member.dto.PayPasswordQueryDTO;
import com.ph.shopping.facade.member.menum.paypwd.PayPwdEnum;
import com.ph.shopping.facade.member.service.IMemberBankCardBindService;
import com.ph.shopping.facade.member.service.IMemberCashService;
import com.ph.shopping.facade.member.service.IPayPasswordService;
import com.ph.shopping.facade.member.vo.MemberInfoByCashVO;
import com.ph.shopping.facade.pay.service.ICashService;
import com.ph.shopping.facade.score.request.QuerySingleScoreInfoDTO;
import com.ph.shopping.facade.score.request.ScorePageDTO;
import com.ph.shopping.facade.score.senum.ScoreTypeEnum;
import com.ph.shopping.facade.score.service.IScoreService;
import com.ph.shopping.facade.score.vo.MemberScoreShopVO;
import com.ph.shopping.facade.score.vo.MemberScoreVO2;
import com.ph.shopping.facade.score.vo.MemberSingleScoreVO;
import com.phshopping.api.appenum.AppResultEnum;
import com.phshopping.api.appenum.AppScoreQueryEnum;
import com.phshopping.api.controller.dto.AppCashDTO;
import com.phshopping.api.controller.dto.AppScoreQueryDTO;
import com.phshopping.api.controller.vo.IntegralDrawVO;
import com.phshopping.api.controller.vo.IntegralVO;
import com.phshopping.api.controller.vo.MemberScoreVO;
import com.phshopping.api.controller.vo.MyScoreVO;
import com.phshopping.api.entity.MemberInfo;
import com.phshopping.api.uitl.UserCacheUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @ClassName:  ScoreController
 * @Description:会员积分相关操作
 * @author: 李杰
 * @date:   2017年5月17日 上午11:03:21
 * @Copyright: 2017
 */
@RestController
@RequestMapping("api/personal/score")
public class ScoreController {

	private static final Logger logger = LoggerFactory.getLogger(ScoreController.class);

	@Reference(version = "1.0.0")
    private IMemberCashService memberCashService;

	@Reference(version = "1.0.0")
	private IScoreService scoreService;

    @Reference(version = "1.0.0")
    private ICashService cashService;

    @Autowired
	private ISmsDataService smsDataService;

    @Reference(version = "1.0.0")
	private IMemberBankCardBindService bankCardBindService;

    /**支付密码相关服务*/
	@Reference(version = "1.0.0", retries = 0)
	private IPayPasswordService payPasswordService;

	@Autowired
	private PuhuiConfig puhuiConfig;

	@Autowired
	private SmsUtil smsUtil;

	/**
	 * 积分相关数据服务
	 */
	private final ScoreDataService dataService = new ScoreDataService();


	@RequestMapping("/callbackmsg")
	public @ResponseBody String returnMsg(Model model, HttpServletRequest request, HttpServletResponse response)throws IOException {
        logger.info("*******pay提现回调*******");
		String orderNum2 = request.getParameter("shen");
		String payStatus2 = request.getParameter("hui");  //2 成功  3 失败
		String money2 = request.getParameter("yuan");
		logger.info("pay提现回调返回加密参数:orderNum:{},payStatus:{},money:{}",orderNum2,payStatus2,money2);
		if(orderNum2 == null || payStatus2 == null || money2 == null){
			return "NullError";
		}
		String orderNum = RSACommonUtils.decryptByPublicKey(orderNum2, RSACommonUtils.CharSet.UTF8);
		String payStatus = RSACommonUtils.decryptByPublicKey(payStatus2, RSACommonUtils.CharSet.UTF8);
		String money = RSACommonUtils.decryptByPublicKey(money2, RSACommonUtils.CharSet.UTF8);
		logger.info("pay提现回调返回解密参数:orderNum:{},payStatus:{},money:{}",orderNum,payStatus,money);
		Map<String, String> map = new HashMap<>();
		map.put("orderNum", orderNum);
		map.put("payStatus", payStatus);
		map.put("money", money);
		String call = cashService.verifySingNotifyForYPay(map);
		if("success".equals(call)){
			logger.info("订单号"+orderNum2+"回调方法处理完毕，返回的字符串"+call+"通知pay收到回调");
			return "success";
		}
		return "error";
	}

	/**
	 * 我的收入
	 */
	@Token(key = "token", isCache = true, cacheType = CacheKeyTypeEnum.HASH)
	@RequestMapping(value = "/getmembersharescore/{token}",method = RequestMethod.POST)
	public Result getMemberShareScore(@PathVariable String token) {

		logger.info("我的收入信息参数 token:{}", token);
		Result result = UserCacheUtil.getUser(token);
		if (result.isSuccess()) {
			MemberInfo memberInfo = (MemberInfo) result.getData();
			MemberScoreVO vo = new MemberScoreVO();
			MemberScoreVO2 ms = scoreService.getMemberScore(memberInfo.getId());
			if (ms == null) {
				vo.setIsExistPayPwd(false);
				vo.setIsBindBank(false);
				vo.setIsExistPayPwd(false);
				vo.setShareMemberScore("0.00");
				vo.setShareMerchantScore("0.00");
				vo.setTiantian("0.00");
				vo.setGuagua("0.00");
				vo.setStoreManagerScore("0.00");
			} else {

				final Long shareMemberScore = ms.getShareMemberScore();
				final Long shareMerchantScore = ms.getShareMerchantScore();

				if (ms.getBindStatus() == 0) {
					vo.setIsBindBank(false);
				} else {
					vo.setIsBindBank(true);
				}

				if (ms.getPayPwd() == null || ms.getPayPwd().equals("") || "null".equals(ms.getPayPwd())) {
					vo.setIsExistPayPwd(false);
				} else {
					vo.setIsExistPayPwd(true);
				}
				Byte isStorageManager= Objects.isNull(memberInfo.getIsStoreManager())?Byte.valueOf("0"):memberInfo.getIsStoreManager();
				vo.setIsStoreManager(String.valueOf(isStorageManager));
				vo.setStoreManagerScore(DoubleUtils.formatRound(MoneyTransUtil.transBigdecimalToDouble(new BigDecimal(ms.getStoreManagerScore())), 2));
				vo.setShareMemberScore(DoubleUtils.formatRound(MoneyTransUtil.transBigdecimalToDouble(new BigDecimal(shareMemberScore)), 2));
				vo.setShareMerchantScore(DoubleUtils.formatRound(MoneyTransUtil.transBigdecimalToDouble(new BigDecimal(shareMerchantScore)), 2));
				RestTemplate restTemplate = new RestTemplate();
				MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
				map.add("token", memberInfo.getTokenToMobile());
				String tt = "0.00";
				String gg = "0.00";
				String response = restTemplate.postForObject(puhuiConfig.getSelectScore(), map, String.class);
				logger.info("普惠积分返回结果:{}",response);
				if (org.apache.commons.lang3.StringUtils.isNotBlank(response)) {
					JSONObject jsonData = JSON.parseObject(response);
					if (Objects.nonNull(jsonData)) {
						String code = jsonData.getString("code");
						if ("200".equals(code)){
							JSONObject data = jsonData.getJSONObject("data");
							tt = data.getString("tt");
							gg = data.getString("gg");
						}
					}
				}
				vo.setGuagua(gg);
				vo.setTiantian(tt);
				result.setData(vo);
			}
			logger.info("我的收入接口返回值,result={}", JSON.toJSONString(result));
		}
	    return result;

	}

	/**
	 * 推广积分明细
	 *
	 */
	@Token(key = "token", isCache = true, cacheType = CacheKeyTypeEnum.HASH)
	@RequestMapping(value = "/getsharescoredetail",method = RequestMethod.POST)
	public Result getShareScoreDetail(String token,int transCode,int pageNo,int pageSize){
		logger.info("获取推广商户积分明细参数token={},transCode={}",token,transCode);
		Result result = UserCacheUtil.getUser(token);
		if (result.isSuccess()) {
			MemberInfo memberInfo = (MemberInfo) result.getData();
			result = scoreService.getShareMerchantScoreDetail(memberInfo.getId(),transCode,pageNo,pageSize);
		}
		logger.info("获取推广商户积分明细返回值,result={}", JSON.toJSONString(result));
		return result;
	}


	/**
	 * 推广提现
	 */
	@Token(key = "token", isCache = true, cacheType = CacheKeyTypeEnum.HASH)
	@RequestMapping(value = "/sharecash", method = RequestMethod.POST)
	public Result shareMemberCash(AppCashDTO dto, HttpServletRequest request) {
		logger.info("积分提现参数，CashDTO={}", JSON.toJSONString(dto));
		Result result = UserCacheUtil.getUser(dto.getToken());
		if (result.isSuccess()) {
			//短信验证
			CheckDTO checkDTO = new CheckDTO();
			checkDTO.setPhone(dto.getTelPhone());
			checkDTO.setCode(dto.getSmsCode());
			checkDTO.setCodeType("Fr170001");
			Result sendResult = smsUtil.check(checkDTO);
			if (!"1".equals(sendResult.getCode())) {
				return ResultUtil.setResult(false,sendResult.getMessage());
			}
			MemberCashDTO cashDto = new MemberCashDTO();
			cashDto.setCashScore(dto.getCashScore());
			cashDto.setId(dto.getMemberId());
			cashDto.setMsgCode(dto.getSmsCode());
			cashDto.setPayPwd(dto.getPayPwd());
			cashDto.setRequestIP(IPUtil.getIpAddress(request));
			cashDto.setCashType(Integer.parseInt(dto.getTransCode()));

			result = memberCashService.memberCash(cashDto);
			if(result.isSuccess()){
				result.setMessage("操作成功，请注意查收");
			}
		}
		logger.info("积分提现返回参数  Result = {}", JSON.toJSONString(result));
		return result;
	}
	/**
	 * 消费红利提现
	 */
	@Token(key = "token", isCache = true, cacheType = CacheKeyTypeEnum.HASH)
	@RequestMapping(value = "giftcash",method = RequestMethod.POST)
	public Result giftCash(AppCashDTO dto, HttpServletRequest request){
		logger.info("积分提现参数，CashDTO={}", JSON.toJSONString(dto));
		Result result = UserCacheUtil.getUser(dto.getToken());
		if (result.isSuccess()) {
			//短信验证
			CheckDTO checkDTO = new CheckDTO();
			checkDTO.setPhone(dto.getTelPhone());
			checkDTO.setCode(dto.getSmsCode());
			checkDTO.setCodeType("Fr170001");
			Result sendResult = smsUtil.check(checkDTO);
			if (!"1".equals(sendResult.getCode())) {
				return ResultUtil.setResult(false,sendResult.getMessage());
			}
			MemberCashDTO cashDto = new MemberCashDTO();
			cashDto.setCashScore(dto.getCashScore());
			cashDto.setId(dto.getMemberId());
			cashDto.setMsgCode(dto.getSmsCode());
			cashDto.setPayPwd(dto.getPayPwd());
			cashDto.setRequestIP(IPUtil.getIpAddress(request));
			cashDto.setCashType(Integer.parseInt(dto.getTransCode()));
			result = memberCashService.memberGiftCash(cashDto,dto.getMemberToken());
			if(result.isSuccess()){
				result.setMessage("操作成功，请注意查收");
			}
		}
		logger.info("积分提现返回参数  Result = {}", JSON.toJSONString(result));
		return result;
	}

	/**
	 *
	 * @Title: getMemberScore
	 * @Description: 获
	 * @param: @param request
	 * @param: @param token
	 * @param: @return
	 * @return: Result
	 * @author：李杰
	 * @throws
	 */
	@RequestMapping(value = "/getMemberScore/{token}", method = RequestMethod.GET)
	public Result getMemberScore(HttpServletRequest request, @PathVariable String token) {
		logger.info("获取会员积分信息参数 token ： " + token);
		Result result = UserCacheUtil.getUser(token);
		if (result.isSuccess()) {
			MemberInfo memberInfo = (MemberInfo) result.getData();
			MemberScoreVO vo = new MemberScoreVO();
			vo.setIsBindBank(isBindBank(memberInfo.getId()));
			vo.setIsExistPayPwd(isExistPayPwd(memberInfo.getId()));
			MemberScoreVO2 ms = getMemberScore(memberInfo.getId());
			final Long enableScore = ms.getEnablescore();
			final Long standbyscore = ms.getStandbyscore();
			vo.setEnablescore(MoneyTransUtil.formatBigDecimalByDivisFiveStr(new BigDecimal(enableScore)));
			vo.setStandbyscore(MoneyTransUtil.formatBigDecimalByDivisFiveStr(new BigDecimal(standbyscore)));
			vo.setDayDrawScore(getDayCashScore(memberInfo.getId()));
			result.setData(vo);
		}
		logger.info("获取会员积分接口返回值，result={}", JSON.toJSONString(result));
		return result;
	}


	/**
	 *
	 * @Title: getMemberIncome
	 * @Description: 查询我的收入接口
	 * @param: @param request
	 * @param: @param token
	 * @param: @return
	 * @return: Result
	 * @author：李杰
	 * @throws
	 */
	@RequestMapping(value = "/getMemberIncome/{token}", method = RequestMethod.GET)
	public Result getMemberIncome(HttpServletRequest request, @PathVariable String token){
		logger.info("查询我的收入参数 token ： " + token);
		Result result = UserCacheUtil.getUser(token);
		if (result.isSuccess()) {
			MemberInfo memberInfo = (MemberInfo) result.getData();
			MemberScoreVO2 ms = getMemberScore(memberInfo.getId());
			MyScoreVO myScore = new MyScoreVO();
			// 会员推广会员奖励积分
			myScore.setMemberRewardScore(MoneyTransUtil.formatBigDecimalByDivisFiveStr(new BigDecimal(ms.getMemberRewardScore())));
			// 会员推广商户奖励积分
			myScore.setMerchantRewardScore(MoneyTransUtil.formatBigDecimalByDivisFiveStr(new BigDecimal(ms.getMerchantRewardScore())));
			myScore.setMemberId(memberInfo.getId());
			// 现金余额（可用积分加分润金额）
			final Long enableScore = ms.getEnablescore();// 可用积分
			final Long memberProfitScore = ms.getMemberProfitScore();// 会员分润积分(分润积分不可用)
			BigDecimal mbd = new BigDecimal(enableScore + memberProfitScore);
			myScore.setCashScore(MoneyTransUtil.formatBigDecimalByDivisFiveStr(mbd));
			// 待用积分
			myScore.setStandbyScore(MoneyTransUtil.formatBigDecimalByDivisFiveStr(new BigDecimal(ms.getStandbyscore())));
			// 可提现积分
			myScore.setDrawnScore(MoneyTransUtil.formatBigDecimalByDivisFiveStr(new BigDecimal(enableScore)));
			result.setData(myScore);
		}
		logger.info("获取会员积分接口返回值，result={}", JSON.toJSONString(result));
		return result;
	}
	/**
	 *
	 * @Title: getCashScore
	 * @Description: 查询我的余额
	 * @param: @param request
	 * @param: @param token
	 * @param: @return
	 * @return: Result
	 * @author：李杰
	 * @throws
	 */
	@RequestMapping(value = "/getMemberScoreInfo", method = RequestMethod.POST)
	public Result getCashScore(HttpServletRequest request, AppScoreQueryDTO adto) {
		logger.info(" 查询我的明细参数 QuerySingleScoreInfoDTO = {} ", JSON.toJSONString(adto));
		Result result = UserCacheUtil.getUser(adto.getToken());
		if (result.isSuccess()) {
			MemberInfo memberInfo = (MemberInfo) result.getData();
			if (!AppScoreQueryEnum.isExists(adto.getIsQueryList())) {
				return ResultUtil.getResult(AppResultEnum.QUERY_TYPE_ERROR);
			}
			QuerySingleScoreInfoDTO dto = new QuerySingleScoreInfoDTO();
			dto.setMemberId(memberInfo.getId());
			dto.setScoreType(ScoreTypeEnum.getScoreTypeEnum(adto.getType()));
			dto.setQueryList(AppScoreQueryEnum.QUERY_LIST.getCode().equals(adto.getIsQueryList()));
			PageBean page = new PageBean();
			Integer pageSize = adto.getPageSize() == null ? 10 : adto.getPageSize();
			Integer pageNum = adto.getPageNum() == null ? 1 : adto.getPageNum();
			page.setPageNum(pageNum);
			page.setPageSize(pageSize);
			result = scoreService.getMemberSingleScoreInfo(dto, page);
			// 当查询可提现余额时查询 绑卡、认证 、当天已提现积分
			if(ScoreTypeEnum.DRWN_BALANCE.getCode().equals(adto.getType())){
				if(result.isSuccess()){
					Object data = result.getData();
					if(data instanceof MemberSingleScoreVO){
						MemberSingleScoreVO vo = (MemberSingleScoreVO)data;
						// 设置是否绑定银行卡
						vo.setIsBindBank(isBindBank(memberInfo.getId()));
						// 设置是否实名认证
						vo.setIsExistPayPwd(isExistPayPwd(memberInfo.getId()));
						// 设置当天提现积分
						vo.setDayDrawScore(getDayCashScore(memberInfo.getId()));
					}
				}
			}
		}
		logger.info(" 查询我的明细返回数据 result={}", JSON.toJSONString(result));
		return result;
	}
	/**
	 *
	 * @Title: getDayCashScore
	 * @Description: 得到当天已提现积分
	 * @param: @param memberId
	 * @param: @return
	 * @return: BigDecimal
	 * @author：李杰
	 * @throws
	 */
	private String getDayCashScore(Long memberId) {
		BigDecimal score = new BigDecimal(0);
		Result dayCash = memberCashService.getDayCashScore(memberId);
		if (dayCash.isSuccess()) {
			Object data = dayCash.getData();
			if (data instanceof BigDecimal) {
				score = (BigDecimal) data;
			}
		}
		return MoneyTransUtil.formatBigDecimalByDivisFiveStr(score);
	}
	/**
	 *
	 * @Title: isBindBank
	 * @Description: 查询是否绑定了银行卡信息
	 * @param: @param memberId
	 * @param: @return
	 * @return: boolean
	 * @author：李杰
	 * @throws
	 */
	private boolean isBindBank(Long memberId) {
		Result bresult = bankCardBindService.selectBindMemberBankCardByUserId(memberId);
		logger.info("查询会员绑定的银行卡信息返回值，bresult={}", JSON.toJSONString(bresult));
		return bresult.isSuccess();
	}
	/**
	 *
	 * @Title: isExistsPayPwd
	 * @Description: 判断是否存在支付密码
	 * @param: @param memberId
	 * @param: @return
	 * @return: boolean
	 * @author：李杰
	 * @throws
	 */
	private boolean isExistPayPwd(Long memberId) {
		PayPasswordQueryDTO dto = new PayPasswordQueryDTO();
		dto.setUserId(memberId);
		dto.setCustomerType(PayPwdEnum.MEMBER_PAY_PWD.getCode());
		Result result = payPasswordService.verifyPayPwdIsExists(dto);
		logger.info("查询是否存在支付密码返回值，result={}", JSON.toJSONString(result));
		return result.isSuccess();
	}
	/**
	 *
	 * @Title: getIntegralList
	 * @Description: 获取积分数据列表
	 * @param: @param request
	 * @param: @param token
	 * @param: @param pageNum
	 * @param: @param pageSize
	 * @param: @return
	 * @return: Result
	 * @author：李杰
	 * @throws
	 */
	@RequestMapping(value = "/getIntegralList/{pageNum}/{pageSize}/{token}", method = RequestMethod.GET)
	public Result getIntegralList(HttpServletRequest request, @PathVariable String token, @PathVariable Integer pageNum,
			@PathVariable Integer pageSize) {
		Result result = UserCacheUtil.getUser(token);
		if (result.isSuccess()) {
			MemberInfo memberInfo = (MemberInfo) result.getData();
			pageNum = pageNum == null ? 1 : pageNum;
			pageSize = pageSize == null ? 10 : pageSize;
			PageBean pageBean = new PageBean();
			pageBean.setPageNum(pageNum);
			pageBean.setPageSize(pageSize);
			ScorePageDTO scorePageRequest = new ScorePageDTO();
			scorePageRequest.setMemberId(memberInfo.getId());
			// 查询个人用户积分列表
			logger.info("查询会员积分流水接口入参，pageBean={},scorePageRequest={}", JSON.toJSONString(pageBean),
					JSON.toJSONString(scorePageRequest));
			result = scoreService.getMemberScorePage(pageBean, scorePageRequest);
			// 封装返回值
			result.setData(dataService.getIntegralVO(result));
			logger.info("查询会员积分流水接口返回值，result={}", JSON.toJSONString(result));
		}
		return result;
	}
	/**
	 *
	 * @Title: getMemberScore
	 * @Description: 得到积分详情
	 * @param: @param memberId
	 * @param: @return
	 * @return: MemberScoreVo2
	 * @author：李杰
	 * @throws
	 */
	private MemberScoreVO2 getMemberScore(Long memberId) {
		logger.info("查询会员积分接口入参，memberId = " +  memberId);
		MemberScoreVO2 memberScoreVo2 = scoreService.getMemberScore(memberId);
		logger.info("查询会员积分接口返回值，memberScoreVo2={}", JSON.toJSONString(memberScoreVo2));
		return memberScoreVo2;
	}
	/**
	 *
	 * @Title: memberCash
	 * @Description: 积分提现
	 * @param: @param model
	 * @param: @param cv
	 * @param: @param request
	 * @param: @return
	 * @return: String
	 * @author：李杰
	 * @throws
	 */
	@Token(key = "token", isCache = true, cacheType = CacheKeyTypeEnum.HASH)
	@RequestMapping(value = "/cash", method = RequestMethod.POST)
	public Result memberCash(AppCashDTO dto, HttpServletRequest request) {
		logger.info("积分提现参数，CashDTO={}", JSON.toJSONString(dto));
		Result result = UserCacheUtil.getUser(dto.getToken());
		if (result.isSuccess()) {
			// 短信验证
			//result = verifySmsCode(dto.getTelPhone(), dto.getSmsCode(), SmsCodeType.MEMBER_CASH_VC);
			result=yzSmsCode(dto.getTelPhone(), dto.getSmsCode());
			if (!result.isSuccess()) {
				return result;
			}
			MemberCashDTO cashDto = new MemberCashDTO();
			cashDto.setCashScore(dto.getCashScore());
			cashDto.setId(dto.getMemberId());
			cashDto.setMsgCode(dto.getSmsCode());
			cashDto.setPayPwd(dto.getPayPwd());
			cashDto.setRequestIP(IPUtil.getIpAddress(request));
			result = memberCashService.memberCash(cashDto);
			if(result.isSuccess()){
				result.setMessage("操作成功，请注意查收");
			}
		}
		logger.info("积分提现返回参数  Result = {}", JSON.toJSONString(result));
		return result;
	}
	/**
	 *
	 * @Title: getMemberCashInfo
	 * @Description: 得到积分提现数据
	 * @param: @param request
	 * @param: @param token
	 * @param: @return
	 * @return: Result
	 * @author：李杰
	 * @throws
	 */
	@RequestMapping(value = "/getMemberCashInfo/{token}", method = RequestMethod.GET)
	public Result getMemberCashInfo(HttpServletRequest request, @PathVariable String token) {
		logger.info("获取积分提现数据参数 token ： " + token);
		Result result = UserCacheUtil.getUser(token);
		if (result.isSuccess()) {
			MemberInfo memberInfo = (MemberInfo) result.getData();
			result = memberCashService.getMemberInfo(memberInfo.getId());
			result.setData(dataService.getIntegralDrawVO(result));
		}
		logger.info("查询会员积分提现信息接口返回值，memberInfo={}", JSON.toJSONString(result));
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
		logger.info("根据手机号获取短信验证码返回数据 Result = {} ", JSON.toJSONString(result));
		return result;
	}
	//验证验证码
	//TODO
	private Result yzSmsCode(String mobile, String smsCode){
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
	 * @ClassName:  ScoreDataService
	 * @Description:积分相关数据服务
	 * @author: 李杰
	 * @date:   2017年6月19日 下午3:58:30
	 * @Copyright: 2017
	 */
	protected class ScoreDataService{
		/**
		 *
		 * @Title: getIntegralVO
		 * @Description: 封装积分相关的数据
		 * @param: @param result
		 * @param: @return
		 * @return: IntegralVO
		 * @author：李杰
		 * @throws
		 */
		@SuppressWarnings("unchecked")
		public List<IntegralVO> getIntegralVO(Result result) {
			List<IntegralVO> integrals = null;
			if (null != result && result.isSuccess()) {
				Object obj = result.getData();
				if (obj instanceof List) {
					integrals = ContainerUtil.lList();
					List<MemberScoreShopVO> list = (List<MemberScoreShopVO>) obj;
					for (MemberScoreShopVO vo : list) {
						IntegralVO iv = new IntegralVO();
						iv.setCreateTime(SyncDateUtil.dateToString("yyyy-MM-dd HH:mm:ss", vo.getCreateTime()));
						iv.setFee(MoneyTransUtil.formatObj(vo.getFee()));
						iv.setRemark(vo.getRemark());
						iv.setScore(MoneyTransUtil.formatObj(vo.getScore()));
						iv.setSource(vo.getSource());
						integrals.add(iv);
					}
				}
			}
			return integrals;
		}
		/**
		 *
		 * @Title: getIntegralDrawVO
		 * @Description:封装积分提现详情数据
		 * @param: @param result
		 * @param: @return
		 * @return: IntegralDrawVO
		 * @author：李杰
		 * @throws
		 */
		public IntegralDrawVO getIntegralDrawVO(Result result) {
			if (null != result && result.isSuccess()) {
				Object obj = result.getData();
				if (obj instanceof MemberInfoByCashVO) {
					MemberInfoByCashVO vo = (MemberInfoByCashVO) obj;
					IntegralDrawVO iv = new IntegralDrawVO();
					iv.setBankCardNo(vo.getBankCard());
					iv.setIdCardNo(vo.getIdCardNo());
					iv.setRealName(vo.getRealName());
					iv.setEnabledScore(MoneyTransUtil.formatObj(vo.getScore()));
					return iv;
				}
			}
			return null;
		}
	}
}
