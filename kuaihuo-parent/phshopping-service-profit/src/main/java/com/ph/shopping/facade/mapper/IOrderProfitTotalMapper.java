package com.ph.shopping.facade.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ph.shopping.facade.profit.dto.PromoterProfitRecordDTO;
import com.ph.shopping.facade.profit.dto.PromoterProfitTotalDTO;
import com.ph.shopping.facade.profit.dto.SupplyProfitTotalDTO;
import com.ph.shopping.facade.profit.vo.PromoterProfitRecordVO;
import com.ph.shopping.facade.profit.vo.PromoterProfitTotalVO;
import com.ph.shopping.facade.profit.vo.SupplyProfitTotalVO;

/**
 * @ClassName: OrderProfitTotalMapper
 * @Description: 订单分润总表接口Mapper(包含供应链和推广师)
 * @author Mr.Dong
 * @date 2017年5月16日 下午4:18:56
 */
public interface IOrderProfitTotalMapper {
	
	
	/**
	 * @Title: getSupplyProfitTotalList
	 * @Description: 获取供应链分润总表list
	 * @author Mr.Dong
	 * @date 2017年5月16日 下午4:03:42
	 * @param orderProfitTotalDTO
	 * @param pagebean
	 * @return
	 */
	public List<SupplyProfitTotalVO> getSupplyProfitTotalList(SupplyProfitTotalDTO orderProfitTotalDTO);
	
	/**
	 * @Title: getPromoterProfitTotalList
	 * @Description: 获取推广师分润总表list
	 * @author Mr.Dong
	 * @date 2017年5月16日 下午4:03:42
	 * @param promoterProfitTotalDTO
	 * @return
	 */
	public List<PromoterProfitTotalVO> getPromoterProfitTotalList(PromoterProfitTotalDTO promoterProfitTotalDTO);
	
	
	
	
	/**
	 * 推广师获取分润的总额(此人通过分润途径得到的金额)
	* @Title: getProfitTotalByMemberID
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @author Mr.Dong
	* @date  2017年6月22日 下午5:30:57
	* @param memberID
	* @return
	 */
	public Long getProfitTotalByMemberID(@Param("memberID")Long memberID);
	
	
	/**
	 * 推广师分润记录 pc商城
	* @Title: getPromoterProfitRecordList
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @author Mr.Dong
	* @date  2017年6月22日 下午9:20:28
	* @param promoterProfitRecordDTO
	* @return
	 */
	public List<PromoterProfitRecordVO>  getPromoterProfitRecordList(PromoterProfitRecordDTO promoterProfitRecordDTO);
}
