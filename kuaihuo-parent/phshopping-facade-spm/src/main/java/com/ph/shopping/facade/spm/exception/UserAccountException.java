package com.ph.shopping.facade.spm.exception;

import com.ph.shopping.common.core.exception.BizException;
/**
 * 
* @ClassName: UserAccountException
* @Description: 用户账户异常类
* @author 王强
* @date 2017年5月26日 上午9:26:04
 */
public class UserAccountException extends BizException {

    private static final long serialVersionUID = 6470263325168367586L;

    public UserAccountException(UserAccountExceptionEnum exceptionEnum) {
        super(exceptionEnum.getMsg(), exceptionEnum.getCode());
    }
	public UserAccountException() {}

}
