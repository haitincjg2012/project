package com.alqsoft.service.impl.huntermessage;

import java.util.List;
import java.util.Map;

import org.alqframework.result.Result;
import org.alqframework.result.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alqsoft.dao.huntermessage.MessageDao;
import com.alqsoft.entity.member.Member;
import com.alqsoft.service.huntermessage.MessageService;

@Service
@Transactional(readOnly = true)
public class MessageServiceImpl implements MessageService {
	@Autowired
	private MessageDao MessageDao;

	@Override
	public Result HunterMessageList(Member member) {
		// 判断member是否为空
		if (member == null) {
			return ResultUtils.returnError("没有对应的批发商");
		}
		// 得到批发商的id
		Long id = member.getHunter().getId();
		// 判断id是否为空
		if (id == null) {
			return ResultUtils.returnError("没有批发商信息");
		}
		
		List<Map> map=MessageDao.getHunterRuleAttachmentListByid(id);
		Map map1 = MessageDao.HunterMessageList(id);
		map1.put("ima", map);
		return ResultUtils.returnSuccess("", map1);
	}

}
