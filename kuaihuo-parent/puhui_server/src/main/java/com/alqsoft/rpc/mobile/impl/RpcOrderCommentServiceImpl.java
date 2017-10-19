package com.alqsoft.rpc.mobile.impl;

import org.alqframework.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.alqsoft.rpc.mobile.RpcOrderCommentService;
import com.alqsoft.service.ordercomment.OrderCommentService;

/**
 * 商品订单评论
 * @author Administrator
 *
 */
@Controller
public class RpcOrderCommentServiceImpl implements RpcOrderCommentService{

	@Autowired
	private OrderCommentService orderCommentService;

	/**
	 * 游客对父级商品评论者评论
	 */
	@Override
	public Result saveVisitorOrderComment(Long hunterid,Long masterid, Long visitorid, String content) {
		// TODO Auto-generated method stub
		return orderCommentService.saveVisitorOrderComment(hunterid,masterid, visitorid, content);
	}

	/**
	 * 用户直接给商品评论
	 */
	@Override
	public Result savaDirectOrderComment(Long orderid,Long memberid, Integer start, String content) {
		// TODO Auto-generated method stub
		return orderCommentService.savaDirectOrderComment(orderid, memberid, start, content);
	}

	/**
	 * 商品评论列表----给父级评论点赞
	 */
	@Override
	public Result saveFabulousOrderComment(Long commentid, Long memberid, Integer type) {
		// TODO Auto-generated method stub
		return orderCommentService.saveFabulousOrderComment(commentid,memberid,type);
	}
	
	

}
