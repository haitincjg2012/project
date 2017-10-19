package com.alqsoft.controller.sms;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.alqframework.easyui.EasyuiResult;
import org.alqframework.webmvc.servlet.ServletUtils;
import org.alqframework.webmvc.springmvc.Result;
import org.alqframework.webmvc.springmvc.SpringMVCUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alqsoft.entity.sms.SmsContentLog;
import com.alqsoft.service.sms.SmsContentLogService;

/**
 * 
 * @Title: SmsContentLogAction.java
 * @Description: 单聊消息日志控制器
 * @author 俞旺
 * @e-mail 13606024548@139.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2015年5月14日 上午9:42:40
 * Copyright © 2013 厦门卓讯信息技术有限公司 All rights reserved.
 * 
 */
@RequestMapping("smscontentlog")
@Controller
public class SmsContentLogController {
	@Autowired
	private SmsContentLogService smsContentLogService;
	
	/**
	 * 跳转到单聊消息日志视图
	 * @Title: smsContentLogList
	 * @Description: TODO
	 * @param: @param model
	 * @param: @return
	 * @return: String
	 * @throws
	 */
	@RequestMapping("smscontentlog-list")
	public String smsContentLogList(Model model){
		
		return "sms/smscontentlog-list";
	}
	
	/**
	 * 获取单聊消息日志分页信息
	 * @Title: getSmsContentLogPage
	 * @Description: TODO
	 * @param: @param page
	 * @param: @param rows
	 * @param: @param request
	 * @param: @return
	 * @return: EasyuiResult<List<FeedBack>>
	 * @throws
	 */
	@RequestMapping("smscontentlog-list-data")
	@ResponseBody
	public EasyuiResult<List<SmsContentLog>> getSmsContentLogPage(@RequestParam("page") Integer page,
			@RequestParam("rows") Integer rows,HttpServletRequest request){
		Map<String, Object> map = ServletUtils.getParametersStartingWith(
				request, "search_");
		return smsContentLogService.getSmsContentLogPage(map, page, rows);
	}
	/**
	 * 单聊消息日志删除
	 * @Title: smsContentLogDelete
	 * @Description: TODO
	 * @param: @param id
	 * @param: @return
	 * @return: Result
	 * @throws
	 */
	@RequestMapping("smscontentlog-delete")
	@ResponseBody
	public Result smsContentLogDelete(@RequestParam("id") String id) {
		String[] ids = StringUtils.split(id, ",");
		try {
				Arrays.stream(ids).forEach(s->{
					smsContentLogService.delete(Long.parseLong(s));	
				});
			return SpringMVCUtils.returnSuccess("删除单聊消息日志成功");
		
		} catch (NumberFormatException e) {
			return SpringMVCUtils.returnError("删除单聊消息日志失败");
		}
		
	}
	/**
	 * 单聊消息日志查看
	 * @Title: smsContentLogInfo
	 * @Description: TODO
	 * @param: @param id
	 * @param: @param model
	 * @param: @return
	 * @return: String
	 * @throws
	 */
	@RequestMapping("smscontentlog-info")
	public String smsContentLogInfo(@RequestParam(value="id",required=false) Long id,Model model){
		SmsContentLog smsContentLog=null;
		if(id!=null){
			smsContentLog=smsContentLogService.get(id);
			if(smsContentLog.getId()!=null){
				model.addAttribute("smsContentLog", smsContentLog);
			}
		}
		return "sms/smscontentlog-info";
	}
}
