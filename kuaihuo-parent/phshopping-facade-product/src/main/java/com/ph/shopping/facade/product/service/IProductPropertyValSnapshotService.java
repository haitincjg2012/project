package com.ph.shopping.facade.product.service;

import java.util.List;

import com.ph.shopping.facade.product.entity.ProductPropertyValSnapshot;

/**
 * 
 * @项目：phshopping-facade-
 *
 * @描述：商品属性值快照接口
 *
 * @作者： 杨颜光
 *
 * @创建时间：2017年5月15日 下午8:18:48
 *
 * @Copyright by 杨颜光
 */
public interface IProductPropertyValSnapshotService {
	
	/**
	 * 获取多个属性值快照快照
	 * 
	 * @param propertyValSnapshot
	 * @return List<ProductPropertyValSnapshot>
	 * 
	 * @author 杨颜光
	 *
	 */
	List<ProductPropertyValSnapshot> getProductImageSnapshotList(ProductPropertyValSnapshot propertyValSnapshot);
	
	/**
	 * 获取单个属性值快照快照
	 * 
	 * @param propertyValSnapshot
	 * @return ProductPropertyValSnapshot
	 * 
	 * @author 杨颜光
	 *
	 */
	ProductPropertyValSnapshot getProductI1mageSnapshot(ProductPropertyValSnapshot propertyValSnapshot);

}