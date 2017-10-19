package com.ph.shopping.common.core.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * 普惠接口
 * @author xwolf
 * @date 2017-09-01 10:45
 * @since 1.8
 **/
@Configuration
@PropertySource(value = "classpath:puhui.properties")
public class PuhuiConfig {

    /**
     * 添加积分(天天返、刮红包)
     */
    @Value("${puhui.score.add}")
    private String add;


    /**
     * 获取积分
     */
    @Value("${puhui.score.get}")
    private String selectScore;

    /**
     * 积分提现
     */
    @Value("${puhui.score.cash}")
    private String cash;

    public String getAdd() {
        return add;
    }

    public String getCash() {
        return cash;
    }

    public String getSelectScore() {
        return selectScore;
    }
}
