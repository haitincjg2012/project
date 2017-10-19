package com.ph.shopping.facade.mapper;

import java.util.List;

import cm.ph.shopping.facade.order.vo.CommentVO;
import org.apache.ibatis.annotations.Param;

import cm.ph.shopping.facade.order.entity.PhMemberOrderOnlineComment;
import cm.ph.shopping.facade.order.vo.OrderOnlineCommentVO;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author: 王雪洋
 * @project: phshopping-service-order
 * @ClassName: IOnlineOrderCommentMapper
 * @Date: 2017年8月23日下午7:51:56
 * @Desc: 订单评论 DAO
 */
@Repository
public interface IOnlineOrderCommentMapper {
	/**
	 * 添加评论
	 * @param params
	 * @return
	 */
	public Integer insertOrderOnlineComment(PhMemberOrderOnlineComment comment);
	
	/**
	 * 
	 * @author:王雪洋
	 * @desc: 根据单个评论ID查询单个评论
	 * @return:PhMemberOrderOnlineComment
	 * @createtime:2017年8月24日 下午5:12:40
	 */
	public PhMemberOrderOnlineComment getOrderCommentById(@Param("commentid") Long commentid);
	/**
	 * 
	 * @author:王雪洋
	 * @desc:查询某个订单下所有评论
	 * @return:List<PhMemberOrderOnlineComment>
	 * @createtime:2017年8月25日 上午8:33:09
	 */
	public List<CommentVO> getOrderCommentsByOrderId(@Param("orderId") Long orderId);
	/**
	 *
	 * @author:王雪洋
	 * @desc:查询某个订单瞎说下会员首次发起的评论内容
	 * @return:List<PhMemberOrderOnlineComment>
	 * @createtime:2017年8月25日 上午8:33:09
	 */
	public OrderOnlineCommentVO getFirstOrderCommentByOrderId(@Param("orderId") Long orderId);
}
