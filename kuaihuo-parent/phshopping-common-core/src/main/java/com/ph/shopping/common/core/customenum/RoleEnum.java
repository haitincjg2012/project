package com.ph.shopping.common.core.customenum;

/**
 * @项目：phshopping-facade-permission
 * @描述： 角色编号枚举
 * @作者： Mr.zheng
 * @创建时间：2017-03-14
 * @Copyright @2017 by Mr.zheng
 */
public enum RoleEnum {
    ADMIN((byte) 1, "管理员"),
    SUPPLIER((byte) 2, "供应商"),
    CITY_AGENT((byte) 3, "市级代理商"),
    COUNTY_AGENT((byte) 4, "县级代理商"),
    COMMUNITY_AGENT((byte) 5, "社区代理商"),
    MERCHANT((byte) 6, "商户"),
    WHOLESALE_CITY_AGENT((byte) 42, "批发市级代理商"),
    WHOLESALE_COUNTY_AGENT((byte) 43, "批发县级代理商"),
    WHOLESALE_COMMUNITY_AGENT((byte) 41, "批发社区代理商"),;

    RoleEnum(Byte code, String message) {
        this.code = code;
        this.message = message;
    }

    private Byte code;
    private String message;

    public Byte getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public static RoleEnum getRoleEnumByCode(Byte code) {
        RoleEnum[] roleEnums =  RoleEnum.values();
        for (RoleEnum roleEnum : roleEnums) {
            if (code == roleEnum.code) {
                return roleEnum;
            }
        }
        return null;
    }


}
