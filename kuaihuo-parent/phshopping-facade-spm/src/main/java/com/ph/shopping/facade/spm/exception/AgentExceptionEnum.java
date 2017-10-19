package com.ph.shopping.facade.spm.exception;

import com.ph.shopping.common.util.core.RespCode;


/**
 * @描述：代理商异常枚举
 *
 * @作者： Mr.Shu
 *
 * @创建时间：2017/4/25 8:53
 *
 * @Copyright @2017 by Mr.Shu
 */
public enum AgentExceptionEnum implements RespCode{
    //代理商
    ADD_AGENT_EXCEPTION("50001", "代理商新增异常"),
    UPDATE_AGENT_EXCEPTION("50002", "代理商修改异常"),
    DELETE_AGENT_EXCEPTION("50003", "代理商删除异常"),
    SELECT_AGENT_EXCEPTION("50004", "获取代理商列表异常"),
    ADD_AGENT_IMAGE_EXCEPTION("50005", "代理商图片新增异常"),
    DELETE_AGENT_IMAGE_EXCEPTION("50007", "代理商图片删除异常"),
    DETAIL_AGENT_EXCEPTION("50008", "获取代理商详细异常"),
    ENTITY_AGENT_HAVE_EXCEPTION("50009", "本市已存在市县级代理商"),
    ENTITY_COMMUNITY_HAVE_EXCEPTION("500010", "本社区已有社区代理"),
    CONGEALYES_AGENT_EXCEPTION("50015", "代理商解冻异常"),
    CONGEALNO_AGENT_EXCEPTION("50016", "代理商冻结异常"),
    PROMOTER_NON_EXISTENT("50017", "推广师不存在"),
	
	//代理商充值提现
	ADD_AGENTCHARGERECORD_EXCEPTION("50018", "代理商充值记录新增异常"),
	ADD_AGENTDRAWCASHRECORD_EXCEPTION("50019", "代理商提现记录新增异常");

    AgentExceptionEnum(String code, String msg) {
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
