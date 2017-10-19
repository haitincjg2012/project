package com.alqsoft.service.feedback;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alqsoft.dao.feedback.FeedBackDao;
import com.alqsoft.entity.feedback.FeedBack;

/**
 * 
 * @Title: FeedBackTest.java
 * @Description: TODO
 * @author 俞旺
 * @e-mail 13606024548@139.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2015年4月22日 下午4:17:15
 * Copyright © 2013 厦门卓讯信息技术有限公司 All rights reserved.
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath*:/applicationContext.xml")
public class FeedBackTest {
	@Autowired
	private FeedBackDao feedBackDao;
	@Test
	public void test()
	{
		FeedBack back = new FeedBack();
		back.setFeedbackContent("测试看看咯");
		back.setFeedbackTitle("你说呢");
		back.setIsLook(0);
		back.setFeedBackTypeId(1L);
		feedBackDao.save(back);
	}
}
