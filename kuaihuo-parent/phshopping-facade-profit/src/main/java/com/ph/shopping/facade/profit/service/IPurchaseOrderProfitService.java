package com.ph.shopping.facade.profit.service;

import com.ph.shopping.common.util.page.PageBean;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.profit.dto.PurchaseOrderProfitDTO;
/**
 * 
 * @ClassName: IPurchaseOrderProfitService
 * @Description: 供应链订单分润接口
 * @author Mr.Dong
 * @date 2017年5月16日 下午4:18:56
 */
public interface IPurchaseOrderProfitService {

	/**
	 * 
	 * @Title: getPurchaseOrderProfitList
	 * @Description: 获取线下订单供应链利润分成列表(分页)
	 * @author Mr.Dong
	 * @date 2017年4月12日 下午4:03:42
	 * @param unlineSupplyChainProfit
	 * @param pagebean
	 * @return
	 */
	public Result getPurchaseOrderProfitList(PurchaseOrderProfitDTO purchaseOrderProfitDTO,PageBean pagebean);
	
	/**
	 * 
	* @Title:getPurchaseOrderProfitEXCEL
	* @Description: 导出供应链订单分润利润分成表EXCEL
	* @author Mr.Dong
	* @date  2017年5月16日 下午5:14:58
	* @return
	 */
	public Result getPurchaseOrderProfitEXCEL(PurchaseOrderProfitDTO purchaseOrderProfitDTO);
	
	/**
	 * 
	* purchaseOrderProfit
	* @Description: 定时器执行供应链订单分润
	* @author Mr.Dong
	* @date  2017年5月16日 下午5:14:58
	* @return
	 */
	public Result purchaseOrderProfit();
}
