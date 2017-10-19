package com.alqsoft.service.impl.productattachment;

import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alqsoft.dao.productattachment.ProductAttachmentDao;
import com.alqsoft.entity.productattachment.ProductAttachment;
import com.alqsoft.service.productattachment.ProductAttachmentService;

/**
 * 商品图片上传
 * @author Administrator
 *
 */
@Service
@Transactional(readOnly=true)
public class ProductAttachmentServiceImpl implements ProductAttachmentService{
	
	private static Logger logger = LoggerFactory.getLogger(ProductAttachmentServiceImpl.class);
	
	@Autowired
	private ProductAttachmentDao productAttachmentDao;
	
	@Override
	@CacheEvict(key="'alq_product_attachment'+#arg0",value="alq_product_attachment")
	public boolean delete(Long arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	@Cacheable(key="'alq_product_attachment'+#arg0",value="alq_product_attachment")
	public ProductAttachment get(Long arg0) {
		// TODO Auto-generated method stub
		return productAttachmentDao.findOne(arg0);
	}

	@Transactional
	@Override
	@CacheEvict(key="'alq_product_attachment'+#arg0.id",value="alq_product_attachment")
	public ProductAttachment saveAndModify(ProductAttachment arg0) {
		// TODO Auto-generated method stub
		return productAttachmentDao.save(arg0);
	}

	/**
	 * 商品轮播图片和商品id关联
	 */
	@Override
	public void updateProcutAttachmentWithProduct(Collection<Long> procutAttachmentids, Long productid) {
		// TODO Auto-generated method stub
		productAttachmentDao.updateProcutAttachmentWithProduct(procutAttachmentids, productid);
	}

	@Override
	public List<ProductAttachment> findProductAttachmentByProductId(Long productid) {
		// TODO Auto-generated method stub
		return productAttachmentDao.findProductAttachmentByProductId(productid);
	}

	@Override
	public void deleteProductAttachmentById(Collection<Long> ids) {
		// TODO Auto-generated method stub
		productAttachmentDao.deleteProductAttachmentById(ids);
	}


}
