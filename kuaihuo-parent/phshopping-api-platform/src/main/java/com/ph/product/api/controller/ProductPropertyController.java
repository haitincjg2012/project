package com.ph.product.api.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.ph.base.BaseController;
import com.ph.shopping.common.util.page.PageBean;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.permission.vo.SessionUserVO;
import com.ph.shopping.facade.product.dto.ProductPropertyDTO;
import com.ph.shopping.facade.product.entity.ProductClassify;
import com.ph.shopping.facade.product.entity.ProductProperty;
import com.ph.shopping.facade.product.exception.ProductExceptionEnum;
import com.ph.shopping.facade.product.service.IProductClassifyService;
import com.ph.shopping.facade.product.service.IProductPropertyService;
import com.ph.shopping.facade.product.status.ProductStatusEnum;

/**
 * 
 * @项目：phshopping-api-platform
 *
 * @描述： 商品属性控制层
 *
 * @作者： 杨颜光
 *
 * @创建时间：2017年5月16日 下午2:08:14
 *
 * @Copyright by 杨颜光
 */
@Controller
@RequestMapping("/web/product/property")
public class ProductPropertyController extends BaseController {

	private static Logger logger = LoggerFactory.getLogger(ProductPropertyController.class);

	// 引用商品属性service
	@Reference(version = "1.0.0")
	private IProductPropertyService iProductPropertyService;

	// 引用商品分类service
	@Reference(version = "1.0.0")
	private IProductClassifyService iPhProductClassifyService;

	

	/**
	 * 商品属性列表页面跳转
	 * 
	 * @return String
	 * @author 杨颜光
	 */
	@RequestMapping(value = "/toProductPropertyPage")
	public String toProductPropertyPage(Model model, Long classifyId)  {
		// 查询分类级别为一级的数据
	    ProductClassify productClassify = new ProductClassify();
	    productClassify.setIsDelete(ProductStatusEnum.Not_DELETE.getCode());
	    productClassify.setStatus(ProductStatusEnum.ENABLED.getCode());
	    productClassify.setClassifyLevel(ProductStatusEnum.CLASSIFY_LEVEL_1.getCode());
		List<ProductClassify> list = this.iPhProductClassifyService.getProductClassifyList(productClassify);
		model.addAttribute("list", list);
		if (classifyId!=null) {
			model.addAttribute("classifyId", classifyId);
		}else{
			model.addAttribute("classifyId",null);
		}
		
		return "product/property/list";
	}

	/**
	 * 商品属性列表分页查询
	 * 
	 * @param phProductPropertyDto
	 * @return Result
	 * @author 杨颜光
	 */
	@RequestMapping(value = "/getPhProductPropertyPageList", method = RequestMethod.GET)
	public @ResponseBody Object getPhProductPropertyPageList(PageBean pageBean, ProductPropertyDTO phProductPropertyDto)
			 {
		phProductPropertyDto.setIsDelete(ProductStatusEnum.Not_DELETE.getCode());
		return iProductPropertyService.getProductPropertyByPage(pageBean, phProductPropertyDto);
	}

	
	/**
	 * 添加属性页面跳转方法
	 * 
	 * @param request
	 * @param model
	 * @return
	 * @
	 * @Copyright 杨颜光
	 */
	@RequestMapping(value = "/toAddProperty")
	public String toAddProductPropertyPage(HttpServletRequest request, Model model)  {
		// 查询分类级别为一级的数据
	    ProductClassify productClassify = new ProductClassify();
	    productClassify.setIsDelete(ProductStatusEnum.Not_DELETE.getCode());
	    productClassify.setStatus(ProductStatusEnum.ENABLED.getCode());
	    productClassify.setClassifyLevel(ProductStatusEnum.CLASSIFY_LEVEL_1.getCode());
		List<ProductClassify> list = this.iPhProductClassifyService.getProductClassifyList(productClassify);
		model.addAttribute("list", list);
		return "product/property/add";
	}

