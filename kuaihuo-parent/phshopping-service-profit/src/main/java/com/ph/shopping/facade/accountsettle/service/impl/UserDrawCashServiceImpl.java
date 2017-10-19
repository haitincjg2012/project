package com.ph.shopping.facade.accountsettle.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ph.shopping.common.core.cache.redis.impl.RedisCacheService;
import com.ph.shopping.common.core.customenum.TransCodeEnum;
import com.ph.shopping.common.util.core.RespCode;
import com.ph.shopping.common.util.core.ResultUtil;
import com.ph.shopping.common.util.date.DateUtil;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.common.util.transform.MoneyTransUtil;
import com.ph.shopping.facade.mapper.IUserDrawCashMapper;
import com.ph.shopping.facade.pay.dto.DefrayDTO;
import com.ph.shopping.facade.pay.service.ICashService;
import com.ph.shopping.facade.pay.utils.union.UniqueUtils;
import com.ph.shopping.facade.permission.constant.RoleIDEnum;
import com.ph.shopping.facade.profit.dto.AuditDTO;
import com.ph.shopping.facade.profit.dto.UserDrawCashDTO;
import com.ph.shopping.facade.profit.exception.ProfitExceptionEnum;
import com.ph.shopping.facade.profit.service.IUserDrawCashService;
import com.ph.shopping.facade.profit.vo.BackUserDrawCashVO;
import com.ph.shopping.facade.profit.vo.UserDrawCashDataVO;
import com.ph.shopping.facade.profit.vo.UserDrawCashVO;
import com.ph.shopping.facade.spm.constant.CachConstant;
import com.ph.shopping.facade.spm.service.IUserBalanceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 
 * @ClassName: UserDrawCashServiceImpl
 * @Description: 供链余额实现类
 * @author 王强
 * @date 2017年6月12日 上午11:14:01
 */
