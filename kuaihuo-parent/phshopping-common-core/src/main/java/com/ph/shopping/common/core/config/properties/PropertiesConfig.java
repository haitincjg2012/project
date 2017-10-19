package com.ph.shopping.common.core.config.properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

import com.ph.shopping.common.core.other.BankCardCheckService;
import com.ph.shopping.common.core.other.IdAuthService;
import com.ph.shopping.common.core.other.sms.SmsCodeService;

/**
 * 
 * 
 * @ClassName:  PropertiesConfig   
 * @Description:properties 相关配置   
 * @author: 李杰
 * @date:   2017年4月28日 上午9:33:29     
 * @Copyright: 2017
 */
@Configuration
@PropertySources({
	@PropertySource(value = "classpath:smsconfig.properties", encoding = "utf-8")
})
public class PropertiesConfig {

	/**
	 * 
	 * @Title: backCardCheckService   
	 * @Description:银行卡件认证服务 
	 * @param: @return      
	 * @return: BackCardCheckService      
	 * @throws
	 */
	@Bean
	public BankCardCheckService backCardCheckService(){
		
		return new BankCardCheckService();
	}
	
	/**
	 * 
	* @Title: smsCodeService  
	* @Description: 短信service  
	* @param @return    参数  
	* @return SmsCodeService    返回类型  
	* @throws
	 */
	@Bean
	public SmsCodeService smsCodeService(){
		
		return new SmsCodeService();
	}
	/**
	 * 
	* @Title: authService  
	* @Description: 身份证件认证  
	* @param @return    参数  
	* @return CertificatesAuthService    返回类型  
	* @throws
	 */
	@Bean
	public IdAuthService authService(){
		
		return new IdAuthService();
	}
}
