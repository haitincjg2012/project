/**  
 * @Title:  MessageServiceImpl.java   
 * @Package com.ph.shopping.facade.member.service.impl   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 李杰    
 * @date:   2017年6月7日 上午11:06:48   
 * @version V1.0 
 * @Copyright: 2017
 */  
package com.ph.shopping.facade.member.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ph.shopping.common.core.other.message.jpush.JPushData;
import com.ph.shopping.common.core.other.message.jpush.handle.IJPushHandle;
import com.ph.shopping.common.util.container.ContainerUtil;
import com.ph.shopping.common.util.core.RespCode;
import com.ph.shopping.common.util.core.ResultUtil;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.common.util.string.StringHelper;
import com.ph.shopping.facade.mapper.MessageMapper;
import com.ph.shopping.facade.member.dto.message.JPushSendDTO;
import com.ph.shopping.facade.member.dto.message.JPushUserDTO;
import com.ph.shopping.facade.member.dto.message.MessagePushRecordDTO;
import com.ph.shopping.facade.member.dto.message.MessageQueryDTO;
import com.ph.shopping.facade.member.dto.message.PushMessageDTO;
import com.ph.shopping.facade.member.dto.message.UnreadMessageRecordDTO;
import com.ph.shopping.facade.member.entity.MessagePushRecord;
import com.ph.shopping.facade.member.exception.MemberException;
import com.ph.shopping.facade.member.menum.message.PushMessageEnum;
import com.ph.shopping.facade.member.service.IMessageService;
import com.ph.shopping.facade.member.vo.MessagePushBackVO;
import com.ph.shopping.facade.member.vo.MessageVO;

/**   
 * @ClassName:  MessageServiceImpl   
 * @Description:消息推送相关数据服务   
 * @author: 李杰
 * @date:   2017年6月7日 上午11:06:48     
 * @Copyright: 2017
 */
@Component
@Service(version = "1.0.0")
public class MessageServiceImpl implements IMessageService{

	private static final Logger logger = LoggerFactory.getLogger(MessageServiceImpl.class);
	
	@Autowired
	private MessageMapper messageMapper;
	
	@Autowired
	private IJPushHandle push;
	
	@Override
	@Transactional
	public Result addMessageSendRecord(MessagePushRecordDTO dto) {
		logger.info("添加消息发送数据参数：MessagePushRecordDTO = {} ", JSON.toJSONString(dto));
		final Result result = ResultUtil.getResult(RespCode.Code.REQUEST_DATA_ERROR);
		try {
			// 校验必填参数
			if (null == dto) {
				return result;
			}
			String errorStr = dto.validateForm();
			if (StringUtils.isNotBlank(errorStr)) {
				result.setMessage(errorStr);
			} else {
				MessagePushRecord mr = new MessagePushRecord();
				BeanUtils.copyProperties(dto, mr);
				messageMapper.insert(mr);
				ResultUtil.setResult(result, RespCode.Code.SUCCESS);
			}
		} catch (Exception e) {
			logger.error("添加消息发送数据错误", e);
			throw new MemberException("添加消息发送数据错误");
		}
		return result;
	}

	@Override
	public Result getMessageSendInfoByPage(MessageQueryDTO dto) {
		logger.info("查询推送数据列表参数：MessageQueryDTO = {} ", JSON.toJSONString(dto));
		final Result result = ResultUtil.getResult(RespCode.Code.REQUEST_DATA_ERROR);
		// 校验必填参数
		if (null == dto) {
			return result;
		}
		//String errorStr = dto.validateForm();
	/*	if (StringUtils.isNotBlank(errorStr)) {
			result.setMessage(errorStr);
		} else {*/
			PageHelper.startPage(dto.getPageNum(), dto.getPageSize());
			List<MessageVO> ms = messageMapper.selectMessageSendInfoByList(dto);
			PageInfo<MessageVO> page = new PageInfo<MessageVO>(ms);
			result.setCount(page.getTotal());
			result.setData(page.getList());
			ResultUtil.setResult(result, RespCode.Code.SUCCESS);
		//}
		return result;
	}

	@Override
	public Result getMessageSendInfoByTmplateId(Long templateId) {
		logger.info("查询推送数据参数：templateId = " + templateId);
		final Result result = ResultUtil.getResult(RespCode.Code.REQUEST_DATA_ERROR);
		if (null != templateId) {
			MessageVO mv = messageMapper.selectMessageInfoByTemplateIdId(templateId);
			result.setData(mv);
			ResultUtil.setResult(result, RespCode.Code.SUCCESS);
		}
		return result;
	}

