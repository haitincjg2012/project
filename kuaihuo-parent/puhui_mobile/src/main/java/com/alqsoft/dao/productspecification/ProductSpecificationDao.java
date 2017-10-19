package com.alqsoft.dao.productspecification;

import com.alqsoft.entity.productspecification.ProductSpecification;
import com.alqsoft.vo.ProductSpecificationVO;

import java.util.List;
import java.util.Map;

import org.alqframework.orm.mybatis.MyBatisRepository;

/**
 * 商品规格
 * @author Xuejizheng
 * @date 2017-03-03 10:16
 */
@MyBatisRepository
public interface ProductSpecificationDao {

    ProductSpecificationVO getProductSpecificationVO(Long pid);

	public Map<String, Object> getProductSpecificationById(Long pId);

	public Map<String, Object> getHIdByPsId(Long pId);
	
	/**
	 * 根据商品id查询商品规格
	 * @param productid
	 * @return
	 */
	public List<ProductSpecification> getProductSpecificationByProductId(Long productid);

    List<ProductSpecificationVO> getProductSpecificationVOList(Long productId);
}
