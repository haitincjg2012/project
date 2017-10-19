package com.ph.shopping.facade.product.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Service;
import com.ph.shopping.facade.mapper.ProductPropertyValMapper;
import com.ph.shopping.facade.product.entity.ProductPropertyVal;
import com.ph.shopping.facade.product.service.IProductPropertyValService;

/**
 * 
 * @项目：phshopping-facade-
 *
 * @描述：商品属性值实现类
 *
 * @作者：杨颜光
 *
 * @创建时间：2017年5月15日 下午3:30:08
 *
 * @Copyright by 杨颜光
 */
@Component
@Service(version = "1.0.0")
public class ProductPropertyValServiceImpl implements IProductPropertyValService {

   @Autowired
	private ProductPropertyValMapper phProductPropertyValMapper;
   
   @Override
   public List<ProductPropertyVal> getProductPropertyValList(ProductPropertyVal productPropertyVal) {
		return  this.phProductPropertyValMapper.select(productPropertyVal);
	}
   
   @Override
	public ProductPropertyVal getProductPropertyVal(ProductPropertyVal productPropertyVal){
		return  this.phProductPropertyValMapper.selectOne(productPropertyVal);
	}
}
