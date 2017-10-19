package com.ph.shopping.facade.spm.exception;

import com.ph.shopping.common.util.core.RespCode;

/**
 * 
* @ClassName: UserAccountExceptionEnum
* @Description: 用户账户模块的枚举
* @author 王强
* @date 2017年5月26日 上午9:26:35
 */
public enum UserAccountExceptionEnum implements RespCode {
	
	//银行卡
    BINDCARD_EXCEPTION("12011", "绑卡异常"),
    UNBINDCARD_EXCEPTION("12012", "解绑异常"),
    CHECKBIND_EXCEPTION("12013", "您输入的验证码错误"),
    CHECK_TEL_EXCEPTION("12015", "新手机号不能为旧手机号"),
    CHECK_BANK_EXCEPTION("12016", "银行卡信息与身份不符合，请检查"),
    BANK_CHECK_FAIL("12017", "校验银行卡失败"),
    USER_ERROR("12018", "用户信息错误"),
    MEMBER_PWD_MISMATCH("12019", "密码错误"),
    INTERNAL_SERVER_ERROR("12020", "系统错误"),
    NEED_PARAMS("12021","请补全参数"),

    UNKNOWN_STATUS("100007","未知状态码"),
    UNFROZEN_FAIL("100006","解冻失败"),
    FROZEN_FAIL("100005","冻结失败"),
	NO_CHECKBIND_EXCEPTION("100004", "您没有绑定该银行卡信息"),
	ADD_USERDRAWCASH_EXCEPTION("100003","新增用户提现记录失败"),
	SELECT_EXCEPTION("100002","查询账户余额失败"),
	ADD_USERCHARGE_EXCEPTION("100001","新增充值记录失败"),
	
	//充值
	USER_CHARGED("150009","已充值成功"),
	CHARGE_EXCEPTION("150010","充值异常"),
	
	//提现
	DRAWCASH_EXCEPTION("150011","提现失败"),
	BALANCE_INTEGER("150012","请输入整数金额"),
	//认证及绑卡
	AUTH_INCOMPLETE("150013", "认证信息不全"),
	AUTH_COMPLETE("150014", "请补全认证信息");
	

	private UserAccountExceptionEnum(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	private String msg;
	private String code;

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
