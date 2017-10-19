package com.ph.shopping.facade.mapper;

import com.ph.shopping.common.core.base.BaseMapper;
import com.ph.shopping.facade.member.entity.BankCodenameData;
import com.ph.shopping.facade.member.vo.BankCodenameDataVO;

/**
 * @version 2.1
 * @项目：phshopping-parent
 * @描述： 银行卡基础mapper
 * @作者： 熊克文
 * @创建时间： 2017/5/26
 * @Copyright by xkw
 */
public interface BankCodenameDataMapper extends BaseMapper<BankCodenameData> {

    BankCodenameDataVO getBankCodenameDataDetailByCode(String bankCode);
}
