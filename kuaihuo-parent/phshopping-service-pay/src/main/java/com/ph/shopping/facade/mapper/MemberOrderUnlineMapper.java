package com.ph.shopping.facade.mapper;

import cm.ph.shopping.facade.order.entity.PhMemberOrderUnline;
import com.ph.shopping.common.core.base.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

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
@Repository
public interface MemberOrderUnlineMapper extends BaseMapper<PhMemberOrderUnline>{
	
	/**
	 * 根据订单号获取记录
	 * @param orderNum
	 */
	public PhMemberOrderUnline getMemberOrderByOrderNum(@Param("orderNum") String orderNum);

	/**
	 * 根据订单号获取记录
	 * @param orderNum
	 */
	public PhMemberOrderUnline getMemberOrderByOrderNumAndStatus(@Param("orderNum") String orderNum);

	/**
	 * 根据订单编号 获取订单金额
	 */
	Long getOrderMoney(@Param("orderNo") String orderNo);
}
