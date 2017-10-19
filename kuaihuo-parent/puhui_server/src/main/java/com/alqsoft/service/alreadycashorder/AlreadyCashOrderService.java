package com.alqsoft.service.alreadycashorder;

import org.alqframework.orm.BaseService;
import org.springframework.data.repository.query.Param;

import com.alqsoft.entity.alreadycashorder.AlreadyCashOrder;

/**
 * 
 * @Description: TODO
 * @author Shen.joe
 * @e-mail sudiluo_java@163.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2017年3月10日 上午10:23:02
 * 
 */
public interface AlreadyCashOrderService extends BaseService<AlreadyCashOrder>{
	Integer findAlreadyCashOrderByMerSeqID(@Param("merSeqId") String merSeqId); 
}
