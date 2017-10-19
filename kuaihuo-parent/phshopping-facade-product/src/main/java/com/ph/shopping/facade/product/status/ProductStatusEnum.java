package com.ph.shopping.facade.product.status;

/**
 * ProductStatusEnum  各种状态枚举
 * @version: 2.1
 * @author: 李超
 * @date: 2017-05-16 11:02:12
 */
public enum ProductStatusEnum {
    /** 类型区分1.全国，2本地 */
    NATIONWIDE((byte)1, "全国"),
    LOCAL((byte)2, "本地"),

    /** 商品来源 1:平台 2:代理商 */
    FROM_SYSTEM_PLATFORM((byte)1, "平台"),
    FROM_AGENT((byte)2, "代理商"),


    /** 审核状态 0:待审核 1:未通过 2:审核通过 */
    APPROVE((byte)0, "待审核"),
    UNAPPROVE((byte)1, "未通过"),
    UNREVIEWED((byte)2, "审核通过"),

    /** 上下架状态 0:已上架 1:未上架 */
    PUBLISH((byte)0, "上架"),
    UNPUBLISH((byte)1, "下架"),

    /** 是否删除 0:未删除 1：已删除 */
    Not_DELETE((byte)0, "未删除"),
    DELETED((byte)1, "已删除"),

    /** 分类级别 1 一级分类 2 二级分类 3 三级分类 */
    CLASSIFY_LEVEL_1((byte)1, "一级分类"),
    CLASSIFY_LEVEL_2((byte)2, "二级分类"),
    CLASSIFY_LEVEL_3((byte)3, "三级分类"),

    /** 是否启用 0 未启用，1 已经启用 */
    DISABLED((byte)0, "未启用"),
    ENABLED((byte)1, "已启用"),
    
    /**商城商品排序查询枚举条件*/
    COMMODITYSALES_DESC((byte)1, "销量倒序"), 
    UPDATETIME_DESC((byte)2, "上架时间倒序"), 
    RETAILPRICE_DESC((byte)3, "零售价倒序"), 
    COMMODITYSALES_PSE((byte)4, "销量正序"), 
    UPDATETIME_PSE((byte)5, "上架时间正序"), 
    RETAILPRICE_PSE((byte)6, "零售价正序"), 
    ;
	
	
    private final byte code;
    private final String msg;
    ProductStatusEnum(byte code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public byte getCode() {
        return code;
    }
    public String getMsg() {
        return msg;
    }
}
