package com.alqsoft.utils.sms;

import java.util.Calendar;
import java.util.Map;

import org.alqframework.string.MyStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import com.alqsoft.dao.smspush.SmsPushDao;
import com.alqsoft.dao.smspush.SmsPushLogDao;
import com.alqsoft.entity.infotemplate.SmsTemplate;
import com.alqsoft.entity.smspush.SmsPush;
import com.alqsoft.entity.smspush.SmsPushLog;
import com.alqsoft.init.InitParam;

/**
 * 短信通用类
 * @Title: SmsUtil.java
 * @Description: TODO
 * @author 俞旺
 * @e-mail 13606024548@139.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2015年4月22日 上午11:34:42
 * Copyright © 2013 厦门卓讯信息技术有限公司 All rights reserved.
 * 
 */
@Component
public class SmsUtil {
	private static Logger logger = LoggerFactory.getLogger(SmsUtil.class);
	@Autowired
	private SmsPushDao smsPushDao;
	@Autowired
	private SmsPushLogDao smsPushLogDao;
	@Autowired
	private StringRedisTemplate redisTemplate;
	@Autowired
	private InitParam initParam;
	/**
	 * 生成未发送短信
	 * @Title: createSms
	 * @Description: TODO
	 * @param: @param template
	 * @param: @param map
	 * @param: @param memberName
	 * @param: @return
	 * @return: boolean
	 * @throws
	 */
	public boolean createSms(SmsTemplate template,Map<String, String> map,String memberName)
	{
		try
		{
			if(template!=null&&StringUtils.isNotBlank(memberName))
			{
				//创建一个未发送短信对象
				SmsPush push = new SmsPush();
				String content = MyStringUtils.placeholderString(map, template.getSmsEnglishName());
				long size = 100L;//默认值
				size = Long.parseLong(initParam.getConstantMap().get("INFO_CONTENT_MAX_LENTH"));
				if(content.length()>size)
				{
					return false;
				}
				Calendar calendar = Calendar.getInstance();
				push.setContent(content);
				push.setDay(calendar.get(Calendar.DAY_OF_MONTH));
				push.setMonth(calendar.get(Calendar.MONTH)+1);
				push.setYear(calendar.get(Calendar.YEAR));
				push.setDescriber("测试看看");
				push.setSmsReceiveName(memberName);
				push.setState(0);//发送状态 未发送
				smsPushDao.save(push);
				return true;
			}
		}catch(Exception e)
		{
			logger.error(e.getMessage());
			return false;
		}
		return false;
	}
	/**
	 * 短信发送数据库操作
	 * @Title: pushToLog
	 * @Description: TODO
	 * @param: @param push
	 * @param: @return
	 * @return: boolean
	 * @throws
	 */
	public boolean pushToLog(SmsPush push)
	{
		try
		{
		SmsPushLog log = new SmsPushLog();
		if(push!=null)
		{
			log.setContent(push.getContent());
			log.setDay(push.getDay());
			log.setMonth(push.getMonth());
			log.setYear(push.getYear());
			log.setSmsReceiveName(push.getSmsReceiveName());
			log.setState(1);
			smsPushDao.delete(push);
			smsPushLogDao.save(log);
			return true;
		}
		}catch(Exception e)
		{
			logger.error(e.getMessage());
			return false;
		}
		return false;
	}
}
