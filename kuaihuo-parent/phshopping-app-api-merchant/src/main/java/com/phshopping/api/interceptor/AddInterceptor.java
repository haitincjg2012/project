package com.phshopping.api.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by Administrator on 2017/9/12.
 */
@Component
public class AddInterceptor extends WebMvcConfigurerAdapter {
    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(new SpringValidateSignatureInterceptor());
    }
}
