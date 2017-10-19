package com.ph.shopping.facade.product.service.impl;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.ph.shopping.facade.product.dto.ProductDTO;
import com.ph.shopping.facade.product.vo.ProductSpecificationVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Service;
import com.ph.shopping.facade.mapper.ProductSpecificationMapper;
import com.ph.shopping.facade.product.entity.ProductSpecification;
import com.ph.shopping.facade.product.service.IProductSpecificationService;

/**
 * 
 * @项目：phshopping-service-product
 *
 * @描述： 商品规格类型service层
 *
 * @作者： 杨颜光
 *
 * @创建时间：2017年5月12日 下午3:53:42
 *
 * @Copyright by  杨颜光
 */
@Component
@Service(version="1.0.0")
public class ProductSpecificationServiceImpl implements IProductSpecificationService {

	private static Logger logger = LoggerFactory.getLogger(ProductSpecificationServiceImpl.class);

	@Autowired
	private ProductSpecificationMapper productSpecificationMapper;
	
	@Override
	public List<ProductSpecification> getProductSpecificationList(ProductSpecification specification){
    	return this.productSpecificationMapper.select(specification);
    }
    
	@Override
	public ProductSpecification getProductSpecification(ProductSpecification specification){
    	return this.productSpecificationMapper.selectOne(specification);
    }

	/**
	 * 查询规格名称以及旗下规格的值
	 */
	@Override
	public List<ProductSpecificationVO> getProductSpecificationVOListByProductId(ProductDTO productDTO) {
		logger.info("进入查询规格名称以及旗下规格的值方法:" + JSON.toJSONString(productDTO));
		List<ProductSpecificationVO> list = productSpecificationMapper.getProductSpecificationVOListByProductId(productDTO);
		logger.info("查询规格名称以及旗下规格的值成功: " + JSON.toJSONString(list));
		return list;
	}
}