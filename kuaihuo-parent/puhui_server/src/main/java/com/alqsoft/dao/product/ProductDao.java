package com.alqsoft.dao.product;

import com.alqsoft.entity.hotrecommend.HotRecommend;
import com.alqsoft.entity.product.Product;
import org.alqframework.orm.hibernate.BaseDao;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.LockModeType;
import java.util.Collection;
import java.util.List;

public interface ProductDao extends BaseDao<Product>{
	
	@Lock(value = LockModeType.PESSIMISTIC_WRITE)
	@Query("from Product as h where h.id=:id")
	public Product getRowLock(@Param("id")Long id);
	
	/**
	 * 批量更新商品下架
	 * @param hunterid
	 * @param productids
	 */
	@Query(value="UPDATE alq_product p SET p.status=:status WHERE  p.hunter_id=:hunterid and p.id IN(:productids) ",nativeQuery=true)
	@Modifying
	public void updateBatchProductCancelOrSale(@Param("hunterid")Long hunterid,@Param("status")Integer status,@Param("productids")Collection<Long> productids);
	

	/**
	 * 批量商品分类
	 * @param hunterid
	 * @param productids
	 * @param kindid
	 */
	@Query(value="UPDATE alq_product p SET p.product_type_id=:kindid WHERE p.hunter_id=:hunterid AND p.id IN(:productids) ",nativeQuery=true)
	@Modifying
	public void updateBatchProductKind(@Param("hunterid")Long hunterid,@Param("productids")Collection<Long> productids,@Param("kindid")Long kindid);

	/**
	 * 根据多个商品的id得到这些商品分类的id
	 * @return
	 */
	@Query(value="FROM Product p WHERE p.id IN(:productid) GROUP BY p.productType.id")
	public List<Product> getProdcutByIdsForType(@Param("productid")Collection<Long> productid);

	/**
	 * 获取所有商品列表
	 * @return
	 */
	@Query(value = " FROM Product p")
	List<Product> getAllProductList();

	/**
	 * 删除所有推荐
	 * @param rid
	 * @return
	 */
	@Query(value = "UPDATE alq_product p SET  p.hot_recommend_id = NULL WHERE p.hot_recommend_id = ?1 ",nativeQuery = true)
	@Modifying
	int setHotRecommendNull(Long rid);

	@Query(value = "from Product  where  hoRecommendId = ?1")
    List<Product> getProductIdsByRecommendId(HotRecommend recommend);
	
	/**
	 * 查询一级商品二级分类的所有总上架的商品数
	 * @param parenttypeid
	 * @param hunterid
	 * @return
	 */
	@Query(value="SELECT COUNT(*) FROM alq_product p WHERE p.`status`=1 AND p.`product_type_id` IN(SELECT id FROM alq_product_type p WHERE p.parent_id=:parenttypeid) AND p.`hunter_id`=:hunterid",nativeQuery=true)
	public int getProcutAllNumByParentType(@Param("parenttypeid")Long parenttypeid,@Param("hunterid")Long hunterid);

	@Query("from Product where name like ?1")
    List<Product> getProductByName(String name);

	@Query(value = "select * from alq_product where hot_recommend_id=?1",nativeQuery = true)
    List<Product> getProductByHotId(String hid);
	
	/**
	 * 商品下架是更新商品的销量为0
	 * @param productids
	 */
	@Query(value="UPDATE alq_product p  SET p.`sale_num`=0 WHERE p.`id` IN(:productids) ",nativeQuery=true)
	@Modifying
	public void updateProductSaleNumZeroForCancel(@Param("productids")Collection<Long> productids);
}
