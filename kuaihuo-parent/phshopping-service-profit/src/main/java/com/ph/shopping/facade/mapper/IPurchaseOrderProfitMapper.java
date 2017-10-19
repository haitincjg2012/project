package com.ph.shopping.facade.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ph.shopping.facade.profit.dto.PurchaseOrderProfitDTO;
import com.ph.shopping.facade.profit.entity.PurchaseOrderProfit;
import com.ph.shopping.facade.profit.vo.PositionVO;
import com.ph.shopping.facade.profit.vo.PurchaseOrderProfitVO;
import com.ph.shopping.facade.profit.vo.PurchaseOrderVO;
import com.ph.shopping.facade.profit.vo.UserBalanceRecordVO;
import com.ph.shopping.facade.profit.vo.UserBalanceVO;
public interface IPurchaseOrderProfitMapper {

	/**
	 * 
	* @Title: getPurchaseOrderProfitList
	* @Description: 获取线下订单供应链利润分成列表
	* @author Mr.Dong
	* @date  2017年4月26日 下午2:14:10
	* @param orderNo
	* @param  orderType
	* @return List<UnlineSupplyChainProfit>
	 */
	public List<PurchaseOrderProfitVO> getPurchaseOrderProfitList(PurchaseOrderProfitDTO purchaseOrderProfitDTO); 
		
	/**
	 * 
	* @Title: getUnlineOrderSupplyChainProfitList
	* @Description: 取供应链订单过来分润
	* @author Mr.Dong
	* @date  2017年5月17日 下午2:14:10
	* @return List<PurchaseOrderVO>
	 */
	public List<PurchaseOrderVO> getPurchaseOrderList();
	/**
	 * 
	* @Title: insertPurchaseOrderProfit
	* @Description: 接下来执行分润 将数据插入 ph_purchase_order_profit 表
	* @author Mr.Dong
	* @date  2017年4月26日 下午2:14:10
	* @param purchaseOrderProfitList
	* @return void
	 */
	public int  insertPurchaseOrderProfit(List<PurchaseOrderProfit> purchaseOrderProfitList);
	
	
	/**
	 * 
	* @Title: updatePurchaseOrderBonus
	* @Description: 接下来更改 ph_purchase_sub_order 字段bonus为2(已分润)
	* @author Mr.Dong
	* @date  2017年5月17日 下午2:14:10
	* @param purchaseOrderList
	* @return void
	 */
	public int  updatePurchaseOrderBonus(List<PurchaseOrderVO> purchaseOrderList);
	
	
	/**
	 * 
	* @Title: updateUserBalance
	* @Description: 修改用户余额记录
	* @author Mr.Dong
	* @date  2017年5月5日 下午2:14:10
	* @param userBalanceVO
	* @return int
	 */
	public int updateUserBalance(UserBalanceVO userBalanceVO);
	
	/**
	 * 
	* @Title: insertUserBalanceRecordBatch
	* @Description: 批量新增用户余额流水表
	* @author Mr.Dong
	* @date  2017年5月5日 下午2:14:10
	* @param userBalanceRecordVoList
	* @return int
	 */
	public int insertUserBalanceRecordBatch(List<UserBalanceRecordVO> userBalanceRecordVoList);
	
	/**
	 * 通过主键获取区域
	* @Title: getPositionById
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @author Mr.Dong
	* @date  2017年6月13日 下午5:24:25
	* @param id
	* @return
	 */
	public PositionVO getPositionById(@Param("id") Long id);
}
