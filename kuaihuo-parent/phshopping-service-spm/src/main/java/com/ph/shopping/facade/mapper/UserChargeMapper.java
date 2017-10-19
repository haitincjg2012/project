package com.ph.shopping.facade.mapper;

import org.apache.ibatis.annotations.Param;

import com.ph.shopping.common.core.base.BaseMapper;
import com.ph.shopping.common.core.customenum.TransCodeEnum;
import com.ph.shopping.facade.spm.dto.UserChargeBackDTO;
import com.ph.shopping.facade.spm.entity.UserCharge;
import com.ph.shopping.facade.spm.vo.CheckPayVO;
import com.ph.shopping.facade.spm.vo.UserChargeVO;

public interface UserChargeMapper extends BaseMapper<UserCharge> {

	/**
	 * 
	 * @Title: updateUserChargeByOrderNo
	 * @Description: 更新用户充值记录状态
	 * @author 王强
	 * @date 2017年6月15日 下午2:04:45
	 * @param userChargeBackDTO
	 * @return
	 */
	int updateUserChargeByOrderNo(UserChargeBackDTO userChargeBackDTO);

	/**
	 * 
	 * @Title: countUserChargeTrade
	 * @Description: 统计是否已经有充值流水
	 * @author 王强
	 * @date 2017年6月15日 下午2:05:38
	 * @param orderNo
	 * @return
	 */
	int countUserChargeTrade(@Param("orderNo") String orderNo);
	
	/**
	 * 
	* @Title: getUserChargeTemp
	* @Description: 获取充值数据
	* @author 王强
	* @date  2017年6月15日 下午3:18:57
	* @param orderNo
	* @return
	 */
	UserChargeVO getUserChargeTemp(@Param("orderNo") String orderNo);
	
	/**
	 * 
	* @Title: getMd5Str
	* @Description: 获取MD5串
	* @author 王强
	* @date  2017年6月15日 下午4:17:55
	* @param orderNo
	* @return
	 */
	CheckPayVO getMd5Str(@Param("orderNo") String orderNo);

	/**
	 * 根据订单号查询充值状态是否充值成功
	 * @param orderNo
	 * @return
	 */
	int selectUserChargeRecordChargeStatus(String orderNo);
	
	/**
	 * 更新用户余额
	 * @param userId
	 * @param userChargeOrder
	 * @param score
	 * @param orderNo
	 * @param l
	 * @param userType
	 */
	void updateUserBalance(@Param("userId") Long userId, TransCodeEnum userChargeOrder, @Param("score") Long score, String orderNo, long l,
			byte userType);
}