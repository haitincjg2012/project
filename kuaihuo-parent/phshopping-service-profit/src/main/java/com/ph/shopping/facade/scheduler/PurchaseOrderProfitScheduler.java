package com.ph.shopping.facade.scheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.profit.service.IPurchaseOrderProfitService;

/**
 * @ClassName: 商户供应链分润定时器
 * @author Mr.Dong
 * @date 2017年4月26日 下午4:34:40
 */
@Component
public class PurchaseOrderProfitScheduler  {
	// 创建日志
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	//@Reference(version = "1.0.0")
	@Autowired
	private  IPurchaseOrderProfitService iPurchaseOrderProfitService;

	//@Scheduled(cron = "0 0 3 * * ?")  //关闭作业
	public void doProfit() {
		logger.debug("供应链分润凌晨3点执行。。。。。。。。。。。。。开始");
		try {
			Result result = iPurchaseOrderProfitService.purchaseOrderProfit();
			if(result.getCode()=="200"){
				logger.info("供应链分润成功");
			}
		} catch (Exception e) {
			logger.error("供应链分润失败");
			e.printStackTrace();
		}
	}
	
}

