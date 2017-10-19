package com.ph.shopping.facade.mapper;

import org.apache.ibatis.annotations.Param;

import com.ph.shopping.common.core.base.BaseMapper;
import com.ph.shopping.facade.pay.entity.AgentChargeRecord;

/**
 * 
 * @项目：phshopping-service-pay
 *
 * @描述：代理商充值记录Mapper
 *
 * @作者： liuy
 *
 * @创建时间：2017年3月23日
 *
 * @Copyright @2017 by liuy
 */
public interface AgentChargeRecordMapper  extends BaseMapper<AgentChargeRecord>{
	
	/**
	 * 根据代理商订单号获取记录
	 * @param orderNum
	 */
	public AgentChargeRecord getAgentChargeRecordByRecordNo(@Param("orderNum")String orderNum);
	
	
}
