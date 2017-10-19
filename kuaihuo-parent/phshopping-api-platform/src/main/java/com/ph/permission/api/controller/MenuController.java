package com.ph.permission.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import com.alibaba.dubbo.config.annotation.Reference;
import com.ph.base.BaseController;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.permission.service.IMenuService;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @项目：phshopping-api-platform
 *
 * @描述：菜单controller
 *
 * @作者： Mr.Shu
 *
 * @创建时间：2017年5月17日
 *
 * @Copyright @2017 by Mr.Shu
 */
@Controller
@RequestMapping("web/permission/menu")
public class MenuController extends BaseController{
	
	@Reference(version="1.0.0")
	IMenuService menuService;

	/**
     * @描述：通过用户id获取菜单
     *
     * @param: userId
     *
     * @return:
     *
	 * @作者： Mr.Shu
	 *
     * @创建时间：2017/5/17 17:46
     */
	@RequestMapping(value="/list",method=RequestMethod.GET)
	@ResponseBody
	public Object getMenuByUserId(@RequestParam(value="userId",required=true)Long userId){
        Result result = menuService.getMenuTreeByUserId(userId);
        return result;
	}
}
