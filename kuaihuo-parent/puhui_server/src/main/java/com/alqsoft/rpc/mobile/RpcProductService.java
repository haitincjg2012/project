package com.alqsoft.rpc.mobile;

import java.util.List;

import org.alqframework.result.Result;

import com.alqsoft.entity.product.Product;
import com.alqsoft.entity.productattachment.ProductAttachment;
import com.alqsoft.entity.productdetail.ProductDetail;
import com.alqsoft.entity.productspecification.ProductSpecification;

/**
 * 商品
 * @author Administrator
 *
 */
public interface RpcProductService {

	
	/**
	 * 商品批量上架或下架
	 * @author Administrator
	 *
	 */
	public Result updateBatchProductSaleOrCancel(Long hunterid, String productid,Integer type);
	
	
	/**
	 * 商品批量分类
	 * @param hunterid
	 * @param productid
	 * @param kindid
	 * @return
	 */
	public Result updateBatchProductKind(Long hunterid, String productid, Long kindid);


	/**
	 * 商品添加
	 * @param prouct
	 * @param productSpecification
	 * @param productDetail
	 * @return
	 */
	public Result saveHunterProduct(Product prouct,List<ProductSpecification> productSpecification,List<ProductDetail> productDetail,List<ProductAttachment> productAttachment);
}