	@Override
	@Transactional
	public Result addMessageSendRecords(List<MessagePushRecordDTO> dto) {
		logger.info("添加消息发送数据参数：List<MessagePushRecordDTO> = {} ", JSON.toJSONString(dto));
		final Result result = ResultUtil.getResult(RespCode.Code.REQUEST_DATA_ERROR);
		if (null != dto && !dto.isEmpty()) {
			List<MessagePushRecord> entitys = getRecordList(dto);
			messageMapper.insertList(entitys);
			result.setData(getPushBackData(entitys));
			ResultUtil.setResult(result, RespCode.Code.SUCCESS);
		}
		return result;
	}

	private List<MessagePushRecord> getRecordList(List<MessagePushRecordDTO> dtos) {
		List<MessagePushRecord> list = ContainerUtil.lList();
		for (MessagePushRecordDTO dto : dtos) {
			MessagePushRecord mr = new MessagePushRecord();
			BeanUtils.copyProperties(dto, mr);
			list.add(mr);
		}
		return list;
	}
	
	private MessagePushBackVO getPushBackData(List<MessagePushRecord> entitys) {
		MessagePushBackVO vo = new MessagePushBackVO();
		if (null != entitys && !entitys.isEmpty()) {
			List<Long> recordIds = ContainerUtil.lList();
			Map<String, String> map = ContainerUtil.map();
			for (MessagePushRecord mr : entitys) {
				recordIds.add(mr.getId());
				map.put(mr.getUserId() + "", mr.getId() + "");
			}
			vo.setPushDataIds(recordIds);
			vo.setUserIdAndDataId(map);
		}
		return vo;
	}
	
	@Override
	@Transactional
	public Result updateMessageSendStatusByIds(List<Long> recordIds) {
		logger.info("修改消息发送数据参数：recordIds = {}", JSON.toJSONString(recordIds));
		final Result result = ResultUtil.getResult(RespCode.Code.REQUEST_DATA_ERROR);
		if (null != recordIds && !recordIds.isEmpty()) {
			Map<String, Object> map = ContainerUtil.map();
			map.put("ids", recordIds);
			map.put("status", PushMessageEnum.PUSH_SUCCESS.getCode());
			messageMapper.updateMessageByStatus(map);
			ResultUtil.setResult(result, RespCode.Code.SUCCESS);
		}
		return result;
	}

	@Override
	@Transactional
	public Result getMessageInfoById(Long recordId) {
		final Result result = ResultUtil.getResult(RespCode.Code.SUCCESS);
		MessageVO mv = messageMapper.selectMessageInfoById(recordId);
		if (null != mv) {
			// 设置已读状态
			MessagePushRecord record = new MessagePushRecord();
			record.setId(mv.getRecordId());
			record.setIsRead(PushMessageEnum.READ.getCode());
			messageMapper.updateByPrimaryKeySelective(record);
		}
		result.setData(mv);
		return result;
	}

	@Override
	public Result getUnreadMessageRecordByUid(UnreadMessageRecordDTO dto) {
		logger.info("查询未读消息参数：UnreadMessageRecordDTO = {}", JSON.toJSONString(dto));
		final Result result = ResultUtil.getResult(RespCode.Code.REQUEST_DATA_ERROR);
		if (null == dto) {
			return result;
		}
		// 校验必填参数
		String errorStr = dto.validateForm();
		if (StringUtils.isNotBlank(errorStr)) {
			result.setMessage(errorStr);
		} else {
			int num = messageMapper.selectUnreadMessageRecordByUid(dto);
			result.setData(num);
			ResultUtil.setResult(result, RespCode.Code.SUCCESS);
		}
		return result;
	}

