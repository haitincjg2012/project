package com.ph.shopping.facade.mapper;

import org.apache.ibatis.annotations.Param;

import com.ph.shopping.common.core.base.BaseMapper;
import com.ph.shopping.facade.pay.entity.MerchantChargeRecord;
/**
 * 
 * @项目：phshopping-service-pay
 *
 * @描述：商户充值记录Mapper
 *
 * @作者： liuy
 *
 * @创建时间：2017年3月23日
 *
 * @Copyright @2017 by liuy
 */
public interface MerchantChargeRecordMapper  extends BaseMapper<MerchantChargeRecord>{

	
	/**
	 * 根据商户订单号获取记录
	 * @param orderNum
	 */
	public MerchantChargeRecord getMerchantChargeRecordByRecordNo(@Param("orderNum")String orderNum);
	
	
}
