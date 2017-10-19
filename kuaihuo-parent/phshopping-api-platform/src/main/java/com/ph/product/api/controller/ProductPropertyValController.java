package com.ph.product.api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.product.entity.ProductPropertyVal;
import com.ph.shopping.facade.product.service.IProductPropertyValService;

/**
 * 
 * @项目：phshopping-facade-
 *
 * @描述：商品属性值查询
 *
 * @作者： 杨颜光
 *
 * @创建时间：2017年5月15日 下午3:43:50
 *
 * @Copyright by 杨颜光
 */
@RestController
@RequestMapping("/web/product/property/val")
public class ProductPropertyValController {
	// 引用商品属性值service
	@Reference(version = "1.0.0")
	private IProductPropertyValService iProductPropertyValService;

	/**
	 * 商品属性值查询列表
	 * 
	 * @author  杨颜光
	 * @param phProductPropertyValDto
	 * @return Result
	 */
	@RequestMapping(value = "/getPhProductPropertyValList", method = RequestMethod.GET)
	public @ResponseBody Result getPhProductPropertyValList(ProductPropertyVal  productPropertyVal)
			 {
		Result result = new Result();
		result.setData(iProductPropertyValService.getProductPropertyValList(productPropertyVal));
		return result;
	}

	/**
	 * 单条商品属性值查询列表
	 * 
	 * @author  杨颜光
	 * @param phProductPropertyValDto：id
	 * @return Result
	 */
	@RequestMapping(value = "/getPhProductPropertyVal", method = RequestMethod.GET)
	public @ResponseBody Result getPhProductPropertyVal(ProductPropertyVal productPropertyVal)
			 {
		Result result = new Result();
		result.setData(iProductPropertyValService.getProductPropertyVal(productPropertyVal));
		return result;
	}
}
