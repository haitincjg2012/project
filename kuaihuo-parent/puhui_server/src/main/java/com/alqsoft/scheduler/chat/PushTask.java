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

import com.alqsoft.entity.account.PushGroupAccount;
import com.alqsoft.entity.msgpush.PushClientResponse;
import com.alqsoft.entity.msgpush.PushContent;
import com.alqsoft.entity.msgpush.PushContentLog;
import com.alqsoft.entity.msgpush.PushGroupContentLog;
import com.alqsoft.entity.msgpush.PushGroupToRecord;
import com.alqsoft.service.account.PushGroupAccountService;
import com.alqsoft.service.msgpush.PushContentLogService;
import com.alqsoft.service.msgpush.PushGroupContentLogService;
import com.alqsoft.service.msgpush.PushGroupToRecordService;

*//**
 * @Title: PushTask.java
 * @Description: 消息推送作业
 * @author 陈振兵
 * @e-mail chenzhenbing@139.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2015年5月19日 下午2:12:20
 * Copyright © 2013 厦门卓讯信息技术有限公司 All rights reserved.
 * 
 *//*
@Component
@Lazy(value = false)
public class PushTask {
	
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	@Autowired
	private PushContentLogService pushContentLogService;
	@Autowired
	private PushGroupContentLogService pushGroupContentLogService;
	@Autowired
	private PushGroupAccountService pushGroupAccountService;
	@Autowired
	private PushGroupToRecordService pushGroupToRecordService;
	
	private static Calendar calendar = Calendar.getInstance();

	*//**
	 * 
	* @Title: pushMsgTask 
	* @Description: 建立接收者缓冲区作业 --用于消息推送
	* @param     设定文件 
	* @return void    返回类型 
	* @throws
	 *//*
	@Scheduled(cron = "0/1 * * * * ?")
	public void pushMsgTask() {
		while (redisTemplate.opsForList().size("push-public") > 0) {
			PushContent pushContent = (PushContent) redisTemplate.opsForList().rightPop("push-public");// 获取放在缓冲区中的聊天信息
			if (pushContent.getState()==0) {//单推
				redisTemplate.opsForList().leftPush("push-" + pushContent.getPushReceiveId(), pushContent);// 建立接收者缓冲区
				redisTemplate.expire("push-" + pushContent.getPushReceiveId(), 24, TimeUnit.HOURS);
				
				//保存推送消息到数据库日志中
				PushContentLog pushContentLog=new PushContentLog();
				pushContentLog.setContent(pushContent.getContent());
				pushContentLog.setPushReceiveId(pushContent.getPushReceiveId());
				pushContentLog.setPushReceiveName(pushContent.getPushReceiveName());
				pushContentLog.setState(pushContent.getState());
				pushContentLog.setPushState(0);
				pushContentLog.setUniqueKey(pushContent.getUniqueKey());
				pushContentLog.setPushSendTime(pushContent.getPushSendTime());//保存发送时间
				pushContentLog.setDay(calendar.get(Calendar.DAY_OF_MONTH));
				pushContentLog.setMonth(calendar.get(Calendar.MONTH)+1);
				pushContentLog.setYear(calendar.get(Calendar.YEAR));
				pushContentLog.setPushTimes(pushContent.getPushTimes());
				pushContentLog.setPushType(pushContent.getPushType());
				redisTemplate.opsForList().leftPush("push-mysql", pushContentLog);// 建立数据库保存缓冲区--保存推送日志
			}else if(pushContent.getState()==1){//群推
			//通过群id获取用户列表
			List<PushGroupAccount> pushGroupAccountlist=pushGroupAccountService.findPushGroupAccountByPushGroupId(pushContent.getPushReceiveId());
			if (pushGroupAccountlist.isEmpty()) {
				//为空群或无用户的异常处理
				continue;
			}
			//保存群推到数据库日志中
			PushGroupContentLog pushGroupContentLog=new PushGroupContentLog();
			pushGroupContentLog.setContent(pushContent.getContent());
			pushGroupContentLog.setPushReceiveId(pushContent.getPushReceiveId());
			pushGroupContentLog.setPushReceiveName(pushContent.getPushReceiveName());
			pushGroupContentLog.setState(pushContent.getState());
			pushGroupContentLog.setUniqueKey(pushContent.getUniqueKey());
			pushGroupContentLog.setPushSendTime(pushContent.getPushSendTime());//保存发送时间
			pushGroupContentLog.setDay(calendar.get(Calendar.DAY_OF_MONTH));
			pushGroupContentLog.setMonth(calendar.get(Calendar.MONTH)+1);
			pushGroupContentLog.setYear(calendar.get(Calendar.YEAR));
			pushGroupContentLog.setPushType(pushContent.getPushType());
			redisTemplate.opsForList().leftPush("push-mysql", pushGroupContentLog);// 建立数据库保存缓冲区
			
			pushGroupAccountlist.parallelStream().forEach(c ->{
				PushContent pushcontentnew=new PushContent();
				MyBeanUtils.propertyUtils(pushcontentnew, pushContent);
				
				pushcontentnew.setPushGroupId(pushContent.getPushReceiveId());
				pushcontentnew.setPushReceiveId(c.getAccountId());
				pushcontentnew.setPushReceiveName(c.getAccountName());
				
				if (pushcontentnew.getPushReceiveId()!=null) {//接收者不能为空
					redisTemplate.opsForList().leftPush("push-" + c.getAccountId(), pushcontentnew);// 建立接收者缓冲区
					redisTemplate.expire("push-" + c.getAccountId(), 24, TimeUnit.HOURS);
					 PushGroupToRecord  pushGroupToRecord=new  PushGroupToRecord();//群发发送记录表
					 pushGroupToRecord.setAccountId(c.getAccountId());
					 pushGroupToRecord.setUniqueKey(pushcontentnew.getUniqueKey());
					 pushGroupToRecord.setPushState(0);
					 pushGroupToRecord.setPushSendTime(new Date());
					 pushGroupToRecord.setPushTimes(pushcontentnew.getPushTimes());
					 redisTemplate.opsForList().leftPush("push-mysql", pushGroupToRecord);// 建立数据库保存缓冲区
				}
			});
			}
		}

	}
	
