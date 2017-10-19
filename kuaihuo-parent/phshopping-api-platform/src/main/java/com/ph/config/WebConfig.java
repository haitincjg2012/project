package com.ph.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @项目：phshopping-api-platform
 *
 * @描述：后台web项目配置
 *
 * @作者： Mr.chang
 *
 * @创建时间：2017年3月14日
 *
 * @Copyright @2017 by Mr.chang
 */
@EnableWebMvc
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter{

	/**
	 * 静态资源拦截器
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		 registry.addResourceHandler("/static/**","/favicon.ico").addResourceLocations("classpath:/static/","/favicon.ico");
		 super.addResourceHandlers(registry);
	}
	
	/**
	 * 登录拦截器
	 */
/*	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new LoginInterceptor()).
		addPathPatterns("/**").
		excludePathPatterns("/login","/doLogin","/member/usersync/**","/api/pay/**","/web/hunter/getprovinces","/web/hunter/getcities",
				"/web/hunter/getcounties","/web/hunter/gettowns","/web/hunter/addhunterprofit","/web/hunter/findarea",
				"/web/hunter/findarea/findtownlist");
	}*/
	
	

}
