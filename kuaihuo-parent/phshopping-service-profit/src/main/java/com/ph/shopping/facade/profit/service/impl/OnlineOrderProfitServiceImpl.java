package com.ph.shopping.facade.profit.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
import com.ph.shopping.facade.mapper.IOnlineOrderProfitMapper;
import com.ph.shopping.facade.mapper.IPurchaseOrderProfitMapper;
import com.ph.shopping.facade.mapper.IUnlineOrderProfitMapper;
import com.ph.shopping.facade.member.dto.MemberDTO;
import com.ph.shopping.facade.member.entity.Member;
import com.ph.shopping.facade.member.service.IMemberService;
import com.ph.shopping.facade.profit.dto.OnlineOrderProfitDTO;
import com.ph.shopping.facade.profit.entity.OnlineOrderProfit;
import com.ph.shopping.facade.profit.entity.PromoterProfitRecord;
import com.ph.shopping.facade.profit.entity.PurchaseOrderProfit;
import com.ph.shopping.facade.profit.entity.SupplyProfitTotal;
import com.ph.shopping.facade.profit.exception.ProfitException;
import com.ph.shopping.facade.profit.exception.ProfitExceptionEnum;
import com.ph.shopping.facade.profit.service.IOnlineOrderProfitService;
import com.ph.shopping.facade.profit.vo.MemberScoreVO;
import com.ph.shopping.facade.profit.vo.OnLineOrderVO;
import com.ph.shopping.facade.profit.vo.OnlineOrderProfitVO;
import com.ph.shopping.facade.profit.vo.UserBalanceRecordVO;
import com.ph.shopping.facade.profit.vo.UserBalanceVO;
import com.ph.shopping.facade.spm.dto.AgentDTO;
import com.ph.shopping.facade.spm.dto.MerchantDTO;
import com.ph.shopping.facade.spm.service.IAgentService;
import com.ph.shopping.facade.spm.service.IMerchantService;
import com.ph.shopping.facade.spm.spmEnum.SPMEnum;
import com.ph.shopping.facade.spm.vo.AgentVO;
import com.ph.shopping.facade.spm.vo.MerchantVO;
import com.ph.shopping.facade.system.service.ISystemParameterService;

/**
 * 
 * @ClassName: OnlineOrderProfitServiceImpl
 * @Description: 线上订单分润实现类
 * @author Mr.Dong
 * @date 2017年5月9日 下午4:34:40
 */
@Component
@Service(version = "1.0.0")
public class OnlineOrderProfitServiceImpl  implements IOnlineOrderProfitService {

	private Logger logger = LoggerFactory.getLogger(PurchaseOrderProfitServiceImpl.class);
	
	//线下订单分润Mapper
	@Autowired
	IUnlineOrderProfitMapper iUnlineOrderProfitMapper;
	
	@Autowired
	IOnlineOrderProfitMapper iOnlineOrderProfitMapper;
	
	//供应链订单分润Mapper
	@Autowired
	IPurchaseOrderProfitMapper purchaseOrderProfitMapper;
	
	//代理接口
	 @Reference(version = "1.0.0")
	 private IAgentService iAgentService;
	 
	//会员接口
	@Reference(version = "1.0.0")
	private IMemberService iMemberService;
	
	//商户接口
	@Reference(version = "1.0.0")
	private IMerchantService iMerchantService;
	
	 //系统参数接口
	 @Reference(version="1.0.0")
	 private ISystemParameterService iSystemParameterService;
	 
	 /**
		 * 线上订单分润list
		 */
		@Override
		public Result getOnLineOrderProfitList(OnlineOrderProfitDTO onlineOrderProfitDTO, PageBean pagebean) {
			PageHelper.startPage(pagebean.getPageNum(), pagebean.getPageSize());
			List<OnlineOrderProfitVO> onlineOrderProfitVOList = iOnlineOrderProfitMapper.getOnLineOrderProfitList(onlineOrderProfitDTO);
			PageInfo<OnlineOrderProfitVO>pageInfo=new PageInfo<OnlineOrderProfitVO>(onlineOrderProfitVOList);
			//装换一下
			for(OnlineOrderProfitVO m : pageInfo.getList()){
				m.setProductMoney1(MoneyTransUtil.transDivisDouble(m.getProductMoney()));
				m.setSettlementPrice1(MoneyTransUtil.transDivisDouble(m.getSettlementPrice()));
				m.setFreight1(MoneyTransUtil.transDivisDouble(m.getFreight()));
				m.setChargeFee1(MoneyTransUtil.transDivisDouble(m.getChargeFee()));
				m.setPhChargeFee1(MoneyTransUtil.transDivisDouble(m.getPhChargeFee()));
				m.setYstChargeFee1(MoneyTransUtil.transDivisDouble(m.getYstChargeFee()));
				m.setCityAgentProfit1(MoneyTransUtil.transDivisDouble(m.getCityAgentProfit()));
				m.setCountyAgentProfit1(MoneyTransUtil.transDivisDouble(m.getCountyAgentProfit()));
				m.setTownAgentProfit1(MoneyTransUtil.transDivisDouble(m.getTownAgentProfit()));
				m.setCityPromoterProfit1(MoneyTransUtil.transDivisDouble(m.getCityPromoterProfit()));
				m.setCountyPromoterProfit1(MoneyTransUtil.transDivisDouble(m.getCountyPromoterProfit()));
				m.setTownPromoterProfit1(MoneyTransUtil.transDivisDouble(m.getTownPromoterProfit()));
				m.setMerchantPromoterProfit1(MoneyTransUtil.transDivisDouble(m.getMerchantPromoterProfit()));
				m.setChargeProfitTotal1(MoneyTransUtil.transDivisDouble(m.getChargeProfitTotal()));
				m.setChargeProfitRemain1(MoneyTransUtil.transDivisDouble(m.getChargeProfitRemain()));
				m.setMerchantChainProfit1(MoneyTransUtil.transDivisDouble(m.getMerchantChainProfit()));
				m.setChainProfit1(MoneyTransUtil.transDivisDouble(m.getChainProfit()));
				m.setPhIncome1(MoneyTransUtil.transDivisDouble(m.getPhIncome()));
				m.setYstIncome1(MoneyTransUtil.transDivisDouble(m.getYstIncome()));
				m.setCityAgentChainProfit1(MoneyTransUtil.transDivisDouble(m.getCityAgentChainProfit()));
				m.setCountyAgentChainProfit1(MoneyTransUtil.transDivisDouble(m.getCountyAgentChainProfit()));
				m.setTownAgentChainProfit1(MoneyTransUtil.transDivisDouble(m.getTownAgentChainProfit()));
				m.setChainTotal1(MoneyTransUtil.transDivisDouble(m.getChainTotal()));
				m.setChainRemain1(MoneyTransUtil.transDivisDouble(m.getChainRemain()));
				m.setYstRemain1(MoneyTransUtil.transDivisDouble(m.getYstRemain()));
				m.setPhRemain1(MoneyTransUtil.transDivisDouble(m.getPhRemain()));
			}
			return  ResultUtil.getResult(RespCode.Code.SUCCESS,pageInfo.getList(),pageInfo.getTotal());
		}
		
