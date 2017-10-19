package com.ph.shopping.facade.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ph.shopping.common.core.exception.BizException;

/**
 * 
 * @ClassName: IPurchaseListMapper
 * @Description: 更新商品数量
 * @author 王强
 * @date 2017年4月26日 下午4:35:31
 */
public interface IPurchaseListMapper {

	/**
	 * 更新商品数量
	 * 
	 * @Title: updateProductNum
	 * @Description: 减少商品数量
	 * @author WQiang
	 * @date 2017年3月18日 下午5:13:11
	 * @param num
	 * @param productId
	 * @return
	 * @throws BizException
	 */
	int updateProductNum(@Param("num") int num, @Param("productId") long productId) throws BizException;


	/**
	 * 
	 * @Title: updateAddProductNum
	 * @Description: 增加商品数量
	 * @author 王强
	 * @date 2017年4月26日 下午4:36:01
	 * @param num
	 * @param productId
	 * @return
	 * @throws BizException
	 */
	int updateAddProductNum(@Param("num") int num, @Param("productId") long productId) throws BizException;

}