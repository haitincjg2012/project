/*package com.alqsoft.activemq;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

*//**
 * 
 * 
 * @author 张靠勤
 * @e-mail 627658539@qq.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2014-5-25 下午3:32:09
 * 
 *//*
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath*:/applicationContext.xml")
public class NotifyMessageProducerTest {
	
	@Autowired
	private NotifyMessageProducer messageProducer;

	@Test
	public void testSendQueue() {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("name", "张靠勤");
		messageProducer.sendQueue(map);
	}

	@Test
	public void testSendTopic() {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("name", "张靠勤");
		messageProducer.sendTopic(map);
	}

}
*/