		/**
		 * 
		* @Title: OnLineOrderProfit
		* @Description: TODO(线上订单分润)
		* @author Mr.Dong
		* @date  2017年5月26日 下午4:56:41
		* @return
		 */
		public Result OnLineOrderProfit(){
			Result result = ResultUtil.getResult(RespCode.Code.SUCCESS);
			try{
				//取需要分润的线上订单
				List<OnLineOrderVO> onlineOrderList = iOnlineOrderProfitMapper.getOnlineOrderList();
				if(onlineOrderList.size() < 1){
					logger.info("无线上订单分润数据");
					return ResultUtil.getResult(RespCode.Code.SUCCESS);
				}
				int i = 0;
				int j = 0;
				//通过收货地址区域获取此商户的各级代理和各级代理的推广师
				for(OnLineOrderVO m : onlineOrderList){
					try{
						AgentDTO agentDTO = new AgentDTO();
						agentDTO.setProvinceId(m.getShippingProvinceId());
						agentDTO.setCityId(m.getShippingCityId());
						agentDTO.setCountyId(m.getShippingCountyId());
						agentDTO.setTownId(m.getShippingTownId());
						
						agentDTO.setStatus(SPMEnum.agentStatus.ONE.getIndex());//审核通过
						agentDTO.setIsDelete(SPMEnum.agentIsDelete.ZERO.getIndex());//没有被删除
						agentDTO.setIsFrozen(SPMEnum.agentIsFrozen.ONE.getIndex());//冻结的 传过去代理直接非冻结
						List<AgentVO> agentPositionList = iAgentService.getAgentPositionList(agentDTO);
						logger.info("通过区域查询各级代理和代理的推广师返回参数："+JSON.toJSONString(agentPositionList));
						if(agentPositionList.size() > 0){
							logger.info("循环代理数据");
							for(AgentVO n : agentPositionList){//循环返回的代理
								if(n.getAgentLevelId() == 1){//市级代理
									m.setCityAgentId(n.getUserId());
									m.setCityAgentName(n.getAgentName());
									
									if(n.getPromoterId() !=null){//如果有推广师 
										MemberDTO memberDTO = new MemberDTO();
										memberDTO.setId(n.getPromoterId());
										Result promotionIsCanProfit = iMemberService.getPromotionIsCanProfit(memberDTO);
										logger.info("调用会员接口判断推广师是否正常,返回参数"+ JSON.toJSONString(promotionIsCanProfit));
										Member member = (Member)promotionIsCanProfit.getData();
										if(member !=null){
											m.setCityPromoterId(n.getPromoterId());//市级代理推广师id
											m.setCityPromoterName(member.getMemberName());//市级代理名字
										}else{
											m.setCityPromoterId(null);//市级代理推广师id
											m.setCityPromoterName(null);//市级代理名字
										}
									}
								}
								if(n.getAgentLevelId() == 2){//县级代理
									m.setCountyAgentId(n.getUserId());
									m.setCountyAgentName(n.getAgentName());
									
									if(n.getPromoterId() !=null){//如果有推广师 
										MemberDTO memberDTO = new MemberDTO();
										memberDTO.setId(n.getPromoterId());
										Result promotionIsCanProfit = iMemberService.getPromotionIsCanProfit(memberDTO);
										logger.info("调用会员接口判断推广师是否正常,返回参数"+ JSON.toJSONString(promotionIsCanProfit));
										Member member = (Member)promotionIsCanProfit.getData();
										if(member !=null){
											m.setCountyPromoterId(n.getPromoterId());//县级代理推广师id
											m.setCountyPromoterName(member.getMemberName());//县级代理推广师名字
										}else{
											m.setCountyPromoterId(null);//县级代理推广师id
											m.setCountyPromoterName(null);//县级代理推广师名字
										}
									}
								}
								if(n.getAgentLevelId() == 3){//社区代理
									m.setTownAgentId(n.getUserId());
									m.setTownAgentName(n.getAgentName());
									
									if(n.getPromoterId() !=null){//如果有推广师 
										MemberDTO memberDTO = new MemberDTO();
										memberDTO.setId(n.getPromoterId());
										Result promotionIsCanProfit = iMemberService.getPromotionIsCanProfit(memberDTO);
										logger.info("调用会员接口判断推广师是否正常,返回参数"+ JSON.toJSONString(promotionIsCanProfit));
										Member member = (Member)promotionIsCanProfit.getData();
										if(member !=null){
											 m.setTownPromoterId(n.getPromoterId());//社区代理推广师id
											 m.setTownPromoterName(member.getMemberName());//社区代理推广师名字
										}else{
											m.setTownPromoterId(null);//社区代理推广师id
											 m.setTownPromoterName(null);//社区代理推广师名字
										}
									}
								}
							}
						}
						//商户推广师分润相关
						if(m.getMerchantId() != null){//商户id存在
							MerchantDTO merchantDTO =new MerchantDTO();
							merchantDTO.setUserId(m.getMerchantId());
							merchantDTO.setStatus((byte)1);// 1：审核通过
							merchantDTO.setIsFrozen((byte)0);//0 ：未冻结
							merchantDTO.setIsDelete((byte)0);//0: 未删除
							List<MerchantVO> merchantList = iMerchantService.getMerchantList(merchantDTO);
							logger.info("调用商户接口判断商户是否正常,返回参数"+ JSON.toJSONString(merchantList));
							if(merchantList.size() > 0){//证明商户是正常的
								if(m.getMerchantPromoterId() != null){//商户推广师存在 判断是否冻结
									MemberDTO memberDTO = new MemberDTO();
									memberDTO.setId(m.getMerchantPromoterId());
									Result promotionIsCanProfit = iMemberService.getPromotionIsCanProfit(memberDTO);
									logger.info("调用会员接口判断推广师是否正常,返回参数"+ JSON.toJSONString(promotionIsCanProfit));
									Member member = (Member)promotionIsCanProfit.getData();
									if(member != null){
										m.setMerchantPromoterId(member.getId());;//商户的推广师id
										m.setMerchantPromoterName(member.getMemberName());;//商户的推广师名字
									}else{
										m.setMerchantPromoterId(null);;//商户的推广师id
										m.setMerchantPromoterName(null);;//商户的推广师名字
									}
								}
							}else{
								m.setMerchantId(null);//商户id
								m.setMerchantName(null);//商户名字
								m.setMerchantPromoterId(null);;//商户的推广师id
								m.setMerchantPromoterName(null);;//商户的推广师名字
							}
						}else{
							m.setMerchantId(null);//商户id
							m.setMerchantName(null);//商户名字
							m.setMerchantPromoterId(null);;//商户的推广师id
							m.setMerchantPromoterName(null);;//商户的推广师名字
						}
					} catch (Exception e) {
						logger.debug("循环报错");
						e.printStackTrace();
					}
				}
				//获取系统参数
				Map<String, Double> systemParameters = getSystemParameters();
				//获取线上订单分润记录
				List<OnlineOrderProfit> onlineOrderProfitList = onlineOrderProfitList(systemParameters,onlineOrderList);
				//批量插入线上订单分润记录表
				int insertOnLineOrderProfitBatch = iOnlineOrderProfitMapper.insertOnLineOrderProfitBatch(onlineOrderProfitList);
				if(insertOnLineOrderProfitBatch < 1){
					throw new Exception("批量插入线上订单分润记录表异常");
				}
				//更改线上子订单表中的分润状态为已近分润
				int updateOnLineOrderBonus = iOnlineOrderProfitMapper.updateOnLineOrderBonus(onlineOrderList);
				if(updateOnLineOrderBonus < 1){
					throw new Exception("更改线上子订单表中的分润状态为已分润异常");
				}
				//开始代理的管理费和供应链的分润
				startAgentProfit(onlineOrderProfitList);
				//开始推广师的可用积分的增加
				startMemberProfit(onlineOrderProfitList);
				//入推广师分润记录表
				insertPromoterProfitRecord(onlineOrderProfitList);
				//入用户分润记录表
				insertUserProfitRecord(onlineOrderProfitList);
				return result;
			} catch (Exception e) {
				e.printStackTrace();
				throw new ProfitException(ProfitExceptionEnum.ONLINE_ORDERP_ROFIT_EXCEPTION);
			}
		}
		
