package com.alqsoft.redis;

import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

import com.alqsoft.init.InitParam;

/**
 * @Title: AuthCode.java
 * @Description: 验证码保存及发送 
 * @author 陈振兵
 * @e-mail chenzhenbing@139.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2015年4月16日 下午10:20:46
 * Copyright © 2013 厦门卓讯信息技术有限公司 All rights reserved.
 * 
 */
public class AuthCode {
	
	@Autowired
	private StringRedisTemplate redisTemplate;
	@Autowired
	private InitParam initParam;
	
	/**
	 * 验证码保存 类似格式 yzm_123@qq.com_Regist
	 * @Title: getRedis
	 * @Description: TODO
	 * @param: @param memberName  用户名 用来拼接
	 * @param: @param codeType  验证码类型 用来拼接
	 * @return: boolean
	 * @throws
	 */
	public boolean getRedis(String memberName,String codeType){
		try
		{
			StringBuffer key = new StringBuffer("yzm_").append(memberName).append(codeType);
			redisTemplate.opsForValue().set(key.toString(), RandomStringUtils.randomNumeric(6));
			Long time = 6000L;//默认值
			time=Long.parseLong(this.initParam.getConstantMap().get("INFO_TIME_OUT"));
			redisTemplate.expire(key.toString(), time, TimeUnit.SECONDS);//单位：s
			return true;
		}catch(Exception e)
		{
			return false;
		}
	}
	
	/**
	 * 验证码验证操作
	 * @Title: checkRedis
	 * @Description: TODO
	 * @param: @param memberName
	 * @param: @param codeType
	 * @param: @param code  传过来的验证码值
	 * @return: boolean
	 * @throws
	 */
	public boolean checkRedis(String memberName,String codeType,String code)
	{
		try
		{
			StringBuffer key = new StringBuffer("yzm_").append(memberName).append(codeType);
			String realCode = redisTemplate.opsForValue().get(key);
			if(StringUtils.isNotBlank(realCode))
			{
				if(realCode.equals(code))
				{
					return true;
				}
			}
			return false;
		}catch(Exception e)
		{
			return false;
		}
	}
	
	
}
