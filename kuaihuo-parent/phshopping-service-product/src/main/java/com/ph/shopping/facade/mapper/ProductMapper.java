package com.ph.shopping.facade.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ph.shopping.common.core.base.BaseMapper;
import com.ph.shopping.facade.product.dto.ProductDTO;
import com.ph.shopping.facade.product.entity.Product;
import com.ph.shopping.facade.product.vo.ProductVO;

/**
 * 
 * @项目：phshopping-facade-
 *
 * @描述：商品接口
 *
 * @作者： 杨颜光
 *
 * @创建时间：2017年5月16日 上午11:09:33
 *
 * @Copyright by  杨颜光
 */
public interface ProductMapper extends BaseMapper<Product> {
	
	
	

	/**
	 * 商品和分类联合查询
	 * 
	 * @param productPropertyDto
	 * @return List<ProductPropertyVo>
	 * @author 杨颜光
	 */
	List<ProductVO> getPhProductVoPageList(ProductDTO productDto);

	/**
	 *  商品和分总条数查询
	 * 
	 * @param phProductPropertyDto
	 * @return Integer
	 *	 @author 杨颜光
	 */
	Integer getPhProductVoPageCount(ProductDTO productDto);
	

	/**
	 * 通过id查询商品信息
	 * 
	 * @param productIds
	 * @return
	 */
	List<ProductVO> getProductListForOder(@Param(value = "productIds") List<Long> productIds);

	/**
	 * 扣除商品数量
	 * 
	 * @param phProductDto
	 * @return
	 */
	Integer reduceStock(ProductDTO phProductDto);
	
	/***
	 * 商城列表展示方法
	 * 
	 * @param productDto
	 * @return List<ProductVO>
	 *
	 */
	List<ProductVO> getProductsForMall(ProductDTO productDto);
	
	/***
	 * 商城分类列表使用
	 * 
	 * @param productDto
	 * @return
	 *
	 */
	List<ProductVO> getProductsForMallClass(ProductDTO productDto);
	
	/**
	 * 商城点击全部查询使用
	 * 
	 * @param productDto
	 * @return
	 *
	 */
	List<ProductVO >	getProductsAllForMall (ProductDTO productDto);
	
	/**
	 * 商城首页查询使用
	 * 
	 * @param productDto
	 * @return
	 *
	 */
	List<ProductVO> getProductsForMallIndexPage(ProductDTO productDto);
	
	/**
	 * 供应商冻结使用
	 * 
	 * @param phProductDto
	 * @return
	 *
	 */
	 Integer  updateForSupplier(ProductDTO phProductDto);
	
}