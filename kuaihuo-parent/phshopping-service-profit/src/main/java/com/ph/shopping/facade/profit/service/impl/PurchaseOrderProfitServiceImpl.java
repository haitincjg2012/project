package com.ph.shopping.facade.profit.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ph.shopping.common.core.customenum.RoleEnum;
import com.ph.shopping.common.core.customenum.TransCodeEnum;
import com.ph.shopping.common.util.core.RespCode;
import com.ph.shopping.common.util.core.ResultUtil;
import com.ph.shopping.common.util.page.PageBean;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.common.util.transform.MoneyTransUtil;
import com.ph.shopping.facade.mapper.IPurchaseOrderProfitMapper;
import com.ph.shopping.facade.mapper.IUnlineOrderProfitMapper;
import com.ph.shopping.facade.profit.dto.PurchaseOrderProfitDTO;
import com.ph.shopping.facade.profit.entity.PurchaseOrderProfit;
import com.ph.shopping.facade.profit.entity.SupplyProfitTotal;
import com.ph.shopping.facade.profit.entity.UnlineOrderProfit;
import com.ph.shopping.facade.profit.exception.ProfitException;
import com.ph.shopping.facade.profit.exception.ProfitExceptionEnum;
import com.ph.shopping.facade.profit.service.IPurchaseOrderProfitService;
import com.ph.shopping.facade.profit.vo.PositionVO;
import com.ph.shopping.facade.profit.vo.PurchaseOrderProfitVO;
import com.ph.shopping.facade.profit.vo.PurchaseOrderVO;
import com.ph.shopping.facade.profit.vo.UserBalanceRecordVO;
import com.ph.shopping.facade.profit.vo.UserBalanceVO;
import com.ph.shopping.facade.spm.dto.AgentDTO;
import com.ph.shopping.facade.spm.service.IAgentService;
import com.ph.shopping.facade.spm.spmEnum.SPMEnum;
import com.ph.shopping.facade.spm.vo.AgentVO;
import com.ph.shopping.facade.system.service.ISystemParameterService;
/**
 * 
 * @ClassName: OnlineOrderProfitServiceImpl
 * @Description: 供应链订单分润实现类
 * @author Mr.Dong
 * @date 2017年5月16日 下午4:34:40
 */
@Component
@Service(version = "1.0.0")
public class PurchaseOrderProfitServiceImpl implements IPurchaseOrderProfitService {
	
	private Logger logger = LoggerFactory.getLogger(PurchaseOrderProfitServiceImpl.class);
	
	//供应链订单分润Mapper
	@Autowired
	IPurchaseOrderProfitMapper purchaseOrderProfitMapper;
	
	//代理接口
	 @Reference(version = "1.0.0")
	 private IAgentService iAgentService;
	 
	 //系统参数接口
	 @Reference(version="1.0.0")
	 private ISystemParameterService iSystemParameterService;
	
	//线下订单分润Mapper
	@Autowired
	IUnlineOrderProfitMapper iUnlineOrderProfitMapper;
		
