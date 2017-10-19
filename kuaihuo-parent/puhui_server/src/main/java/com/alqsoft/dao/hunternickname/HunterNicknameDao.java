package com.alqsoft.dao.hunternickname;

import org.alqframework.orm.hibernate.BaseDao;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.alqsoft.entity.hunter.Hunter;

public interface HunterNicknameDao extends BaseDao<Hunter> {
	
	
	@Modifying
	@Query(value = "update alq_hunter set nickname = ?2 where id = ?1 ",nativeQuery=true)
	int updateHunterNickName(@Param("id")Long id ,@Param("nickname")String nickname);

}
