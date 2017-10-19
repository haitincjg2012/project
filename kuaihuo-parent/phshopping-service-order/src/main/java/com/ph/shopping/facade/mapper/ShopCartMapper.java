package com.ph.shopping.facade.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.ph.shopping.common.core.base.BaseMapper;

import cm.ph.shopping.facade.order.dto.ShopCartDTO;
import cm.ph.shopping.facade.order.entity.ShopCart;
import cm.ph.shopping.facade.order.vo.ShopCartVO;



/**
 * @项目：phshopping-facade-order
 * @描述：购物车操作
 * @作者： gaoge
 * @创建时间： 2017/8/24
 * @Copyright @2017
 */

public interface ShopCartMapper extends BaseMapper<ShopCart> {
	/*
	 * 查询商户下的商品
	 */
	List<ShopCartVO> getShopCartList(Map<String, Object> params);
	/**
	 * 
	 * @param map
	 * @return
	 */
	 ShopCartVO  getShopCart(Map<String,Object> map);

	/**
	 * 快火二期  李治桦
	 * @param map
	 * @return
	 */
	ShopCartVO  getShopCart2(Map<String,Object> map);
	 
	 List<ShopCartVO> getShopCartListByMemberId(Long memberid);
	 
	 ShopCartVO getShopCartById(Map<String,Object> map);
	 
	 void insertShopToCar(ShopCartDTO shopCartDTO);
	 
	 void updateShopCart(ShopCartDTO shopCartDTO);
	 
	 List<Integer> getShopcarType(Map<String,Object> map);
}
