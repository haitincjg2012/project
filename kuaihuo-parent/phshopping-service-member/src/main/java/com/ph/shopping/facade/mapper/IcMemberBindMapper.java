package com.ph.shopping.facade.mapper;

import com.ph.shopping.common.core.base.BaseMapper;
import com.ph.shopping.facade.member.dto.MemberCardInfoDTO;
import com.ph.shopping.facade.member.entity.IcMemberBind;
import com.ph.shopping.facade.member.vo.MemberCardInfoVO;

import java.util.List;

public interface IcMemberBindMapper extends BaseMapper<IcMemberBind> {

    /**
     * @描述：获取绑定的会员卡数据列表
     * @param: memberCardInfoDTO
     * @return:
     * @作者： Mr.Shu
     * @创建时间：2017/5/24 13:06
     */
    List<MemberCardInfoVO> getMemberBindCardList(MemberCardInfoDTO memberCardInfoDTO);

    /**
     * @描述：判断会员卡是否被绑定
     *
     * @param: null
     *
     * @return:
     *
     * @作者： Mr.Shu
     *
     * @创建时间：2017/5/24 10:26
     */
    int getBindMemberCardByCodeAndStatus(MemberCardInfoDTO memberCardInfoDTO);

}
