package com.ph.shopping.common.core.customenum;

/**
 * 系统日志操作类型枚举
 *
 * @author 郑朋
 * @create 2017/5/8
 **/
public enum SystemOperateEnum {
    INSERT((byte)1,"新增"),
    DELETE((byte)2,"删除"),
    UPDATE((byte)3,"修改"),
    SELECT((byte)4,"查询"),
    IMPORT_EXPORT((byte)5,"导入/出");

    private Byte type;
    private String desc;

    SystemOperateEnum(Byte type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public Byte getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }
}
