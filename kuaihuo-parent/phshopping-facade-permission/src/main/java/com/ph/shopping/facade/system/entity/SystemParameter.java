package com.ph.shopping.facade.system.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import java.io.Serializable;
import java.util.Date;

/**
 * 系统参数实体
 *
 * @author 郑朋
 * @create 2017/5/8
 **/
@Table(name = "ph_system_parameter")
public class SystemParameter implements Serializable{

    private static final long serialVersionUID = 258038922352464590L;

    @Id
    private Long id;
    /**
     * 参数名称
     */
    @Column(name = "parameterName")
    private String parameterName;
    /**
     * 参数备注
     */
    @Column(name = "remark")
    private String remark;
    /**
     * 参数值
     */
    @Column(name = "parameterValue")
    private Double parameterValue;
    /**
     * 单位
     */
    @Column(name = "unit")
    private String unit;

    @Column(name="createTime")
    private Date createTime;

    @Column(name="updateTime")
    private Date updateTime;
    /**
     * 修改人ID
     */
    @Column(name="updaterId")
    private Long updaterId;
    /**
     * 
     * 是否可以修改
     */
    @Column(name="isUpdate")
    private Byte isUpdate;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getParameterName() {
        return parameterName;
    }

    public void setParameterName(String parameterName) {
        this.parameterName = parameterName;
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

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

	public Long getUpdaterId() {
		return updaterId;
	}

	public void setUpdaterId(Long updaterId) {
		this.updaterId = updaterId;
	}

	public Byte getIsUpdate() {
		return isUpdate;
	}

	public void setIsUpdate(Byte isUpdate) {
		this.isUpdate = isUpdate;
	}
}
