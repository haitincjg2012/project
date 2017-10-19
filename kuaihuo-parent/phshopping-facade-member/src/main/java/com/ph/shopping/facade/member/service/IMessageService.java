package com.ph.shopping.facade.member.service;

import java.util.List;
import java.util.Map;

import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.member.dto.message.JPushSendDTO;
import com.ph.shopping.facade.member.dto.message.MessagePushRecordDTO;
import com.ph.shopping.facade.member.dto.message.MessageQueryDTO;
import com.ph.shopping.facade.member.dto.message.UnreadMessageRecordDTO;
import com.ph.shopping.facade.member.vo.MessageVO;

public interface IMessageService {
	
	/**
	 * 
	 * @Title: addMessageSendRecord   
	 * @Description: 添加发送记录   
	 * @param: @param entity
	 * @param: @return      
	 * @return: Result
	 * @author：李杰      
	 * @throws
	 */
	Result addMessageSendRecord(MessagePushRecordDTO dto);
	/**
	 * 
	 * @Title: updateMessageSendRecord   
	 * @Description: 修改发送数据为成功状态
	 * @param: @param entity
	 * @param: @return      
	 * @return: Result
	 * @author：李杰      
	 * @throws
	 */
	Result updateMessageSendStatusByIds(List<Long> recordIds);
	/**
	 * 
	 * @Title: addMessageSendRecords   
	 * @Description: 批量添加   
	 * @param: @param entitys
	 * @param: @return      
	 * @return: Result
	 * @author：李杰      
	 * @throws
	 */
	Result addMessageSendRecords(List<MessagePushRecordDTO> dto);
	/**
	 * 
	 * @Title: getMessageSendInfo   
	 * @Description: 消息列表查询   
	 * @param: @param dto
	 * @param: @return      
	 * @return: Result
	 * @author：李杰      
	 * @throws
	 */
	Result getMessageSendInfoByPage(MessageQueryDTO dto);
	/**
	 * 
	 * @Title: getMessageSendInfoById   
	 * @Description: 根据消息模板ID 得到消息   
	 * @param: @param templateId
	 * @param: @return      
	 * @return: Result
	 * @author：李杰      
	 * @throws
	 */
	Result getMessageSendInfoByTmplateId(Long templateId);
	/**
	 * 
	 * @Title: getMessageInfoById   
	 * @Description: 根据ID 得到发送的信息详情   
	 * @param: @param id
	 * @param: @return      
	 * @return: Result
	 * @author：李杰      
	 * @throws
	 */
	Result getMessageInfoById(Long recordId);
	/**
	 * 
	 * @Title: getUnreadMessageRecordByUid   
	 * @Description: 查询未读记录   
	 * @param: @param dto
	 * @param: @return      
	 * @return: Result
	 * @author：李杰      
	 * @throws
	 */
	Result getUnreadMessageRecordByUid(UnreadMessageRecordDTO dto);
	/**
	 * 
	 * @Title: pushMessage   
	 * @Description: 消息推送   
	 * @param: @param dto
	 * @param: @return      
	 * @return: Result
	 * @author：李杰      
	 * @throws
	 */
	Result pushMessage(JPushSendDTO dto);
	/**
	 * 
	 * @Title: getMessageContentByTemplateId   
	 * @Description: 根据模板ID 得到发送内容   
	 * @param: @param templateId
	 * @param: @param params
	 * @param: @return      
	 * @return: Result
	 * @author：李杰      
	 * @throws
	 */
	Result getMessageContentByTemplateId(Long templateId,Map<String, String> params);
	/**
	 * <pre>findUserPhone(作用：展示消息推送)   
	 */
	MessageVO findUserPhone(Long userId);
}
