package com.ph.shopping.common.core.other;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ph.shopping.common.core.customenum.MessageEnum;
import com.ph.shopping.common.util.result.MessageInfo;
/**
 * 
* @ClassName: Hunter
* @Description: 北京分润需要使用
* @author 王强
* @date 2017年4月26日 下午3:49:04
 */
public class HunterService {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	protected MessageInfo getMessageInfo(MessageEnum messageEnum, Object data) {
		MessageInfo info = new MessageInfo();
		info.setCode(messageEnum.getCode());
		info.setMsg(messageEnum.getMsg());
		info.setData(data);
		return info;
	}
	
	protected MessageInfo getMessageInfo(MessageEnum messageEnum) {
		MessageInfo info = new MessageInfo();
		info.setCode(messageEnum.getCode());
		info.setMsg(messageEnum.getMsg());
		return info;
	}
}
