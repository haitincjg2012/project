package com.ph.shopping.facade.mapper;

import com.ph.shopping.common.core.base.BaseMapper;
import com.ph.shopping.facade.pay.entity.ScoreCostTrade;

/**
 * @项目：phshopping-facade-pay
 * @描述：积分支出流水记录表
 * @作者： 张霞
 * @创建时间： 17:06 2017/4/11
 * @Copyright @2017 by zhangxia
 */
public interface ScoreCostTradeMapper extends BaseMapper<ScoreCostTrade> {
    /**
     * 插入积分提现记录(支出记录)
     * @param scoreCostTrade
     */
    public void addScoreCostTrade(ScoreCostTrade scoreCostTrade);
}
