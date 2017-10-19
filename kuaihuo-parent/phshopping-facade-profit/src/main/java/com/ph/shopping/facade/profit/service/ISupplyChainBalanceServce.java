package com.ph.shopping.facade.profit.service;

import java.util.List;

import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.profit.dto.SupplyChainBalanceDTO;
import com.ph.shopping.facade.profit.vo.SupplyChainBalanceVO;

/**
 * 
* @ClassName: ISupplyChainBalanceServce
* @Description: 供链余额接口
* @author 王强
* @date 2017年6月9日 下午12:24:19
 */
public interface ISupplyChainBalanceServce {
	/**
	 * 
	* @Title: getSupplyChainBalanceList
	* @Description: 获取供链余额列表
	* @author 王强
	* @date  2017年6月9日 下午12:25:36
	* @return
	 */
	Result getSupplyChainBalanceList(SupplyChainBalanceDTO supplyChainBalanceDTO);

	/**
	 * 
	* @Title: getExportData
	* @Description: 导出数据
	* @author 王强
	* @date  2017年6月9日 下午4:04:57
	* @param balanceDetailDTO
	* @return
	 */
	List<SupplyChainBalanceVO> getExportData(SupplyChainBalanceDTO supplyChainBalanceDTO);
}
