package com.ph.shopping.facade.product.service;

import java.util.List;

import com.ph.shopping.facade.product.entity.ProductSpecificationVal;

/**
 * 
 * @项目：phshopping-facade-
 *
 * @描述： 商品规格值 接口
 *
 * @作者： 杨颜光
 *
 * @创建时间：2017年5月15日 下午6:18:47
 *
 * @Copyright by 杨颜光
 */
public interface IProductSpecificationValService {

	/**
	 * 获取单个规格值
	 * 
	 * @param productSku
	 * @return List<ProductSpecificationVal>
	 * @author 杨颜光
	 */
	List<ProductSpecificationVal> getProductSpecificationValtList(ProductSpecificationVal specificationVal);

	/**
	 * 获取单个规规格值
	 * 
	 * @param productSku
	 * @return ProductSku
	 * @author 杨颜光
	 */
	ProductSpecificationVal getProductSpecificationVal(ProductSpecificationVal specificationVal);

}