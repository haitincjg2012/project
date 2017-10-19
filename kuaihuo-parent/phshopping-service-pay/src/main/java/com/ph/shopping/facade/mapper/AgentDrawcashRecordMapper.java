package com.ph.shopping.facade.mapper;

import com.ph.shopping.common.core.base.BaseMapper;
import com.ph.shopping.facade.pay.entity.AgentDrawcashRecord;
/**
 * 
 * @项目：phshopping-service-pay
 *
 * @描述：代理商提现记录Mapper
 *
 * @作者： liuy
 *
 * @创建时间：2017年3月23日
 *
 * @Copyright @2017 by liuy
 */
public interface AgentDrawcashRecordMapper  extends BaseMapper<AgentDrawcashRecord>{

	/**
	 * 代理商提现记录新增
	 * @param agentDrawcashRecord 代理商提现记录
	 * @throws
	 */
	public void addAgentDrawcashRecord(AgentDrawcashRecord agentDrawcashRecord);
}
