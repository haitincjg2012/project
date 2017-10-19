package com.ph.shopping.facade.mapper;

import com.ph.shopping.common.core.base.BaseMapper;
import com.ph.shopping.facade.pay.entity.SupplierDrawcashRecord;
/**
 * 
 * @项目：phshopping-service-pay
 *
 * @描述：供应商提现记录Mapper
 *
 * @作者： liuy
 *
 * @创建时间：2017年3月23日
 *
 * @Copyright @2017 by liuy
 */
public interface SupplierDrawcashRecordMapper extends BaseMapper<SupplierDrawcashRecord> {

	/**
	 * 供应商提现记录新增
	 * @param supplierDrawcashRecord 供应商提现记录
	 * @throws
	 */
	public void addSupplierDrawcashRecord(SupplierDrawcashRecord supplierDrawcashRecord);
}
