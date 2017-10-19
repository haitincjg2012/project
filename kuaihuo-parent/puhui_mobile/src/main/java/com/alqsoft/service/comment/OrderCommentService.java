package com.alqsoft.service.comment;

import java.util.List;
import java.util.Map;

import org.alqframework.result.Result;

import com.alqsoft.entity.ordercomment.OrderComment;

import static org.bouncycastle.asn1.x500.style.RFC4519Style.member;

public interface OrderCommentService {
	
	/**
	 * 商品评价列表
	 * @param productid
	 * @param type
	 * @param page
	 * @param rows
	 * @return
	 */
	public Result findOrderCommentList(Long productid,Integer type,Integer page,Integer rows,Long memberid);
	
	/**
	 * 批发商评论父级列表
	 * @param params
	 * @return
	 */
	public List<Map<String,Object>> findOderCommentForParentList(Map<String,Object> params);
	
	/**
	 * 批发商评论父级列表数量,也是该批发商的评论总数量
	 * @param params
	 * @return
	 */
	public int getOrderCommentListForParentCount(Long productid);
	
	/**
	 * 批发商评论该父级下的子评论by父级评论的id
	 * @param params
	 * @return
	 */
	public List<Map<String,Object>> findSonOrderCommentForByParentIdList(Long parentId);
	
	/**
	 * 用户直接评论商品，只要确认收货以后才能评论
	 * @param productid
	 * @param memberid
	 * @param start
	 * @param content
	 * @return
	 */
	public Result savaDirectOrderComment(Long orderid, Long memberid, Integer start, String content);
	
	/**
	 * 商品评论---游客给父级评论者评论
	 * @param masterid	  父级评论id
	 * @param visitorid	  给父级评论的会员id
	 * @param content
	 * @return
	 */
	public Result saveVisitorOrderComment(Long hunterid,Long masterid, Long visitorid, String content);
	
	
	/**
	 * 查询用户对评论的点赞状态
	 * @param params
	 * @return
	 */
	public Map<String,Object>  getOrderCommentParentForMemberFabulousStatus(Map<String,Object> params);
	
	
	/**
	 * 查询商品评论byId
	 * @param commentid
	 * @return
	 */
	public OrderComment getOrderCommentById(Long commentid);
	
	

	/**
	 * 商品评论列表，给父级评论点赞
	 * @param hunterid
	 * @param memberid
	 * @param type
	 * @return
	 */
	public Result saveFabulousOrderComment(Long commentid,Long memberid,Integer type);

	
	/**
	 * 根据订单号查询该商品的评价详情
	 * @param orderid
	 * @return
	 */
	public OrderComment getOrderCommentByOrderId(Long orderid);
	
	/**
	 * 订单评价详情byId
	 * @param orderid
	 * @param memberid
	 * @return
	 */
	public Result getOrderCommentDetail(Long orderid,Long memberid);

	/**
	 * 查询属于该批发商下订单的评价列表
	 * @param params
	 * @return
	 */
	public List<Map<String,Object>> findHunterOrderCommentList(Map<String,Object> params);
	
	/**
	 * 属于该批发商下订单的评价列表
	 * @param hunterid
	 * @param type
	 * @param page
	 * @param rows
	 * @param memberid
	 * @return
	 */
	public Result hunterOrderCommentList(Long hunterid, Integer type, Integer page, Integer rows,Long memberid);

	/**
	 * 根据用户信息和订单id获取用户评论信息
	 * @param oid
	 * @return
	 */
	Result getOrderCommentByOrderAndMember(Long oid);

	/**
	 *
	 * @param orderno   主订单
	 * @param mId       会员的id
	 * @return
	 */
	public Result getOrderCommentDetailByOrderNo(String orderno,Long mId);
}