	/**
	 * 线下供应链订单分润表列表
	 */
	@Override
	public Result getPurchaseOrderProfitList(PurchaseOrderProfitDTO purchaseOrderProfitDTO,
			PageBean pagebean) {
		try {
			PageHelper.startPage(pagebean.getPageNum(), pagebean.getPageSize());
			List<PurchaseOrderProfitVO> purchaseOrderProfitVOList = purchaseOrderProfitMapper
					.getPurchaseOrderProfitList(purchaseOrderProfitDTO);
			PageInfo<PurchaseOrderProfitVO> pageInfo = new PageInfo<PurchaseOrderProfitVO>(
					purchaseOrderProfitVOList);
			//转换下
			for(PurchaseOrderProfitVO m : pageInfo.getList()){
				m.setRetailPrice1(MoneyTransUtil.transDivisDouble(m.getRetailPrice()));
				m.setSettlementPrice1(MoneyTransUtil.transDivisDouble(m.getSettlementPrice()));
				m.setPurchasePrice1(MoneyTransUtil.transDivisDouble(m.getPurchasePrice()));
				m.setLogisticsFee1(MoneyTransUtil.transDivisDouble(m.getLogisticsFee()));
				m.setChainProfit1(MoneyTransUtil.transDivisDouble(m.getChainProfit()));
				m.setPhIncome1(MoneyTransUtil.transDivisDouble(m.getPhIncome()));
				m.setYstIncome1(MoneyTransUtil.transDivisDouble(m.getYstIncome()));
				m.setCityAgentProfit1(MoneyTransUtil.transDivisDouble(m.getCityAgentProfit()));
				m.setCountyAgentProfit1(MoneyTransUtil.transDivisDouble(m.getCountyAgentProfit()));
				m.setTownAgentProfit1(MoneyTransUtil.transDivisDouble(m.getTownAgentProfit()));
				m.setChainTotal1(MoneyTransUtil.transDivisDouble(m.getChainTotal()));
				m.setChainRemain1(MoneyTransUtil.transDivisDouble(m.getChainRemain()));
				m.setYstRemain1(MoneyTransUtil.transDivisDouble(m.getYstRemain()));
				m.setPhRemain1(MoneyTransUtil.transDivisDouble(m.getPhRemain()));
			}
			return ResultUtil.getResult(RespCode.Code.SUCCESS, pageInfo.getList(), pageInfo.getTotal());
		} catch (Exception e) {
			e.printStackTrace();
			throw new ProfitException(ProfitExceptionEnum.PROFIT_LIST_EXCEPTION);
		}
		
	}

	/**
	 * 导出供应链分润订单利润分成表EXCEL
	 * @return
	 */
	public Result getPurchaseOrderProfitEXCEL(PurchaseOrderProfitDTO purchaseOrderProfitDTO) {
		try {
			List<PurchaseOrderProfitVO> purchaseOrderProfitVOList = purchaseOrderProfitMapper
					.getPurchaseOrderProfitList(purchaseOrderProfitDTO);
			//转换下
			for(PurchaseOrderProfitVO m : purchaseOrderProfitVOList){
				m.setRetailPrice1(MoneyTransUtil.transDivisDouble(m.getRetailPrice()));
				m.setSettlementPrice1(MoneyTransUtil.transDivisDouble(m.getSettlementPrice()));
				m.setPurchasePrice1(MoneyTransUtil.transDivisDouble(m.getPurchasePrice()));
				m.setLogisticsFee1(MoneyTransUtil.transDivisDouble(m.getLogisticsFee()));
				m.setChainProfit1(MoneyTransUtil.transDivisDouble(m.getChainProfit()));
				m.setPhIncome1(MoneyTransUtil.transDivisDouble(m.getPhIncome()));
				m.setYstIncome1(MoneyTransUtil.transDivisDouble(m.getYstIncome()));
				m.setCityAgentProfit1(MoneyTransUtil.transDivisDouble(m.getCityAgentProfit()));
				m.setCountyAgentProfit1(MoneyTransUtil.transDivisDouble(m.getCountyAgentProfit()));
				m.setTownAgentProfit1(MoneyTransUtil.transDivisDouble(m.getTownAgentProfit()));
				m.setChainTotal1(MoneyTransUtil.transDivisDouble(m.getChainTotal()));
				m.setChainRemain1(MoneyTransUtil.transDivisDouble(m.getChainRemain()));
				m.setYstRemain1(MoneyTransUtil.transDivisDouble(m.getYstRemain()));
				m.setPhRemain1(MoneyTransUtil.transDivisDouble(m.getPhRemain()));
			}
			return ResultUtil.getResult(RespCode.Code.SUCCESS, purchaseOrderProfitVOList);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ProfitException(ProfitExceptionEnum.PROFIT_EXCEPTION);
		}
	}
	
