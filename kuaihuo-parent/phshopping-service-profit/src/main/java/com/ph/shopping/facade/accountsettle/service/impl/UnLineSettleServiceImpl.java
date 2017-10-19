package com.ph.shopping.facade.accountsettle.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ph.shopping.common.core.customenum.PayTypeEnum;
import com.ph.shopping.common.core.customenum.RoleEnum;
import com.ph.shopping.common.core.customenum.TransCodeEnum;
import com.ph.shopping.common.util.core.RespCode;
import com.ph.shopping.common.util.core.ResultUtil;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.common.util.transform.MoneyTransUtil;
import com.ph.shopping.facade.mapper.IPurchaseOrderProfitMapper;
import com.ph.shopping.facade.mapper.IUnLineSettleMapper;
import com.ph.shopping.facade.profit.dto.UnLineSettleDTO;
import com.ph.shopping.facade.profit.service.IUnLineSettleService;
import com.ph.shopping.facade.profit.vo.UnLineSettleOrderVO;
import com.ph.shopping.facade.profit.vo.UnLineSettleVO;
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
 * 线下订单结算接口实现类
* @ClassName: UnLineSettleServiceImpl
* @Description: TODO(这里用一句话描述这个类的作用)
* @author Mr.Dong
* @date 2017年6月7日 上午11:14:12
 */
@Component
@Service(version = "1.0.0")
public class UnLineSettleServiceImpl implements IUnLineSettleService {

	private Logger logger = LoggerFactory.getLogger(UnLineSettleServiceImpl.class);

	
	@Autowired
	private IUnLineSettleMapper  iUnLineSettleMapper;
	
	//供应链订单分润Mapper
	@Autowired
	IPurchaseOrderProfitMapper purchaseOrderProfitMapper;
	
	/**
	 * 
	* Title: getUnLineSettleList
	* Description:线下订单结算list
	* @author Mr.Dong
	* @date 2017年6月7日 上午11:39:06
	* @param unLineSettleDTO
	* @return
	* @see com.ph.shopping.facade.profit.service.IUnLineSettleService#getUnLineSettleList(com.ph.shopping.facade.profit.dto.UnLineSettleDTO)
	 */
	@Override
	public Result getUnLineSettleList(UnLineSettleDTO unLineSettleDTO) {
		PageHelper.startPage(unLineSettleDTO.getPageNum(), unLineSettleDTO.getPageSize());
		List<UnLineSettleVO> unLineSettleList = iUnLineSettleMapper.getUnLineSettleList(unLineSettleDTO);
		PageInfo<UnLineSettleVO>pageInfo=new PageInfo<UnLineSettleVO>(unLineSettleList);
		//除以10000
		for(UnLineSettleVO m : unLineSettleList){
			m.setOrderMoney1(MoneyTransUtil.stringFormat(MoneyTransUtil.transDivisDouble(m.getOrderMoney())));
			m.setSettleMoney1(MoneyTransUtil.stringFormat(MoneyTransUtil.transDivisDouble(m.getSettleMoney())));
		}
		return  ResultUtil.getResult(RespCode.Code.SUCCESS,pageInfo.getList(),pageInfo.getTotal());
	}
	/**
	 * 导出线下结算EXCEL
	* Title: getUnLineSettleEXCEL
	* Description:
	* @author Mr.Dong
	* @date 2017年6月7日 下午5:43:23
	* @param unLineSettleDTO
	* @return
	* @see com.ph.shopping.facade.profit.service.IUnLineSettleService#getUnLineSettleEXCEL(com.ph.shopping.facade.profit.dto.UnLineSettleDTO)
	 */
	@Override
	public Result getUnLineSettleEXCEL(UnLineSettleDTO unLineSettleDTO) {
		List<UnLineSettleVO> unLineSettleList = iUnLineSettleMapper.getUnLineSettleList(unLineSettleDTO);
		//除以10000
		for(UnLineSettleVO m : unLineSettleList){
			m.setOrderMoney1(MoneyTransUtil.stringFormat(MoneyTransUtil.transDivisDouble(m.getOrderMoney())));
			m.setSettleMoney1(MoneyTransUtil.stringFormat(MoneyTransUtil.transDivisDouble(m.getSettleMoney())));
		}
		return  ResultUtil.getResult(RespCode.Code.SUCCESS,unLineSettleList);
	}
	
	
	
