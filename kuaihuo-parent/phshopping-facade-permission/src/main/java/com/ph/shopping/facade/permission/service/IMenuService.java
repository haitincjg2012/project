package com.ph.shopping.facade.permission.service;

import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.permission.vo.MenuVO;

import java.util.List;

/**
 * @项目：phshopping-facade-permission
 *
 * @描述：菜单接口类
 *
 * @作者： Mr.chang
 *
 * @创建时间：2017年3月13日
 *
 * @Copyright @2017 by Mr.chang
 */
public interface IMenuService {

	/**
     * @描述：根据用户id查询菜单列表树型结构
     *
     * @param: userId
     *
     * @return:
     *
	 * @作者： Mr.Shu
	 *
     * @创建时间：2017/5/17 18:21
     */
    public Result getMenuTreeByUserId(Long userId);

	/**
     * @描述：获取所有的菜单
     *
     * @param: null
     *
     * @return:
     *
	 * @作者： Mr.Shu
	 *
     * @创建时间：2017/5/17 17:55
     */
	public Result getMenuList();

	/**
     * @描述：根据角色获取权限
     *
     * @param: roleIds
     *
     * @return:
     *
	 * @作者： Mr.Shu
	 *
     * @创建时间：2017/5/17 17:53
     */
    List<MenuVO> getMenuByRoleIds(List<Long> roleIds);

}
