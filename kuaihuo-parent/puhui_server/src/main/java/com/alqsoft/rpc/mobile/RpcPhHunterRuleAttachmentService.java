package com.alqsoft.rpc.mobile;

import org.alqframework.result.Result;

import com.alqsoft.entity.attachment.Attachment;
import com.alqsoft.entity.phhunterruleattachment.PhHunterRuleAttachment;

public interface RpcPhHunterRuleAttachmentService {
	/**
	 * 上传图片
	 * 
	 * @param urlfile
	 * @param field
	 * @param ext
	 * @param module
	 * @return
	 */    
	public PhHunterRuleAttachment mobileUploadAttachment(PhHunterRuleAttachment phHunterRuleAttachment);
	/*
	 * 修改图片，营业执照，身份证照
	 */
	public Result updateAttachment(Long saveimageid,Long updateimageid,Long hunterid,int type);
	
	//关联图图片
		public Result addAttachment(Long hunterid, Long imageid);
}
