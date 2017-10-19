package com.ph.shopping.facade.mapper;

import java.util.List;

import com.ph.shopping.common.core.base.BaseMapper;
import com.ph.shopping.facade.product.dto.ProductDTO;
import com.ph.shopping.facade.product.dto.ProductPropertyDTO;
import com.ph.shopping.facade.product.entity.ProductProperty;
import com.ph.shopping.facade.product.vo.ProductPropertyVO;

/**
 * 
 * @项目：phshopping-facade-
 *
 * @描述： 商品属性dao层
 *
 * @作者： 杨颜光
 *
 * @创建时间：2017年5月15日 下午1:58:18
 *
 * @Copyright by 杨颜光
 */
public interface ProductPropertyMapper extends BaseMapper<ProductProperty> {
	

	/**
	 * 商品属性列表查询
	 * 
	 * @param productPropertyDto
	 * @return List<ProductPropertyVo>
	 * @author 杨颜光
	 */
	List<ProductPropertyVO> getPhProductPropertyVoList(ProductPropertyDTO phProductPropertyDto);

	/**
	 * 商品属性总条数查询
	 * 
	 * @param phProductPropertyDto
	 * @return Integer
	 * @author 杨颜光
	 */
	Integer getPhProductPropertyVoCount(ProductPropertyDTO phProductPropertyDto);
	
	/***
	 * 
	 * 通过(商品所属的三级分类id)查询对应的属性（后台和商城都可使用）
	 * @param phProductPropertyDto
	 * @return
	 * @author 杨颜光
	 */
	List<ProductPropertyVO>  getProductPropertyVoByThreeCassifyId(ProductDTO productDto);

	
}