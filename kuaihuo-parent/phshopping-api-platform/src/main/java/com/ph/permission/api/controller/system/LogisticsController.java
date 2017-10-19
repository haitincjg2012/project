package com.ph.permission.api.controller.system;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ph.base.BaseController;
import com.ph.shopping.common.util.page.PageBean;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.system.dto.LogisticsDTO;
import com.ph.shopping.facade.system.service.ILogisticsService;

/**
 * 物流公司controller
* @ClassName: LogisticsController
* @Description: TODO(这里用一句话描述这个类的作用)
* @author Mr.Dong
* @date 2017年6月15日 上午9:31:14
 */
@Controller
@RequestMapping(value = "web/logistics")
public class LogisticsController extends BaseController  {

	//物流公司service
	@Reference(version = "1.0.0")
	private  ILogisticsService  iLogisticsService;
	
	/**
	 * 跳转物流公司页面
	* @Title: toLogisticsPage
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @author Mr.Dong
	* @date  2017年6月15日 上午9:41:09
	* @return
	 */
	@RequestMapping(value = "toLogisticsPage", method = RequestMethod.GET)
	public String toLogisticsPage() {
		return "permission/system/logistics";
	}
	
	
	/**
	 * 物流公司list
	* @Title: getLogisticsList
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @author Mr.Dong
	* @date  2017年6月15日 上午10:08:18
	* @param pageBean
	* @return
	 */
	@RequestMapping(value = "/getLogisticsList", method = RequestMethod.GET)
	@ResponseBody
	public Result getLogisticsList(PageBean pageBean){
		return iLogisticsService.getLogisticsList(pageBean);
	}
	
	/**
	 * 删除物流公司
	* @Title: deleteLogistics
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @author Mr.Dong
	* @date  2017年6月15日 上午10:09:43
	* @param id
	* @return
	 */
	@RequestMapping(value = "/deleteLogistics", method = RequestMethod.POST)
	@ResponseBody
	public Result deleteLogistics(Long id){
		return iLogisticsService.deleteLogistics(id);
	}
	
	/**
	 * 添加物流公司
	* @Title: addLogistics
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @author Mr.Dong
	* @date  2017年6月15日 上午11:12:45
	* @param dto
	* @return
	 */
	@RequestMapping(value = "/addLogistics", method = RequestMethod.POST)
	@ResponseBody
	public Result addLogistics(LogisticsDTO dto){
		return iLogisticsService.addLogistics(dto);
	}
	
	/**
	 * 修改物流公司
	* @Title: updateLogistics
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @author Mr.Dong
	* @date  2017年6月15日 上午11:40:55
	* @param dto
	* @return
	 */
	@RequestMapping(value = "/updateLogistics", method = RequestMethod.POST)
	@ResponseBody
	public Result updateLogistics(LogisticsDTO dto){
		return iLogisticsService.updateLogistics(dto);
	}
	
	
	/**
	 * 通过id获取物流公司
	* @Title: getLogisticById
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @author Mr.Dong
	* @date  2017年6月15日 下午12:22:37
	* @param id
	* @return
	 */
	@RequestMapping(value = "/getLogisticById", method = RequestMethod.GET)
	@ResponseBody
	public Result getLogisticById(Long id){
		return iLogisticsService.getLogisticById(id);
	}
}
