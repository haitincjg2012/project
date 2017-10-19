package com.phshopping.api.controller.message;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.ph.shopping.common.core.other.annotation.Token;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.member.dto.message.JPushSendDTO;
import com.ph.shopping.facade.member.dto.message.MessageQueryDTO;
import com.ph.shopping.facade.member.dto.message.UnreadMessageRecordDTO;
import com.ph.shopping.facade.member.menum.message.PushMessageEnum;
import com.ph.shopping.facade.member.service.IMessageService;
import com.ph.shopping.facade.member.vo.MessageVO;
import com.phshopping.api.entity.MemberInfo;
import com.phshopping.api.uitl.UserCacheUtil;

@RestController
@RequestMapping("api/message")
public class MessageController {

	private static final Logger logger = LoggerFactory.getLogger(MessageController.class);
	
	@Reference(version = "1.0.0", retries = 0, timeout = 10000)
	private IMessageService messageService;
	/**
	 * 
	 * @Title: pushMessage   
	 * @Description: 消息推送   
	 * @param: @param request
	 * @param: @param dto
	 * @param: @return      
	 * @return: Result
	 * @author：李杰      
	 * @throws
	 */
	@Token(key = "templateId")
	@RequestMapping(value = "pushMessage", method = RequestMethod.POST)
	public Result pushMessage(HttpServletRequest request, JPushSendDTO dto) {
		logger.info("消息推送 参数 JPushSendDTO = {}", JSON.toJSONString(dto));
		Result result = messageService.pushMessage(dto);
		logger.info("消息推送返回数据 ： Result = {} ", JSON.toJSONString(result));
		return result;
	}

	/**
	 * 
	 * @Title: getSendMessageInfo   
	 * @Description: 查询发送数据列表   
	 * @param: @param dto
	 * @param: @return      
	 * @return: Result
	 * @author：李杰      
	 * @throws
	 */
	@RequestMapping(value = "getMessageList/{token}/{pageNum}/{pageSize}", method = RequestMethod.GET)
	public Result getSendMessageInfo(HttpServletRequest request, @PathVariable String token, @PathVariable Integer pageNum, @PathVariable Integer pageSize) {
		logger.info("查询发送数据记录列表参数 ：token = " + token + " pageNum = " + pageNum + " pageSize = " + pageSize);
		Result result = UserCacheUtil.getUser(token);
		if(result.isSuccess()){
			pageNum = pageNum == null ? 1 : pageNum;
			pageSize = pageSize == null ? 10 : pageSize;
			MemberInfo memberInfo = (MemberInfo)result.getData();
			MessageQueryDTO dto = new MessageQueryDTO();
			dto.setUserId(memberInfo.getId());
			MessageVO userPhone= messageService.findUserPhone(dto.getUserId());
			dto.setTelPhone(userPhone.getUserId());
			//dto.setUserType(PushMessageEnum.MEMBER.getCode());
			dto.setPageNum(pageNum);
			dto.setPageSize(pageSize);
			result = messageService.getMessageSendInfoByPage(dto);
		}
		logger.info("查询发送数据记录返回列表数据 ： Result = {} ", JSON.toJSONString(result));
		return result;
	}
	/**
	 * 
	 * @Title: getSendMessageInfoById   
	 * @Description: 根据id得到消息记录详情   
	 * @param: @param request
	 * @param: @param token
	 * @param: @param id
	 * @param: @return      
	 * @return: Result
	 * @author：李杰      
	 * @throws
	 */
	@RequestMapping(value = "getMessageInfo/{token}/{recordId}", method = RequestMethod.GET)
	public Result getSendMessageInfoById(HttpServletRequest request, @PathVariable String token,
			@PathVariable Long recordId) {
		logger.info("查询发送数据记录参数 ：token = " + token + " recordId = " + recordId);
		Result result = UserCacheUtil.getUser(token);
		if (result.isSuccess()) {
			result = messageService.getMessageInfoById(recordId);
		}
		logger.info("查询发送数据记录返回数据 ： Result = {} ", JSON.toJSONString(result));
		return result;
	}
	/**
	 * 
	 * @Title: getUnreadMessageRecord   
	 * @Description: 查询未读消息记录   
	 * @param: @param request
	 * @param: @param token
	 * @param: @return      
	 * @return: Result
	 * @author：李杰      
	 * @throws
	 */
	@RequestMapping(value = "getUnreadMessageRecord/{token}", method = RequestMethod.GET)
	public Result getUnreadMessageRecord(HttpServletRequest request, @PathVariable String token) {
		logger.info("查询未读消息记录参数 ：token = " + token);
		Result result = UserCacheUtil.getUser(token);
		if (result.isSuccess()) {
			MemberInfo memberInfo = (MemberInfo) result.getData();
			UnreadMessageRecordDTO dto = new UnreadMessageRecordDTO();
			dto.setUserId(memberInfo.getId());
			dto.setUserType(PushMessageEnum.MEMBER.getCode());
			result = messageService.getUnreadMessageRecordByUid(dto);
		}
		logger.info("查询未读消息记录返回数据 ： Result = {} ", JSON.toJSONString(result));
		return result;
	}
}
