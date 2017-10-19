package com.ph.shopping.facade.spm.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.ph.shopping.common.core.customenum.TransCodeEnum;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.spm.dto.UserBalanceTradeDTO;
import com.ph.shopping.facade.spm.vo.TransCodeVO;


/**
 * 
 * @ClassName: IUserBalance
 * @Description: 余额流水记录接口
 * @author 王强
 * @date 2017年5月25日 下午5:57:52
 */
public interface IUserBalanceService {

	/**
	 * 
	* @Title: userBalanceTrade
	* @Description: 余额流水记录入口及更新用户余额
	* @author 王强
	* @date  2017年5月27日 上午11:58:15
	* @param userId 用户id(商户、代理商、供应商) 
	* @param codeEnum 交易枚举TransCodeEnum(里面定义自己的交易类型)
     * @param score 交易金额 (需要乘以10000)
     * @param orderNo 订单号
     * @param handingCharge 手续费
	* @param userType 用户角色
	* @return
	* @throws Exception
	 */
	public int userBalanceTrade(Long userId, TransCodeEnum codeEnum, Long score, String orderNo, Long handingCharge,
			Byte userType) throws Exception;

	/**
	 * 
	 * @Title: getUserBalance
	 * @Description: 获取用户余额
	 * @author 王强
	 * @date 2017年5月17日 上午10:09:34
	 * @return
	 */
	public Result getUserBalance(Long userId);
	
	/**
	 * 
	* @Title: getUserBalanceTrade
	* @Description: 获取用户余额流水列表
	* @author 王强
	* @date  2017年6月2日 上午10:56:38
	* @return
	 */
	public Result getUserBalanceTrade(UserBalanceTradeDTO userBalanceTradeDTO);
	
	/**
	 * 
	* @Title: getTransCodes
	* @Description: 获取交易码
	* @author 王强
	* @date  2017年6月2日 下午4:06:35
	* @return
	 */
	public List<TransCodeVO> getTransCodes();
	
	/**
	 * 
	* @Title: updateBalanceStatus
	* @Description: 冻结与解冻余额
	* @author 王强
	* @date  2017年6月9日 下午10:39:02
	* @return
	 */
	public Result updateBalanceStatus(Long balanceId, Integer status);
	
	/**
	 * 
	* @Title: deleteUserBalance
	* @Description:删除用户余额
	* @author 王强
	* @date  2017年6月16日 下午12:25:04
	* @param userId
	* @return
	 */
	Result deleteUserBalance(Long userId);
	/**
	 * 
	* @Title: aaa  
	* @Description: 线下订单获取余额、更新余额事物改造
	* @param @param pwd
	* @param @param merchantId
	* @param @param serviceCost
	* @param @param orderNo
	* @param @return
	* @param @throws Exception   
	* @return Result  
	* @author 李治桦
	 */
	public Result getAndUpdateBalance( String pwd, Long merchantId,Long serviceCost, String orderNo ) throws Exception;

	/**
	 * 线上充值获取余额以及更新余额
	 * @param userId
	 * @param userChargeOrder
	 * @param score
	 * @param orderNo
	 * @param l
	 * @param userType
	 * @return
	 * @throws Exception 
	 * @author 赵俊彪
	 */
	public Result getAndUpdateBalanceRecord(long userId, TransCodeEnum userChargeOrder, long score, String orderNo,
			long l, byte userType) throws Exception;
	
}
