package com.alqsoft.rpc.mobile.impl;

import org.alqframework.result.Result;
import org.alqframework.result.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.alqsoft.rpc.mobile.RpcHunterCommentService;
import com.alqsoft.service.huntercomment.HunterCommentService;

@Controller
public class RpcHunterCommentServiceImpl implements RpcHunterCommentService{
	@Autowired
	private HunterCommentService hunterCommentService;
	/**
	 * app首页批发商评论列表--游客给父级评论者评论
	 * @param hunterid   批发商id
	 * @param masterid	  父级评论id
	 * @param visitorid	  给父级评论的会员id
	 * @param content
	 * @return
	 */
	@Override
	public Result saveVisitorHunterComment(Long hunterid, Long masterid, Long visitorid, String content) {
	
		return hunterCommentService.saveVisitorHunterComment(hunterid, masterid, visitorid, content);
		
	}
	
	/**
	 * app首页批发商评论列表--直接评论批发商
	 */
	@Override
	public Result savaDirectHunterComment(Long hunterid, Long memberid, Integer start, String content) {
		
		return hunterCommentService.savaDirectHunterComment(hunterid, memberid, start, content);
	}

	/**
	 * 批发商评论列表--给父级点赞
	 */
	@Override
	public Result saveFabulousHunterComment(Long commentid, Long memberid, Integer type) {
		
		return hunterCommentService.saveFabulousHunterComment(commentid,memberid,type);
	}

}
