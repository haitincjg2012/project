package com.alqsoft.dao.producttype;

import java.util.List;

import javax.persistence.LockModeType;

import org.alqframework.orm.hibernate.BaseDao;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.alibaba.dubbo.common.utils.Log;
import com.alibaba.dubbo.config.support.Parameter;
import com.alqsoft.entity.producttype.ProductType;

public interface ProductTypeDao extends BaseDao<ProductType>{
	
	@Modifying
	@Query(value = "update alq_product_type set content = ?2 where id = ?1 ",nativeQuery=true)	
public int	updateProductType(@Param("pId") Long pId,@Param("newContent") String newContent);

	@Modifying
	@Query(value = "delete from alq_product_type where  id= ?1 And hunter_id=?2",nativeQuery=true)	
public int deleteById(@Param("pId") Long pId,@Param("hId") Long hId);
	
	
	@Lock(value = LockModeType.PESSIMISTIC_WRITE)
	@Query("from ProductType as h where h.id=:id")
	public ProductType getRowLock(@Param("id")Long id);
	
	@Modifying
	@Query(value="update alq_product_type set sort_num = ?3 where sort_num = ?2 and hunter_id=?4 and id= ?1",nativeQuery=true)
	public int updateProductTypeUp(@Param("cId") Long cId,@Param("split") String split,@Param("sortNum") Integer sortNum,@Param("hId") Long hId);
	
	/**
	 * 根据商品分类id查询该分类下的上架商品数量
	 * @return
	 */
	@Query(value="SELECT COUNT(*) FROM alq_product p WHERE p.`product_type_id`=:kindid AND p.`status`=1",nativeQuery=true)
	public int getProductCountByTypeId(@Param("kindid")Long kindid);
   /***
    * 查询是一级类是否存在
    */
	@Query(value="SELECT COUNT(*) FROM alq_product_type Where hunter_id=?1 and content=?2 and parent_id=?3" ,nativeQuery=true)
	public int findRightOrError(@Param("hId") Long hId,@Param("parentName") String parentName,@Param("parentId") Long parentId);
    /***
     * 查询是二级和一级是否向关联hId,parentId,pid
     */
	@Query(value="select * from alq_product_type as h where h.hunter_id=?1 and h.parent_id=?2 and h.id=?3",nativeQuery=true)
    public ProductType getRightOrError(@Param("hId") Long hId,@Param("parentId") Long parentId,@Param("pId")Long pId );
    /***
     * 
     */ 
	@Query(value="SELECT COUNT(*) FROM alq_product_type where parent_id=?1",nativeQuery=true)
	public Long getCount(@Param("parent")Long parentId);
	/**
	 *是否与一级类名存在相同的类名
	 */
	@Query(value="SELECT * FROM alq_product_type where hunter_id=?1 and content=?2 and parent_id is null",nativeQuery=true)
	public List<ProductType> findRightOrError(@Param("hId") Long hId,@Param("firstType") String firstType );
	/***
	 * 获取sortNum
	 */
	@Query(value="Select count(1) from alq_product_type",nativeQuery=true)
	public Long getCount();
	/***
	 * 获取二级分类的种类
	 */
	@Query(value="select count(*) from alq_product_type as pt where pt.hunter_id =?2 and pt.content=?3 and pt.parent_id=?1",nativeQuery=true)
	public Long  getSecondTypeName(@Param("idFirst")Long idFirst,@Param("hId")Long  hId,@Param("name")String name);
	/***
	 * 判断一级类中是否含有商品
	 * @param firstId 一级类Id
	 * @return
	 */
	@Query(value="select count(1) from alq_product as pt where pt.product_type_id=?1",nativeQuery=true)
	public Long  getProductTypeContainProduct(@Param("firstId")Long firstId);
}
