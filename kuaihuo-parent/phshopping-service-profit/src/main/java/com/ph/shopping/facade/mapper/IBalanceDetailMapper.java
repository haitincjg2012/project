package com.ph.shopping.facade.mapper;

import java.util.List;

import com.ph.shopping.facade.profit.dto.BalanceDetailDTO;
import com.ph.shopping.facade.profit.vo.BalanceDetailVO;

/**
 * 
* @ClassName: IScoreDetailMapper
* @Description: 积分明细mapper
* @author 王强
* @date 2017年6月6日 上午11:05:34
 */
public interface IBalanceDetailMapper {
	/**
	 * 
	* @Title: getScoreDetails
	* @Description: 获取余额明细
	* @author 王强
	* @date  2017年6月8日 下午2:16:09
	* @return
	 */
	List<BalanceDetailVO> getBalanceDetail(BalanceDetailDTO balanceDetailDTO);
}
