package com.ph.shopping.facade.profit.service;

import java.util.List;

import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.profit.dto.OnLineSettleDTO;
import com.ph.shopping.facade.profit.vo.OnLineSettleVO;

/**
 * 
* @ClassName: IOnLineSettleService
* @Description: 线上结算
* @author 王强
* @date 2017年6月5日 下午4:11:18
 */
public interface IOnLineSettleService {
	/**
	 * 
	* @Title: getOnLineSettleList
	* @Description: 增加线上订单结算
	* @author 王强
	* @date  2017年6月6日 上午11:04:38
	* @return
	 */
	Result queryOnlineSettles(OnLineSettleDTO lineSettleDTO);
	/**
	 * 
	* @Title: updateOnLineOrderIsSettle
	* @Description: 批量更新订单结算状态
	* @author 王强
	* @date  2017年6月7日 上午11:11:14
	* @param dtos
	* @return
	 */
	int updateOnLineOrderIsSettle(List<String> dtos);
	/**
	 * 
	* @Title: getScoreDetailExport
	* @Description: 导出数据
	* @author 王强
	* @date  2017年6月8日 上午10:27:56
	* @param lineSettleDTO
	* @return
	 */
	List<OnLineSettleVO> getExportData(OnLineSettleDTO lineSettleDTO);
	
	/**
	 * 
	* @Title: updateOnLineOrderSingleIsSettle
	* @Description: 单条更新记录
	* @author 王强
	* @date  2017年7月3日 下午5:32:32
	* @param orderNo
	 */
	int updateOnLineOrderSingleIsSettle(String orderNo);
}
