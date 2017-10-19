package com.ph.shopping.facade.mapper;

import com.ph.shopping.common.core.base.BaseMapper;
import com.ph.shopping.facade.permission.dto.RoleDTO;
import com.ph.shopping.facade.permission.entity.Role;
import com.ph.shopping.facade.permission.vo.RoleVO;
import com.ph.shopping.facade.permission.vo.UserVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @项目：phshopping-service-permission
 * @描述： 角色mapper
 * @作者： Mr.Shu
 * @创建时间：2017-05-12
 * @Copyright @2017 by Mr.Shu
 */
@Repository
public interface RoleMapper extends BaseMapper<Role> {

	/**
     * @methodname getRoleListByUserId 的描述：根据用户id获取角色列表
     * @param userId
	 * @return java.util.List<com.ph.shopping.facade.permission.entity.Role>
	 * @author Mr.Shu
	 * @create 2017/5/17
     */
    List<RoleVO> getRoleListByUserId(@Param("userId") Long userId);

	/**
	 * @methodname getRoleByPage 的描述：分页查询角色信息
     * @param roleDTO
     * @return java.util.List<com.ph.shopping.facade.permission.vo.RoleVO>
     * @author 郑朋
	 * @create 2017/4/24
	 */
    List<RoleVO> getRoleByPage(RoleDTO roleDTO);

	/**
	 * @methodname getAllRole 的描述：查询所有的角色
	 * @param
	 * @return java.util.List<com.ph.shopping.facade.permission.entity.Role>
	 * @author 郑朋
	 * @create 2017/4/24
	 */
    List<RoleVO> getAllRole();

	/**
	 * @methodname selectRoleBySelective 的描述：通过条件查询角色
	 * @param role
	 * @return java.util.List<com.ph.shopping.facade.permission.entity.Role>
	 * @author 郑朋
	 * @create 2017/4/24
	 */
	List<Role> selectRoleBySelective(Role role);

    /**
     * @param roleId
     * @return List<UserVO>
     * @methodname getUserListByRoleId 的描述：通过角色Id查询用户列表
	 * @author Mr.Shu
	 * @create 2017/5/13
     */
    List<UserVO> getUserListByRoleId(@Param("roleId") Long roleId);
}
