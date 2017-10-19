package com.ph.shopping.facade.product.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Service;
import com.ph.shopping.facade.mapper.ProductSpecificationValSnapshotMapper;
import com.ph.shopping.facade.product.entity.ProductSpecificationValSnapshot;
import com.ph.shopping.facade.product.service.IProductSpecificationValSnapshotService;

/**
 * 
 * @项目：phshopping-service-product
 *
 * @描述： 商品规格值快照service层
 *
 * @作者： 杨颜光
 *
 * @创建时间：2017年5月12日 下午3:53:42
 *
 * @Copyright by  杨颜光
 */
@Component
@Service(version = "1.0.0")
public class ProductSpecificationValSnapshotServiceImpl implements IProductSpecificationValSnapshotService  {
	@Autowired
	private   ProductSpecificationValSnapshotMapper productSpecificationValSnapshotMapper;
	
	@Override
	public  List< ProductSpecificationValSnapshot>  getProductSpecificationValtList( ProductSpecificationValSnapshot specificationValSnapshot){
    	return  this.productSpecificationValSnapshotMapper.select(specificationValSnapshot);
    }
    
	@Override
	public   ProductSpecificationValSnapshot  getProductSpecificationVal( ProductSpecificationValSnapshot specificationValSnapshot){
    	return  this.productSpecificationValSnapshotMapper.selectOne(specificationValSnapshot);
    }
}