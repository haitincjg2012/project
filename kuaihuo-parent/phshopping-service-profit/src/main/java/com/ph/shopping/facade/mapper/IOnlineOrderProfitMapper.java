package com.ph.shopping.facade.mapper;

import java.util.List;
import com.ph.shopping.common.core.base.BaseMapper;
import com.ph.shopping.facade.profit.dto.OnlineOrderProfitDTO;
import com.ph.shopping.facade.profit.entity.OnlineOrderProfit;
import com.ph.shopping.facade.profit.vo.OnLineOrderVO;
import com.ph.shopping.facade.profit.vo.OnlineOrderProfitVO;

/**
 * 
* @ClassName: IOnlineOrderProfitMapper
* @Description: 线上订单分润mapper
* @author Mr.Dong
* @date 2017年5月9日 下午5:11:45
 */
public interface IOnlineOrderProfitMapper extends BaseMapper<OnlineOrderProfit> {

	/**
	* @Title: getUnlineOrderSupplyChainProfitList
	* @Description: 获取线下订单供应链利润分成列表
	* @author Mr.Dong
	* @date  2017年4月26日 下午2:14:10
	* @param onlineOrderProfitDTO
	* @return List<OnlineOrderProfitVO>
	 */
	public List<OnlineOrderProfitVO> getOnLineOrderProfitList(OnlineOrderProfitDTO onlineOrderProfitDTO); 
	
	/**
	 * 
	* @Title: getOnlineOrderList
	* @Description: TODO(获取线上订单记录用于分润)
	* @author Mr.Dong
	* @date  2017年5月26日 下午5:48:43
	* @return
	 */
	public List<OnLineOrderVO> getOnlineOrderList();
	
	/**
	 * 
	* @Title: insertOnLineOrderProfitBatch
	* @Description: TODO(批量插入线上订单分润记录表)
	* @author Mr.Dong
	* @date  2017年5月27日 下午4:59:58
	* @param onlineOrderProfitList
	* @return
	 */
	public int insertOnLineOrderProfitBatch(List<OnlineOrderProfit> onlineOrderProfitList);
	
	/**
	 * 
	* @Title: updateOnLineOrderBonus
	* @Description: TODO(批量更改线上订单子订单表中的分润状态)
	* @author Mr.Dong
	* @date  2017年5月27日 下午5:21:46
	* @param onlineOrderList
	* @return
	 */
	public int updateOnLineOrderBonus(List<OnLineOrderVO> onlineOrderList);
	
}
