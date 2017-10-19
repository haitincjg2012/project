package com.ph.shopping.facade.product.service;

import java.util.List;

import com.ph.shopping.facade.product.entity.ProductImageSnapshot;

/**
 * 
 * @项目：phshopping-facade-
 *
 * @描述：商品图片快照接口
 *
 * @作者： 杨颜光
 *
 * @创建时间：2017年5月15日 下午8:18:48
 *
 * @Copyright by 杨颜光
 */
public interface IProductImageSnapshotService {

	/**
	 * 获取多个商品图片快照
	 * 
	 * @param propertyValSnapshot
	 * @return List<ProductImageSnapshot>
	 * 
	 * @author 杨颜光
	 *
	 */
	List<ProductImageSnapshot> getProductImageSnapshotList(ProductImageSnapshot imageSnapshot);

	/**
	 * 获取单个商品图片快照
	 * 
	 * @param propertyValSnapshot
	 * @return ProductPropertyValSnapshot
	 * 
	 * @author 杨颜光
	 *
	 */
	ProductImageSnapshot getProductI1mageSnapshot(ProductImageSnapshot imageSnapshot);

}