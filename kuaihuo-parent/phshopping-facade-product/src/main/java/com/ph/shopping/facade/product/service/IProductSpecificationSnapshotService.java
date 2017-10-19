package com.ph.shopping.facade.product.service;

import java.util.List;

import com.ph.shopping.facade.product.entity.ProductSpecificationSnapshot;


/**
 * 
 * @项目：phshopping-facade-
 *
 * @描述： 商品规格类型快照 接口
 *
 * @作者： 杨颜光
 *
 * @创建时间：2017年5月15日 下午6:18:47
 *
 * @Copyright by 杨颜光
 */
public interface IProductSpecificationSnapshotService {
	
	/**
	 * 获取多个规格类型快照
	 * 
	 * @param productSku
	 * @return List<ProductSpecificationSnapshot>
	 * @author 杨颜光
	 */
	List<ProductSpecificationSnapshot> getProductSpecificationSnapshotList(
			ProductSpecificationSnapshot specificationSnapshot);

	/**
	 * 获取单个规格类型快照
	 * 
	 * @param productSku
	 * @return ProductSku
	 * @author 杨颜光
	 */
	ProductSpecificationSnapshot getProductSpecificationSnapshot(ProductSpecificationSnapshot specificationSnapshot);

}