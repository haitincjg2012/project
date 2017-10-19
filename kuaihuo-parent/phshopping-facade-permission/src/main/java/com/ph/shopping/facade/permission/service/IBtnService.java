package com.ph.shopping.facade.permission.service;

import com.ph.shopping.facade.permission.vo.BtnVO;

import java.util.List;

/**
 * @项目：phshopping-facade-permission
 * @描述：按钮接口类
 * @作者： Mr.Shu
 * @创建时间：2017年5月13日
 * @Copyright @2017 by Mr.Shu
 */
public interface IBtnService {
    /**
     * @描述：根据菜单id获取按钮
     *
     * @param: roleId
     * @param: menuIds
     *
     * @return:
     *
     * @作者： Mr.Shu
     *
     * @创建时间：2017/5/17 17:51
     */
    List<BtnVO> getBtnListByMenuId(List<Long> menuIds, Long roleId);

    /**
     * @描述：获取所有的按钮
     * @param: null
     * @return:
     * @作者： Mr.Shu
     * @创建时间：2017/5/17 17:51
     */
    public List<BtnVO> getBtnList();

}
