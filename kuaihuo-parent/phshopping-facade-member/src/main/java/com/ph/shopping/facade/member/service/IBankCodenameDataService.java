package com.ph.shopping.facade.member.service;

import com.ph.shopping.common.util.result.Result;

/**
 * @version 2.1
 * @项目：phshopping-parent
 * @描述： 银行卡数据接口
 * @作者： 熊克文
 * @创建时间： 2017/5/26
 * @Copyright by xkw
 */
public interface IBankCodenameDataService {

    /**
     * @param bankCode 银行卡号
     * @return Result
     */
    Result getBankCodenameDataDetailByCode(String bankCode);

    /**
     * @param bankCode 银行卡号
     * @param bankName 银行名称
     * @return Result
     */
    Result insert(String bankCode, String bankName);

}
