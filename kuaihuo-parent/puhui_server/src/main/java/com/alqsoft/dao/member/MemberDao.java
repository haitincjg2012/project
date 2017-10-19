package com.alqsoft.dao.member;

import javax.persistence.LockModeType;

import org.alqframework.orm.hibernate.BaseDao;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.alqsoft.entity.member.Member;

public interface MemberDao extends BaseDao<Member> {
	
	@Lock(value = LockModeType.PESSIMISTIC_WRITE)
	@Query("from Member as m where m.id=:id")
	public Member getRowLock(@Param("id")Long id);
}
