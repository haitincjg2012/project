package com.ph.shopping.facade.member.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.mapper.BankCodenameDataMapper;
import com.ph.shopping.facade.member.entity.BankCodenameData;
import com.ph.shopping.facade.member.service.IBankCodenameDataService;
import com.ph.shopping.facade.member.vo.BankCodenameDataVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.stream.Stream;

/**
 * @version 2.1
 * @项目：phshopping-parent
 * @描述： 银行基础卡接口实现
 * @作者： 熊克文
 * @创建时间： 2017/5/27
 * @Copyright by xkw
 */
@Component
@Service(version = "1.0.0")
public class BankCodenameDataServiceImpl implements IBankCodenameDataService {

    /**
     * 银行基础信息mapper
     */
    @Autowired
    private BankCodenameDataMapper bankCodenameDataMapper;

    @Override
    public Result getBankCodenameDataDetailByCode(final String bankCode) {
        Result result = new Result(false);
        if (StringUtils.isNotBlank(bankCode)) {
            if (bankCode.length() >= 6) {
                BankCodenameDataVO bankCodenameDataVO = bankCodenameDataMapper.getBankCodenameDataDetailByCode(bankCode);
                if (bankCodenameDataVO != null) {
                    result.setData(bankCodenameDataVO);
                }
            }
        }

        result.setSuccess(result.getData() != null);
        return result;
    }


    @Override
    public Result insert(String bankCode, String bankName) {
        Result result = new Result(false);
        //校验参数
        if (Stream.of(bankCode, bankName).anyMatch(StringUtils::isBlank)) {
            result.setMessage(String.format("传入参数错误，不能为空 bankCode:%s,bankName:%s", bankCode, bankName));
        } else {
            BankCodenameData insert = new BankCodenameData();
            insert.setCreateTime(new Date());
            insert.setBankName(bankName);
            insert.setBankCode(bankCode);
            result.setSuccess(this.bankCodenameDataMapper.insert(insert) > 0);
        }

        return result;
    }
}
