package com.alqsoft.controller.infotemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.alqframework.bean.MyBeanUtils;
import org.alqframework.easyui.EasyuiResult;
import org.alqframework.webmvc.servlet.ServletUtils;
import org.alqframework.webmvc.springmvc.Result;
import org.alqframework.webmvc.springmvc.SpringMVCUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alqsoft.entity.infotemplate.InfoType;
import com.alqsoft.service.infotemplate.InfoTypeService;

/**
 * 
 * @Title: InfoTypeController.java
 * @Description: 模板类型控制器
 * @author 俞旺
 * @e-mail 13606024548@139.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2015年4月21日 下午17:41:12
 * Copyright © 2013 厦门卓讯信息技术有限公司 All rights reserved.
 * 
 */
@RequestMapping("infotype")
@Controller
public class InfoTypeController {
	@Autowired
	private InfoTypeService infoTypeService;
	
	/**
	 * 跳转到模板类型视图页面
	 * @Title: infoTypeList
	 * @Description: TODO
	 * @param: @return
	 * @return: String
	 * @throws
	 */
	@RequestMapping("infotype-list")
	public String infoTypeList()
	{
		return "infotemplate/infotype-list";
	}
	/**
	 * 分页查询
	 * @Title: infoTypeListData
	 * @Description: TODO
	 * @param: @param page
	 * @param: @param rows
	 * @param: @param request
	 * @param: @return
	 * @return: EasyuiResult<List<InfoType>>
	 * @throws
	 */
	@RequestMapping("infotype-list-data")
	@ResponseBody
	public EasyuiResult<List<InfoType>> infoTypeListData(@RequestParam("page") Integer page,
			@RequestParam("rows") Integer rows, HttpServletRequest request){
		// 解析查询
		Map<String, Object> map = ServletUtils.getParametersStartingWith(request, "search_");
		return infoTypeService.getInfoTypePage(map, page, rows);
	}
	
	/**
	 * 新增或修改模板类型的视图
	 * @Title: infoType
	 * @Description: TODO
	 * @param: @param model
	 * @param: @param id
	 * @param: @return
	 * @return: String
	 * @throws
	 */
	@RequestMapping("infotype-input")
	public String infoTypeInput(Model model, @RequestParam(value = "id", required = false) Long id) {
		InfoType infoType = null;
		if (id != null) {
			infoType = infoTypeService.get(id);
			if (infoType != null)
				model.addAttribute(infoType);
		}
		return "infotemplate/infotype-input";
	}
	
	/**
	 * 新增和修改模板类型信息
	 * 
	 * @param user
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping("infotype-add")
	@ResponseBody
	public Result infoTypeAdd(@Valid InfoType infotype, BindingResult bindingResult) {
		// 验证判断是否有错
		if (!bindingResult.hasErrors()) {
				InfoType infoType2 = infoTypeService.getInfoTypeByTypeEnglishName(infotype.getInfoTypeEnglishName());
				if(infotype.getId()==null)
				{
					if(infoType2!=null)
					{
						return SpringMVCUtils.returnError("您新增的模板类型名已存在，请重新输入");
					}
					else
					{
						if (infoTypeService.saveAndModify(infotype)!=null) {
							return SpringMVCUtils.returnSuccess("模板类型保存成功");
						} else {
							return SpringMVCUtils.returnError("系统错误，请联系管理员");
						}

					}
				}
				else
				{
					infoType2 = infoTypeService.get(infotype.getId());
					MyBeanUtils.propertyUtils(infoType2, infotype);
					if (infoTypeService.saveAndModify(infoType2)!=null) {
						return SpringMVCUtils.returnSuccess("模板类型修改成功");
					} else {
						return SpringMVCUtils.returnError("系统错误，请联系管理员");
					}
				}
				
		} else {
			return SpringMVCUtils.returnError(bindingResult);
		}
	}
	
	/**
	 * 删除模板类型信息
	 * @Title: userDelete
	 * @Description: TODO
	 * @param: @param id
	 * @param: @return
	 * @return: Result
	 * @throws
	 */
	@RequestMapping("infotype-delete")
	@ResponseBody
	public Result infoTypeDelete(@RequestParam("id") String id) {
		String[] ids = StringUtils.split(id, ",");
		try {
			Arrays.stream(ids).forEach(s->{
				infoTypeService.delete(Long.parseLong(s));
			});
			return SpringMVCUtils.returnSuccess("删除模板类型成功");
		} catch (NumberFormatException e) {
			return SpringMVCUtils.returnError("删除模板类型失败");
		}
	}
	
	/**
	 * 模板选择跳转到模板类型选择页面
	 * @Title: windowInfoTypeList
	 * @Description: TODO
	 * @param: @return
	 * @return: String
	 * @throws
	 */
	@RequestMapping("window-infotype-list")
	public String windowInfoTypeList() {
		return "infotemplate/window-infotype-list";
	}
}
