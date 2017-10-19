package com.ph.shopping.facade.mapper;


import java.math.BigDecimal;

import com.ph.shopping.common.core.base.BaseMapper;
import com.ph.shopping.facade.spm.entity.AgentDrawcashRecord;

/**
 * 
 * phshopping-service-spm
 *
 * @description：代理商提现记录Mapper
 *
 * @author：liuy
 *
 * @createTime：2017年3月23日
 *
 * @Copyright @2017 by liuy
 */
public interface AgentDrawcashRecordMapper extends BaseMapper<AgentDrawcashRecord> {

    /**
     * 代理商提现记录新增
     * @param agentDrawcashRecord 代理商提现记录
	 * @author liuy
	 * @createTime 2017年3月23日
     */
    public void addAgentDrawcashRecord(AgentDrawcashRecord agentDrawcashRecord);
	
    /**
     * 查询登陆人当天的已提现总额
     * @param agentDrawcashRecord 代理商提现记录
     * @return BigDecimal
	 * @author liuy
	 * @createTime 2017年3月23日
     */
    public BigDecimal getDrawcashMoneyToday(AgentDrawcashRecord agentDrawcashRecord);

    /**
     * 查询登陆人提现后的待审核状态总额
     * @param agentDrawcashRecord 代理商提现记录
     * @return BigDecimal 
     * @return Result
     * @author liuy
	 * @createTime 2017年3月23日
     */
    public BigDecimal getDrawcashStatusPendingTotal(AgentDrawcashRecord agentDrawcashRecord);
}