	*//**
	 * 
	* @Title: getPushContentLogToMysqlTask ---进行数据库的操作
	* @Description: 从redis数据缓冲区保存到数据库中的作业
	* @param     设定文件 
	* @return void    返回类型 
	* @throws
	 *//*
	@Scheduled(cron = "0/1 * * * * ?")
	public void getPushContentLogToMysqlTask(){
		while (redisTemplate.opsForList().size("push-mysql") > 0) {
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			System.out.println("---从redis数据缓冲区保存到数据库中的作业【推送】----");
			Object object = redisTemplate.opsForList().rightPop("push-mysql");// 获取数据库缓冲区中的聊天日志信息
			if (object!=null) {
				if (object instanceof PushContentLog) {//单推
					PushContentLog pushContentLog = (PushContentLog) object;// 获取数据库缓冲区中的聊天信息
					PushContentLog pushContentLogSql=pushContentLogService.getPushContentLogByUniqueKey(pushContentLog.getUniqueKey());
					if (pushContentLogSql!=null) {//判断数据库是否已经存在，如果存在进行修改操作
						pushContentLogSql.setPushState(pushContentLog.getPushState());//修改信息发送状态
						pushContentLogSql.setStepWatch(new Date().getTime()-pushContentLog.getPushSendTime().getTime());
						pushContentLogSql.setPushTimes(pushContentLog.getPushTimes());
						pushContentLogService.saveAndModify(pushContentLogSql);
					}else{
						pushContentLogService.saveAndModify(pushContentLog);
					}
				}else if(object instanceof PushGroupContentLog){//群推
					PushGroupContentLog pushGroupContentLog = (PushGroupContentLog) object;// 获取数据库缓冲区中的聊天信息
					PushGroupContentLog pushGroupContentLogSql=pushGroupContentLogService.getPushGroupContentLogByUniqueKey(pushGroupContentLog.getUniqueKey());
					if (pushGroupContentLogSql==null) {
						pushGroupContentLogService.saveAndModify(pushGroupContentLog);
					}
				}else if(object instanceof PushGroupToRecord){//群发记录表
					PushGroupToRecord pushGroupToRecord = (PushGroupToRecord) object;// 获取数据库缓冲区中的聊天信息
					PushGroupToRecord pushGroupToRecordSql=pushGroupToRecordService.getPushGroupToRecordByUniqueKeyAndAccountId(pushGroupToRecord.getUniqueKey(),pushGroupToRecord.getAccountId());
					if (pushGroupToRecordSql!=null) {
						pushGroupToRecordSql.setPushState(pushGroupToRecord.getPushState());//修改信息发送状态
						pushGroupToRecordSql.setStepWatch(new Date().getTime()-pushGroupToRecordSql.getPushSendTime().getTime());
						pushGroupToRecordSql.setPushTimes(pushGroupToRecord.getPushTimes());
						pushGroupToRecordService.saveAndModify(pushGroupToRecordSql);
						//修改日志表中的延迟时间和发送次数
						PushGroupContentLog pushGroupContentLogNew=pushGroupContentLogService.getPushGroupContentLogByUniqueKey(pushGroupToRecordSql.getUniqueKey());
						if (pushGroupContentLogNew!=null) {
							pushGroupContentLogNew.setStepWatch(pushGroupToRecordSql.getStepWatch());
							pushGroupContentLogNew.setPushTimes(pushGroupToRecordSql.getPushTimes());
							pushGroupContentLogService.saveAndModify(pushGroupContentLogNew);
						}
					}else{
						pushGroupToRecordService.saveAndModify(pushGroupToRecord);
					}
				}else if(object instanceof PushClientResponse){//客户端收到消息进行响应
					PushClientResponse clientResponse = (PushClientResponse) object;// 获取数据库缓冲区中的聊天信息
					if (clientResponse.getMessageKey().equals("pushClientResponse")) {//--单推响应
						PushContentLog pushContentLogSql=pushContentLogService.getPushContentLogByUniqueKey(clientResponse.getUniqueKey());
						pushContentLogSql.setPushState(3);//修改为已接收状态
						pushContentLogService.saveAndModify(pushContentLogSql);
					}else if(clientResponse.getMessageKey().equals("pushGroupClientResponse")){//---群推响应
						PushGroupToRecord pushGroupToRecordSql=pushGroupToRecordService.getPushGroupToRecordByUniqueKeyAndAccountId(clientResponse.getUniqueKey(),NumberUtils.toLong(clientResponse.getAccountId()));
						if (pushGroupToRecordSql!=null) {
							pushGroupToRecordSql.setPushState(3);//修改为已接收状态
							pushGroupToRecordService.saveAndModify(pushGroupToRecordSql);
						}
					}
					
				}
			}
			
		}
	}

}
*/