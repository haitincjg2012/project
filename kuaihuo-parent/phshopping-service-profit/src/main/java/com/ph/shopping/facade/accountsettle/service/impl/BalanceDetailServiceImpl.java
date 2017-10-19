package com.ph.shopping.facade.accountsettle.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ph.shopping.common.util.core.RespCode;
import com.ph.shopping.common.util.core.ResultUtil;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.common.util.transform.MoneyTransUtil;
import com.ph.shopping.facade.mapper.IBalanceDetailMapper;
import com.ph.shopping.facade.profit.dto.BalanceDetailDTO;
import com.ph.shopping.facade.profit.service.IBalanceDetailService;
import com.ph.shopping.facade.profit.vo.BalanceDetailVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 
 * @ClassName: SupplyChainBalanceServiceImpl
 * @Description: 余额明细实现类
 * @author 王强
 * @date 2017年6月8日 下午2:13:47
 */
@Component
@Service(version = "1.0.0")
public class BalanceDetailServiceImpl implements IBalanceDetailService {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private IBalanceDetailMapper iBalanceDetailMapper;

	@Override
	public Result getBalanceDetail(BalanceDetailDTO balanceDetailDTO) {
		try {
			PageHelper.startPage(balanceDetailDTO.getPageNum(), balanceDetailDTO.getPageSize());
			List<BalanceDetailVO> balanceDetailVOs = (Page<BalanceDetailVO>) iBalanceDetailMapper.getBalanceDetail(balanceDetailDTO);
			PageInfo<BalanceDetailVO> pageInfo = new PageInfo<BalanceDetailVO>(balanceDetailVOs);
			for (BalanceDetailVO balanceDetailVO : pageInfo.getList()) {
				balanceDetailVO.setScore(MoneyTransUtil.stringFormat(MoneyTransUtil.transDivisDouble(balanceDetailVO.getScore1())));
				balanceDetailVO.setHandingCharge(MoneyTransUtil.stringFormat(MoneyTransUtil.transDivisDouble(balanceDetailVO.getHandingCharge1())));
			}
			
			return ResultUtil.getResult(RespCode.Code.SUCCESS, pageInfo.getList(), pageInfo.getTotal());
		} catch (Exception e) {
			logger.error("查询余额明细异常", e);
			return ResultUtil.getResult(RespCode.Code.FAIL, null, 0l);
		}
	}

	@Override
	public List<BalanceDetailVO> getExportData(BalanceDetailDTO balanceDetailDTO) {
		List<BalanceDetailVO> balanceDetailVOs = iBalanceDetailMapper.getBalanceDetail(balanceDetailDTO);
		for (BalanceDetailVO balanceDetailVO : balanceDetailVOs) {
			balanceDetailVO.setScore(MoneyTransUtil.stringFormat(MoneyTransUtil.transDivisDouble(balanceDetailVO.getScore1())));
			balanceDetailVO.setHandingCharge(MoneyTransUtil.stringFormat(MoneyTransUtil.transDivisDouble(balanceDetailVO.getHandingCharge1())));
		}
		return balanceDetailVOs;
	}

}
