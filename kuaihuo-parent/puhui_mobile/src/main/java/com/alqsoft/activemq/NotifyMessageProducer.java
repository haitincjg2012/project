/*package com.alqsoft.activemq;

import java.util.Map;

import javax.jms.Destination;

import org.springframework.jms.core.JmsTemplate;

*//**
 * JMS用户变更消息生产者.
 * 
 * 使用jmsTemplate将用户变更消息分别发送到queue与topic.
 * 
 * @author 张靠勤
 * @e-mail 627658539@qq.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2014-5-25 下午3:13:17
 * 
 *//*
public class NotifyMessageProducer {
	private JmsTemplate jmsTemplate;
	private Destination notifyQueue;
	private Destination notifyTopic;

	public void sendQueue(final Map<String, Object> map) {
		sendMessage(map, notifyQueue);
	}

	public void sendTopic(final Map<String, Object> map) {
		sendMessage(map, notifyTopic);
	}

	*//**
	 * 使用jmsTemplate最简便的封装convertAndSend()发送Map类型的消息.
	 *//*
	private void sendMessage(Map<String, Object> map, Destination destination) {
		jmsTemplate.convertAndSend(destination, map);
	}

	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}

	public void setNotifyQueue(Destination notifyQueue) {
		this.notifyQueue = notifyQueue;
	}

	public void setNotifyTopic(Destination nodifyTopic) {
		this.notifyTopic = nodifyTopic;
	}
}*/