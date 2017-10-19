package com.ph.shopping.facade.spm.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ph.shopping.common.core.config.pay.PayConfiguration;
import com.ph.shopping.common.core.customenum.TransCodeEnum;
import com.ph.shopping.common.util.core.ResultUtil;
import com.ph.shopping.common.util.date.UtilDate;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.common.util.transform.MoneyTransUtil;
import com.ph.shopping.facade.mapper.UserBalanceMapper;
import com.ph.shopping.facade.mapper.UserDrawcashMapper;
import com.ph.shopping.facade.pay.dto.CashReceiveStation;
import com.ph.shopping.facade.spm.constant.CachConstant;
import com.ph.shopping.facade.spm.constant.MessageEnum;
import com.ph.shopping.facade.spm.dto.UserDrawCashDTO;
import com.ph.shopping.facade.spm.entity.UserDrawcash;
import com.ph.shopping.facade.spm.exception.UserAccountException;
import com.ph.shopping.facade.spm.exception.UserAccountExceptionEnum;
import com.ph.shopping.facade.spm.service.IUserBalanceService;
import com.ph.shopping.facade.spm.service.IUserDrawCashService;
import com.ph.shopping.facade.spm.vo.BalanceVO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

@Component
@Service(version = "1.0.0")
public class UserDrawCashImpl implements IUserDrawCashService {

	private Logger logger = LoggerFactory.getLogger(UserDrawCashImpl.class);

	@Autowired
	private UserBalanceMapper userBalanceMapper; 

	@Autowired
	private UserDrawcashMapper userDrawcashMapper;

	@Autowired
	private IUserBalanceService iUserBalance;

