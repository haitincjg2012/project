package com.ph.shopping.facade.spm.exception;

import com.ph.shopping.common.core.exception.BizException;

/**
 * @描述：代理商异常模块
 *
 * @作者： Mr.Shu
 *
 * @创建时间：2017/4/25 8:53
 *
 * @Copyright @2017 by Mr.Shu
 */
public class AgentException extends BizException {

    private static final long serialVersionUID = 4790611300720211303L;


    public AgentException(AgentExceptionEnum exceptionEnum) {
        super(exceptionEnum.getMsg(), exceptionEnum.getCode());
    }

    public AgentException(String msg, String code) {
        super(msg, code);
    }

    public AgentException(String msg) {
        super(msg);
    }
    public AgentException() {
    }
}
