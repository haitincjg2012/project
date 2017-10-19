package com.ph.shopping.facade.permission.service.impl;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.dubbo.config.annotation.Service;
import com.ph.shopping.common.util.core.ResultUtil;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.mapper.MenuMapper;
import com.ph.shopping.facade.mapper.RoleMapper;
import com.ph.shopping.facade.permission.exception.PermissionEnum;
import com.ph.shopping.facade.permission.service.IMenuService;
import com.ph.shopping.facade.permission.vo.MenuVO;
import com.ph.shopping.facade.permission.vo.RoleVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @项目：phshopping-service-permission
 * @描述：菜单service
 * @作者： Mr.Shu
 * @创建时间：2017-05-15
 * @Copyright @2017 by Mr.Shu
 */
@Component
@Service(version="1.0.0")
public class MenuServiceImpl implements IMenuService {

	private static final Logger logger = LoggerFactory.getLogger(MenuServiceImpl.class);
	
	@Autowired
	private MenuMapper menuMapper;
	
	@Autowired
	private RoleMapper roleMapper;

	@Override
    public Result getMenuTreeByUserId(Long userId) {
        try {
            List<RoleVO> list = roleMapper.getRoleListByUserId(userId);
            if (CollectionUtils.isEmpty(list)) {
				return ResultUtil.getResult(PermissionEnum.NO_PERMISSION);
			}
			List<Long> role = new ArrayList<>();
            for (RoleVO r : list) {
                role.add(r.getId());
			}
            List<MenuVO> menuVos = menuMapper.getMenuByRoleIds(role);
            return ResultUtil.getResult(PermissionEnum.Code.SUCCESS,getMenuTree(menuVos));
		} catch (Exception ex) {
			logger.error("通过用户id查询菜单异常，ex={}",ex);
		}
		return ResultUtil.getResult(PermissionEnum.Code.INTERNAL_SERVER_ERROR);
	}

    @Override
	public Result getMenuList() {
		return ResultUtil.getResult(PermissionEnum.Code.SUCCESS, menuMapper.getAllMenu());
	}

    @Override
    public List<MenuVO> getMenuByRoleIds(List<Long> roleIds) {
        return menuMapper.getMenuByRoleIds(roleIds);
    }

//===============================================私有通用方法================================================/

    /**
	 * 获取菜单树
	 * @param menuVos
	 * @return
	 */
    public static List<MenuVO> getMenuTree(List<MenuVO> menuVos) {
        List<MenuVO> treeList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(menuVos)) {
            for (MenuVO vo : menuVos) {
                if (null != vo.getParentId() && Long.valueOf(0).equals(vo.getParentId())) {
					vo = getChildMenu(vo,menuVos);
					treeList.add(vo);
				}
			}
		}
		return treeList;
	}

	private static MenuVO getChildMenu(MenuVO parent, List<MenuVO> menuVos) {
		List<MenuVO> child = null;
		for (MenuVO vo : menuVos) {
			if (parent.getId().equals(vo.getParentId())) {
				vo = getChildMenu(vo, menuVos);
				if (child == null) {
					child = new ArrayList<>();
				}
				child.add(vo);
			}
		}
		parent.setChild(child);
		return parent;
	}


}
