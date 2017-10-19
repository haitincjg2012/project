package com.ph.shopping.facade.pay.exception;

import com.ph.shopping.common.core.exception.BizException;

public class CommonPayException extends BizException{

	/**
	 *
	 */
	private static final long serialVersionUID = -6168280564738880053L;

	public CommonPayException(CommonPayExceptionEnum pe) {
		newInstance(pe);
	}

	/**
	 * 实例化异常
	 * @param pe
	 * @return
	 * @author Mr.Chang
	 * @since 2017.04.26
	 */
	public CommonPayException newInstance(CommonPayExceptionEnum pe) {
		return new CommonPayException(pe.getMsg(),pe.getCode());
	}

    public CommonPayException(String msg, String code) {
        super(msg, code);
	}

	public CommonPayException() {
	}

}
