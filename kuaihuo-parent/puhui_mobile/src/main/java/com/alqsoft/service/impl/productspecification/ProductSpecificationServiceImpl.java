package com.alqsoft.service.impl.productspecification;

import com.alqsoft.dao.productspecification.ProductSpecificationDao;
import com.alqsoft.entity.productspecification.ProductSpecification;
import com.alqsoft.service.productspecification.ProductSpecificationService;
import com.alqsoft.vo.ProductSpecificationVO;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *  商品规格管理
 * @author Xuejizheng
 * @date 2017-03-03 10:12
 */
@Service
@Transactional(readOnly = true)
public class ProductSpecificationServiceImpl implements ProductSpecificationService {

	@Autowired
	private ProductSpecificationDao productSpecificationDao;

	@Override
	public ProductSpecificationVO getProductSpecificationVO(Long pid) {
		return productSpecificationDao.getProductSpecificationVO(pid);
	}

	@Override
	public Map<String, Object> getProductSpecificationById(Long pId) {
		return this.productSpecificationDao.getProductSpecificationById(pId);
	}

	@Override
	public Map<String, Object> getHIdByPsId(Long pId) {
		return this.productSpecificationDao.getHIdByPsId(pId);
	}

	/**
	 * 根据商品id查询商品规格
	 */
	@Override
	public List<ProductSpecification> getProductSpecificationByProductId(Long productid) {
		// TODO Auto-generated method stub
		return productSpecificationDao.getProductSpecificationByProductId(productid);
	}

	@Override
	public List<ProductSpecificationVO> getProductSpecificationVOList(Long productId) {
		return productSpecificationDao.getProductSpecificationVOList(productId);
	}
}
