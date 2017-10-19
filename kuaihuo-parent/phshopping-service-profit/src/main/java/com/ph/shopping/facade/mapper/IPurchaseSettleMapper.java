package com.ph.shopping.facade.mapper;

import java.util.List;

import com.ph.shopping.facade.profit.dto.PurchaseSettleDTO;
import com.ph.shopping.facade.profit.vo.PurchaseSettleOrderVO;
import com.ph.shopping.facade.profit.vo.PurchaseSettleVO;

/**
 * 供应链结算Mapper
* @ClassName: PurchaseSettleMapper
* @Description: TODO(这里用一句话描述这个类的作用)
* @author Mr.Dong
* @date 2017年6月8日 下午5:23:55
 */
public interface IPurchaseSettleMapper {

	/**
	 * 获取供应链结算list
	* @Title: getPurchaseSettleList
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @author Mr.Dong
	* @date  2017年6月9日 下午3:27:54
	* @param purchaseSettleDTO
	* @return
	 */
	public List<PurchaseSettleVO> getPurchaseSettleList(PurchaseSettleDTO purchaseSettleDTO);
	
	/**
	 * 需要结算的供应链订单
	* @Title: getPurchaseSettleOrder
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @author Mr.Dong
	* @date  2017年6月11日 下午12:31:48
	* @return
	 */
	public List<PurchaseSettleOrderVO> getPurchaseSettleOrder();
	
	/**
	 * 批量更改供应链订单结算状态
	* @Title: updatePurchaseOrderIsSettleBatch
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @author Mr.Dong
	* @date  2017年6月11日 下午2:47:11
	* @param purchaseSettleOrder
	* @return
	 */
	public int updatePurchaseOrderIsSettleBatch(List<PurchaseSettleOrderVO> purchaseSettleOrder);
}
