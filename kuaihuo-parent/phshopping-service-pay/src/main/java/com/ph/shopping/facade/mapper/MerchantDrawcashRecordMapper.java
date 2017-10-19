package com.ph.shopping.facade.mapper;

import com.ph.shopping.common.core.base.BaseMapper;
import com.ph.shopping.facade.pay.entity.MerchantDrawcashRecord;
/**
 * 
 * @项目：phshopping-service-pay
 *
 * @描述：商户提现记录Mapper
 *
 * @作者： liuy
 *
 * @创建时间：2017年3月23日
 *
 * @Copyright @2017 by liuy
 */
public interface MerchantDrawcashRecordMapper  extends BaseMapper<MerchantDrawcashRecord>{

	/**
	 * 商户提现记录新增
	 * @param merchantDrawcashRecord 商户提现记录
	 * @throws
	 */
	public void addMerchantDrawcashRecord(MerchantDrawcashRecord merchantDrawcashRecord);
}
