package com.ph.shopping.facade.mapper;

import com.ph.shopping.common.core.base.BaseMapper;
import com.ph.shopping.facade.profit.dto.SupplyChainBalanceDTO;
import com.ph.shopping.facade.profit.entity.UserBalanceStatistics;
import com.ph.shopping.facade.profit.vo.SupplyChainBalanceVO;

import java.util.List;

/**
 * @项目：phshopping-facade-order
 * @描述：统计供应链余额
 * @作者： 张霞
 * @创建时间： 16:52 2017/7/5
 * @Copyright @2017 by zhangxia
 */
public interface IUserBalanceStatisticsMapper extends BaseMapper<UserBalanceStatistics> {
    List<SupplyChainBalanceVO> getSupplyChainBalancesStatistics(SupplyChainBalanceDTO supplyChainBalanceDTO);
}
