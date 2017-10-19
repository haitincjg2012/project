package cm.ph.shopping.facade.order.service;

import com.ph.shopping.common.util.result.Result;

import cm.ph.shopping.facade.order.dto.PhMemberOrderUnlineDTO;

/**
 * @date 2017-08-26 17:30
 * @author 李治桦
 * 会员交易订单
 */
public interface MemberTradOrderService {
	/*
	 * 会员获取交易订单列表
	 */
	Result getMemberTradOrder(PhMemberOrderUnlineDTO member, int page, int pageSize);



}
