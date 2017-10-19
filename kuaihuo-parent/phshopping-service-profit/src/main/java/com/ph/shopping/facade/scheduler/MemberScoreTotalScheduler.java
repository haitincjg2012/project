package com.ph.shopping.facade.scheduler;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.profit.service.IMemberScoreService;
import com.ph.shopping.facade.profit.service.IUnLineSettleService;

/**
 * 会员积分统计定时器
* @ClassName: UnLineSettleScheduler
* @Description: TODO(这里用一句话描述这个类的作用)
* @author Mr.Dong
* @date 2017年6月8日 上午9:48:19
 */
@Component
public class MemberScoreTotalScheduler {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	//@Reference(version="1.0.0")
	@Autowired
	private IMemberScoreService iMemberScoreService;
	
	
	//@Scheduled(cron = "0 10 6 * * ?")   关闭作业
	public void doStatistics() {
		logger.debug("会员积分统计每天早上6点10分执行---------->开始");
		try {
			Result result = iMemberScoreService.memberScoreStatistics();
			if(result.getCode()=="200"){
				logger.info("会员积分统计成功");
			}
		} catch (Exception e) {
			logger.error("会员积分统计异常");
			e.printStackTrace();
		}
	}
}
