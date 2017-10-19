package com.ph.shopping.facade.accountsettle.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.ph.shopping.common.util.core.RespCode;
import com.ph.shopping.common.util.core.ResultUtil;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.mapper.ISupplyChainBalanceMapper;
import com.ph.shopping.facade.mapper.IUserBalanceStatisticsMapper;
import com.ph.shopping.facade.profit.dto.SupplyChainBalanceDTO;
import com.ph.shopping.facade.profit.entity.UserBalanceStatistics;
import com.ph.shopping.facade.profit.service.IUserBalanceStatisticsService;
import com.ph.shopping.facade.profit.vo.SupplyChainBalanceVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @项目：phshopping-facade-order
 * @描述：
 * @作者： 张霞
 * @创建时间： 16:40 2017/7/5
 * @Copyright @2017 by zhangxia
 */
@Component
@Service(version = "1.0.0")
public class UserBalanceStatisticsServiceImpl implements IUserBalanceStatisticsService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private ISupplyChainBalanceMapper iSupplyChainBalanceMapper;
    @Autowired
    private IUserBalanceStatisticsMapper iUserBalanceStatisticsMapper;
    @Override
    public Result doStatistics() {
        try {
            SupplyChainBalanceDTO supplyChainBalanceDTO = new SupplyChainBalanceDTO();
            List<SupplyChainBalanceVO> supplyChainBalanceVOs = (Page<SupplyChainBalanceVO>) iSupplyChainBalanceMapper.getSupplyChainBalances(supplyChainBalanceDTO);
            List<UserBalanceStatistics> userBalanceStatisticss = new ArrayList<>(  );
            for (SupplyChainBalanceVO supplyChainBalanceVO: supplyChainBalanceVOs) {
                UserBalanceStatistics userBalanceStatistics = new UserBalanceStatistics();
//                BeanUtils.copyProperties( supplyChainBalanceVO,userBalanceStatistics );
                userBalanceStatistics.setTelPhone( supplyChainBalanceVO.getTelPhone() );
                userBalanceStatistics.setUserType( supplyChainBalanceVO.getUserType() );
                userBalanceStatistics.setUserId( supplyChainBalanceVO.getUserId() );
                userBalanceStatistics.setOncharge( supplyChainBalanceVO.getOncharge1());
                userBalanceStatistics.setDrawcash( supplyChainBalanceVO.getDrawcash1() );
                userBalanceStatistics.setUnlineServiceCost( supplyChainBalanceVO.getServiceCost1() );
                userBalanceStatistics.setUnlineSettle( supplyChainBalanceVO.getUnlineSettle1() );
                userBalanceStatistics.setUnlineManageProfit( supplyChainBalanceVO.getUnlineManageProfit1() );
                userBalanceStatistics.setOnlineSettle( supplyChainBalanceVO.getOnlineSettle1() );
                userBalanceStatistics.setOnlineManageProfit( supplyChainBalanceVO.getOnlineManageProfit1() );
                userBalanceStatistics.setOnlineChainProfit( supplyChainBalanceVO.getOnlineChainProfit1() );
                userBalanceStatistics.setSupplyChainSettle( supplyChainBalanceVO.getSupplyChainSettle1() );
                userBalanceStatistics.setSupplyChainBalancePay( supplyChainBalanceVO.getSupplyChainBalancePay1() );
                userBalanceStatistics.setSupplyChainRefund( supplyChainBalanceVO.getSupplyChainRefund1() );
                userBalanceStatistics.setHunterManageProfit( supplyChainBalanceVO.getHunterManageProfit1() );
                userBalanceStatistics.setHunterChainProfit( supplyChainBalanceVO.getHunterChainProfit1() );
                userBalanceStatistics.setCreaterId( 0l );
                userBalanceStatistics.setStatus( supplyChainBalanceVO.getStatus().byteValue() );
                userBalanceStatistics.setCreateTime( new Date(  ) );
                userBalanceStatistics.setBalanced( 0l );
                userBalanceStatisticss.add( userBalanceStatistics );
            }
            iUserBalanceStatisticsMapper.insertList( userBalanceStatisticss );
            return ResultUtil.getResult( RespCode.Code.SUCCESS);
        } catch (Exception e) {
            logger.error("定时执行供应链余额统计异常，{}", e);
            return ResultUtil.getResult(RespCode.Code.FAIL, null, 0l);
        }
    }
}
