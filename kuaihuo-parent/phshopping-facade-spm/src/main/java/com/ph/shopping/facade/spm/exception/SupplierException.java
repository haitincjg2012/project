package com.ph.shopping.facade.spm.exception;

import com.ph.shopping.common.core.exception.BizException;

/**
 * 
 * phshopping-facade-supplier
 *
 * @description：供应商异常
 *
 * @author：liuy
 *
 * @createTime：2017年4月24日
 *
 * @Copyright @2017 by liuy
 */
public class SupplierException extends BizException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2679622680632475390L;

	
	public SupplierException(SupplierExceptionEnum supplierEnum) {
		super(supplierEnum.getMsg(),supplierEnum.getCode());
	}

    public SupplierException(String message, String code) {
        super(message, code);
	}
    
	public SupplierException(String msg) {
		super(msg);
	}
	public SupplierException() {
	}

}
