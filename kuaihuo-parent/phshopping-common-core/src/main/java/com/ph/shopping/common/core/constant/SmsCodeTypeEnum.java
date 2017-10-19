package com.ph.shopping.common.core.constant;

/**
 * 短信发送codeType
 * @author xwolf
 * @date 2017-08-29 15:06
 * @since 1.8
 **/
public enum SmsCodeTypeEnum {

    REGISTER("Fr170001","注册"),
    FIND_PASSWORD("Fr170001","找回密码"),
    SCORE("Fr170002","积分"),
    WITHDRAW("Fr170002","提现"),
    ;
    private String code;
    private String msg;

    SmsCodeTypeEnum(String code,String msg){
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
