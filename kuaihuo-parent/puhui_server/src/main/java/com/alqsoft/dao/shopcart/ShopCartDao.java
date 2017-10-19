package com.alqsoft.dao.shopcart;

import com.alqsoft.entity.member.Member;
import com.alqsoft.entity.shopcart.ShopCart;

import java.util.List;

import org.alqframework.orm.hibernate.BaseDao;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @author Xuejizheng
 * @date 2017-03-01 11:30
 * @see
 * @since 1.8
 */
public interface ShopCartDao extends BaseDao<ShopCart> {

    @Query(value=" delete from alq_shop_cart where member_id =?1 and product_specification_id =?2 ",nativeQuery = true)
    @Modifying
    void deleteByMemeberAndProductSpecification(Long uid,Long spid);

    @Query("FROM ShopCart AS s WHERE s.member.id=:id AND s.productSpecification.id=:psId")
	public List<ShopCart> findByMidAndPsId(@Param("id")Long id, @Param("psId")Long psId);

    @Modifying
    @Query(value = "DELETE FROM alq_shop_cart WHERE product_specification_id IN (SELECT n.id FROM alq_product_specification n ,alq_product p WHERE n.product_id=p.id AND p.hunter_id=?2) and member_id=?1",nativeQuery = true)
    int deleteShopCartsByHunterId(Long mid,Long hid);
}
