package com.alqsoft.dao.register;

import org.alqframework.orm.hibernate.BaseDao;
import org.apache.poi.ss.formula.functions.T;

import com.alqsoft.entity.log.UserLog;
import com.alqsoft.entity.member.Member;

/**
 * 
 * @Description: TODO
 * @author Wudi
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2017年3月7日 下午10:55:43
 * Copyright 
 */
public interface RegisterDao extends BaseDao<Member> {
	Member save(Member member); 
	
	


}
