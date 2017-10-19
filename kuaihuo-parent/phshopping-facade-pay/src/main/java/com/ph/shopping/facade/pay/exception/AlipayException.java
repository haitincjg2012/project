package com.ph.shopping.facade.pay.exception;

import com.ph.shopping.common.core.exception.BizException;

public class AlipayException extends BizException{

	/**
	 *
	 */
	private static final long serialVersionUID = -6168280564738880053L;

	public AlipayException(AlipayExceptionEnum exceptionEnum) {
		super(exceptionEnum.getMsg(),exceptionEnum.getCode());
	}

	public AlipayException(String msg, String code) {
		super(msg, code);
	}

	public AlipayException(String msg) {
		super(msg);
	}
	public AlipayException() {
	}

}
