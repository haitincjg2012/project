package com.ph.permission.api.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ph.base.BaseController;
import com.ph.log.SystemService;
import com.ph.shopping.common.core.customenum.RoleEnum;
import com.ph.shopping.common.core.customenum.SystemOperateEnum;
import com.ph.shopping.common.util.page.PageBean;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.permission.dto.GetMenuTreeDTO;
import com.ph.shopping.facade.permission.dto.RoleDTO;
import com.ph.shopping.facade.permission.dto.RoleMenuDTO;
import com.ph.shopping.facade.permission.entity.Role;
import com.ph.shopping.facade.permission.service.IMenuService;
import com.ph.shopping.facade.permission.service.IRoleService;
import com.ph.shopping.facade.permission.vo.MenuTreeVO;
import com.ph.shopping.facade.permission.vo.SessionUserVO;
import com.ph.shopping.facade.permission.vo.UserVO;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

/**
 * @项目：phshopping-api-platform
 *
 * @描述：角色controller
 *
 * @作者： Mr.Shu
 *
 * @创建时间：2017年5月17日
 *
 * @Copyright @2017 by Mr.Shu
 */
@Controller
@RequestMapping(value="web/permission/role")
public class RoleController extends BaseController{
	
	private static Logger logger = LoggerFactory.getLogger(RoleController.class);

	@Reference(version="1.0.0")
	IRoleService roleService;

    @Reference(version = "1.0.0")
    IMenuService menuService;

    //日志接口
    @Autowired
    private SystemService systemService;


//=========================================页面跳转===============================================/

    /**
     * @描述：跳转角色列表页面
     *
     * @param: null
     *
     * @return:
     *
     * @作者： Mr.Shu
     *
     * @创建时间：2017/5/17 16:55
     */
	@RequestMapping(value="/listPage",method=RequestMethod.GET)
    public String toListRolePage() {
        return "permission/role";
    }
	

	/**
     * @描述：跳转新增页面
     *
     * @param: null
     *
     * @return:
     *
     * @作者： Mr.Shu
     *
     * @创建时间：2017/5/17 16:55
     */
	@RequestMapping(value="/addPage",method=RequestMethod.GET)
    public String toAddRolePage() {
        return "permission/role/add";
    }
	

	/**
     * @描述：跳转修改页面
     *
     * @param: null
     *
     * @return:
     *
     * @作者： Mr.Shu
     *
     * @创建时间：2017/5/17 16:56
     */
	@RequestMapping(value="/updatePage",method=RequestMethod.GET)
    public ModelAndView toUpdateRolePage(Long id) {
        ModelAndView mv = new ModelAndView("permission/role/update");
        Result result=roleService.getRoleById(id);
		mv.addObject("role", result.getData());
		return mv;
	}
	
	/**
     * @描述：跳转权限分配页面
     *
     * @param: roleId
     * @param: model
     *
     * @return:
     *
     * @作者： Mr.Shu
     *
     * @创建时间：2017/5/17 16:58
     */
	@RequestMapping(value="/permissionPage",method=RequestMethod.GET)
    public String toPermissionPage(Long roleId, Model model) {
        model.addAttribute("roleId", roleId);
        return "permission/role/roleMenu";
    }


//============================================数据操作================================================/

    //=================分配权限页面菜单树的获取与修改=========/

    /**
     * @描述：根据角色id查询分配权限页面的菜单树
     *
     * @param: roleId
     *
     * @return:
     *
     * @作者： Mr.Shu
     *
     * @创建时间：2017/5/17 17:00
     */
    @RequestMapping(value = "/getRoleMenu", method = RequestMethod.POST)
    @ResponseBody
    public Object getRoleMenuByRoleId(Long roleId) {
        Result result = roleService.getRoleMenuAndMenuBtnByRoleId(roleId);
        return result;
    }
	
	

	/**
     * @描述：修改分配权限页面角色菜单树
     *
     * @param: getMenuTreeDTO
     *
     * @return:
     *
     * @作者： Mr.Shu
     *
     * @创建时间：2017/5/17 17:02
     */
    @RequestMapping(value="/updateRoleMenu",method=RequestMethod.POST)
	@ResponseBody
    public Object updateRoleMenu(@RequestBody GetMenuTreeDTO getMenuTreeDTO) {

        //获取前端传来的json数据
        List<MenuTreeVO> menuTreeVOS = getMenuTreeDTO.getMenuTreeVOS();
        Long roleId = getMenuTreeDTO.getRoleId();

        //组装数据
        List<Long> listMenu = new ArrayList<>();
        Map<Long, List<Long>> menuBtns = new HashMap<>();
        if (menuTreeVOS != null && menuTreeVOS.size() != 0) {
            for (MenuTreeVO mtv : menuTreeVOS) {
                Long menuId = mtv.getId();
                Long btnId = mtv.getBtnId();
                Long pId = mtv.getpId();
                if (menuId != null && !"".equals(menuId)) {
                    listMenu.add(menuId);
                } else if (btnId != null && !"".equals(btnId)) {

                    if (menuBtns.containsKey(pId)) {
                        List<Long> listBtn = menuBtns.get(pId);
                        listBtn.add(btnId);
                    } else {
                        List<Long> listBtn = new ArrayList<>();
                        listBtn.add(btnId);
                        menuBtns.put(pId, listBtn);
                    }
                }
            }
        }

        //获取Session
        SessionUserVO sessionUserVO = getLoginUser();

        //修改角色菜单
        RoleMenuDTO roleMenuDto = new RoleMenuDTO();
        roleMenuDto.setRoleId(roleId);
        roleMenuDto.setMenuIds(listMenu);
        roleMenuDto.setCreaterId(sessionUserVO.getId());

        //记录日志
        Result result = systemService.addSysLog(sessionUserVO, SystemOperateEnum.UPDATE.getType(), "分配权限");
        if (result.isSuccess()) {
            result = roleService.updateRoleMenuAndMenuBtn(roleMenuDto, menuBtns);
            if (result.isSuccess()) {
                forceLogoutUsersByRoleId(roleId);
            }
        }
        return result;
    }