	/**
	 * 
	* Title: purchaseOrderProfit
	* Description:主方法 供应链订单分润
	* @author Mr.Dong
	* @date 2017年5月23日 上午9:40:28
	* @return
	* @see com.ph.shopping.facade.profit.service.IPurchaseOrderProfitService#purchaseOrderProfit()
	 */
	@Override
	@Transactional
	public Result purchaseOrderProfit() {
		Result result = ResultUtil.getResult(RespCode.Code.SUCCESS);
		try {
			//第一步得到需要分润的供应链订单
			List<PurchaseOrderVO> purchaseOrderList = purchaseOrderProfitMapper.getPurchaseOrderList();
			if(purchaseOrderList.size() < 1){
				logger.info("无供应链订单分润数据");
				return ResultUtil.getResult(RespCode.Code.SUCCESS);
			}
			int i = 0;
			//通过商户区域获取此商户的各级代理
			for(PurchaseOrderVO m : purchaseOrderList){
				try{
					AgentDTO agentDTO = new AgentDTO();
					if(m.getMerchantPositionId() == null){
						logger.info("商户的区域positionId为null");
						continue;
					}
					PositionVO positionById = purchaseOrderProfitMapper.getPositionById(m.getMerchantPositionId());
					
					agentDTO.setProvinceId(positionById.getProvinceId());
					agentDTO.setCityId(positionById.getCityId());
					agentDTO.setCountyId(positionById.getCountyId());
					agentDTO.setTownId(positionById.getTownId());
					
					agentDTO.setStatus(SPMEnum.agentStatus.ONE.getIndex());//审核通过
					agentDTO.setIsDelete(SPMEnum.agentIsDelete.ZERO.getIndex());//没有被删除
					agentDTO.setIsFrozen(SPMEnum.agentIsFrozen.ONE.getIndex());//冻结的 传过去代理直接非冻结
					List<AgentVO> agentPositionList = iAgentService.getAgentPositionList(agentDTO);
					if(agentPositionList.size() < 1){
						logger.info("区域获取代理数据为null");
						i = i + 1;
						continue;
					}
					logger.info("通过区域查询各级代理返回参数："+JSON.toJSONString(agentPositionList));
					if(agentPositionList.size() > 0){
						for(AgentVO n : agentPositionList){//循环返回的代理
							if(n.getAgentLevelId() == 1){//市级代理
								m.setCityAgentId(n.getUserId());
								m.setCityAgentName(n.getAgentName());
							}else if(n.getAgentLevelId() == 2){//县级代理
								m.setCountyAgentId(n.getUserId());
								m.setCountyAgentName(n.getAgentName());
							}else if(n.getAgentLevelId() == 3){//社区代理
								m.setTownAgentId(n.getUserId());
								m.setTownAgentName(n.getAgentName());
							}
						}
					}
				} catch (Exception e) {
					logger.debug("循环报错");
					e.printStackTrace();
				}	
			}
			
			if(i == purchaseOrderList.size()){
				logger.info("所有的区域获取代理数据都为null");
				return ResultUtil.getResult(RespCode.Code.SUCCESS);
			}
			
			//获取系统参数
			Map<String, Double> systemParameters = getSystemParameters();
			//得到供应链订单分润记录
			List<PurchaseOrderProfit> purchaseOrderProfitList = getPurchaseOrderProfitList(purchaseOrderList,systemParameters);
			// 接下来执行分润 将数据插入 ph_purchase_order_profit 表
			int insertPurchaseOrderProfit = purchaseOrderProfitMapper.insertPurchaseOrderProfit(purchaseOrderProfitList);
			if(insertPurchaseOrderProfit < 1){
				throw new Exception("批量插入供应链订单分润记录表异常");
			}
			// 接下来更改 ph_purchase_sub_order 字段isSettle为1(已分润)
			int updatePurchaseOrderBonus = purchaseOrderProfitMapper.updatePurchaseOrderBonus(purchaseOrderList);
			if(updatePurchaseOrderBonus < 1){
				throw new Exception("更改供应链子订单表中的分润状态为已分润异常");
			}
			StartProfit(purchaseOrderProfitList);//开始执行分润(更改用户余额)	
			//入用户分润记录表
			insertUserProfitRecord(purchaseOrderProfitList);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("线下订单供应链利润分润异常:" + e.getMessage());
			throw new ProfitException(ProfitExceptionEnum.PURCHASE_ORDER_PROFIT_EXCEPTION);
		}
	}
	
