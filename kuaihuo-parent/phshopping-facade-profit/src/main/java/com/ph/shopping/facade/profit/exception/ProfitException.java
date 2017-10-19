package com.ph.shopping.facade.profit.exception;

import com.ph.shopping.common.core.exception.BizException;
/**
 * @author Mr.Dong
 * @ClassName: ProfitException
 * @Description: 分润异常类
 * @date 2017年4月25日 下午5:07:14
 */
public class ProfitException extends BizException {
	
	private static final long serialVersionUID = -6116808411630224513L;

	public ProfitException(ProfitExceptionEnum exceptionEnum) {
       super(exceptionEnum.getMsg(), exceptionEnum.getCode());
    }
	
    public ProfitException(String msg, String code) {
        super(msg, code);
    }

    public ProfitException(String message) {
        super(message);
    }
    public ProfitException() {
    }
}
