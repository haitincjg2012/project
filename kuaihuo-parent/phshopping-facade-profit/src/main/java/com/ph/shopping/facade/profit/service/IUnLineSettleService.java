package com.ph.shopping.facade.profit.service;

import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.profit.dto.UnLineSettleDTO;
import com.ph.shopping.facade.profit.vo.UnLineSettleOrderVO;

/**
 * 线下订单结算接口
* @ClassName: IUnLineSettleService
* @Description: TODO(这里用一句话描述这个类的作用)
* @author Mr.Dong
* @date 2017年6月7日 上午11:12:26
 */
public interface IUnLineSettleService {

	/**
	* @Title: getUnLineSettleList
	* @Description: TODO(线下订单结算list)
	* @author Mr.Dong
	* @date  2017年6月7日 上午11:38:25
	* @param unLineSettleDTO
	* @return
	 */
	public Result getUnLineSettleList(UnLineSettleDTO unLineSettleDTO);
	
	/**
	 * 导出线下结算EXCEL
	* @Title: getUnLineSettleEXCEL
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @author Mr.Dong
	* @date  2017年6月7日 下午5:40:45
	* @param unLineSettleDTO
	* @return
	 */
	public Result getUnLineSettleEXCEL(UnLineSettleDTO unLineSettleDTO);
	
	
	/**
	 * 线下结算主方法
	* @Title: doUnLineSettle
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @author Mr.Dong
	* @date  2017年6月8日 上午9:53:46
	* @return
	 */
	public Result doUnLineSettle();
	
	
	/**
	 * 线下订单立即结算 2017-7-31 新需求
	* @Title: doUnLineSettleNow
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @author Mr.Dong
	* @date  2017年7月31日 下午4:40:06
	* @param unLineSettleOrderVO
	* @return
	 */
	public  Result doUnLineSettleNow(UnLineSettleOrderVO unLineSettleOrderVO) throws Exception;
	
}
