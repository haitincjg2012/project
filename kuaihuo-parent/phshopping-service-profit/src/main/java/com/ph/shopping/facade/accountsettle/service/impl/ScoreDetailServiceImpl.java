package com.ph.shopping.facade.accountsettle.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ph.shopping.common.util.core.RespCode;
import com.ph.shopping.common.util.core.ResultUtil;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.common.util.transform.MoneyTransUtil;
import com.ph.shopping.facade.mapper.IScoreDetailMapper;
import com.ph.shopping.facade.profit.dto.ScoreDetailDTO;
import com.ph.shopping.facade.profit.service.IScoreDetailService;
import com.ph.shopping.facade.profit.vo.ScoreDetailVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 
 * @ClassName: ScoreDetailServiceImpl
 * @Description: 积分明细实现类
 * @author 王强
 * @date 2017年6月7日 下午2:01:45
 */
@Component
@Service(version = "1.0.0")
public class ScoreDetailServiceImpl implements IScoreDetailService {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private IScoreDetailMapper iScoreDetailMapper;

	@Override
	public Result getScoreDetail(ScoreDetailDTO scoreDetailDTO) {
		try {
			PageHelper.startPage(scoreDetailDTO.getPageNum(), scoreDetailDTO.getPageSize());
			List<ScoreDetailVO> scoreDetailVOs = (Page<ScoreDetailVO>) iScoreDetailMapper.getScoreDetails(scoreDetailDTO);
			PageInfo<ScoreDetailVO> pageInfo = new PageInfo<ScoreDetailVO>(scoreDetailVOs);
			
			for (ScoreDetailVO scoreDetailVO : pageInfo.getList()) {
				scoreDetailVO
						.setScore(MoneyTransUtil.stringFormat(MoneyTransUtil.transDivisDouble(scoreDetailVO.getScore1())));
				scoreDetailVO.setHandingCharge(
						MoneyTransUtil.stringFormat(MoneyTransUtil.transDivisDouble(scoreDetailVO.getHandingCharge1())));
			}
			
			return ResultUtil.getResult(RespCode.Code.SUCCESS, pageInfo.getList(), pageInfo.getTotal());
		} catch (Exception e) {
			logger.error("查询积分明细异常", e);
			return ResultUtil.getResult(RespCode.Code.FAIL, null, 0l);
		}
	}

	@Override
	public List<ScoreDetailVO> getExportData(ScoreDetailDTO scoreDetailDTO) {
		List<ScoreDetailVO> scoreDetailVOs = iScoreDetailMapper.getScoreDetails(scoreDetailDTO);
		for (ScoreDetailVO scoreDetailVO : scoreDetailVOs) {
			scoreDetailVO
					.setScore(MoneyTransUtil.stringFormat(MoneyTransUtil.transDivisDouble(scoreDetailVO.getScore1())));
			scoreDetailVO.setHandingCharge(
					MoneyTransUtil.stringFormat(MoneyTransUtil.transDivisDouble(scoreDetailVO.getHandingCharge1())));
		}
		
		return scoreDetailVOs;
	}
}
