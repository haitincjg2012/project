/*package com.alqsoft.activemq;

import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

*//**
 * 消息的异步被动接收者.
 * 
 * 使用Spring的MessageListenerContainer侦听消息并调用本Listener进行处理.
 * 
 * @author 张靠勤
 * @e-mail 627658539@qq.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2014-5-25 下午3:34:46
 * 
 *//*
public class NotifyMessageListener implements MessageListener{
	private static Logger logger = LoggerFactory.getLogger(NotifyMessageListener.class);

	*//**
	 * MessageListener回调函数.
	 *//*
	@Override
	public void onMessage(Message message) {
		try {
			MapMessage mapMessage = (MapMessage) message;
			System.out.println(mapMessage.getString("name"));
		} catch (Exception e) {
			logger.error("处理消息时发生异常.", e);
		}
	}
}*/