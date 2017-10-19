package com.alqsoft.service.user;

import com.alqsoft.entity.user.User;


/**
 * 
 * 
 * @author 张靠勤
 * @e-mail 627658539@qq.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2013-2-28 下午9:28:46
 * 
 */
public interface UserService {
	
	public User getUser(Long id);
	
	public boolean updateUser(User u);
}
