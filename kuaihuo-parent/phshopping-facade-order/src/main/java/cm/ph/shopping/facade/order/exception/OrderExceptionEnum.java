package cm.ph.shopping.facade.order.exception;

import com.ph.shopping.common.util.core.RespCode;

/**
 * 
* @ClassName: OrderExceptionEnum
* @Description: 会员订单异常码
* @author 王强
* @date 2017年4月24日 下午4:35:41
 */
public enum OrderExceptionEnum implements RespCode {
	//订单异常
	ADD_ORDER_EXCEPTION("80001","新增订单异常"),
	UPDATE_ORDER_EXCEPTION("80002","更新订单异常"),
	SELECT_ORDER_EXCEPTION("80003","查询线上订单异常"),
	ADD_UNLINEORDER_EXCEPTION("80004", "创建线下订单异常"),
	CANCEL_UNLINEORDER_EXCEPTION("80005","取消线下订单异常"),
	UPDATE_REFUND_ONLINEORDER_EXCEPTION("80006","更新申请退款的审核状态异常")
	;

	OrderExceptionEnum(String code, String msg) {
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
