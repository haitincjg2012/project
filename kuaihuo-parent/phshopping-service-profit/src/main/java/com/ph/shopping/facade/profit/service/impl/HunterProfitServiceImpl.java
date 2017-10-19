package com.ph.shopping.facade.profit.service.impl;


import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ph.shopping.common.core.customenum.MessageEnum;
import com.ph.shopping.common.core.customenum.RoleEnum;
import com.ph.shopping.common.core.customenum.TransCodeEnum;
import com.ph.shopping.common.core.other.HunterService;
import com.ph.shopping.common.util.core.RespCode;
import com.ph.shopping.common.util.core.ResultUtil;
import com.ph.shopping.common.util.page.PageBean;
import com.ph.shopping.common.util.result.MessageInfo;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.common.util.transform.MoneyTransUtil;
import com.ph.shopping.facade.mapper.IHunterOrderProfitMapper;
import com.ph.shopping.facade.mapper.IPurchaseOrderProfitMapper;
import com.ph.shopping.facade.mapper.IUnlineOrderProfitMapper;
import com.ph.shopping.facade.member.dto.MemberDTO;
import com.ph.shopping.facade.member.entity.Member;
import com.ph.shopping.facade.member.service.IMemberService;
import com.ph.shopping.facade.profit.dto.HunterOrderProfitDTO;
import com.ph.shopping.facade.profit.entity.HunterOrderProfit;
import com.ph.shopping.facade.profit.entity.PromoterProfitRecord;
import com.ph.shopping.facade.profit.entity.SupplyProfitTotal;
import com.ph.shopping.facade.profit.exception.ProfitException;
import com.ph.shopping.facade.profit.exception.ProfitExceptionEnum;
import com.ph.shopping.facade.profit.request.HunterDTO;
import com.ph.shopping.facade.profit.service.IHunterOrderProfitService;
import com.ph.shopping.facade.profit.vo.HunterOrderProfitVO;
import com.ph.shopping.facade.profit.vo.MemberScoreVO;
import com.ph.shopping.facade.profit.vo.UserBalanceRecordVO;
import com.ph.shopping.facade.profit.vo.UserBalanceVO;
import com.ph.shopping.facade.score.service.IScoreService;
import com.ph.shopping.facade.spm.dto.AgentDTO;
import com.ph.shopping.facade.spm.service.IAgentService;
import com.ph.shopping.facade.spm.spmEnum.SPMEnum;
import com.ph.shopping.facade.spm.vo.AgentVO;
import com.ph.shopping.facade.system.service.ISystemParameterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * 
 * @ClassName: ProfitServiceImpl
 * @Description: 分润实现类
 * @author 王强
 * @date 2017年4月26日 下午4:34:40
 */
@Component
@Service(version = "1.0.0")
public class HunterProfitServiceImpl extends HunterService implements IHunterOrderProfitService {

	private Logger logger = LoggerFactory.getLogger(HunterProfitServiceImpl.class);

	 @Autowired
	 IHunterOrderProfitMapper iHunterOrderProfitMapper;
	
	 //积分模块
	 @Reference(version="1.0.0")
	 private IScoreService iScoreService;
	
	 //供应链订单分润Mapper
	 @Autowired
     IPurchaseOrderProfitMapper purchaseOrderProfitMapper;

	 //系统参数接口
	 @Reference(version="1.0.0")
	 private ISystemParameterService iSystemParameterService;
	
	 //代理接口
	 @Reference(version="1.0.0")
	 private IAgentService iAgentService;
	 
	//会员接口
	@Reference(version = "1.0.0")
	private IMemberService iMemberService;
	
	//线下订单分润Mapper
	@Autowired
	IUnlineOrderProfitMapper iUnlineOrderProfitMapper;
	 
	/**
	 * 
	 * Title: getAreaList Description:根据乡镇id获取省市县列表
	 * 
	 * @author 王强
	 * @date 2017年4月6日 上午10:17:45
	 * @param townId
	 * @return
	 * @see com.ph.shopping.facade.profit.service.IHunterOrderProfitService#getAreaList(long)
	 */
	@Override
	public MessageInfo getAreaList(long townId) {
		return getMessageInfo(MessageEnum.SUCCESS, iHunterOrderProfitMapper.getAreaByTownId(townId));
	}

	/**
	 * 
	 * Title: getTownList Description:根据县id获取乡镇列表
	 * 
	 * @author 王强
	 * @date 2017年4月6日 上午10:32:17
	 * @param countyId
	 * @return
	 * @see com.ph.shopping.facade.profit.service.IHunterOrderProfitService#getTownList(long)
	 */
	@Override
	public MessageInfo getTownList(long countyId) {
		return getMessageInfo(MessageEnum.SUCCESS, iHunterOrderProfitMapper.getTowns(countyId));
	}

