package com.alqsoft.utils;

/**
 * @author Xuejizheng
 * @date 2017-02-28 15:32
 * @see
 * @since 1.8
 */
public enum StatusCodeEnums {
    SUCCESS(1,"成功"),
    SUCCESS_NO_DATA(2,"成功但返回数据为空"),
    USER_INFO_ERROR(3,"用户信息异常"),
    ERROR(0,"接口调用失败"),
    ERROR_PARAM(-1,"参数异常");

     StatusCodeEnums(int code, String msg){
           this.code=code;
           this.msg=msg;
    }
    private int code;
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
