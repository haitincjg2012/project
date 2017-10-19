package com.ph.shopping.facade.profit.constant;

/**
 * 结算常量枚举
* @ClassName: ProfitEnum
* @Description: TODO(这里用一句话描述这个类的作用)
* @author Mr.Dong
* @date 2017年6月13日 上午11:06:25
 */
public enum ProfitConstantEnum {
	DISABLE(1,"冻结"),
	ENABLE(0,"解冻");
		
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
	private ProfitConstantEnum(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}
	
	
}