	/**
	 * 
	 * Title: getProvinces Description:获取省
	 * 
	 * @author 王强
	 * @date 2017年4月26日 下午12:02:42
	 * @return
	 * @see com.ph.shopping.facade.profit.service.IHunterOrderProfitService#getProvinces()
	 */
	@Override
	public MessageInfo getProvinces() {

		return getMessageInfo(MessageEnum.SUCCESS, iHunterOrderProfitMapper.getPosition());
	}

	/**
	 * 
	 * Title: getCitys Description:获取市
	 * 
	 * @author 王强
	 * @date 2017年4月26日 下午12:02:54
	 * @param provinceId
	 * @return
	 * @throws Exception
	 * @see com.ph.shopping.facade.profit.service.IHunterOrderProfitService#getCitys(long)
	 */
	@Override
	public MessageInfo getCitys(long provinceId) throws Exception {

		return getMessageInfo(MessageEnum.SUCCESS, iHunterOrderProfitMapper.getCityByPid(provinceId));
	}

	/**
	 * 
	 * Title: getCountys Description:获取区县
	 * 
	 * @author 王强
	 * @date 2017年4月26日 下午12:03:06
	 * @param cityId
	 * @return
	 * @throws Exception
	 * @see com.ph.shopping.facade.profit.service.IHunterOrderProfitService#getCountys(long)
	 */
	@Override
	public MessageInfo getCountys(long cityId) throws Exception {

		return getMessageInfo(MessageEnum.SUCCESS, iHunterOrderProfitMapper.getCountyByCid(cityId));
	}

	/**
	 * 
	 * Title: getTowns Description:获取乡镇
	 * 
	 * @author 王强
	 * @date 2017年4月26日 下午12:03:18
	 * @param countyId
	 * @return
	 * @throws Exception
	 * @see com.ph.shopping.facade.profit.service.IHunterOrderProfitService#getTowns(long)
	 */
	@Override
	public MessageInfo getTowns(long countyId) throws Exception {

		return getMessageInfo(MessageEnum.SUCCESS, iHunterOrderProfitMapper.getTownByCid(countyId));
	}

	/**
	 * 只返回信息结果
	 * 
	 * @param supplierEnum
	 * @return
	 */
	protected Result getResult(ProfitExceptionEnum profitExceptionEnum) {
		Result result = new Result();
		result.setMessage(profitExceptionEnum.getMsg());
		result.setCode(String.valueOf(profitExceptionEnum.getCode()));
		return result;
	}	
	
	
	/*---------------------------上面不动----------------------------------------------------------------------------------*/
	
	/**
	 * 
	* Title: hunterOrderProfitList
	* Description: 批发商订单分润list
	* @author Mr.Dong
	* @date 2017年6月1日 下午5:45:37
	* @param hunterOrderProfitDTO
	* @return
	* @see com.ph.shopping.facade.profit.service.IHunterOrderProfitService#hunterOrderProfitList(com.ph.shopping.facade.profit.dto.HunterOrderProfitDTO)
	 */
	@Override
	public Result hunterOrderProfitList(HunterOrderProfitDTO hunterOrderProfitDTO,PageBean pagebean) {
		PageHelper.startPage(pagebean.getPageNum(), pagebean.getPageSize());
		List<HunterOrderProfitVO> hunterOrderProfitList = iHunterOrderProfitMapper.hunterOrderProfitList(hunterOrderProfitDTO);
		PageInfo<HunterOrderProfitVO>pageInfo=new PageInfo<HunterOrderProfitVO>(hunterOrderProfitList);
		//装换一下
		for(HunterOrderProfitVO m : pageInfo.getList()){
			m.setCityAgentProfit1(MoneyTransUtil.transDivisDouble(m.getCityAgentProfit()));
			m.setCountyAgentProfit1(MoneyTransUtil.transDivisDouble(m.getCountyAgentProfit()));
			m.setCityPromoterProfit1(MoneyTransUtil.transDivisDouble(m.getCityPromoterProfit()));
			m.setCountyPromoterProfit1(MoneyTransUtil.transDivisDouble(m.getCountyPromoterProfit()));
			m.setChargeProfitTotal1(MoneyTransUtil.transDivisDouble(m.getChargeProfitTotal()));
			m.setChargeProfitRemain1(MoneyTransUtil.transDivisDouble(m.getChargeProfitRemain()));
			m.setChainProfit1(MoneyTransUtil.transDivisDouble(m.getChainProfit()));
			m.setPhChainProfit1(MoneyTransUtil.transDivisDouble(m.getPhChainProfit()));
			m.setYstChainProfit1(MoneyTransUtil.transDivisDouble(m.getYstChainProfit()));
			m.setCityAgentChainProfit1(MoneyTransUtil.transDivisDouble(m.getCityAgentChainProfit()));
			m.setCountyAgentChainProfit1(MoneyTransUtil.transDivisDouble(m.getCountyAgentChainProfit()));
			m.setChainProfitTotal1(MoneyTransUtil.transDivisDouble(m.getChainProfitTotal()));
			m.setChainProfitRemain1(MoneyTransUtil.transDivisDouble(m.getChainProfitRemain()));
			m.setOrderMoney1(MoneyTransUtil.transDivisDouble(m.getOrderMoney()));
			m.setHunterProfit1(MoneyTransUtil.transDivisDouble(m.getHunterProfit()));
			m.setPhProfit1(MoneyTransUtil.transDivisDouble(m.getPhProfit()));
			m.setChargeProfit1(MoneyTransUtil.transDivisDouble(m.getChargeProfit()));
			m.setPhChargeProfit1(MoneyTransUtil.transDivisDouble(m.getPhChargeProfit()));
			m.setYstChargeProfit1(MoneyTransUtil.transDivisDouble(m.getYstChargeProfit()));
			m.setTownAgentProfit1(MoneyTransUtil.transDivisDouble(m.getTownAgentProfit()));
			m.setTownPromoterProfit1(MoneyTransUtil.transDivisDouble(m.getTownPromoterProfit()));
			m.setTownAgentChainProfit1(MoneyTransUtil.transDivisDouble(m.getTownAgentChainProfit()));
			m.setCompensationMoney1(MoneyTransUtil.transDivisDouble(m.getCompensationMoney()));
			m.setGuildProfit1(MoneyTransUtil.transDivisDouble(m.getGuildProfit()));
			m.setYytProfit1(MoneyTransUtil.transDivisDouble(m.getYytProfit()));
		}
		return  ResultUtil.getResult(RespCode.Code.SUCCESS,pageInfo.getList(),pageInfo.getTotal());
	}

