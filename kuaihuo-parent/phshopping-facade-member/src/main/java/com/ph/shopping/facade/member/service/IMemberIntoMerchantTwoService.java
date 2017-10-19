package com.ph.shopping.facade.member.service;

import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.member.dto.MerchantDishDTO;

/**
 * 商户进入商家service
 * @author 李治桦
 * @date 2017-08-27 21:38
 *
 */
public interface IMemberIntoMerchantTwoService {
	/**
	 * 获取商户基本信息
	 * @param merchantId
	 * @return
	 */
	Result getMerchantInfoTwo(Long merchantId);
	/**
	 * 获取商户的消费时段
	 */
	Result getMerchantTimeTwo(Long merchantId);
	/**
	 * 获取商户下的菜品信息
	 * @param typeId
	 * @param merchantId 
	 * @return
	 */
	Result getDishAllTwo(MerchantDishDTO dish);
	/**
	 * 获取商户下的菜品信息
	 * @param merchantId(userId) memberId
	 * @return
	 */
	Result getOnlineOrderDish(MerchantDishDTO order);
	/**
	 * 包间菜品回显  购物车中的和订单中的
	 * @param orderId
	 * @return
	 * @lzh
	 */
	Result getOnlineOrderAndShopCartDish(Long orderId);
	/**
	 * 点击菜品显示菜品详情
	 * @param dishId
	 * @return
	 * @lzh
	 */
	Result getDishInfoByDishId(Long dishId);
}
