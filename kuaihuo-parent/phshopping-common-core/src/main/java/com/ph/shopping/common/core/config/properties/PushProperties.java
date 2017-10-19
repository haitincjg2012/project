/**  
 * @Title:  PushConfig.java   
 * @Package com.ph.shopping.common.core.config.properties   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 李杰    
 * @date:   2017年6月29日 下午12:39:38   
 * @version V1.0 
 * @Copyright: 2017
 */
package com.ph.shopping.common.core.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @ClassName: PushConfig
 * @Description:极光推送相关配置
 * @author: 李杰
 * @date: 2017年6月29日 下午12:39:38
 * @Copyright: 2017
 */
@PropertySource("classpath:push.properties")
@ConfigurationProperties(prefix = "jpush")
@Component
public class PushProperties {
	/**
	 * 极光推送appkey
	 */
	private String appKey;
	/**
	 * 极光推送appkey
	 */
	private String masterSecret;
	/**
	 * 指定离线消息保存时间(毫秒)
	 */
	private Long timeToLive;
	/**
	 * 推送环境： 0、测试环境 1、生产环境
	 */
	private String environment;
	/**
	 * 最大重试次数
	 */
	private Integer maxRetryTimes;
	
	public String getAppKey() {
		return appKey == null ? null : appKey.trim();
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	public String getMasterSecret() {
		return masterSecret == null ? null : masterSecret.trim();
	}

	public void setMasterSecret(String masterSecret) {
		this.masterSecret = masterSecret;
	}

	public String getEnvironment() {
		return environment == null ? null : environment.trim();
	}

	public void setEnvironment(String environment) {
		this.environment = environment;
	}

	public Long getTimeToLive() {
		return timeToLive;
	}

	public void setTimeToLive(Long timeToLive) {
		this.timeToLive = timeToLive;
	}

	public Integer getMaxRetryTimes() {
		return maxRetryTimes;
	}

	public void setMaxRetryTimes(Integer maxRetryTimes) {
		this.maxRetryTimes = maxRetryTimes;
	}
}
