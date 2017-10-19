package com.ph.shopping.facade.product.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Service;
import com.ph.shopping.facade.mapper.ProductSpecificationSnapshotMapper;
import com.ph.shopping.facade.product.entity.ProductSpecificationSnapshot;
import com.ph.shopping.facade.product.service.IProductSpecificationSnapshotService;

/**
 * 
 * @项目：phshopping-service-product
 *
 * @描述： 商品规格类型快照service层
 *
 * @作者： 杨颜光
 *
 * @创建时间：2017年5月12日 下午3:53:42
 *
 * @Copyright by  杨颜光
 */
@Component
@Service(version = "1.0.0")
public class ProductSpecificationSnapshotServiceImpl implements IProductSpecificationSnapshotService {
	@Autowired
	private   ProductSpecificationSnapshotMapper productSpecificationSnapshotMapper;
	
	@Override
	public  List< ProductSpecificationSnapshot>  getProductSpecificationSnapshotList( ProductSpecificationSnapshot specificationSnapshot){
    	return  this.productSpecificationSnapshotMapper.select(specificationSnapshot);
    }
    
	@Override
	public   ProductSpecificationSnapshot  getProductSpecificationSnapshot( ProductSpecificationSnapshot specificationSnapshot){
    	return  this.productSpecificationSnapshotMapper.selectOne(specificationSnapshot);
    }
}