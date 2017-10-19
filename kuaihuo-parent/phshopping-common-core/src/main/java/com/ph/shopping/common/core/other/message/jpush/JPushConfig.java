package com.ph.shopping.common.core.other.message.jpush;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ph.shopping.common.core.config.properties.PushProperties;

import cn.jiguang.common.ClientConfig;
import cn.jpush.api.JPushClient;
/**
 * 
 * @ClassName:  JPushConfig   
 * @Description:消息推送相关配置   
 * @author: 李杰
 * @date:   2017年5月27日 上午10:30:05     
 * @Copyright: 2017
 */
@Component
public class JPushConfig {
	
	private JPushClient buyerJpushClient;
	/**
	 * 默认离线保存时间
	 */
	private final long defaultTime = 60 * 60 * 24;
	
	@Autowired
	private PushProperties pushConfig;
	/**
	 * 
	 * @Title: init   
	 * @Description: 初始化配置   
	 * @param:       
	 * @return: void
	 * @author：李杰      
	 * @throws
	 */
	private void init(){
		ClientConfig config = getClientConfig();
		buyerJpushClient = new JPushClient(pushConfig.getMasterSecret(), pushConfig.getAppKey(), null, config);
	}
	/**
	 * 
	 * @Title: getPushEnvironment   
	 * @Description: 得到推送环境  
	 * @param: @return      
	 * @return: boolean
	 * @author：李杰      
	 * @throws
	 */
	private boolean isProduction() {
		// 0、测试环境 1、生产环境
		return "1".equals(pushConfig.getEnvironment());
	}
	/**
	 * 
	 * @Title: getTimeToLive   
	 * @Description:得到离线最大保留时间  
	 * @param: @return      
	 * @return: long
	 * @author：李杰      
	 * @throws
	 */
	public long getTimeToLive() {
		Long timeToLive = pushConfig.getTimeToLive();
		return timeToLive == null ? defaultTime : timeToLive;
	}
	/**
	 * @Title: getClientConfig   
	 * @Description: 初始化推送配置   
	 * @param: @return      
	 * @return: ClientConfig
	 * @author：李杰      
	 * @throws
	 */
	private ClientConfig getClientConfig() {
		ClientConfig config = ClientConfig.getInstance();
		config.setMaxRetryTimes(pushConfig.getMaxRetryTimes());
		config.setApnsProduction(isProduction());
		config.setTimeToLive(getTimeToLive());
		return config;
	}
	/**
	 * 
	 * @Title: getBuyerJpushClient   
	 * @Description: 得到推送客服端实例   
	 * @param: @return      
	 * @return: JPushClient
	 * @author：李杰      
	 * @throws
	 */
	public JPushClient getBuyerJpushClient() {
		init();
		return buyerJpushClient;
	}

	public void setBuyerJpushClient(JPushClient buyerJpushClient) {
		this.buyerJpushClient = buyerJpushClient;
	}
}
