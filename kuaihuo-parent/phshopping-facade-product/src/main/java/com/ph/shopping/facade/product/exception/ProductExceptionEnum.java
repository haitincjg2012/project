package com.ph.shopping.facade.product.exception;

/**
 * @项目：phshopping-facade-peoduct
 * @描述：商品错误码枚举
 * @作者： 杨颜光
 * @创建时间：2017年4月25日 上午10:43:10
 * @Copyright by 杨颜光
 */
public enum ProductExceptionEnum {
    // 商品
    SUCCESS("200", "成功"),
    ADD_PRODUCT_EXCEPTION("70001", "商品新增异常"),
    UPDATE_PRODUCT_EXCEPTION("70002", "商品修改异常"),
    DELETE_PRODUCT_EXCEPTION(
            "70003", "商品删除异常"),
    ENTITY_CHECK_EXCEPTION("70004", "数据校验异常"),
    SELECT_PRODUCT_EXCEPTION("70005", "获取商品信息异常"),
    ADD_PRODUCT_CLASSIFY_EXCEPTION("70006", "商品分类新增异常"),
    UPDATE_PRODUCT_CLASSIFY_EXCEPTION("70007", "商品分类修改异常"),
    SELECT_PRODUCT_CLASSIFY_EXCEPTION("70008", "获取商品分类信息异常"),
    ADD_PRODUCT_PROPERTY_EXCEPTION("70009", "商品属性新增异常"),
    UPDATE_PRODUCT_PROPERTY_EXCEPTION("70010", "商品属性修改异常"),
    SELECT_PRODUCT_PROPERTY_EXCEPTION("70011", "获取商品属性信息异常"),
    SORT_PRODUCT_PROPERTY_EXCEPTION("700019", "商品属性列表排序异常"),
	DETELE_PRODUCT_CLASSIFY_EXCEPTION("700012", "商品分类删除异常"),
	DETELE_PRODUCT_PROPERTY_EXCEPTION("700013", "商品属性删除异常"),
	AUDIT_PRODUCT_EXCEPTION("700014", "商品审核异常"),
	ONSAL_PRODUCT_EXCEPTION("700015", "商品上架下架异常"),
    REFERENCED("700016", "商品分类已被商品引用"),
    DISABLE_PRODUCT_CLASSIFY_EXCEPTION("700017", "商品分类停用异常"),
    ENABLE_PRODUCT_CLASSIFY_EXCEPTION("700018", "商品分类启用异常");


    ProductExceptionEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

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
}
