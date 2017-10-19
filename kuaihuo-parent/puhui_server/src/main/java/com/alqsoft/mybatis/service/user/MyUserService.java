package com.alqsoft.mybatis.service.user;

import com.alqsoft.mybatis.entity.user.MyUser;


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
public interface MyUserService {
	public MyUser getUser(Long id);
	boolean updateUser(MyUser myUser);
}
