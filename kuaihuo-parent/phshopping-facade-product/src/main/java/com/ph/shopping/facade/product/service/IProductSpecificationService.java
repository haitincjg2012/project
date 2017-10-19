package com.ph.shopping.facade.product.service;

import java.util.List;

import com.ph.shopping.facade.product.dto.ProductDTO;
import com.ph.shopping.facade.product.entity.ProductSpecification;
import com.ph.shopping.facade.product.vo.ProductSpecificationVO;

/**
 * 
 * @项目：phshopping-facade-
 *
 * @描述： 商品规格类型 接口
 *
 * @作者： 杨颜光
 *
 * @创建时间：2017年5月15日 下午6:18:47
 *
 * @Copyright by 杨颜光
 */
public interface IProductSpecificationService {
	
	/**
	 * 获取单个规格类型
	 * 
	 * @param specification
	 * @return List<ProductSpecification>
	 * @author 杨颜光
	 */
	List<ProductSpecification> getProductSpecificationList(ProductSpecification specification);

	/**
	 * 获取单个规格类型
	 * 
	 * @param specification
	 * @return ProductSku
	 * @author 杨颜光
	 */
	ProductSpecification getProductSpecification(ProductSpecification specification);

	/**
	 * 根据商品id获取规格名称以及旗下规格的值
	 *	只会匹配id 设置其他条件无效
	 * @param productDTO
	 * @return List<ProductSpecificationVO>
	 * 
	 * @author: 李超
	 * @date: 2017-05-17 14:33:25
	 */
	List<ProductSpecificationVO> getProductSpecificationVOListByProductId(ProductDTO productDTO);

}