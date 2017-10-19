package com.phshopping.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @项目：phshopping-parent
 * @描述：跨域配置
 * @作者： Mr.chang
 * @创建时间：2017/6/8
 * @Copyright @2017 by Mr.chang
 */
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter{

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowCredentials(true)
                .allowedMethods("GET", "POST")
                .maxAge(3600);
    }
}
