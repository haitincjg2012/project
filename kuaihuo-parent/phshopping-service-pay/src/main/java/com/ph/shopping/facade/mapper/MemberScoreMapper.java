package com.ph.shopping.facade.mapper;

import com.ph.shopping.common.core.base.BaseMapper;
import com.ph.shopping.facade.pay.entity.MemberScore;
import org.apache.ibatis.annotations.Param;

/**
 * @项目：phshopping-service-pay
 * @描述：会员积分基础表mapper
 * @作者： 张霞
 * @创建时间： 16:06 2017/4/11
 * @Copyright @2017 by zhangxia
 */
public interface MemberScoreMapper extends BaseMapper<MemberScore>{
    /**
     *
     * @return
     */
    public MemberScore getMemberScoreByMemberId(@Param("memberId")String memberId);
}
