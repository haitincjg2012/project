package com.ph.shopping.facade.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ph.shopping.common.util.result.Result;

import cm.ph.shopping.facade.order.service.IOnlineOrderService;

/**
 * @ClassName: 线上订单15分钟未支付  自动关闭
 * @author chen
 * @date 2017年8月22日09:15:12
 */
@Component
public class OrderOnlineCloseScheduler  {
	// 创建日志
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private  IOnlineOrderService iOnlineOrderService;

	//@Scheduled(cron = "0/1 * * * * *")
	public void doProfit() {
		try {
			Result result = iOnlineOrderService.onlineOrderClose();
			if(result.getCode()=="200"){
				logger.info("线上订单自动取消成功");
			}
		} catch (Exception e) {
			logger.error("线上订单自动取消失败");
			e.printStackTrace();
		}
	}
	
}
