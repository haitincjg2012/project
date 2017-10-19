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

import com.alqsoft.entity.infotemplate.EmailTemplate;
import com.alqsoft.service.infotemplate.EmailTemplateService;

/**
 * 
 * @Title: EmailTemplateController.java
 * @Description: 邮箱模板控制器
 * @author 俞旺
 * @e-mail 13606024548@139.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2015年4月20日 下午2:41:12
 * Copyright © 2013 厦门卓讯信息技术有限公司 All rights reserved.
 * 
 */
@RequestMapping("emailtemplate")
@Controller
public class EmailTemplateController {
	@Autowired
	private EmailTemplateService emailTemplateService;
	
	/**
	 * 跳转到邮箱模板视图页面
	 * @Title: emailTemplateList
	 * @Description: TODO
	 * @param: @return
	 * @return: String
	 * @throws
	 */
	@RequestMapping("emailtemplate-list")
	public String emailTemplateList()
	{
		return "infotemplate/emailtemplate-list";
	}
	/**
	 * 分页查询
	 * @Title: emailTemplateListData
	 * @Description: TODO
	 * @param: @param page
	 * @param: @param rows
	 * @param: @param request
	 * @param: @return
	 * @return: EasyuiResult<List<EmailTemplate>>
	 * @throws
	 */
	@RequestMapping("emailtemplate-list-data")
	@ResponseBody
	public EasyuiResult<List<EmailTemplate>> emailTemplateListData(@RequestParam("page") Integer page,
			@RequestParam("rows") Integer rows, HttpServletRequest request){
		// 解析查询
		Map<String, Object> map = ServletUtils.getParametersStartingWith(request, "search_");
		return emailTemplateService.getEmailTemplatePage(map, page, rows);
	}
	
	/**
	 * 新增或修改邮箱模板的视图
	 * @Title: emailTemplate
	 * @Description: TODO
	 * @param: @param model
	 * @param: @param id
	 * @param: @return
	 * @return: String
	 * @throws
	 */
	@RequestMapping("emailtemplate-input")
	public String emailTemplate(Model model, @RequestParam(value = "id", required = false) Long id) {
		EmailTemplate emailTemplate = null;
		if (id != null) {
			emailTemplate = emailTemplateService.get(id);
			if (emailTemplate != null)
				model.addAttribute(emailTemplate);
		}
		return "infotemplate/emailtemplate-input";
	}
	
	/**
	 * 新增和修改邮箱模板信息
	 * 
	 * @param user
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping("emailtemplate-add")
	@ResponseBody
	public Result emailTemplateAdd(@Valid EmailTemplate emailtemplate, BindingResult bindingResult) {
		// 验证判断是否有错
		if (!bindingResult.hasErrors()) {
				EmailTemplate emailTemplate2 = emailTemplateService.getEmailTemplateByEnglishName(emailtemplate.getEmailEnglishName());
				if(emailtemplate.getId()==null)
				{
					if(emailTemplate2!=null)
					{
						return SpringMVCUtils.returnError("您新增的邮箱模板类型名已存在，请重新输入");
					}
					else
					{
						if (emailTemplateService.saveAndModify(emailtemplate)!=null) {
							return SpringMVCUtils.returnSuccess("邮箱模板保存成功");
						} else {
							return SpringMVCUtils.returnError("系统错误，请联系管理员");
						}

					}
				}
				else
				{
					emailTemplate2 = emailTemplateService.get(emailtemplate.getId());
					MyBeanUtils.propertyUtils(emailTemplate2, emailtemplate);
					if (emailTemplateService.saveAndModify(emailTemplate2)!=null) {
						return SpringMVCUtils.returnSuccess("邮箱模板修改成功");
					} else {
						return SpringMVCUtils.returnError("系统错误，请联系管理员");
					}
				}
				
		} else {
			return SpringMVCUtils.returnError(bindingResult);
		}
	}
	
	/**
	 * 查看模板信息
	 * @Title: emailTemplateInfo
	 * @Description: TODO
	 * @param: @param model
	 * @param: @param id
	 * @param: @return
	 * @return: String
	 * @throws
	 */
	@RequestMapping("emailtemplate-info")
	public String emailTemplateInfo(Model model, @RequestParam(value = "id", required = false) Long id)
	{
		EmailTemplate emailTemplate = emailTemplateService.get(id);
		if(emailTemplate!=null)
		{
			model.addAttribute(emailTemplate);
		}
		return "infotemplate/emailtemplate-info";
	}
	
	/**
	 * 删除邮件模板信息
	 * @Title: userDelete
	 * @Description: TODO
	 * @param: @param id
	 * @param: @return
	 * @return: Result
	 * @throws
	 */
	@RequestMapping("emailtemplate-delete")
	@ResponseBody
	public Result emailtemplateDelete(@RequestParam("id") String id) {
		String[] ids = StringUtils.split(id, ",");
		try {
				Arrays.stream(ids).forEach(s->{
					emailTemplateService.delete(Long.parseLong(s));
				});
			return SpringMVCUtils.returnSuccess("删除模板信息成功");
		} catch (NumberFormatException e) {
			return SpringMVCUtils.returnError("删除模板信息失败");
		}
	}
	
	
}
