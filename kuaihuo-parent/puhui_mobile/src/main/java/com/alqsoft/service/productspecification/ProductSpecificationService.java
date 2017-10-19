package com.alqsoft.service.productspecification;

import java.util.List;
import java.util.Map;

import com.alqsoft.entity.productspecification.ProductSpecification;
import com.alqsoft.vo.ProductSpecificationVO;

/**
 *  商品分类管理
 * @author Xuejizheng
 * @date 2017-03-03 10:12
 * @see
 * @since 1.8
 */
public interface ProductSpecificationService{

    ProductSpecificationVO getProductSpecificationVO(Long pid);

	public Map<String, Object> getProductSpecificationById(Long pId);

	public Map<String, Object> getHIdByPsId(Long pId);
	
	/**
	 * 根据商品id查询商品规格
	 * @param productid
	 * @return
	 */
	public List<ProductSpecification> getProductSpecificationByProductId(Long productid);

    List<ProductSpecificationVO> getProductSpecificationVOList(Long id);
}
