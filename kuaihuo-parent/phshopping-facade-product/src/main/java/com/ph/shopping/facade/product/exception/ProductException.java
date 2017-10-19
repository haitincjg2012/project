package com.ph.shopping.facade.product.exception;

import com.ph.shopping.common.core.exception.BizException;

/**
 * 商品异常类
 *
 * @项目：phshopping-facade-
 * @描述：
 * @作者： 杨 颜 光
 * @创建时间：2017年4月25日 下午2:11:46
 * @Copyright by 杨颜光
 */
public class ProductException extends BizException {

    private static final long serialVersionUID = 4790611300720211303L;

    public ProductException(ProductExceptionEnum exceptionEnum) {
    	super(exceptionEnum.getMsg(),exceptionEnum.getCode());
    }

    public ProductException(String msg, String code) {
		super(msg, code);
	}

    public ProductException(String msg) {
		super(msg);
	}

    public ProductException() {
    }
   
}
