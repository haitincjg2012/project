package com.ph.permission.api.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ph.base.BaseController;
import com.ph.log.SystemService;
import com.ph.shopping.common.core.customenum.RoleEnum;
import com.ph.shopping.common.core.customenum.SmsSourceEnum;
import com.ph.shopping.common.core.customenum.SystemOperateEnum;
import com.ph.shopping.common.util.core.ResultUtil;
import com.ph.shopping.common.util.page.PageBean;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.permission.dto.UpdatePassDTO;
import com.ph.shopping.facade.permission.dto.UpdateUserRoleDTO;
import com.ph.shopping.facade.permission.dto.UserDTO;
import com.ph.shopping.facade.permission.dto.UserRoleDTO;
import com.ph.shopping.facade.permission.entity.User;
import com.ph.shopping.facade.permission.exception.PermissionEnum;
import com.ph.shopping.facade.permission.service.IUserService;
import com.ph.shopping.facade.permission.vo.SessionUserVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * @项目：phshopping-api-platform
 * @描述：用户controller
 * @作者： Mr.Shu
 * @创建时间：2017年5月17日
 * @Copyright @2017 by Mr.Shu
 */
@Controller
@RequestMapping(value="web/permission/user")
public class UserController extends BaseController {

	private final static Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Reference(version="1.0.0")
	IUserService userService;

	//日志接口
	@Autowired
	private SystemService systemService;

//=============================================页面跳转================================================/

	/**
	 * @描述：跳转列表页面
	 *
	 * @param: null
	 *
	 * @return:
	 *
	 * @作者： Mr.Shu
	 *
	 * @创建时间：2017/5/17 17:32
	 */
	@RequestMapping(value = "/listPage", method = RequestMethod.GET)
	public String toListRolePage() {
        return "permission/user";
    }


	/**
	 * @描述：跳转新增页面
	 * @param: null
	 * @return:
	 * @作者： Mr.Shu
	 * @创建时间：2017/5/17 17:33
	 */
	@RequestMapping(value = "/addPage", method = RequestMethod.GET)
	public String toAddRolePage() {
		return "permission/user/add";
	}


	/**
	 * @描述：跳转修改页面
	 * @param: null
	 * @return:
	 * @作者： Mr.Shu
	 * @创建时间：2017/5/17 17:33
	 */
	@RequestMapping(value = "/updatePage", method = RequestMethod.GET)
	public ModelAndView toUpdateRolePage(@ModelAttribute User user) {
		ModelAndView mv = new ModelAndView("permission/user/update");
		Result result = userService.getUserById(user);
		mv.addObject("user", result.getData());
		return mv;
	}


//==========================================数据操作=================================================/

	//===============分配角色页面数据操作===================/

	/**
	 * @描述：获取用户拥有的角色列表
	 *
	 * @param: userId
	 *
	 * @return:
	 *
	 * @作者： Mr.Shu
	 *
	 * @创建时间：2017/5/18 9:32
	 */
	@RequestMapping(value = "/getUserRole", method = RequestMethod.GET)
	@ResponseBody
	public Object getUserRole(Long userId) {
		return userService.getUserRoleByUserId(userId);
	}


	/**
	 * @描述：分配角色
	 *
	 * @param: ud
	 *
	 * @return:
	 *
	 * @作者： Mr.Shu
	 *
	 * @创建时间：2017/5/18 9:32
	 */
	@RequestMapping(value = "/updateUserPermission", method = RequestMethod.POST)
	@ResponseBody
	public Object updateUserRole(@ModelAttribute UpdateUserRoleDTO ud) {
		//判断角色是否选择
		if (ud.getRoleIds() == null || ud.getRoleIds().size() == 0) {
			return new Result(false, PermissionEnum.ROLE_NOT_NULL_ERROR.getCode(),
					PermissionEnum.ROLE_NOT_NULL_ERROR.getMsg(), "[]", 0);
		}
		SessionUserVO sessionUserVO = getLoginUser();
		ud.setCreaterId(sessionUserVO.getId());
		ud.setUpdaterId(sessionUserVO.getId());

		//记录日志
		Result result = systemService.addSysLog(sessionUserVO, SystemOperateEnum.UPDATE.getType(), "分配角色");
		if (result.isSuccess()) {
			result = userService.updateUserRole(ud);
			//踢出该用户
			if (result.isSuccess()) {
				forceLogoutUsersByUserId(ud.getTelPhone());
			}
		}
		return result;
	}


	//===============用户增删改查===================/

	/**
	 * @描述：分页获取列表
	 * @param: page
	 * @param: userDto
	 * @return:
	 * @作者： Mr.Shu
	 * @创建时间：2017/5/17 17:34
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseBody
	public Object getUserListByPage(@ModelAttribute PageBean page, @ModelAttribute UserDTO userDto) {
		return userService.getUserListByPage(page, userDto);
	}

	/**
	 * @描述：新增用户
	 * @param: userDto
	 * @return:
	 * @作者： Mr.Shu
	 * @创建时间：2017/5/17 17:36
	 */
	@RequestMapping(value="/add",method= RequestMethod.POST)
	@ResponseBody
	public Object addUser(@ModelAttribute UserRoleDTO userDto) {
		SessionUserVO sessionUserVO = getLoginUser();
		userDto.setCreaterId(sessionUserVO.getId());
		userDto.setUpdaterId(sessionUserVO.getId());
		userDto.setRoleCode(RoleEnum.ADMIN.getCode());

		//后台用户添加，默认密码为123456
		userDto.setPassword("123456");
		//记录日志
		Result result = systemService.addSysLog(sessionUserVO, SystemOperateEnum.INSERT.getType(), "用户新增");
		if (result.isSuccess()) {
			result = userService.addUser(userDto);
		}
		return result;
	}

