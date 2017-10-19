package com.ph.shopping.facade.product.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Service;
import com.ph.shopping.facade.mapper.ProductPropertyValSnapshotMapper;
import com.ph.shopping.facade.product.entity.ProductPropertyValSnapshot;
import com.ph.shopping.facade.product.service.IProductPropertyValSnapshotService;

/**
 * 
 * @项目：phshopping-facade-
 *
 * @描述： 商品属性值快照service层
 *
 * @作者： 杨颜光
 *
 * @创建时间：2017年5月15日 下午3:26:35
 *
 * @Copyright by 杨颜光
 */
@Component
@Service(version = "1.0.0")
public class ProductPropertyValSnapshotServiceImpl implements IProductPropertyValSnapshotService  {
		
	 @Autowired
	 ProductPropertyValSnapshotMapper productPropertyValSnapshotMapper;
	  
  	@Override
	public List<ProductPropertyValSnapshot> getProductImageSnapshotList(ProductPropertyValSnapshot propertyValSnapshot) {
			return  this.productPropertyValSnapshotMapper.select(propertyValSnapshot);
	}
	   
	@Override
	public ProductPropertyValSnapshot getProductI1mageSnapshot(ProductPropertyValSnapshot propertyValSnapshot){
			return  this.productPropertyValSnapshotMapper.selectOne(propertyValSnapshot);
	}
	
}