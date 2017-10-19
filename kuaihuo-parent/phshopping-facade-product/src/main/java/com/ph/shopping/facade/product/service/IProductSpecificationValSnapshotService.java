package com.ph.shopping.facade.product.service;

import java.util.List;

import com.ph.shopping.facade.product.entity.ProductSpecificationValSnapshot;

/**
 * 
 * @项目：phshopping-facade-
 *
 * @描述： 商品规格值快照 接口
 *
 * @作者： 杨颜光
 *
 * @创建时间：2017年5月15日 下午6:18:47
 *
 * @Copyright by 杨颜光
 */
public interface IProductSpecificationValSnapshotService {
	
	/**
	 * 获取多个规格值快照
	 * 
	 * @param productSku
	 * @return List<ProductSpecificationValSnapshot>
	 * @author 杨颜光
	 */
	List<ProductSpecificationValSnapshot> getProductSpecificationValtList(
			ProductSpecificationValSnapshot specificationValSnapshot);

	/**
	 * 获取单个规格值快照
	 * 
	 * @param productSku
	 * @returnProductSku
	 * @author 杨颜光
	 */
	ProductSpecificationValSnapshot getProductSpecificationVal(
			ProductSpecificationValSnapshot specificationValSnapshot);

}