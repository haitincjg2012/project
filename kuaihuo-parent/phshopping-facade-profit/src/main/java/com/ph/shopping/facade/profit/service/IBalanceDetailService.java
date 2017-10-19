package com.ph.shopping.facade.profit.service;

import java.util.List;

import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.profit.dto.BalanceDetailDTO;
import com.ph.shopping.facade.profit.vo.BalanceDetailVO;

/**
 * 
* @ClassName: ISupplyChainBalanceService
* @Description: 余额明细接口
* @author 王强
* @date 2017年6月8日 下午1:42:25
 */
public interface IBalanceDetailService {
	/**
	 * 
	* @Title: getBalanceDetail
	* @Description:获取余额明细
	* @author 王强
	* @date  2017年6月8日 下午2:13:23
	* @return
	 */
	Result getBalanceDetail(BalanceDetailDTO balanceDetailDTO);

	/**
	 * 
	* @Title: getExportData
	* @Description: 获取导出数据
	* @author 王强
	* @date  2017年6月8日 下午3:41:05
	* @param balanceDetailDTO
	* @return
	 */
	List<BalanceDetailVO> getExportData(BalanceDetailDTO balanceDetailDTO);
}
