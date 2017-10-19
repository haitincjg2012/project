package com.ph.shopping.facade.pay.exception;

import com.ph.shopping.common.core.exception.BizException;

public class SwiftpayException extends BizException{

	/**
	 *
	 */
	private static final long serialVersionUID = -6168280564738880053L;

	public SwiftpayException(SwiftPayExceptionEnum exceptionEnum) {
		super(exceptionEnum.getMsg(),exceptionEnum.getCode());
	}

	public SwiftpayException(String msg, String code) {
		super(msg, code);
	}

	public SwiftpayException(String msg) {
		super(msg);
	}
	public SwiftpayException() {
	}

}
