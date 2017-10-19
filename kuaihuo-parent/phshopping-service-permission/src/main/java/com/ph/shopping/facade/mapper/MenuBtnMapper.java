package com.ph.shopping.facade.mapper;

import com.ph.shopping.common.core.base.BaseMapper;
import com.ph.shopping.facade.permission.entity.MenuBtn;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @项目：phshopping-service-permission
 * @描述： 菜单按钮关系mapper
 * @作者： Mr.Shu
 * @创建时间：2017-05-12
 * @Copyright @2017 by Mr.Shu
 */
@Repository
public interface MenuBtnMapper extends BaseMapper<MenuBtn> {

    /**
     * @param menuId
     * @param roleId
     * @return int
     * @methodname deleteBtnByMenuIdAndRoleId 的描述：通过角色id和菜单id删除菜单按钮
     * @author Mr.Shu
     * @create 2017/5/13
     */
    int deleteBtnByMenuIdAndRoleId(@Param("menuId") Long menuId, @Param("roleId") Long roleId);

    /**
     * @param menuId
     * @param btnIds
     * @return int
     * @methodname insertMenuBtn 的描述：给菜单id赋予按钮
     * @author Mr.Shu
     * @create 2017/5/13
     */
    int insertMenuBtn(@Param("menuId") Long menuId, @Param("btnIds") List<Long> btnIds, @Param("createrId") Long createrId, @Param("roleId") Long roleId);

    /**
     * @param menuId
     * @return java.util.List<java.lang.Long>
     * @methodname selectMenuBtn 的描述：通过菜单id和角色Id查询菜单按钮
     * @author Mr.Shu
     * @create 2017/5/13
     */
    List<Long> selectMenuBtnByMenuIdAndRoleId(@Param("menuId") Long menuId, @Param("roleId") Long roleId);

}
