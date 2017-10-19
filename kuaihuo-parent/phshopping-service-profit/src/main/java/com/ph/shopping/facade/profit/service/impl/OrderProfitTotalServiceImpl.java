package com.ph.shopping.facade.profit.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ph.shopping.common.util.core.RespCode;
import com.ph.shopping.common.util.core.ResultUtil;
import com.ph.shopping.common.util.page.PageBean;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.common.util.transform.MoneyTransUtil;
import com.ph.shopping.facade.mapper.IOrderProfitTotalMapper;
import com.ph.shopping.facade.profit.dto.PromoterProfitRecordDTO;
import com.ph.shopping.facade.profit.dto.PromoterProfitTotalDTO;
import com.ph.shopping.facade.profit.dto.SupplyProfitTotalDTO;
import com.ph.shopping.facade.profit.service.IOrderProfitTotalService;
import com.ph.shopping.facade.profit.vo.PromoterProfitRecordVO;
import com.ph.shopping.facade.profit.vo.PromoterProfitTotalVO;
import com.ph.shopping.facade.profit.vo.SupplyProfitTotalVO;

/**
 * 
* @ClassName: OrderProfitTotalServiceImpl
* @Description: TODO(订单分润总表实现类)
* @author Mr.Dong
* @date 2017年6月5日 上午11:37:33
 */
@Component
@Service(version = "1.0.0")
public class OrderProfitTotalServiceImpl implements IOrderProfitTotalService {

	@Autowired
	IOrderProfitTotalMapper profitTotalMapper;
	
	/**
	 * 供应链订单分润总表
	* Title: getSupplyProfitTotalList
	* Description:
	* @author Mr.Dong
	* @date 2017年6月5日 下午4:25:41
	* @param orderProfitTotalDTO
	* @param pagebean
	* @return
	* @see com.ph.shopping.facade.profit.service.IOrderProfitTotalService#getPurchaseProfitTotalList(com.ph.shopping.facade.profit.dto.SupplyProfitTotalDTO, com.ph.shopping.common.util.page.PageBean)
	 */
	@Override
	public Result getSupplyProfitTotalList(SupplyProfitTotalDTO orderProfitTotalDTO, PageBean pagebean) {
		PageHelper.startPage(pagebean.getPageNum(), pagebean.getPageSize());
		List<SupplyProfitTotalVO> purchaseProfitTotalList = profitTotalMapper.getSupplyProfitTotalList(orderProfitTotalDTO);
		PageInfo<SupplyProfitTotalVO> pageInfo = new PageInfo<SupplyProfitTotalVO>(
				purchaseProfitTotalList);
		for(SupplyProfitTotalVO  m : pageInfo.getList()){
			m.setProfited1(MoneyTransUtil.transDivisDouble(m.getProfited()));
		}
		return ResultUtil.getResult(RespCode.Code.SUCCESS, pageInfo.getList(), pageInfo.getTotal());
	}

	/**
	 * 
	 * Title: getSupplyProfitTotalEXCEL
	 * Description:导出供应链分润总表EXCEL
	 * @author Mr.Dong
	 * @date 2017年6月5日 下午6:02:41
	 * @param orderProfitTotalDT
	 * @return
	 * @see com.ph.shopping.facade.profit.service.IOrderProfitTotalService#getPurchaseProfitTotalEXCEL(com.ph.shopping.facade.profit.dto.SupplyProfitTotalDTO)
	 */
	@Override
	public Result getSupplyProfitTotalEXCEL(SupplyProfitTotalDTO orderProfitTotalDT) {
		List<SupplyProfitTotalVO> purchaseProfitTotalList = profitTotalMapper.getSupplyProfitTotalList(orderProfitTotalDT);
		for(SupplyProfitTotalVO  m :purchaseProfitTotalList){
			m.setProfited1(MoneyTransUtil.transDivisDouble(m.getProfited()));
		}
		return ResultUtil.getResult(RespCode.Code.SUCCESS, purchaseProfitTotalList);
	}
	
	
	/*-------------------------------------------上面是供应链分润总表下面是推广师分润总表------------------------------------------------------*/
	
