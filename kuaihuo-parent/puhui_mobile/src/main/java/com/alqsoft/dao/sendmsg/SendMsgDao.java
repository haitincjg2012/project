package com.alqsoft.dao.sendmsg;

import org.alqframework.orm.hibernate.BaseDao;
import org.alqframework.orm.mybatis.MyBatisRepository;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * @Description: TODO
 * @author wudi
 * @version v1.0
 * @create-time 2017年4月25日 下午6:39:56
 * 
 */
@MyBatisRepository
public interface SendMsgDao  {
	
	/**
	 * 验证手机号绑定验证码
	 */
	 public Long  sendMessageCode(@Param("phone") String phone);
	 
	 /**
	 * 验证用户手机号是否在数据库Member表中
	 */
	public Long selectMemberByPhone(@Param("phone") String phone);
}
