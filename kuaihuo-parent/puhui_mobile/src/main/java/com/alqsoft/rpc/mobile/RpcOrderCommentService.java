package com.alqsoft.rpc.mobile;

import org.alqframework.result.Result;

public interface RpcOrderCommentService {
	/**
	 * 商品评论---游客给父级评论者评论
	 * @param hunterid   批发商id
	 * @param masterid	  父级评论id
	 * @param visitorid	  给父级评论的会员id
	 * @param content
	 * @return
	 */
	public Result saveVisitorOrderComment(Long hunterid,Long masterid, Long visitorid, String content);
	
	/**
	 * 商品评论--直接评论批发商
	 * @param productid
	 * @param memberid
	 * @return
	 */
	public Result savaDirectOrderComment(Long orderid,Long memberid,Integer start,String content);
	
	/**
	 * 商品评论列表----给父级评论点赞
	 * @param commentid
	 * @param memberid
	 * @param type
	 * @return
	 */
	public Result saveFabulousOrderComment(Long commentid, Long memberid, Integer type);
}
