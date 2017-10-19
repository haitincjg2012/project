package com.ph.permission.api.controller.system;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ph.base.BaseController;
import com.ph.shopping.common.util.page.PageBean;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.system.dto.CommunityAddDTO;
import com.ph.shopping.facade.system.dto.CommunityQueryDTO;
import com.ph.shopping.facade.system.dto.CommunityUpdateDTO;
import com.ph.shopping.facade.system.service.ICommunityService;

/**
 * 社区控制层
* @ClassName: CommunityController
* @Description: TODO(系统管理下社区列表)
* @author Mr.Dong
* @date 2017年6月15日 下午3:28:59
 */
@Controller
@RequestMapping(value = "web/community")
public class CommunityController  extends BaseController {

	//社区service
	@Reference(version = "1.0.0")
	private  ICommunityService  iCommunityService;
	
	/**
	 * 跳转社区列表页面
	* @Title: toCommunityPage
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @author Mr.Dong
	* @date  2017年6月15日 下午3:34:41
	* @return
	 */
	@RequestMapping(value = "toCommunityPage", method = RequestMethod.GET)
	public String toCommunityPage() {
		return "permission/system/community";
	}
	
	/**
	 * 获取社区列表
	* @Title: getCommunityList
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @author Mr.Dong
	* @date  2017年6月15日 下午3:35:49
	* @param dto
	* @param pageBean
	* @return
	 */
	@RequestMapping(value = "/getCommunityList", method = RequestMethod.GET)
	@ResponseBody
	public Result getCommunityList(CommunityQueryDTO dto,PageBean pageBean){
		return iCommunityService.getCommunityListByPage(dto, pageBean);
	}
	
	/**
	 * 添加社区
	* @Title: addCommunity
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @author Mr.Dong
	* @date  2017年6月15日 下午6:02:16
	* @param dto
	* @return
	 */
	@RequestMapping(value = "/addCommunity", method = RequestMethod.POST)
	@ResponseBody
	public Result addCommunity(CommunityAddDTO dto){
		return iCommunityService.addCommunity(dto);
	}
	
	/**
	 * 修改社区 回填
	* @Title: getPositionById
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @author Mr.Dong
	* @date  2017年6月16日 上午11:23:49
	* @param id
	* @return
	 */
	@RequestMapping(value = "/getPositionById", method = RequestMethod.GET)
	@ResponseBody
	public Result getPositionById(Long id){
		return iCommunityService.getPositionById(id);
	}
	
	/**
	 * 修改社区
	* @Title: updateCommunity
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @author Mr.Dong
	* @date  2017年6月16日 上午11:28:12
	* @param dto
	* @return
	 */
	@RequestMapping(value = "/updateCommunity", method = RequestMethod.POST)
	@ResponseBody
	public Result updateCommunity(CommunityUpdateDTO dto){
		return iCommunityService.updateCommunity(dto);
	}
}
