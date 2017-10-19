package com.ph.shopping.facade.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ph.shopping.facade.profit.dto.SupplyChainBalanceDTO;
import com.ph.shopping.facade.profit.vo.SupplyChainBalanceVO;

/**
 * 
* @ClassName: ISupplyChainBalanceMapper
* @Description: 供链余额mapper
* @author 王强
* @date 2017年6月9日 下午12:30:36
 */
public interface ISupplyChainBalanceMapper {
	/**
	 * 
	* @Title: getSupplyChainBalances
	* @Description: 查询供链余额列表
	* @author 王强
	* @date  2017年6月9日 下午12:32:36
	* @param supplyChainBalanceDTO
	* @return
	 */
	List<SupplyChainBalanceVO> getSupplyChainBalances(SupplyChainBalanceDTO supplyChainBalanceDTO);
	

	/**
	 * 根据编码transCode统计数据
	* @Title: getStatistics
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @author Mr.Dong
	* @date  2017年7月4日 下午7:16:22
	* @param userId
	* @param transCode
	* @return
	 */
	Long getStatistics(@Param("userId")Long userId,@Param("transCode") Integer  transCode);
}
