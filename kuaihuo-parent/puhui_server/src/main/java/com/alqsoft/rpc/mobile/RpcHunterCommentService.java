package com.alqsoft.rpc.mobile;

import org.alqframework.result.Result;

/**
 * 批发商评论列表
 * @author Administrator
 *
 */
public interface RpcHunterCommentService {
	
	/**
	 * app首页批发商评论列表--游客给父级评论者评论
	 * @param hunterid   批发商id
	 * @param masterid	  父级评论id
	 * @param visitorid	  给父级评论的会员id
	 * @param content
	 * @return
	 */
	public Result saveVisitorHunterComment(Long hunterid, Long masterid, Long visitorid, String content);
	
	/**
	 * app首页批发商评论列表--直接评论批发商
	 * @param hunterid
	 * @param memberid
	 * @return
	 */
	public Result savaDirectHunterComment(Long hunterid,Long memberid,Integer start,String content);

	
	/**
	 * 批发商评论列表--给父级点赞
	 * @param hunterid
	 * @param memberid
	 * @param type
	 * @return
	 */
	public Result saveFabulousHunterComment(Long hunterid, Long memberid, Integer type);
}
