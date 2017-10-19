package com.alqsoft.dao.memberconnect;

import java.util.Map;

import org.alqframework.orm.mybatis.MyBatisRepository;

import com.alqsoft.entity.memberconnect.MemberConnect;

/***
 * 
*@Description: 
* 
* @author : zhuangll
* @ProjectName : haitunwu-interface
* @version : v4.0 - 2014
* @createTime : 2014-8-18 下午3:40:22
*
 */
@MyBatisRepository
public interface MemberConnectDao {
	public MemberConnect getMemberConnByAccount(Map<String, Object> map);
}
