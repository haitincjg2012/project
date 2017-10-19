package com.ph.shopping.facade.accountsettle.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ph.shopping.common.util.core.RespCode;
import com.ph.shopping.common.util.core.ResultUtil;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.common.util.transform.MoneyTransUtil;
import com.ph.shopping.facade.mapper.ISupplyChainBalanceMapper;
import com.ph.shopping.facade.mapper.IUserBalanceStatisticsMapper;
import com.ph.shopping.facade.profit.dto.SupplyChainBalanceDTO;
import com.ph.shopping.facade.profit.service.ISupplyChainBalanceServce;
import com.ph.shopping.facade.profit.vo.SupplyChainBalanceVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
/**
 * 
* @ClassName: SupplyChainBalanceServiceImpl
* @Description: 供链余额实现类
* @author 王强
* @date 2017年6月9日 下午12:28:17
 */
@Service(version="1.0.0")
public class SupplyChainBalanceServiceImpl implements ISupplyChainBalanceServce {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	
	@Autowired
	private ISupplyChainBalanceMapper iSupplyChainBalanceMapper;

	@Autowired
	private IUserBalanceStatisticsMapper iUserBalanceStatisticsMapper;

	@Override
	public Result getSupplyChainBalanceList(SupplyChainBalanceDTO supplyChainBalanceDTO) {
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			String format = df.format(new Date());
			format = format +" 6:00:00";
			supplyChainBalanceDTO.setCreateTime( format );
			PageHelper.startPage(supplyChainBalanceDTO.getPageNum(), supplyChainBalanceDTO.getPageSize());
			List<SupplyChainBalanceVO> supplyChainBalanceVOs = (Page<SupplyChainBalanceVO>) iUserBalanceStatisticsMapper.getSupplyChainBalancesStatistics(supplyChainBalanceDTO);
			supplyChainBalanceVOs = transData(supplyChainBalanceVOs);
			PageInfo<SupplyChainBalanceVO> pageInfo = new PageInfo<SupplyChainBalanceVO>(supplyChainBalanceVOs);
			return ResultUtil.getResult(RespCode.Code.SUCCESS, pageInfo.getList(), pageInfo.getTotal());
		} catch (Exception e) {
			logger.error("查询余额明细异常", e);
			return ResultUtil.getResult(RespCode.Code.FAIL, null, 0l);
		}
	}
	
	@Override
	public List<SupplyChainBalanceVO> getExportData(SupplyChainBalanceDTO supplyChainBalanceDTO) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String format = df.format(new Date());
		format = format +" 7:00:00";
		supplyChainBalanceDTO.setCreateTime( format );
		List<SupplyChainBalanceVO> supplyChainBalanceVOs = (Page<SupplyChainBalanceVO>) iUserBalanceStatisticsMapper.getSupplyChainBalancesStatistics(supplyChainBalanceDTO);
		supplyChainBalanceVOs = transData(supplyChainBalanceVOs);
		return supplyChainBalanceVOs;
	}

	/**
	 * @author: 张霞
	 * @description：转换数据中的金额数据
	 * @createDate: 14:45 2017/7/7
	 * @param supplyChainBalanceVOs
	 * @return: java.util.List<com.ph.shopping.facade.profit.vo.SupplyChainBalanceVO>
	 * @version: 2.1
	 */
	private List<SupplyChainBalanceVO> transData(List<SupplyChainBalanceVO> supplyChainBalanceVOs){

		for (SupplyChainBalanceVO supplyChainBalanceVO : supplyChainBalanceVOs) {
			supplyChainBalanceVO.setScore(MoneyTransUtil.stringFormat(MoneyTransUtil.transDivisDouble(supplyChainBalanceVO.getScore1())));
			supplyChainBalanceVO.setOncharge(MoneyTransUtil.stringFormat(
					MoneyTransUtil.transDivisDouble(supplyChainBalanceVO.getOncharge1())));
			supplyChainBalanceVO.setDrawcash(MoneyTransUtil.stringFormat(
					MoneyTransUtil.transDivisDouble( supplyChainBalanceVO.getDrawcash1() )));
			supplyChainBalanceVO.setServiceCost(MoneyTransUtil.stringFormat(
					MoneyTransUtil.transDivisDouble(supplyChainBalanceVO.getServiceCost1())));
			supplyChainBalanceVO.setUnlineSettle(MoneyTransUtil.stringFormat(
					MoneyTransUtil.transDivisDouble(supplyChainBalanceVO.getUnlineSettle1())));
			supplyChainBalanceVO.setUnlineManageProfit(MoneyTransUtil.stringFormat(
					MoneyTransUtil.transDivisDouble(supplyChainBalanceVO.getUnlineManageProfit1())));
			supplyChainBalanceVO.setOnlineSettle(MoneyTransUtil.stringFormat(
					MoneyTransUtil.transDivisDouble(supplyChainBalanceVO.getOnlineSettle1())));
			supplyChainBalanceVO.setOnlineManageProfit(MoneyTransUtil.stringFormat(
					MoneyTransUtil.transDivisDouble(supplyChainBalanceVO.getOnlineManageProfit1())));
			supplyChainBalanceVO.setOnlineChainProfit(MoneyTransUtil.stringFormat(
					MoneyTransUtil.transDivisDouble(supplyChainBalanceVO.getOnlineChainProfit1())));
			supplyChainBalanceVO.setSupplyChainSettle(MoneyTransUtil.stringFormat(
					MoneyTransUtil.transDivisDouble(supplyChainBalanceVO.getSupplyChainSettle1())));
			supplyChainBalanceVO.setSupplyChainBalancePay(MoneyTransUtil.stringFormat(
					MoneyTransUtil.transDivisDouble(supplyChainBalanceVO.getSupplyChainBalancePay1())));
			supplyChainBalanceVO.setHunterManageProfit(MoneyTransUtil.stringFormat(
					MoneyTransUtil.transDivisDouble(supplyChainBalanceVO.getHunterManageProfit1())));
			supplyChainBalanceVO.setHunterChainProfit(MoneyTransUtil.stringFormat(
					MoneyTransUtil.transDivisDouble(supplyChainBalanceVO.getHunterChainProfit1())));
			supplyChainBalanceVO.setSupplyChainRefund(MoneyTransUtil.stringFormat(
					MoneyTransUtil.transDivisDouble(supplyChainBalanceVO.getSupplyChainRefund1())));
			//平衡差
			supplyChainBalanceVO.setBalanceDifference("0.00");
		}
		return supplyChainBalanceVOs;
	}

}
