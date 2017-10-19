package com.alqsoft.dao.comment;

import com.alqsoft.entity.ordercomment.OrderComment;
import org.alqframework.orm.mybatis.MyBatisRepository;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 订单评论
 * @author Administrator
 *
 */
@MyBatisRepository
public interface OrderCommentDao {
	
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
	 * 查询商品的评论详情通过订单号 byOrderNo
	 * @param orderNo
	 * @return
	 */
	public List<OrderComment> getOrderCommentDetailByOrderNo(String orderNo);
	
	/**
	 * 根据订单号查询该商品的评价详情
	 * @param orderid
	 * @return
	 */
	public OrderComment getOrderCommentByOrderId(Long orderid);
	
	/**
	 * 查询属于该批发商下订单的评价列表
	 * @param params
	 * @return
	 */
	public List<Map<String,Object>> findHunterOrderCommentList(Map<String,Object> params);

	/**
	 * 获取用户评论信息
	 * @param oid
	 * @return
	 */
	String getordercommentByOrderAndMember(@Param("oid") Long oid);

	/**
	 * 通过批发商id获取商户头像和昵称
	 * @param id
	 * @return
	 */
	public Map getHunterHeadImageAndNickName(@Param("id") Long id);
}
