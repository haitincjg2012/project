package com.ph.shopping.facade.member.vo;


import com.ph.shopping.facade.member.entity.Attachment;

import java.io.Serializable;

/**
 * @version 2.1
 * @项目：phshopping-parent
 * @描述： 批发行业的首页
 * @作者： wudi
 * @创建时间： 2017/5/23
 * @Copyright by xkw
 */
public class IndustryVO implements Serializable {

   private String createdTime;//创建的时间

    private Long id ;//行业的分类

    private String name;//行业分类名称

    private Attachment attachment;//关联对应的附件

    private Integer isDelete;//0未删，1删除

    private Integer sortnum;//置顶排序

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getSortnumtime() {
        return sortnumtime;
    }

    public void setSortnumtime(String sortnumtime) {
        this.sortnumtime = sortnumtime;
    }

    private String sortnumtime;//排序时间

    private Integer isTop;//是否置于首页  0不置首页  1置于首页

   private String topDate;//置顶时间  首页按此字段排序 显示最新的16个

    public String getTopDate() {
        return topDate;
    }

    public void setTopDate(String topDate) {
        this.topDate = topDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Integer getSortnum() {
        return sortnum;
    }

    public void setSortnum(Integer sortnum) {
        this.sortnum = sortnum;
    }


    public Integer getIsTop() {
        return isTop;
    }

    public void setIsTop(Integer isTop) {
        this.isTop = isTop;
    }





}
