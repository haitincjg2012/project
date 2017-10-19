package com.ph.shopping.facade.spm.exception;

import com.ph.shopping.common.util.core.RespCode;

/**
 * @author Mr.Dong
 * @ClassName: MerchantExceptionEnum
 * @Description: 商户异常码
 * @date 2017年4月24日 下午4:35:41
 */
public enum MerchantExceptionEnum implements RespCode {

    //商户
    ADD_MRRCHANT_EXCEPTION("60001", "商户新增异常"),
    UPDATE_MRRCHANT_EXCEPTION("60002", "商户修改异常"),
    DELETE_MRRCHANT_EXCEPTION("60003", "商户删除异常"),
    SELECT_MRRCHANT_EXCEPTION("60004", "获取商户列表异常"),
    ADD_MRRCHANT_IMAGE_EXCEPTION("60005", "商户图片新增异常"),
    DETETE_MRRCHANT_IMAGE_EXCEPTION("60007", "商户图片删除异常"),
    DETAILE_MRRCHANT_EXCEPTION("60008", "获取商户详细异常"),
    USER_BALANCE_RECORD_EXCEPTION("60009", "获取用户余额异常"),
    UPDATE_MERCHANT_STATUES_EXCEPTION("60011", "修改商户状态异常"),
    SELECT_POSITION_EXCEPTION("60010", "获取地理位置异常"),
    GET_COMMUNITY_EXCEPTION("60012", "获取社区异常"),
    NO_USER_BALANCE_RECORD_EXCEPTION("60013", "无此用户记录"),
    MERCHANT_MONEYPAY_FALIL_EXCEPTION("60014", "商户线下订单现金支付失败"),
    NOENOUGH_MONEY_EXCEPTION("60015", "余额不足"),
    ADD_MERCHANTUNLINEORDER_EXCEPTION("60016", "创建商户线下订单失败"),
    SCORE_MERCHANTPAY_EXCEPTION("60017", "商户创建线下订单积分支付失败"),
    NO_MERCHANT_EXCEPTION("60018", "无此商户数据"),
    NO_POSITION_EXCEPTION("60019", "无区域数据"),
    GET_AGENT_BY_MERCHANT("60020", "通过商户获取代理数据异常"),
    ADD_MERCHANT_BALANCE_EXCEPTION("60021", "新增商户账户余额异常"),
    UNLINEORDER_SCORE_PROFIT_EXCEPTION("60023", "商户线下订单积分赠送异常"),
    ERROR_PAYPASSWORD_EXCEPTION("60024", "商户支付密码错误"),
    NOENOUGH_MEMBER_SCORE_EXCEPTION("60025", "会员积分支付不足"),
    NO_MEMBER_UNLINEORDER_EXCEPTION("60026", "无会员线下订单记录"),
    NO_MERCHANDATA_EXCEPTION("60027", "无商户记录"),
    NO_UNLINEORDERDATA_EXCEPTION("60028", "获取线下订单异常"),
    NO_MEMBERPAYPASS_EXCEPTION("60029", "无商户初始支付密码"),
    UPDATE_USERBALANCE_EXCEPTION("60030", "修改用户余额异常"),
    REGISTERED("60031","手机号已被注册"),
    GET_SALESMAN_BY_ABENT("60032","根据代理查询业务员数据异常"),

    //提现
    CASH_MONEY_PASS_ENABLE_ERROR("14001","提现积分大于可用积分"),
    CASH_MONEY_PASS_MAX_ERROR("14002","超过提现积分限定"),
    CASH_MEMBER_EXCEPTION("14003","会员提现异常"),
    CASH_MERCHANT_EXCEPTION("14004","更改商户提现状态异常"),
    CASH_AGENT_EXCEPTION("14005","更改代理提现状态异常"),
    CASH_USERPROFIT_EXCEPTION("14006","更改分润表中提现相关状态异常"),

    //商户充值提现
    ADD_MERCHANTCHARGERECORD_EXCEPTION("50018", "商户充值记录新增异常"),
    ADD_MERCHANTDRAWCASHRECORD_EXCEPTION("50019", "商户提现记录新增异常");

    MerchantExceptionEnum(String code, String msg) {
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
