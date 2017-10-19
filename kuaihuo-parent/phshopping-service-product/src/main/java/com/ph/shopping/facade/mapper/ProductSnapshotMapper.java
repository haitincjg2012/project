package com.ph.shopping.facade.mapper;

import java.util.List;

import com.ph.shopping.common.core.base.BaseMapper;
import com.ph.shopping.facade.product.dto.ProductSnapshotDTO;
import com.ph.shopping.facade.product.entity.ProductSnapshot;
import com.ph.shopping.facade.product.vo.ProductSnapshotVO;

/**
 * 
 * @项目：phshopping-facade-
 *
 * @描述：商品快照接口
 *
 * @作者： 杨颜光
 *
 * @创建时间：2017年5月16日 上午11:09:33
 *
 * @Copyright by  杨颜光
 */
public interface ProductSnapshotMapper extends BaseMapper<ProductSnapshot> {
		
		/**
		 * 通用商品id查询商品快照
		 * 
		 * @param dtos
		 * @return  List<ProductSnapshotVO> 
		 * @author 杨颜光
		 */
		List<ProductSnapshotVO>  selectProductSnapshotByproductId(ProductSnapshotDTO dtos);
}