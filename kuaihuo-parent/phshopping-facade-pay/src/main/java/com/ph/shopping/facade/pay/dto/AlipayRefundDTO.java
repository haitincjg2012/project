package com.ph.shopping.facade.pay.dto;

import com.ph.shopping.common.core.base.BaseValidate;

import java.io.Serializable;

/**
 * @项目：phshopping-parent
 * @描述支付宝退款接口
 * @作者： Mr.chang
 * @创建时间：2017/6/13
 * @Copyright @2017 by Mr.chang
 */
public class AlipayRefundDTO extends BaseValidate implements Serializable{

    private static final long serialVersionUID = -4144717968660267243L;

    private String out_trade_no;//交易订单号
    private String amount;//退款金额
    private String reason;//退款原因

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
