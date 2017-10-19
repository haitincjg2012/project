package com.ph.shopping.facade.spm.exception;

public enum SupplierExceptionEnum {
	
	//供应商
    ADD_SUPPLIER_EXCEPTION("40001", "供应商新增异常"),
    UPDATE_SUPPLIER_EXCEPTION("40002", "供应商修改异常"),
    DELETE_SUPPLIER_EXCEPTION("40003", "供应商删除异常"),
    CONGEALYES_SUPPLIER_EXCEPTION("40004", "供应商解冻异常"),
    CONGEALNO_SUPPLIER_EXCEPTION("40005", "供应商冻结异常"),
    EXAMINEYES_SUPPLIER_EXCEPTION("40006", "供应商审核通过异常"),
    EXAMINENO_SUPPLIER_EXCEPTION("40007", "供应商审核不通过异常"),
    ENTITY_CHECK_SUPPLIER_EXCEPTION("40008", "供应商数据实体校验异常"),
    ENTITY_CHECK_SUPPLIERIMAGE_EXCEPTION("40009", "供应商图片数据实体校验异常"),
  	PROMOTER_NON_EXISTENT("40010", "推广师不存在"),
  	SENPHONEDMESSAGE_EXISTENT("40011", "给用户发送密码短信接口异常"),
  	REGISTERED("40012","手机号已被注册"),
  	RESETPASSWORD_EXCEPTION("40013", "重置密码异常"),
	SELECT_SUPPLIER_EXCEPTION("40014", "供应商查询异常");

    private String code;
    private String msg;

    SupplierExceptionEnum(String code, String msg) {
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
