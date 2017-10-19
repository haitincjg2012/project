package com.ph.shopping.facade.product.service;

import java.util.List;

import com.ph.shopping.facade.product.dto.ProductSkuDTO;
import com.ph.shopping.facade.product.entity.ProductSku;
import com.ph.shopping.facade.product.vo.ProductSkuVO;

/**
 * 
 * @项目：phshopping-facade-
 *
 * @描述： 商品sku 接口
 *
 * @作者： 杨颜光
 *
 * @创建时间：2017年5月15日 下午6:18:47
 *
 * @Copyright by 杨颜光
 */
public interface IProductSkuService {

	/**
	 * 获取多个sku
	 * 
	 * @param productSku
	 * @return List<ProductSku>
	 * @author 杨颜光
	 */
	List<ProductSku> getProductSkuList(ProductSku productSku);

	/**
	 * 获取单个sku
	 * 
	 * @param productSku
	 * @return ProductSku
	 * @author 杨颜光
	 */
   	ProductSku getProductSku(ProductSku productSku);
   	
   	/**
   	 * 通过商品id查询最低价零售价商品信息（商城详情查询使用）
   	 * 
   	 * @param skuDTO
   	 * @return ProductSkuVO
   	 * @author 杨颜光
   	 */
	ProductSkuVO selectSkuMinRetailPrice(ProductSkuDTO skuDTO);

}