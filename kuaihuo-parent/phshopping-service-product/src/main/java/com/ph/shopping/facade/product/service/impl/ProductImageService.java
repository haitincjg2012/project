package com.ph.shopping.facade.product.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Service;
import com.ph.shopping.facade.mapper.ProductImageMapper;
import com.ph.shopping.facade.product.entity.ProductImage;
import com.ph.shopping.facade.product.service.IProductImageService;

/**
 * 
 * @项目：phshopping-facade-
 *
 * @描述：商品图片实现类
 *
 * @作者： 杨颜光
 *
 * @创建时间：2017年5月15日 下午4:32:30
 *
 * @Copyright by 杨颜光
 */
@Component
@Service(version = "1.0.0")
public class ProductImageService implements IProductImageService {
	
	@Autowired
	private ProductImageMapper phProductImageMapper;
	
	@Override
	public List<ProductImage> getProductImageList(ProductImage productImage)  {
		return phProductImageMapper.select(productImage);
	}

	@Override
	public ProductImage getProductImage(ProductImage productImage) {
		return phProductImageMapper.selectOne(productImage);
	}
}