	/**
	 * 
	* Title: getPromoterProfitTotalList
	* Description:推广师分润总表
	* @author Mr.Dong
	* @date 2017年6月6日 上午9:59:54
	* @param orderProfitTotalDTO
	* @param pagebean
	* @return
	* @see com.ph.shopping.facade.profit.service.IOrderProfitTotalService#getPromoterProfitTotalList(com.ph.shopping.facade.profit.dto.SupplyProfitTotalDTO, com.ph.shopping.common.util.page.PageBean)
	 */
	@Override
	public Result getPromoterProfitTotalList(PromoterProfitTotalDTO promoterProfitTotalDTO, PageBean pagebean) {
		PageHelper.startPage(pagebean.getPageNum(), pagebean.getPageSize());
		List<PromoterProfitTotalVO> promoterProfitTotalList = profitTotalMapper.getPromoterProfitTotalList(promoterProfitTotalDTO);
		PageInfo<PromoterProfitTotalVO> pageInfo = new PageInfo<PromoterProfitTotalVO>(
				promoterProfitTotalList);
		for(PromoterProfitTotalVO m : pageInfo.getList()){
			m.setProfited1(MoneyTransUtil.transDivisDouble(m.getProfited()));
		}
		return ResultUtil.getResult(RespCode.Code.SUCCESS, pageInfo.getList(), pageInfo.getTotal());
	}

	/**
	 * 
	* Title: getPromoterProfitTotalEXCEL
	* Description: 推广师分润总表
	* @author Mr.Dong
	* @date 2017年6月6日 下午4:10:48
	* @param promoterProfitTotalDTO
	* @return
	* @see com.ph.shopping.facade.profit.service.IOrderProfitTotalService#getPromoterProfitTotalEXCEL(com.ph.shopping.facade.profit.dto.PromoterProfitTotalDTO)
	 */
	@Override
	public Result getPromoterProfitTotalEXCEL(PromoterProfitTotalDTO promoterProfitTotalDTO) {
		List<PromoterProfitTotalVO> promoterProfitTotalList = profitTotalMapper.getPromoterProfitTotalList(promoterProfitTotalDTO);
		for(PromoterProfitTotalVO  m :promoterProfitTotalList){
			m.setProfited1(MoneyTransUtil.transDivisDouble(m.getProfited()));
		}
		return ResultUtil.getResult(RespCode.Code.SUCCESS, promoterProfitTotalList);
	}

	/**
	 * 统计推广师通过分润得到的金额 
	* Title: getProfitTotalByMemberID
	* Description:
	* @author Mr.Dong
	* @date 2017年6月22日 下午5:29:00
	* @param memberID
	* @return
	* @see com.ph.shopping.facade.profit.service.IOrderProfitTotalService#getProfitTotalByMemberID(java.lang.Long)
	 */
	@Override
	public Result getProfitTotalByMemberID(Long memberID) {
		return ResultUtil.getResult(RespCode.Code.SUCCESS, MoneyTransUtil.transDivisDouble(profitTotalMapper.getProfitTotalByMemberID(memberID)));
	}

	/**
	 * 推广师分润记录 pc商城
	* Title: getPromoterProfitRecordList
	* Description:
	* @author Mr.Dong
	* @date 2017年6月22日 下午9:25:44
	* @param promoterProfitRecordDTO
	* @return
	* @see com.ph.shopping.facade.profit.service.IOrderProfitTotalService#getPromoterProfitRecordList(com.ph.shopping.facade.profit.dto.PromoterProfitRecordDTO)
	 */
	@Override
	public Result getPromoterProfitRecordList(PromoterProfitRecordDTO promoterProfitRecordDTO) {
		PageHelper.startPage(promoterProfitRecordDTO.getPageNum(), promoterProfitRecordDTO.getPageSize());
		List<PromoterProfitRecordVO> promoterProfitRecordList = profitTotalMapper.getPromoterProfitRecordList(promoterProfitRecordDTO);
		PageInfo<PromoterProfitRecordVO> pageInfo = new PageInfo<PromoterProfitRecordVO>(
				promoterProfitRecordList);
		//除以10000
		for(PromoterProfitRecordVO  m : pageInfo.getList()){
			m.setProProfit(MoneyTransUtil.transDivisDouble(m.getProProfit1()));
			m.setOrderMoney(MoneyTransUtil.transDivisDouble(m.getOrderMoney1()));
		}
		return ResultUtil.getResult(RespCode.Code.SUCCESS,pageInfo.getList(), pageInfo.getTotal());
	}


}
