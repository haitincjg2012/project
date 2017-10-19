package com.alqsoft.service.impl.productdetail;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alqsoft.dao.productdetail.ProductDetailDao;
import com.alqsoft.entity.productdetail.ProductDetail;
import com.alqsoft.service.productdetail.ProductDetailService;


@Service
@Transactional(readOnly=true)
public class ProductDetailServiceImpl implements ProductDetailService{
	
	@Autowired
	private ProductDetailDao productDetailDao;
	
	@CacheEvict(key="'alq_product_detail'+#arg0",value="alq_product_detail")
	@Override
	public boolean delete(Long arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	@Cacheable(key="'alq_product_detail'+#arg0",value="alq_product_detail")
	public ProductDetail get(Long arg0) {
		// TODO Auto-generated method stub
		return productDetailDao.findOne(arg0);
	}

	@CacheEvict(key="'alq_product_detail'+#arg0.id",value="alq_product_detail")
	@Transactional
	@Override
	public ProductDetail saveAndModify(ProductDetail arg0) {
		// TODO Auto-generated method stub
		return productDetailDao.save(arg0);
	}

	/**
	 * 查询该商品的商品详情信息只查图片type为1类型的
	 */
	@Override
	public List<ProductDetail> findProductDetailByProductIdToPicture(Long productid) {
		// TODO Auto-generated method stub
		return productDetailDao.findProductDetailByProductIdToPicture(productid);
	}

	@Override
	public void deleteProductDetailByIds(Collection<Long> ids, Long productids) {
		// TODO Auto-generated method stub
		productDetailDao.deleteProductDetailByIds(ids, productids);
	}

}