	/**
	 * 
	* Title: getHunterOrderOrderProfitEXCEL
	* Description: 导出批发商订单分润记录EXCEL
	* @author Mr.Dong
	* @date 2017年6月2日 上午10:29:32
	* @param hunterOrderProfitDTO
	* @return
	* @see com.ph.shopping.facade.profit.service.IHunterOrderProfitService#getOnLineOrderProfitEXCEL(com.ph.shopping.facade.profit.dto.HunterOrderProfitDTO)
	 */
	@Override
	public Result getHunterOrderOrderProfitEXCEL(HunterOrderProfitDTO hunterOrderProfitDTO) {
		// TODO Auto-generated method stub
		List<HunterOrderProfitVO> hunterOrderProfitList = iHunterOrderProfitMapper.hunterOrderProfitList(hunterOrderProfitDTO);
		//装换一下
		for(HunterOrderProfitVO m : hunterOrderProfitList){
			m.setCityAgentProfit1(MoneyTransUtil.transDivisDouble(m.getCityAgentProfit()));
			m.setCountyAgentProfit1(MoneyTransUtil.transDivisDouble(m.getCountyAgentProfit()));
			m.setCityPromoterProfit1(MoneyTransUtil.transDivisDouble(m.getCityPromoterProfit()));
			m.setCountyPromoterProfit1(MoneyTransUtil.transDivisDouble(m.getCountyPromoterProfit()));
			m.setChargeProfitTotal1(MoneyTransUtil.transDivisDouble(m.getChargeProfitTotal()));
			m.setChargeProfitRemain1(MoneyTransUtil.transDivisDouble(m.getChargeProfitRemain()));
			m.setChainProfit1(MoneyTransUtil.transDivisDouble(m.getChainProfit()));
			m.setPhChainProfit1(MoneyTransUtil.transDivisDouble(m.getPhChainProfit()));
			m.setYstChainProfit1(MoneyTransUtil.transDivisDouble(m.getYstChainProfit()));
			m.setCityAgentChainProfit1(MoneyTransUtil.transDivisDouble(m.getCityAgentChainProfit()));
			m.setCountyAgentChainProfit1(MoneyTransUtil.transDivisDouble(m.getCountyAgentChainProfit()));
			m.setChainProfitTotal1(MoneyTransUtil.transDivisDouble(m.getChainProfitTotal()));
			m.setChainProfitRemain1(MoneyTransUtil.transDivisDouble(m.getChainProfitRemain()));
			m.setOrderMoney1(MoneyTransUtil.transDivisDouble(m.getOrderMoney()));
			m.setHunterProfit1(MoneyTransUtil.transDivisDouble(m.getHunterProfit()));
			m.setPhProfit1(MoneyTransUtil.transDivisDouble(m.getPhProfit()));
			m.setChargeProfit1(MoneyTransUtil.transDivisDouble(m.getChargeProfit()));
			m.setPhChargeProfit1(MoneyTransUtil.transDivisDouble(m.getPhChargeProfit()));
			m.setYstChargeProfit1(MoneyTransUtil.transDivisDouble(m.getYstChargeProfit()));
			m.setTownAgentProfit1(MoneyTransUtil.transDivisDouble(m.getTownAgentProfit()));
			m.setTownPromoterProfit1(MoneyTransUtil.transDivisDouble(m.getTownPromoterProfit()));
			m.setTownAgentChainProfit1(MoneyTransUtil.transDivisDouble(m.getTownAgentChainProfit()));
			m.setCompensationMoney1(MoneyTransUtil.transDivisDouble(m.getCompensationMoney()));
			m.setGuildProfit1(MoneyTransUtil.transDivisDouble(m.getGuildProfit()));
			m.setYytProfit1(MoneyTransUtil.transDivisDouble(m.getYytProfit()));
		}
		return  ResultUtil.getResult(RespCode.Code.SUCCESS,hunterOrderProfitList);
	}



