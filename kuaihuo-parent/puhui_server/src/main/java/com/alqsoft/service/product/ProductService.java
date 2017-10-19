package com.alqsoft.service.product;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.alqsoft.entity.hotrecommend.HotRecommend;
import com.alqsoft.entity.order.Order;

import org.alqframework.orm.BaseService;
import org.alqframework.result.Result;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.alqsoft.entity.product.Product;
import com.alqsoft.entity.productattachment.ProductAttachment;
import com.alqsoft.entity.productdetail.ProductDetail;
import com.alqsoft.entity.productspecification.ProductSpecification;

/**
 * 商品管理
 * @author Administrator
 *
 */
public interface ProductService extends BaseService<Product>{
	
	public Product getRowLock(Long id);
	
	/**
	 * 商品管理---批量上架和下架
	 * @param hunterid
	 * @param productid
	 * @return
	 */
	public Result updateBatchProductSaleOrCancel(Long hunterid, String productid,Integer type);
	
	/**
	 * 更新商品批量下架  上架   0下架，1上架
	 * @param hunterid
	 * @param productids
	 */
	public void updateBatchProductCancelOrSale(Long hunterid,Integer status,Collection<Long> productids);
	
	/**
	 * 商品的批量分类
	 * @param hunterid
	 * @param productid
	 * @param kindid
	 * @return
	 */
	public Result updateBatchProductKind(Long hunterid, String productid, Long kindid);
	
	/**
	 * 商品的批量分类toDao
	 * @param hunterid
	 * @param productids
	 * @param kindid
	 */
	public void updateBatchProductKind(Long hunterid,Collection<Long> productids,Long kindid);


	/**
	 * 商品添加
	 * @param prouct
	 * @param productSpecification
	 * @param productDetail
	 * @return
	 */
	public Result saveHunterProduct(Product prouct,List<ProductSpecification> productSpecification,List<ProductDetail> productDetail,List<ProductAttachment> productAttachment);
	
	/**
	 * 根据多个商品的id得到这些商品分类的id
	 * @param productid
	 * @return
	 */
	public List<Product> getProdcutByIdsForType(Collection<Long> productid);

	/**
	 * 获取所有商品列表
	 * @return
	 */
	List<Product> getAllProductList();

	int setHotRecommendNull(Long rid);

    List<Product> getProductIdsByRecommendId(HotRecommend recommend);
    
    /**
	 * 查询一级商品二级分类的所有总上架的商品数
	 * @param parenttypeid
	 * @param hunterid
	 * @return
	 */
    public int getProcutAllNumByParentType(@Param("parenttypeid")Long parenttypeid,@Param("hunterid")Long hunterid);

	String getProductOptionsByName(String name);

	String getProductOptionsByHotId(String hid);

	public void updateCommentNumOrder(Order order);
}
