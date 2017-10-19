package com.alqsoft.service.ordercomment;

import org.alqframework.orm.BaseService;
import org.alqframework.result.Result;
import org.springframework.data.repository.query.Param;

import com.alqsoft.entity.ordercomment.OrderComment;

public interface OrderCommentService extends BaseService<OrderComment>{
	
	/**
	 * 商品评论---游客给父级评论者评论
	 * @param productid   批发商id
	 * @param masterid	  父级评论id
	 * @param visitorid	  给父级评论的会员id
	 * @param content
	 * @return
	 */
	public Result saveVisitorOrderComment(Long hunterid,Long masterid, Long visitorid, String content);
	
	/**
	 * 商品评论--直接评论
	 * @param productid
	 * @param memberid
	 * @return
	 */
	public Result savaDirectOrderComment(Long orderid,Long memberid,Integer start,String content);

	/**
	 * 判断该会员是否已有过评价此订单商品
	 * @param orderno
	 * @param productid
	 * @param memberid
	 * @return
	 */
	public int getDirectOrderCommentCount(Long orderid,Long memberid);
	
	
	public OrderComment getRowLock(Long id);
	
	/**
	 * 商品评论列表--给父级点赞
	 * @param commentid
	 * @param memberid
	 * @param type
	 * @return
	 */
	public Result saveFabulousOrderComment(Long commentid,Long memberid,Integer type);

	public OrderComment getCommentByOrderId(Long id);
}
