package com.ph.shopping.facade.spm.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.ph.shopping.common.core.customenum.PayTypeEnum;
import com.ph.shopping.common.core.customenum.TransCodeEnum;
import com.ph.shopping.common.core.other.EcoPayHelper;
import com.ph.shopping.common.core.other.Unpacket;
import com.ph.shopping.common.util.core.RespCode;
import com.ph.shopping.common.util.core.ResultUtil;
import com.ph.shopping.common.util.order.OrderBizCode;
import com.ph.shopping.common.util.order.OrderUtil;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.mapper.UserChargeMapper;
import com.ph.shopping.facade.pay.utils.payeco.PayecoUtils;
import com.ph.shopping.facade.spm.constant.ChargeConstant;
import com.ph.shopping.facade.spm.dto.UserChargeBackDTO;
import com.ph.shopping.facade.spm.entity.UserCharge;
import com.ph.shopping.facade.spm.exception.UserAccountException;
import com.ph.shopping.facade.spm.exception.UserAccountExceptionEnum;
import com.ph.shopping.facade.spm.service.IUserBalanceService;
import com.ph.shopping.facade.spm.service.IUserChargeService;
import com.ph.shopping.facade.spm.vo.CheckPayVO;
import com.ph.shopping.facade.spm.vo.PayVO;
import com.ph.shopping.facade.spm.vo.UserChargeVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * @ClassName: UserChargeRecordImpl
 * @Description: 后台用户充值实现类
 * @author 王强
 * @date 2017年5月17日 上午10:08:46
 */
@Component
@Service(version = "1.0.0")
public class UserChargeImpl implements IUserChargeService {

	Logger logger = LoggerFactory.getLogger(UserChargeImpl.class);

	@Autowired
	private UserChargeMapper userChargeMapper;

	@Reference(version = "1.0.0")
	private IUserBalanceService iUserBalance;

	@Override
	@Transactional(rollbackFor = UserAccountException.class)
	public Result addUserChargeRecord(UserCharge userCharge) {
		try {
			String orderNo = OrderUtil.createOrderCode(OrderBizCode.CHARGE_ORDER);
			userCharge.setOrderNo(orderNo);
			//app端发起充值
			if (PayTypeEnum.PAY_TYPE_ALIPAY.getPayType()==userCharge.getChargeType() || PayTypeEnum.PAY_TYPE_APP.getPayType()==userCharge.getChargeType()){
				int res = userChargeMapper.insertSelective(userCharge);
				if (res==1){
					Result result = ResultUtil.getResult(RespCode.Code.SUCCESS);
					result.setData( orderNo );
					return result;
				}else {
					logger.info("新增充值异常订单号:" + userCharge.getOrderNo() + ",用户ID:" + userCharge.getUserId());
					throw new UserAccountException(UserAccountExceptionEnum.ADD_USERCHARGE_EXCEPTION);
				}
			} else {
				//银行卡充值
				userCharge.setMd5Str(PayecoUtils.getMac(userCharge.getScoreTrans().toString(), orderNo));
				logger.info( "充值记录入参参数userCharge={}",JSON.toJSONString( userCharge ) );
				int res = userChargeMapper.insertSelective(userCharge);
				if (res == 1) {
					// 保存支付相关数据到缓存
					PayVO payVO = Unpacket.unpacket(EcoPayHelper.getInstance().ecoPay("用户线上充值",userCharge.getScoreTrans().toString(), orderNo, userCharge.getPayUrl()), PayVO.class);
					return ResultUtil.getResult(RespCode.Code.SUCCESS, payVO);
				} else {
					logger.info("新增充值异常订单号:" + userCharge.getOrderNo() + ",用户ID:" + userCharge.getUserId());
					throw new UserAccountException(UserAccountExceptionEnum.ADD_USERCHARGE_EXCEPTION);
				}
			}
		} catch (Exception e) {
			logger.error("新增充值异常订单号:" + userCharge.getOrderNo() + ",用户ID:" + userCharge.getUserId());
			logger.error("新增充值记录异常", e);
			throw new UserAccountException(UserAccountExceptionEnum.ADD_USERCHARGE_EXCEPTION);
		}
	}
 
	//更新充值记录状态及相关信息
	@Override
	@Transactional
	public Result updateUSerChargeRecord(UserChargeBackDTO userChargeBackDTO) {
		try {
			logger.info("入参数据userChargeBackDTO:" + JSON.toJSONString(userChargeBackDTO));
			// 判断是否已充值  (countUserChargeTrade统计是否已经有充值流水)
			/*if (userChargeMapper.countUserChargeTrade(userChargeBackDTO.getOrderNo()) > 0) {
				return ResultUtil.getResult(UserAccountExceptionEnum.USER_CHARGED);
			}*/
			//判断充值状态																
			if (userChargeMapper.selectUserChargeRecordChargeStatus(userChargeBackDTO.getOrderNo()) > 0 && userChargeBackDTO.getChargeStatus().equals(ChargeConstant.CHARGED)) {
				return ResultUtil.getResult(UserAccountExceptionEnum.USER_CHARGED);
			}
			int res = userChargeMapper.updateUserChargeByOrderNo(userChargeBackDTO);
			if (userChargeBackDTO.getChargeStatus() == ChargeConstant.CHARGED) {
				// 获取充值数据  根据订单号查询充值状态是否成功 返回用户id  余额   角色
				UserChargeVO userChargeVO = userChargeMapper.getUserChargeTemp(userChargeBackDTO.getOrderNo());
				if (res == 1) {
					/*iUserBalance.userBalanceTrade(userChargeVO.getUserId(), TransCodeEnum.USER_CHARGE_ORDER,
							userChargeVO.getScore(), userChargeBackDTO.getOrderNo(), 0l, userChargeVO.getUserType());*/
					iUserBalance.getAndUpdateBalanceRecord(userChargeVO.getUserId(), TransCodeEnum.USER_CHARGE_ORDER,
							userChargeVO.getScore(), userChargeBackDTO.getOrderNo(), 0l, userChargeVO.getUserType());
					logger.info("充值成功");
					return ResultUtil.getResult(RespCode.Code.SUCCESS);
				} else {
					throw new UserAccountException(UserAccountExceptionEnum.CHARGE_EXCEPTION);
				}
			} else {
				logger.info("充值未成功");
				return ResultUtil.getResult(RespCode.Code.SUCCESS);
			}
		} catch (Exception e) {
			logger.error("充值失败", e);
			throw new UserAccountException(UserAccountExceptionEnum.CHARGE_EXCEPTION);
		}
	}

	@Override
	public CheckPayVO getUserCharegMd5Str(String orderNum) {
		return userChargeMapper.getMd5Str(orderNum);
	}
}
