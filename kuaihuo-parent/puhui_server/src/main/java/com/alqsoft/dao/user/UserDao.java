package com.alqsoft.dao.user;

import java.util.List;

import org.alqframework.orm.hibernate.BaseDao;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.alqsoft.entity.user.User;

/**
 * 用户dao
 * 
 * @author 张靠勤
 * @e-mail 627658539@qq.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2013-6-5 下午8:23:19
 * 
 */
public interface UserDao extends BaseDao<User> {

	@Query("SELECT u FROM User u WHERE u.userName=:userName")
	public User getUserByName(@Param("userName") String userName); 
	/**
	 * 根据用户名和激活状态查询用户
	 * 
	 * @param userName
	 * @return
	 */
	@Query("SELECT u FROM User u WHERE u.userName=:userName AND isLocked=0")
	public User getUserByNameByUnLocal(@Param("userName") String userName);
	
	/**
	 * 
	* @Title: getUserByNameIsLocked 
	* @Description: 根据用户名查询未被激活的状态查询用户
	* @param @param userName
	* @param @return    设定文件 
	* @return User    返回类型 
	* @throws
	 */
	@Query("SELECT u FROM User u WHERE u.userName=:userName AND isLocked=1")
	public User getUserByNameIsLocked(@Param("userName") String userName);

	/**
	 * 根据角色id查找用户
	 * 
	 * @param roleId
	 * @return
	 */
	@Query("from User u where u.role.id=:roleId")
	public List<User> getUsersByRoleId(@Param("roleId") Long roleId);
}
