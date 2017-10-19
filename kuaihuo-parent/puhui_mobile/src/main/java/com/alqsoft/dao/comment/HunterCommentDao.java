package com.alqsoft.dao.comment;

import java.util.List;
import java.util.Map;

import org.alqframework.orm.mybatis.MyBatisRepository;

import com.alqsoft.entity.huntercomment.HunterComment;
/**
 * 批发商评论
 * @author Administrator
 *
 */
@MyBatisRepository
public interface HunterCommentDao {
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
	 * 批发商评论该父级下的子评论by父级评论的id
	 * @param params
	 * @return
	 */
	public List<Map<String,Object>> findSonHunterCommentForByParentIdList(Long parentId);
	
	/**
	 * app首页批发商评论列表--查询该会员是否已有评价过此批发商
	 * @param params
	 * @return
	 */
	public int getDirectHunterCommentCount(Map<String,Object> params);
	
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
	public HunterComment getHunterCommentById(Long commentid);

}
