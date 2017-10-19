package com.alqsoft.dao.adshome;

import java.util.List;
import java.util.Map;

import org.alqframework.orm.hibernate.BaseDao;
import org.alqframework.orm.mybatis.MyBatisRepository;
import org.javasimon.callback.timeline.StopwatchTimeRange;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.alqsoft.entity.adattachment.AdAttachment;

/**
 * 
 * @Description: TODO
 * @author Wudi
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2017年3月17日 下午1:47:20
 * Copyright 
 */
public interface AdsHomeDao extends BaseDao<AdAttachment> {
	@Query(value="UPDATE alq_ad_attachment SET header=:name,address=:address WHERE id=:id",nativeQuery=true)
	@Modifying
public int saveUpdatePictureHeaderName(@Param("id")Long id,@Param("name")String name,@Param("address")String address);
	@Query(value="UPDATE alq_ad_attachment SET detail_content=?2 WHERE id=?1",nativeQuery=true)
	@Modifying
public int saveUpdatePictureCode(Long id,String detailContent);	
}
