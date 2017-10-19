package com.ph.shopping.facade.spm.exception;

import com.ph.shopping.common.core.exception.BizException;

/**
 * @author Mr.Dong
 * @ClassName: MerchantException
 * @Description: 商户异常类
 * @date 2017年4月25日 下午5:07:14
 */
public class MerchantException extends BizException {

    private static final long serialVersionUID = 4520865415136523205L;

    public MerchantException(MerchantExceptionEnum exceptionEnum) {
        newInstance(exceptionEnum);
    }

    /**
     * @param exceptionEnum
     * @return
     * @Title: newInstance
     * @Description: 实例化异常
     * @author Mr.Dong
     * @date 2017年4月25日 下午5:00:57
     */
    public MerchantException newInstance(MerchantExceptionEnum exceptionEnum) {
        return new MerchantException(exceptionEnum.getMsg(), exceptionEnum.getCode());
    }

    public MerchantException(String msg, String code) {
        super(msg, code);
    }
    public MerchantException() {
    }
}
