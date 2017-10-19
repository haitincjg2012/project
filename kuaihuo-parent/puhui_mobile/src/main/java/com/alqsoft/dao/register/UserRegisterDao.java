package com.alqsoft.dao.register;

import java.util.List;

import org.alqframework.orm.mybatis.MyBatisRepository;

import com.alqsoft.entity.member.Member;

/**
 * 
 * @Description: TODO
 * @author Wudi
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2017年3月7日 下午9:28:34
 * Copyright 
 */
@MyBatisRepository
public interface UserRegisterDao {
	
	public Long getId(String phone);
	
	public Member findAllById(String phone);

}