	/**
	 * 线下结算主方法
	* Title: doUnLineSettle
	* Description:
	* @author Mr.Dong
	* @date 2017年6月8日 上午9:54:15
	* @return
	* @see com.ph.shopping.facade.profit.service.IUnLineSettleService#doUnLineSettle()
	 */
	@Override
	@Transactional
	public Result doUnLineSettle() {
		try{
			//第一步查询需要结算的线下订单
			List<UnLineSettleOrderVO> unLineSettleOrder = iUnLineSettleMapper.getUnLineSettleOrder();
			if(unLineSettleOrder.size() < 1 ){
				logger.info("无需要结算的线下订单");
				 return ResultUtil.getResult(RespCode.Code.SUCCESS);
			}
			//批量更改线下订单中结算状态
			int updateUnLineOrderIsSettleBatch = iUnLineSettleMapper.updateUnLineOrderIsSettleBatch(unLineSettleOrder);
			if(updateUnLineOrderIsSettleBatch < 0 ){
				logger.info("批量更改线下订单结算状态异常");
				throw new Exception("批量更改线下订单结算状态异常");
			}
					
			//单条增加商户的余额 改余额 插流水;
			List<UserBalanceVO> userBalanceVoList = new ArrayList<UserBalanceVO>();
			List<UserBalanceRecordVO> userBalanceRecordVoList = new ArrayList<UserBalanceRecordVO>();
			//循环构造数据
			for(UnLineSettleOrderVO m : unLineSettleOrder){
				UserBalanceVO  userBalanceVo = new UserBalanceVO();
				userBalanceVo.setManageId(m.getMerchantId());
				userBalanceVo.setBalance(m.getSettleMoney());
				userBalanceVoList.add(userBalanceVo);
				
				UserBalanceRecordVO userBalanceRecordVO = new UserBalanceRecordVO();
				userBalanceRecordVO.setUserId(m.getMerchantId());
				userBalanceRecordVO.setOrderNo(m.getOrderNo());
				userBalanceRecordVO.setMoney(m.getSettleMoney());
				userBalanceRecordVO.setTransCode(String.valueOf(TransCodeEnum.UNLINE_ORDER_SETTLE.getCode()));
				userBalanceRecordVO.setUserType(String.valueOf(RoleEnum.MERCHANT.getCode()));
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
			logger.error("线下订单结算定时器执行抛异常");
		}
		return ResultUtil.getResult(RespCode.Code.SUCCESS);
	}
	
	/**
	 *线下立即结算 2017-7-31 新需求
	* Title: doUnLineSettleNow
	* Description:
	* @author Mr.Dong
	* @date 2017年7月31日 下午4:40:56
	* @param unLineSettleOrderVO
	* @return
	* @see com.ph.shopping.facade.profit.service.IUnLineSettleService#doUnLineSettleNow(com.ph.shopping.facade.profit.vo.UnLineSettleOrderVO)
	 */
	@Override
	public Result doUnLineSettleNow(UnLineSettleOrderVO unLineSettleOrderVO) throws Exception {
		try{
			List<UnLineSettleOrderVO> unLineSettleOrder = new ArrayList<UnLineSettleOrderVO>();
			unLineSettleOrder.add(unLineSettleOrderVO);
			if(unLineSettleOrder.size() < 1 ){
				logger.info("无需要结算的线下订单");
				 return ResultUtil.getResult(RespCode.Code.SUCCESS);
			}
			//批量更改线下订单中结算状态  
			//2017-7-31  不对线下订单表中做处理  他那边处理
//			int updateUnLineOrderIsSettleBatch = iUnLineSettleMapper.updateUnLineOrderIsSettleBatch(unLineSettleOrder);
//			if(updateUnLineOrderIsSettleBatch < 0 ){
//				logger.info("批量更改线下订单结算状态异常");
//				throw new Exception("批量更改线下订单结算状态异常");
//			}
					
			//单条增加商户的余额 改余额 插流水;
			List<UserBalanceVO> userBalanceVoList = new ArrayList<UserBalanceVO>();
			List<UserBalanceRecordVO> userBalanceRecordVoList = new ArrayList<UserBalanceRecordVO>();
			//循环构造数据
			for(UnLineSettleOrderVO m : unLineSettleOrder){
				UserBalanceVO  userBalanceVo = new UserBalanceVO();
				userBalanceVo.setManageId(m.getMerchantId());
				userBalanceVo.setBalance(m.getSettleMoney());
				userBalanceVoList.add(userBalanceVo);
				
				UserBalanceRecordVO userBalanceRecordVO = new UserBalanceRecordVO();
				userBalanceRecordVO.setUserId(m.getMerchantId());
				userBalanceRecordVO.setOrderNo(m.getOrderNo());
				userBalanceRecordVO.setMoney(m.getSettleMoney());
				userBalanceRecordVO.setTransCode(String.valueOf(TransCodeEnum.UNLINE_ORDER_SETTLE.getCode()));
				userBalanceRecordVO.setUserType(String.valueOf(RoleEnum.MERCHANT.getCode()));
				userBalanceRecordVoList.add(userBalanceRecordVO);
			}
			if(unLineSettleOrderVO.getPayType() == PayTypeEnum.PAY_TYPE_SCORE.getPayType()) {
				//单条更新用户余额记录
				for (UserBalanceVO n : userBalanceVoList) {
					int updateUserBalance = purchaseOrderProfitMapper.updateUserBalance(n);
					if (updateUserBalance < 0) {
						throw new Exception("跟新用户余额异常");
					}
				}
			}
			//批量入用户余额流水表
			int insertUserBalanceRecordBatch = purchaseOrderProfitMapper.insertUserBalanceRecordBatch(userBalanceRecordVoList);
			if(insertUserBalanceRecordBatch < 0){
				throw new Exception("插入余额流水异常");
			}		
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("线下订单结算抛异常");
			throw new Exception("线下订单结算抛异常了");
		}
		return ResultUtil.getResult(RespCode.Code.SUCCESS);
	}

	
}