	/***
	 * 属性添加
	 * 
	 * @param productStr
	 * @param imageStr
	 * @param prpoValStr
	 * @return
	 * @ 
	 *
	 */
	@RequestMapping(value = "/add")
	@ResponseBody
	public Result addProductProperty(@ModelAttribute ProductProperty productProperty, HttpServletRequest request)  {
		Result result=new Result();
		SessionUserVO info = getLoginUser();
		logger.info("属性添加开始，传入参数" + productProperty);
		productProperty.setCreateTime(new Date());
//		productProperty.setCreaterId(1L);
		productProperty.setCreaterId(info.getId());
		// 后台数据验证开始
		List<String> errorList = productProperty.validateForm();
		if (errorList != null) {
			logger.info("信息检索表[com.ph.shopping.facade.product.entity.ProductProperty]实体中出错验证错误：错误信息如下"
					+ JSON.toJSONString(errorList));
			result.setCode(ProductExceptionEnum.ENTITY_CHECK_EXCEPTION.getCode());
			result.setMessage(ProductExceptionEnum.ENTITY_CHECK_EXCEPTION.getMsg());
			return result;
		}
		// 添加开始
		result=this.iProductPropertyService.addProductPeoperty(productProperty);
		return result;
	}

	/**
	 * 属性修改跳转
	 * 
	 * @param id
	 * @param model
	 * @author 杨颜光
	 * @
	 *@return String
	 */
	@RequestMapping(value = "/toUpdate")
	public String updateProductProperty(@RequestParam("id") Long id, Model model)  {
		ProductProperty	propertyQuery=new ProductProperty(); 
		propertyQuery.setId(id);
		ProductProperty	property = this.iProductPropertyService.getProductProperty(propertyQuery);

		// 通过对象属性：classifyId获取级别和当前分类的数据
		ProductClassify classlevel = iPhProductClassifyService.getProductClassifyById(property.getClassifyId());

		// 查询分类级别为一级的数据
		ProductClassify productClassifylevel1= new ProductClassify();
		productClassifylevel1.setClassifyLevel(ProductStatusEnum.CLASSIFY_LEVEL_1.getCode());
		productClassifylevel1.setIsDelete(ProductStatusEnum.Not_DELETE.getCode());
		List<ProductClassify> list = this.iPhProductClassifyService.getProductClassifyList(productClassifylevel1);
		model.addAttribute("list", list);
		if (classlevel.getClassifyLevel() == ProductStatusEnum.CLASSIFY_LEVEL_1.getCode()) {
			model.addAttribute("second", "");
			model.addAttribute("three", "");

			model.addAttribute("theSelectID1", property.getClassifyId());
			model.addAttribute("theSelectID2", "");
			model.addAttribute("theSelectID3", "");
		}
		if (classlevel.getClassifyLevel() == ProductStatusEnum.CLASSIFY_LEVEL_2.getCode()) {
			ProductClassify dtoSecond = new ProductClassify();
			dtoSecond.setClassifyLevel( ProductStatusEnum.CLASSIFY_LEVEL_2.getCode());
			dtoSecond.setParentId(classlevel.getParentId());
			dtoSecond.setIsDelete(ProductStatusEnum.Not_DELETE.getCode());
			List<ProductClassify> list2 = this.iPhProductClassifyService.getProductClassifyList(dtoSecond);
			model.addAttribute("second", list2);
			model.addAttribute("three", "");
			model.addAttribute("theSelectID1", classlevel.getParentId());
			model.addAttribute("theSelectID2", property.getClassifyId());
			model.addAttribute("theSelectID3", "");
		}
		if (classlevel.getClassifyLevel() == ProductStatusEnum.CLASSIFY_LEVEL_3.getCode()) {
			// 查询分类级别为三级的数据
			ProductClassify dtoThree = new ProductClassify();
			dtoThree.setClassifyLevel(ProductStatusEnum.CLASSIFY_LEVEL_3.getCode());
			dtoThree.setParentId(classlevel.getParentId());
			dtoThree.setIsDelete(ProductStatusEnum.Not_DELETE.getCode());
			List<ProductClassify> list3 = this.iPhProductClassifyService.getProductClassifyList(dtoThree);
			// 获取三级分类的上级数据
			ProductClassify dtoEntity = new ProductClassify();
			dtoEntity.setId(classlevel.getParentId());
			dtoEntity.setClassifyLevel(ProductStatusEnum.CLASSIFY_LEVEL_2.getCode());
			dtoEntity.setIsDelete(ProductStatusEnum.Not_DELETE.getCode());
			ProductClassify  voParent = this.iPhProductClassifyService.getOneProductClassify(dtoEntity);

			ProductClassify dtoParentBrother = new ProductClassify();
			dtoParentBrother.setParentId(voParent.getParentId());
			dtoParentBrother.setClassifyLevel(ProductStatusEnum.CLASSIFY_LEVEL_2.getCode());
			dtoParentBrother.setIsDelete(ProductStatusEnum.Not_DELETE.getCode());

			List<ProductClassify> list4 = this.iPhProductClassifyService.getProductClassifyList(dtoParentBrother);
			model.addAttribute("second", list4);
			model.addAttribute("three", list3);

			model.addAttribute("theSelectID1", voParent.getParentId());
			model.addAttribute("theSelectID2", classlevel.getParentId());
			model.addAttribute("theSelectID3", property.getClassifyId());
		}
		model.addAttribute("property", property);
		return "product/property/update";
	}

