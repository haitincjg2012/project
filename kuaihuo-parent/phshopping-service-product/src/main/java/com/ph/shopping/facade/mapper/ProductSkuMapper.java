package com.ph.shopping.facade.mapper;

import com.ph.shopping.common.core.base.BaseMapper;
import com.ph.shopping.facade.product.dto.ProductSkuDTO;
import com.ph.shopping.facade.product.entity.ProductSku;
import com.ph.shopping.facade.product.vo.ProductSkuVO;

/**
 * 
 * @项目：phshopping-service-product
 *
 * @描述： 商品skudao层
 *
 * @作者： 杨颜光
 *
 * @创建时间：2017年5月12日 下午3:53:42
 *
 * @Copyright by  杨颜光
 */
public interface ProductSkuMapper extends BaseMapper<ProductSku> {
	
	/**
	 * 通过商品id查询最低价零售价商品信息（商城详情查询使用）
	 * 
	 * @param skuDTO
	 * @return
	 *
	 */
	ProductSkuVO  selectSkuMinRetailPrice(ProductSkuDTO skuDTO);
}