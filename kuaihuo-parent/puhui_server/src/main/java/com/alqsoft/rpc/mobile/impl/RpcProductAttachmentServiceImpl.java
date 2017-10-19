package com.alqsoft.rpc.mobile.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.alqsoft.entity.productattachment.ProductAttachment;
import com.alqsoft.rpc.mobile.RpcProductAttachmentService;
import com.alqsoft.service.productattachment.ProductAttachmentService;

/**
 * 商品图片上传
 * @author Administrator
 *
 */
@Controller
public class RpcProductAttachmentServiceImpl implements RpcProductAttachmentService{
	
	@Autowired
	private ProductAttachmentService productAttachmentService;
	
	@Override
	public ProductAttachment mobileUploadProductAttachment(ProductAttachment productAttachment) {
		// TODO Auto-generated method stub
		return productAttachmentService.saveAndModify(productAttachment);
	}

}
