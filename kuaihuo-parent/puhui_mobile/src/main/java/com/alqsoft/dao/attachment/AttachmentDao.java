package com.alqsoft.dao.attachment;

import org.alqframework.orm.mybatis.MyBatisRepository;

import com.alqsoft.entity.attachment.Attachment;

@MyBatisRepository
public interface AttachmentDao {

	public Attachment getAttachmentById(Long imageId);


    public String getLogoImgByHunterId(Long hunterId);
}
