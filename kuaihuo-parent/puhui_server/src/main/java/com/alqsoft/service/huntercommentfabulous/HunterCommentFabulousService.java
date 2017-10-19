package com.alqsoft.service.huntercommentfabulous;

import org.alqframework.orm.BaseService;

import com.alqsoft.entity.hunter.Hunter;
import com.alqsoft.entity.huntercommentfabulous.HunterCommentFabulous;

public interface HunterCommentFabulousService extends BaseService<HunterCommentFabulous> {

	
	/**
	 * app首页批发商评论点赞，查询该父级评论是否被当前登录用户点赞
	 * @param commentId
	 * @param memberId
	 * @return
	 */
	public HunterCommentFabulous getHunterCommentFabulousByCommentIdAndMemberId(Long commentid,Long memberid);
}
