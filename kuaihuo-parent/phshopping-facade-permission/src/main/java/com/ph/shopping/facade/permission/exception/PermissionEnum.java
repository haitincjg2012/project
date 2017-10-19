package com.ph.shopping.facade.permission.exception;

import com.ph.shopping.common.util.core.RespCode;

/**
 * 权限枚举
 *
 * @author 郑朋
 * @create 2017/4/25
 **/
public enum PermissionEnum implements RespCode{
    //权限
    NO_PERMISSION("10001","用户无访问权限"),
    REGISTERED("10002","手机号已被注册"),
    ROLE_CODE("10003","角色编码错误"),
    ROLE_NOT_EXIST("10004","角色不存在"),
    USER_ISABLED("10005","账号被冻结"),
    TELPHONE_PASSWORD_ERROR("10006","手机号或者密码错误"),
    ROLE_NOT_NULL_ERROR("10007","角色不能为空"),
    PASSWORD_REMIND("10008","初始化密码为123456"),
    ROLE_CAN_NOT_DELETE("10009","该角色不能被删除"),
    PASSWORD_ERROR("10010","输入旧密码错误"),
    NO_TELPHONE_ERROR("100023", "手机号不存在"),

    //增删改异常
    ADD_ROLE_ERROR("10011", "新增角色异常"),
    UPDATE_ROLE_ERROR("10012", "修改角色异常"),
    DELETE_ROLE_ERROR("10013", "删除角色异常"),
    ADD_USER_ERROR("10014", "新增用户异常"),
    UPDATE_USER_ERROR("10015", "修改用户异常"),
    DELETE_USER_ERROR("10016", "删除用户异常"),
    UPDATE_USER_ROLE_ERROR("10017", "修改用户角色异常"),
    RESET_PASSWORD_ERROR("10018", "重置密码异常"),
    START_ERROR("10019", "启用异常"),
    DISABLE_ERROR("10020", "禁用异常"),
    UPDATE_PERMISSION_ERROR("10021", "修改权限异常"),
    UPDATE_PASSWORD_ERROR("10022", "重置密码异常"),

    NO_FIND_PASSWORD_ERROR("10023", "后台管理员没有忘记密码操作"),
    FIND_PASSWORD_ERROR("10024", "找回密码异常"),



    //新增登录验证枚举  liuy 2017.05.09
    UNKNOWN_ACCOUNT("10011","未知账户"),
    INCORRECT_CREDENTIALS("10012","密码不正确"),
    LOCKED_ACCOUNT("10013","账户已锁定"),
    EXCESSIVE_ATTEMPTS("10014","用户名或密码错误次数过多"),
    AUTHENTICATION("10015", "用户名或密码不正确"),
    NO_ROLE("10016", "该用户还没有角色，请先分配角色"),
    NO_PASS("10017", "未通过审核，不能重置密码"),
    ;

    private String code;
    private String msg;

    PermissionEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
