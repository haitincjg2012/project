package com.alqsoft.rpc.mobile;

import com.alqsoft.entity.productattachment.ProductAttachment;

public interface RpcProductAttachmentService {

	/**
	 * 保存商品图片
	 * @param productAttachment
	 * @return
	 */
	public ProductAttachment mobileUploadProductAttachment(ProductAttachment productAttachment);
}
