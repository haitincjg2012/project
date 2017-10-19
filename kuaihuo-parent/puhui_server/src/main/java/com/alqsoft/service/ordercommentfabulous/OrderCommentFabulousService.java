package com.alqsoft.service.ordercommentfabulous;

import org.alqframework.orm.BaseService;

import com.alqsoft.entity.ordercommentfabulous.OrderCommentFabulous;

public interface OrderCommentFabulousService extends BaseService<OrderCommentFabulous>{
	
	public OrderCommentFabulous getOrderCommentFabulousByCommentIdAndMemberId(Long commentid,Long memberid);

}
