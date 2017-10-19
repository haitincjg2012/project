package com.ph.shopping.facade.product.service;

import java.util.List;

import com.ph.shopping.common.util.page.PageBean;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.product.dto.ProductDTO;
import com.ph.shopping.facade.product.dto.ProductPropertyDTO;
import com.ph.shopping.facade.product.entity.ProductProperty;
import com.ph.shopping.facade.product.vo.ProductPropertyVO;

/**
 * 
 * @项目：phshopping-facade-
 *
 * @描述：商品属性接口
 *
 * @作者：杨颜光
 *
 * @创建时间：2017年5月15日 上午11:34:15
 *
 * @Copyright by 杨颜光
 */
public interface IProductPropertyService {

	/**
	 * 商品属性添加方法
	 * 
	 * @param property
	 * @author 杨颜光
	 */
	Result addProductPeoperty(ProductProperty property);

	/**
	 * 商品属性修改方法
	 * 
	 * @param property
	 * @ @author 杨颜光
	 */
	Result updateProductPeoperty(ProductProperty property);

	/**
	 * 商品属性分页列表查询
	 * 
	 * @param pageBean
	 * @param phProductPropertyDto
	 * @return Result
	 * @author 杨颜光
	 */
	Result getProductPropertyByPage(PageBean pageBean, ProductPropertyDTO phProductPropertyDto);

	/**
	 * 单条商品属性列表查询
	 * 
	 * @param productProperty
	 * @return Result
	 * @author 杨颜光
	 *
	 */
	ProductProperty getProductProperty(ProductProperty productProperty);

	/**
	 * 获取多条商品属性
	 * 
	 * @param productProperty
	 * @return List<ProductProperty>
	 * @author 杨颜光
	 *
	 */
	List<ProductProperty> getProductProperties(ProductProperty productProperty);

	/**
	 * 逻辑删除商品属性
	 * 
	 * @param productProperty
	 * @return Result
	 * @author 杨颜光
	 *
	 */
	Result deleteProductProperty(ProductProperty productProperty);

	/***
	 * 
	 * 通过(商品所属的三级分类id)查询对应的属性（后台和商城都可使用）
	 * 
	 * @param productDto (参数需要商品主键ID和分类ID)
	 * @return
	 * @author 杨颜光
	 */
	List<ProductPropertyVO> getProductPropertyVoByThreeCassifyId(ProductDTO productDto);
	
	
	/**
	 * 商品属性列表排序方法
	 * 
	 * @param property
	 * @ @author 杨颜光
	 */
	Result updateProductPeopertyForSort(List<ProductProperty> propertiess);
}