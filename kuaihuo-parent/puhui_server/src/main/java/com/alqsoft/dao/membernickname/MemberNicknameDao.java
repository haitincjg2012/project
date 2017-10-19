package com.alqsoft.dao.membernickname;

import org.alqframework.orm.hibernate.BaseDao;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.alqsoft.entity.member.Member;

public interface MemberNicknameDao extends BaseDao<Member>{

	@Modifying
	@Query(value = "update alq_member set nickname = ?2 where id = ?1 ",nativeQuery=true)
	int updateMemberNickName(@Param("id")Long id ,@Param("nickname")String nickname);

}
