package com.ph.shopping.facade.system.entity;

import com.ph.shopping.common.core.customenum.SystemOperateEnum;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import java.io.Serializable;
import java.util.Date;

/**
 * 系统日志实体
 *
 * @author 郑朋
 * @create 2017/5/8
 **/
@Table(name = "ph_system_log")
public class SystemLog implements Serializable{

    private static final long serialVersionUID = 8804083059982096534L;
    /**
     * 创建时间
     */
    @Column(name="createTime")
    private Date createTime;

    @Id
    private Long id;
    /**
     * 操作账号
     */
    @Column(name = "operateAccount")
    private String operateAccount;
    /**
     * 操作类型 {@link SystemOperateEnum}
     */
    @Column(name = "operateType")
    private Byte operateType;
    /**
     * 操作内容
     */
    @Column(name = "operateContent")
    private String operateContent;

    /**
     * 创建人id
     */
    @Column(name="createrId")
    private Long createrId;

    /**
     * 创建人名称
     */
    @Column(name="createrName")
    private String createrName;

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

    public Long getCreaterId() {
        return createrId;
    }

    public void setCreaterId(Long createrId) {
        this.createrId = createrId;
    }

    public String getCreaterName() {
        return createrName;
    }

    public void setCreaterName(String createrName) {
        this.createrName = createrName;
    }
}
