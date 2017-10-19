package com.ph.shopping.common.core.other.message.jpush.handle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.ph.shopping.common.core.other.message.jpush.JPushConfig;
import com.ph.shopping.common.core.other.message.jpush.JPushData;
import com.ph.shopping.common.core.other.message.jpush.JpushTemplate;
import com.ph.shopping.common.util.container.ParamVerifyUtil;
import com.ph.shopping.common.util.core.RespCode;
import com.ph.shopping.common.util.core.ResultUtil;
import com.ph.shopping.common.util.result.Result;

import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.PushPayload;

@Component
public class JPushHandle implements IJPushHandle{
	
	// 日志
	private static final Logger logger = LoggerFactory.getLogger(JPushHandle.class);

	@Autowired
	private JPushConfig config;
	
	/**
	 * 
	 * @Title: sendPush   
	 * @Description: 消息推送   
	 * @param: @param pushData
	 * @param: @return      
	 * @return: Result
	 * @author：李杰      
	 * @throws
	 */
	@Override
	public Result sendPushByAndroidAndIos(JPushData pushData) {
		Result result = verifyPushData(pushData);
		if (result.isSuccess()) {
			PushPayload pushPayload = JpushTemplate.buildPushObjectByAndroidAndIos(pushData);
			result = sendPush(pushPayload);
			logger.info("推送返回数据 ：Result = {} ", JSON.toJSONString(result));
		}
		return result;
	}

	@Override
	public Result sendPushByAndroid(JPushData pushData) {
		Result result = verifyPushData(pushData);
		if (result.isSuccess()) {
			PushPayload pushPayload = JpushTemplate.buildPushObjectByAndroid(pushData);
			result = sendPush(pushPayload);
			logger.info("推送返回数据 ：Result = {} ", JSON.toJSONString(result));
		}
		return result;
	}

	@Override
	public Result sendPushByIos(JPushData pushData) {
		Result result = verifyPushData(pushData);
		if (result.isSuccess()) {
			PushPayload pushPayload = JpushTemplate.buildPushObjectByIos(pushData);
			result = sendPush(pushPayload);
			logger.info("推送返回数据 ：Result = {} ", JSON.toJSONString(result));
		}
		return result;
	}
	/**
	 * 
	 * @Title: verifyPushData   
	 * @Description: 推送入参校验   
	 * @param: @param pushData
	 * @param: @return      
	 * @return: Result
	 * @author：李杰      
	 * @throws
	 */
	private Result verifyPushData(JPushData pushData) {
		logger.info("【IOS】消息推送参数 JPushData = {}", JSON.toJSONString(pushData));
		Result result = ResultUtil.getResult(RespCode.Code.REQUEST_DATA_ERROR);
		String[] fields = { "content", "title", "alter" };
		if (!ParamVerifyUtil.entityIsNotNullByField(pushData, fields)) {
			return result;
		} else {
			ResultUtil.setResult(result, RespCode.Code.SUCCESS);
		}
		return result;
	}
	/**
	 * 
	 * @Title: sendPush   
	 * @Description: 执行推送  
	 * @param: @param pushPayload
	 * @param: @return      
	 * @return: PushResult
	 * @author：李杰      
	 * @throws
	 */
	private Result sendPush(PushPayload pushPayload) {
		Result result = ResultUtil.getResult(RespCode.Code.FAIL);
		result.setMessage("推送失败");
		try {
			PushResult presult = config.getBuyerJpushClient().sendPush(pushPayload);
			logger.info("send push message call back data ：PushResult = {} ", JSON.toJSONString(presult));
			if (null != presult) {
				if (presult.isResultOK()) {
					ResultUtil.setResult(result, RespCode.Code.SUCCESS);
				}
			}
		} catch (Exception e) {
			logger.error("send push message  and ios message error", e);
		}
		return result;
	}
}
