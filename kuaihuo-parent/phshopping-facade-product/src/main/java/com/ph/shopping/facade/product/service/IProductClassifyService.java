package com.ph.shopping.facade.product.service;

import java.util.List;

import com.ph.shopping.common.util.page.PageBean;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.product.dto.ProductClassifyDTO;
import com.ph.shopping.facade.product.entity.ProductClassify;
import com.ph.shopping.facade.product.vo.ProductClassifyVO;

/**
 * IProductClassifyService 商品分类接口
 *
 * @version 2.1
 * @author: 李超
 * @date: 2017-05-15 10:45:02
 */
public interface IProductClassifyService {


    /**
     * @param productClassify 分类对象
     * @return Result
     *
     * @description: 新增商品分类
     * @author 李超
     * @date 2017年3月15日
     */
    Result addProductClassify(ProductClassify productClassify) ;

    /**
     * @param productClassify
     * @return Result
     *
     * @description: 修改商品分类
     * @author 李超
     * @date 2017年3月15日
     */
    Result updateProductClassify(ProductClassify productClassify) ;


    /**
     *  修改商品分类排序
     *
     * @param productClassify
     * @return Result
     * 
     * @author: 李超
     * @date: 2017-06-07 15:07:13
     */
    Result updateProductClassifySort(ProductClassify productClassify);
    
    /**
     * 根据商品类型id获取下面所有被引用的商品数量
     *  默认查询条件为查询商品以及类型都标识为未删除的数据
     *
     * @param id
     * @return Integer
     * 
     * @author: 李超
     * @date: 2017-05-17 17:58:11
     */
    Integer getExistProductCountByClassify(Long id);

    /**
     * 根据条件获取商品类别VO列表(默认查询条件为查询标识为未删除的数据)
     *
     * @param productClassifyDTO
     * @return 
     * 
     * @author: 李超
     * @date: 2017-05-15 16:52:37
     */
    List<ProductClassifyVO> getProductClassifyVOList(ProductClassifyDTO productClassifyDTO);

   /**
    * 根据条件获取商品类别集合(单表查询)
    *   默认查询条件为查询标识为未删除的数据
    *
    * @param productClassify
    * @return 
    * 
    * @author: 李超
    * @date: 2017-05-15 16:52:45
    */
    List<ProductClassify> getProductClassifyList(ProductClassify productClassify);

   /**
    * 根据当前分类获取所有子类别(结果包含自己)
    *   默认查询条件为查询标识为未删除的数据
    *
    * @param productClassifyDTO
    * @return 
    * 
    * @author: 李超
    * @date: 2017-05-15 20:42:41
    */
    List<ProductClassifyVO> getProductClassifyChildren(ProductClassifyDTO productClassifyDTO);

    /**
     * 根据当前分类获取所有上级类别(结果包含自己)
     *   默认查询条件为查询标识为未删除的数据
     *
     * @param productClassifyDTO
     * @return 
     * 
     * @author: 李超
     * @date: 2017-05-15 20:43:44
     */
    List<ProductClassifyVO> getProductClassifyParents(ProductClassifyDTO productClassifyDTO);


    /**
     *  根据主键id查询
     *
     * @param id
     * @return ProductClassify
     * 
     * @author: 李超
     * @date: 2017-05-16 09:45:53
     */
    ProductClassify getProductClassifyById(long id);

    /**
     *  根据单表条件返回一个商品分类对象
     *  注意!!! 根据实体中的属性进行查询，只能有一个返回值，有多个结果是抛出异常
     *  实现类直接调用selectOne()
     *
     * @param productClassify
     * @return 
     * 
     * @author: 李超
     * @date: 2017-05-16 15:02:27
     */
    ProductClassify getOneProductClassify(ProductClassify productClassify);

    /**
     * 删除商品分类
     *
     * @param id
     * @return Result
     * 
     * @author: 李超
     * @date: 2017-05-16 11:56:32
     */
    Result deleteProductClassifyById(long id);
    
    /**
     * 停用商品分类
     *      停用分类同时停用该类型的所有子类分类(如果有子类)
     * @param id
     * @return Result
     * 
     * @author: 李超
     * @date: 2017-05-16 11:56:36
     */
    Result updateDisableProductClassifyById(long id);

    /**
     * 启用商品分类
     *      启用分类同时启用该类型的所有上级类分类(如果有上级)
     * @param id
     * @return Result
     * 
     * @author: 李超
     * @date: 2017-05-16 11:56:41
     */
    Result updateEnableProductClassifyById(long id);

    /**
     * 商城首页商品分类展示 导航
     *  根据sort排序获取前number个一级分类一级其二级子类
     *
     * @param number 需要展示的一级分类的个数
     * @return 
     * 
     * @author: 李超
     * @date: 2017-06-12 11:09:01
     */
    List<ProductClassifyVO> getProductClassifyShowIndex(Integer number);

    /**
     * 商城首页商品分类展示 导航
     *  根据sort排序默认获取前8个一级分类一级其二级子类

     * @return
     *
     * @author: 李超
     * @date: 2017-06-12 11:09:01
     */
    List<ProductClassifyVO> getProductClassifyShowIndex();

    /**
     * 商城首页商品分类模块

     * @return
     *
     * @author: 李超
     * @date: 2017-06-12 11:09:01
     */
    List<ProductClassifyVO> getIndexClassify();

}
