package com.alqsoft.dao.hunterruleattachment;

import java.util.Collection;
import java.util.List;

import org.alqframework.orm.hibernate.BaseDao;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.alqsoft.entity.hunterruleattachment.HunterRuleAttachment;

public interface HunterRuleAttachmentDao extends BaseDao<HunterRuleAttachment>{

	@Query("from HunterRuleAttachment where hunterRule.id=:hunterRuleId")
	List<HunterRuleAttachment> getHunterRuleAttachmentByHunterRuleId(@Param("hunterRuleId") Long hunterRuleId);

	@Modifying
	@Query(value="update alq_hunter_rule_attachment hr set hr.hunter_rule_id=:ruleid WHERE hr.id in(:pids)",nativeQuery=true)
	public void updateHunterRulePicture(@Param("pids")Collection<Long> pids,@Param("ruleid")Long ruleid);
}
