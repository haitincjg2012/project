package com.ph.shopping.facade.permission.service;

import com.ph.shopping.common.util.page.PageBean;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.permission.dto.RoleDTO;
import com.ph.shopping.facade.permission.dto.RoleMenuDTO;
import com.ph.shopping.facade.permission.entity.Role;
import com.ph.shopping.facade.permission.vo.RoleVO;
import com.ph.shopping.facade.permission.vo.UserVO;

import java.util.List;
import java.util.Map;

/**
 * @项目：phshopping-facade-permission
 *
 * @描述：角色接口
 *
 * @作者： Mr.Shu
 *
 * @创建时间：2017-03-13
 *
 * @Copyright @2017 by Mr.Shu
 */
public interface IRoleService {

    //==================================单个角色表操作========================================/
    /**
     * @描述：新增角色
     *
     * @param: role
     *
     * @return: Result
     *
     * @作者： Mr.Shu
     *
     * @创建时间：2017/5/17 17:59
     */
    Result addRole(Role role);


    /**
     * @描述：修改角色
     *
     * @param: role
     *
     * @return: Result
     *
     * @作者： Mr.Shu
     *
     * @创建时间：2017/5/17 17:59
     */
    Result updateRole(Role role);

    /**
     * @描述：删除角色
     *
     * @param: role
     *
     * @return: Result
     *
     * @作者： Mr.Shu
     *
     * @创建时间：2017/5/17 17:59
     */
    Result deleteRole(Role role);

    /**
     * @描述：启用或者停用角色
     *
     * @param: role
     *
     * @return: Result
     *
     * @作者： Mr.Shu
     *
     * @创建时间：2017/5/17 18:00
     */
    Result enableOrDisableRole(Role role);

    /**
     * @描述：通过角色id查询角色信息
     *
     * @param: roleId
     *
     * @return: Result
     *
     * @作者： Mr.Shu
     *
     * @创建时间：2017/5/17 18:03
     */
    Result getRoleById(Long roleId);

    /**
     * @描述：分页查询角色
     *
     * @param: page
     * @param: role
     *
     * @return: Result
     *
     * @作者： Mr.Shu
     *
     * @创建时间：2017/5/17 18:00
     */
    Result getRoleListByPage(PageBean page,RoleDTO role);

    /**
     * @描述：查询所有角色
     *
     * @return: Result
     *
     * @作者： Mr.Shu
     *
     * @创建时间：2017/5/17 18:00
     */
    Result getRoleList();


    //========================================联合权限关系表操作===============================================/
    /**
     * @描述：通过角色id查询菜单
     *
     * @param: roleId
     *
     * @return: Result
     *
     * @作者： Mr.Shu
     *
     * @创建时间：2017/5/17 18:02
     */
    Result getRoleMenuAndMenuBtnByRoleId(Long roleId);

    /**
     * @描述：通过角色修改角色菜单和菜单按钮
     *
     * @param: roleMenuDto
     * @param: menuBtns
     *
     * @return: Result
     *
     * @作者： Mr.Shu
     *
     * @创建时间：2017/5/17 18:04
     */
    Result updateRoleMenuAndMenuBtn(RoleMenuDTO roleMenuDto, Map<Long, List<Long>> menuBtns);

    /**
     * @描述：通过角色Id查询用户列表
     *
     * @param: roleId
     *
     * @return: List<UserVO>
     *
     * @作者： Mr.Shu
     *
     * @创建时间：2017/5/17 18:05
     */
    List<UserVO> getUsersByRoleId(Long roleId);


    /**
     * @描述：根据用户Id查询角色集合
     * @param: userId
     * @return: List<RoleVO>
     * @作者： Mr.Shu
     * @创建时间：2017/5/17 18:05
     */
    List<RoleVO> getRoleByUserId(Long userId);


}
