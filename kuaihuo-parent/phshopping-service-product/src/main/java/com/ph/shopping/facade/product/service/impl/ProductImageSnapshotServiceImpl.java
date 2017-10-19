package com.ph.shopping.facade.product.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Service;
import com.ph.shopping.facade.mapper.ProductImageSnapshotMapper;
import com.ph.shopping.facade.product.entity.ProductImageSnapshot;
import com.ph.shopping.facade.product.service.IProductImageSnapshotService;

/**
 * 
 * @项目：phshopping-facade-
 *
 * @描述： 商品图片快照service层
 *
 * @作者：杨颜光
 *
 * @创建时间：2017年5月15日 下午8:00:39
 *
 * @Copyright  杨颜光
 */
@Component
@Service(version = "1.0.0")
public class ProductImageSnapshotServiceImpl implements IProductImageSnapshotService{
	
	  @Autowired
	  ProductImageSnapshotMapper imageSnapshotMapper;
	  
   	@Override
	public List<ProductImageSnapshot> getProductImageSnapshotList(ProductImageSnapshot imageSnapshot) {
			return  this.imageSnapshotMapper.select(imageSnapshot);
		}
	   
	@Override
	public ProductImageSnapshot getProductI1mageSnapshot(ProductImageSnapshot imageSnapshot){
			return  this.imageSnapshotMapper.selectOne(imageSnapshot);
		}
}