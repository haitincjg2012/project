package com.ph.shopping.facade.mapper;

import com.ph.shopping.common.core.base.BaseMapper;
import com.ph.shopping.facade.member.dto.MemberBankCardBindDTO;
import com.ph.shopping.facade.member.entity.MemberBankCardBind;
import com.ph.shopping.facade.member.vo.MemberBankCardBindInfoVO;

/**
 * @version 2.1
 * @项目：phshopping-parent
 * @描述： 用户绑定银行卡mapper
 * @作者： 熊克文
 * @创建时间： 2017/5/25
 * @Copyright by xkw
 */
public interface MemberBankCardBindMapper extends BaseMapper<MemberBankCardBind> {

    MemberBankCardBindInfoVO selectBindCardDetail(MemberBankCardBindDTO dto);
}
