package com.ph.shopping.facade.scheduler;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.profit.service.IUnlineOrderProfitService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class UnLineOrderProfitScheduler  {

	//@Reference(version = "1.0.0")
	@Autowired
	private IUnlineOrderProfitService iUnlineOrderProfitService;
	// 创建日志
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@Scheduled(cron = "0/30 * * * * ?")    //分润定时任务 （实时分润）
	public void doManagerProfit() {
		logger.debug("线下订单分润定时器开始");
		try {
			Result result = iUnlineOrderProfitService.UnLineOrderProfit();
			if(result.getCode()=="200"){
				logger.info("线下订单分润成功");
			}
		} catch (Exception e) {
			logger.error("线下订单分润异常");
			e.printStackTrace();
		}
	}

	
	@Scheduled(cron = "0/1 * * * * ?") //每秒执行
	public void doBackScore() {
		logger.debug("返还积分作业开始。。。");
		try {
			Result result = iUnlineOrderProfitService.doBackScore();
			if(result.getCode()=="200"){
				logger.info("返还积分作业成功");
			}
		} catch (Exception e) {
			logger.error("返还积分作业结束。。。");
			e.printStackTrace();
		}
	}
}
