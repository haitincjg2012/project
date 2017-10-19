package com.alqsoft.dao.industrymoney;

import javax.persistence.LockModeType;

import org.alqframework.orm.hibernate.BaseDao;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.alqsoft.entity.industrymoney.IndustryMoney;

public interface IndustryMoneyDao extends BaseDao<IndustryMoney> {

	@Lock(value = LockModeType.PESSIMISTIC_WRITE)
	@Query("from IndustryMoney as c where c.orderNo=:orderNo")
	public IndustryMoney getRowLock(@Param("orderNo")String orderNo);

}
