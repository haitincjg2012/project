
package com.ph.shopping.facade.spm.service.impl;

import java.util.List;
import java.util.Objects;

import com.alibaba.fastjson.JSON;
import com.ph.shopping.common.util.date.DoubleUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ph.shopping.common.core.customenum.RoleEnum;
import com.ph.shopping.common.core.customenum.TransCodeEnum;
import com.ph.shopping.common.core.exception.BizException;
import com.ph.shopping.common.util.core.RespCode;
import com.ph.shopping.common.util.core.ResultUtil;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.common.util.transform.MoneyTransUtil;
import com.ph.shopping.facade.mapper.UserBalanceMapper;
import com.ph.shopping.facade.spm.constant.UserBalanceConstant;
import com.ph.shopping.facade.spm.dto.UserBalanceTradeDTO;
import com.ph.shopping.facade.spm.entity.UserBalanceRecord;
import com.ph.shopping.facade.spm.exception.UserAccountException;
import com.ph.shopping.facade.spm.exception.UserAccountExceptionEnum;
import com.ph.shopping.facade.spm.service.IUserBalanceService;
import com.ph.shopping.facade.spm.vo.BalanceVO;
import com.ph.shopping.facade.spm.vo.TransCodeVO;
import com.ph.shopping.facade.spm.vo.UserBalanceTradeVO;

import cm.ph.shopping.facade.order.constant.OrderResultEnum;

/**
 * 
 * @ClassName: UserBalanceImpl
 * @Description: 余额流水操作记录
 * @author 王强
 * @date 2017年5月25日 下午5:37:52
 */
@Component
@Service(version = "1.0.0")
public class UserBalanceImpl implements IUserBalanceService {

	// 创建日志
	private Logger logger = LoggerFactory.getLogger(UserBalanceImpl.class);

	@Autowired
	private UserBalanceMapper userBalanceMapper;
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public int userBalanceTrade(Long userId, TransCodeEnum codeEnum, Long score, String orderNo, Long handingCharge,
			Byte userType) throws Exception {
		score = codeEnum.getMark() * score;
		handingCharge = codeEnum.getMark() * handingCharge;

		switch (codeEnum) {
			case MERCHANT_UNLINE_ORDER:
				return handleUserBalanceTrade(userId, codeEnum, score, orderNo, handingCharge, userType);
			case USER_CHARGE_ORDER:
				return userChargeBalanceTrade(userId, codeEnum, score, orderNo, handingCharge, userType);
			case USER_STOCK:
				return handleUserBalanceTrade(userId, codeEnum, score, orderNo, handingCharge, userType);
			case USER_RE_STOCK:
				return handleUserBalanceTrade(userId, codeEnum, score, orderNo, handingCharge, userType);
			case MERCHANT_UNLINE_ORDER_CANCEL:
				return handleUserBalanceTrade(userId, codeEnum, score, orderNo, handingCharge, userType);
			case ONLINE_ORDER_SETTLE:
				return handleUserBalanceTrade(userId, codeEnum, score, orderNo, handingCharge, userType);
			case USER_DRAWCASH_ORDER:
				return handleUserDrawCashTrade(userId, codeEnum, score, orderNo, handingCharge, userType);
			case USER_DRAWCASH_ORDER_FAIL:
				return handleUserBalanceTrade(userId, codeEnum, score, orderNo, handingCharge, userType);
			case WHOLESALE_ORDER_PROFIT:
				return handleUserBalanceTrade(userId, codeEnum, score, orderNo, handingCharge, userType);
		default:
			throw new Exception("用户余额记录失败");
		}
	}

	/**
	 * 
	* @Title: purchaseBalanceTrade
	* @Description: 线上充值
	* @author 王强
	* @date  2017年6月5日 下午4:00:50
	* @param userId
	* @param codeEnum
	* @param score
	* @param orderNo
	* @param handingCharge
	* @param userType
	* @return
	 */
	private int userChargeBalanceTrade(Long userId, TransCodeEnum codeEnum, Long score, String orderNo,
			Long handingCharge, Byte userType) throws Exception {

		updateUserBalance(userId, score);

		insertUserBalanceRecord(userId, codeEnum.getCode(), score, orderNo, handingCharge, userType);
		
		return UserBalanceConstant.SUCCESS;
	}

