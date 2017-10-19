package com.ph.shopping.facade.spm.exception;

/**
 * phshopping-facade-spm
 *
 * @description：仓库地址枚举
 * @author：liuy
 * @createTime：2017年4月25日
 * @Copyright @2017 by liuy
 */
public enum WarehouseAddressExceptionEnum {

    //仓库地址
    ADD_ORDER_ADDRESS_EXCEPTION("13001", "仓库地址新增异常"),
    UPDATE_ORDER_ADDRESS_EXCEPTION("13002", "仓库地址修改异常"),
    DELETE_ORDER_ADDRESS_EXCEPTION("13003", "仓库地址删除异常"),
    DEFAULT_ORDER_ADDRESS_EXCEPTION("13004", "仓库地址设置默认地址异常"),
    DELIVERY_POINT_ORDER_ADDRESS_EXCEPTION("13005", "仓库地址设置默认提货点异常");

    private String code;
    private String msg;

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

    private WarehouseAddressExceptionEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
