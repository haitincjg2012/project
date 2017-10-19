package com.ph.shopping.facade.profit.service;

import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.profit.dto.PurchaseSettleDTO;

/**
 * 供应链结算接口
* @ClassName: IPurchaseSettleService
* @Description: TODO(这里用一句话描述这个类的作用)
* @author Mr.Dong
* @date 2017年6月8日 下午5:21:19
 */
public interface IPurchaseSettleService {

	/**
	 * 供应链结算list
	* @Title: getPurchaseSettleList
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @author Mr.Dong
	* @date  2017年6月9日 下午3:33:55
	* @param purchaseSettleDTO
	* @return
	 */
	public Result getPurchaseSettleList(PurchaseSettleDTO purchaseSettleDTO); 
	
	/**
	 * 导出供应链结算EXCEL
	* @Title: getPurchaseSettleEXCEL
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @author Mr.Dong
	* @date  2017年6月9日 下午3:42:39
	* @param purchaseSettleDTO
	* @return
	 */
	public Result getPurchaseSettleEXCEL(PurchaseSettleDTO purchaseSettleDTO);
	
	/**
	 * 供应链订单结算主方法
	* @Title: doPurchaseSettle
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @author Mr.Dong
	* @date  2017年6月11日 下午12:11:10
	* @return
	 */
	public Result doPurchaseSettle();
	
}
