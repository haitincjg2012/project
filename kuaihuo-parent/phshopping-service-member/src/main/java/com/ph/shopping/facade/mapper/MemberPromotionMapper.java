package com.ph.shopping.facade.mapper;

import com.ph.shopping.common.core.base.BaseMapper;
import com.ph.shopping.facade.member.dto.MemberPromotionDTO;
import com.ph.shopping.facade.member.dto.PromotionRecordDTO;
import com.ph.shopping.facade.member.entity.MemberPromotion;
import com.ph.shopping.facade.member.vo.MemberPromotionVO;
import com.ph.shopping.facade.member.vo.PromotionRecordVO;

import java.util.List;

/**
 * @项目：phshopping-service-member
 * @描述：推广师mapper
 * @作者： Mr.Shu
 * @创建时间：2017-5-19
 * @Copyright @2017 by Mr.Shu
 */
public interface MemberPromotionMapper extends BaseMapper<MemberPromotion> {
    /**
     * @描述：根据条件查询推广师信息
     * @param: Result
     * @return:
     * @作者： Mr.Shu
     * @创建时间：2017/5/19 11:02
     */
    MemberPromotionVO getMemberAuthInfoByCondition(MemberPromotionDTO memberAuthDTO);

    /**
     * @描述：查询推广师列表
     * @param: memberAuthDTO
     * @return:
     * @作者： Mr.Shu
     * @创建时间：2017/5/19 11:13
     */
    List<MemberPromotionVO> getMemberAuthList(MemberPromotionDTO memberAuthDTO);

    /**
     * @Title: selectPromotionIsExist
     * @Description: 根据三方账号、账号类型、memberID判断三方账号是否被绑定
     * @author liuy
     * @date  2017年6月27日 下午3:31:42
     * @param memberAuthDTO
     * @return
     */
    MemberPromotionVO selectPromotionIsExist(MemberPromotionDTO memberAuthDTO);
	
	/**
     * 审核推广师
     * @param memberAuthDTO
	 * @return int 返回类型  
	 * @author liuy
	 * @createTime 2017年05月19日
     */
    int updateStatus(MemberPromotion memberAuth);

	/**
     * 查询推广师推广记录
     * @param dto
	 * @return Member 返回类型  
	 * @author liuy
	 * @createTime 2017年05月19日
     */
    List<PromotionRecordVO> selectPromotionRecords(PromotionRecordDTO dto);

}
