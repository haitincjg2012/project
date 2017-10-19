package com.alqsoft.dao.huntersourcingservice;

import org.alqframework.orm.hibernate.BaseDao;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.alqsoft.entity.hunterservice.HunterService;

public interface HunterSourcingDao extends BaseDao<HunterService>{

	@Query("FROM HunterService AS hs WHERE hs.hunter.id=:hId")
	public HunterService getHunterServiceByHId(@Param("hId")Long hId);

}
