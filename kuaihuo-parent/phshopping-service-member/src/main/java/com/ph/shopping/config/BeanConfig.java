package com.ph.shopping.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ph.shopping.facade.member.service.user.LoginRegisterCheckService;

/**
 * 
* @ClassName: BeanConfig  
* @Description: spring bean config service 
* @author lijie  
* @date 2017年3月15日  
*
 */
@Configuration
public class BeanConfig {
	
	/**
	 * 
	* @Title: loginRegService  
	* @Description: 登录注册外部接口哦调用服务
	* @param @return    参数  
	* @return LoginRegisterCheckService    返回类型  
	* @throws
	 */
	@Bean
	public LoginRegisterCheckService loginRegService(){
		
		return new LoginRegisterCheckService();
	}
}
