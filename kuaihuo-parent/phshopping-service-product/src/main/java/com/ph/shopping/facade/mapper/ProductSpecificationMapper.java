package com.ph.shopping.facade.mapper;

import com.ph.shopping.common.core.base.BaseMapper;
import com.ph.shopping.facade.product.dto.ProductDTO;
import com.ph.shopping.facade.product.entity.ProductSpecification;
import com.ph.shopping.facade.product.vo.ProductSpecificationVO;

import java.util.List;


/**
 * 
 * @项目：phshopping-service-product
 *
 * @描述： 商品规格类型dao层
 *
 * @作者： 杨颜光
 *
 * @创建时间：2017年5月12日 下午3:53:42
 *
 * @Copyright by  杨颜光
 */
public interface ProductSpecificationMapper extends BaseMapper<ProductSpecification>{

    /**
     * 查询规格名称以及旗下规格的值
     *
     * @param productDTO
     * @return List<ProductSpecificationVO>
     * 
     * @author: 李超
     * @date: 2017-05-17 14:57:29
     */
    List<ProductSpecificationVO> getProductSpecificationVOListByProductId(ProductDTO productDTO);




    /**
     * 根据商品id联表物理删除商品规格和规格值
     *
     * @param id
     * @return Integer
     * 
     * @author: 李超
     * @date: 2017-05-18 15:28:08
     */
    Integer deleteProductSpecificationAndValByProductId(Long id);
}