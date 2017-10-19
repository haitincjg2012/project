package com.ph.shopping.facade.spm.constant;

/**
 * @project：phshopping-facade-spm
 * @description：代理级别
 * @author：Mr.Shu
 * @createTime：2017年4月26日
 * @Copyright @2017 by Mr.Shu
 */
public enum AgentLevelEnum {
    //代理级别
    CITY_AGENT(new Long(1), "市级代理"),
    COUNTY_AGENT(new Long(2), "县级代理"),
    TOWN_AGENT(new Long(3), "社区代理"),

    //特殊业务专用
    COUNTY_AGENT_AND_TOWN_AGENT(new Long(5), "县级代理和社区代理");

    private Long code;
    private String msg;

    private AgentLevelEnum(Long code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
