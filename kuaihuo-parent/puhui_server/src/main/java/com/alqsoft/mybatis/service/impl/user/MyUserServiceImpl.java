package com.alqsoft.mybatis.service.impl.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alqsoft.mybatis.dao.user.MyUserDao;
import com.alqsoft.mybatis.entity.user.MyUser;
import com.alqsoft.mybatis.service.user.MyUserService;


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
@Transactional(value="mybatistransactionManager",readOnly=true)
public class MyUserServiceImpl implements MyUserService{

	@Autowired
	private MyUserDao myUserDao;
	
	@Override
	public MyUser getUser(Long id) {
		return myUserDao.getUser(id);
	}
	@Override
	public boolean updateUser(MyUser myUser){
		try{
			myUserDao.updateUser(myUser);
			return true;
		}catch(Exception e){
			System.out.println(e.getMessage());
			return false;
		}
	}
}
