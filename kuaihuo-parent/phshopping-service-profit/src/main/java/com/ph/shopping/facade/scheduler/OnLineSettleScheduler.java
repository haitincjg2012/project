package com.ph.shopping.facade.scheduler;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.ph.shopping.common.core.customenum.RoleEnum;
import com.ph.shopping.common.core.customenum.TransCodeEnum;
import com.ph.shopping.common.util.verifycode.VerifyUtil;
import com.ph.shopping.facade.profit.dto.OnLineSettleDTO;
import com.ph.shopping.facade.profit.service.IOnLineSettleService;
import com.ph.shopping.facade.profit.vo.OnLineSettleVO;
import com.ph.shopping.facade.spm.service.IUserBalanceService;

@Component
public class OnLineSettleScheduler {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private IOnLineSettleService iOnLineSettleService;

	@Reference(version = "1.0.0")
	private IUserBalanceService iUserBalanceService;

	/**
	 * 
	 * @Title: doSettle
	 * @Description: 线上结算定时器执行
	 * @author 王强
	 * @date 2017年6月7日 上午11:18:33
	 */
	//@Scheduled(cron = "0 0 2 * * ?")    //关闭作业
	public void doSettle() {
		long start = System.currentTimeMillis();
		logger.info("线上结算凌晨2点执行定时器开始" + start);
		OnLineSettleDTO lineSettleDTO = new OnLineSettleDTO();
		lineSettleDTO.setStatus(3);
		@SuppressWarnings("unchecked")
		List<OnLineSettleVO> lineSettleVOs = (List<OnLineSettleVO>) iOnLineSettleService.queryOnlineSettles(lineSettleDTO).getData();
		List<String> orderNos = new ArrayList<String>();
		try {
			// 更新用户余额
			logger.info("查询到的线上订单:" + JSON.toJSONString(lineSettleVOs));
			for (OnLineSettleVO lineSettleVO : lineSettleVOs) {
				if (lineSettleVO != null) {
					
					// 单条执行更新订单状态
					iOnLineSettleService.updateOnLineOrderSingleIsSettle(lineSettleVO.getOrderNo());
					
					iUserBalanceService.userBalanceTrade(lineSettleVO.getUserId(), TransCodeEnum.ONLINE_ORDER_SETTLE,
							lineSettleVO.getSettlementMoney1() + lineSettleVO.getLogisticsMoney1(),
							lineSettleVO.getOrderNo(), 0l, RoleEnum.SUPPLIER.getCode());
					// 添加订单号
					orderNos.add(lineSettleVO.getOrderNo());
				}
			}
//			// 批量更新结算状态
//			if (VerifyUtil.listIsNotNull(orderNos)) {
//				logger.info("订单号集合：" + orderNos);
//				iOnLineSettleService.updateOnLineOrderIsSettle(orderNos);
//			} else {
//				logger.warn("暂无线上订单需要更新状态");
//			}

			logger.info("执行定时器结束消耗时间: " + (System.currentTimeMillis() - start));
		} catch (Exception e) {
			logger.error("定时器循环异常", e);
		}
	}
}
