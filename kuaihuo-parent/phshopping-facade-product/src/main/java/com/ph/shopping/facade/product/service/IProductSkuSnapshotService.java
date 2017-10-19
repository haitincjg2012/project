package com.ph.shopping.facade.product.service;

import java.util.List;

import com.ph.shopping.facade.product.entity.ProductSkuSnapshot;

/**
 * 
 * @项目：phshopping-facade-
 *
 * @描述：  商品sku 快照接口
 *
 * @作者： 杨颜光
 *
 * @创建时间：2017年5月15日 下午6:18:47
 *
 * @Copyright by 杨颜光
 */
public interface IProductSkuSnapshotService {
	
	/**
	 * 获取多个sku快照
	 * 
	 * @param productSku
	 * @return List<ProductSkuSnapshot> 
	 * @author 杨颜光
	 */
	List<ProductSkuSnapshot> getProductSkuSnapshotList(ProductSkuSnapshot skuSnapshot);

	/**
	 * 获取单个sku快照
	 * 
	 * @param productSku
	 * @return ProductSku
	 * @author 杨颜光
	 */
	ProductSkuSnapshot getProductSkuSnapshot(ProductSkuSnapshot skuSnapshot);

}