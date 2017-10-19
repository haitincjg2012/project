package com.alqsoft.entity.user;

import org.apache.ibatis.type.Alias;

import com.alqsoft.entity.IdEntity;



/**
 * 
 * 
 * @author 张靠勤
 * @e-mail 627658539@qq.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2013-2-27 下午4:36:10
 * 
 */
@Alias("user")
public class User extends IdEntity{
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
