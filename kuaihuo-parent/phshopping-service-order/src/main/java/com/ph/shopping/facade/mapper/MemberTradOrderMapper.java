package com.ph.shopping.facade.mapper;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import cm.ph.shopping.facade.order.dto.PhMemberOrderUnlineDTO;
import cm.ph.shopping.facade.order.entity.PhMemberOrderUnline;

public interface MemberTradOrderMapper {
	/**
	 * 会员查询交易订单
	 * @param rowBounds 
	 * @param memeberId
	 * @param status
	 * @return
	 */
	List<PhMemberOrderUnlineDTO> getMemberTradOrder(PhMemberOrderUnlineDTO member, RowBounds rowBounds);

}
