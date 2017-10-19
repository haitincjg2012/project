package com.ph.shopping.facade.system.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ph.shopping.common.core.customenum.SystemOperateEnum;
import java.io.Serializable;
import java.util.Date;

/**
 * 系统日志列表VO
 *
 * @author 郑朋
 * @create 2017/5/8
 **/
public class SystemLogVO implements Serializable{

    private static final long serialVersionUID = 6211048042119590614L;

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
     * 操作内容
     */
    private String operateContent;

    /**
     * 创建时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;

    /**
     * id
     */
    private Long id;

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

    public String getOperateContent() {
        return operateContent;
    }

    public void setOperateContent(String operateContent) {
        this.operateContent = operateContent;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
