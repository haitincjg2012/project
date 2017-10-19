package com.ph.shopping.facade.permission.exception;

import com.ph.shopping.common.core.exception.BizException;

/**
 * @项目：phshopping-facade-permission
 *
 * @描述：绑定银行卡异常处理
 *
 * @作者： Mr.Shu
 *
 * @创建时间：2017年3月14日
 *
 * @Copyright @2017 by Mr.Shu
 */
public class ManageBankCardBizException extends BizException {

    private static final long serialVersionUID = 902198845675620978L;

    public ManageBankCardBizException(ManageBankCardExceptionEnum exceptionEnum) {
        super(exceptionEnum.getMsg(), exceptionEnum.getCode());
    }

    public ManageBankCardBizException(String msg, String code) {
        super(msg, code);
    }

    public ManageBankCardBizException(String msg) {
        super(msg);
    }
    public ManageBankCardBizException() {
    }


}
