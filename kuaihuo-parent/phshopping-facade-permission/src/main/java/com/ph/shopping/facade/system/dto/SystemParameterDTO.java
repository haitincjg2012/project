package com.ph.shopping.facade.system.dto;

import java.io.Serializable;

/**
 * 查询系统参数DTO
 *
 * @author 郑朋
 * @create 2017/5/9
 **/
public class SystemParameterDTO implements Serializable {
    private static final long serialVersionUID = 278673401114525198L;

    /**
     * id
     */
    private Long id;

    /**
     * 参数名称
     */
    private String parameterName;
    /**
     * 参数备注
     */
    private String remark;
    /**
     * 参数值
     */
    private Double parameterValue;

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
}
