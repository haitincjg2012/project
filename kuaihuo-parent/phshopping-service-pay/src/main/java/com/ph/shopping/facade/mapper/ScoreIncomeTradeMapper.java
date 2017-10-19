package com.ph.shopping.facade.mapper;

import com.ph.shopping.common.core.base.BaseMapper;
import com.ph.shopping.facade.pay.entity.ScoreIncomeTrade;

/**
 * @项目：phshopping-facade-pay
 * @描述：积分收入流水记录表 实体
 * @作者： 张霞
 * @创建时间： 18:04 2017/4/11
 * @Copyright @2017 by zhangxia
 */
public interface ScoreIncomeTradeMapper extends BaseMapper<ScoreIncomeTrade> {
    /**
     * 插入积分提现记录(收入记录)
     * @param scoreIncomeTrade
     */
    public void addScoreIncomeTrade(ScoreIncomeTrade scoreIncomeTrade);
}
