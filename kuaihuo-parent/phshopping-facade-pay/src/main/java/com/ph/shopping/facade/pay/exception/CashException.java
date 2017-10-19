package com.ph.shopping.facade.pay.exception;

import com.ph.shopping.common.core.exception.BizException;

/**
 * @项目：phshopping-facade-
 * @描述：提现异常类
 * @作者：Mr.Chang
 * @创建时间：2017年5月31日
 * @Copyright by Mr.Chang
 */
public class CashException extends BizException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6168280564738880053L;
	public CashException(CashExceptionEnum exceptionEnum) {
		super(exceptionEnum.getMsg(),exceptionEnum.getCode());
	}

	public CashException(String msg, String code) {
		super(msg, code);
	}

	public CashException(String msg) {
		super(msg);
	}
	public CashException() {
	}

}
