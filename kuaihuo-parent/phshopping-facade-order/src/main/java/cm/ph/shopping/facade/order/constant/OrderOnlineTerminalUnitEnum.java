package cm.ph.shopping.facade.order.constant;

/**
 * @项目：phshopping-facade-order
 * @描述：线上订单终端来源  终端来源:0=平台；1=APP商城
 * @作者： 张霞
 * @创建时间： 14:20 2017/5/24
 * @Copyright @2017 by zhangxia
 */
public enum OrderOnlineTerminalUnitEnum {
    TERMINAL_UNIT_PC((byte) 0,"pc端"),
    TERMINAL_UNIT_APP((byte) 1,"移动端")
    ;
    /**
     * 标识值：线上订单终端来源
     */
    private byte terminalUnit;
    /**
     * 标识描述信息
     */
    private String desc;

    OrderOnlineTerminalUnitEnum(byte terminalUnit, String desc) {
        this.terminalUnit = terminalUnit;
        this.desc = desc;
    }

    public byte getTerminalUnit() {
        return terminalUnit;
    }

    public void setTerminalUnit(byte terminalUnit) {
        this.terminalUnit = terminalUnit;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
