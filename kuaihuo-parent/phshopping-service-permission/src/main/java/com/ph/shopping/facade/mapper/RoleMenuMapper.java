package com.ph.shopping.facade.mapper;

import com.ph.shopping.common.core.base.BaseMapper;
import com.ph.shopping.facade.permission.entity.RoleMenu;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @项目：phshopping-service-permission
 * @描述：
 * @作者： Mr.Shu
 * @创建时间：2017/5/17
 * @Copyright @2017 by Mr.Shu
 */
@Repository
public interface RoleMenuMapper extends BaseMapper<RoleMenu> {

    /**
     * @methodname deleteMenuByRoleId 的描述：通过角色id删除角色菜单
     * @param roleId
     * @return int
     * @author Mr.Shu
     * @create 2017/5/17
     */
    int deleteRoleMenuByRoleId(@Param("roleId") Long roleId);


    /**
     * @methodname insertRoleMenu 的描述：给角色id赋予菜单
     * @param roleId
     * @param menuIds
     * @return int
     * @author Mr.Shu
     * @create 2017/5/17
     */
    int insertRoleMenu(@Param("roleId") Long roleId, @Param("menuIds") List<Long> menuIds, @Param("createrId") Long createrId);


    /**
     * @methodname selectRoleMenuByRoleId 的描述：通过角色id查询角色菜单
     * @param roleId
     * @return java.util.List<java.lang.Long>
     * @author Mr.Shu
     * @create 2017/5/17
     */
    List<Long> selectRoleMenuByRoleId(@Param("roleId") Long roleId);
}