	/**
	 * 
	* Title: addHunterProfit
	* Description: 批发商分润主方法
	* @author Mr.Dong
	* @date 2017年6月2日 下午2:24:26
	* @param request
	* @return
	* @throws Exception
	* @see com.ph.shopping.facade.profit.service.IHunterOrderProfitService#addHunterProfit(com.ph.shopping.facade.profit.request.HunterDTO)
	 */
	@Override
	@Transactional
	public MessageInfo addHunterProfit(HunterDTO hunterDTO) {
		try{
			logger.info("批发商分润Service调传入参数:"+ JSON.toJSONString(hunterDTO));
			//根据批发商来源不同不同的分润模式
			if("0".equals(hunterDTO.getFromSys())){//批发商平台
				HunterOrderProfit  m = new HunterOrderProfit();
				m.setFromSys(hunterDTO.getFromSys());
				m.setOrderNo(hunterDTO.getOrderNo());
				m.setProvinceId(hunterDTO.getProvinceId());
				m.setCityId(hunterDTO.getCityId());
				m.setCountyId(hunterDTO.getCountyId());
				m.setTownId(hunterDTO.getTownId());
				m.setHunterTel(hunterDTO.getHunterTel());
				m.setMemberTel(hunterDTO.getMemberTel());
				m.setProfitTime(hunterDTO.getProfitTime());
				
				Long a = hunterDTO.getOrderMoney();
				m.setOrderMoney(a); //北京传过来是以分为单位         订单金额     2017-7-24 发现北京传过来的不是以分单位 比如订单金额是100 传过来的是1000000
				
				//6个值参数写死 不从数据库读取 start
				Double m0 = a.doubleValue() * 0.8;
				m.setHunterProfit(m0.longValue());//批发商分润
				Double m3 = a.doubleValue() * 0.2;
				m.setPhProfit(m3.longValue());//普惠分润
				
				
				Double m1 = a.doubleValue() * 0.01;
				m.setCompensationMoney(m1.longValue());//批发商赔付金
				m.setGuildProfit(m1.longValue());//工会
				Double m2 = a.doubleValue() * 0.05;
				m.setYytProfit(m2.longValue());//易耀通
				Double d = a.doubleValue() * 0.13;
				m.setChargeProfit(d.longValue());//管理费
				//6个值参数写死 不从数据库读取 end
				//插入批发商分润记录
				int insertHunterOrderProfit = iHunterOrderProfitMapper.insertHunterOrderProfit(m);
				if(insertHunterOrderProfit != 1){
					throw new Exception("插入批发商分润记录异常");
				}
				//返批发商待用积分
				logger.info("批发商订单返待用积分开始");
				Result memberInfoByMobile = iMemberService.getMemberInfoByMobile(hunterDTO.getMemberTel());
				logger.info("通过会员手机号获取会员信息返回数据："+JSON.toJSONString(memberInfoByMobile));
				if(memberInfoByMobile.getCode().equals("200") && memberInfoByMobile.getData() !=null){
					Member member = (Member)memberInfoByMobile.getData();
					logger.info("开始调用积分模块返回待用积分");
					iScoreService.memberScoreTrade(member.getId(),TransCodeEnum.HUNTER_ORDER_RETURN_SCORE,
							m.getOrderMoney(),m.getOrderNo(),0);
				}
				logger.info("批发商订单返待用积分结束");
			}else if("1".equals(hunterDTO.getFromSys())){
				//获取系统参数
				Map<String, Double> systemParameters = getSystemParameters();
				//得到批发商分润记录
				HunterOrderProfit hunterOrderProfit = getHunterOrderProfit(systemParameters,hunterDTO);
				if(hunterOrderProfit == null){
					logger.info("通过区域获取代理无数据");
					return getMessageInfo(MessageEnum.NO_AGENT_BY_POSITION);
				}
				//插入批发商分润记录
				int insertHunterOrderProfit = iHunterOrderProfitMapper.insertHunterOrderProfit(hunterOrderProfit);
				if(insertHunterOrderProfit != 1){
					throw new Exception("插入批发商分润记录异常");
				}
				//更新代理的余额
				logger.info("批发商分润开始代理余额更新,传入参数："+JSON.toJSONString(hunterOrderProfit));
				startAgentProfit(hunterOrderProfit);
				logger.info("批发商分润开始代理余额更新结束");
				//更新会员的积分
				logger.info("批发商分润开始更新会员积分,传入参数："+JSON.toJSONString(hunterOrderProfit));
				startMemberProfit(hunterOrderProfit);
				logger.info("批发商分润开始更新会员积分结束");
				//入推广师分润记录表
				logger.info("批发商分润开始入推广师分润记录表,传入参数："+JSON.toJSONString(hunterOrderProfit));
				insertPromoterProfitRecord(hunterOrderProfit);
				logger.info("批发商分润入推广师分润记录表结束");
				//入用户分润记录表
				logger.info("批发商分润开始入用户分润记录表,传入参数："+JSON.toJSONString(hunterOrderProfit));
				insertUserProfitRecord(hunterOrderProfit);
				logger.info("批发商分润入用户分润记录表结束");
				//返回批发商的待用积分
				logger.info("批发商订单返待用积分开始");
				Result memberInfoByMobile = iMemberService.getMemberInfoByMobile(hunterDTO.getMemberTel());
				logger.info("通过会员手机号获取会员信息返回数据："+JSON.toJSONString(memberInfoByMobile));
				if(memberInfoByMobile.getCode().equals("200") && memberInfoByMobile.getData() !=null){
					Member member = (Member)memberInfoByMobile.getData();
					logger.info("开始调用积分模块返回待用积分");
					iScoreService.memberScoreTrade(member.getId(),TransCodeEnum.HUNTER_ORDER_RETURN_SCORE,
							hunterOrderProfit.getOrderMoney(),hunterOrderProfit.getOrderNo(),0);
				}
				logger.info("批发商订单返待用积分结束");
			}else{
				return getMessageInfo(MessageEnum.NO_CORRECT_FROMSYS);
			}
		}catch (Exception e) {
			e.printStackTrace();
			throw new ProfitException(ProfitExceptionEnum.HUNTER_ORDERP_ROFIT_EXCEPTION);
		}
		return getMessageInfo(MessageEnum.SUCCESS);
	}
	
