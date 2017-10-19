/*package com.alqsoft.scheduler.chat;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;




import org.alqframework.bean.MyBeanUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;




import com.alqsoft.entity.account.ChatGroupAccount;
import com.alqsoft.entity.sms.ChatGroupContentLog;
import com.alqsoft.entity.sms.ChatGroupToRecord;
import com.alqsoft.entity.sms.ClientResponse;
import com.alqsoft.entity.sms.SmsContent;
import com.alqsoft.entity.sms.SmsContentLog;
import com.alqsoft.service.account.ChatGroupAccountService;
import com.alqsoft.service.sms.ChatGroupContentLogService;
import com.alqsoft.service.sms.ChatGroupToRecordService;
import com.alqsoft.service.sms.SmsContentLogService;


*//**
 * @Title: ChatTask.java
 * @Description: 聊天消息缓冲区分配作业
 * @author 陈振兵
 * @e-mail chenzhenbing@139.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2015年5月13日 上午11:40:53 Copyright © 2013 厦门卓讯信息技术有限公司 All rights
 *              reserved.
 * 
 *//*
@Component
@Lazy(value = false)
public class ChatTask {

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	@Autowired
	private SmsContentLogService smsContentLogService;
	@Autowired
	private ChatGroupContentLogService chatGroupContentLogService;
	@Autowired
	private ChatGroupAccountService chatGroupAccountService;
	@Autowired
	private ChatGroupToRecordService chatGroupToRecordService;
	
	private static Calendar calendar = Calendar.getInstance();

	*//**
	 * 
	* @Title: sendChatTask 
	* @Description: 建立接收者缓冲区作业
	* @param     设定文件 
	* @return void    返回类型 
	* @throws
	 *//*
	@Scheduled(cron = "0/1 * * * * ?")
	public void sendChatTask() {
		while (redisTemplate.opsForList().size("sms-public") > 0) {
			SmsContent smsContent = (SmsContent) redisTemplate.opsForList().rightPop("sms-public");// 获取放在缓冲区中的聊天信息
			if (smsContent.getState()==0) {//单发
				redisTemplate.opsForList().leftPush("sms-" + smsContent.getSmsReceiveId(), smsContent);// 建立接收者缓冲区
				redisTemplate.expire("sms-" + smsContent.getSmsReceiveId(), 24, TimeUnit.HOURS);
				
				//保存聊天消息到数据库日志中
				SmsContentLog smsContentLog=new SmsContentLog();
				smsContentLog.setContent(smsContent.getContent());
				smsContentLog.setSmsReceiveId(smsContent.getSmsReceiveId());
				smsContentLog.setSmsReceiveName(smsContent.getSmsReceiveName());
				smsContentLog.setSmsSenderId(smsContent.getSmsSenderId());
				smsContentLog.setSmsSenderName(smsContent.getSmsSenderName());
				smsContentLog.setState(smsContent.getState());
				smsContentLog.setSmsState(0);
				smsContentLog.setUniqueKey(smsContent.getUniqueKey());
				smsContentLog.setSmsSendTime(smsContent.getSmsSendTime());//保存发送时间
				smsContentLog.setDay(calendar.get(Calendar.DAY_OF_MONTH));
				smsContentLog.setMonth(calendar.get(Calendar.MONTH)+1);
				smsContentLog.setYear(calendar.get(Calendar.YEAR));
				smsContentLog.setTimes(smsContent.getTimes());
				redisTemplate.opsForList().leftPush("sms-mysql", smsContentLog);// 建立数据库保存缓冲区
			}else if(smsContent.getState()==1){//群发
			//通过群id获取用户列表
			List<ChatGroupAccount> chatGroupAccountlist=chatGroupAccountService.findChatGroupAccountByChatGroupId(smsContent.getSmsReceiveId());
			if (chatGroupAccountlist.isEmpty()) {
				//为空群或无用户的异常处理
				continue;
			}
			//保存群聊天消息到数据库日志中
			ChatGroupContentLog chatGroupContentLog=new ChatGroupContentLog();
			chatGroupContentLog.setContent(smsContent.getContent());
			chatGroupContentLog.setSmsReceiveId(smsContent.getSmsReceiveId());
			chatGroupContentLog.setSmsReceiveName(smsContent.getSmsReceiveName());
			chatGroupContentLog.setSmsSenderId(smsContent.getSmsSenderId());
			chatGroupContentLog.setSmsSenderName(smsContent.getSmsSenderName());
			chatGroupContentLog.setState(smsContent.getState());
			chatGroupContentLog.setUniqueKey(smsContent.getUniqueKey());
			chatGroupContentLog.setSmsSendTime(smsContent.getSmsSendTime());//保存发送时间
			chatGroupContentLog.setDay(calendar.get(Calendar.DAY_OF_MONTH));
			chatGroupContentLog.setMonth(calendar.get(Calendar.MONTH)+1);
			chatGroupContentLog.setYear(calendar.get(Calendar.YEAR));
			redisTemplate.opsForList().leftPush("sms-mysql", chatGroupContentLog);// 建立数据库保存缓冲区
			
			chatGroupAccountlist.parallelStream().forEach(c ->{
				SmsContent smscontentnew=new SmsContent();
				MyBeanUtils.propertyUtils(smscontentnew, smsContent);
				
				smscontentnew.setChatGroupName(	c.getChatGroup().getChatGroupName());
				smscontentnew.setChatGroupId(smsContent.getSmsReceiveId());
				smscontentnew.setSmsReceiveId(c.getAccountId());
				smscontentnew.setSmsReceiveName(c.getAccountName());
				if (smscontentnew.getSmsSenderId().longValue()!=smscontentnew.getSmsReceiveId().longValue()) {//发送者和接收者不能相等
					redisTemplate.opsForList().leftPush("sms-" + c.getAccountId(), smscontentnew);// 建立接收者缓冲区
					redisTemplate.expire("sms-" + c.getAccountId(), 24, TimeUnit.HOURS);
					 ChatGroupToRecord  chatGroupToRecord=new  ChatGroupToRecord();//群发发送记录表
					 chatGroupToRecord.setAccountId(c.getAccountId());
					 chatGroupToRecord.setUniqueKey(smscontentnew.getUniqueKey());
					 chatGroupToRecord.setSmsState(0);
					 chatGroupToRecord.setSmsSendTime(new Date());
					 chatGroupToRecord.setTimes(smscontentnew.getTimes());
					 redisTemplate.opsForList().leftPush("sms-mysql", chatGroupToRecord);// 建立数据库保存缓冲区
				}
			});
			}
		}

	}
	
	*//**
	 * 
	* @Title: getSmsContentLogTask ---进行数据库的操作
	* @Description: 从redis数据缓冲区保存到数据库中的作业
	* @param     设定文件 
	* @return void    返回类型 
	* @throws
	 *//*
	@Scheduled(cron = "0/1 * * * * ?")
	public void getSmsContentLogToMySqlTask(){
		while (redisTemplate.opsForList().size("sms-mysql") > 0) {
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			System.out.println("---从redis数据缓冲区保存到数据库中的作业----");
			Object object = redisTemplate.opsForList().rightPop("sms-mysql");// 获取数据库缓冲区中的聊天日志信息
			if (object instanceof SmsContentLog) {//单发
				SmsContentLog smsContentLog = (SmsContentLog) object;// 获取数据库缓冲区中的聊天信息
				SmsContentLog smsContentLogSql=smsContentLogService.getSmsContentLogByUniqueKey(smsContentLog.getUniqueKey());
				if (smsContentLogSql!=null) {//判断数据库是否已经存在，如果存在进行修改操作
					smsContentLogSql.setSmsState(smsContentLog.getSmsState());//修改信息发送状态
					smsContentLogSql.setStepWatch(new Date().getTime()-smsContentLogSql.getSmsSendTime().getTime());
					smsContentLogSql.setTimes(smsContentLog.getTimes());
					smsContentLogService.saveAndModify(smsContentLogSql);
				}else{
					smsContentLogService.saveAndModify(smsContentLog);
				}
			}else if(object instanceof ChatGroupContentLog){//群发
				ChatGroupContentLog chatGroupContentLog = (ChatGroupContentLog) object;// 获取数据库缓冲区中的聊天信息
				ChatGroupContentLog chatGroupContentLogSql=chatGroupContentLogService.getChatGroupContentLogByUniqueKey(chatGroupContentLog.getUniqueKey());
				if (chatGroupContentLogSql==null) {
					chatGroupContentLogService.saveAndModify(chatGroupContentLog);
				}
			}else if(object instanceof ChatGroupToRecord){//群发记录表
				ChatGroupToRecord chatGroupToRecord = (ChatGroupToRecord) object;// 获取数据库缓冲区中的聊天信息
				ChatGroupToRecord chatGroupToRecordSql=chatGroupToRecordService.getChatGroupToRecordByUniqueKeyAndAccountId(chatGroupToRecord.getUniqueKey(),chatGroupToRecord.getAccountId());
				if (chatGroupToRecordSql!=null) {
					chatGroupToRecordSql.setSmsState(chatGroupToRecord.getSmsState());//修改信息发送状态
					chatGroupToRecordSql.setStepWatch(new Date().getTime()-chatGroupToRecordSql.getSmsSendTime().getTime());
					chatGroupToRecordSql.setTimes(chatGroupToRecord.getTimes());
					chatGroupToRecordService.saveAndModify(chatGroupToRecordSql);
					//修改日志表中的延迟时间和发送次数
					ChatGroupContentLog chatGroupContentLogNew=chatGroupContentLogService.getChatGroupContentLogByUniqueKey(chatGroupToRecordSql.getUniqueKey());
					if (chatGroupContentLogNew!=null) {
						chatGroupContentLogNew.setStepWatch(chatGroupToRecordSql.getStepWatch());
						chatGroupContentLogNew.setTimes(chatGroupToRecordSql.getTimes());
						chatGroupContentLogService.saveAndModify(chatGroupContentLogNew);
					}
				}else{
					chatGroupToRecordService.saveAndModify(chatGroupToRecord);
				}
			}else if(object instanceof ClientResponse){//客户端收到消息进行响应
				ClientResponse clientResponse = (ClientResponse) object;// 获取数据库缓冲区中的聊天信息
				if (clientResponse.getMessageKey().equals("clientResponse")) {
					SmsContentLog smsContentLogSql=smsContentLogService.getSmsContentLogByUniqueKey(clientResponse.getUniqueKey());
					smsContentLogSql.setSmsState(3);//修改为已接收状态
					smsContentLogService.saveAndModify(smsContentLogSql);
				}else if(clientResponse.getMessageKey().equals("chatGroupClientResponse")){
					ChatGroupToRecord chatGroupToRecordSql=chatGroupToRecordService.getChatGroupToRecordByUniqueKeyAndAccountId(clientResponse.getUniqueKey(),NumberUtils.toLong(clientResponse.getAccountId()));
					if (chatGroupToRecordSql!=null) {
						chatGroupToRecordSql.setSmsState(3);//修改为已接收状态
						chatGroupToRecordService.saveAndModify(chatGroupToRecordSql);
					}
				}
				
			}
			
		}
	}
}
*/