package com.alqsoft.service.email;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alqsoft.dao.emailpush.EmailPushDao;
import com.alqsoft.service.infotemplate.EmailTemplateService;
import com.alqsoft.utils.email.EmailUtil;

/**
 * 
 * @Title: EmailTest.java
 * @Description: TODO
 * @author 俞旺
 * @e-mail 13606024548@139.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2015年4月21日 下午4:16:23
 * Copyright © 2013 厦门卓讯信息技术有限公司 All rights reserved.
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath*:/applicationContext.xml")
public class EmailTest {
	@Autowired
	private EmailUtil emailUtil;
	@Autowired
	private EmailTemplateService emailTemplateService;
	@Autowired
	private EmailPushDao emailPushDao;
	/*@Test
	public void test()
	{
		EmailTemplate template = emailTemplateService.getEmailTemplateByEnglishName("Regist");
		Map<String, String> map = new HashMap<String, String>();
		map.put("memberName", "843978167@qq.com");
		map.put("code", "123456");
		emailUtil.createEmail(template, map, "843978167@qq.com");
	}*/
	
}
