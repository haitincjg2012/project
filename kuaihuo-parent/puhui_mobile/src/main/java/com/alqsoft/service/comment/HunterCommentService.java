package com.alqsoft.service.comment;

import java.util.List;
import java.util.Map;

import org.alqframework.result.Result;

import com.alqsoft.entity.huntercomment.HunterComment;

/**
 * 评论
 * @author Administrator
 *
 */
public interface HunterCommentService {


	/**
	 *  app批发商评论列表
	 * @param hunterId
	 * @param type
	 * @param page
	 * @param rows
	 * @param memberid  用户登录id
	 * @return
	 */
	public Result findHunterCommentList(Long hunterId,Integer type,Integer page,Integer rows,Long memberid);
	
	
	/**
	 * 批发商评论父级列表
	 * @param params
	 * @return
	 */
	public List<Map<String,Object>> findHunterCommentForParentList(Map<String,Object> params);
	
	/**
	 * 批发商评论父级列表数量,也是该批发商的评论总数量
	 * @param params
	 * @return
	 */
	public int getHunterCommentListForParentCount(Long hunterid);
	
	/**
	 * 批发商评论父级下的子评论
	 * @param params
	 * @return
	 */
	public List<Map<String,Object>> findSonHunterCommentForByParentIdList(Long parentId);
	
	/**
	 * app首页批发商评论列表--游客给父级评论者评论
	 * @param hunterid   批发商id
	 * @param masterid	  父级评论id
	 * @param visitorid	  给父级评论的会员id
	 * @param content
	 * @return
	 */
	public Result saveVisitorHunterComment(Long hunterid,Long masterid,Long visitorid,String content);
	
	/**
	 *  app首页批发商评论列表--直接评论批发商
	 * @param hunterid
	 * @param memberid
	 * @param start
	 * @param content
	 * @return
	 */
	public Result savaDirectHunterComment(Long hunterid,Long memberid,Integer start,String content);
	
	/**
	 * app首页批发商评论列表--查询该会员是否已有评价过此批发商
	 * @param params
	 * @return
	 */
	public int getDirectHunterCommentCount(Map<String,Object> params);
	
	/**
	 * 批发商评论列表，给父级评论点赞    1点赞   2取消赞
	 * @param commentid
	 * @param memberid
	 * @param type
	 * @return
	 */
	public Result saveFabulousHunterComment(Long commentid,Long memberid,Integer type);
	
	
	/**
	 * 查询用户对这条评论的点赞状态
	 * @param params
	 * @return
	 */
	public Map<String,Object> getHunterCommentParentForMemberFabulousStatus(Map<String,Object> params);
	
	/**
	 * 查询评论byid
	 * @param id
	 * @return
	 */
	public HunterComment getHunterCommentById(Long id);
}
