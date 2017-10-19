/**  
 * @Title:  CacheEnum.java   
 * @Package com.ph.custom.cenum   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 李杰    
 * @date:   2017年5月14日 下午11:25:47   
 * @version V1.0 
 * @Copyright: 2017
 */  
package com.ph.shopping.common.core.customenum;

import java.util.concurrent.TimeUnit;

/**   
 * @ClassName:  CacheEnum   
 * @Description:缓存相关枚举
 * @author: 李杰
 * @date:   2017年5月14日 下午11:25:47     
 * @Copyright: 2017
 */
public enum CacheConfigEnum {
	/**
	 * 用户缓存最大时间(单位分钟)
	 */
	USER_MAX_TIME(120l,TimeUnit.MINUTES),
	/**
	 * 短信最大缓存时间
	 */
	SMS_MAX_TIME(5l,TimeUnit.MINUTES),
	/**
	 * 商城用户缓存最大时间(单位分钟)
	 */
	SHOPPING_MAX_TIME(30l,TimeUnit.MINUTES),
	/**
	 * 用户缓存时间临界值(单位分钟)，小于10分钟，则改为30分钟，提高用户体验
	 */
	SHOPPING_LOWER_TIME(10l,TimeUnit.MINUTES),
	;
	
	/**
	 * 缓存时长
	 */
	private long duration;
	/**
	 * 时间单位
	 */
	private TimeUnit unit;
	
	private CacheConfigEnum(long duration,TimeUnit unit){
		this.duration = duration;
		this.unit = unit;
	}
	public long getDuration() {
		return duration;
	}
	public void setDuration(long duration) {
		this.duration = duration;
	}
	public TimeUnit getUnit() {
		return unit;
	}
	public void setUnit(TimeUnit unit) {
		this.unit = unit;
	}
	
}
