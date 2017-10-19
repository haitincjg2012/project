/**  
 * @Title:  SmsProperties.java   
 * @Package com.wholesale.config.properties   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 李杰    
 * @date:   2017年7月5日 上午9:41:19   
 * @version V1.0 
 * @Copyright: 2017
 */  
package com.ph.shopping.common.core.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**   
 * @ClassName:  SmsProperties   
 * @Description:短息相关配置   
 * @author: 李杰
 * @date:   2017年7月5日 上午9:41:19     
 * @Copyright: 2017
 */
@PropertySource("classpath:sms.properties")
@ConfigurationProperties(prefix = "sms")
@Component
public class SmsProperties {
	/**
	 * 云片appkey
	 */
	private String ypApikey;
	/**
	 * 云片短信url
	 */
	private String ypUrl;
	/**
	 * 最大缓存时间
	 */
	private Long maxTime;
	
	public String getYpApikey() {
		return ypApikey == null ? null : ypApikey.trim();
	}

	public void setYpApikey(String ypApikey) {
		this.ypApikey = ypApikey;
	}

	public String getYpUrl() {
		return ypUrl == null ? null : ypUrl.trim();
	}

	public void setYpUrl(String ypUrl) {
		this.ypUrl = ypUrl;
	}

	public Long getMaxTime() {
		return maxTime;
	}

	public void setMaxTime(Long maxTime) {
		this.maxTime = maxTime;
	}
	
}
