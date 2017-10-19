package com.alqsoft.dao.alreadycashorder;

import org.alqframework.orm.hibernate.BaseDao;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.alqsoft.entity.alreadycashorder.AlreadyCashOrder;

/**
 * 
 * @Description: TODO
 * @author Shen.joe
 * @e-mail sudiluo_java@163.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2017年3月10日 上午9:57:59
 * 
 */
public interface AlreadyCashOrderDao extends BaseDao<AlreadyCashOrder>{
	@Query("select COUNT(*) from AlreadyCashOrder as a where a.merSeqId=:merSeqId")
	public Integer findAlreadyCashOrderByMerSeqID(@Param("merSeqId") String merSeqId);
}
