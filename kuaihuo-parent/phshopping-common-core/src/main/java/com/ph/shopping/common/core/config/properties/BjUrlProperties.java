/**  
 * @Title:  BjUrlProperties.java   
 * @Package com.ph.shopping.common.core.config.properties   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 李杰    
 * @date:   2017年7月11日 下午2:08:18   
 * @version V1.0 
 * @Copyright: 2017
 */  
package com.ph.shopping.common.core.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**   
 * @ClassName:  BjUrlProperties   
 * @Description:北京提供的连接地址   
 * @author: 李杰
 * @date:   2017年7月11日 下午2:08:18     
 * @Copyright: 2017
 */
@PropertySource("classpath:bjurl.properties")
@ConfigurationProperties(prefix = "bj")
@Component
public class BjUrlProperties {

	/**
	 * 注册校验url
	 */
	private String registerCheckUrl;
	/**
	 * 登录校验url
	 */
	private String loginCheckUrl;
	/**
	 * 添加批发商url
	 */
	private String addHeadhuntingUrl;
	/**
	 * 获取批发商url
	 */
	private String getHeadhuntingUrl;
	
	public String getRegisterCheckUrl() {
		return registerCheckUrl == null ? null : registerCheckUrl.trim();
	}

	public void setRegisterCheckUrl(String registerCheckUrl) {
		this.registerCheckUrl = registerCheckUrl;
	}

	public String getLoginCheckUrl() {
		return loginCheckUrl == null ? null : loginCheckUrl.trim();
	}

	public void setLoginCheckUrl(String loginCheckUrl) {
		this.loginCheckUrl = loginCheckUrl;
	}

	public String getAddHeadhuntingUrl() {
		return addHeadhuntingUrl == null ? null : addHeadhuntingUrl.trim();
	}

	public void setAddHeadhuntingUrl(String addHeadhuntingUrl) {
		this.addHeadhuntingUrl = addHeadhuntingUrl;
	}

	public String getGetHeadhuntingUrl() {
		return getHeadhuntingUrl == null ? null : getHeadhuntingUrl.trim();
	}

	public void setGetHeadhuntingUrl(String getHeadhuntingUrl) {
		this.getHeadhuntingUrl = getHeadhuntingUrl;
	}
}