	/**
	* @Title: getHunterOrderProfitList
	* @Description: TODO(获取批发商订单记录)
	* @author Mr.Dong
	* @date  2017年6月2日 下午3:11:12
	* @param systemParameters
	* @param hunterDTO
	* @param positionIdByTownId 区域主键
	* @return
	 */
	public HunterOrderProfit  getHunterOrderProfit(Map<String, Double> systemParameters,HunterDTO hunterDTO){
		HunterOrderProfit  m = new HunterOrderProfit();
		//通过区域获取各级代理
		AgentDTO agentDTO = new AgentDTO();
		agentDTO.setProvinceId(hunterDTO.getProvinceId());
		agentDTO.setCityId(hunterDTO.getCityId());
		agentDTO.setCountyId(hunterDTO.getCountyId());
		agentDTO.setTownId(hunterDTO.getTownId());
		
		agentDTO.setStatus(SPMEnum.agentStatus.ONE.getIndex());//审核通过
		agentDTO.setIsDelete(SPMEnum.agentIsDelete.ZERO.getIndex());//没有被删除
		agentDTO.setIsFrozen(SPMEnum.agentIsFrozen.ONE.getIndex());//冻结的 传过去代理直接非冻结
		List<AgentVO> agentPositionList = iAgentService.getAgentPositionList(agentDTO);
		logger.info("通过区域查询各级代理和代理的推广师返回参数："+JSON.toJSONString(agentPositionList));
		if(agentPositionList.size() == 0){
			logger.info("通过区域获取数据为null");
			return null;
		}
		if(agentPositionList.size() > 0){
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
		Long a = hunterDTO.getOrderMoney();
		m.setOrderMoney(a); //北京传过来是以分为单位         订单金额        2017-7-24 发现北京传过来的不是以分单位 比如订单金额是100 传过来的是1000000
		
		//8个值参数写死 不从数据库读取 start
		Double m0 = a.doubleValue() * 0.8;
		m.setHunterProfit(m0.longValue());//批发商分润
		Double m3 = a.doubleValue() * 0.2;
		m.setPhProfit(m3.longValue());//普惠分润
		
	
		Double m1 = a.doubleValue() * 0.01;
		m.setCompensationMoney(m1.longValue());//批发商赔付金
		m.setGuildProfit(m1.longValue());//工会
		Double m2 = a.doubleValue() * 0.05;
		m.setYytProfit(m2.longValue());//易耀通
		Double d = a.doubleValue() * 0.13;
		m.setChargeProfit(d.longValue());//管理费
		Double e = a.doubleValue() * 0.1;
		m.setPhChargeProfit(e.longValue());//普惠管理费
		Double f = a.doubleValue() * 0.03;
		m.setYstChargeProfit(f.longValue());//易商通管理费
		//8个值参数写死 不从数据库读取 end
		Double r = a * systemParameters.get("r");
		m.setChainProfit(r.longValue());//供应链利润
		Double s = a * systemParameters.get("s");
		m.setPhChainProfit(s.longValue());//普惠供应链利润
		Double t = a * systemParameters.get("t");
		m.setYstChainProfit(t.longValue());//易商通供应链利润
		
		Double pp = 0d;
		Double yy = 0d;
		if(m.getCityAgentId() != null){
			Double g = a * systemParameters.get("g") * systemParameters.get("z");
			m.setCityAgentProfit(g.longValue());//市级代理分润
			pp = pp + g;
		}
		
		if(m.getCountyAgentId() != null){
			Double h = a * systemParameters.get("h") * systemParameters.get("z");
			m.setCountyAgentProfit(h.longValue());//县级代理分润
			pp = pp + h;
		}
		if(m.getTownAgentId() != null){//社区代理
			Double i = a * systemParameters.get("i") * systemParameters.get("z") ;
			m.setTownAgentProfit(i.longValue());//社区代理分润
			pp = pp + i;
		}
		
		if(m.getCityPromoterId() != null){
			Double j = a * systemParameters.get("j") ;
			m.setCityPromoterProfit(j.longValue());//市级代理推广师傅分润
			pp = pp + j;
		}
		if(m.getCountyPromoterId() != null){
			Double k =  a * systemParameters.get("k") ;
			m.setCountyPromoterProfit(k.longValue());//县级代理推广师傅分润
			pp = pp + k;
		}
		if(m.getTownPromoterId() != null){//社区级代理推广师傅
			Double l = a * systemParameters.get("l");
			m.setTownPromoterProfit(l.longValue());
			pp = pp + l;
		}
		
		m.setChargeProfitTotal(pp.longValue());//管理费支出合计
		m.setChainProfitTotal(yy.longValue());//供应链支出合计
		Double nn = f - pp;
		m.setChargeProfitRemain(nn.longValue());//管理费剩余
		Double qq = t - yy;
		m.setChainProfitRemain(qq.longValue());//供应链剩余
		
		m.setOrderNo(hunterDTO.getOrderNo());
		m.setProvinceId(hunterDTO.getProvinceId());
		m.setCityId(hunterDTO.getCityId());
		m.setCountyId(hunterDTO.getCountyId());
		m.setTownId(hunterDTO.getTownId());
		m.setHunterTel(hunterDTO.getHunterTel());
		m.setMemberTel(hunterDTO.getMemberTel());
		m.setProfitTime(hunterDTO.getProfitTime());
		m.setFromSys(hunterDTO.getFromSys());
		return m;
	}
	
	/**
	 * 
	* @Title: getSystemParameters
	* @Description: TODO(获取系统参数)
	* @author Mr.Dong
	* @date  2017年6月2日 下午2:42:52
	* @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String,Double> getSystemParameters(){
		//获取系统参数
		List<Long> ids = new ArrayList<Long>();
		/*管理费相关*/
		ids.add(4l);//管理费分润比率
		ids.add(5l);//普惠管理费分润比率
		ids.add(6l);//易商通管理费分润比率
		ids.add(7l);//市级代理管理费分润比率
		ids.add(8l);//县级代理管理费分润比率
		ids.add(9l);//社区代理管理费分润比率
		
		ids.add(10l);//市级代理推广师分润比率
		ids.add(11l);//县级代理推广师分润比率
		ids.add(12l);//社区代理推广师分润比率
		
		ids.add(14l);//供链股权分配比例  69%
		/*供应链相关*/
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
		Double FSign = (Double)(map.get(new Long (6)));//易商通管理费分润比率
		Double GSign = (Double)(map.get(new Long (7)));//市级代理管理费分润比率
		Double HSign = (Double)(map.get(new Long (8)));//县级代理管理费分润比率
		Double ISign = (Double)(map.get(new Long (9)));//社区代理管理费分润比率
		
		Double JSign = (Double)(map.get(new Long (10)));//市级代理推广师分润比率
		Double KSign = (Double)(map.get(new Long (11)));//县级代理推广师分润比率
		Double LSign = (Double)(map.get(new Long (12)));//社区代理推广师分润比率
		
		Double ZSign = (Double)(map.get(new Long (14)));//供链股权分配比例  69%-----------------特殊
		
		/*供应链*/
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
		mapNew.put("f", FSign);
		mapNew.put("g", GSign);
		mapNew.put("h", HSign);
		mapNew.put("i", ISign);
		mapNew.put("j", JSign);
		mapNew.put("k", KSign);
		mapNew.put("l", LSign);
		mapNew.put("z", ZSign); //供链股权分配比例  69%
		/*供应链*/
		mapNew.put("r", RSign);
		mapNew.put("s", SSign);
		mapNew.put("t", TSign);
		mapNew.put("u", USign);
		mapNew.put("w", WSign);
		mapNew.put("x", XSign);
		return mapNew;
	}
	
