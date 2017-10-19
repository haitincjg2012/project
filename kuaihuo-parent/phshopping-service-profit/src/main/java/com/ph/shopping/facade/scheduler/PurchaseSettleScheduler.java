package com.ph.shopping.facade.scheduler;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.profit.service.IPurchaseSettleService;
import com.ph.shopping.facade.profit.service.IUnLineSettleService;          
/**
 * 供应链结算定时器
* @ClassName: PurchaseSettleScheduler
* @Description: TODO(这里用一句话描述这个类的作用)
* @author Mr.Dong
* @date 2017年6月8日 上午9:48:19
 */
@Component
public class PurchaseSettleScheduler {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	//@Reference(version = "1.0.0")
	@Autowired
	private IPurchaseSettleService iPurchaseSettleService;
	
	
	//@Scheduled(cron="0 0 4 * * ?")   //关闭供应链结算 （供应链模块取消）
	public void doSettle() {
		logger.debug("每天凌晨4点执行---------->开始—---供应链结算定时器");
		try {
			Result result = iPurchaseSettleService.doPurchaseSettle();
			if(result.getCode()=="200"){
				logger.info("线下订单结算成功");
			}
		} catch (Exception e) {
			logger.error("线下订单结算异常");
			e.printStackTrace();
		}
	}
}
