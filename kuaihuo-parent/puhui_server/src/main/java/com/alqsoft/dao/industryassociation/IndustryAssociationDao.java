package com.alqsoft.dao.industryassociation;

import javax.persistence.LockModeType;

import org.alqframework.orm.hibernate.BaseDao;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.alqsoft.entity.industryassociation.IndustryAssociation;

public interface IndustryAssociationDao extends BaseDao<IndustryAssociation> {

	@Lock(value = LockModeType.PESSIMISTIC_WRITE)
	@Query("FROM IndustryAssociation WHERE id=:id")
	public IndustryAssociation getRowLock(@Param("id") Long id);
	
	/**
	 * 查看是否存在该手机号
	 * @param phone
	 * @return
	 */
	@Query("FROM IndustryAssociation a WHERE a.phone=:phone")
	public IndustryAssociation checkHasPhone(@Param("phone")String phone);

	@Query("FROM IndustryAssociation a WHERE a.phone=:phone")
	public IndustryAssociation getIndustryAssociationByPhone(@Param("phone")String phone);

}
