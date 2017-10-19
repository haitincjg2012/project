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

import com.alqsoft.entity.infotemplate.SmsTemplate;
import com.alqsoft.service.infotemplate.SmsTemplateService;

/**
 * 
 * @Title: SmsTemplateController.java
 * @Description: 短信模板控制器
 * @author 俞旺
 * @e-mail 13606024548@139.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2015年4月20日 下午2:41:12
 * Copyright © 2013 厦门卓讯信息技术有限公司 All rights reserved.
 * 
 */
@RequestMapping("smstemplate")
@Controller
public class SmsTemplateController {
	@Autowired
	private SmsTemplateService smsTemplateService;
	
	/**
	 * 跳转到短信模板视图页面
	 * @Title: smsTemplateList
	 * @Description: TODO
	 * @param: @return
	 * @return: String
	 * @throws
	 */
	@RequestMapping("smstemplate-list")
	public String smsTemplateList()
	{
		return "infotemplate/smstemplate-list";
	}
	/**
	 * 分页查询
	 * @Title: smsTemplateListData
	 * @Description: TODO
	 * @param: @param page
	 * @param: @param rows
	 * @param: @param request
	 * @param: @return
	 * @return: EasyuiResult<List<SmsTemplate>>
	 * @throws
	 */
	@RequestMapping("smstemplate-list-data")
	@ResponseBody
	public EasyuiResult<List<SmsTemplate>> smsTemplateListData(@RequestParam("page") Integer page,
			@RequestParam("rows") Integer rows, HttpServletRequest request){
		// 解析查询
		Map<String, Object> map = ServletUtils.getParametersStartingWith(request, "search_");
		return smsTemplateService.getSmsTemplatePage(map, page, rows);
	}
	
	/**
	 * 新增或修改短信模板的视图
	 * @Title: smsTemplate
	 * @Description: TODO
	 * @param: @param model
	 * @param: @param id
	 * @param: @return
	 * @return: String
	 * @throws
	 */
	@RequestMapping("smstemplateinput")
	public String smsTemplateInput(Model model, @RequestParam(value = "id", required = false) Long id) {
		SmsTemplate smsTemplate = null;
		if (id != null) {
			smsTemplate = smsTemplateService.get(id);
			if (smsTemplate != null)
				model.addAttribute(smsTemplate);
		}
		return "infotemplate/smstemplate-input";
	}
	
	/**
	 * 新增和修改短信模板信息
	 * 
	 * @param user
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping("smstemplate-add")
	@ResponseBody
	public Result smsTemplateAdd(@Valid SmsTemplate smstemplate, BindingResult bindingResult) {
		// 验证判断是否有错
		if (!bindingResult.hasErrors()) {
				SmsTemplate smsTemplate2 = smsTemplateService.getSmsTemplateByEnglishName(smstemplate.getSmsEnglishName());
				if(smstemplate.getId()==null)
				{
					if(smsTemplate2!=null)
					{
						return SpringMVCUtils.returnError("您新增的短信模板类型名已存在，请重新输入");
					}
					else
					{
						if (smsTemplateService.saveAndModify(smstemplate)!=null) {
							return SpringMVCUtils.returnSuccess("短信模板保存成功");
						} else {
							return SpringMVCUtils.returnError("系统错误，请联系管理员");
						}

					}
				}
				else
				{
					smsTemplate2 = smsTemplateService.get(smstemplate.getId());
					MyBeanUtils.propertyUtils(smsTemplate2, smstemplate);
					if (smsTemplateService.saveAndModify(smsTemplate2)!=null) {
						return SpringMVCUtils.returnSuccess("短信模板修改成功");
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
	 * @Title: smsTemplateInfo
	 * @Description: TODO
	 * @param: @param model
	 * @param: @param id
	 * @param: @return
	 * @return: String
	 * @throws
	 */
	@RequestMapping("smstemplateinfo")
	public String smsTemplateInfo(Model model, @RequestParam(value = "id", required = false) Long id)
	{
		SmsTemplate smsTemplate = smsTemplateService.get(id);
		if(smsTemplate!=null)
		{
			model.addAttribute(smsTemplate);
		}
		return "infotemplate/smstemplate-info";
	}
	
	/**
	 * 删除短信模板信息
	 * @Title: userDelete
	 * @Description: TODO
	 * @param: @param id
	 * @param: @return
	 * @return: Result
	 * @throws
	 */
	@RequestMapping("smstemplate-delete")
	@ResponseBody
	public Result smsTemplateDelete(@RequestParam("id") String id) {
		String[] ids = StringUtils.split(id, ",");
		try {
			Arrays.stream(ids).forEach(s->{
				smsTemplateService.delete(Long.parseLong(s));
			});
			return SpringMVCUtils.returnSuccess("删除模板信息成功");
		} catch (NumberFormatException e) {
			return SpringMVCUtils.returnError("删除模板信息失败");
		}
	}
}