	/**
	 * 开始分润(更新用户余额)
	 * @param purchaseOrderList
	 */
	public void StartProfit(List<PurchaseOrderProfit> purchaseOrderProfitList) throws Exception{
		List<UserBalanceVO> userBalanceVoList = new ArrayList<UserBalanceVO>();
		List<UserBalanceRecordVO> userBalanceRecordVoList = new ArrayList<UserBalanceRecordVO>();
		
		//循环供应链订单分润 
		for(PurchaseOrderProfit m : purchaseOrderProfitList){
			if(m.getCityAgentId()!= null){
				UserBalanceVO  userBalanceVo = new UserBalanceVO();
				userBalanceVo.setManageId(m.getCityAgentId());
				userBalanceVo.setBalance(m.getCityAgentProfit());
				userBalanceVoList.add(userBalanceVo);
				
				UserBalanceRecordVO userBalanceRecordVO = new UserBalanceRecordVO();
				userBalanceRecordVO.setUserId(m.getCityAgentId());
				userBalanceRecordVO.setOrderNo(m.getOrderNo());
				userBalanceRecordVO.setMoney(m.getCityAgentProfit());
				userBalanceRecordVO.setTransCode(String.valueOf(TransCodeEnum.PURCHASE_ORDER_PROFIT_TRANSCODE.getCode()));
				userBalanceRecordVO.setUserType(String.valueOf(RoleEnum.CITY_AGENT.getCode()));
				userBalanceRecordVoList.add(userBalanceRecordVO);
				
			}
			if(m.getCountyAgentId() != null){
				UserBalanceVO  userBalanceVo = new UserBalanceVO();
				userBalanceVo.setManageId(m.getCountyAgentId());
				userBalanceVo.setBalance(m.getCountyAgentProfit());
				userBalanceVoList.add(userBalanceVo);
				
				UserBalanceRecordVO userBalanceRecordVO = new UserBalanceRecordVO();
				userBalanceRecordVO.setUserId(m.getCountyAgentId());
				userBalanceRecordVO.setOrderNo(m.getOrderNo());
				userBalanceRecordVO.setMoney(m.getCountyAgentProfit());
				userBalanceRecordVO.setTransCode(String.valueOf(TransCodeEnum.PURCHASE_ORDER_PROFIT_TRANSCODE.getCode()));
				userBalanceRecordVO.setUserType(String.valueOf(RoleEnum.COUNTY_AGENT.getCode()));
				userBalanceRecordVoList.add(userBalanceRecordVO);
				
			}
			if(m.getTownAgentId()  != null){
				UserBalanceVO  userBalanceVo = new UserBalanceVO();
				userBalanceVo.setManageId(m.getTownAgentId());
				userBalanceVo.setBalance(m.getTownAgentProfit());
				userBalanceVoList.add(userBalanceVo);
				
				UserBalanceRecordVO userBalanceRecordVO = new UserBalanceRecordVO();
				userBalanceRecordVO.setUserId(m.getTownAgentId());
				userBalanceRecordVO.setOrderNo(m.getOrderNo());
				userBalanceRecordVO.setMoney(m.getTownAgentProfit());
				userBalanceRecordVO.setTransCode(String.valueOf(TransCodeEnum.PURCHASE_ORDER_PROFIT_TRANSCODE.getCode()));
				userBalanceRecordVO.setUserType(String.valueOf(RoleEnum.COMMUNITY_AGENT.getCode()));
				userBalanceRecordVoList.add(userBalanceRecordVO);
			}
		}
		
		if(userBalanceVoList.size() > 0){
			//单条更新用户余额记录
			for(UserBalanceVO n : userBalanceVoList){
				int updateUserBalance = purchaseOrderProfitMapper.updateUserBalance(n);
				if(updateUserBalance < 0){
					throw new Exception("跟新用户余额异常");
				}
			}
		}
		
		if(userBalanceRecordVoList.size() > 0){
			//批量入用户余额流水表
			int insertUserBalanceRecordBatch = purchaseOrderProfitMapper.insertUserBalanceRecordBatch(userBalanceRecordVoList);
			if(insertUserBalanceRecordBatch < 0){
				throw new Exception("插入余额流水异常");
			}
		}
	}
	
	
	/**
	 * 获取系统参数(各种分润比率)
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String,Double> getSystemParameters(){
		//获取系统参数
		List<Long> ids = new ArrayList<Long>();
		ids.add(new Long (2));//进货价比率
		ids.add(new Long (3));//供应链利润比率
		ids.add(new Long (21));//普惠供应链收入比率
		ids.add(new Long (22));//易商通供应链收入比率
		ids.add(new Long (14));//供链股权分配比例 69%
		ids.add(new Long (15));//市级代理供应链分润比率
		ids.add(new Long (16));//县代供链分润比例
		ids.add(new Long (17));//区代供链分润比例
		Result systemParameterListByIds = iSystemParameterService.getSystemParameterListByIds(ids);
		logger.info("调用系统参数接口返回参数：result={}",JSON.toJSONString(systemParameterListByIds));
		Map<Long,Object> map = (Map<Long,Object>)systemParameterListByIds.getData();
		Double CSign = (Double)(map.get(new Long (2)));//进货价比率
		Double FSign = (Double)(map.get(new Long (3)));//供应链利润比率
		Double GSign = (Double)(map.get(new Long (21)));//普惠供应链收入比率
		Double HSign = (Double)(map.get(new Long (22)));//易商通供应链收入比率
		Double XSign = (Double)(map.get(new Long (14)));//供链股权分配比例 69%
		Double ISign = (Double)(map.get(new Long (15)));//市级代理供应链分润比率
		Double JSign = (Double)(map.get(new Long (16)));//县代供链分润比例
		Double KSign = (Double)(map.get(new Long (17)));//社区代供链分润比例
		Map<String,Double> mapNew = new  HashMap<String, Double>();
		mapNew.put("c", CSign);
		mapNew.put("f", FSign);
		mapNew.put("g", GSign);
		mapNew.put("h", HSign);
		mapNew.put("x", XSign);
		mapNew.put("i", ISign);
		mapNew.put("j", JSign);
		mapNew.put("k", KSign);
		return mapNew;
	}
	
	/**
	 * 得到供应链订单分润记录list
	 * @param purchaseOrderList
	 * @param systemParameters
	 * @return
	 */
	public List<PurchaseOrderProfit> getPurchaseOrderProfitList(List<PurchaseOrderVO> purchaseOrderList,Map<String, Double> systemParameters){
		//得到供应链订单分润记录
		List<PurchaseOrderProfit> purchaseOrderProfitList = new ArrayList<PurchaseOrderProfit>();
		for(PurchaseOrderVO n : purchaseOrderList){
			PurchaseOrderProfit p = new PurchaseOrderProfit();
			p.setOrderNo(n.getOrderNo());
			p.setRetailPrice(n.getRetailPrice());//零售价
			Double b = n.getSettlementPrice().doubleValue();//结算成本
			p.setSettlementPrice(n.getSettlementPrice());//结算成本
			Double c =(n.getRetailPrice().doubleValue()  * systemParameters.get("c").doubleValue());
			p.setPurchasePrice(c.longValue());//门店进货价格
			Double d = n.getFreight().doubleValue();
			p.setFreight(n.getFreight());//物流费
			Double f = (n.getRetailPrice().doubleValue()  * systemParameters.get("f").doubleValue());
			p.setChainProfit(f.longValue());//供应链利润
			Double g = n.getRetailPrice().doubleValue()  * systemParameters.get("g").doubleValue();
			p.setPhIncome(g.longValue());//普惠供应链收入
			Double h = (n.getRetailPrice().doubleValue()  * systemParameters.get("h").doubleValue());
			p.setYstIncome(h.longValue());//易商通供应链收入
			
			Double l = 0d;
			if(n.getCityAgentId() != null){
				Double i = (n.getRetailPrice().doubleValue()  * systemParameters.get("i").doubleValue()* systemParameters.get("x").doubleValue());
				p.setCityAgentProfit(i.longValue());//市级代理供应链分润
				l = l+ i;
			}
			if(n.getCountyAgentId() != null ){
				Double j = (n.getRetailPrice().doubleValue()  * systemParameters.get("j").doubleValue()* systemParameters.get("x").doubleValue());
				p.setCountyAgentProfit(j.longValue());//县级供应链分润
				l = l+ j;
			}
			if(n.getTownAgentId() != null){
				Double k = (n.getRetailPrice().doubleValue()  * systemParameters.get("k").doubleValue()* systemParameters.get("x").doubleValue());
				p.setTownAgentProfit(k.longValue());//社区代理供应链分润
				l = l+ k;
			}
			p.setChainTotal(l.longValue());//供应链支出合计
			Double mm = f-g-l;
			p.setChainRemain(mm.longValue());//供应链剩余
			Double nn = c-b-d-l-g;
			
			p.setYstRemain(nn.longValue());//易商通总余
			p.setPhRemain(g.longValue());//普惠总余
			
			p.setCityAgentId(n.getCityAgentId());
			p.setCountyAgentId(n.getCountyAgentId());
			p.setTownAgentId(n.getTownAgentId());
			p.setCityAgentName(n.getCityAgentName());
			p.setCountyAgentName(n.getCountyAgentName());
			p.setTownAgentName(n.getTownAgentName());
			p.setOrderTime(n.getOrderTime());
			purchaseOrderProfitList.add(p);
		}
		return purchaseOrderProfitList;
	}
	
