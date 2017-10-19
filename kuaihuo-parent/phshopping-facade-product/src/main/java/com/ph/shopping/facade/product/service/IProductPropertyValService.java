package com.ph.shopping.facade.product.service;

import java.util.List;

import com.ph.shopping.facade.product.entity.ProductPropertyVal;

/**
 * 
 * @项目：phshopping-facade-
 *
 * @描述：商品属性值接口
 *
 * @作者：杨颜光
 *
 * @创建时间：2017年5月15日 上午11:34:15
 *
 * @Copyright by 杨颜光
 */
public interface IProductPropertyValService {
	/**
	 * 商品属性值查询多条
	 * 
	 * @author 杨颜光
	 * @param productPropertyVal
	 * @return List<ProductPropertyVal>
	 */
	List<ProductPropertyVal> getProductPropertyValList(ProductPropertyVal productPropertyVal)
			;

	/**
	 * 单条商品属性值查询列表
	 * 
	 * @author 杨颜光
	 * @param phProductPropertyValDto
	 * @return PhProductPropertyValVo
	 */
	ProductPropertyVal getProductPropertyVal(ProductPropertyVal productPropertyVal) ;
}
