package cm.ph.shopping.facade.order.service;

import com.ph.shopping.common.util.result.Result;

import cm.ph.shopping.facade.order.dto.CommentDTO;

/**
 * 
 * @author: 王雪洋
 * @project: phshopping-facade-order
 * @ClassName: IOnlineOrderCommentService
 * @Date: 2017年8月23日下午5:45:06
 * @Desc: 订单评论 统一接口
 */
public interface IOnlineOrderCommentService {
	/**
	 * 用户直接评论商品，商户接单以后才能评论
	 * @param commentDTO {orderid 订单号,memberid 会员ID,start 星星数,content 评论内容}
	 * @return result
	 */
	public Result savaDirectOrderComment(CommentDTO commentDTO);
	
	/**
	 * 
	 * @author:王雪洋
	 * @desc: 评论回复
	 * @param: commentDTO {commentid 评论ID memberid 会员ID content 评论内容}
	 * @return:Result
	 * @createtime:2017年8月24日 下午2:02:41
	 */
	public Result saveReplyComment(CommentDTO commentDTO,String userType);
	/**
	 * 
	 * @author:王雪洋
	 * @desc: 查询单个订单下所有评论信息
	 * @return:List<PhMemberOrderOnlineComment>
	 * @createtime:2017年8月25日 上午9:14:11
	 */
	public Result queryOrderCommentsByOrderId(Long orderId);
	
}