	/**
	* @Title: startAgentProfit
	* @Description: TODO(代理加余额)
	* @author Mr.Dong
	* @date  2017年6月2日 下午5:08:03
	* @param m
	* @throws Exception
	 */
	public void startAgentProfit(HunterOrderProfit m) throws Exception{
		List<UserBalanceVO> userBalanceVoList = new ArrayList<UserBalanceVO>();
		List<UserBalanceRecordVO> userBalanceRecordVoList = new ArrayList<UserBalanceRecordVO>();
		
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
			userBalanceRecordVO.setTransCode(String.valueOf(TransCodeEnum.HUNTER_ORDER_PROFIT_MANAGE_TRANSCODE.getCode()));
			userBalanceRecordVO.setUserType(String.valueOf(RoleEnum.CITY_AGENT.getCode()));
			userBalanceRecordVoList.add(userBalanceRecordVO);
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
			userBalanceRecordVO.setTransCode(String.valueOf(TransCodeEnum.HUNTER_ORDER_PROFIT_MANAGE_TRANSCODE.getCode()));
			userBalanceRecordVO.setUserType(String.valueOf(RoleEnum.COUNTY_AGENT.getCode()));
			userBalanceRecordVoList.add(userBalanceRecordVO);
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
			userBalanceRecordVO.setTransCode(String.valueOf(TransCodeEnum.HUNTER_ORDER_PROFIT_MANAGE_TRANSCODE.getCode()));
			userBalanceRecordVO.setUserType(String.valueOf(RoleEnum.COMMUNITY_AGENT.getCode()));
			userBalanceRecordVoList.add(userBalanceRecordVO);
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
	public void  startMemberProfit(HunterOrderProfit m) throws Exception {
		List<MemberScoreVO> memberScoreVOList = new ArrayList<MemberScoreVO>();
			
		if(m.getCityPromoterId() !=null){//市推
			MemberScoreVO memberScoreVO = new  MemberScoreVO();
			memberScoreVO.setMemberId(m.getCityPromoterId());
			memberScoreVO.setOrderNo(m.getOrderNo());
			memberScoreVO.setScore(m.getCityPromoterProfit());
			memberScoreVO.setTransCode(String.valueOf(TransCodeEnum.HUNTER_ORDER_PROFIT_ADD_SCORE_TRANSCODE.getCode()));
			memberScoreVOList.add(memberScoreVO);
		}
		if(m.getCountyPromoterId() != null){//县推
			MemberScoreVO memberScoreVO = new  MemberScoreVO();
			memberScoreVO.setMemberId(m.getCountyPromoterId());
			memberScoreVO.setOrderNo(m.getOrderNo());
			memberScoreVO.setScore(m.getCountyPromoterProfit());
			memberScoreVO.setTransCode(String.valueOf(TransCodeEnum.HUNTER_ORDER_PROFIT_ADD_SCORE_TRANSCODE.getCode()));
			memberScoreVOList.add(memberScoreVO);
		}
		if(m.getTownPromoterId() != null){//社区推
			MemberScoreVO memberScoreVO = new  MemberScoreVO();
			memberScoreVO.setMemberId(m.getTownPromoterId());
			memberScoreVO.setOrderNo(m.getOrderNo());
			memberScoreVO.setScore(m.getTownPromoterProfit());
			memberScoreVO.setTransCode(String.valueOf(TransCodeEnum.HUNTER_ORDER_PROFIT_ADD_SCORE_TRANSCODE.getCode()));
			memberScoreVOList.add(memberScoreVO);
		}
		if(memberScoreVOList.size() > 0){
			//1  更新推广师可用积分(单条)  2 插入积分流水总表 ph_score_total_trade      3 插入积分流水进账表 ph_score_income_trade   
			for(MemberScoreVO n : memberScoreVOList){
				int updateMemberScore = iUnlineOrderProfitMapper.updateMemberScore(n);
				if(updateMemberScore < 0){
					throw new Exception("更新会员可用积分异常");
				}
				int insertMemberScoreTotal = iUnlineOrderProfitMapper.insertMemberScoreTotal(n);
				if(insertMemberScoreTotal < 0){
					throw new Exception("插入会员积分总表异常");
				}
				int insertMemberScoreIncome = iUnlineOrderProfitMapper.insertMemberScoreIncome(n);
				if(insertMemberScoreIncome < 0){
					throw new Exception("插入会员积分入账流水表异常");
				}
			}
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
	public void  insertPromoterProfitRecord(HunterOrderProfit m) throws Exception{
		List<PromoterProfitRecord>   ppr = new ArrayList<PromoterProfitRecord>();
		//获取需要入推广师分润的记录的list
		if(m.getCityPromoterName() !=null ){
			PromoterProfitRecord p = new  PromoterProfitRecord();
			p.setOrderNo(m.getOrderNo());
			p.setDeliveryDate(new Date());
			p.setOrderMoney(m.getOrderMoney());
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
			p.setDeliveryDate(new Date());
			p.setOrderMoney(m.getOrderMoney());
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
			p.setDeliveryDate(new Date());
			p.setOrderMoney(m.getOrderMoney());
			p.setProProfit(m.getTownPromoterProfit());
			p.setEnterpriseType(RoleEnum.COMMUNITY_AGENT.getCode());
			
			p.setEnterpriseName(m.getTownAgentName());
			p.setUserId(m.getTownAgentId());
			
			p.setPromoterId(m.getTownPromoterId());
			ppr.add(p);
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
	public void insertUserProfitRecord(HunterOrderProfit m) throws Exception {
		List<SupplyProfitTotal> spt = new ArrayList<SupplyProfitTotal>();
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
		//批量入用户分润记录表
		if(spt.size() > 0){
			iUnlineOrderProfitMapper.insertUserProfitRecord(spt);
		}
	}

	/**
	 * 快火批发订单
	 * @param hunterOrderProfitDTO
	 * @param pagebean
	 * @return
	 */
	@Override
	public Result hunterOrderProfitPageList(HunterOrderProfitDTO hunterOrderProfitDTO, PageBean pagebean) {
		//通过list返回
		PageHelper.startPage(pagebean.getPageNum(), pagebean.getPageSize());
		List<Map> maps = iHunterOrderProfitMapper.hunterOrderProfitPageList(hunterOrderProfitDTO);
		//List<HunterOrderProfitVO> hunterOrderProfitList = iHunterOrderProfitMapper.hunterOrderProfitList(hunterOrderProfitDTO);
		PageInfo<Map> pageInfo=new PageInfo<Map>(maps);
		//装换一下

		if (maps!=null && maps.size()>0) {
			for (int i = 0; maps.size() > i; i++) {
               maps.get(i);
				//BigDecimal b = new BigDecimal(resultMap.get("amount"));
				//b=b.setScale(2, BigDecimal.ROUND_HALF_UP); //四舍五入

			   maps.get(i).put("totalPrice",new BigDecimal(maps.get(i).get("totalPrice").toString()).setScale(2, BigDecimal.ROUND_HALF_UP));
				maps.get(i).put("serviceFee",new BigDecimal(maps.get(i).get("serviceFee").toString()).setScale(2, BigDecimal.ROUND_HALF_UP));
				maps.get(i).put("puhuiFee",new BigDecimal(maps.get(i).get("puhuiFee").toString()).setScale(2, BigDecimal.ROUND_HALF_UP));
				maps.get(i).put("manageFee",new BigDecimal(maps.get(i).get("manageFee").toString()).setScale(2, BigDecimal.ROUND_HALF_UP));
				maps.get(i).put("kuhuoFee",new BigDecimal(maps.get(i).get("kuhuoFee").toString()).setScale(2, BigDecimal.ROUND_HALF_UP));
				maps.get(i).put("agentFee",new BigDecimal(maps.get(i).get("agentFee").toString()).setScale(2, BigDecimal.ROUND_HALF_UP));
				maps.get(i).put("promoterFee",new BigDecimal(maps.get(i).get("promoterFee").toString()).setScale(2, BigDecimal.ROUND_HALF_UP));
				/*maps.get(i).put("serviceFee",MoneyTransUtil.transDivisDouble( (Long)maps.get(i).get("serviceFee")));
				maps.get(i).put("puhuiFee",MoneyTransUtil.transDivisDouble( (Long)maps.get(i).get("puhuiFee")));
				maps.get(i).put("manageFee",MoneyTransUtil.transDivisDouble( (Long)maps.get(i).get("manageFee")));
				maps.get(i).put("kuhuoFee",MoneyTransUtil.transDivisDouble( (Long)maps.get(i).get("kuhuoFee")));
				maps.get(i).put("agentFee",MoneyTransUtil.transDivisDouble( (Long)maps.get(i).get("agentFee")));
				maps.get(i).put("promoterFee",MoneyTransUtil.transDivisDouble( (Long)maps.get(i).get("promoterFee")));*/

			}
		}
		return new Result(true, "获取成功", "", pageInfo.getList(), pageInfo.getTotal());
		//return  ResultUtil.getResult(RespCode.Code.SUCCESS,maps);

	}

}
