package com.ph.shopping.facade.mapper;

import java.util.List;
import java.util.Map;

import com.ph.shopping.common.core.base.BaseMapper;

import cm.ph.shopping.facade.order.entity.PhMemberOrderOnlineComment;

/**
 * @项目  phshopping-service-spm
 * @描述   商户评论
 * @author 高歌
 * @时间 2017-8-23
 * @version 2.1
 */
public interface PhMemberOrderOnlineCommentMapper extends BaseMapper<PhMemberOrderOnlineComment> {
	/**
	 * 批发商评论父级列表
	 * @param params
	 * @return
	 */
	public List<Map<String,Object>> findMerchantCommentForParentList(Map<String,Object> params);
	/**
	 * 批发商评论父级列表数量,也是该批发商的评论总数量
	 * @param params
	 * @return
	 */
	public int getHunterCommentListForParentCount(Long hunterid);
	
	/**
	 * 获取商户的评价数量
	 * @return
	 */
	public Map<String, Object> getMerchantCommentNum(Map<String,Object> params);
	
	/**
	 * 批发商评论该父级下的子评论by父级评论的id
	 * @param params
	 * @return
	 */
	public List<Map<String,Object>> findSonHunterCommentForByParentIdList(Long parentId);
	/**
	 * 查询用户对这条评论的点赞状态
	 * @param params
	 * @return
	 */
	public Map<String,Object> getHunterCommentParentForMemberFabulousStatus(Map<String,Object> params);
}
