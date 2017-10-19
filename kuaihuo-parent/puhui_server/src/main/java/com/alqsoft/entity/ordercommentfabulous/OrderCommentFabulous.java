package com.alqsoft.entity.ordercommentfabulous;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.alqframework.orm.hibernate.IdEntity;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.alqsoft.entity.member.Member;
import com.alqsoft.entity.ordercomment.OrderComment;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 点赞、取消赞
 * @author sunhuijie
 *
 * @date 2017年3月1日
 * @used
 */
@Entity
@Table(name = "alq_order_comment_fabulous", indexes = {})
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler", "fieldHandler" })
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
	@ManyToOne(cascade =CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	
	@ManyToOne(cascade =CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "order_comment_id")
	public OrderComment getOrderComment() {
		return OrderComment;
	}

	public void setOrderComment(OrderComment orderComment) {
		OrderComment = orderComment;
	}
	
}
