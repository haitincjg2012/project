package com.ph.shopping.facade.accountsettle.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ph.shopping.common.util.core.RespCode;
import com.ph.shopping.common.util.core.ResultUtil;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.common.util.transform.MoneyTransUtil;
import com.ph.shopping.facade.mapper.IOnLineSettleMapper;
import com.ph.shopping.facade.profit.dto.OnLineSettleDTO;
import com.ph.shopping.facade.profit.exception.ProfitException;
import com.ph.shopping.facade.profit.exception.ProfitExceptionEnum;
import com.ph.shopping.facade.profit.service.IOnLineSettleService;
import com.ph.shopping.facade.profit.vo.OnLineSettleVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 
 * @ClassName: OnLineSettle
 * @Description: 线上订单结算实现类
 * @author 王强
 * @date 2017年6月5日 下午4:08:32
 */
@Component
@Service(version = "1.0.0")
public class OnLineSettleServiceImpl implements IOnLineSettleService {

	private Logger logger = LoggerFactory.getLogger(OnLineSettleServiceImpl.class);

	@Autowired
	private IOnLineSettleMapper iOnLineSettleMapper;

	@Override
	public Result queryOnlineSettles(OnLineSettleDTO lineSettleDTO) {
		try {
			PageHelper.startPage(lineSettleDTO.getPageNum(), lineSettleDTO.getPageSize());
			// 查询线上结算数据
			List<OnLineSettleVO> lineSettleVOs = (Page<OnLineSettleVO>) iOnLineSettleMapper.getOnLineSettles(lineSettleDTO);
			for (OnLineSettleVO lineSettleVO : lineSettleVOs) {
				// 设置订单金额
				lineSettleVO.setOrderMoney(
						MoneyTransUtil.stringFormat(MoneyTransUtil.transDivisDouble(lineSettleVO.getOrderMoney1())));
				// 设置结算金额
				long total = lineSettleVO.getSettlementMoney1() + lineSettleVO.getLogisticsMoney1();
				lineSettleVO.setTotalSettlePrice(MoneyTransUtil.stringFormat(MoneyTransUtil.transDivisDouble(total)));
			}
			PageInfo<OnLineSettleVO> pageInfo = new PageInfo<OnLineSettleVO>(lineSettleVOs);

			return ResultUtil.getResult(RespCode.Code.SUCCESS, pageInfo.getList(), pageInfo.getTotal());
		} catch (Exception e) {
			logger.error("查询线上结算列表异常", e);
			return ResultUtil.getResult(RespCode.Code.SUCCESS, null, 0l);
		}
	}

	@Override
	@Transactional
	public int updateOnLineOrderIsSettle(List<String> dtos) {
		try {
			return iOnLineSettleMapper.updOrderIsSettle(dtos);
		} catch (Exception e) {
			logger.error("更新结算状态失败", e);
			throw new ProfitException(ProfitExceptionEnum.SETTLE_EXCEPTION);
		}
	}

	@Override
	public List<OnLineSettleVO> getExportData(OnLineSettleDTO lineSettleDTO) {
		List<OnLineSettleVO> lineSettleVOs = iOnLineSettleMapper.getOnLineSettles(lineSettleDTO);
		for (OnLineSettleVO lineSettleVO : lineSettleVOs) {
			// 设置订单金额
			lineSettleVO.setOrderMoney(
					MoneyTransUtil.stringFormat(MoneyTransUtil.transDivisDouble(lineSettleVO.getOrderMoney1())));
			// 设置结算金额
			long total = lineSettleVO.getSettlementMoney1() + lineSettleVO.getLogisticsMoney1();
			lineSettleVO.setTotalSettlePrice(MoneyTransUtil.stringFormat(MoneyTransUtil.transDivisDouble(total)));
		}
		return lineSettleVOs;
	}
	
	@Override
	@Transactional
	public int updateOnLineOrderSingleIsSettle(String orderNo) {
		try {
			return iOnLineSettleMapper.updateSingleIsSettle(orderNo);
		} catch (Exception e) {
			logger.error("更新结算状态失败", e);
			throw new ProfitException(ProfitExceptionEnum.SETTLE_EXCEPTION);
		}
	}
}
