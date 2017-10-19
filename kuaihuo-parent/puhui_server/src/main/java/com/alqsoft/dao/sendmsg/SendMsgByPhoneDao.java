package com.alqsoft.dao.sendmsg;

import org.alqframework.orm.hibernate.BaseDao;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.alqsoft.entity.user.User;

/**
 * 
 * @Description: TODO
 * @author wudi
 * @version v1.0
 * @create-time 2017年4月25日 下午2:10:13
 * 
 */
public interface SendMsgByPhoneDao extends BaseDao<User>{
	
	
@Query("select COUNT(*) from User as a where a.userName=:phone")
public int sendMsgByPhone(@Param("phone")String phone);

}
