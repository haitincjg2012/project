package com.alqsoft.dao.membermoney;

import javax.persistence.LockModeType;

import org.alqframework.orm.hibernate.BaseDao;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.alqsoft.entity.membermoney.MemberMoney;

public interface MemberMoneyDao extends BaseDao<MemberMoney>{

	@Lock(value = LockModeType.PESSIMISTIC_WRITE)
	@Query("from MemberMoney as c where c.orderNo=:orderNo")
	public MemberMoney getRowLock(@Param("orderNo")String orderNo);

}
