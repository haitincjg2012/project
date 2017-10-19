package com.ph.shopping.facade.member.service;

import com.ph.shopping.common.util.page.PageBean;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.member.dto.CheckMemberPromotionDTO;
import com.ph.shopping.facade.member.dto.MemberPromotionDTO;
import com.ph.shopping.facade.member.dto.PromotionRecordDTO;

/**
 * @项目：phshopping-facade-member
 * @描述：推广师相关接口
 * @作者： liuy
 * @创建时间：2017-0-1
 * @Copyright @2017 by liuy
 */
public interface IMemberPromotionService {

    /**
     * @描述：分页查询推广师列表
     * @param: pageBean
     * @param: dto
     * @return: Result
     * @作者： liuy
     * @创建时间：2017/5/19 10:49
     */
    Result getMemberAuthListByPage(PageBean pageBean, MemberPromotionDTO dto);

    /**
     * @描述：查询推广师列表（不分页）
     * @param: dto
     * @return:
     * @作者： liuy
     * @创建时间：2017/5/19 14:43
     */
    Result getMemberAuthList(MemberPromotionDTO dto);

    /**
     * @描述：根据条件查询推广师信息
     * @param: Result
     * @return:
     * @作者： liuy
     * @创建时间：2017/5/19 11:02
     */
    Result getMemberAuthInfoByCondition(MemberPromotionDTO dto);
    
    /**
     * 申请推广师（App和商城->推广师认证）
     * @param memberAuthDTO
	 * @author liuy
	 * @createTime 2017年05月24日
     */
    Result apply(MemberPromotionDTO memberAuthDTO);

    /**
     * 审核推广师
     * @param checkMemberAuthDTO
	 * @author liuy
	 * @createTime 2017年05月24日
     */
    Result check(CheckMemberPromotionDTO checkMemberAuthDTO);

    /**
     * 查询推广师推广记录（推广企业）
     * @param checkMemberAuthDTO
	 * @author liuy
	 * @createTime 2017年05月24日
     */
    Result getAllPromRecordByPage(PromotionRecordDTO promotionRecordDTO);
    
}
