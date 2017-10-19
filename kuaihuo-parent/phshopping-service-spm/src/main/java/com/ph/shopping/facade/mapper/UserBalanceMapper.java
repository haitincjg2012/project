package com.ph.shopping.facade.mapper;

import java.util.List;

import com.ph.shopping.facade.spm.dto.UserDrawCashDTO;
import org.apache.ibatis.annotations.Param;

import com.ph.shopping.common.core.base.BaseMapper;
import com.ph.shopping.facade.spm.entity.UserBalance;
import com.ph.shopping.facade.spm.entity.UserBalanceRecord;
import com.ph.shopping.facade.spm.vo.BalanceVO;
import com.ph.shopping.facade.spm.vo.TransCodeVO;
import com.ph.shopping.facade.spm.vo.UserBalanceTradeVO;

/**
 * 
 * @ClassName: UserBalanceMapper
 * @Description: 用户余额mapper
 * @author 王强
 * @date 2017年5月26日 上午9:23:27
 */
public interface UserBalanceMapper extends BaseMapper<UserBalance> {
	/**
	 * 
	 * @Title: updateUserBalance
	 * @Description:更新用户余额
	 * @author 王强
	 * @date 2017年5月26日 上午9:23:56
	 * @param userId
	 * @return
	 */
	int updateUserBalance(@Param("userId") Long userId, @Param("score") Long score) throws Exception;

	/**
	 * 
	 * @Title: insertUserBalanceRecord
	 * @Description: 新增余额流水
	 * @author 王强
	 * @date 2017年5月26日 上午9:25:03
	 * @param userBalanceRecord
	 *            余额流水实体
	 * @return
	 * @throws Exception
	 */
	int insertUserBalanceRecord(UserBalanceRecord userBalanceRecord) throws Exception;

	/**
	 * 
	 * @Title: getUserBalance
	 * @Description: 获取用户余额及支付密码
	 * @author 王强
	 * @date 2017年6月2日 上午11:19:15
	 * @param userId
	 *            用户id
	 * @return
	 */
	BalanceVO getUserBalance(@Param("userId") Long userId);

	/**
	 * 
	 * @Title: getUserBalanceTrade
	 * @Description: 查询用户余额流水列表
	 * @author 王强
	 * @date 2017年6月2日 上午11:18:25
	 * @param userId
	 *            用户id
	 * @param startDate
	 *            开始时间
	 * @param endDate
	 *            结束时间
	 * @return
	 */
	List<UserBalanceTradeVO> getUserBalanceTrade(@Param("userId") Long userId, @Param("startDate") String startDate,
			@Param("endDate") String endDate, @Param("transCode") Integer transCode,@Param("orderNo")String orderNo);

	/**
	 * 
	 * @Title: getTransCode
	 * @Description: 获取交易码
	 * @author 王强
	 * @date 2017年6月2日 下午4:05:34
	 * @return
	 */
	List<TransCodeVO> getTransCode();

	/**
	 * 
	 * @Title: frozenOrUnFrozenBalance
	 * @Description: 解冻或冻结用户余额
	 * @author 王强
	 * @date 2017年6月11日 下午3:07:06
	 * @param balanceId 用户余额表id
	 * @param status 状态值
	 * @return
	 */
	int frozenOrUnFrozenBalance(@Param("balanceId") Long balanceId, @Param("status") Integer status);
	
	/**
	 * 
	* @Title: delUserBalance
	* @Description: 删除余额(物理删除)
	* @author 王强
	* @date  2017年6月16日 上午11:57:09
	* @param userId
	* @return
	 */
	int delUserBalance(@Param("userId") Long userId);

	// 查询用户信息
	UserDrawCashDTO getUserInfo(Long userId);

	Long getOrderTotalPrice(String orderNo);
}