	@Override
	@Transactional
	public Result pushMessage(JPushSendDTO dto) {
		logger.info("消息推送 参数 JPushSendDTO = {}", JSON.toJSONString(dto));
		final Result result = ResultUtil.getResult(RespCode.Code.REQUEST_DATA_ERROR);
		// 校验必填参数
		if (null == dto) {
			return result;
		}
		String errorStr = dto.validateForm();
		if (StringUtils.isNotBlank(errorStr)) {
			result.setMessage(errorStr);
			return result;
		}
		try {
			// 封装推送数据
			PushMessageDTO pv = backPushParam(dto);
			// 执行推送
			return executePushMessage(pv, dto.getSendType());
		} catch (Exception e) {
			logger.error("消息推送错误", e);
			throw new MemberException("消息推送错误");
		}
	}
	/**
	 * 
	 * @Title: executePushMessage   
	 * @Description: 执行消息推送   
	 * @param: @param pv
	 * @param: @param type
	 * @param: @return      
	 * @return: boolean
	 * @author：李杰      
	 * @throws
	 */
	private Result executePushMessage(PushMessageDTO pv, Byte type) {
		Result result = ResultUtil.getResult(RespCode.Code.FAIL);
		if (null != pv) {
			// 添加推送记录
			final Result addrsult = addMessageSendRecords(pv.getList());
			logger.info("添加推送记录返回数据 addrsult = {}", JSON.toJSONString(addrsult));
			if (addrsult.isSuccess()) {
				Object obj = addrsult.getData();
				if (obj instanceof MessagePushBackVO) {
					MessagePushBackVO vo = (MessagePushBackVO) obj;
					// 新增返回的消息ID 集合
					List<Long> recordIds = vo.getPushDataIds();
					// 得到推送数据
					JPushData pushData = pv.getPushData();
					if (null != vo.getUserIdAndDataId()) {
						pushData.setExtras(vo.getUserIdAndDataId());
					}
					Result sendResult = null;
					if (PushMessageEnum.ALL.getCode().equals(type)) {
						sendResult = push.sendPushByAndroidAndIos(pushData);
					} else if (PushMessageEnum.ANDROID.getCode().equals(type)) {
						sendResult = push.sendPushByAndroid(pushData);
					} else if (PushMessageEnum.IOS.getCode().equals(type)) {
						sendResult = push.sendPushByIos(pushData);
					}
					logger.info("推送消息返回数据 sendResult = {}", JSON.toJSONString(sendResult));
					// 推送成功修改状态
					if (null != sendResult && sendResult.isSuccess()) {
						Result updateResult = updateMessageSendStatusByIds(recordIds);
						logger.info("推送成功修改消息记录返回数据 updateResult = {}", JSON.toJSONString(updateResult));
						if (updateResult.isSuccess()) {
							ResultUtil.setResult(result, RespCode.Code.SUCCESS);
						}
					}
				}
			}
		}
		return result;
	}
	/**
	 * 
	 * @Title: backPushParam   
	 * @Description: 封装请求数据   
	 * @param: @param dto
	 * @param: @return      
	 * @return: Map<String,Object>
	 * @author：李杰      
	 * @throws
	 */
	private PushMessageDTO backPushParam(JPushSendDTO dto) {
		PushMessageDTO pv = null;
		if (null != dto) {
			MessageVO message = messageMapper.selectMessageInfoByTemplateIdId(dto.getTemplateId());
			logger.info("根据 templateId 得到消息详情返回数据 ：Result = {} ", JSON.toJSONString(message));
			if (null != message) {
				pv = new PushMessageDTO();
				JPushData pushData = new JPushData();
				pushData.setAlter(message.getMessageAlert());
				pushData.setTitle(message.getMessageTitle());
				pushData.setContent(dto.getMessageContent());
				pushData.setPushType(dto.getPushType());
				List<JPushUserDTO> users = dto.getUsers();
				// 得到设备ID集合
				List<String> registrationIds = ContainerUtil.aList();
				// 得到消息推送记录
				List<MessagePushRecordDTO> ms = ContainerUtil.lList();
				if (null != users && users.size() > 0) {
					for (JPushUserDTO u : users) {
						if (null != u.getEquipmentId()) {
							registrationIds.add(u.getEquipmentId());
						}
						// 创建发送记录信息
						MessagePushRecordDTO mdto = new MessagePushRecordDTO();
						mdto.setCreateTime(new Date());
						mdto.setEquipmentId(u.getEquipmentId());
						mdto.setTemplateId(message.getTemplateId());
						mdto.setUserId(u.getUserId());
						mdto.setCreaterId(dto.getCreaterId());
						mdto.setUserType(u.getUserType());
						mdto.setMessageContent(dto.getMessageContent());
						mdto.setIsRead(PushMessageEnum.UNREAD.getCode());
						mdto.setStatus(PushMessageEnum.PUSH_FAIL.getCode());
						ms.add(mdto);
					}
				}
				pushData.setRegistrationIds(registrationIds);
				pv.setList(ms);
				pv.setPushData(pushData);
			}
		}
		return pv;
	}

	@Override
	public Result getMessageContentByTemplateId(Long templateId, Map<String, String> params) {
		logger.info("获取消息推送信息 参数 templateId = " + templateId + " params = " + params);
		final Result result = ResultUtil.getResult(RespCode.Code.REQUEST_DATA_ERROR);
		if (null == templateId) {
			result.setMessage("消息模板ID 不能为空");
			return result;
		}
		MessageVO mv = messageMapper.selectMessageInfoByTemplateIdId(templateId);
		String template = mv.getMessageTemplate();
		String content = StringHelper.renderString(template, params);
		result.setCode(content);
		return ResultUtil.setResult(result, RespCode.Code.SUCCESS);
	}

	@Override
	public MessageVO findUserPhone(Long userId) {
		return messageMapper.findUserPhone(userId);
	}
}
