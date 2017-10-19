package com.ph.shopping.facade.pay.exception;

import com.ph.shopping.common.core.exception.BizException;

public class PayecoException extends BizException{

	private static final long serialVersionUID = -7879164059625335413L;

	public PayecoException(PayecoExceptionEnum exceptionEnum) {
		super(exceptionEnum.getMsg(),exceptionEnum.getCode());
	}

	public PayecoException() {
	}

}
