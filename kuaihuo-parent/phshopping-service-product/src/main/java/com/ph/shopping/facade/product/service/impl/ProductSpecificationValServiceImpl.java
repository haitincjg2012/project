package com.ph.shopping.facade.product.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Service;
import com.ph.shopping.facade.mapper.ProductSpecificationValMapper;
import com.ph.shopping.facade.product.entity.ProductSpecificationVal;
import com.ph.shopping.facade.product.service.IProductSpecificationValService;

/**
 * 
 * @项目：phshopping-service-product
 *
 * @描述： 商品规格值service层
 *
 * @作者： 杨颜光
 *
 * @创建时间：2017年5月12日 下午3:53:42
 *
 * @Copyright by  杨颜光
 */
@Component
@Service(version = "1.0.0")
public class ProductSpecificationValServiceImpl implements IProductSpecificationValService  {
	@Autowired
	private  ProductSpecificationValMapper productSpecificationValMapper;
	
	
	@Override
	public  List< ProductSpecificationVal>  getProductSpecificationValtList( ProductSpecificationVal specificationVal){
    	return  this.productSpecificationValMapper.select(specificationVal);
    }
    
	@Override
	public   ProductSpecificationVal  getProductSpecificationVal( ProductSpecificationVal specificationVal){
    	return  this.productSpecificationValMapper.selectOne(specificationVal);
    }
}