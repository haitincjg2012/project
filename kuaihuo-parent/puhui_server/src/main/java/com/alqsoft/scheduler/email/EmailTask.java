package com.alqsoft.scheduler.email;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.alqsoft.dao.emailpush.EmailPushDao;
import com.alqsoft.entity.emailpush.EmailPush;
import com.alqsoft.utils.email.EmailUtil;

/**
 * 邮件发送定时作业
 * 
 * @Title: EmailTask.java
 * @Description: TODO
 * @author 俞旺
 * @e-mail 13606024548@139.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2015年4月22日 下午3:12:33 Copyright © 2013 厦门卓讯信息技术有限公司 All rights
 *              reserved.
 * 
 */
@Component
@Lazy(value = false)
public class EmailTask {
	@Autowired
	private EmailUtil emailUtil;
	@Autowired
	private EmailPushDao emailPushDao;

	@Scheduled(cron = "0 0/1 * * * *")
	public void sendTask() {
		// 获取前十条未发送邮件
		List<EmailPush> list = emailPushDao.findEmailPushByNum(10);

		list.parallelStream().forEach(emailPush -> {
			// 执行发送操作
				emailUtil.SendMessage(emailPush);
				// 数据表数据的交互
				emailUtil.pushToLog(emailPush);
			});
		

	}
}
