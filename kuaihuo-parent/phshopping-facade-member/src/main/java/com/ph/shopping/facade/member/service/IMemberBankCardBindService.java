package com.ph.shopping.facade.member.service;

import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.member.dto.BankCardBindInfoDTO;

/**
 * @version 2.1
 * @项目：phshopping-parent
 * @描述： 银行卡绑定接口
 * @作者： 熊克文
 * @创建时间： 2017/5/25
 * @Copyright by xkw
 */
public interface IMemberBankCardBindService {

    /**
     * @param bankCardBindInfoDTO 绑定银行卡参数
     * @return Result
     * @描述： 用户绑定银行卡接口
     */
    Result bindMemberBankCard(BankCardBindInfoDTO bankCardBindInfoDTO);

    /**
     * @param userId 用户id
     * @return Result
     * @描述： 查询用户是否已经绑定过银行卡 只查询成功绑定的
     */
    Result selectBindMemberBankCardByUserId(Long userId);

    /**
     * @param bindId    银行卡绑定id
     * @param userId    银行卡用户id
     * @param operateId 当前登录人id  操作人
     * @param ip        ip
     * @return Result
     * @描述： 用户解绑银行卡接口
     */
    Result unBindByBindIdAndMemberId(Long bindId, Long userId, Long operateId, String ip);
}
