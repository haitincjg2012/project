package com.alqsoft.entity.huntercommentfabulous;

import org.alqframework.orm.hibernate.IdEntity;

import com.alqsoft.entity.huntercomment.HunterComment;
import com.alqsoft.entity.member.Member;

/**
 * 点赞、取消赞
 * @author sunhuijie
 *
 * @date 2017年3月1日
 *
 */

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

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}
	
	public HunterComment getHunterComment() {
		return hunterComment;
	}

	public void setHunterComment(HunterComment hunterComment) {
		this.hunterComment = hunterComment;
	}
	
}
