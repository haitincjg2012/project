package com.alqsoft.service.impl.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alqsoft.anno.Explosionproof;
import com.alqsoft.dao.user.UserDao;
import com.alqsoft.entity.user.User;
import com.alqsoft.service.user.UserService;

/**
 * 
 * 
 * @author 张靠勤
 * @e-mail 627658539@qq.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2013-2-28 下午9:30:56
 * 
 */
@Service
@Transactional(readOnly=true)
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

	public User getUser(Long id) {
		return userDao.getUser(id);
	}

	@Override
	@Explosionproof
	public boolean updateUser(User u) {
		System.out.println("\\\\\\\\\\\\\\\\\\///\\\\\\\\\\\\\\\\\\");
		try{
			userDao.updateUser(u);
			return true;
		}catch(Exception e){
			System.out.println(e.getMessage());
			return false;
		}
	}

}
