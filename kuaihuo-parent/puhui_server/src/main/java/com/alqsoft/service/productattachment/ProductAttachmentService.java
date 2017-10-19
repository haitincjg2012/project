package com.alqsoft.service.productattachment;

import java.util.Collection;
import java.util.List;

import org.alqframework.orm.BaseService;
import org.springframework.data.repository.query.Param;

import com.alqsoft.entity.productattachment.ProductAttachment;

public interface ProductAttachmentService extends BaseService<ProductAttachment>{

	/**
	 * 商品轮播图片和商品id关联
	 * @param procutAttachmentids
	 * @param productid
	 */
	public void updateProcutAttachmentWithProduct(Collection<Long> procutAttachmentids,Long productid);
	
	
	/**
	 * 查询商品的轮播图片
	 * @param productid
	 * @return
	 */
	public List<ProductAttachment> findProductAttachmentByProductId(Long productid);
	
	/**
	 * 删除轮播图片
	 * @param id
	 */
	public void deleteProductAttachmentById(Collection<Long> ids);
}
