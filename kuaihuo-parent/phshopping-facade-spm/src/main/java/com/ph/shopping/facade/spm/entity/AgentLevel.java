package com.ph.shopping.facade.spm.entity;

import javax.persistence.Column;
import javax.persistence.Table;

import com.ph.shopping.common.core.base.BaseEntity;

/**
 * @项目：phshopping-facade-spm
 * @描述：代理商等级信息表
 * @作者 何文浪
 * @时间：2017-5-15
 * @version: 2.1
 */
@Table(name = "ph_agent_level")
public class AgentLevel  extends BaseEntity{

    /**
	 * 
	 */
	private static final long serialVersionUID = 3679350961507524829L;

	/** 级别名称 */
	@Column(name ="levelName" )
    private String levelName;

    /** 状态：0待审核，1审核通过，2被驳回 */
	@Column(name ="status" )
    private Integer status;

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
}