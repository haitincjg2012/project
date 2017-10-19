package com.phshopping.api.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ph.shopping.common.core.customenum.CacheKeyTypeEnum;
import com.ph.shopping.common.core.other.annotation.Token;
import com.ph.shopping.common.util.core.RespCode;
import com.ph.shopping.common.util.core.ResultUtil;
import com.ph.shopping.common.util.result.Result;
import com.phshopping.api.aop.annotation.AccessToken;

import cm.ph.shopping.facade.order.dto.DishDTO;
import cm.ph.shopping.facade.order.dto.DishTypeDTO;
import cm.ph.shopping.facade.order.service.IDishService;
import cm.ph.shopping.facade.order.vo.DishTypeVO;
import cm.ph.shopping.facade.order.vo.DishVO;

/**
 * 商品app进入接口
 * @author 赵俊彪
 *
 */
@Controller
@RequestMapping("api/dishs")
public class DishController extends  BaseMerchantController{
	private static final Logger logger =LoggerFactory.getLogger(DishController.class);
	
	@Reference(version = "1.0.0")
    private IDishService iDishService;
	/**
	 * 商品管理界面的出售中和下架列表 
	 * @param merchantid
	 * @param isDelete 0:出售中 1：下架
	 * @param type 1：添加时间降序，2添加时间升序，3销量降序，4销量升序，5库存降序，6库存降序
	 * @return
	 */
	@RequestMapping("getDishsSaleOrCancelList")
	@ResponseBody
	public Result getDishsSaleOrCancelList(
			@RequestParam("merchantid") Long merchantid,
			@RequestParam(value="isDelete",defaultValue="0") Integer isDelete,
			@RequestParam(value="type",defaultValue="0") Integer type,
			@RequestParam(value="pageNum") Integer pageNum,
			@RequestParam(value="pageSize") Integer pageSize,
			@RequestParam(value="sort",defaultValue="2") Integer sort
			){
		return iDishService.getDishsSaleOrCancelList(merchantid, isDelete,pageNum,pageSize,type,sort);
	}
	
	/**
	 * 商品批量管理（上下架）
	 * @param merchantid
	 * @param dishId
	 * @param isDelete 0:出售中 1：下架
	 * @return
	 */
	@AccessToken
	@RequestMapping("dishBatchSaleOrCancel")
	@ResponseBody
	public Result dishBatchSaleOrCancel(
			@RequestParam("merchantid") Long merchantid,
			@RequestParam("dishIds") String[] dishIds,
			@RequestParam("isDelete") Integer isDelete
			){
		iDishService.dishBatchSaleOrCancel(merchantid,dishIds, isDelete);
		return ResultUtil.getResult(RespCode.Code.SUCCESS);
	}
	/**
	 * 添加商品
	 * @param merchantid
	 * @param dishDTO
	 * @return
	 */
	@AccessToken
	@RequestMapping("addDish")
	@ResponseBody
	public Result addDish(DishDTO dishDTO){
		return iDishService.addDish(dishDTO);
		
	}
	/**
	 * 查询商品分类
	 * @param merchantid
	 * @return
	 */
	@RequestMapping("getDishtypeList")
	@ResponseBody
	public Result getDishtypeList( Long merchantId,int type){
		try {
			List<DishTypeVO> getDishtypeList = iDishService.getDishtypeList(merchantId,type);
			logger.info("分类列表展示{}",getDishtypeList);
			if (getDishtypeList == null) {
				return ResultUtil.getResult(RespCode.Code.FAIL, getDishtypeList);
			}
			return ResultUtil.getResult(RespCode.Code.SUCCESS, getDishtypeList);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultUtil.getResult(RespCode.Code.INTERNAL_SERVER_ERROR);
		}
	}
	/**
	 * 根据分类查询商品
	 * @param merchantid
	 * @param dishTypeId
	 * @return
	 */
	@RequestMapping("getDishList")
	@ResponseBody
	public Result getDishList(@RequestParam("merchantid") Long merchantid,@RequestParam("dishTypeId") Long dishTypeId){
		try {
			List<DishVO> dishList = iDishService.getDishList(merchantid,dishTypeId);
			if(dishList==null){
				return ResultUtil.getResult(RespCode.Code.FAIL,dishList);
			}
			for (int i = 0; i < dishList.size(); i++) {
				dishList.get(i).setMoney(dishList.get(i).getMoney()/10000);
			}
			return ResultUtil.getResult(RespCode.Code.SUCCESS,dishList);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultUtil.getResult(RespCode.Code.INTERNAL_SERVER_ERROR);
		}
		
		
	}
	/**
	 * 添加商品分类
	 * @param dishTypeDTO
	 * @return
	 */
	@AccessToken
	@RequestMapping("/addDishType")
	@ResponseBody
	public Result addDishType(DishTypeDTO dishTypeDTO){
		return iDishService.addDishType(dishTypeDTO);
	}
	
	/**
	 * <pre>UpdateDish(作用：修改商品)   
	 * 创建人：赵俊彪   
	 */
	@AccessToken
	@RequestMapping("/UpdateDish")
	@ResponseBody
	public Result UpdateDish(DishDTO dishDTO){
		return iDishService.UpdateDish(dishDTO);
	}
	/**
	 * <pre>updateClassifyTo(作用：分类至)   
	 * 创建人：赵俊彪   
	 */
	@AccessToken
	@RequestMapping("updateClassifyTo")
	@ResponseBody
	public Result updateClassifyTo(String ids,Long dishTypeId){
		logger.info("要修改的商品是"+ids+"修改为"+ dishTypeId);
		return iDishService.updateClassifyTo(dishTypeId,ids);
	}
	
	@AccessToken
	@RequestMapping("updateTypeName")
	@ResponseBody
	public Result updateTypeName(Long id,String typeName){
		return iDishService.updateTypeName(id,typeName);
	}
	
	@RequestMapping("deleteTypeId")
	@ResponseBody
	public Result deleteTypeId(Long id,Long merchantid,int type){
		return iDishService.deleteTypeId(id,merchantid,type);
	}
	
	/**
	 * <pre>findDistOneId(作用：商品回显)   
	 * 创建人：赵俊彪   
	 * 创建时间：2017年9月1日 下午6:01:58    
	 * @param response
	 * @param id
	 * @return</pre>
	 */
	@RequestMapping("findDistOneId")
	@ResponseBody
	public Result findDistOneId(HttpServletResponse response,DishDTO dishdto){
		return iDishService.findDistOneId(dishdto);
	}
}
