/**
 *
 */
package com.ph.shopping.common.core.base;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.io.Serializable;
import java.util.*;

/**
 * @项目：phshopping-common-core
 * @描述：实体base类
 * @作者： Mr.chang
 * @创建时间：2017年3月8日
 * @Copyright @2017 by Mr.chang
 */
public class BaseEntity implements Serializable {

    public final static String[] BASE_FIELD_STRINGS = new String[]{"id", "createrId", "createTime", "updaterId", "updateTime"};
    /**
     *
     */
    private static final long serialVersionUID = -5300113985007593228L;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "createTime")
    private Date createTime;

    @Id
    private Long id;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "updateTime")
    private Date updateTime;

    @Column(name = "createrId")
    private Long createrId;

    @Column(name = "updaterId")
    private Long updaterId;

    public Long getCreaterId() {
        return createrId;
    }

    public void setCreaterId(Long createrId) {
        this.createrId = createrId;
    }

    public Long getUpdaterId() {
        return updaterId;
    }

    public void setUpdaterId(Long updaterId) {
        this.updaterId = updaterId;
    }

    /**
     * @return the createTime
     */
    public Date getCreateTime() {
        return createTime;
    }

    public Long getId() {
        return id;
    }

    /**
     * @return the updateTime
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * @param createTime the createTime to set
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @param updateTime the updateTime to set
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }


    //验证FormBean
    public List<String> validateForm() {
        List<String> errorList = null;
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<BaseEntity>> constraintViolation = validator.validate(this);
        if (constraintViolation.size() > 0)
            errorList = new ArrayList<String>(constraintViolation.size());
        for (ConstraintViolation<BaseEntity> violation : constraintViolation) {
            errorList.add(violation.getMessage());
        }
        return errorList;
    }

    public void basic(Long userId) {
        if (Objects.nonNull(userId)) {
            Date date = new Date();
            if (Objects.isNull(this.getCreaterId())) {
                this.setCreaterId(userId);
            }
            if (Objects.isNull(this.getCreateTime())) {
                this.setCreateTime(date);
            }
            this.setUpdaterId(userId);
            this.setUpdateTime(date);
        }
    }
}
