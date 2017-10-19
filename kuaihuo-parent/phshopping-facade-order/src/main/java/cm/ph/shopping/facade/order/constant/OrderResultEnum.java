package cm.ph.shopping.facade.order.constant;

import com.ph.shopping.common.util.core.RespCode;

/**
 * @项目：phshopping-facade-order
 * @描述：订单操作结果
 * @作者： 张霞
 * @创建时间： 16:41 2017/5/27
 * @Copyright @2017 by zhangxia
 */
public enum OrderResultEnum implements RespCode {
    //订单操作结果
    ADD_ORDER_ADDRESS_FAIL("80001","新增收货地址失败"),
    DEL_ORDER_ADDRESS_FAIL("80002","删除收货地址失败"),
    UPD_ORDER_ADDRESS_FAIL("80003","修改收货地址失败"),
    ADD_ORDER_ADDRESS_FULL("80004","添加收货地址达到上限"),
    CANCEL_ORDER_ADDRESS_DEFAULT_FAIL("80005","取消默认地址失败"),
    UPD_ORDER_ADDRESS_DEFAULT_FAIL("80006","设置默认地址失败"),
    CANCEL_ORDER_FAIL("80007","取消订单失败"),
    CANCEL_ORDER_UNABLE("80028","此订单不能取消"),
    NO_PRODUCT("80008","库存不足"),
    GET_ORDER_DETAIL_EXCEPTION("80009","获取订单详情失败"),
    SELECT_NO_DATA("80010","暂无数据"),
    SHIPPING_DEFAULT_FAIL("800011","发货失败"),
    USER_ERROR("80012","用户信息有误!"),
    PAY_ORDER_FAIL("800013","使用余额支付失败"),
    PAY_PASSWORD_ERROR("800014","支付密码输入错误"),
    PAY__ORDER_BALANCE_IS_NOT_ENOUGH("800015","账户余额不足"),
    PAY_CONFIRM_RECEIPT_FAIL("800016","交易完成（确认收货）失败"),
    PAY_PWD_EMPTY("800017","未设置支付密码"),
    UPDATE_FREIGHT_FAIL("800018","修改物流费用失败"),
    NO_MEMBER_UNLINEORDER_DATA("80019", "无会员线下订单记录"),
    NO_MEMBERCARD_DATA("80020","无此会员卡数据"),
    NOENOUGH_MONEY("80021","余额不足"),
    NO_MEMBERPAYPASS("80022","无商户初始支付密码"),
    NO_MEMBERSCORCE("80023","无会员积分数据"),
    NOENOUGH_MEMBER_SCORE("80024","会员积分支付不足"),
    CHECK_CODE_ERROR("80025", "您输入的验证码错误"),
    ADD_UNLINEORDER_FAIL("80026","创建线下订单失败"),
    SELECT_PARAM_NULL("80027","查询操作参数为空"),
    ORDER_OPERAION_INVALID("80029","非法操作"),
    ORDER_ADD_PRODUCT_ERROR("80030","订单商品添加数量失败"),
    ORDER_SUBDUCTION_PRODUCT_ERROR("80031","订单商品减少数量失败"),
    ORDER_REFUND_APPLY_FAIL("80032","申请退款失败"),
    MERCHANT_PROFITRATIO_ERROE("80033","商户分润比例有误"),
    ORDER_MONEY_NOTMATCH("80034","订单金额不匹配"),
    NO_MERCHANT_DATA("80035","无此商户数据"),
    ;

    /**
     * 编码
     */
    private String code;
    /**
     * 编码说明
     */
    private String msg;

    OrderResultEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
