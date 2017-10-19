package com.ph.shopping.facade.permission.service.impl;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ph.shopping.common.util.core.ResultUtil;
import com.ph.shopping.common.util.page.PageBean;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.mapper.*;
import com.ph.shopping.facade.permission.constant.RoleIDEnum;
import com.ph.shopping.facade.permission.dto.RoleDTO;
import com.ph.shopping.facade.permission.dto.RoleMenuDTO;
import com.ph.shopping.facade.permission.entity.Role;
import com.ph.shopping.facade.permission.entity.UserRole;
import com.ph.shopping.facade.permission.exception.PermissionBizException;
import com.ph.shopping.facade.permission.exception.PermissionEnum;
import com.ph.shopping.facade.permission.service.IRoleService;
import com.ph.shopping.facade.permission.vo.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @项目：phshopping-service-permission
 *
 * @描述：角色实现层
 *
 * @作者：Mr.Shu
 *
 * @创建时间：2017-05-18
 *
 * @Copyright @2017 by Mr.Shu
 */
@Component
@Service(version="1.0.0")
public class RoleServiceImpl implements IRoleService {

	private static final Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);

    @Autowired
    private RoleMapper roleMapper;
    @Autowired
	private RoleMenuMapper roleMenuMapper;
    @Autowired
	private MenuMapper menuMapper;
	@Autowired
    private UserRoleMapper userRoleMapper;
	@Autowired
	private MenuBtnMapper menuBtnMapper;
	@Autowired
	private BtnMapper btnMapper;


	@Override
    @Transactional
    public Result addRole(Role role) {
    	try {
			role.setCreateTime(new Date());
			role.setUpdateTime(new Date());
			role.setStatus(0);//默认为启用状态
			roleMapper.insert(role);
			return ResultUtil.getResult(PermissionEnum.Code.SUCCESS);
		} catch (Exception ex) {
			logger.error("新增角色异常，ex={}", ex);
            throw new PermissionBizException(PermissionEnum.ADD_ROLE_ERROR);
        }
    }


	@Override
    @Transactional
    public Result updateRole(Role role) {
    	try {
			role.setUpdateTime(new Date());
			roleMapper.updateByPrimaryKeySelective(role);
			return ResultUtil.getResult(PermissionEnum.Code.SUCCESS);
		} catch (Exception ex) {
			logger.error("修改角色异常，ex={}", ex);
            throw new PermissionBizException(PermissionEnum.UPDATE_ROLE_ERROR);
        }

    }


	@Override
    @Transactional
    public Result deleteRole(Role role) {
    	try {
			//默认角色不能删除 角色id 1,2,3,4,5,6 或者 如果角色存在用户绑定，不能删除
			UserRole userRole = new UserRole();
			userRole.setRoleId(role.getId());
			if (CollectionUtils.isNotEmpty(userRoleMapper.selectUserRole(userRole))
					|| null != RoleIDEnum.getRoleEnumByCode(role.getId()))
				return ResultUtil.getResult(PermissionEnum.ROLE_CAN_NOT_DELETE);
			roleMapper.delete(role);
            roleMenuMapper.deleteRoleMenuByRoleId(role.getId());
            return ResultUtil.getResult(PermissionEnum.Code.SUCCESS);
		} catch (Exception ex) {
			logger.error("删除角色异常，ex={}", ex);
            throw new PermissionBizException(PermissionEnum.DELETE_ROLE_ERROR);
        }
    }


	@Override
	@Transactional
	public Result enableOrDisableRole(Role role) {
		try {
			Role dbRole = new Role();
			dbRole.setUpdateTime(new Date());
			dbRole.setId(role.getId());
			dbRole.setStatus(role.getStatus());
			roleMapper.updateByPrimaryKeySelective(dbRole);
			return ResultUtil.getResult(PermissionEnum.Code.SUCCESS);
		} catch (Exception ex) {
			logger.error("启用或者停用角色异常，ex={}", ex);
            throw new PermissionBizException(PermissionEnum.START_ERROR);
        }
	}

	@Override
	public Result getRoleListByPage(PageBean page,RoleDTO role) {
		PageHelper.startPage(page.getPageNum(), page.getPageSize());
        List<RoleVO> list = roleMapper.getRoleByPage(role);
        PageInfo<RoleVO> pageInfo = new PageInfo<>(list);
        return ResultUtil.getResult(PermissionEnum.Code.SUCCESS,pageInfo.getList(),pageInfo.getTotal());
	}

	@Override
	public Result getRoleList() {
        return ResultUtil.getResult(PermissionEnum.Code.SUCCESS, roleMapper.getAllRole());
    }

	@Override
    public Result getRoleMenuAndMenuBtnByRoleId(Long roleId) {
        //通过角色ID获取菜单列表
        List<MenuVO> list = menuMapper.getAllBaseMenuByRoleId(roleId);
        List<Long> menuIds = roleMenuMapper.selectRoleMenuByRoleId(roleId);

		//treevo 转换
		return ResultUtil.getResult(PermissionEnum.Code.SUCCESS, paramsTrans(insertBtn(list, roleId), menuIds));
	}

	@Override
	@Transactional
    public Result updateRoleMenuAndMenuBtn(RoleMenuDTO roleMenuDto, Map<Long, List<Long>> menuBtns) {
        try {
			//删除按钮
            List<Long> menuIds = roleMenuMapper.selectRoleMenuByRoleId(roleMenuDto.getRoleId());
            for (Long mId : menuIds) {
                menuBtnMapper.deleteBtnByMenuIdAndRoleId(mId, roleMenuDto.getRoleId());
            }
			//删除菜单
            roleMenuMapper.deleteRoleMenuByRoleId(roleMenuDto.getRoleId());

			//修改菜单
			if (roleMenuDto.getMenuIds() != null && roleMenuDto.getMenuIds().size() != 0) {
				roleMenuMapper.insertRoleMenu(roleMenuDto.getRoleId(), roleMenuDto.getMenuIds(), roleMenuDto.getCreaterId());
			}

            //修改按钮
			Set<Long> set = menuBtns.keySet();
			for (Long key : set) {
                menuBtnMapper.insertMenuBtn(key, menuBtns.get(key), roleMenuDto.getCreaterId(), roleMenuDto.getRoleId());
            }
            return ResultUtil.getResult(PermissionEnum.Code.SUCCESS);
		} catch (Exception ex) {
			logger.error("修改角色权限异常，ex={}", ex);
            throw new PermissionBizException(PermissionEnum.UPDATE_PERMISSION_ERROR);
        }
	}

    @Override
    public List<UserVO> getUsersByRoleId(Long roleId) {
        return roleMapper.getUserListByRoleId(roleId);
    }

    @Override
    public Result getRoleById(Long id) {
        Role r = roleMapper.selectByPrimaryKey(id);
        return ResultUtil.getResult(PermissionEnum.Code.SUCCESS, r);
    }

    @Override
	public List<RoleVO> getRoleByUserId(Long userId) {
		if (userId != null) {
			return roleMapper.getRoleListByUserId(userId);
		} else {
            return null;
        }

    }

