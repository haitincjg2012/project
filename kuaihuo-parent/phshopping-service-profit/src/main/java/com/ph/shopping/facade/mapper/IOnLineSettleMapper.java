package com.ph.shopping.facade.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ph.shopping.facade.profit.dto.OnLineSettleDTO;
import com.ph.shopping.facade.profit.vo.OnLineSettleVO;

/**
 * 
* @ClassName: IOnLineSettleMapper
* @Description: 线上结算mapper
* @author 王强
* @date 2017年6月6日 上午11:05:34
 */
public interface IOnLineSettleMapper {
	/**
	 * 
	* @Title: getOnLineSettles
	* @Description: 获取线上订单结算数据
	* @author 王强
	* @date  2017年6月6日 上午11:07:35
	* @return
	 */
	List<OnLineSettleVO> getOnLineSettles(OnLineSettleDTO lineSettleDTO);
	/**
	 * 
	* @Title: updOrderIsSettle
	* @Description: 批量更新线上订单状态
	* @author 王强
	* @date  2017年6月7日 上午11:05:17
	* @param itemm
	* @return
	 */
	int updOrderIsSettle(List<String> item);
	
	/**
	 * 
	* @Title: updateSingleIsSettle
	* @Description: 单条更新
	* @author 王强
	* @date  2017年7月3日 下午5:31:11
	* @param orderNo
	* @return
	 */
	int updateSingleIsSettle(@Param("orderNo") String orderNo);
}
