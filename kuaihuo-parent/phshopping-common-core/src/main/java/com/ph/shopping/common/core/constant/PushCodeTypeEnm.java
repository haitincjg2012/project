package com.ph.shopping.common.core.constant;

/**
 * @author xwolf
 * @since 1.8
 **/
public enum PushCodeTypeEnm {

    KH_MEMBER("fr0001","快火会员"),
    HK_MERCHENT("fr0002","快火批发商"),
    ;

    private String code;
    private String msg;

    PushCodeTypeEnm(String code,String msg){
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
