package com.ph.shopping.facade.accountsettle.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ph.shopping.common.core.customenum.RoleEnum;
import com.ph.shopping.common.core.customenum.TransCodeEnum;
import com.ph.shopping.common.util.core.RespCode;
import com.ph.shopping.common.util.core.ResultUtil;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.common.util.transform.MoneyTransUtil;
import com.ph.shopping.facade.mapper.IPurchaseOrderProfitMapper;
import com.ph.shopping.facade.mapper.IPurchaseSettleMapper;
import com.ph.shopping.facade.profit.dto.PurchaseSettleDTO;
import com.ph.shopping.facade.profit.service.IPurchaseSettleService;
import com.ph.shopping.facade.profit.vo.PurchaseSettleOrderVO;
import com.ph.shopping.facade.profit.vo.PurchaseSettleVO;
import com.ph.shopping.facade.profit.vo.UserBalanceRecordVO;
import com.ph.shopping.facade.profit.vo.UserBalanceVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 供应链结算实现类
* @ClassName: PurchaseSettleServiceImpl
* @Description: TODO(这里用一句话描述这个类的作用)
* @author Mr.Dong
* @date 2017年6月8日 下午5:22:08
 */
@Component
@Service(version = "1.0.0")
@SuppressWarnings("unused")
public class PurchaseSettleServiceImpl implements IPurchaseSettleService {

	private Logger logger = LoggerFactory.getLogger(UnLineSettleServiceImpl.class);

	@Autowired
	private IPurchaseSettleMapper ipurchaseSettleMapper ;
	
	//供应链订单分润Mapper
	@Autowired
	IPurchaseOrderProfitMapper purchaseOrderProfitMapper;
	
	/**
	 * 供应链结算list
	* Title: getPurchaseSettleList
	* Description:
	* @author Mr.Dong
	* @date 2017年6月9日 下午3:34:43
	* @param purchaseSettleDTO
	* @return
	* @see com.ph.shopping.facade.profit.service.IPurchaseSettleService#getPurchaseSettleList(com.ph.shopping.facade.profit.dto.PurchaseSettleDTO)
	 */
	@Override
	public Result getPurchaseSettleList(PurchaseSettleDTO purchaseSettleDTO) {
		PageHelper.startPage(purchaseSettleDTO.getPageNum(),purchaseSettleDTO.getPageSize());
		List<PurchaseSettleVO> purchaseSettleList = ipurchaseSettleMapper.getPurchaseSettleList(purchaseSettleDTO);
		PageInfo<PurchaseSettleVO> pageInfo = new PageInfo<>(purchaseSettleList);
		//除以10000
		for(PurchaseSettleVO m : pageInfo.getList()){
			m.setOrderMoney1(MoneyTransUtil.transDivisDouble(m.getOrderMoney()));
			m.setSettleMoney1(MoneyTransUtil.transDivisDouble(m.getSettleMoney()));
		}
		return ResultUtil.getResult(RespCode.Code.SUCCESS,pageInfo.getList(),pageInfo.getTotal());
	}
	
	/**
	 * 导出供应链结算EXCEL
	* Title: getPurchaseSettleEXCEL
	* Description:
	* @author Mr.Dong
	* @date 2017年6月9日 下午3:43:11
	* @param purchaseSettleDTO
	* @return
	* @see com.ph.shopping.facade.profit.service.IPurchaseSettleService#getPurchaseSettleEXCEL(com.ph.shopping.facade.profit.dto.PurchaseSettleDTO)
	 */
	@Override
	public Result getPurchaseSettleEXCEL(PurchaseSettleDTO purchaseSettleDTO) {
		List<PurchaseSettleVO> purchaseSettleList = ipurchaseSettleMapper.getPurchaseSettleList(purchaseSettleDTO);
		//除以10000
		for(PurchaseSettleVO m : purchaseSettleList){
			m.setOrderMoney1(MoneyTransUtil.transDivisDouble(m.getOrderMoney()));
			m.setSettleMoney1(MoneyTransUtil.transDivisDouble(m.getSettleMoney()));
		}
		return ResultUtil.getResult(RespCode.Code.SUCCESS,purchaseSettleList);
	}

	/**
	 * 供应链订单主方法
	* Title: doPurchaseSettle
	* Description:
	* @author Mr.Dong
	* @date 2017年6月11日 下午12:11:42
	* @return
	* @see com.ph.shopping.facade.profit.service.IPurchaseSettleService#doPurchaseSettle()
	 */
	@Override
	@Transactional
	public Result doPurchaseSettle() {
		try{
			//查询需要结算的供应链订单
			List<PurchaseSettleOrderVO> purchaseSettleOrder = ipurchaseSettleMapper.getPurchaseSettleOrder();
			if(purchaseSettleOrder.size() < 1 ){
				logger.info("无需要结算的供应链订单");
				 return ResultUtil.getResult(RespCode.Code.SUCCESS);
			}
			
			//批量更改线下订单中结算状态
			int updatePurchaseOrderIsSettleBatch = ipurchaseSettleMapper.updatePurchaseOrderIsSettleBatch(purchaseSettleOrder);
			if(updatePurchaseOrderIsSettleBatch < 0 ){
				logger.info("批量更改供应链订单结算状态异常");
				throw new Exception("批量更改供应链订单结算状态异常");
			}
			
			
			//单条增加商户的余额 改余额 插流水
			List<UserBalanceVO> userBalanceVoList = new ArrayList<UserBalanceVO>();
			List<UserBalanceRecordVO> userBalanceRecordVoList = new ArrayList<UserBalanceRecordVO>();
			//循环构造数据
			for(PurchaseSettleOrderVO m : purchaseSettleOrder){
				UserBalanceVO  userBalanceVo = new UserBalanceVO();
				userBalanceVo.setManageId(m.getSenderId());
				userBalanceVo.setBalance(m.getSettleMoney());
				userBalanceVoList.add(userBalanceVo);
				
				UserBalanceRecordVO userBalanceRecordVO = new UserBalanceRecordVO();
				userBalanceRecordVO.setUserId(m.getSenderId());
				userBalanceRecordVO.setOrderNo(m.getOrderNo());
				userBalanceRecordVO.setMoney(m.getSettleMoney());
				userBalanceRecordVO.setTransCode(String.valueOf(TransCodeEnum.SUPPLY_CHAIN_ORDER_SETTLE.getCode()));
				if(m.getPurchaseType() == 0){//商户进货
					userBalanceRecordVO.setUserType(String.valueOf(RoleEnum.CITY_AGENT.getCode()));
				}else{
					userBalanceRecordVO.setUserType(String.valueOf(RoleEnum.SUPPLIER.getCode()));
				}
				userBalanceRecordVoList.add(userBalanceRecordVO);
			}
			//单条更新用户余额记录
			for(UserBalanceVO n : userBalanceVoList){
				int updateUserBalance = purchaseOrderProfitMapper.updateUserBalance(n);
				if(updateUserBalance < 0){
					throw new Exception("跟新用户余额异常");
				}
			}
			//批量入用户余额流水表
			int insertUserBalanceRecordBatch = purchaseOrderProfitMapper.insertUserBalanceRecordBatch(userBalanceRecordVoList);
			if(insertUserBalanceRecordBatch < 0){
				throw new Exception("插入余额流水异常");
			}			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("供应链订单结算定时器执行抛异常");
		}
		return ResultUtil.getResult(RespCode.Code.SUCCESS);
	}

}
