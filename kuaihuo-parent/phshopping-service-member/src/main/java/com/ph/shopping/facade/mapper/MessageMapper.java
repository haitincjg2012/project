package com.ph.shopping.facade.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ph.shopping.common.core.base.BaseMapper;
import com.ph.shopping.facade.member.dto.message.MessageQueryDTO;
import com.ph.shopping.facade.member.dto.message.UnreadMessageRecordDTO;
import com.ph.shopping.facade.member.entity.MessagePushRecord;
import com.ph.shopping.facade.member.vo.MessageVO;

public interface MessageMapper extends BaseMapper<MessagePushRecord>{
	/**
	 * 
	 * @Title: selectMessageSendInfoByList   
	 * @Description: 得到消息发送数据列表   
	 * @param: @param dto
	 * @param: @return      
	 * @return: List<MessageVO>
	 * @author：李杰      
	 * @throws
	 */
	List<MessageVO> selectMessageSendInfoByList(MessageQueryDTO dto);
	/**
	 * 
	 * @Title: selectMessageInfoById   
	 * @Description: 消息详情   
	 * @param: @param templateId
	 * @param: @return      
	 * @return: MessageVO
	 * @author：李杰      
	 * @throws
	 */
	MessageVO selectMessageInfoByTemplateIdId(Long templateId);
	/**
	 * @Title: updateMessageByStatus   
	 * @Description: 修改消息发送状态   
	 * @param: @param map      
	 * @return: void
	 * @author：李杰      
	 * @throws
	 */
	void updateMessageByStatus(Map<String, Object> map);
	/**
	 * 
	 * @Title: selectMessageInfoById   
	 * @Description: 根据消息记录ID 得到发送的消息详情   
	 * @param: @param id
	 * @param: @return      
	 * @return: MessageVO
	 * @author：李杰      
	 * @throws
	 */
	MessageVO selectMessageInfoById(Long recordId);
	/**
	 * 
	 * @Title: selectUnreadMessageRecordByUid   
	 * @Description: 查询是否有未读记录   
	 * @param: @param dto
	 * @param: @return      
	 * @return: int
	 * @author：李杰      
	 * @throws
	 */
	int selectUnreadMessageRecordByUid(UnreadMessageRecordDTO dto);
	MessageVO findUserPhone(@Param("userId") Long userId);

}