		/**
		 * 
		* @Title: getSystemParameters
		* @Description: 获取系统参数
		* @author Mr.Dong
		* @date  2017年5月23日 上午11:56:03
		* @return
		 */
		@SuppressWarnings("unchecked")
		public Map<String,Double> getSystemParameters(){
			//获取系统参数
			List<Long> ids = new ArrayList<Long>();
			/*管理费相关*/
			ids.add(4l);//管理费分润比率
			ids.add(5l);//普惠管理费分润比率
			ids.add(7l);//市级代理管理费分润比率
			ids.add(8l);//县级代理管理费分润比率
			ids.add(9l);//社区代理管理费分润比率
			ids.add(10l);//市级代理推广师分润比率
			ids.add(11l);//县级代理推广师分润比率
			ids.add(12l);//社区代理推广师分润比率
			ids.add(13l);//商户的推广师分润比率
			ids.add(14l);//供链股权分配比例  69%
			/*供应链相关*/
			ids.add(23l);//商户分润比率(自提)
			ids.add(3l);//供应链利润比率
			ids.add(21l);//普惠供应链收入比率
			ids.add(22l);//易商通供应链收入比率
			ids.add(15L);//市级代理供应链分润比率
			ids.add(16L);//县代供链分润比例
			ids.add(17L);//区代供链分润比例
			
			
			
			Result systemParameterListByIds = iSystemParameterService.getSystemParameterListByIds(ids);
			logger.info("调用系统参数接口返回参数：result={}",JSON.toJSONString(systemParameterListByIds));
			Map<Long,Object> map = (Map<Long,Object>)systemParameterListByIds.getData();
			/*管理费*/
			Double DSign = (Double)(map.get(new Long (4)));//管理费
			Double ESign = (Double)(map.get(new Long (5)));//普惠管理费分润比率
			Double GSign = (Double)(map.get(new Long (7)));//市级代理管理费分润比率
			Double HSign = (Double)(map.get(new Long (8)));//县级代理管理费分润比率
			Double ISign = (Double)(map.get(new Long (9)));//社区代理管理费分润比率
			Double JSign = (Double)(map.get(new Long (10)));//市级代理推广师分润比率
			Double KSign = (Double)(map.get(new Long (11)));//县级代理推广师分润比率
			Double LSign = (Double)(map.get(new Long (12)));//社区代理推广师分润比率
			Double MSign = (Double)(map.get(new Long (13)));//商户的推广师分润比率
			Double ZSign = (Double)(map.get(new Long (14)));//供链股权分配比例  69%-----------------特殊
			
			/*供应链*/
			Double OSign = (Double)(map.get(new Long (23)));//商户供应链利润比率
			Double RSign = (Double)(map.get(new Long (3)));//供应链利润比率
			Double SSign = (Double)(map.get(new Long (21)));//普惠供应链收入比率
			Double TSign = (Double)(map.get(new Long (22)));//易商通供应链收入比率
			Double USign = (Double)(map.get(new Long (15)));//市级代理供应链分润比率
			Double WSign = (Double)(map.get(new Long (16)));//县代供链分润比例
			Double XSign = (Double)(map.get(new Long (17)));//社区代供链分润比例
			
			
			
			Map<String,Double> mapNew = new  HashMap<String, Double>();
			/*管理费*/
			mapNew.put("d", DSign);
			mapNew.put("e", ESign);
			mapNew.put("g", GSign);
			mapNew.put("h", HSign);
			mapNew.put("i", ISign);
			mapNew.put("j", JSign);
			mapNew.put("k", KSign);
			mapNew.put("l", LSign);
			mapNew.put("m", MSign);
			mapNew.put("z", ZSign); //供链股权分配比例  69%
			/*供应链*/
			mapNew.put("o", OSign);
			mapNew.put("r", RSign);
			mapNew.put("s", SSign);
			mapNew.put("t", TSign);
			mapNew.put("u", USign);
			mapNew.put("w", WSign);
			mapNew.put("x", XSign);
			
			return mapNew;
		}
		
