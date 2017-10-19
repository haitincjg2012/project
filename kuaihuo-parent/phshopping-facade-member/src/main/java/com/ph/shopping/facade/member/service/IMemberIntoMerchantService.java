package com.ph.shopping.facade.member.service;

import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.member.dto.MerchantDishDTO;

/**
 * 商户进入商家service
 * @author 李治桦
 * @date 2017-08-27 21:38
 *
 */
public interface IMemberIntoMerchantService {
	/**
	 * 获取商户基本信息
	 * @param merchantId
	 * @return
	 */
	Result getMerchantInfo(Long merchantId);
	/**
	 * 获取商户下的菜品信息
	 * @param typeId
	 * @param merchantId 
	 * @return
	 */
	Result getDishAll(MerchantDishDTO dish);

}
