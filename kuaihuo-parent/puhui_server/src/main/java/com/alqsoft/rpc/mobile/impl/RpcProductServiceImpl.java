package com.alqsoft.rpc.mobile.impl;

import java.util.List;

import org.alqframework.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.alqsoft.entity.product.Product;
import com.alqsoft.entity.productattachment.ProductAttachment;
import com.alqsoft.entity.productdetail.ProductDetail;
import com.alqsoft.entity.productspecification.ProductSpecification;
import com.alqsoft.rpc.mobile.RpcProductService;
import com.alqsoft.service.product.ProductService;

@Controller
public class RpcProductServiceImpl implements RpcProductService{
	
	@Autowired
	private ProductService productService;

	/**
	 * 商品管理---批量上架和下架
	 * 1：下架，2：上架
	 */
	@Override
	public Result updateBatchProductSaleOrCancel(Long hunterid, String productid,Integer type) {
		// TODO Auto-generated method stub
		return productService.updateBatchProductSaleOrCancel(hunterid,productid,type);
	}

	/**
	 * 商品管理---商品的批量分类
	 */
	@Override
	public Result updateBatchProductKind(Long hunterid, String productid, Long kindid) {
		// TODO Auto-generated method stub
		return productService.updateBatchProductKind(hunterid,productid,kindid);
	}
	
	/**
	 * 商品管理---商品添加
	 */
	@Override
	public Result saveHunterProduct(Product prouct,List<ProductSpecification> productSpecification,List<ProductDetail> productDetail,List<ProductAttachment> productAttachment) {
		// TODO Auto-generated method stub
		return productService.saveHunterProduct(prouct,productSpecification,productDetail,productAttachment);
	}

	
}
