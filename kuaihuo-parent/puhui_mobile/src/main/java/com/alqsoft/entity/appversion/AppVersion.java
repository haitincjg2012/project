package com.alqsoft.entity.appversion;

import org.alqframework.orm.hibernate.IdEntity;

import com.alqsoft.entity.attachment.Attachment;

/**
 * Date:     2017年2月28日  14:14:41 <br/>
 * @author   ding
 * @version  软件更新版本信息
 * @since    JDK 1.8
 * @see 	 
 */
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
