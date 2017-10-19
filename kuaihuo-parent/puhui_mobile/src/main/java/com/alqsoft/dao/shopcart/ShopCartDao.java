package com.alqsoft.dao.shopcart;

import com.alqsoft.vo.ShopCartVO;
import org.alqframework.orm.mybatis.MyBatisRepository;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 购物车DAO
 * @author Xuejizheng
 * @date 2017-02-28 14:55
 * @see
 * @since 1.8
 */
@MyBatisRepository
public interface ShopCartDao {

      List<ShopCartVO> getShopCartList(Map<String,Object> map);

      List<ShopCartVO> getShopCartListByMemberId(Long uid);

      ShopCartVO  getShopCart(Map<String,Object> map);

      ShopCartVO getShopCartById(Map<String,Object> map);

      long getShopCartCountByMemberId(Long id);

      List<Map<String,Object>> getShopCartListByHunterAndMember(@Param("hid") Long hid,@Param("uid") Long uid);

      public Long getShopCartBuyNum(@Param("phone")String phone,@Param("uuid")String uuid,@Param("specificationId")Long id);

      Long getTotalPriceByMember(@Param("uid") Long uid);
}