@Component
@Service(version = "1.0.0")
public class UserDrawCashServiceImpl implements IUserDrawCashService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private IUserDrawCashMapper iUserDrawCashMapper;

	@Reference(version = "1.0.0")
	private IUserBalanceService iUserBalanceService;

	@Reference(version = "1.0.0")
	private ICashService iCashService;
	
	@Autowired
	private RedisCacheService<String, Long> redisCacheService;

	@Override
	public Result getUserDrawCahsList(UserDrawCashDTO userDrawCashDTO) {
		try {
			PageHelper.startPage(userDrawCashDTO.getPageNum(), userDrawCashDTO.getPageSize());
			List<UserDrawCashVO> userDrawCashVOs = (Page<UserDrawCashVO>) iUserDrawCashMapper
					.queryUserDrawCashes(userDrawCashDTO);
			PageInfo<UserDrawCashVO> pageInfo = new PageInfo<UserDrawCashVO>(userDrawCashVOs);
			for (UserDrawCashVO userDrawCashVO : pageInfo.getList()) {
				userDrawCashVO.setScore(
						MoneyTransUtil.stringFormat(MoneyTransUtil.transDivisDouble(userDrawCashVO.getScore1())));
				userDrawCashVO.setHandingCharge(MoneyTransUtil
						.stringFormat(MoneyTransUtil.transDivisDouble(userDrawCashVO.getHandingCharge1())));
				// 平衡差
				userDrawCashVO.setBalanceDifference("0.00");
			}

			return ResultUtil.getResult(RespCode.Code.SUCCESS, pageInfo.getList(), pageInfo.getTotal());
		} catch (Exception e) {
			logger.error("查询供链提现失败", e);
			return ResultUtil.getResult(RespCode.Code.FAIL, null, 0l);
		}
	}

	@Override
	public List<UserDrawCashVO> getExportData(UserDrawCashDTO userDrawCashDTO) {
		List<UserDrawCashVO> userDrawCashVOs = iUserDrawCashMapper.queryUserDrawCashes(userDrawCashDTO);
		for (UserDrawCashVO userDrawCashVO : userDrawCashVOs) {
			userDrawCashVO
					.setScore(MoneyTransUtil.stringFormat(MoneyTransUtil.transDivisDouble(userDrawCashVO.getScore1())));
			userDrawCashVO.setHandingCharge(
					MoneyTransUtil.stringFormat(MoneyTransUtil.transDivisDouble(userDrawCashVO.getHandingCharge1())));
			
			userDrawCashVO.setCreateTime1(DateUtil.dateToStr(userDrawCashVO.getCreateTime(), DateUtil.DATETIME_FORMAR_STRING));
			userDrawCashVO.setOperatorCheckTime1(DateUtil.dateToStr(userDrawCashVO.getOperatorCheckTime(), DateUtil.DATETIME_FORMAR_STRING));
			userDrawCashVO.setTreasurerCheckTime1(DateUtil.dateToStr(userDrawCashVO.getTreasurerCheckTime(), DateUtil.DATETIME_FORMAR_STRING));
			// 平衡差
			userDrawCashVO.setBalanceDifference("0.00");
		}
		return userDrawCashVOs;
	}

	@Override
	public Result doAuditOperator(AuditDTO auditDTO) {
		try {
			logger.info("用户提现审核操作入参:auditDTO = " + JSON.toJSONString(auditDTO));
			// 用户提现查询提现数据
			UserDrawCashDataVO drawCashDataVO = iUserDrawCashMapper.getUserDrawCashData(auditDTO.getDrawCashId());

			// 审核状态为4并且角色ID为财务的才能发起提现请求
			if (auditDTO.getAuditState() == 4 && auditDTO.getAuditRgint() == RoleIDEnum.TREASURER.getId()) {
				String bathNo = UniqueUtils.getBathNo();
				DefrayDTO defrayDTO = new DefrayDTO(auditDTO.getDrawCashId().toString(), bathNo,
						drawCashDataVO.getBankNo(), drawCashDataVO.getReceiver(), drawCashDataVO.getScore().toString(),
						drawCashDataVO.getBankName(), drawCashDataVO.getOrderNo());
				Result result1 = iCashService.defray(defrayDTO);
				if(result1 != null) {
					if (!result1.isSuccess()) {
						return result1;
					}
				}
			}

			int res = iUserDrawCashMapper.auditOperator(auditDTO);
			if (res != 1) {
				return ResultUtil.getResult(ProfitExceptionEnum.AUDIT_FAIL);
			} else {
				if (auditDTO.getType() == 2) {
					logger.info("=====审核不通过返回用户余额=====");
					backUserScore(auditDTO.getOrderNo());
				}
				return ResultUtil.getResult(ProfitExceptionEnum.AUDIT_SUCCESS);
			}

		} catch (Exception e) {
			logger.error("审核异常", e);
			return ResultUtil.getResult(ProfitExceptionEnum.AUDIT_EXCEPTION);
		}
	}

	@Override
	public Result updStatus(int status, String orderNo, String tradeState) {
		// 判断提现记录状态是否成功
		logger.info("更新用户提现订单状态入参:status=" + status + ",orderNo=" + orderNo + ",tradeState=" + tradeState);
		int total = iUserDrawCashMapper.countDrawCashTotal(orderNo);
		if (total == 1) {
			logger.info("-----------------------已支付订单无需再支付---------------------");
			return null;
		}

		int res = iUserDrawCashMapper.updateStatus(status, orderNo, tradeState);
		if (res == 1) {
			logger.info("-----------------------提现成功，修改状态成功---------------------");
			return ResultUtil.getResult(RespCode.Code.SUCCESS);
		} else {
			logger.info("-----------------------提现成功，修改状态失败---------------------");
			return ResultUtil.getResult(RespCode.Code.FAIL);
		}
	}

	@Override
	public void backUserScore(String orderNo) {
		try {
			BackUserDrawCashVO backDrawCashVO = iUserDrawCashMapper.getBackDrawCashData(orderNo);
			Object obj = redisCacheService.get(CachConstant.DRAWCASHKEY + backDrawCashVO.getUserId());
			logger.info("返回前的金额：" + obj.toString());
			if(obj != null) {
				Long value = (Long) obj;
				logger.info("需要返回的金额:" + backDrawCashVO.getScore() / 10000);
				Long enbleValue = value - backDrawCashVO.getScore() / 10000;
//				redisCacheService.remove(CachConstant.DRAWCASHKEY + backDrawCashVO.getUserId());
				logger.info("继续缓存的金额为：" + enbleValue);
				redisCacheService.set(CachConstant.DRAWCASHKEY + backDrawCashVO.getUserId(), enbleValue, CachConstant.SECONDS,
						TimeUnit.SECONDS);
			}
			
			iUserBalanceService.userBalanceTrade(backDrawCashVO.getUserId(), TransCodeEnum.USER_DRAWCASH_ORDER_FAIL,
					backDrawCashVO.getScore(), orderNo, 0L, backDrawCashVO.getUserType());
		} catch (Exception e) {
			logger.error("返回用户余额失败", e);
		}
	}

}
