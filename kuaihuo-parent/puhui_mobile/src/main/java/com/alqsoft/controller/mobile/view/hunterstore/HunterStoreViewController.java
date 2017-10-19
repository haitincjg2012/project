package com.alqsoft.controller.mobile.view.hunterstore;

import org.alqframework.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alqsoft.service.hunterstore.HunterStoreService;

/**
 * 批发商个人中心-批发商店铺管理-点击轮播图进入详细信息
 * @author Administrator
 *
 */
@RequestMapping("mobile/view/hunterstore")
@Controller
public class HunterStoreViewController {

	@Autowired
	private HunterStoreService hunterStoreService;
	/**
	 * 批发商店铺列表-点击轮播图进入详细信息
	 * @param id 店铺的id
	 * @return
	 */
	@RequestMapping(value="hunterstore-list",method=RequestMethod.POST)
	@ResponseBody
	public Result HunterStoreList(Long hunterid){
		
	return hunterStoreService.findHunterStoreList(hunterid);

	}
	
}