	/**
	 * 
	* @Title: userChargeBalanceTrade
	* @Description: 用户充值失败
	* @author 王强
	* @date  2017年6月5日 下午4:00:32
	* @param userId
	* @param codeEnum
	* @param score
	* @param orderNo
	* @param handingCharge
	* @param userType
	* @return
	* @throws Exception
	 */
//	private int userChargeBalanceTrade(Long userId, TransCodeEnum codeEnum, Long score, String orderNo,
//			Long handingCharge, Byte userType) throws Exception {
//
//		insertUserBalanceRecord(userId, codeEnum.getCode(), score, orderNo, handingCharge, userType);
//
//		return UserBalanceConstant.SUCCESS;
//	}

	/**
	 * 
	 * @Title: merchantUnlineOrder
	 * @Description: 更新用户余额及增加余额流水
	 * @author 王强
	 * @date 2017年5月26日 上午9:53:19
	 * @param userId
	 * @param score
	 * @param orderNo
	 * @param handingCharge
	 * @param userType
	 * @return
	 * @throws Exception
	 */
	private int handleUserBalanceTrade(Long userId, TransCodeEnum codeEnum, Long score, String orderNo,
			Long handingCharge, Byte userType) throws Exception {
		logger.info("请求参数userId：" +userId + ",codeEnum:" + codeEnum.toString() + ",score:" + score + ",orderNo:" 
				+ orderNo + ",handingCharge:" + handingCharge + ",userType:" + userType);
		updateUserBalance(userId, score);

		insertUserBalanceRecord(userId, codeEnum.getCode(), score, orderNo, handingCharge, userType);

		return UserBalanceConstant.SUCCESS;
	}
	
	/**
	 * 
	 * @Title: merchantUnlineOrder
	 * @Description: 更新用户余额及增加余额流水
	 * @author 王强
	 * @date 2017年5月26日 上午9:53:19
	 * @param userId
	 * @param score
	 * @param orderNo
	 * @param handingCharge
	 * @param userType
	 * @return
	 * @throws Exception
	 */
	private int handleUserDrawCashTrade(Long userId, TransCodeEnum codeEnum, Long score, String orderNo,
			Long handingCharge, Byte userType) throws Exception {

		long drawCashScore = score + handingCharge;
		updateUserBalance(userId, drawCashScore);
		
		insertUserBalanceRecord(userId, codeEnum.getCode(), score, orderNo, handingCharge, userType);

		return UserBalanceConstant.SUCCESS;
	}
	

	/**
	 * 
	 * @Title: updateUserBalance
	 * @Description: 更新用户余额
	 * @author 王强
	 * @date 2017年5月25日 下午5:55:46
	 * @throws Exception
	 */
	@Transactional(rollbackFor = BizException.class)
	public void updateUserBalance(final long userId,final long score) throws Exception {
		logger.info("用户id :" + userId + ",score :" + score);
		if (userBalanceMapper.updateUserBalance(userId, score) != 1) {
			throw new Exception("更新用户余额失败!");
		}
	}

	/**
	 * 
	 * @Title: insertUserBalanceRecord
	 * @Description: 新增余额流水记录
	 * @author 王强
	 * @date 2017年5月25日 下午5:55:27
	 * @param transCode
	 * @param orderNo
	 * @throws Exception
	 */
	private void insertUserBalanceRecord(final long userId,final int transCode,final long score,final String orderNo,final Long handingCharge,
			final Byte userType) throws Exception {
		UserBalanceRecord userBalanceRecord = new UserBalanceRecord();
		userBalanceRecord.setUserId(userId);
		userBalanceRecord.setTransCode(transCode);
		userBalanceRecord.setScore(score);
		userBalanceRecord.setOrderNo(orderNo);
		userBalanceRecord.setHandingCharge(handingCharge);
		userBalanceRecord.setUserType(userType);

		if (userBalanceMapper.insertUserBalanceRecord(userBalanceRecord) != 1) {
			throw new Exception("新增用户余额流水记录失败!");
		}
	}

