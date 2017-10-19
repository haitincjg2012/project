package com.ph.shopping.facade.spm.entity;

import com.ph.shopping.common.core.base.BaseEntity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Table;
/**
 * @项目：phshopping-facade-spm
 * @描述：行业分类
 * @作者 chen
 * @时间：2017-8-21
 * @version: 2.1
 */
@Table(name = "ph_industry_type")
public class IndustryType extends BaseEntity{

    /**
	 * 
	 */
	private static final long serialVersionUID = 9062731938520990166L;

	/** 是否删除 */
	@Column(name ="is_delete" )
    private int is_delete;

    /** 是否置顶 */
	@Column(name ="is_top" )
    private int is_top;

    /** 分类名称 */
	@Column(name ="name" )
    private String name;

    /** 排序 */
	@Column(name ="sortnum" )
    private int sortnum;

	@Column(name ="sortnumtime" )
    private Date sortnumtime;

	@Column(name ="top_date" )
    private Date top_date;

	@Column(name ="attachment_id" )
    private Long attachment_id;

	public int getIs_delete() {
		return is_delete;
	}

	public void setIs_delete(int is_delete) {
		this.is_delete = is_delete;
	}

	public int getIs_top() {
		return is_top;
	}

	public void setIs_top(int is_top) {
		this.is_top = is_top;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSortnum() {
		return sortnum;
	}

	public void setSortnum(int sortnum) {
		this.sortnum = sortnum;
	}

	public Date getSortnumtime() {
		return sortnumtime;
	}

	public void setSortnumtime(Date sortnumtime) {
		this.sortnumtime = sortnumtime;
	}

	public Date getTop_date() {
		return top_date;
	}

	public void setTop_date(Date top_date) {
		this.top_date = top_date;
	}

	public Long getAttachment_id() {
		return attachment_id;
	}

	public void setAttachment_id(Long attachment_id) {
		this.attachment_id = attachment_id;
	}
    
}