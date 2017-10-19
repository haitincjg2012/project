package com.ph.shopping.facade.product.service;

import java.util.List;

import com.ph.shopping.common.util.page.PageBean;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.product.dto.ProductDTO;
import com.ph.shopping.facade.product.entity.Product;
import com.ph.shopping.facade.product.entity.ProductImage;
import com.ph.shopping.facade.product.entity.ProductPropertyVal;
import com.ph.shopping.facade.product.entity.ProductSku;
import com.ph.shopping.facade.product.vo.ProductVO;


/**
 * 
 * @项目：phshopping-facade-
 *
 * @描述：商品
 *
 * @作者： 杨颜光
 *
 * @创建时间：2017年5月16日 上午10:53:49
 *
 * @Copyright by 杨颜光
 */
public interface IProductService {
	
	/**
	 * 商品分页列表查询
	 * 
	 * @param pageBean
	 * @param phProductDto
	 * @return Result
	 * @author 杨颜光
	 *
	 */
	Result getProductByPage(PageBean pageBean, ProductDTO productDto) ;

	/**
	 * 单条商品查询
	 * 
	 * @param product
	 * @return Product
	 * @author 杨颜光
	 *
	 */
	 Product getProduct(Product product) ;
	 
	 /**
	  * 多条商品列表查询
	  * 
	  * @param product
	  * @return List<Product>
	  * @author 杨颜光
	  */
	  List<Product>  getProductList(Product product) ;

	  /**
	   * 逻辑删除商品
	   * 
	   * @param phProductDto
	   * @return Result
	   * @author 杨颜光
	   */
	  Result deleteLogicProduct(Product  product) ;

	/**
	 * @description: 修改的商品 1.所有金额转换操作 2.图片和属性值的修改方式是删除所有之前的记录, 新增当前的记录
	 * @param product
	 *            实体
	 * @param imageList
	 *            图片集合
	 * @param ppvList
	 *            属性值集合
	 * @param skuList
	 *            规格值组合SKU集合
	 * @param specificationAndValJson
	 *            规格和值得json
	 * @return Result
	 * 
	 * @author: 李超
	 * @date: 2017-05-18 14:51:14
	 */
	Result updateProduct(Product product, List<ProductImage> imageList, List<ProductPropertyVal> ppvList,
						 List<ProductSku> skuList, String specificationAndValJson);

	/**
	 * 商品添加方法
	 * 
	 * @param product
	 *            实体
	 * @param imageList
	 *            图片集合
	 * @param ppvList
	 *            属性值集合
	 * @param skuList
	 *            规格值组合SKU集合
	 * @param specificationAndValJson
	 *            规格和值得json
	 * @return Result
	 * @author 杨颜光
	 */
	Result addProduct(Product product, List<ProductImage> imageList, List<ProductPropertyVal> ppvList,List<ProductSku> skuList,
			String specificationAndValJson);

	/**
	 * 审核商品
	 * 
	 * @author  杨颜光
	 * @param phProductDto:id-auditState
	 * @return Result
	 */
	Result auditProduct(Product product) ;

	/**
	 * 商品上架、下架
	 * 
	 * @author 杨颜光
	 * @param phProductDto:id-saleNoSaleStatus
	 * @return Result
	 */
	Result onsalProduct(Product product) ;

	/**
	 * 
	 * @description: 根据分类id获取商品数量
	 * @param classifyId
	 * @return
	 * @author 李超
	 * @date 2017年3月28日
	 */
	Integer getProductCountByClassify(Long classifyId);

	/**
	 * 通过id查询商品信息(供应链使用)
	 * 
	 * @param productIds
	 * @return List<ProductVO>
	 * @author 杨颜光
	 */
	List<ProductVO> getProductListForOder(List<Long> productIds);
	
	/**
	 * 商城商品列表展示方法
	 * 
	 * @param productDto
	 * @return List<ProductVO>
	 * @author 杨颜光
	 */
	Result getProductsForMall(PageBean pageBean,ProductDTO productDto);
	
	/**
	 * 商城分类列表展示方法
	 * 
	 * @param productDto
	 * @return List<ProductVO>
	 * @author 杨颜光
	 */
	List<ProductVO> getProductsForMallClass(ProductDTO productDto);

	/***
	 * 商城首页查询使用
	 * 
	 * @param productDto
	 * @return  List<ProductVO>
	 * @author 杨颜光
	 */
	List<ProductVO> getProductsForMallIndexPage(ProductDTO productDto);
	
	/**
	 * 供应商冻结将对应商品下架
	 * 
	 * @param phProductDto
	 * @return
	 *
	 */
	Integer updateForSupplier(ProductDTO phProductDto);

	/**
	 * 商城点击全部查询使用
	 * 
	 * @param pageBean
	 * @param productDto
	 * @return Result
	 * @author 杨颜光
	 */
	Result getProductsAllForMall(PageBean pageBean, ProductDTO productDto);
}