	/**
	 * 
	 * Title: getUserBalance Description:获取用户余额
	 * 
	 * @author 王强
	 * @date 2017年5月17日 下午1:40:38
	 * @param userId
	 * @return
	 */
	@Override
	public Result getUserBalance(Long userId) {
		try {
			BalanceVO balanceVO = userBalanceMapper.getUserBalance(userId);
			if(balanceVO != null) {
				balanceVO.setScore(MoneyTransUtil.transDivisDouble(balanceVO.getScore1()));
				return ResultUtil.getResult(RespCode.Code.SUCCESS, balanceVO);
			} else {
				return ResultUtil.getResult(RespCode.Code.FAIL);
			}
		} catch (Exception e) {
			logger.error("查询余额失败", e);
			throw new UserAccountException(UserAccountExceptionEnum.SELECT_EXCEPTION);
		}
	}

	@Override
	public Result getUserBalanceTrade(UserBalanceTradeDTO userBalanceTradeDTO) {
		try {
			logger.info("余额流水记录:{}", JSON.toJSON(userBalanceTradeDTO));
			PageHelper.startPage(userBalanceTradeDTO.getPageNum(), userBalanceTradeDTO.getPageSize());
			List<UserBalanceTradeVO> tradeVOs = (Page<UserBalanceTradeVO>) userBalanceMapper.getUserBalanceTrade(
					userBalanceTradeDTO.getUserId(), userBalanceTradeDTO.getStartDate(),
					userBalanceTradeDTO.getEndDate(), userBalanceTradeDTO.getTransCode(),userBalanceTradeDTO.getOrderNo());
			for (UserBalanceTradeVO balanceTradeVO : tradeVOs) {
				balanceTradeVO.setScore(MoneyTransUtil.stringFormat(MoneyTransUtil.transDivisDouble(balanceTradeVO.getScore1())));
				balanceTradeVO.setHandingCharge(MoneyTransUtil.stringFormat(MoneyTransUtil.transDivisDouble(balanceTradeVO.getHandingCharge1())));
				Long totalprice= Objects.isNull(userBalanceMapper.getOrderTotalPrice(balanceTradeVO.getOrderNo()))?0L:userBalanceMapper.getOrderTotalPrice(balanceTradeVO.getOrderNo());
				balanceTradeVO.setTotalMoney(Double.valueOf(DoubleUtils.formatRound(totalprice/100.00,2)));
                logger.info("******************************"+balanceTradeVO.getOrderNo()+balanceTradeVO.getTotalMoney());

			}

			PageInfo<UserBalanceTradeVO> pageInfo = new PageInfo<UserBalanceTradeVO>(tradeVOs);
			return ResultUtil.getResult(RespCode.Code.SUCCESS, pageInfo.getList(), pageInfo.getTotal());
		} catch (Exception e) {
			logger.error("查询余额流水异常", e);
			return ResultUtil.getResult(RespCode.Code.FAIL, null, 0l);
		}

	}

	@Override
	public List<TransCodeVO> getTransCodes() {
		return userBalanceMapper.getTransCode();
	}

