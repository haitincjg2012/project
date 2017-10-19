/*package com.alqsoft.scheduler.sms;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.alqsoft.dao.smspush.SmsPushDao;
import com.alqsoft.entity.smspush.SmsPush;
import com.alqsoft.utils.sms.SmsUtil;

*//**
 * 短信发送定时作业
 * @Title: SmsTask.java
 * @Description: TODO
 * @author 俞旺
 * @e-mail 13606024548@139.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2015年4月22日 下午6:23:17
 * Copyright © 2013 厦门卓讯信息技术有限公司 All rights reserved.
 * 
 *//*
@Component
@Lazy(value=false)
public class SmsTask {
	@Autowired
	private SmsUtil smsUtil;
	@Autowired
	private SmsPushDao smsPushDao;
	
	@Scheduled(cron="0 0 0/1 * * *")
	public void sendTask()
	{
		//获取前十条未发送邮件
		List<SmsPush> list = smsPushDao.findSmsPushByNum(10);
		
		list.parallelStream().forEach(smsPush->{
			//数据表数据的交互
			smsUtil.pushToLog(smsPush);
		});
	}
}
*/