package cm.ph.shopping.facade.order.constant;

/**
 * @项目：phshopping-facade-order
 * @描述：审核结果
 * @作者： 张霞
 * @创建时间： 14:23 2017/6/5
 * @Copyright @2017 by zhangxia
 */
public enum CheckResultEnum {
    SUCCESS((byte)0,"审核通过"),
    FAIL((byte)1,"审核未通过")
    ;
    /**
     * 编码
     */
    private byte code;
    /**
     * 编码说明
     */
    private String msg;

    CheckResultEnum(byte code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public byte getCode() {
        return code;
    }

    public void setCode(byte code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
