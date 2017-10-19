package com.ph.shopping.facade.mapper;

import java.util.List;

import com.ph.shopping.facade.profit.dto.UnLineSettleDTO;
import com.ph.shopping.facade.profit.vo.UnLineSettleOrderVO;
import com.ph.shopping.facade.profit.vo.UnLineSettleVO;

/**
 * 线下订单结算Mapper
* @ClassName: IUnLineSettleMapper
* @Description: TODO(这里用一句话描述这个类的作用)
* @author Mr.Dong
* @date 2017年6月7日 上午11:15:27
 */
public interface IUnLineSettleMapper {

	/**
	 * 
	* @Title: getUnLineSettleList
	* @Description: TODO(线下结算LIST)
	* @author Mr.Dong
	* @date  2017年6月7日 下午1:54:46
	* @param unLineSettleDTO
	* @return
	 */
	public List<UnLineSettleVO> getUnLineSettleList(UnLineSettleDTO unLineSettleDTO);
	
	/**
	* @Title: getUnLineSettleOrder
	* @Description: TODO(获取线下结算订单)
	* @author Mr.Dong
	* @date  2017年6月8日 上午10:05:40
	* @return
	 */
	public List<UnLineSettleOrderVO>  getUnLineSettleOrder();
	
	
	/**
	 * 
	* @Title: updateUnLineOrderIsSettleBatch
	* @Description: TODO(批量更改线下订单结算表中结算状态)
	* @author Mr.Dong
	* @date  2017年6月8日 上午10:24:21
	* @param unLineSettleOrder
	* @return
	 */
	public int updateUnLineOrderIsSettleBatch(List<UnLineSettleOrderVO> unLineSettleOrder);
	
}
