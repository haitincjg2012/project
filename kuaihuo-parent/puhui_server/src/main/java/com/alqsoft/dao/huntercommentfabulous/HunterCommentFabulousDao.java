package com.alqsoft.dao.huntercommentfabulous;

import org.alqframework.orm.hibernate.BaseDao;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.alqsoft.entity.huntercommentfabulous.HunterCommentFabulous;

public interface HunterCommentFabulousDao extends BaseDao< HunterCommentFabulous>{

	
	/**
	 * app首页批发商评论点赞，查询该父级评论是否被当前登录用户点赞
	 * @param commentId
	 * @param memberId
	 * @return
	 */
	@Query(value="SELECT * FROM alq_hunter_comment_fabulous hc WHERE  hc.hunter_comment_id=:commentid AND hc.member_id=:memberid",nativeQuery=true)
	public HunterCommentFabulous getHunterCommentFabulousByCommentIdAndMemberId(@Param("commentid")Long commentid,@Param("memberid")Long memberid);
}
