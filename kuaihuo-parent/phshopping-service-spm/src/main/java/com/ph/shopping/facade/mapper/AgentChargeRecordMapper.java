package com.ph.shopping.facade.mapper;


import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Service;
import com.ph.shopping.common.core.base.BaseMapper;
import com.ph.shopping.facade.spm.entity.AgentChargeRecord;

/**
 * 
 * phshopping-service-spm
 *
 * @description：代理商充值记录Mapper
 *
 * @author：liuy
 *
 * @createTime：2017年3月23日
 *
 * @Copyright @2017 by liuy
 */
@Component
@Service(version = "1.0.0")
public interface AgentChargeRecordMapper extends BaseMapper<AgentChargeRecord> {

}

