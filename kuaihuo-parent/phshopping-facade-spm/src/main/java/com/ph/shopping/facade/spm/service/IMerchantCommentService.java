package com.ph.shopping.facade.spm.service;

import java.util.List;
import java.util.Map;

import com.ph.shopping.common.util.result.Result;
/**
 * 评论
 * @author gaoge
 *
 */
public interface IMerchantCommentService {

	/**
	 *  商户评论列表
	 * @param merchantId 
	 * @param type
	 * @param page
	 * @param rows
	 * @param memberid  用户登录id
	 * @return
	 */
	public Result findMerchantCommentList(Long merchantId,Integer type,Integer page,Integer rows,Long memberid);
	
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
	 * 批发商评论父级下的子评论
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
	/**
	 * 获取商户的评价数
	 * @return
	 */
	public Map<String, Object> getMerchantCommentNum(Map<String,Object> params);
}
