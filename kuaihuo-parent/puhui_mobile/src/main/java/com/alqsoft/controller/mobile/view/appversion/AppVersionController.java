package com.alqsoft.controller.mobile.view.appversion;

import java.util.Map;

import org.alqframework.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alqsoft.service.appversion.AppVersionService;



/**
 * @ClassName  AppVersionController
 * Date:     2017年2月28日  15:25:41 <br/>
 * @author   dinglanlan
 * @version  软件更新版本信息
 * @see 	 
 */
@RequestMapping("mobile/view/appversion")
@Controller
public class AppVersionController {
	
	@Autowired
	private AppVersionService appVersionService;
	
	/**
	 * 检测软件更新版本信息
	 * @return
	 */
	@RequestMapping(value="getappversion",method=RequestMethod.POST)
	@ResponseBody
	public Result getappversion(@RequestParam(value="client_type")String client_type,@RequestParam(value="version")String version){
		
		Result result =this.appVersionService.getAppVersion(client_type,version);
		
		return result;
	}
	
	/**
	 * 分享下载链接地址
	 * @return
	 */
	@RequestMapping(value="downapp")
	public String downloadApp(Model model){
		model.addAttribute("address", appVersionService.getDownLink());
		return "view/downapp";
	}
}
