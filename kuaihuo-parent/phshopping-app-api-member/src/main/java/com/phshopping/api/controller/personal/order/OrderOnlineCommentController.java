package com.phshopping.api.controller.personal.order;

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
 * <pre>项目名称：phshopping-app-api-member    
 * 类名称：OrderOnlineCommentController    
 * 类描述：会员回复评论    
 * 创建人：王雪洋    
 * 创建时间：2017年8月27日 下午10:29:18         
 * @version </pre>
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
	 * @desc:会员回复评论
	 * @param: userType {}
	 * @param: commentDTO {commentid 评论ID memberid 会员ID content 评论内容}
	 * @return:Result
	 * @createtime:2017年8月24日 下午8:55:01
	 */
	@RequestMapping(value = "/addComment/member" , method = RequestMethod.GET)
	@ResponseBody
	public Result addOrderComment(CommentDTO commentDTO){
		return onlineOrderCommentService.saveReplyComment(commentDTO,"member");
	}
	
	/**
	 * @desc:获取某个订单下所有的评价信息
	 * @return:Result
	 * @createtime:2017年8月24日 下午8:55:01
	 */
	@RequestMapping(value = "member/findComments" , method = RequestMethod.GET)
	@ResponseBody
	public Result findOrderCommentsByOrderId(Long orderId){
		return onlineOrderCommentService.queryOrderCommentsByOrderId(orderId);
	}
}
