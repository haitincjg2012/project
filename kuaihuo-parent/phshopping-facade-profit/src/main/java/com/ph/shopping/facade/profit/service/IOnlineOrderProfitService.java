package com.ph.shopping.facade.profit.service;

import com.ph.shopping.common.util.page.PageBean;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.profit.dto.OnlineOrderProfitDTO;
/**
 * 
 * @ClassName: IOnlineOrderProfitService
 * @Description: 线上订单分润接口
 * @author Mr.Dong
 * @date 2017年5月9日 下午4:18:56
 */
public interface IOnlineOrderProfitService {

	/**
	 * 
	 * @Title: getOnLineOrderProfitList
	 * @Description: 获取线上订单分润list
	 * @author Mr.Dong
	 * @date 2017年5月9日 下午4:03:42
	 * @param onlineOrderProfitDTO
	 * @param pagebean
	 * @return
	 */
	public Result getOnLineOrderProfitList(OnlineOrderProfitDTO onlineOrderProfitDTO, PageBean pagebean);
	
	/**
	 * 
	* @Title: getOnLineOrderProfitEXCEL
	* @Description: TODO(线上订单导出excel)
	* @author Mr.Dong
	* @date  2017年5月31日 上午10:29:22
	* @param onlineOrderProfitDTO
	* @return
	 */
	public Result getOnLineOrderProfitEXCEL(OnlineOrderProfitDTO onlineOrderProfitDTO);
	
	/**
	 * 
	* @Title: OnLineOrderProfit
	* @Description: TODO(线上订单分润)
	* @author Mr.Dong
	* @date  2017年5月26日 下午4:56:41
	* @return
	 */
	public Result OnLineOrderProfit();
}
