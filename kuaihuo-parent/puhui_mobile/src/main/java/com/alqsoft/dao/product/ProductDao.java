package com.alqsoft.dao.product;

import java.util.List;
import java.util.Map;

import com.alqsoft.vo.ProductVo;
import org.alqframework.orm.mybatis.MyBatisRepository;

import com.alqsoft.entity.product.Product;
import org.apache.ibatis.annotations.Param;

@MyBatisRepository
public interface ProductDao {

	/**
	 * 商品出售中列表销售中或下架的   默认列表显示的是每个商品规格时间最早的  status : 0  下架 1出售中
	 * @param params
	 * @return
	 */
	public List<Map<String,Object>> findProductSaleOrCancelList(Map<String,Object> params);
	
	
	public Product getProductById(Long id);
	
	/**
	 * 商品编辑时用到，获取商品表基本的信息
	 * @param productid
	 * @return
	 */
	public Map<String,Object> getProductBaseMsgById(Long productid);
	
	/**
	 * 根据商品分类查询商品
	 * @param params
	 * @return
	 */
	public List<Map<String,Object>> findProductByTypeList(Map<String,Object> params);

	public List<ProductVo> getProductByHunterIdAndTypeId(@Param("hunterId")Long hunterId, @Param("typeId")Long typeId,@Param("cpage")Integer cpage,@Param("numPage")Integer numPage);

    List<ProductVo> getProductByHunterId(@Param("hunterId")Long hunterId,@Param("cpage")Integer cpage,@Param("numPage")Integer numPage);
}