	/**
	 * 属性修改
	 * 
	 * @param productProperty
	 * @return Result
	 * @ 
	 *	
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public Result updateProductProperty(@ModelAttribute ProductProperty productProperty)  {
		Result result=new Result();
		SessionUserVO info = getLoginUser();
		productProperty.setUpdaterId(info.getId());
		productProperty.setUpdateTime(new Date());
		logger.info("属性添加开始，传入参数" + productProperty);
		// 后台数据验证开始
		List<String> errorList = productProperty.validateForm();
		if (errorList != null) {
			logger.info("信息检索表[com.ph.shopping.facade.product.entity.ProductProperty]实体中出错验证错误：错误信息如下"
					+ JSON.toJSONString(errorList));
			result.setCode(ProductExceptionEnum.ENTITY_CHECK_EXCEPTION.getCode());
			result.setMessage(ProductExceptionEnum.ENTITY_CHECK_EXCEPTION.getMsg());
			return result;
		}
		// 修改开始
		result=	this.iProductPropertyService.updateProductPeoperty(productProperty);
		return result;
	}

	/**
	 * 逻辑删除商品属性
	 * 
	 * @param id
	 * @return 
	 * @author 杨颜光
	 */
	@RequestMapping(value = "/deletePhProductProperty", method = RequestMethod.GET)
	public @ResponseBody Result deletePhProductProperty(Long id)  {
		ProductProperty productProperty=new ProductProperty();
		productProperty.setId(id);
		productProperty.setIsDelete(ProductStatusEnum.DELETED.getCode());
		Result result= iProductPropertyService.deleteProductProperty(productProperty);
		return result;
	}

/**
 * 属性列表排序保存方法
 * 
 * @param request
 * @param model
 * @return
 * @
 * @Copyright 杨颜光
 */
	@RequestMapping(value = "/toSortSave",method = RequestMethod.POST)
	public @ResponseBody Result toSortSave(String sotrJson)  {
		System.err.println(sotrJson);
		SessionUserVO info = getLoginUser();
		List<ProductProperty> propertyList = JSON.parseArray(sotrJson, ProductProperty.class);
		for (ProductProperty productProperty : propertyList) {
			productProperty.setUpdaterId(info.getId());
//			productProperty.setUpdaterId(1L);
			productProperty.setUpdateTime(new Date());
		}
		Result result=this.iProductPropertyService.updateProductPeopertyForSort(propertyList);
		return result;
	}
}
