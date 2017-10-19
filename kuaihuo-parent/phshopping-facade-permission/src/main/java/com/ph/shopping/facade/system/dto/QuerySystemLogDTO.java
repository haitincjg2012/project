package com.ph.shopping.facade.system.dto;

import com.ph.shopping.common.core.customenum.SystemOperateEnum;

import java.io.Serializable;
import java.util.Date;

/**
 * 查询列表参数DTO
 *
 * @author 郑朋
 * @create 2017/5/8
 **/
public class QuerySystemLogDTO implements Serializable {

    private static final long serialVersionUID = -7981667569115027024L;

    /**
     * 操作人
     */
    private String operator;
    /**
     * 操作账号
     */
    private String operateAccount;
    /**
     * 操作类型 {@link SystemOperateEnum}
     */
    private Byte operateType;

    /**
     * 创建开始时间
     */
    private String starTime;

    /**
     * 创建结束时间
     */
    private String endTime;

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getOperateAccount() {
        return operateAccount;
    }

    public void setOperateAccount(String operateAccount) {
        this.operateAccount = operateAccount;
    }

    public Byte getOperateType() {
        return operateType;
    }

    public void setOperateType(Byte operateType) {
        this.operateType = operateType;
    }

    public String getStarTime() {
        return starTime;
    }

    public void setStarTime(String starTime) {
        this.starTime = starTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
