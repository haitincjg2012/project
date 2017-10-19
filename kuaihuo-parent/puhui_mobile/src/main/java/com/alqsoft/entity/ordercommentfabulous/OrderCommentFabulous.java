package com.alqsoft.entity.ordercommentfabulous;

import org.alqframework.orm.hibernate.IdEntity;

import com.alqsoft.entity.member.Member;
import com.alqsoft.entity.ordercomment.OrderComment;

/**
 * 点赞、取消赞
 * @author sunhuijie
 *
 * @date 2017年3月1日
 *
 */
public class OrderCommentFabulous extends IdEntity {

	private Integer type;//点赞 1   取消赞2
	
	private Member member;//赞的用户
	
	private OrderComment OrderComment;//关联的评论

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public OrderComment getOrderComment() {
		return OrderComment;
	}

	public void setOrderComment(OrderComment orderComment) {
		OrderComment = orderComment;
	}
	
}
