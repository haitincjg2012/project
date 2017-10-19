package com.phshopping.api.controller;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.ph.shopping.common.core.customenum.CacheKeyTypeEnum;
import com.ph.shopping.common.core.other.annotation.Token;
import com.ph.shopping.common.util.core.RespCode;
import com.ph.shopping.common.util.core.ResultUtil;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.spm.entity.MerchantTimeSection;
import com.ph.shopping.facade.spm.service.IMerchantService;
import com.ph.shopping.facade.spm.vo.MerchanTimeSectionVO;
import com.phshopping.api.aop.annotation.AccessToken;

import cm.ph.shopping.facade.order.dto.AddRestaurantOrSeatDTO;
import cm.ph.shopping.facade.order.dto.DishDTO;
import cm.ph.shopping.facade.order.service.CompartmentService;

/**
 * 
 * <pre>
 * 项目名称：phshopping-app-api-merchant    
 * 类名称：CompartmentController    
 * 类描述：   餐位管理
 * 创建人：赵俊彪    
 * 创建时间：2017年8月22日 上午9:42:21    
 * 修改人：赵俊彪     
 * 修改时间：2017年8月22日 上午9:42:21    
 * 修改备注：       
 * &#64;version
 * </pre>
 */
@Controller
@RequestMapping("compartment")
@ResponseBody
public class CompartmentController extends BaseMerchantController {
	private static final Logger logger = LoggerFactory.getLogger(CompartmentController.class);

	@Reference(version = "1.0.0")
	private CompartmentService compartmentService;
	
	@Reference(version = "1.0.0")
	private IMerchantService iMerchantService;

	/**
	 * <pre>
	 * findRestaurantList(餐位展示)   
	 * 创建人：赵俊彪   
	 * &#64;param dish
	 * &#64;param result
	 * &#64;param response
	 * &#64;return
	 * </pre>
	 */
	@RequestMapping(value = "findRestaurantList")
	@ResponseBody
	public Result findRestaurantList(DishDTO dishDto, HttpServletResponse response) {
			//dish.setMerchantId(getUserId());
			return compartmentService.findRestaurantList(dishDto);
	}
	
	/**
	 * <pre>saveRestaurant(作用：添加餐位，并把传回来的图片保存到数据库当中)   
	 * 创建人：赵俊彪   
	 * 创建时间：2017年8月30日 下午9:04:08    
	 * @param addRestaurantOrSeatDTO
	 * @param response
	 * @return</pre>
	 */
	@AccessToken
	@RequestMapping(value = "addRestaurant")
	public Result saveRestaurant(AddRestaurantOrSeatDTO addRestaurantOrSeatDTO, HttpServletResponse response) {
		Result result = new Result();
		logger.info("开始添加餐位，传入参数{}",addRestaurantOrSeatDTO);
		result = compartmentService.saveRestaurant(addRestaurantOrSeatDTO);
		return result;
	}
	/**
	 * <pre>delsRestaurant(作用：删除餐位,需求：被预定的餐位不可删除)   
	 * 创建人：赵俊彪   
	 * 创建时间：2017年8月23日 下午3:49:10    
	 * @param ids
	 * @param response
	 * @return</pre>
	 */
	@RequestMapping(value="delsRestaurant")
	public Result delsRestaurant(String ids,HttpServletResponse response){
		logger.info("要删除的集合"+ids);
		return compartmentService.delsRestaurant(ids);
	}
	
	/**
	 * <pre>updateRestaurant(作用：修改餐位)   
	 * 创建人：赵俊彪   
	 * 创建时间：2017年8月27日 下午9:20:47    
	 * @param addRestaurantOrSeatDTO
	 * @param response
	 * @return</pre>
	 */
	@AccessToken
	@RequestMapping("updateRestaurant")
	public Result updateRestaurant(AddRestaurantOrSeatDTO addRestaurantOrSeatDTO,HttpServletResponse response){
		logger.info("修改餐位传入的参数={}",JSON.toJSONString(addRestaurantOrSeatDTO));
		return compartmentService.updateRestaurant(addRestaurantOrSeatDTO);
	}
	/**
	 * <pre>findId(作用：餐位回显)   
	 * 创建人：赵俊彪   
	 * 创建时间：2017年8月28日 下午9:26:08    
	 * @param id
	 * @return</pre>
	 */
	@RequestMapping("findId")
	public Result findId(Long id){
		Result findId = compartmentService.findId(id);
		return findId;
	}
	/**
	 * <pre>findDelId(作用：查询不可预定的餐位)   
	 * 创建人：赵俊彪   
	 * 创建时间：2017年8月31日 下午4:32:07    
	 * @return</pre>
	 */
	@RequestMapping("findDelId")
	public Result findDelId(String ids){
		return  compartmentService.findDelId(ids);
	}
	/**
	 * <pre>findType(作用：查询餐位分类以及分类下所有餐位)
	 * 创建人：赵俊彪
	 * 创建时间：2017年9月3日 下午12:11:09
	 * @return</pre>
	 */
	@RequestMapping("findType")
	public Result findType(Long merchantId){
		return compartmentService.findType(merchantId);

	}

	@RequestMapping("findTypeDishName")
	public Result findTypeDishName(Long merchantId,Long dishTypeId){
		return compartmentService.findTypeDishName(merchantId,dishTypeId);

	}

	@RequestMapping("findType2")
	public Result findType2(){
		return compartmentService.findType2();

	}
	/**
	 * <pre>addDissipate(作用：添加用户消费时段)   
	 * @param merchantTimeSection  
	 * @return</pre>
	 */
	@RequestMapping("addDissipate")
	public Result  addDissipate(MerchanTimeSectionVO merchantTimeSection ){
		return iMerchantService.addDissipate(merchantTimeSection);
	}
	/**
	 * <pre>findDissipate(作用：查询用户消费时间段)   
	 * @return</pre>
	 */
	@RequestMapping("findDissipate")
	public Result findDissipate(Long  merchantId){
		return iMerchantService.findDissipate(merchantId);
	}
	/**
	 * <pre>delDissipate(作用：删除用户消费时间段)   
	 * @param id   时间id
	 * @return</pre>
	 */
	@RequestMapping("delDissipate")
	public Result delDissipate(Long id ){
		logger.info("删除的消费时间段"+id);
		if(id==null){
			return ResultUtil.getResult(RespCode.Code.REQUEST_DATA_ERROR);
		}
		return iMerchantService.delDissipate(id);
	}
	
}
