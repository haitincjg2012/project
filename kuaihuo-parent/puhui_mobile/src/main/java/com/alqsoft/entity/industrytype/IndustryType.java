
package com.alqsoft.entity.industrytype;

import com.alqsoft.entity.attachment.Attachment;

import java.util.Date;

import org.alqframework.orm.hibernate.IdEntity;

/**
 * Date:     2017年2月27日 下午2:03:20 <br/>
 * @author   zhangcan
 * @version  行业分类
 * @since    JDK 1.8
 * @see 	 
 */

public class IndustryType extends IdEntity{
	
	private String name;//行业分类名称
	
	private IndustryType parentId;//父级id

	private Attachment attachment;//关联对应的附件
	
	private Integer isDelete;//0未删，1删除
	
	private Integer sortnum;//置顶排序
	
	private Date sortnumtime;//排序时间
	
    private Integer isTop;//是否置于首页  0不置首页  1置于首页
	
	public Integer getIsTop() {
		return isTop;
	}

	public void setIsTop(Integer isTop) {
		this.isTop = isTop;
	}

	public Date getTopDate() {
		return topDate;
	}

	public void setTopDate(Date topDate) {
		this.topDate = topDate;
	}

	private Date topDate;//置顶时间  首页按此字段排序 显示最新的16个
	
	

	public Integer getSortnum() {
		return sortnum;
	}

	public void setSortnum(Integer sortnum) {
		this.sortnum = sortnum;
	}

	public Date getSortnumtime() {
		return sortnumtime;
	}

	public void setSortnumtime(Date sortnumtime) {
		this.sortnumtime = sortnumtime;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public IndustryType getParentId() {
		return parentId;
	}

	public void setParentId(IndustryType parentId) {
		this.parentId = parentId;
	}

	public Attachment getAttachment() {
		return attachment;
	}

	public void setAttachment(Attachment attachment) {
		this.attachment = attachment;
	}

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}
	
	
}

