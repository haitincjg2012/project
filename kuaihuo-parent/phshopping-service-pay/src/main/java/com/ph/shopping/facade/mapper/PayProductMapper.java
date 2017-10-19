package com.ph.shopping.facade.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 订单商品接连部分mapper
 *
 * @author 郑朋
 * @create 2017/5/16
 **/
@Repository
public interface PayProductMapper {

    /**
     * @methodname selectCurrentProductSkuId 的描述：通过商品id和商品规格组合id查询商品规格属性最新的skuId
     * @param productId
     * @param specificationValIds
     * @return java.lang.Long
     * @author 郑朋
     * @create 2017/5/16
     */
    Long selectCurrentProductSkuId(@Param("productId") Long productId, @Param("specificationValIds") String specificationValIds);

    /**
     * @methodname addProductCount 的描述：增加商品总数
     * @param productId
     * @param num
     * @return int
     * @author 郑朋
     * @create 2017/5/16
     */
    int addProductCount(@Param("productId") Long productId, @Param("num") Integer num);

    /**
     * @methodname addSkuCount 的描述：增加商品sku总数
     * @param skuId
     * @param num
     * @return int
     * @author 郑朋
     * @create 2017/5/16
     */
    int addSkuCount(@Param("skuId") Long skuId, @Param("num") Integer num);


}
