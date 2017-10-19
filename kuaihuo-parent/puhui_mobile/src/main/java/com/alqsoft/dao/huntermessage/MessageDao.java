package com.alqsoft.dao.huntermessage;

import java.util.List;
import java.util.Map;

import org.alqframework.orm.mybatis.MyBatisRepository;
import org.springframework.data.repository.query.Param;

@MyBatisRepository
public interface MessageDao {
	public Map HunterMessageList(@Param("id")Long id);
	
	public List<Map> getHunterRuleAttachmentListByid(@Param("id")Long id);
}
