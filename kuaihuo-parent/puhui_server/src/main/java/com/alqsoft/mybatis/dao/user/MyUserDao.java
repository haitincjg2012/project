package com.alqsoft.mybatis.dao.user;

import org.alqframework.orm.mybatis.MyBatisRepository;

import com.alqsoft.mybatis.entity.user.MyUser;

/**
 * 
 * 
 * @author 张靠勤
 * @e-mail 627658539@qq.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2013-2-28 下午9:19:46
 * 
 */
@MyBatisRepository
public interface MyUserDao {
	public MyUser getUser(Long id);
	public void updateUser(MyUser myUser);
}
