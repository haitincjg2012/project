package com.phshopping.api.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.ph.shopping.common.core.cache.redis.ICacheService;
import com.ph.shopping.common.core.customenum.RoleEnum;

public class BaseMerchantController {
	
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private ICacheService<String, String> redisService;

	/**
	 * 得到request对象
	 */
	protected HttpServletRequest getRequest() {
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
	}

	/**
	 * 得到response对象
	 */
	protected HttpServletResponse getResponse() {
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
	}
	
	protected Long getUserId() {
		// 获取商户id
		String merchantAppKey = redisService.get(getMerchantAppKey(getRequest().getHeader("token"), RoleEnum.MERCHANT))
				.toString();
		logger.info("缓存中获取的值:" + merchantAppKey);
		return Long.valueOf(merchantAppKey);
	}
	
	/**
	 * 获取redis的key值
	 * 
	 * @Title: getMerchantAppKey
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @author Mr.Dong
	 * @date 2017年6月21日 上午10:43:55
	 * @param mobile
	 * @param roleEnum
	 * @return
	 */
	protected String getMerchantAppKey(String token, RoleEnum roleEnum) {
		StringBuilder bud = new StringBuilder(roleEnum.name()).append("_").append(token);
		return bud.toString();
	}
}
