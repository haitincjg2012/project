package com.ph.shopping.facade.profit.service;

import com.ph.shopping.common.util.page.PageBean;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.profit.dto.PromoterProfitRecordDTO;
import com.ph.shopping.facade.profit.dto.PromoterProfitTotalDTO;
import com.ph.shopping.facade.profit.dto.SupplyProfitTotalDTO;

/**
 * @ClassName: IOrderProfitTotalService
 * @Description: 订单分润总表接口(包含供应链和推广师)
 * @author Mr.Dong
 * @date 2017年5月16日 下午4:18:56
 */
public interface IOrderProfitTotalService {

	/**
	 * @Title: getOnLineOrderProfitList
	 * @Description: 获取供应链分润总表list
	 * @author Mr.Dong
	 * @date 2017年5月16日 下午4:03:42
	 * @param orderProfitTotalDTO
	 * @param pagebean
	 * @return
	 */
	public Result getSupplyProfitTotalList(SupplyProfitTotalDTO orderProfitTotalDTO, PageBean pagebean);
	
	/**
	 * 
	* @Title: getPurchaseProfitTotalEXCEL
	* @Description: TODO(导出供应链订单分润总表excel)
	* @author Mr.Dong
	* @date  2017年6月5日 下午6:01:04
	* @param orderProfitTotalDT
	* @return
	 */
	public Result getSupplyProfitTotalEXCEL(SupplyProfitTotalDTO orderProfitTotalDT);
	
	
	
	/*-----------------------------------------------------------------------------------------------------------------------*/
	
	/**
	 * @Title: getPromoterProfitTotalList   
	 * @Description: 获取推广师分润总表list
	 * @author Mr.Dong
	 * @date 2017年5月16日 下午4:03:42
	 * @param promoterProfitTotalDTO
	 * @param pagebean
	 * @return
	 */
	public Result getPromoterProfitTotalList(PromoterProfitTotalDTO promoterProfitTotalDTO, PageBean pagebean);
	
	/**
	 * 
	* @Title: getPromoterProfitTotalEXCEL
	* @Description: TODO(导出推广师分润EXCEL)
	* @author Mr.Dong
	* @date  2017年6月6日 下午4:10:13
	* @param promoterProfitTotalDTO
	* @return
	 */
	public Result getPromoterProfitTotalEXCEL(PromoterProfitTotalDTO promoterProfitTotalDTO);
	
	
	/**
	 * 通过推广师的id获取该推广师的通过分润获取的金额(积分)  返回double 除以10000之后的数据
	* @Title: getProfitTotalByMemberID
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @author Mr.Dong
	* @date  2017年6月22日 下午5:27:42
	* @param memberID
	* @return
	 */
	public Result getProfitTotalByMemberID(Long memberID);
	
	/**
	 * 推广师分润记录 pc商城
	* @Title: getPromoterProfitRecordList
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @author Mr.Dong
	* @date  2017年6月22日 下午9:25:08
	* @param promoterProfitRecordDTO
	* @return
	 */
	public Result  getPromoterProfitRecordList(PromoterProfitRecordDTO promoterProfitRecordDTO);
}
