package com.ph.shopping.facade.product.service;

import java.util.List;

import com.ph.shopping.facade.product.entity.ProductImage;

/**
 * 
 * @项目：phshopping-facade-
 *
 * @描述：商品图片接口
 *
 * @作者： 杨颜光
 *
 * @创建时间：2017年5月15日 下午4:30:23
 *
 * @Copyright by 杨颜光
 */
public interface IProductImageService {
	
	/**
	 * 查询多个商品图片
	 * 
	 * @param productImage
	 * @return 	List<ProductImage>
	 * @author 杨颜光
	 *
	 */
	List<ProductImage> getProductImageList(ProductImage productImage) ;

	/**
	 *  单条查询商品图片
	 * 
	 * @param productImage
	 * @return ProductImage
	 *@author 杨颜光
	 */
	ProductImage getProductImage(ProductImage productImage) ;
}
