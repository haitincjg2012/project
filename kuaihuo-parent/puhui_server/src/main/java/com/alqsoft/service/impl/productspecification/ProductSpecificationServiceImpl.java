package com.alqsoft.service.impl.productspecification;

import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.alqsoft.dao.productspecification.ProductSpecificationDao;
import com.alqsoft.entity.order.Order;
import com.alqsoft.entity.product.Product;
import com.alqsoft.entity.productspecification.ProductSpecification;
import com.alqsoft.service.product.ProductService;
import com.alqsoft.service.productspecification.ProductSpecificationService;

@Service
@Transactional(readOnly=true)
public class ProductSpecificationServiceImpl implements ProductSpecificationService {

	@Autowired
	private ProductSpecificationDao productSpecificationDao;
	@Autowired
	private ProductService productService;
	private static final Logger logger = LoggerFactory.getLogger(ProductSpecificationServiceImpl.class);	
	@Override
	public boolean delete(Long arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ProductSpecification get(Long arg0) {
		// TODO Auto-generated method stub
		return this.productSpecificationDao.findOne(arg0);
	}

	@Override
	@Transactional
	public ProductSpecification saveAndModify(ProductSpecification arg0) {
		// TODO Auto-generated method stub
		return this.productSpecificationDao.save(arg0);
	}

	@Override
	@Transactional
	public void updateNum(ProductSpecification productSpecification, int count) {
		try {
			/*Long num = 0L;    无库存概念
			if(productSpecification.getNum().longValue() >= count){
				num = productSpecification.getNum().longValue()-count;
			}
			productSpecification.setNum(num);*/
			Long saleNum = productSpecification.getSaleNum()==null?0L:productSpecification.getSaleNum();
			productSpecification.setSaleNum(saleNum.longValue()+count);
			
			Product product = this.productService.get(productSpecification.getProduct().getId());
			Long sale = product.getSaleNum()==null?0L:product.getSaleNum();
			product.setSaleNum(sale.longValue()+count);
			this.productService.saveAndModify(product);
			this.saveAndModify(productSpecification);
			logger.info(productSpecification.getId()+"库存数量更改成功");
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			logger.info(productSpecification.getId()+"库存数量更改成功");
		}
	}

	/**
	 * 商品批量分类---更新此商品规格关联的商品分类id
	 */
	@Override
	@Transactional
	public void updateProductSpecification(Collection<Long> productids, Long kindid) {
		// TODO Auto-generated method stub
		productSpecificationDao.updateProductSpecification(productids, kindid);
	}

	@Override
	@Transactional
	public ProductSpecification getLockRow(Long id) {
		return this.productSpecificationDao.getLockRow(id);
	}

	@Override
	@Transactional
	public void updateNumByBack(Order order) {
		ProductSpecification productSpecification = this.getLockRow(order.getProductSpecification().getId());
		try {
			/*Long n = productSpecification.getNum()==null?0L:productSpecification.getNum();
			Long num  = n.longValue()+order.getNum().intValue();
			productSpecification.setNum(num);*/
			Long saleNum = productSpecification.getSaleNum()==null?0L:productSpecification.getSaleNum();
			productSpecification.setSaleNum(saleNum.longValue()-order.getNum().intValue());
			Product product = this.productService.get(productSpecification.getProduct().getId());
			Long sale = product.getSaleNum()==null?0L:product.getSaleNum();
			product.setSaleNum(sale.longValue()-order.getNum().intValue());
			this.productService.saveAndModify(product);
			this.saveAndModify(productSpecification);
			logger.info(productSpecification.getId()+"库存数量更改成功");
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			logger.info(productSpecification.getId()+"库存数量更改成功");
		}
		
	}

	/**
	 * 查询商品的所有商品规格，未删除
	 */
	@Override
	public List<ProductSpecification> findProductSpecificationByProductId(Long productid) {
		// TODO Auto-generated method stub
		return productSpecificationDao.findProductSpecificationByProductId(productid);
	}

	/**
	 * 逻辑删除商品规格
	 */
	@Override
	public void deleteProductSpecificationByIds(Collection<Long> ids) {
		// TODO Auto-generated method stub
		productSpecificationDao.deleteProductSpecificationByIds(ids);
	}

	
	

}
