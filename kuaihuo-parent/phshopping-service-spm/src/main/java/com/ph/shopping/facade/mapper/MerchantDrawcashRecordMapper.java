package com.ph.shopping.facade.mapper;


import java.math.BigDecimal;

import com.ph.shopping.common.core.base.BaseMapper;
import com.ph.shopping.facade.spm.entity.MerchantDrawcashRecord;
/**
 * 
 * phshopping-service-spm
 *
 * @description：商户提现记录Mapper
 *
 * @author：liuy
 *
 * @createTime：2017年3月23日
 *
 * @Copyright @2017 by liuy
 */
public interface MerchantDrawcashRecordMapper  extends BaseMapper<MerchantDrawcashRecord>{

	/**
	 * 商户提现记录新增
	 * @param merchantDrawcashRecord 商户提现记录
	 * @author liuy
	 * @createTime 2017年3月23日
	 */
	public void addMerchantDrawcashRecord(MerchantDrawcashRecord merchantDrawcashRecord);

    /**
     * 查询登陆人当天的已提现总额
     * @param agentDrawcashRecord 商户提现记录
     * @return BigDecimal
	 * @author liuy
	 * @createTime 2017年3月23日
     */
	public BigDecimal getDrawcashMoneyToday(MerchantDrawcashRecord merchantDrawcashRecord);
    
	 /**
     * 查询登陆人提现后的待审核状态总额
     * @param agentDrawcashRecord 商户提现记录
     * @return BigDecimal 
     * @return Result
     * @author liuy
	 * @createTime 2017年3月23日
     */
	public BigDecimal getDrawcashStatusPendingTotal(MerchantDrawcashRecord merchantDrawcashRecord);
}

