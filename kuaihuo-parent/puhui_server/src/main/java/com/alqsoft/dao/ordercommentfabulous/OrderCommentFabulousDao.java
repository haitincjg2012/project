package com.alqsoft.dao.ordercommentfabulous;

import org.alqframework.orm.hibernate.BaseDao;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.alqsoft.entity.ordercommentfabulous.OrderCommentFabulous;

public interface OrderCommentFabulousDao extends BaseDao<OrderCommentFabulous>{

	
	/**
	 * 商品评论点赞，查询该父级评论是否被当前登录用户点赞
	 * @param commentId
	 * @param memberId
	 * @return
	 */
	@Query(value="SELECT * FROM alq_order_comment_fabulous oc WHERE  oc.order_comment_id=:commentid AND oc.member_id=:memberid",nativeQuery=true)
	public OrderCommentFabulous getOrderCommentFabulousByCommentIdAndMemberId(@Param("commentid")Long commentid,@Param("memberid")Long memberid);
}