	/**
	 * @描述：修改用户
	 *
	 * @param: user
	 *
	 * @return:
	 *
	 * @作者： Mr.Shu
	 *
	 * @创建时间：2017/5/17 17:38
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public Object update(@ModelAttribute User user) {
		SessionUserVO sessionUserVO = getLoginUser();
		user.setUpdaterId(sessionUserVO.getId());
		//记录日志
		Result result = systemService.addSysLog(sessionUserVO, SystemOperateEnum.UPDATE.getType(), "用户编辑");
		if (result.isSuccess()) {
			result = userService.updateUser(user);
		}
		return result;
	}

	/**
	 * @描述：删除用户
	 *
	 * @param: null
	 *
	 * @return:
	 *
	 * @作者： Mr.Shu
	 *
	 * @创建时间：2017/5/17 17:37
	 */
	@RequestMapping(value="/delete",method= RequestMethod.POST)
	@ResponseBody
	public Object deleteUser(@ModelAttribute User user) {
		return userService.deleteUser(user);
	}


	//===============用户冻结与解冻，重置密码与修改密码===================/

	/**
	 * @描述：冻结账户
	 *
	 * @param: user
	 *
	 * @return:
	 *
	 * @作者： Mr.Shu
	 *
	 * @创建时间：2017/5/17 17:42
	 */
	@RequestMapping(value="/frozen",method= RequestMethod.POST)
	@ResponseBody
	public Object frozenCount(@ModelAttribute User user){
		SessionUserVO sessionUserVO = getLoginUser();
		user.setUpdaterId(sessionUserVO.getId());
		user.setIsable(new Byte("2"));

		//记录日志
		Result result = systemService.addSysLog(sessionUserVO, SystemOperateEnum.UPDATE.getType(), "用户冻结");
		if (result.isSuccess()) {
			result = userService.frozenOrEnableUser(user);
			if (result.isSuccess()) {
				forceLogoutUsersByUserId(user.getTelphone());
			}
		}
		return result;
	}
	
	/**
	 * @描述：启用账户
	 *
	 * @param: user
	 *
	 * @return:
	 *
	 * @作者： Mr.Shu
	 *
	 * @创建时间：2017/5/17 17:42
	 */
	@RequestMapping(value="/start",method= RequestMethod.POST)
	@ResponseBody
	public Object startCount(@ModelAttribute User user){
		SessionUserVO sessionUserVO = getLoginUser();
		user.setUpdaterId(sessionUserVO.getId());
		user.setIsable(new Byte("1"));

		//记录日志
		Result result = systemService.addSysLog(sessionUserVO, SystemOperateEnum.UPDATE.getType(), "用户解冻");
		if (result.isSuccess()) {
			result = userService.frozenOrEnableUser(user);
		}
		return result;
	}

	/**
	 * @描述：初始化或者重置密码
	 *
	 * @param: userId
	 *
	 * @return:
	 *
	 * @作者： Mr.Shu
	 *
	 * @创建时间：2017/5/17 17:43
	 */
	@RequestMapping(value = "/resetPass", method = RequestMethod.POST)
	@ResponseBody
	public Object resetPass(Long userId, String codeVal, String type) {
		SmsSourceEnum smsSourceEnum = null;
		SessionUserVO sessionUserVO = getLoginUser();
		User user = new User();
		user.setUpdaterId(sessionUserVO.getId());
		user.setId(userId);
		if (codeVal != null && !"".equals(codeVal)) {
			if (SmsSourceEnum.AGENT.getCode().equals(codeVal)) {
				smsSourceEnum = SmsSourceEnum.AGENT;
			} else if (SmsSourceEnum.MERCHANT.getCode().equals(codeVal)) {
				smsSourceEnum = SmsSourceEnum.MERCHANT;
			} else if (SmsSourceEnum.SUPPLIER.getCode().equals(codeVal)) {
				smsSourceEnum = SmsSourceEnum.SUPPLIER;
			}
		}
		//记录日志
		Result result = systemService.addSysLog(sessionUserVO, SystemOperateEnum.UPDATE.getType(), "初始化或者重置密码");
		if (result.isSuccess()) {
			result = userService.resetPassword(user, smsSourceEnum, type);
		}
		return result;
	}

	/**
	 * @描述：后台用户修改密码
	 *
	 * @param: updatePassDto
	 *
	 * @return:
	 *
	 * @作者： Mr.Shu
	 *
	 * @创建时间：2017/5/17 17:43
	 */
	@RequestMapping(value="/updatePsw",method= RequestMethod.POST)
	@ResponseBody
	public Object updatePsw(UpdatePassDTO updatePassDto) {
		Result result = ResultUtil.getResult(PermissionEnum.Code.FAIL);
			if (null != updatePassDto) {
				SessionUserVO sessionUserVO = getLoginUser();
				updatePassDto.setId(sessionUserVO.getId());
				//记录日志
				result = systemService.addSysLog(sessionUserVO, SystemOperateEnum.UPDATE.getType(), "修改密码");
				if (result.isSuccess()) {
					result = userService.updatePass(updatePassDto);
				}
			}
		return result;
	}

}
