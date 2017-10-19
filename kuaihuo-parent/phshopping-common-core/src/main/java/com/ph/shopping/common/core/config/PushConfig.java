package com.ph.shopping.common.core.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * 推送
 * @author xwolf
 * @since 1.8
 **/
@PropertySource(value ="classpath:push.properties")
@Configuration
public class PushConfig {

    //日志对象
    private static Logger log = LoggerFactory.getLogger(PushConfig.class);

    //推送url
    @Value("${jpush.url}")
    private String url;

    public String getUrl() {
        return url;
    }
}
