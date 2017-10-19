package com.alqsoft.dao.user;

import org.alqframework.orm.mybatis.MyBatisRepository;

import com.alqsoft.entity.user.User;

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
public interface UserDao {
	
	public User getUser(Long id);

	public void updateUser(User u);
}
