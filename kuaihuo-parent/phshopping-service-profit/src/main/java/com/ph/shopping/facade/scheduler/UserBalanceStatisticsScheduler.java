package com.ph.shopping.facade.scheduler;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.profit.service.IUserBalanceStatisticsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @项目：phshopping-facade-order
 * @描述：定时统计供应链余额数据
 * @作者： 张霞
 * @创建时间： 16:32 2017/7/5
 * @Copyright @2017 by zhangxia
 */
@Component
public class UserBalanceStatisticsScheduler {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    //@Reference(version = "1.0.0")
    @Autowired
    private IUserBalanceStatisticsService iUserBalanceStatisticsService;

    //@Scheduled(cron="0 1 7 * * ?")   //关闭作业
    public void doStatistics() {
        logger.debug("定时统计供应链余额数据每天早上7点1分执行---------->开始");
        Result result = iUserBalanceStatisticsService.doStatistics();
        if(result.getCode()=="200"){
            logger.info("定时统计供应链余额数据成功");
        }else {
            logger.info("定时统计供应链余额数据失败");
        }
    }
}
