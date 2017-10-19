package com.alqsoft.controller.user;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.alqframework.bean.MyBeanUtils;
import org.alqframework.easyui.EasyuiResult;
import org.alqframework.webmvc.servlet.ServletUtils;
import org.alqframework.webmvc.springmvc.Result;
import org.alqframework.webmvc.springmvc.SpringMVCUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alqsoft.annotations.AlqReplace;
import com.alqsoft.entity.role.Role;
import com.alqsoft.entity.user.User;
import com.alqsoft.service.role.RoleService;
import com.alqsoft.service.user.UserService;
import com.alqsoft.utils.SystemRole;
import com.alqsoft.utils.WebUtils;

/**
 * 用户控制器
 * 
 * @author 张靠勤
 * @e-mail 627658539@qq.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2013-7-26 下午8:01:14
 * 
 */
@RequestMapping("user")
@Controller
public class UserController {

	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private StandardPasswordEncoder passwordEncoder;
	
	
	@RequestMapping("user-password")
	public String userPassword() {
		return "user/user-password";
	}

	@RequestMapping("userpass-add")
	@ResponseBody
	public Result userpassAdd(@RequestParam("olePassword") String olePassword,
			@RequestParam("newPassword") String newPassword,
			@RequestParam("realPassword") String realPassword,HttpServletResponse response) {
		User user = WebUtils.getUser();
		if (passwordEncoder.matches(olePassword, user.getUserPassword())) {
			if (newPassword.equals(realPassword)) {
				user.setUserPassword(passwordEncoder.encode(newPassword));
				if (userService.saveAndModify(user)!=null) {
					return SpringMVCUtils.returnSuccess("您的密码修改成功！！！");
				} else {
					return SpringMVCUtils.returnError("发生系统错误，请联系管理员");
				}
			} else {
				return SpringMVCUtils.returnError("您两次新密码的输入不一致，请重新输入");
			}
		} else {
			return SpringMVCUtils.returnError("您的旧密码输入不正确，请重新输入");
		}
	}

	@RequestMapping("user-info")
	public String userInfo(){
		return "user/user-info";
	}
	
	
	/**
	 * 用户列表页面
	 * 
	 * @return
	 */
	@RequestMapping("user-list")
	public String userList(Model model) {
		List<Role> roles = roleService.getRoleAll();
		model.addAttribute("roles", roles);
		CollectionUtils.filter(roles, new Predicate() {

			@Override
			public boolean evaluate(Object object) {
				Role role = (Role) object;
				return role.getId()!=1l;
			}
		});
		return "user/user-list";
	}

	/**
	 * 分页查询
	 * 
	 * @param page
	 * @param rows
	 * @param request
	 * @return
	 */
	@RequestMapping("user-list-data")
	@ResponseBody
	@AlqReplace
	public EasyuiResult<List<User>> userListData(@RequestParam("page") Integer page,
			@RequestParam("rows") Integer rows, HttpServletRequest request) {
		// 解析查询
		Map<String, Object> map = ServletUtils.getParametersStartingWith(request, "search_");
		//不是超级管理员
		if(!WebUtils.getRoleName().equals(SystemRole.SUPERADMIN.getName())){
			map.put("NQ_role.id", "1");
		}
		return userService.getUserPage(map, page, rows);
	}

	/**
	 * 新增或者修改页面的视图
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("user-input")
	public String userInput(Model model, @RequestParam(value = "id", required = false) Long id,
			HttpServletRequest request) {
		HttpSession session = request.getSession();
		User systemUser = (User) session.getAttribute("user");
		User user = null;
		if (id != null) {
			user = userService.get(id);
			if (user != null)
				model.addAttribute(user);
		}
		List<Role> roles = roleService.getRoleAll();
		if (systemUser.getRole().getId() != 1) {
			CollectionUtils.filter(roles, new Predicate() {

				@Override
				public boolean evaluate(Object object) {
					Role role = (Role) object;
					return role.getId() != 1;
				}
			});
		}
		model.addAttribute("roles", roles);
		return "user/user-input";
	}

	/**
	 * 新增和修改用户信息
	 * 
	 * @param user
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping("user-add")
	@ResponseBody
	public Result userAdd(@Valid User user, BindingResult bindingResult) {
		// 判断角色
		if (user.getRole().getId() == null) {
			return SpringMVCUtils.returnError("请选择角色");
		}
		// 验证判断是否有错
		if (!bindingResult.hasErrors()) {
			User user2 = userService.getUserByName(user.getUserName());
			if (user.getId() == null) {
				if (user2 != null) {
					return SpringMVCUtils.returnError("您新增的用户名已经重复，请重新输入");
				} else {
					// 设置角色
					user.setRole(roleService.get(user.getRole().getId()));
					user.setUserPassword(passwordEncoder.encode(user.getUserPassword()));
					// 保存用户
					if (userService.saveAndModify(user)!=null) {
						return SpringMVCUtils.returnSuccess("用户保存成功");
					} else {
						return SpringMVCUtils.returnError("系统错误，请联系管理员");
					}
				}
			} else {
				user.setTimes(user2.getTimes());
				MyBeanUtils.propertyUtils(user2, user);
				// 设置角色
				user2.setRole(roleService.get(user.getRole().getId()));
				user2.setUserPassword(passwordEncoder.encode(user2.getUserPassword()));
				// 保存用户
				if (userService.saveAndModify(user2)!=null) {
					return SpringMVCUtils.returnSuccess("用户保存成功");
				} else {
					return SpringMVCUtils.returnError("系统错误，请联系管理员");
				}
			}

		} else {
			return SpringMVCUtils.returnError(bindingResult);
		}
	}

	/**
	 * 删除用户
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("user-delete")
	@ResponseBody
	public Result userDelete(@RequestParam("id") String id) {
		String[] ids = StringUtils.split(id, ",");
		try {
			for (String string : ids) {
				userService.delete(Long.parseLong(string));
			}
			return SpringMVCUtils.returnSuccess("删除用户成功");
		} catch (NumberFormatException e) {
			return SpringMVCUtils.returnError("删除用户失败");
		}
	}

}
