package com.phshopping.api.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ph.shopping.common.util.result.Result;

import cm.ph.shopping.facade.order.dto.CommentDTO;
import cm.ph.shopping.facade.order.service.IOnlineOrderCommentService;

/**
 * 
 * @author: 王雪洋
 * @project: phshopping-app-api-merchant
 * @ClassName: OrderOnlineCommentController
 * @Date: 2017年8月24日上午8:38:19
 * @Desc:  订单评论
 */
@RestController
@RequestMapping("api/orderOnlineComment")
public class OrderOnlineCommentController {
	
	@Reference(version = "1.0.0")
	private IOnlineOrderCommentService onlineOrderCommentService;
	
	/**
	 * 
	 * @author:王雪洋
	 * @desc: 会员直接发起订单评论
	 * @return:Result
	 * @createtime:2017年8月24日 上午9:23:06
	 * @param commentDTO {orderid 订单号,memberid 会员ID,start 星星数,content 评论内容}
	 */
	
	@RequestMapping(value = "/orderdirectcomment" , method = RequestMethod.GET)
	@ResponseBody
	public Result orderdirectcomment(CommentDTO commentDTO){
		return onlineOrderCommentService.savaDirectOrderComment(commentDTO);
	}
	/**
	 * 
	 * @author:王雪洋
	 * @desc:回复评论
	 * @param: userType {}
	 * @param: commentDTO {commentid 评论ID memberid 会员ID content 评论内容}
	 * @return:Result
	 * @createtime:2017年8月24日 下午8:55:01
	 */
	@RequestMapping(value = "/addComment/{userType}" , method = RequestMethod.GET)
	@ResponseBody
	public Result addOrderComment(@PathVariable String userType , CommentDTO commentDTO){
		return onlineOrderCommentService.saveReplyComment(commentDTO,userType);
	}
	
	/**
	 * @desc:获取某个订单下所有的评价信息
	 * @return:Result
	 * @createtime:2017年8月24日 下午8:55:01
	 */
	@RequestMapping(value = "merchant/findComments" , method = RequestMethod.GET)
	@ResponseBody
	public Result findOrderCommentsByOrderId(Long orderId){
		return onlineOrderCommentService.queryOrderCommentsByOrderId(orderId);
	}
}