    //===========================================角色的增删改查，禁用与启用================================================/

    //==============禁用与启用===========/

    /**
     * @描述：启用角色
     *
     * @param: role
     *
     * @return:
     *
     * @作者： Mr.Shu
     *
     * @创建时间：2017/5/17 17:03
     */
	@RequestMapping(value="/enable",method=RequestMethod.POST)
	@ResponseBody
	public Object enable(@ModelAttribute Role role){
        SessionUserVO sessionUserVO = getLoginUser();
        role.setUpdaterId(sessionUserVO.getId());
        role.setStatus(0);
        //记录日志
        Result result = systemService.addSysLog(sessionUserVO, SystemOperateEnum.UPDATE.getType(), "启用角色");
        if (result.isSuccess()) {
            result = roleService.enableOrDisableRole(role);
        }
        return result;

	}


	/**
     * @描述：禁用角色
     *
     * @param: role
     *
     * @return:
     *
     * @作者： Mr.Shu
     *
     * @创建时间：2017/5/17 17:03
     */
	@RequestMapping(value="/disable",method=RequestMethod.POST)
	@ResponseBody
	public Object disable(@ModelAttribute Role role){
        SessionUserVO sessionUserVO = getLoginUser();
        role.setUpdaterId(sessionUserVO.getId());
        role.setStatus(1);
        //记录日志
        Result result = systemService.addSysLog(sessionUserVO, SystemOperateEnum.UPDATE.getType(), "禁用角色");
        if (result.isSuccess()) {
            result = roleService.enableOrDisableRole(role);
        }
        return result;
    }


    //==============增删改查===========/

    /**
     * @描述 分页获取角色列表
     * @param: page
     * @param: role
     * @return:
     * @作者： Mr.Shu
     * @创建时间：2017/5/17 17:03
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public Object list(@ModelAttribute PageBean page, @ModelAttribute RoleDTO role) {
        return roleService.getRoleListByPage(page, role);
    }

    /**
     * @描述：获取所有角色(管理员code)
     * @param: null
     * @return:
     * @作者： Mr.Shu
     * @创建时间：2017/5/18 9:29
     */
    @RequestMapping(value = "/list/all", method = RequestMethod.GET)
    @ResponseBody
    public Object listAll() {
        //查询管理员类型的roleCode
        return roleService.getRoleList();
    }

    /**
     * @描述：新增角色
     * @param: role
     * @return:
     * @作者： Mr.Shu
     * @创建时间：2017/5/17 17:05
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public Object addRole(@ModelAttribute Role role) {
        SessionUserVO sessionUserVO = getLoginUser();
        role.setCreaterId(sessionUserVO.getId());
        role.setUpdaterId(sessionUserVO.getId());
        role.setRoleCode(Byte.valueOf(RoleEnum.ADMIN.getCode() + ""));
        //记录日志
        Result result = systemService.addSysLog(sessionUserVO, SystemOperateEnum.INSERT.getType(), "新增角色");
        if (result.isSuccess()) {
            result = roleService.addRole(role);
        }
        return result;
    }

    /**
     * @描述：修改角色
     * @param: role
     * @return:
     * @作者： Mr.Shu
     * @创建时间：2017/5/17 17:06
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public Object updateRole(@ModelAttribute Role role) {
        SessionUserVO sessionUserVO = getLoginUser();
        role.setUpdaterId(sessionUserVO.getId());
        //记录日志
        Result result = systemService.addSysLog(sessionUserVO, SystemOperateEnum.UPDATE.getType(), "编辑角色");
        if (result.isSuccess()) {
            result = roleService.updateRole(role);
        }
        return result;
    }

    /**
     * @描述：删除角色
     * @param: role
     * @return:
     * @作者： Mr.Shu
     * @创建时间：2017/5/17 17:06
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public Object deleteRole(@ModelAttribute Role role) {
        SessionUserVO sessionUserVO = getLoginUser();
        //记录日志
        Result result = systemService.addSysLog(sessionUserVO, SystemOperateEnum.DELETE.getType(), "删除角色");
        if (result.isSuccess()) {
            result = roleService.deleteRole(role);
        }
        return result;
    }


//======================================================私有逻辑处理======================================================/

    /**
     * @描述：通过SessionManage获取在线用户列表修改角色后提出拥有该角色的用户
     * @param: roleId
     * @return:
     * @作者： Mr.Shu
     * @创建时间：2017/5/16 17:05
     */
    private void forceLogoutUsersByRoleId(Long roleId) {
        //处理session
        DefaultWebSecurityManager securityManager = (DefaultWebSecurityManager) SecurityUtils.getSecurityManager();
        DefaultWebSessionManager sessionManager = (DefaultWebSessionManager) securityManager.getSessionManager();
        Collection<Session> sessions = sessionManager.getSessionDAO().getActiveSessions();//获取当前已登录的用户session列表

        List<UserVO> userVoList = roleService.getUsersByRoleId(roleId);
        for (UserVO u : userVoList) {
            for (Session session : sessions) {
                //清除该用户以前登录时保存的session
                if (u.getTelphone().equals(String.valueOf(session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY)))) {
                    sessionManager.getSessionDAO().delete(session);
                }
            }
        }

    }

}
