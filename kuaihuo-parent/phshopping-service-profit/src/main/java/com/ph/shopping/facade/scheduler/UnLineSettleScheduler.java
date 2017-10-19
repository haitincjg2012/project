package com.ph.shopping.facade.scheduler;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.profit.service.IUnLineSettleService;

/**
 * 线下订单结算定时器
* @ClassName: UnLineSettleScheduler
* @Description: TODO(这里用一句话描述这个类的作用)
* @author Mr.Dong
* @date 2017年6月8日 上午9:48:19
 */
@Component
public class UnLineSettleScheduler {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	//@Reference(version = "1.0.0")
	@Autowired
	private IUnLineSettleService iUnLineSettleService;
	
	
	//@Scheduled(cron="0 0 6 * * ?")  //关闭结算定时任务  （实时结算）
	public void doSettle() {
		logger.debug("线下订单结算每天早上6点执行---------->开始");
		try {
			Result result = iUnLineSettleService.doUnLineSettle();
			if(result.getCode()=="200"){
				logger.info("线下订单结算成功");
			}
		} catch (Exception e) {
			logger.error("线下订单结算异常");
			e.printStackTrace();
		}
	}
}
