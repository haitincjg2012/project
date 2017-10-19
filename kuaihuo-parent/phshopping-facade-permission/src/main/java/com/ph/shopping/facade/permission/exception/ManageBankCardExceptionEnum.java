package com.ph.shopping.facade.permission.exception;

import com.ph.shopping.common.util.core.RespCode;

/**
 * 银行卡异常枚举
 *
 * @author Mr.Shu
 * @create 2017/4/25
 **/
public enum ManageBankCardExceptionEnum implements RespCode {

    //银行卡
    BINDCARD_EXCEPTION("12011", "绑卡异常"),
    UNBINDCARD_EXCEPTION("12012", "解绑异常"),
    CHECKBIND_EXCEPTION("12013", "您输入的验证码错误"),
    NO_CHECKBIND_EXCEPTION("12014", "您没有绑定该银行卡信息"),
    CHECK_TEL_EXCEPTION("12015", "新手机号不能为旧手机号"),
    CHECK_BANK_EXCEPTION("12016", "银行卡信息与身份不符合，请检查"),
    BANK_CHECK_FAIL("12017", "校验银行卡失败"),
    USER_ERROR("12018", "用户信息错误"),
    MEMBER_PWD_MISMATCH("12019", "密码错误"),
    INTERNAL_SERVER_ERROR("12020", "系统错误"),;

    private String code;
    private String msg;

    ManageBankCardExceptionEnum(String code, String msg) {
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
