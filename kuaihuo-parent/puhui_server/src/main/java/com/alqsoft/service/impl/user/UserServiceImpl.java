package com.alqsoft.service.impl.user;

import java.util.List;
import java.util.Map;

import org.alqframework.easyui.EasyUtils;
import org.alqframework.easyui.EasyuiResult;
import org.alqframework.orm.filter.DynamicSpecifications;
import org.alqframework.orm.filter.SearchFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alqsoft.dao.user.UserDao;
import com.alqsoft.entity.user.User;
import com.alqsoft.service.user.UserService;

/**
 * 用户管理实体
 * 
 * @author 张靠勤
 * @e-mail 627658539@qq.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2013-6-6 下午10:53:50
 * 
 */
@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private UserDao userDao;

	@Override
	@Transactional
	public boolean delete(Long arg0) {
		try{
			userDao.delete(arg0);
			return true;
		}catch(Exception e){
			return false;
		}
	}

	@Override
	public User get(Long arg0) {
		return userDao.findOne(arg0);
	}

	@Override
	@Transactional
	public User saveAndModify(User arg0) {
		return userDao.save(arg0);
	}

	@Override
	public User getUserByName(String userName) {
		return userDao.getUserByName(userName);
	}

	@Override
	public EasyuiResult<List<User>> getUserPage(Map<String, Object> map, Integer page, Integer rows) {
		Map<String, SearchFilter> filter = SearchFilter.parse(map);
		Specification<User> specification = DynamicSpecifications.bySearchFilter(filter.values(),
				User.class);
		Page<User> userPage = userDao.findAll(specification, new PageRequest(page - 1, rows,
				new Sort(Direction.DESC, new String[] { "updateTime" })));
		return EasyUtils.returnPage(User.class, userPage);
	}

	@Override
	public List<User> getUsersByRoleId(Long roleId) {
		return userDao.getUsersByRoleId(roleId);
	}

	@Override
	public User getUserByNameIsLocked(String userName) {
		return userDao.getUserByNameIsLocked(userName);
	}

	@Override
	public User getUserByNameByUnLocal(String userName) {
		return userDao.getUserByNameByUnLocal(userName);
	}

}
