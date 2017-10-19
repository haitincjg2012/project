package com.alqsoft.entity.huntercommentfabulous;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.alqframework.orm.hibernate.IdEntity;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.alqsoft.entity.huntercomment.HunterComment;
import com.alqsoft.entity.member.Member;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 点赞、取消赞
 * @author sunhuijie
 *
 * @date 2017年3月1日
 *
 */
@Entity
@Table(name = "alq_hunter_comment_fabulous", indexes = {})
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler", "fieldHandler" })
public class HunterCommentFabulous extends IdEntity {

	private Integer type;//点赞 1   取消赞2
	
	private Member member;//赞的用户
	
	private HunterComment hunterComment;//关联的评论

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
	@JoinColumn(name = "hunter_comment_id")
	public HunterComment getHunterComment() {
		return hunterComment;
	}

	public void setHunterComment(HunterComment hunterComment) {
		this.hunterComment = hunterComment;
	}
	
}
