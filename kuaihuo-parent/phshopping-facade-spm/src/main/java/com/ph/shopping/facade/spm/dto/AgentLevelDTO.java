package com.ph.shopping.facade.spm.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
/**
 * @项目：phshopping-facade-spm
 * @描述：代理商等级信息表
 * @作者 何文浪
 * @时间：2017-5-15
 * @version: 2.1
 */
public class AgentLevelDTO  implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -4832405475184535998L;

	/** 主键 */
    private Long id;

    /** 级别名称 */
    private String levelName;

    /** 状态：0待审核，1审核通过，2被驳回 */
    private Integer status;

    /** 创建时间 */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    private Date createTime;

    /** 修改时间 */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    private Date updateTime;

    /** 创建人Id */
    private Long createrId;

    /**  */
    private Long updaterId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    
}