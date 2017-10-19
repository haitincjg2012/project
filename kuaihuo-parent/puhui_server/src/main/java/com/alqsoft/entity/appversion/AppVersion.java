package com.alqsoft.entity.appversion;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.alqframework.orm.hibernate.IdEntity;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.alqsoft.entity.attachment.Attachment;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Date:     2017年2月28日  14:14:41 <br/>
 * @author   ding
 * @version  软件更新版本信息
 * @since    JDK 1.8
 * @see
 * @used
 */
@Entity
@Table(name = "alq_app_version")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"}) 
public class AppVersion extends IdEntity{

	private String version;//版本号
	
	private Attachment attachment;//文件
	
	private Integer isUsed;//是否使用该版本 1是  0不是
	
	private Integer isMustToUpdate;//是否强制更新 1强制更新 0不强制更新
	
	public Integer getIsUsed() {
		return isUsed;
	}

	public void setIsUsed(Integer isUsed) {
		this.isUsed = isUsed;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	@ManyToOne(cascade =CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "attachment_id")
	public Attachment getAttachment() {
		return attachment;
	}

	public void setAttachment(Attachment attachment) {
		this.attachment = attachment;
	}

	public Integer getIsMustToUpdate() {
		return isMustToUpdate;
	}

	public void setIsMustToUpdate(Integer isMustToUpdate) {
		this.isMustToUpdate = isMustToUpdate;
	}
	
}