	/**
	 * 
	* @Title: insertUserProfitRecord
	* @Description: TODO(入用户分润记录表)
	* @author Mr.Dong
	* @date  2017年7月4日 下午2:33:41
	* @param unLineOrderProfitList
	* @throws Exception
	 */
	public void insertUserProfitRecord(List<PurchaseOrderProfit> purchaseOrderProfitList) throws Exception {
		List<SupplyProfitTotal> spt = new ArrayList<SupplyProfitTotal>();
		for(PurchaseOrderProfit m : purchaseOrderProfitList){
			if(m.getCityAgentId()!= null){
				SupplyProfitTotal sp = new SupplyProfitTotal();
				sp.setProfited(m.getCityAgentProfit());
				sp.setUserId(m.getCityAgentId());
				sp.setUserName(m.getCityAgentName());
				sp.setUserType(RoleEnum.CITY_AGENT.getCode());
				spt.add(sp);
			}
			if(m.getCountyAgentId() != null){
				SupplyProfitTotal sp = new SupplyProfitTotal();
				sp.setProfited(m.getCountyAgentProfit());
				sp.setUserId(m.getCountyAgentId());
				sp.setUserName(m.getCountyAgentName());
				sp.setUserType(RoleEnum.COUNTY_AGENT.getCode());
				spt.add(sp);
			}
			if(m.getTownAgentId()  != null){
				SupplyProfitTotal sp = new SupplyProfitTotal();
				sp.setProfited(m.getTownAgentProfit());
				sp.setUserId(m.getTownAgentId());
				sp.setUserName(m.getTownAgentName());
				sp.setUserType(RoleEnum.COMMUNITY_AGENT.getCode());
				spt.add(sp);
			}
		}
		//批量入用户分润记录表
		if(spt.size() > 0){
			iUnlineOrderProfitMapper.insertUserProfitRecord(spt);
		}
	}
}
