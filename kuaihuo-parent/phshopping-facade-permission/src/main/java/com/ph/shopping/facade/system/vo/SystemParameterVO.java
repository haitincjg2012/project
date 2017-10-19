package com.ph.shopping.facade.system.vo;

import java.io.Serializable;

/**
 * 系统参数VO
 *
 * @author 郑朋
 * @create 2017/5/8
 **/
public class SystemParameterVO implements Serializable {

    private static final long serialVersionUID = -7276329604495810054L;

    /**
     * id
     */
    private Long id;

    /**
     * 参数备注
     */
    private String remark;
    /**
     * 参数值
     */
    private Double parameterValue;

    /**
     * 参数名称
     */
    private String parameterName;
    /**
     * 是否可以修改（0、不能修改 1、可以修改）
     */
    private Byte isUpdate;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Double getParameterValue() {
        return parameterValue;
    }

    public void setParameterValue(Double parameterValue) {
        this.parameterValue = parameterValue;
    }

    public String getParameterName() {
        return parameterName;
    }

    public void setParameterName(String parameterName) {
        this.parameterName = parameterName;
    }

	public Byte getIsUpdate() {
		return isUpdate;
	}

	public void setIsUpdate(Byte isUpdate) {
		this.isUpdate = isUpdate;
	}
}
