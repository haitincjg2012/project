package com.ph.shopping.facade.mapper;

import java.util.List;

import com.ph.shopping.common.core.base.BaseMapper;
import com.ph.shopping.facade.product.dto.ProductClassifyDTO;
import com.ph.shopping.facade.product.entity.ProductClassify;
import com.ph.shopping.facade.product.vo.ProductClassifyVO;

/**
 * ProductClassifyMapper
 * @version: 2.1
 * @author: 李超
 * @date: 2017-05-15 14:09:23
 */
public interface ProductClassifyMapper extends BaseMapper<ProductClassify> {


	/**
	 * 根据商品类型(id)获取所有引用该类型的商品的总数量
	 *
	 * @param id
	 * @return Integer
	 * 
	 * @author: 李超
	 * @date: 2017-05-15 14:09:53
	 */
	Integer getExistProductCountByClassify(Long id);

	/**
	 *	根据条件查询商品分类列表 (扩展取得每个分类下面包含的商品总数)
	 *
	 * @param productClassifyDTO
	 * @return List<ProductClassifyVO>
	 * 
	 * @author: 李超
	 * @date: 2017-05-15 16:24:51
	 */
	List<ProductClassifyVO> getProductClassifyVOList(ProductClassifyDTO productClassifyDTO);

	/**
	 * 根据当前分类获取所有子类别(结果包含自己)
	 *
	 * @param productClassifyDTO
	 * @return List<ProductClassifyVO>
	 * 
	 * @author: 李超
	 * @date: 2017-05-16 09:09:17
	 */
	List<ProductClassifyVO> getProductClassifyChildren(ProductClassifyDTO productClassifyDTO);
	
	/**
	 *  根据当前分类获取所有上级类别(结果包含自己)
	 *
	 * @param productClassifyDTO
	 * @return List<ProductClassifyVO>
	 * 
	 * @author: 李超
	 * @date: 2017-05-16 09:10:06
	 */
	List<ProductClassifyVO> getProductClassifyParents(ProductClassifyDTO productClassifyDTO);

	/**
	 * 逻辑删除商品类别(包含删除所有子类别)
	 *
	 * @param id
	 * @return
	 * 
	 * @author: 李超
	 * @date: 2017-05-16 17:28:26
	 */
	Integer deleteProductClassifyById(Long id);
	
	/**
	 *	根据商品类别[逻辑删除]该类别以及子类别中包含的[属性]
	 *
	 * @param id
	 * @return Integer
	 * 
	 * @author: 李超
	 * @date: 2017-05-16 20:47:22
	 */
	Integer deleteProductPropertyByProductClassifyId(Long id);
	
	/**
	 * 根据商品类别[物理删除]该类别以及子类别中包含的[属性值]
	 *
	 * @param id
	 * @return Integer
	 * 
	 * @author: 李超
	 * @date: 2017-05-16 20:48:21
	 */
	Integer deleteProductPropertyValByProductClassifyId(Long id);

	/**
	 * 停用商品分类,时停用该类型的所有子类分类(如果有子类)
	 *
	 * @param id
	 * @return Integer
	 * 
	 * @author: 李超
	 * @date: 2017-05-16 17:26:26
	 */
	Integer updateDisableProductClassifyById(Long id);

	/**
	 * 启用商品分类,同时启用该类型的所有上级类分类(如果有上级)
	 *
	 * @param id
	 * @return Integer
	 * 
	 * @author: 李超
	 * @date: 2017-05-16 17:20:36
	 */
	Integer updateEnableProductClassifyById(Long id);

	/**
	 * 商城首页商品分类展示 导航
	 *  根据sort排序默认获取前8个一级分类一级其二级子类
	 *
	 * @param number
	 * @return 
	 * 
	 * @author: 李超
	 * @date: 2017-06-12 15:57:34
	 */
	List<ProductClassifyVO> getProductClassifyShowIndex(Integer number);

	/**
	 * 商城首页商品分类模块
	 *
	 * @return
	 *
	 * @author: 李超
	 * @date: 2017-06-13 15:57:34
	 */
	List<ProductClassifyVO> getIndexClassify();

}