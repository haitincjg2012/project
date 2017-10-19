package com.ph.shopping.facade.mapper;

import org.apache.ibatis.annotations.Param;

import com.ph.shopping.common.core.base.BaseMapper;
import com.ph.shopping.facade.spm.entity.UserBalance;

/**
 * @项目：phshopping-service-pay
 *
 * @描述：用户余额mapper
 *
 * @作者： Mr.chang
 *
 * @创建时间：2017年3月30日
 *
 * @Copyright @2017 by Mr.chang
 */
public interface UserBalanceMapper extends BaseMapper<UserBalance>{
	
	/**
	 * 获取商户余额
	 * @param userId
	 * @return
	 */
	public UserBalance getUserBalance(@Param("userId")Long userId);

}
