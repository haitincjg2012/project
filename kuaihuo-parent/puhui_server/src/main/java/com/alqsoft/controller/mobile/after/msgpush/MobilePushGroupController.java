package com.alqsoft.controller.mobile.after.msgpush;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.alqframework.result.Result;
import org.alqframework.result.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alqsoft.entity.msgpush.PushGroup;
import com.alqsoft.service.msgpush.PushGroupService;
import com.alqsoft.utils.easyuiweb.EasyuiWebResult;

/**
 * 
 * @Title: MobilePushPushGroupController.java
 * @Description: 客户端推送消息管理
 * @author 俞旺
 * @e-mail 13606024548@139.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2015年5月20日 上午11:37:44
 * Copyright © 2013 厦门卓讯信息技术有限公司 All rights reserved.
 * 
 */
@RequestMapping("view/mobile/after/pushgroup")
@Controller
public class MobilePushGroupController {
	@Autowired
	private PushGroupService pushGroupService;
	/**
	 * 创建群操作
	 * @Title: addPushgroup
	 * @Description: TODO
	 * @param: @param pushGroupName
	 * @param: @param accountNum
	 * @param: @param description
	 * @param: @return
	 * @return: Result
	 * @throws
	 */
	@RequestMapping("addPushGroup")
	@ResponseBody
	public Result addPushgroups(@RequestParam("pushGroupName") String pushGroupName,@RequestParam("accountNum") Long accountNum,String description)
	{
		if(pushGroupService.getPushGroupByPushGroupName(pushGroupName)!=null)
		{
			return ResultUtils.returnError("该群名已被使用，请重新输入。", 0);
		}
		PushGroup pushGroup = new PushGroup();
		pushGroup.setAccountNum(accountNum);
		pushGroup.setPushGroupName(pushGroupName);
		pushGroup.setDescription(description);
		PushGroup pushGroup2 = pushGroupService.saveAndModify(pushGroup);
		if(pushGroup2!=null)
		{
			return ResultUtils.returnSuccess("群添加成功", pushGroup2);
		}
		else
		{
			return ResultUtils.returnError("群添加失败", 0);
		}
	}
	/**
	 * 返回群列表接口
	 * @Title: findPushGroup
	 * @Description: TODO
	 * @param: @param pageNo
	 * @param: @param pageSize
	 * @param: @return
	 * @return: Result
	 * @throws
	 */
	@RequestMapping("findPushGroup")
	@ResponseBody
	public Result findPushGroup(@RequestParam("pageNo") Integer pageNo,@RequestParam("pageSize") Integer pageSize)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		EasyuiWebResult<List<PushGroup>> list = pushGroupService.getPushGroupPageByMobile(map, pageNo, pageSize);
		if(list.getTotal()<=0)
		{
			return ResultUtils.returnError("列表不存在", -2);
		}
		return ResultUtils.returnSuccess("获取列表成功", list);
	}
}