	@Autowired
	private PayConfiguration payConfiguration;





//	@Autowired
//	private RedisCacheService<String, Long> redisCacheService;

/*	@Override
	@Transactional
	public Result addDrawCashRecord(UserDrawCashDTO userDrawcashDTO) {
		try {
			UserDrawcash userDrawcash = new UserDrawcash();
			BeanUtils.copyProperties(userDrawcashDTO, userDrawcash);
			userDrawcash.setScore(MoneyTransUtil.transLongToBigdecimal(userDrawcashDTO.getScore()));
			userDrawcash.setHandingCharge(MoneyTransUtil.transLongToBigdecimal(userDrawcashDTO.getHandingCharge()));

			BalanceVO balanceVO = userBalanceMapper.getUserBalance(userDrawcash.getUserId());
			
			if (balanceVO == null) {
				return ResultUtil.getResult(MessageEnum.ERROR_USER);
			}
			//balanceVO.setScore(MoneyTransUtil.transDivisDouble(balanceVO.getScore1()));
			
			//如果余额小于等于0就不能体现 
			if(balanceVO.getScore1().longValue() <= 0L) {
				return ResultUtil.getResult(MessageEnum.TEMPORARILY_UNABLE_DRAWCASH);
			}
			// 判断余额是否足够
			if (userDrawcashDTO.getDrawCashScore().longValue()*10000 > balanceVO.getScore1().longValue()) {
				return ResultUtil.getResult(MessageEnum.BALANCE_NOT_ENOUGH);
			}

			// 当前输入提现金额
			if (userDrawcashDTO.getDrawCashScore() < CachConstant.UPPERLIMIT
					|| userDrawcashDTO.getDrawCashScore() > CachConstant.DOWNLIMIT) {
				return ResultUtil.getResult(MessageEnum.BALANCE_BETWEEN);
			}

			Long cashMoney = userDrawcashMapper.getCashMoney(userDrawcashDTO.getUserId());
			
			Long actuaMoney = 0l;
			
			if(cashMoney != null && cashMoney != 0){
				actuaMoney = cashMoney.longValue()/10000 + userDrawcashDTO.getDrawCashScore().longValue();
				
				if (actuaMoney > CachConstant.DOWNLIMIT) {
					return ResultUtil.getResult(MessageEnum.BALANCE_EXCEED);
				}
			}else{
				actuaMoney = userDrawcashDTO.getDrawCashScore();
			}
			*//*Object obj = redisCacheService.get(CachConstant.DRAWCASHKEY + userDrawcash.getUserId());
			Long actuaMoney = 0l;
			if (obj != null) {
				actuaMoney = (Long) obj;
				logger.info("缓存中的金额：" + actuaMoney);
				// 一天中提现的金额累加,并存然后缓存中保持一天
				actuaMoney += userDrawcashDTO.getDrawCashScore();
				if (actuaMoney > CachConstant.DOWNLIMIT) {
					return ResultUtil.getResult(MessageEnum.BALANCE_EXCEED);
				}
			} else {
				actuaMoney = userDrawcashDTO.getDrawCashScore();
			}
			redisCacheService.set(CachConstant.DRAWCASHKEY + userDrawcash.getUserId(), actuaMoney, CachConstant.SECONDS,
					TimeUnit.SECONDS);*//*
			// 判断累计提现金额是否在规定范围内
			if (actuaMoney < CachConstant.UPPERLIMIT || actuaMoney > CachConstant.DOWNLIMIT) {
				return ResultUtil.getResult(MessageEnum.BALANCE_EXCEED);
			}

			String orderNo = OrderUtil.createOrderCode(OrderBizCode.DRAW_CASH_NO);
			userDrawcash.setOrderNo(orderNo);
			userDrawcash.setStatus((byte) 0);
			userDrawcash.setCreateTime(new Date());
			userDrawcash.setCreaterId(userDrawcash.getUserId());
			userDrawcash.setAuditState((byte) 0);
			// 新增提现记录
			int res = userDrawcashMapper.insertSelective(userDrawcash);
			if (res != 1) {
				return ResultUtil.getResult(MessageEnum.ADD_DRAWCASH_FAIL);
			}

			// 记录充值流水
			iUserBalance.userBalanceTrade(userDrawcash.getUserId(), TransCodeEnum.USER_DRAWCASH_ORDER,
					userDrawcash.getScore().longValue(), orderNo, userDrawcash.getHandingCharge().longValue(),
					userDrawcash.getUserType());

			return ResultUtil.getResult(RespCode.Code.SUCCESS);
		} catch (Exception e) {
			logger.error("新增用户提现记录失败", e);
			throw new UserAccountException(UserAccountExceptionEnum.ADD_USERDRAWCASH_EXCEPTION);
		}
	}*/

