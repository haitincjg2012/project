package com.alqsoft.mail;

import java.io.File;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath*:/applicationContext.xml")
public class MailTest {
	
//	可以直接new对象
	@Autowired
	private JavaMailSender mailSender;
//	可以直接new对象	
	@Autowired
	private SimpleMailMessage mailMessage;
	
	@Test
	public void testMail(){
		mailMessage.setText("这是个测试邮件！！！");
		mailSender.send(mailMessage);
	}
	/**
	 * 上传福附件
	 * @throws MessagingException 
	 */
	@Test
	public void testMimeMail() throws MessagingException{
		MimeMessage mm = mailSender.createMimeMessage();
//		true代表 传送附件
		MimeMessageHelper helper = new MimeMessageHelper(mm,true);
//		发送邮箱必须有
		helper.setFrom("1023059764@qq.com");
//		接受邮箱必须有
		helper.setTo("1023059764@qq.com");
		helper.setText("发送附件");
		FileSystemResource file = new FileSystemResource(new File("d:/a.txt"));
		helper.addAttachment("测试文件", file);
		mailSender.send(mm);
	}
	
}