	@Override
	@Transactional
	public Result updateBalanceStatus(Long balanceId, Integer status) {
		try {
			if(status == 1) {
				int res = userBalanceMapper.frozenOrUnFrozenBalance(balanceId, UserBalanceConstant.FROZEN);
				if(res != 1) {
					return ResultUtil.getResult(UserAccountExceptionEnum.FROZEN_FAIL);
				}
			} else if(status == 0) {
				int res = userBalanceMapper.frozenOrUnFrozenBalance(balanceId, UserBalanceConstant.UNFROZEN);
				if(res != 1) {
					return ResultUtil.getResult(UserAccountExceptionEnum.UNFROZEN_FAIL);
				}
			} else {
				logger.info("未知状态码");
				return ResultUtil.getResult(UserAccountExceptionEnum.UNKNOWN_STATUS);
			}
			
			return ResultUtil.getResult(RespCode.Code.SUCCESS);
		} catch (Exception e) {
			logger.error("冻结操作异常", e);
			throw new UserAccountException(UserAccountExceptionEnum.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public Result deleteUserBalance(Long userId) {
		if(userBalanceMapper.delUserBalance(userId) == 1) {
			return ResultUtil.getResult(RespCode.Code.SUCCESS);
		} else {
			return ResultUtil.getResult(RespCode.Code.FAIL);
		}
	}
	/**
	 * 下单改造--将获取账户余额、更新余额改造到同一个事物中
	 * @date 2017年8月14日 下午15:38
	 * @author 李治桦
	 */
	@Override
	@Transactional
	public Result getAndUpdateBalance( String pwd, Long merchantId,Long serviceCost,String orderNo) throws Exception {
		Result result = ResultUtil.getResult( RespCode.Code.SUCCESS );
		//现金支付--获取账户余额
		Result userBalance2 = getUserBalance(merchantId);//获取账户余额
		BalanceVO balanceVO = new BalanceVO();
		balanceVO = (BalanceVO) userBalance2.getData();
		if(!RespCode.Code.SUCCESS.getCode().equals(userBalance2.getCode())){//无此账户
			return userBalance2;
		}
		//判断余额是否足够
		if(balanceVO.getScore1() + 10000000l < serviceCost){//余额不足
			return  ResultUtil.getResult(OrderResultEnum.NOENOUGH_MONEY);
		}
		//判断商户密码
		if(balanceVO.getPayPwd()==null){
			logger.error("获取商户支付密码异常");
			return  ResultUtil.getResult(OrderResultEnum.PAY_PWD_EMPTY);
		}
		if(!balanceVO.getPayPwd().equals( pwd ) ){
			logger.error("商户密码不对");
			return  ResultUtil.getResult(OrderResultEnum.PAY_PASSWORD_ERROR);
		}
		//现金支付余额校验-end
		//现金支付--更新账户余额信息
		int updateUserBalanceRecord = userBalanceTrade( merchantId,
					TransCodeEnum.MERCHANT_UNLINE_ORDER,
					serviceCost,
					orderNo,
					0L, (byte) RoleEnum.MERCHANT.getCode() );
		if(updateUserBalanceRecord !=1){
			result.setMessage( "操作失败" );
			logger.info("修改商户余额和添加记录流水失败");
			throw new Exception("修改商户余额和添加记录流水失败");
		}
		if(userBalance2.getData() != null){
			return ResultUtil.getResult(RespCode.Code.SUCCESS,userBalance2.getData());
		}else{
			return ResultUtil.getResult(RespCode.Code.FAIL);
		}
		
	}
	
	/**
	 * 修改在线充值的金额回调 将更新余额和查询余额放在同一个事务中
	 * @param userId
	 * @param score
	 * @param orderNo
	 * @param userType
	 * @return
	 * @throws Exception
	 * @author dell
	 */
	@Override
	@Transactional
	public Result getAndUpdateBalanceRecord(long userId, TransCodeEnum userChargeOrder, long score, String orderNo,
			long l, byte userType) throws Exception {
		Result result = ResultUtil.getResult( RespCode.Code.SUCCESS );
		//获取账户余额
		Result userBalance2 = getUserBalance(userId);
		//修改账户余额信息
	
			int	updateUserBalanceRecord = userBalanceTrade( userId,
						TransCodeEnum.USER_CHARGE_ORDER,
						score, orderNo,
						l,RoleEnum.MERCHANT.getCode() );
			if(updateUserBalanceRecord !=1){
				result.setMessage( "操作失败" );
				logger.info("修改商户余额和添加记录流水失败");
				throw new Exception("修改商户余额和添加记录流水失败");
			}
			if(userBalance2.getData() != null){
				return ResultUtil.getResult(RespCode.Code.SUCCESS,userBalance2.getData());
			}else{
				return ResultUtil.getResult(RespCode.Code.FAIL,userBalance2.getData());
			}
	}

}
