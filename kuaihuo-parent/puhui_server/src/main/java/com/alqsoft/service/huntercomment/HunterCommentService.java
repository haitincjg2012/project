package com.alqsoft.service.huntercomment;

import org.alqframework.orm.BaseService;
import org.alqframework.result.Result;
import org.springframework.data.repository.query.Param;

import com.alqsoft.entity.huntercomment.HunterComment;

/*
 * 首页批发商评论列表
 */
public interface HunterCommentService extends BaseService<HunterComment>{
	
	/**
	 * app首页批发商评论列表，保存给父级评论
	 * @param hunterid
	 * @param masterid
	 * @param visitorid
	 * @param content
	 * @return
	 */
	public Result saveVisitorHunterComment(Long hunterid, Long masterid, Long visitorid, String content);
	
	/**
	 * app首页批发商评论列表--查询该会员是否已有评价过此批发商
	 * @param hunterid
	 * @param memberid
	 * @return
	 */
	public int getDirectHunterCommentCount(Long hunterid,Long memberid);
	
	/**
	 * app首页批发商评论列表--直接评论批发商
	 * @param hunterid
	 * @param memberid
	 * @param start
	 * @param content
	 * @return
	 */
	public Result savaDirectHunterComment(Long hunterid, Long memberid, Integer start, String content);
	
	public HunterComment getRowLock(Long id);
	
	/**
	 * app首页批发商评论列表--用户给父级评论点赞
	 * @param hunterid
	 * @param memberid
	 * @param type
	 * @return
	 */
	public Result saveFabulousHunterComment(Long commentid, Long memberid, Integer type);

}
