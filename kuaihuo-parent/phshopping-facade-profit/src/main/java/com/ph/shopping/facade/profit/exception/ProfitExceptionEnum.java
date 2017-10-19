package com.ph.shopping.facade.profit.exception;

import com.ph.shopping.common.util.core.RespCode;

/**
 * @author Mr.Dong
 * @ClassName: ProfitExceptionEnum
 * @Description: 分润异常码
 * @date 2017年4月26日 下午4:35:41
 */
public enum ProfitExceptionEnum implements  RespCode{
	//分润
	PROFIT_LIST_EXCEPTION("90000","查询分润列表异常"),
	PURCHASE_ORDER_PROFIT_EXCEPTION("90001","供应链订单分润异常"), 
	UNLINE_ORDERP_ROFIT_EXCEPTION("90002","线下订单分润异常"), 
	ONLINE_ORDERP_CLOSE_EXCEPTION("90008","线上订单关闭异常"),
	ONLINE_ORDERP_ROFIT_EXCEPTION("90009","线上订单分润异常"),
	HUNTER_ORDERP_ROFIT_EXCEPTION("90010","批发商订单分润异常"),
	
	NO_PROFIT("90003","暂无数据需要导出!"),
	AUDIT_EXCEPTION("90004","审核异常"),
	EXPORT_EXCEPTION("90005","导出数据异常"),
	ADD_PROFIT_EXCEPTION("90006","新增分润异常"),
	FAIL_PROFIT("90007","分润失败"),
	PROFIT_EXCEPTION("90008","管理费分润异常"),
	
	SETTLE_EXCEPTION("90100","更新线上订单结算状态失败"),
	UPDATE_MEMBER_SCORE_EXCEPTION("90101","更新会员积分冻结状态失败"),
	
	AUDIT_FAIL("90102","操作失败"),
	AUDIT_SUCCESS("90103","操作成功"),
	HAVE_NO_RIGHT("90104","无权进行审核"),
	INVALID_AUDIT_CODE("90105", "未通过,无效的状态码"),
	HAVE_AUDITED("90106", "已通过,无需再审核"),
	DATA_EXCEPTION("90107","系统繁忙,请稍后再试!"),
	UNAUTHORIZED_OPERATION("90108","运营审核通过后才能进行审核"),
	ILLEGAL_OPERATION("90109","非法操作"),
	BACK_SCORE_FAIL("90110", "返回积分失败"),
	UPDATE_DRAWCHAS_EXCEPTION("90111","更新提现订单异常");
	
	
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
	private ProfitExceptionEnum(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}
	 
	 
}
