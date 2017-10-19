package com.ph.shopping.facade.system.exception;

import com.ph.shopping.common.util.core.RespCode;

/**
 * 系统枚举
 *
 * @author 郑朋
 * @create 2017/5/8
 **/
public enum SystemEnum implements RespCode {
	
    PARAM_INCOMPLETE("10001","请求参数不全")
    ;

	/**
	 * 编码
	 */
	private String code;
	/**
	 * 描述
	 */
	private String msg;
	
	private SystemEnum(String code,String msg){
		this.code = code;
		this.msg = msg;
	}
    @Override
    public String getMsg() {
        return msg;
    }

    @Override
    public String getCode() {
        return code;
    }
}
