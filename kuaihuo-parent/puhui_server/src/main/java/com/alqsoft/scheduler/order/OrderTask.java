package com.alqsoft.scheduler.order;

import com.alqsoft.service.order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author Xuejizheng
 * @date 2017-03-13 13:50
 */
@Component
@Lazy(value = false)
public class OrderTask {

    @Autowired
    private OrderService orderService;

    /**
     * 十五天自动确认收货
     */
    //@Scheduled(cron = "0/1 * * * * *")
    public void authConfirmReceive(){
        orderService.autoConfirmReceive();
    }
    
    /**
     * 七天自动分润
     */
    //@Scheduled(cron = "0/1 * * * * *")//每秒执行
    public void orderFenRun(){
        this.orderService.orderFenRun();
    }
    
    /**
     * 普惠分润 10分钟一次
     */
    @Scheduled(cron = "0/10 * * * * *")//10分钟执行
    public void orderPuhuiFenRun(){
        this.orderService.orderPuhuiFenRun();
    }

}
