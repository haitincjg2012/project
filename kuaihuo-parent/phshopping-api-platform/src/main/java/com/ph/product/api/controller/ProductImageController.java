package com.ph.product.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.product.entity.ProductImage;
import com.ph.shopping.facade.product.service.IProductImageService;

/**
 * 
 * @项目：phshopping-facade-
 *
 * @描述： 商品图片查询
 *
 * @作者： 杨颜光
 *
 * @创建时间：2017年5月16日 下午2:01:19
 *
 * @Copyright by 杨颜光
 */
@Controller
@RequestMapping("/web/phProduct/image")
public class ProductImageController {
	
	@Reference(version = "1.0.0")
	private IProductImageService iProductImageService;

	
	/***
	 * 查询商品图片列表
	 * 
	 * @param productImage
	 * @return Result
	 * @author 杨颜光
	 */
	@RequestMapping(value = "/getPhProductImageList", method = RequestMethod.GET)
	public @ResponseBody Result getPhProductImageList(ProductImage productImage)  {
		Result result = new Result();
		result.setData(iProductImageService.getProductImageList(productImage));
		return result;
	}

	/***
	 * 单条查询商品图片
	 * 
	 * @param productImage
	 * @return Result
	 * @author 杨颜光
	 */
	@RequestMapping(value = "/getPhProductImage", method = RequestMethod.GET)
	public @ResponseBody Result getPhProductImage(ProductImage productImage)  {
		Result result = new Result();
		result.setData(iProductImageService.getProductImage(productImage));
		return result;
	}

}
