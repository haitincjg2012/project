package com.ph.shopping.facade.product.service;

import java.util.List;

import com.ph.shopping.facade.product.dto.ProductSnapshotDTO;
import com.ph.shopping.facade.product.entity.ProductSnapshot;
import com.ph.shopping.facade.product.vo.ProductSnapshotVO;

/**
 * 
 * @项目：phshopping-facade-
 *
 * @描述：商品主表快照接口
 *
 * @作者： 杨颜光
 *
 * @创建时间：2017年6月1日 下午5:53:56
 *
 * @Copyright by 杨颜光
 */
public interface IProductSnapshotService {

	/**
	 * 获取多条商品快照
	 * 
	 * @param productSnapshot
	 * @return
	 *
	 */
	List<ProductSnapshot> getProductSnapshotList(ProductSnapshot productSnapshot);

	/**
	 * 获取单条商品快照
	 * 
	 * @param productSnapshot
	 * @return
	 *
	 */
	ProductSnapshot getProducSnapshot(ProductSnapshot productSnapshot);
	
	/**
	 * 通过商品id获取商品最新快照（按时间排序）
	 * 
	 * @param dtos
	 * @return ProductSnapshotVO
	 *
	 */
	ProductSnapshotVO selectProductSnapshotByproductId(ProductSnapshotDTO dtos);

}