package com.alqsoft.service.infotemplate;

import org.dom4j.DocumentException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alqsoft.entity.infotemplate.EmailTemplate;

/**
 * 
 * @Title: templateTest.java
 * @Description: TODO
 * @author 俞旺
 * @e-mail 13606024548@139.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2015年4月20日 下午5:51:10
 * Copyright © 2013 厦门卓讯信息技术有限公司 All rights reserved.
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath*:/applicationContext.xml")
public class templateTest {
	
	@Autowired
	private EmailTemplateService emailTemplateService;
	@Test
	public void testSaveAndModify() throws DocumentException {
		EmailTemplate te = new EmailTemplate();
		te.setEmailContent("${123}456");
		te.setEmailEnglishName("hello");
		te.setEmailTitle("测试一下");
		te.setEmailVariableTags("${123}");
		emailTemplateService.saveAndModify(te);
	}
}
