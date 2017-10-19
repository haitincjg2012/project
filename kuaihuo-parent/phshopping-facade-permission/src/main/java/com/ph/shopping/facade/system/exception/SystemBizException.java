package com.ph.shopping.facade.system.exception;

import com.ph.shopping.common.core.exception.BizException;
import com.ph.shopping.common.util.core.RespCode;

/**
 * 系统管理公共异常类
 *
 * @author 郑朋
 * @create 2017/5/8
 **/
public class SystemBizException  extends BizException {

    private static final long serialVersionUID = -5143285889022766208L;

    public SystemBizException(RespCode exceptionEnum) {
        super(exceptionEnum.getMsg(),exceptionEnum.getCode());
    }

    public SystemBizException(String msg, String code) {
        super(msg, code);
    }

    public SystemBizException(String message) {
        super(message);
    }
    public SystemBizException() {
    }
}
