package com.ph.shopping.facade.mapper;

import com.ph.shopping.common.core.base.BaseMapper;
import com.ph.shopping.facade.permission.entity.Btn;
import com.ph.shopping.facade.permission.vo.BtnVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @项目：phshopping-service-permission
 * @描述： 按钮mapper
 * @作者： Mr.Shu
 * @创建时间：2017-05-12
 * @Copyright @2017 by Mr.Shu
 */
@Repository
public interface BtnMapper extends BaseMapper<Btn> {

    /**
     * @param menuIds
     * @param roleId
     * @return java.util.List<com.ph.shopping.facade.permission.vo.MenuVO>
     * @methodname getBtnListByMenuId 的描述：根据角色Id和菜单id获取已分配（已选择）按钮
     * @author Mr.Shu
     * @create 2017/5/13
     */
    List<BtnVO> getBtnListByMenuIdsAndRoleId(@Param("menuIds") List<Long> menuIds, @Param("roleId") Long roleId);

    /**
     * @param menuId
     * @return java.util.List<com.ph.shopping.facade.permission.vo.BtnVO>
     * @methodname getAllBaseBtnByMenuId 的描述：通过菜单id查询该菜单的基础按钮
     * @author Mr.Shu
     * @create 2017/5/12
     */
    List<BtnVO> getAllBaseBtnByMenuId(@Param("menuId") Long menuId);

    /**
     * @param
     * @return java.util.List<com.ph.shopping.facade.permission.vo.BtnVO>
     * @methodname getAllBtn 的描述：查询所有按钮
     * @author Mr.Shu
     * @create 2017/5/13
     */
    List<BtnVO> getAllBtn();

}