		/**
		 * 
		* @Title: getOnLineOrderProfitList
		* @Description: TODO(获取线上订单分润记录list)
		* @author Mr.Dong
		* @date  2017年5月27日 下午2:35:15
		* @param systemParameters
		* @param onlineOrderList
		* @return
		 */
		public List<OnlineOrderProfit> onlineOrderProfitList(Map<String, Double> systemParameters,List<OnLineOrderVO> onlineOrderList){
			List<OnlineOrderProfit> onlineOrderProfitList = new  ArrayList<OnlineOrderProfit>();
			for(OnLineOrderVO m: onlineOrderList){
				OnlineOrderProfit p = new OnlineOrderProfit();
				//统计的几个
				Double pp = 0d;//管理费支出合计
				
				Double yy = 0d;//供应链支出合计
				
				Double zz = 0d;//易商通总余
				
				Double vv = 0d;//普惠总余
				p.setOrderNo(m.getOrderNo());
				p.setProductMoney(m.getRetailPrice());
				Double a = m.getRetailPrice().doubleValue();//零售价
				p.setSettlementPrice(m.getSettlementPrice());
				Double b = m.getSettlementPrice().doubleValue();//结算成本
				p.setFreight(m.getFreight());
				Double c = m.getFreight().doubleValue();//物流费用
				Double d = a * systemParameters.get("d");//管理费
				p.setChargeFee(d.longValue());
				Double e = a * systemParameters.get("e");//普惠管理费
				p.setPhChargeFee(e.longValue());
				Double f = d - e; //易商通管理费
				p.setYstChargeFee(f.longValue());//易商通管理费
				zz = a-b-c;
				
				Double r = a * systemParameters.get("r");//供应链利润
				p.setChainProfit(r.longValue());
				Double s = a * systemParameters.get("s");//普惠供应链利润
				p.setPhIncome(s.longValue());
				Double t = r - s ;
				p.setYstIncome(t.longValue());//易商通供应链收入
				
				if(m.getCityAgentId() !=null ){//市级代理
					p.setCityAgentId(m.getCityAgentId());
					p.setCityAgentName(m.getCityAgentName());
					Double g = a * systemParameters.get("g") * systemParameters.get("z") ;
					p.setCityAgentProfit(g.longValue());//市级代理分润
					Double u = a * systemParameters.get("u") * systemParameters.get("z") ;
					p.setCityAgentChainProfit(u.longValue());//市级代理供应链分润
					pp = pp + g;
					yy = yy + u;
				}
				if(m.getCountyAgentId() !=null){//县级代理
					p.setCountyAgentId(m.getCountyAgentId());
					p.setCountyAgentName(m.getCountyAgentName());
					Double h = a * systemParameters.get("h") * systemParameters.get("z") ;
					p.setCountyAgentProfit(h.longValue());//县级代理分润
					Double w = a * systemParameters.get("w") * systemParameters.get("z") ;
					p.setCountyAgentChainProfit(w.longValue());//县级代理供应链分润
					pp = pp + h;
					yy = yy + w;
				}
				if(m.getTownAgentId() != null){//社区代理
					p.setTownAgentId(m.getTownAgentId());
					p.setTownAgentName(m.getTownAgentName());
					Double i = a * systemParameters.get("i") * systemParameters.get("z") ;
					p.setTownAgentProfit(i.longValue());//社区代理分润
					Double x = a * systemParameters.get("x") * systemParameters.get("z") ;
					p.setTownAgentChainProfit(x.longValue());//社区代理供应链分润
					pp = pp + i;
					yy = yy + x;
				}
				
				/*推广师相关---------------------------start*/
				if(m.getCityPromoterId() !=null){//市级代理推广师
					Double j = a * systemParameters.get("j");
					p.setCityPromoterId(m.getCityPromoterId());//市级代理推广师
					p.setCityPromoterName(m.getCityPromoterName());
					p.setCityPromoterProfit(j.longValue());//市级代理推广师分润
					pp = pp + j;
				}
				
				if(m.getCountyPromoterId() !=null){//县级代理推广师傅
					Double k = a * systemParameters.get("k");
					p.setCountyPromoterId(m.getCountyPromoterId());
					p.setCountyPromoterName(m.getCountyPromoterName());
					p.setCountyPromoterProfit(k.longValue());
					pp = pp + k;
				}
				if(m.getTownPromoterId() != null){//社区级代理推广师傅
					Double l = a * systemParameters.get("l");
					p.setTownPromoterId(m.getTownPromoterId());
					p.setTownPromoterName(m.getTownPromoterName());
					p.setTownPromoterProfit(l.longValue());
					pp = pp + l;
				}
				if(m.getMerchantPromoterId() != null){//商户推广师
					Double mm = a * systemParameters.get("m");
					p.setMerchantPromoterId(m.getMerchantPromoterId());
					p.setMerchantPromoterName(m.getMerchantPromoterName());
					p.setMerchantPromoterProfit(mm.longValue());
					pp = pp + mm;
				}
				/*推广师相关---------------------------end*/
				
				/*商户相关---------------------------start*/
				if(m.getMerchantId() != null){
					Double o = a * systemParameters.get("o");
					p.setMerchantId(m.getMerchantId());
					p.setMerchantName(m.getMerchantName());
					p.setMerchantChainProfit(o.longValue());
					zz = zz - o;
				}
				/*商户相关---------------------------end*/
				p.setChargeProfitTotal(pp.longValue());//管理费支出合计
				Double nn = f - pp;//管理费剩余
				p.setChargeProfitRemain(nn.longValue());
				
				p.setChainTotal(yy.longValue());//供应链支出合计
				
				Double qq = t - yy;
				p.setChainRemain(qq.longValue());//供应链剩余
				
				zz = zz - pp - yy - e -s;
				vv = e + s;
				p.setYstRemain(zz.longValue());//易商通总余
				p.setPhRemain(vv.longValue());//普惠总余
				
				p.setOrderTime(m.getOrderTime());//订单时间
				p.setShippingProvinceId(m.getShippingProvinceId());
				p.setShippingCountyId(m.getShippingCountyId());
				p.setShippingCityId(m.getShippingCityId());
				p.setShippingTownId(m.getShippingTownId());
				
				onlineOrderProfitList.add(p);
			}
			return onlineOrderProfitList;
		}
		/**
		 * 
		* @Title: startAgentProfit
		* @Description: TODO(开始代理的管理费和供应链的分润)
		* @author Mr.Dong
		* @date  2017年5月27日 下午5:32:18
		* @param onlineOrderProfitList
		 */
		public void startAgentProfit(List<OnlineOrderProfit> onlineOrderProfitList) throws Exception{
			List<UserBalanceVO> userBalanceVoList = new ArrayList<UserBalanceVO>();
			List<UserBalanceRecordVO> userBalanceRecordVoList = new ArrayList<UserBalanceRecordVO>();
			
			//循环线上订单分润 
			for(OnlineOrderProfit m : onlineOrderProfitList){
				if(m.getCityAgentId()!= null){
					/*市级代理管理费分润*/
					UserBalanceVO  userBalanceVo = new UserBalanceVO();
					userBalanceVo.setManageId(m.getCityAgentId());
					userBalanceVo.setBalance(m.getCityAgentProfit());
					userBalanceVoList.add(userBalanceVo);
					
					UserBalanceRecordVO userBalanceRecordVO = new UserBalanceRecordVO();
					userBalanceRecordVO.setUserId(m.getCityAgentId());
					userBalanceRecordVO.setOrderNo(m.getOrderNo());
					userBalanceRecordVO.setMoney(m.getCityAgentProfit());
					userBalanceRecordVO.setTransCode(String.valueOf(TransCodeEnum.ONLINE_ORDER_PROFIT_MANAGE_TRANSCODE.getCode()));
					userBalanceRecordVO.setUserType(String.valueOf(RoleEnum.CITY_AGENT.getCode()));
					userBalanceRecordVoList.add(userBalanceRecordVO);
					
					/*市级代理供应链分润*/
					UserBalanceVO  userBalanceVo1 = new UserBalanceVO();
					userBalanceVo1.setManageId(m.getCityAgentId());
					userBalanceVo1.setBalance(m.getCityAgentChainProfit());
					userBalanceVoList.add(userBalanceVo1);
					
					UserBalanceRecordVO userBalanceRecordVO1 = new UserBalanceRecordVO();
					userBalanceRecordVO1.setUserId(m.getCityAgentId());
					userBalanceRecordVO1.setOrderNo(m.getOrderNo());
					userBalanceRecordVO1.setMoney(m.getCityAgentChainProfit());
					userBalanceRecordVO1.setTransCode(String.valueOf(TransCodeEnum.ONLINE_ORDER_PROFIT_CHAIN_TRANSCODE.getCode()));
					userBalanceRecordVO1.setUserType(String.valueOf(RoleEnum.CITY_AGENT.getCode()));
					userBalanceRecordVoList.add(userBalanceRecordVO1);
					
				}
				if(m.getCountyAgentId() != null){
					/*县级代理管理费分润*/
					UserBalanceVO  userBalanceVo = new UserBalanceVO();
					userBalanceVo.setManageId(m.getCountyAgentId());
					userBalanceVo.setBalance(m.getCountyAgentProfit());
					userBalanceVoList.add(userBalanceVo);
					
					UserBalanceRecordVO userBalanceRecordVO = new UserBalanceRecordVO();
					userBalanceRecordVO.setUserId(m.getCountyAgentId());
					userBalanceRecordVO.setOrderNo(m.getOrderNo());
					userBalanceRecordVO.setMoney(m.getCountyAgentProfit());
					userBalanceRecordVO.setTransCode(String.valueOf(TransCodeEnum.ONLINE_ORDER_PROFIT_MANAGE_TRANSCODE.getCode()));
					userBalanceRecordVO.setUserType(String.valueOf(RoleEnum.COUNTY_AGENT.getCode()));
					userBalanceRecordVoList.add(userBalanceRecordVO);
					
					/*县级代理供应链分润*/
					UserBalanceVO  userBalanceVo1 = new UserBalanceVO();
					userBalanceVo1.setManageId(m.getCountyAgentId());
					userBalanceVo1.setBalance(m.getCountyAgentChainProfit());
					userBalanceVoList.add(userBalanceVo1);
					
					UserBalanceRecordVO userBalanceRecordVO1 = new UserBalanceRecordVO();
					userBalanceRecordVO1.setUserId(m.getCountyAgentId());
					userBalanceRecordVO1.setOrderNo(m.getOrderNo());
					userBalanceRecordVO1.setMoney(m.getCountyAgentChainProfit());
					userBalanceRecordVO1.setTransCode(String.valueOf(TransCodeEnum.ONLINE_ORDER_PROFIT_CHAIN_TRANSCODE.getCode()));
					userBalanceRecordVO1.setUserType(String.valueOf(RoleEnum.COUNTY_AGENT.getCode()));
					userBalanceRecordVoList.add(userBalanceRecordVO1);
					
				}
				if(m.getTownAgentId()  != null){
					/*社区级代理供应链分润*/
					UserBalanceVO  userBalanceVo = new UserBalanceVO();
					userBalanceVo.setManageId(m.getTownAgentId());
					userBalanceVo.setBalance(m.getTownAgentProfit());
					userBalanceVoList.add(userBalanceVo);
					
					UserBalanceRecordVO userBalanceRecordVO = new UserBalanceRecordVO();
					userBalanceRecordVO.setUserId(m.getTownAgentId());
					userBalanceRecordVO.setOrderNo(m.getOrderNo());
					userBalanceRecordVO.setMoney(m.getTownAgentProfit());
					userBalanceRecordVO.setTransCode(String.valueOf(TransCodeEnum.ONLINE_ORDER_PROFIT_MANAGE_TRANSCODE.getCode()));
					userBalanceRecordVO.setUserType(String.valueOf(RoleEnum.COMMUNITY_AGENT.getCode()));
					userBalanceRecordVoList.add(userBalanceRecordVO);
					
					/*社区级代理供应链分润*/
					UserBalanceVO  userBalanceVo1 = new UserBalanceVO();
					userBalanceVo1.setManageId(m.getTownAgentId());
					userBalanceVo1.setBalance(m.getTownAgentChainProfit());
					userBalanceVoList.add(userBalanceVo1);
					
					UserBalanceRecordVO userBalanceRecordVO1 = new UserBalanceRecordVO();
					userBalanceRecordVO1.setUserId(m.getTownAgentId());
					userBalanceRecordVO1.setOrderNo(m.getOrderNo());
					userBalanceRecordVO1.setMoney(m.getTownAgentChainProfit());
					userBalanceRecordVO1.setTransCode(String.valueOf(TransCodeEnum.ONLINE_ORDER_PROFIT_CHAIN_TRANSCODE.getCode()));
					userBalanceRecordVO1.setUserType(String.valueOf(RoleEnum.COMMUNITY_AGENT.getCode()));
					userBalanceRecordVoList.add(userBalanceRecordVO1);
				}
				if(m.getMerchantId()  != null){
					/*商户供应链分润*/
					UserBalanceVO  userBalanceVo = new UserBalanceVO();
					userBalanceVo.setManageId(m.getMerchantId());
					userBalanceVo.setBalance(m.getMerchantChainProfit());
					userBalanceVoList.add(userBalanceVo);
					
					UserBalanceRecordVO userBalanceRecordVO = new UserBalanceRecordVO();
					userBalanceRecordVO.setUserId(m.getMerchantId());
					userBalanceRecordVO.setOrderNo(m.getOrderNo());
					userBalanceRecordVO.setMoney(m.getMerchantChainProfit());
					userBalanceRecordVO.setTransCode(String.valueOf(TransCodeEnum.ONLINE_ORDER_PROFIT_MANAGE_TRANSCODE.getCode()));
					userBalanceRecordVO.setUserType(String.valueOf(RoleEnum.MERCHANT.getCode()));
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
		 * 
		* @Title: StartMemberProfit
		* @Description: TODO(线上订单分润——推广师加可用积分)
		* @author Mr.Dong
		* @date  2017年5月31日 上午9:21:51
		* @param onlineOrderProfitList
		 */
		public void  startMemberProfit(List<OnlineOrderProfit> onlineOrderProfitList) throws Exception {
			List<MemberScoreVO> memberScoreVOList = new ArrayList<MemberScoreVO>();
			for(OnlineOrderProfit m : onlineOrderProfitList){
				if(m.getCityPromoterId() !=null){//市推
					MemberScoreVO memberScoreVO = new  MemberScoreVO();
					memberScoreVO.setMemberId(m.getCityPromoterId());
					memberScoreVO.setOrderNo(m.getOrderNo());
					memberScoreVO.setScore(m.getCityPromoterProfit());
					memberScoreVO.setTransCode(String.valueOf(TransCodeEnum.ONLINE_ORDER_PROFIT_ADD_SCORE_TRANSCODE.getCode()));
					memberScoreVOList.add(memberScoreVO);
				}
				if(m.getCountyPromoterId() != null){//县推
					MemberScoreVO memberScoreVO = new  MemberScoreVO();
					memberScoreVO.setMemberId(m.getCountyPromoterId());
					memberScoreVO.setOrderNo(m.getOrderNo());
					memberScoreVO.setScore(m.getCountyPromoterProfit());
					memberScoreVO.setTransCode(String.valueOf(TransCodeEnum.ONLINE_ORDER_PROFIT_ADD_SCORE_TRANSCODE.getCode()));
					memberScoreVOList.add(memberScoreVO);
				}
				if(m.getTownPromoterId() != null){//社区推
					MemberScoreVO memberScoreVO = new  MemberScoreVO();
					memberScoreVO.setMemberId(m.getTownPromoterId());
					memberScoreVO.setOrderNo(m.getOrderNo());
					memberScoreVO.setScore(m.getTownPromoterProfit());
					memberScoreVO.setTransCode(String.valueOf(TransCodeEnum.ONLINE_ORDER_PROFIT_ADD_SCORE_TRANSCODE.getCode()));
					memberScoreVOList.add(memberScoreVO);
				}
				if(m.getMerchantPromoterId() != null){//商推
					MemberScoreVO memberScoreVO = new  MemberScoreVO();
					memberScoreVO.setMemberId(m.getMerchantPromoterId());
					memberScoreVO.setOrderNo(m.getOrderNo());
					memberScoreVO.setScore(m.getMerchantPromoterProfit());
					memberScoreVO.setTransCode(String.valueOf(TransCodeEnum.ONLINE_ORDER_PROFIT_ADD_SCORE_TRANSCODE.getCode()));
					memberScoreVOList.add(memberScoreVO);
				}
			}
			//1  更新推广师可用积分(单条)  2 插入积分流水总表 ph_score_total_trade      3 插入积分流水进账表 ph_score_income_trade 
			if(memberScoreVOList.size() > 0){
				for(MemberScoreVO m : memberScoreVOList){
					int updateMemberScore = iUnlineOrderProfitMapper.updateMemberScore(m);
					if(updateMemberScore < 0){
						throw new Exception("更新会员可用积分异常");
					}
					int insertMemberScoreTotal = iUnlineOrderProfitMapper.insertMemberScoreTotal(m);
					if(insertMemberScoreTotal < 0){
						throw new Exception("插入会员积分总表异常");
					}
					int insertMemberScoreIncome = iUnlineOrderProfitMapper.insertMemberScoreIncome(m);
					if(insertMemberScoreIncome < 0){
						throw new Exception("插入会员积分入账流水表异常");
					}
				}
			}
		}

		/**
		 * 
		* Title: getOnLineOrderProfitEXCEL
		* Description:线上订单分润导出EXCEL
		* @author Mr.Dong
		* @date 2017年5月31日 上午10:34:49
		* @param onlineOrderProfitDTO
		* @return
		* @see com.ph.shopping.facade.profit.service.IOnlineOrderProfitService#getOnLineOrderProfitEXCEL(com.ph.shopping.facade.profit.dto.OnlineOrderProfitDTO)
		 */
		@Override
		public Result getOnLineOrderProfitEXCEL(OnlineOrderProfitDTO onlineOrderProfitDTO) {
			try {
				 List<OnlineOrderProfitVO> onLineOrderProfitList = iOnlineOrderProfitMapper
						.getOnLineOrderProfitList(onlineOrderProfitDTO);
				//装换一下
				for(OnlineOrderProfitVO m : onLineOrderProfitList){
					m.setProductMoney1(MoneyTransUtil.transDivisDouble(m.getProductMoney()));
					m.setSettlementPrice1(MoneyTransUtil.transDivisDouble(m.getSettlementPrice()));
					m.setFreight1(MoneyTransUtil.transDivisDouble(m.getFreight()));
					m.setChargeFee1(MoneyTransUtil.transDivisDouble(m.getChargeFee()));
					m.setPhChargeFee1(MoneyTransUtil.transDivisDouble(m.getPhChargeFee()));
					m.setYstChargeFee1(MoneyTransUtil.transDivisDouble(m.getYstChargeFee()));
					m.setCityAgentProfit1(MoneyTransUtil.transDivisDouble(m.getCityAgentProfit()));
					m.setCountyAgentProfit1(MoneyTransUtil.transDivisDouble(m.getCountyAgentProfit()));
					m.setTownAgentProfit1(MoneyTransUtil.transDivisDouble(m.getTownAgentProfit()));
					m.setCityPromoterProfit1(MoneyTransUtil.transDivisDouble(m.getCityPromoterProfit()));
					m.setCountyPromoterProfit1(MoneyTransUtil.transDivisDouble(m.getCountyPromoterProfit()));
					m.setTownPromoterProfit1(MoneyTransUtil.transDivisDouble(m.getTownPromoterProfit()));
					m.setMerchantPromoterProfit1(MoneyTransUtil.transDivisDouble(m.getMerchantPromoterProfit()));
					m.setChargeProfitTotal1(MoneyTransUtil.transDivisDouble(m.getChargeProfitTotal()));
					m.setChargeProfitRemain1(MoneyTransUtil.transDivisDouble(m.getChargeProfitRemain()));
					m.setMerchantChainProfit1(MoneyTransUtil.transDivisDouble(m.getMerchantChainProfit()));
					m.setChainProfit1(MoneyTransUtil.transDivisDouble(m.getChainProfit()));
					m.setPhIncome1(MoneyTransUtil.transDivisDouble(m.getPhIncome()));
					m.setYstIncome1(MoneyTransUtil.transDivisDouble(m.getYstIncome()));
					m.setCityAgentChainProfit1(MoneyTransUtil.transDivisDouble(m.getCityAgentChainProfit()));
					m.setCountyAgentChainProfit1(MoneyTransUtil.transDivisDouble(m.getCountyAgentChainProfit()));
					m.setTownAgentChainProfit1(MoneyTransUtil.transDivisDouble(m.getTownAgentChainProfit()));
					m.setChainTotal1(MoneyTransUtil.transDivisDouble(m.getChainTotal()));
					m.setChainRemain1(MoneyTransUtil.transDivisDouble(m.getChainRemain()));
					m.setYstRemain1(MoneyTransUtil.transDivisDouble(m.getYstRemain()));
					m.setPhRemain1(MoneyTransUtil.transDivisDouble(m.getPhRemain()));
				}
				return ResultUtil.getResult(RespCode.Code.SUCCESS, onLineOrderProfitList);
			} catch (Exception e) {
				e.printStackTrace();
				throw new ProfitException(ProfitExceptionEnum.EXPORT_EXCEPTION);
			}
		}
		
		
		/**
		 * 入推广师分润记录表     ph_profit_promoter_record
		* @Title: insertPromoterProfitRecord
		* @Description: TODO(这里用一句话描述这个方法的作用)
		* @author Mr.Dong
		* @date  2017年6月22日 下午6:00:08
		* @param unLineOrderProfitList
		* @throws Exception
		 */
		public void  insertPromoterProfitRecord(List<OnlineOrderProfit> onlineOrderProfitList) throws Exception{
			List<PromoterProfitRecord>   ppr = new ArrayList<PromoterProfitRecord>();
			//获取需要入推广师分润的记录的list
			for(OnlineOrderProfit  m : onlineOrderProfitList){
				if(m.getCityPromoterName() !=null ){
					PromoterProfitRecord p = new  PromoterProfitRecord();
					p.setOrderNo(m.getOrderNo());
					p.setDeliveryDate(m.getOrderTime());
					p.setOrderMoney(m.getProductMoney());
					p.setProProfit(m.getCityPromoterProfit());
					p.setEnterpriseType(RoleEnum.CITY_AGENT.getCode());
					
					p.setEnterpriseName(m.getCityAgentName());
					p.setUserId(m.getCityAgentId());
					
					p.setPromoterId(m.getCityPromoterId());
					ppr.add(p);
				}
				if(m.getCountyPromoterName() !=null ){
					PromoterProfitRecord p = new  PromoterProfitRecord();
					p.setOrderNo(m.getOrderNo());
					p.setDeliveryDate(m.getOrderTime());
					p.setOrderMoney(m.getProductMoney());
					p.setProProfit(m.getCountyPromoterProfit());
					p.setEnterpriseType(RoleEnum.COUNTY_AGENT.getCode());
					
					p.setEnterpriseName(m.getCountyAgentName());
					p.setUserId(m.getCountyAgentId());
					
					p.setPromoterId(m.getCountyPromoterId());
					ppr.add(p);
				}
				
				if(m.getTownPromoterName() !=null ){
					PromoterProfitRecord p = new  PromoterProfitRecord();
					p.setOrderNo(m.getOrderNo());
					p.setDeliveryDate(m.getOrderTime());
					p.setOrderMoney(m.getProductMoney());
					p.setProProfit(m.getTownPromoterProfit());
					p.setEnterpriseType(RoleEnum.COMMUNITY_AGENT.getCode());
					
					p.setEnterpriseName(m.getTownAgentName());
					p.setUserId(m.getTownAgentId());
					
					p.setPromoterId(m.getTownPromoterId());
					ppr.add(p);
				}
				
				if(m.getMerchantPromoterName() !=null ){
					PromoterProfitRecord p = new  PromoterProfitRecord();
					p.setOrderNo(m.getOrderNo());
					p.setDeliveryDate(m.getOrderTime());
					p.setOrderMoney(m.getProductMoney());
					p.setProProfit(m.getMerchantPromoterProfit());
					p.setEnterpriseType(RoleEnum.MERCHANT.getCode());
					
					p.setEnterpriseName(m.getMerchantName());
					p.setUserId(m.getMerchantId());
					
					p.setPromoterId(m.getMerchantPromoterId());
					ppr.add(p);
				}
				
			}
			//批量入推广师分润记录表
			if(ppr.size() > 0){
				int insertPromoterProfitRecord = iUnlineOrderProfitMapper.insertPromoterProfitRecord(ppr);
				if (insertPromoterProfitRecord < 0) {
					logger.error("INSERT PROMOTER RECORD FAIL");
					throw new Exception("新增推广师分润记录失败");
				}
			}	
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
		public void insertUserProfitRecord(List<OnlineOrderProfit> onlineOrderProfitList) throws Exception {
			List<SupplyProfitTotal> spt = new ArrayList<SupplyProfitTotal>();
			for(OnlineOrderProfit m : onlineOrderProfitList){
				if(m.getCityAgentId()!= null){
					SupplyProfitTotal sp = new SupplyProfitTotal();
					sp.setProfited(m.getCityAgentProfit()+m.getCityAgentChainProfit());
					sp.setUserId(m.getCityAgentId());
					sp.setUserName(m.getCityAgentName());
					sp.setUserType(RoleEnum.CITY_AGENT.getCode());
					spt.add(sp);
				}
				if(m.getCountyAgentId() != null){
					SupplyProfitTotal sp = new SupplyProfitTotal();
					sp.setProfited(m.getCountyAgentProfit()+m.getCountyAgentChainProfit());
					sp.setUserId(m.getCountyAgentId());
					sp.setUserName(m.getCountyAgentName());
					sp.setUserType(RoleEnum.COUNTY_AGENT.getCode());
					spt.add(sp);
				}
				if(m.getTownAgentId()  != null){
					SupplyProfitTotal sp = new SupplyProfitTotal();
					sp.setProfited(m.getTownAgentProfit()+m.getTownAgentChainProfit());
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
