package com.alqsoft.entity.hunternotice;

import com.alqsoft.entity.IdEntity;

import java.util.Date;
/**
 * @author   chen
 * @version  批发商公告
 * @since    JDK 1.8
 * @see
 * @used
 */
public class HunterNotice extends IdEntity{
    private Long hunterId;//批发商

    private String content;//文本内容

    private Integer  isDeleted;//0 null  不删除  1删除

    private Integer  isRelease;//0 null  未发布  1发布

    private Date releaseTime;// 公告日期

    public Integer getIsRelease() {
        return isRelease;
    }

    public void setIsRelease(Integer isRelease) {
        this.isRelease = isRelease;
    }

    public Date getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(Date releaseTime) {
        this.releaseTime = releaseTime;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Long getHunterId() {
        return hunterId;
    }

    public void setHunterId(Long hunterId) {
        this.hunterId = hunterId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
