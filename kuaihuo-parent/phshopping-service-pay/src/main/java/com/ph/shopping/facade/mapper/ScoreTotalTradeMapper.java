package com.ph.shopping.facade.mapper;

import com.ph.shopping.common.core.base.BaseMapper;
import com.ph.shopping.facade.pay.entity.ScoreTotalTrade;

/**
 * @项目：phshopping-facade-pay
 * @描述：积分总记录表
 * @作者： 张霞
 * @创建时间： 18:10 2017/4/11
 * @Copyright @2017 by zhangxia
 */
public interface ScoreTotalTradeMapper extends BaseMapper<ScoreTotalTrade> {
    /**
     * 插入积分总的流水记录
     * @param scoreTotalTrade
     */
    public void addScoreTotalTrade(ScoreTotalTrade scoreTotalTrade);
}
