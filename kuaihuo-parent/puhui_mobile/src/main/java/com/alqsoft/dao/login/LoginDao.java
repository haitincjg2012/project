package com.alqsoft.dao.login;

import com.alqsoft.entity.member.Member;
import org.alqframework.orm.mybatis.MyBatisRepository;

import java.util.List;
import java.util.Map;

/**
 * 
 * @Description: TODO
 * @author Wudi
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2017年3月3日 下午1:29:39
 * Copyright 
 */
@MyBatisRepository
public interface LoginDao {

	/**
	 * 获取当前用户信息
	 * */
	
	public Member getLoginData(String phone);
	
	/**
	 * 获取用户的基本信息
	 */
	public List<Map> getUserBaseMessager(String phone);

	public List<Map<String, Object>> getMemberByPhone(String phone);
	/**
	 * 快火批发APP接口根据hunter_id查询member
	 * @return 
	 */
	public Member getmemberinfo(Long hunter_id);

	/**通过phone查询member实体类
	 * @param phone
	 * @return
	 */
	public Map<String, Object> getMemberByPhone2(String phone);

	/**
	 * 判断手机号是否存在
	 * @param phone
	 * @return
	 */
	public Map getMemberAndMerchant(String phone);
}
