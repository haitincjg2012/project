package com.ph.shopping.facade.mapper;

import org.apache.ibatis.annotations.Param;

import com.ph.shopping.common.core.base.BaseMapper;

import cm.ph.shopping.facade.order.entity.PhOrderOnline;

/**
 * @项目：phshopping-service-pay
 *
 * @描述：会员订单支付mapper
 *
 * @作者： Mr.chang
 *
 * @创建时间：2017年3月30日
 *
 * @Copyright @2017 by Mr.chang
 */
public interface MemberOrderMapper extends BaseMapper<PhOrderOnline>{
	
	/**
	 * 根据订单号获取记录
	 * @param orderNum
	 */
	public PhOrderOnline getMemberOrderByOrderNum(@Param("orderNum")String orderNum);

}
