package com.ph.shopping.facade.spm.exception;

import com.ph.shopping.common.core.exception.BizException;

/**
 * phshopping-facade-spm
 *
 * @description：仓库地址异常
 * @author：liuy
 * @createTime：2017年4月25日
 * @Copyright @2017 by liuy
 */
public class WarehouseAddressException extends BizException {

    /**
     *
     */
    private static final long serialVersionUID = 1586876039402776521L;

    public WarehouseAddressException(WarehouseAddressExceptionEnum warehouseAddressEnum) {
    	super(warehouseAddressEnum.getMsg(), warehouseAddressEnum.getCode());
    }

    public WarehouseAddressException(String message, String code) {
        super(message, code);
    }
    
	public WarehouseAddressException(String msg) {
		super(msg);
	}
	public WarehouseAddressException() {
	}
}
