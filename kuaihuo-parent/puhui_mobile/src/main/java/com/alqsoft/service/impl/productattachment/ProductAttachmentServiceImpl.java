package com.alqsoft.service.impl.productattachment;

import java.util.List;
import java.util.Map;

import com.alqsoft.vo.ProductPictureVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alqsoft.dao.productattachment.ProductAttachmentDao;
import com.alqsoft.entity.productattachment.ProductAttachment;
import com.alqsoft.service.productattachment.ProductAttachmentService;

@Service
@Transactional(readOnly=true)
public class ProductAttachmentServiceImpl implements ProductAttachmentService{

	@Autowired
	private ProductAttachmentDao productAttachmentDao;

	@Override
	public ProductAttachment getProductAttachmentById(Long pattid) {
		// TODO Auto-generated method stub
		return productAttachmentDao.getProductAttachmentById(pattid);
	}

	/**
	 * 根据商品id查询轮播图
	 */
	@Override
	public List<Map<String, Object>> getProductAttachmentByProductId(Long productid) {
		// TODO Auto-generated method stub
		return productAttachmentDao.getProductAttachmentByProductId(productid);
	}

	@Override
	public List<ProductPictureVo> getProductPictureVOByProductId(Long productId) {
		return productAttachmentDao.getProductPictureVOByProductId(productId);
	}

}