//=====================================================私有通用方法======================================================/

	/**
	 * @描述：把按钮加入菜单树中
	 * @param: menuVOS
	 * @return: List<MenuVO>
	 * @作者： Mr.Shu
	 * @创建时间：2017/5/12 16:27
	 */
	private List<MenuVO> insertBtn(List<MenuVO> menuVOS, Long roleId) {
		List<MenuVO> btns = new ArrayList<MenuVO>();
		for (MenuVO menuVO : menuVOS) {
            List<BtnVO> BtnVOs = btnMapper.getAllBaseBtnByMenuId(menuVO.getId());
            List<Long> btnIds = menuBtnMapper.selectMenuBtnByMenuIdAndRoleId(menuVO.getId(), roleId);
            if (BtnVOs != null && BtnVOs.size() != 0) {
				for (BtnVO b : BtnVOs) {
					MenuVO btnMenuVo = new MenuVO();
					btnMenuVo.setBtnId(b.getId());
					btnMenuVo.setMenuName(b.getBtnName());
					btnMenuVo.setMenuUrl(b.getBtnUrl());
					btnMenuVo.setParentId(menuVO.getId());
					for (Long btnId : btnIds) {
						if (btnId.equals(btnMenuVo.getBtnId())) {
							btnMenuVo.setIsChecked(1);
						}
					}
					btns.add(btnMenuVo);
				}
			}
		}
		menuVOS.addAll(btns);
		return menuVOS;
	}
	
	/**
	 * 判断角色拥有的菜单
	 * @param menuVos
	 * @param menuIds
	 */
    private List<MenuTreeVO> paramsTrans(List<MenuVO> menuVos, List<Long> menuIds) {
        List<MenuTreeVO> list = null;
        MenuTreeVO menuTreeVo;
        if (CollectionUtils.isNotEmpty(menuVos)) {
			list = new ArrayList<>();
            for (MenuVO menuVo : menuVos) {
                menuTreeVo = new MenuTreeVO();
                transVo(menuTreeVo, menuVo);
				if (CollectionUtils.isNotEmpty(menuIds)) {
					for (Long menuId : menuIds) {
						if (menuId.equals(menuVo.getId())) {
							menuTreeVo.setChecked(true);
							break;
						}
					}
				}
				list.add(menuTreeVo);
			}
		}
		return list;
	}

    private void transVo(MenuTreeVO menuTreeVo, MenuVO menuVo) {
        menuTreeVo.setId(menuVo.getId());
		menuTreeVo.setpId(menuVo.getParentId());
		menuTreeVo.setName(menuVo.getMenuName());
		menuTreeVo.setBtnId(menuVo.getBtnId());
		if (1 == menuVo.getIsChecked()) {
			menuTreeVo.setChecked(true);
		}
		
	}
}