	@Override
	@Transactional
	public Result addDrawCashRecord(UserDrawCashDTO userDrawcashDTO) {
        logger.info("掌柜提现入参:{}",JSON.toJSON(userDrawcashDTO));
		UserDrawcash userDrawcash = new UserDrawcash();
		String orderNo = null;
		BalanceVO balanceVO = null;
		UserDrawCashDTO userBankInfo = userBalanceMapper.getUserInfo(userDrawcashDTO.getUserId());

		if(userBankInfo == null){
			return ResultUtil.setResult(false,"暂未获取到用户银行卡信息,不能提现");
		}
		try {
			BeanUtils.copyProperties(userDrawcashDTO, userDrawcash);
			userDrawcash.setScore(MoneyTransUtil.transLongToBigdecimal(userDrawcashDTO.getScore()));
			userDrawcash.setHandingCharge(MoneyTransUtil.transLongToBigdecimal(userDrawcashDTO.getHandingCharge()));

			balanceVO = userBalanceMapper.getUserBalance(userDrawcash.getUserId());

			if (balanceVO == null) {
				return ResultUtil.getResult(MessageEnum.ERROR_USER);
			}
			//balanceVO.setScore(MoneyTransUtil.transDivisDouble(balanceVO.getScore1()));
			//如果余额小于等于0就不能体现
			if(balanceVO.getScore1().longValue() <= 0L) {
				return ResultUtil.getResult(MessageEnum.TEMPORARILY_UNABLE_DRAWCASH);
			}
			// 判断余额是否足够
			if (userDrawcashDTO.getDrawCashScore().longValue()*10000 > balanceVO.getScore1().longValue()) {
				return ResultUtil.getResult(MessageEnum.BALANCE_NOT_ENOUGH);
			}

			// 当前输入提现金额
			if (userDrawcashDTO.getDrawCashScore() < CachConstant.UPPERLIMIT
					|| userDrawcashDTO.getDrawCashScore() > CachConstant.DOWNLIMIT) {
				return ResultUtil.getResult(MessageEnum.BALANCE_BETWEEN);
			}

			Long cashMoney = userDrawcashMapper.getCashMoney(userDrawcashDTO.getUserId());

			Long actuaMoney = 0l;

			if(cashMoney != null && cashMoney != 0){
				actuaMoney = cashMoney.longValue()/10000 + userDrawcashDTO.getDrawCashScore().longValue();

				if (actuaMoney > CachConstant.DOWNLIMIT) {
					return ResultUtil.getResult(MessageEnum.BALANCE_EXCEED);
				}
			}else{
				actuaMoney = userDrawcashDTO.getDrawCashScore();
			}
			/*Object obj = redisCacheService.get(CachConstant.DRAWCASHKEY + userDrawcash.getUserId());
			Long actuaMoney = 0l;
			if (obj != null) {
				actuaMoney = (Long) obj;
				logger.info("缓存中的金额：" + actuaMoney);
				// 一天中提现的金额累加,并存然后缓存中保持一天
				actuaMoney += userDrawcashDTO.getDrawCashScore();
				if (actuaMoney > CachConstant.DOWNLIMIT) {
					return ResultUtil.getResult(MessageEnum.BALANCE_EXCEED);
				}
			} else {
				actuaMoney = userDrawcashDTO.getDrawCashScore();
			}
			redisCacheService.set(CachConstant.DRAWCASHKEY + userDrawcash.getUserId(), actuaMoney, CachConstant.SECONDS,
					TimeUnit.SECONDS);*/
			// 判断累计提现金额是否在规定范围内
			if (actuaMoney < CachConstant.UPPERLIMIT || actuaMoney > CachConstant.DOWNLIMIT) {
				return ResultUtil.getResult(MessageEnum.BALANCE_EXCEED);
			}

			orderNo = "KHSHTX"+ UtilDate.getOrderNum()+UtilDate.getThree();
			userDrawcash.setOrderNo(orderNo);
			userDrawcash.setStatus((byte) 0);
			userDrawcash.setCreateTime(new Date());
			userDrawcash.setCreaterId(userDrawcash.getUserId());
			userDrawcash.setAuditState((byte) 0);
			userDrawcash.setTradeState("CREATE");
			// 新增提现记录
			int res = userDrawcashMapper.insertSelective(userDrawcash);

			if (res != 1) {
				return ResultUtil.getResult(MessageEnum.ADD_DRAWCASH_FAIL);
			}
			// 记录充值流水
			iUserBalance.userBalanceTrade(userDrawcash.getUserId(), TransCodeEnum.USER_DRAWCASH_ORDER,
					userDrawcash.getScore().longValue(), orderNo, userDrawcash.getHandingCharge().longValue(),
					userDrawcash.getUserType());
		} catch (Exception e) {
			logger.error("新增用户提现记录失败", e);
			throw new UserAccountException(UserAccountExceptionEnum.ADD_USERDRAWCASH_EXCEPTION);
		}
		try{
			UserDrawcash queryUserDrawCash = new UserDrawcash();
			queryUserDrawCash.setOrderNo(orderNo);
			UserDrawcash userDrawcashResult = userDrawcashMapper.selectOne(queryUserDrawCash);
			if (Objects.isNull(userDrawcashResult)){
				return ResultUtil.setResult(false,"提现记录不存在");
			}
			Long money = userDrawcash.getScore().divide(BigDecimal.valueOf(100)).longValue();

			CashReceiveStation cashReceiveStation = new CashReceiveStation();
			cashReceiveStation.setCardNo(userBankInfo.getBankNo()); 	  //收款账户号
			cashReceiveStation.setMerSeqId(orderNo);	  				  // 订单号
			cashReceiveStation.setMoney(money);							  // 实际到账金额 （分）
			cashReceiveStation.setCssId(userDrawcash.getUserId());		  // 用户id
			cashReceiveStation.setPersonName(userBankInfo.getOwnerName()); // 开户姓名
			cashReceiveStation.setBankName(userDrawcash.getBankName());	  // 银行全称
			cashReceiveStation.setFromSystem("PAY_SYSTEM_FROM_KHAPP"); // 发起来源
			cashReceiveStation.setComments(userBankInfo.getIdCardNo());	  // 身份证号
			cashReceiveStation.setCurrentBalance(BigDecimal.valueOf(balanceVO.getScore1()).divide(BigDecimal.valueOf(100)).longValue());  // 提现时余额
			cashReceiveStation.setCssType(4);//角色
			cashReceiveStation.setBankAbbr("CMB");//银行缩写
			String json = JSON.toJSONString(cashReceiveStation);
			logger.info("调用pay入参:{}",json);
            RestTemplate restTemplate = new RestTemplate();
			MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
			map.add("json",json);
            String response = restTemplate.postForObject(payConfiguration.getCash(),map,String.class);
            logger.info("pay工程商户提现返回结果:{}",response);
			if(StringUtils.isBlank(response)){
				logger.info("调用pay工程,请求返回结果result为空");
				return ResultUtil.setResult(false, "调用pay工程,请求返回结果result为空");
			}
			JSONObject jsonObject = JSONObject.parseObject(response);
			Result jsonResult = JSONObject.toJavaObject(jsonObject, Result.class);
			if( "0".equals(jsonResult.getCode()) ){
				logger.info("调用pay工程，提现成功,用户ID为：" + userDrawcash.getUserId() + "返回参数Code：0,返回参数Msg："
						+ jsonResult.getMessage()  + ",时间:" + new Date());

				userDrawcashResult.setTradeState("CALLBACK");
				userDrawcashMapper.updateByPrimaryKeySelective(userDrawcashResult);
				return ResultUtil.setResult(true,"提现成功");
			} else if ( "1".equals(jsonResult.getCode()) ){

				logger.info("调用pay 提现失败,用户ID为：" + userDrawcash.getUserId() + "返回参数Code：0,返回参数Msg："
						+ jsonResult.getMessage()  + ",时间:" + new Date());
				iUserBalance.userBalanceTrade(userDrawcash.getUserId(), TransCodeEnum.USER_DRAWCASH_ORDER_FAIL,
						userDrawcash.getScore().longValue()+userDrawcash.getHandingCharge().longValue(), orderNo, userDrawcash.getHandingCharge().longValue(),
						userDrawcash.getUserType());
				userDrawcashResult.setStatus((byte)2);
				userDrawcashResult.setTradeState("FAIL");
				userDrawcashMapper.updateByPrimaryKeySelective(userDrawcashResult);
				return ResultUtil.setResult(false,"提现失败");
			} else {
				logger.info("调用pay工程,请求返回结果状态code有误，(1:请求失败;0:请求成功)code为："+jsonResult.getCode());
				return ResultUtil.setResult(false,"调用pay工程,请求返回结果状态code有误");
			}

		} catch (Exception e) {
            logger.error("调用pay工程失败:{}",e.getMessage());
			//           logger.info("调用pay工程,请求返回结果状态code有误，(1:请求失败;0:请求成功)code为："+result.getCode());
			return ResultUtil.setResult(false,"调用pay工程,请求返回结果状态code有误");
		}

	}


}
