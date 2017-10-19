package com.alqsoft.utils.email;

import java.util.Calendar;
import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.alqframework.string.MyStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.alqsoft.dao.emailpush.EmailPushDao;
import com.alqsoft.dao.emailpush.EmailPushLogDao;
import com.alqsoft.entity.emailpush.EmailPush;
import com.alqsoft.entity.emailpush.EmailPushLog;
import com.alqsoft.entity.infotemplate.EmailTemplate;

/**
 * 邮件通用类 增加未发送邮件信息
 * @Title: EmailUtil.java
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
public class EmailUtil {
	private static Logger logger = LoggerFactory.getLogger(EmailUtil.class);
	@Autowired
	private SimpleMailMessage mailMessage;
	@Autowired
	private JavaMailSenderImpl mailSender;
	@Autowired
	private EmailPushDao emailPushDao;
	@Autowired
	private EmailPushLogDao emailPushLogDao;
	/**
	 * 生成未发送邮件
	 * @Title: createEmail
	 * @Description: TODO
	 * @param: @param template
	 * @param: @param map
	 * @param: @param memberName
	 * @param: @return
	 * @return: boolean
	 * @throws
	 */
	public boolean createEmail(EmailTemplate template,Map<String, String> map,String memberName)
	{
		try
		{
			if(template!=null&&StringUtils.isNotBlank(memberName))
			{
				//创建一个未发送邮件对象
				EmailPush push = new EmailPush();
				String content = MyStringUtils.placeholderString(map, template.getEmailContent());
				Calendar calendar = Calendar.getInstance();
				push.setContent(content);
				push.setDay(calendar.get(Calendar.DAY_OF_MONTH));
				push.setMonth(calendar.get(Calendar.MONTH)+1);
				push.setYear(calendar.get(Calendar.YEAR));
				push.setDescriber("测试看看");
				push.setEmailReceiveName(memberName);
				push.setState(0);//发送状态 未发送
				emailPushDao.save(push);
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
	 * 邮件发送数据库操作
	 * @Title: pushToLog
	 * @Description: TODO
	 * @param: @param push
	 * @param: @return
	 * @return: boolean
	 * @throws
	 */
	public boolean pushToLog(EmailPush push)
	{
		try
		{
		EmailPushLog log = new EmailPushLog();
		if(push!=null)
		{
			log.setContent(push.getContent());
			log.setDay(push.getDay());
			log.setMonth(push.getMonth());
			log.setYear(push.getYear());
			log.setEmailReceiveName(push.getEmailReceiveName());
			log.setState(1);
			emailPushDao.delete(push);
			emailPushLogDao.save(log);
			return true;
		}
		}catch(Exception e)
		{
			logger.error(e.getMessage());
			return false;
		}
		return false;
	}
	
	
	public void SendMessage(EmailPush push){
		try {
			// 得到消息对象
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			// 建立帮助对象
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
			// 设置发件人
			mimeMessageHelper.setFrom(mailMessage.getFrom(),"tigofood-太古粮家");
			// 设置收件人
			mimeMessageHelper.setTo(new String[] {push.getEmailReceiveName()});
			// 设置邮件主题
			mimeMessageHelper.setSubject("测试");
			// 设置内容
			mimeMessageHelper.setText(new String(push.getContent().getBytes(), "ISO-8859-1"), true);
			mailSender.send(mimeMessage);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("发送邮件失败");
		}	
		}
	
}
