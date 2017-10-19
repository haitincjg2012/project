package com.alqsoft.dao.hunterindustrytype;

import org.alqframework.orm.hibernate.BaseDao;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.alqsoft.entity.hunterindustrytype.HunterIndustryType;

public interface HunterIndustryTypeDao extends BaseDao<HunterIndustryType>{

	/**
	 * 根据批发商id删除关联关系
	 */
	@Query(value="delete from alq_hunter_industry_type where hunter_id =:id ",nativeQuery=true)
	@Modifying
	public int  deletehunterIndustryTypeById(@Param("id")Long id);

	
}
