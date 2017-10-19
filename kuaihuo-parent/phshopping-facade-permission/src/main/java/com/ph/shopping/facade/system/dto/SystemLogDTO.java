package com.ph.shopping.facade.system.dto;

import org.hibernate.validator.constraints.NotBlank;

import com.ph.shopping.common.core.base.BaseValidate;
import com.ph.shopping.common.core.customenum.SystemOperateEnum;

import javax.validation.constraints.NotNull;

/**
 * 系统日志新增入参
 *
 * @author 郑朋
 * @create 2017/5/8
 **/
public class SystemLogDTO extends BaseValidate{

    private static final long serialVersionUID = -1844430629146510405L;

    /**
     * 创建人ID
     */
    @NotNull(message = "创建人ID不能为空")
    private Long createrId;
    /**
     * 操作账号
     */
    @NotBlank(message = "操作账号不能为空")
    private String operateAccount;
    /**
     * 操作类型 {@link SystemOperateEnum}
     */
    @NotNull(message = "操作类型 不能为空")
    private Byte operateType;
    /**
     * 操作内容
     */
    private String operateContent;
    /**
     * 操作人名称
     */
    private String createrName;
    
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

    public String getCreaterName() {
		return createrName;
	}

	public void setCreaterName(String createrName) {
		this.createrName = createrName;
	}

	public Long getCreaterId() {
		return createrId;
	}

	public void setCreaterId(Long createrId) {
		this.createrId = createrId;
	}
	
}
