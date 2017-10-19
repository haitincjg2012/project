package com.alqsoft.utils.chat;

import java.util.Calendar;
import java.util.Date;

import org.alqframework.result.Result;
import org.alqframework.result.ResultUtils;
import org.alqframework.utils.MyObjectUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alqsoft.entity.sms.ChatGroupToRecord;
import com.alqsoft.entity.sms.ClientResponse;
import com.alqsoft.entity.sms.SmsContent;
import com.alqsoft.entity.sms.SmsContentLog;

/**
 * @Title: ChatCommonUtils.java
 * @Description: 消息通用工具类
 * @author 陈振兵
 * @e-mail chenzhenbing@139.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2015年5月18日 上午11:57:26
 * Copyright © 2013 厦门卓讯信息技术有限公司 All rights reserved.
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath*:/applicationContext.xml")
@Component
public class ChatCommonUtils {
	
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	
	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	

	/**
	 * 
	* @Title: getRedisSmsContent 
	* @Description: 获取保存在redis缓冲区的消息对象
	* @param @param userId
	* @param @return    设定文件 
	* @return SmsContent    返回类型 
	* @throws
	 */
	public SmsContent getRedisSmsContent(String userId){
		SmsContent smsContent = (SmsContent) redisTemplate.opsForList().rightPop("sms-" + userId);
		return smsContent;
	}
	
	/**
	 * 
	* @Title: sendChatMsg 
	* @Description: 接收消息并把消息日志放到redis缓冲区
	* @param @param userId
	* @param @return    设定文件 
	* @return Result    返回类型 
	* @throws
	 */
	public Result getChatMsg(String userId,SmsContent smsContent){
		// 获取作业中接收者缓冲区的消息发送内容
		if (userId.equals(smsContent.getSmsReceiveId().toString())) {// 验证注册的用户id跟接收者缓冲区的用户id是否一致
			if (smsContent.getState()==0) {//单发
				System.out.println("----注册成功的用户进行【单发】消息的发送---");
				// 进行发送次数统计和状态变更
				Calendar calendar = Calendar.getInstance();
				SmsContentLog smsContentLog = new SmsContentLog();
				smsContentLog.setContent(smsContent.getContent());
				smsContentLog.setSmsReceiveId(smsContent.getSmsReceiveId());
				smsContentLog.setSmsReceiveName(smsContent.getSmsReceiveName());
				smsContentLog.setSmsSenderId(smsContent.getSmsSenderId());
				smsContentLog.setSmsSenderName(smsContent.getSmsSenderName());
				smsContentLog.setState(smsContent.getState());
				smsContentLog.setSmsState(1);
				smsContentLog.setUniqueKey(smsContent.getUniqueKey());
				smsContentLog.setSmsSendTime(smsContent.getSmsSendTime());// 保存发送时间
				smsContentLog.setDay(calendar.get(Calendar.DAY_OF_MONTH));
				smsContentLog.setMonth(calendar.get(Calendar.MONTH) + 1);
				smsContentLog.setYear(calendar.get(Calendar.YEAR));
				smsContentLog.setTimes(smsContent.getTimes() + 1);
				redisTemplate.opsForList().leftPush("sms-mysql", smsContentLog);// 建立数据库保存缓冲区

			}else if(smsContent.getState()==1){//群发
				System.out.println("----注册成功的用户进行【群发】消息的发送---");
				
				ChatGroupToRecord  chatGroupToRecord=new  ChatGroupToRecord();
				chatGroupToRecord.setAccountId(smsContent.getSmsReceiveId());
				chatGroupToRecord.setUniqueKey(smsContent.getUniqueKey());
				chatGroupToRecord.setSmsState(1);
				chatGroupToRecord.setTimes(smsContent.getTimes()+1);
				
				redisTemplate.opsForList().leftPush("sms-mysql", chatGroupToRecord);// 建立数据库保存缓冲区
			}
			// 进行消息发送
			Result result = new Result();
			result.setContent(smsContent);
			result.setCode(1);
			result.setMsg("消息发送成功");
			return  result;
		}else{
			// 进行消息发送
			Result result = new Result();
			result.setCode(-1);
			result.setMsg("用户不存在，消息发送失败！");
			return  result;			
		}
	}
	
	/**
	 * 
	* @Title: addChatMsgToRedis 
	* @Description: 把发送消息放入缓冲区
	* @param @param smsContent
	* @param @return    设定文件 
	* @return Result    返回类型 
	* @throws
	 */
	public Result addChatMsgToRedis(SmsContent smsContent){
		if (smsContent!=null) {
			smsContent.setSmsSendTime(new Date());// 保存发送时间
			// 消息内容 （以队列的形式放入一级缓冲区里：左进右出）,用于ChatTask作业中消息缓冲区的保存
			redisTemplate.opsForList().leftPush("sms-public", smsContent);
			return ResultUtils.returnSuccess("发送消息放入缓冲区成功");
		}else{
			return ResultUtils.returnError("发送消息放入缓冲区失败", -1);
		}
	}
	
	/**
	 * 
	* @Title: getChatMsgClientResponse 
	* @Description: 客户端接收信息后进行响应  【单发】
	* @param @param content
	* @param @return    设定文件 
	* @return Result    返回类型 
	* @throws
	 */
	public ClientResponse getChatMsgClientResponse(ClientResponse content){
		if(content!=null){
//			ClientResponse clientResponse = JSON.parseObject(content, ClientResponse.class);
			redisTemplate.opsForList().leftPush("sms-mysql", content);// 建立数据库保存缓冲区
			return content;
		}
		return null;
	}
	
	/**
	 * 
	* @Title: getChatMsgClientResponse 
	* @Description: 客户端接收信息后进行响应  【群发】
	* @param @param content
	* @param @return    设定文件 
	* @return Result    返回类型 
	* @throws
	 */
	public ClientResponse getChatGroupMsgClientResponse(ClientResponse content,String accountId){
		if(content!=null){
			content.setAccountId(accountId);
			redisTemplate.opsForList().leftPush("sms-mysql", content);// 建立数据库保存缓冲区
			return content;
		}
		return null;
	}

	/**
	 * 
	* @Title: isOnLineByUserId 
	* @Description: 根据用户id判断用户是否在线
	* @param @param userId
	* @param @return    设定文件 
	* @return boolean    返回类型 
	* @throws
	 */
	public boolean isOnLineByUserId(String userId){
		if (stringRedisTemplate.opsForValue().get("sms-user-" + userId)!=null) {
			return true;
		}else{
			return false;
		}
	}
}
