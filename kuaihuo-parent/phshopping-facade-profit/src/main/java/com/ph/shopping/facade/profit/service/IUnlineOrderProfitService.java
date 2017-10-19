package com.ph.shopping.facade.profit.service;

import com.ph.shopping.common.util.page.PageBean;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.profit.dto.UnLineOrderProfitDTO;

/**
 * 
 * @ClassName: IUnlineOrderProfitService
 * @Description: 线下订单分润接口
 * @author Mr.Dong
 * @date 2017年5月22日 下午4:18:56
 */
public interface IUnlineOrderProfitService {
	
	/**
	 * 
	 * @Title: unLineOrderProfitList
	 * @Description: 线下订单分润list
	 * @author 
	 * @date 2017年5月22日 下午4:16:44
	 * @param unLineOrderProfitDTO
	 * @param pageBean
	 * @return
	 */
	public Result unLineOrderProfitList(UnLineOrderProfitDTO unLineOrderProfitDTO,PageBean pageBean);
	
	/**
	 * 
	 * @Title: getUnLineOrderProfitEXCEL
	 * @Description: 导出线下订单分润EXCEL(导出当前查询条件下的全部)
	 * @author Mr.Dong
	 * @date 2017年5月22日 下午4:16:44
	 * @param unLineOrderProfitDTO
	 * @return/
	 */
	public Result getUnLineOrderProfitEXCEL(UnLineOrderProfitDTO unLineOrderProfitDTO);
	
	/**
	* @Title: UnLineOrderProfit
	* @Description:线下订单分润定时器
	* @author Mr.Dong
	* @date  2017年5月23日 上午9:32:45
	* @return
	 */
	public  Result  UnLineOrderProfit();

	/**
	 * @Description:订单返还积分
	 * @return
	 */
	public Result doBackScore();
	/**
	 * 分润
	 */
	public Result OrderProfit(String orderNo);


}
