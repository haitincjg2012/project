package com.ph.shopping.facade.profit.service.impl;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ph.shopping.common.core.customenum.RoleEnum;
import com.ph.shopping.common.core.customenum.TransCodeEnum;
import com.ph.shopping.common.core.dto.PuhuiScoreDTO;
import com.ph.shopping.common.core.util.PuhuiUtil;
import com.ph.shopping.common.util.core.RespCode;
import com.ph.shopping.common.util.core.ResultUtil;
import com.ph.shopping.common.util.date.DoubleUtils;
import com.ph.shopping.common.util.page.PageBean;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.common.util.transform.MoneyTransUtil;
import com.ph.shopping.facade.mapper.IPurchaseOrderProfitMapper;
import com.ph.shopping.facade.mapper.IUnlineOrderProfitMapper;
import com.ph.shopping.facade.member.dto.MemberDTO;
import com.ph.shopping.facade.member.entity.Member;
import com.ph.shopping.facade.member.menum.member.MemberIsMarketingEnum;
import com.ph.shopping.facade.member.menum.member.MemberIsRewardRecordEnum;
import com.ph.shopping.facade.member.service.IMemberService;
import com.ph.shopping.facade.profit.constant.OrderIsProfitEnum;
import com.ph.shopping.facade.profit.dto.UnLineOrderProfitDTO;
import com.ph.shopping.facade.profit.entity.PromoterProfitRecord;
import com.ph.shopping.facade.profit.entity.SupplyProfitTotal;
import com.ph.shopping.facade.profit.entity.UnlineOrderProfit;
import com.ph.shopping.facade.profit.exception.ProfitException;
import com.ph.shopping.facade.profit.exception.ProfitExceptionEnum;
import com.ph.shopping.facade.profit.service.IUnlineOrderProfitService;
import com.ph.shopping.facade.profit.vo.*;
import com.ph.shopping.facade.spm.dto.AgentDTO;
import com.ph.shopping.facade.spm.dto.MerchantDTO;
import com.ph.shopping.facade.spm.entity.Agent;
import com.ph.shopping.facade.spm.service.IAgentService;
import com.ph.shopping.facade.spm.service.IMerchantService;
import com.ph.shopping.facade.spm.spmEnum.SPMEnum;
import com.ph.shopping.facade.spm.vo.AgentVO;
import com.ph.shopping.facade.spm.vo.MerchantVO;
import com.ph.shopping.facade.system.service.ISystemParameterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 
 * @ClassName: UnlineOrderProfitServiceImpl
 * @Description: 线下订单分润实现类
 * @author Mr.Dong
 * @date 2017年5月22日 下午4:34:40
 */
@Component
@Service(version = "1.0.0")
@SuppressWarnings("unused")
public class UnlineOrderProfitServiceImpl implements IUnlineOrderProfitService{
	private Logger logger = LoggerFactory.getLogger(UnlineOrderProfitServiceImpl.class);
	
	//线下订单分润Mapper
	@Autowired
	IUnlineOrderProfitMapper iUnlineOrderProfitMapper;
	
	//供应链订单分润Mapper
	@Autowired
	IPurchaseOrderProfitMapper purchaseOrderProfitMapper;
	
	//会员接口
	@Reference(version = "1.0.0")
	private IMemberService iMemberService;
	
	//代理接口
	 @Reference(version = "1.0.0")
	 private IAgentService iAgentService;
	 
	 //系统参数接口
	 @Reference(version="1.0.0")
	 private ISystemParameterService iSystemParameterService;
	 
	//商户接口
	@Reference(version = "1.0.0")
	private IMerchantService iMerchantService;

	@Autowired
	private PuhuiUtil puhuiUtil;

	/**
	 * 
	 * @Title: unLineOrderProfitList
	 * @Description: 线下订单分润list
	 * @author 
	 * @date 2017年5月22日 下午4:16:44
	 * @param unLineOrderProfitDTO
	 * @param pageBean
	 * @return
	 */
	public Result unLineOrderProfitList(UnLineOrderProfitDTO unLineOrderProfitDTO,PageBean pageBean){
		try {
			PageHelper.startPage(pageBean.getPageNum(), pageBean.getPageSize());
			List<UnLineOrderProfitVO> unLineOrderProfitList = iUnlineOrderProfitMapper.
					unLineOrderProfitList(unLineOrderProfitDTO);
			PageInfo<UnLineOrderProfitVO> pageInfo = new PageInfo<UnLineOrderProfitVO>(
					unLineOrderProfitList);
			//转换下
			for(UnLineOrderProfitVO m : pageInfo.getList()){
				m.setOrderMoney1(MoneyTransUtil.transDivisDouble(m.getOrderMoney()));
				m.setChargeFee1(MoneyTransUtil.transDivisDouble(m.getChargeFee()));
				m.setPhChargeFee1(MoneyTransUtil.transDivisDouble(m.getPhChargeFee()));
				m.setYstChargeFee1(MoneyTransUtil.transDivisDouble(m.getYstChargeFee()));
				m.setCityAgentProfit1(MoneyTransUtil.transDivisDouble(m.getCityAgentProfit()));
				m.setCountyPromoterProfit1(MoneyTransUtil.transDivisDouble(m.getCountyPromoterProfit()));
				m.setTownAgentProfit1(MoneyTransUtil.transDivisDouble(m.getTownAgentProfit()));
				m.setCityPromoterProfit1(MoneyTransUtil.transDivisDouble(m.getCityPromoterProfit()));
				m.setCountyPromoterProfit1(MoneyTransUtil.transDivisDouble(m.getCountyPromoterProfit()));
				m.setTownPromoterProfit1(MoneyTransUtil.transDivisDouble(m.getTownPromoterProfit()));
				m.setMerchantPromoterProfit1(MoneyTransUtil.transDivisDouble(m.getMerchantPromoterProfit()));
				m.setChargeProfitTotal1(MoneyTransUtil.transDivisDouble(m.getChargeProfitTotal()));
				m.setChargeProfitRemain1(MoneyTransUtil.transDivisDouble(m.getChargeProfitRemain()));
				m.setCountyAgentProfit1(MoneyTransUtil.transDivisDouble(m.getCountyAgentProfit()));//县代理分润
				m.setStoreManagerProfit1(MoneyTransUtil.transDivisDouble(m.getStoreManagerProfit()));//店面经理的分润
				m.setMemberPromoterProfit1(MoneyTransUtil.transDivisDouble(m.getMemberPromoterProfit()));//推荐会员的分润
				m.setKhChargeFee1(MoneyTransUtil.transDivisDouble(m.getKhChargeFee()));//快火分润
			}
			return ResultUtil.getResult(RespCode.Code.SUCCESS, pageInfo.getList(), pageInfo.getTotal());
		} catch (Exception e) {
			e.printStackTrace();
			throw new ProfitException(ProfitExceptionEnum.PROFIT_LIST_EXCEPTION);
		}
	}
	/**
	 * 
	 * @Title: getUnLineOrderProfitEXCEL
	 * @Description: 导出线下订单分润EXCEL(导出当前查询条件下的全部)
	 * @author 
	 * @date 2017年5月22日 下午4:16:44
	 * @param unLineOrderProfitDTO
	 * @return
	 */
	public Result getUnLineOrderProfitEXCEL(UnLineOrderProfitDTO unLineOrderProfitDTO){
		try {
			List<UnLineOrderProfitVO> unLineOrderProfitList = iUnlineOrderProfitMapper.
					unLineOrderProfitList(unLineOrderProfitDTO);
			//转换下
			for(UnLineOrderProfitVO m : unLineOrderProfitList){
				m.setOrderMoney1(MoneyTransUtil.transDivisDouble(m.getOrderMoney()));
				m.setChargeFee1(MoneyTransUtil.transDivisDouble(m.getChargeFee()));
				m.setPhChargeFee1(MoneyTransUtil.transDivisDouble(m.getPhChargeFee()));
				m.setYstChargeFee1(MoneyTransUtil.transDivisDouble(m.getYstChargeFee()));
				m.setCityAgentProfit1(MoneyTransUtil.transDivisDouble(m.getCityAgentProfit()));
				m.setCountyPromoterProfit1(MoneyTransUtil.transDivisDouble(m.getCountyPromoterProfit()));
				m.setTownAgentProfit1(MoneyTransUtil.transDivisDouble(m.getTownAgentProfit()));
				m.setCityPromoterProfit1(MoneyTransUtil.transDivisDouble(m.getCityPromoterProfit()));
				m.setCountyPromoterProfit1(MoneyTransUtil.transDivisDouble(m.getCountyPromoterProfit()));
				m.setTownPromoterProfit1(MoneyTransUtil.transDivisDouble(m.getTownPromoterProfit()));
				m.setMerchantPromoterProfit1(MoneyTransUtil.transDivisDouble(m.getMerchantPromoterProfit()));
				m.setChargeProfitTotal1(MoneyTransUtil.transDivisDouble(m.getChargeProfitTotal()));
				m.setChargeProfitRemain1(MoneyTransUtil.transDivisDouble(m.getChargeProfitRemain()));
				m.setCountyAgentProfit1(MoneyTransUtil.transDivisDouble(m.getCountyAgentProfit()));//县代理分润
			}
			return ResultUtil.getResult(RespCode.Code.SUCCESS, unLineOrderProfitList);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ProfitException(ProfitExceptionEnum.PROFIT_LIST_EXCEPTION);
		}
	}
	/**
	 * 
	* Title: UnLineOrderProfit
	* Description:分润定时器实现类
	* @author Mr.Dong
	* @date 2017年5月23日 上午9:37:40
	* @return
	* @see com.ph.shopping.facade.profit.service.IUnlineOrderProfitService#UnLineOrderProfit()
	 */
	@Override
	@Transactional
	public Result UnLineOrderProfit() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:sss");
		logger.info("#################################线下订单开始分润 CurrentTime：+"+sdf.format(new Date())+"#####################################");
		try{
			Result result = ResultUtil.getResult(RespCode.Code.SUCCESS);
			List<UnLineOrderVO> unLineOrderList = iUnlineOrderProfitMapper.getUnLineOrderList();
			if(unLineOrderList.size() < 1){
				logger.info("无线下订单分润数据");
				return ResultUtil.getResult(RespCode.Code.SUCCESS);
			}
			
			int i = 0;
			int j = 0;
			//通过商户区域获取此商户的各级代理和各级代理的推广师
			for(UnLineOrderVO m : unLineOrderList){
				result = this.OrderProfit(m.getOrderNo());
				this.iUnlineOrderProfitMapper.updateOrderIsProfitById(m.getId());
			}
			logger.info("#################################线下订单分润结束CurrentTime：+"+sdf.format(new Date())+"#####################################");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ProfitException(ProfitExceptionEnum.UNLINE_ORDERP_ROFIT_EXCEPTION);
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
		ids.add(new Long (4));//管理费分润比率   15 
		
		ids.add(new Long (5));//普惠管理费分润比率  10
		ids.add(new Long (6));//易商通管理费分润比率  3
		ids.add(new Long (25));//快火管理收入比例  2
		ids.add(new Long (7));//市级代理管理费分润比率 0.1
		ids.add(new Long (8));//县级代理管理费分润比率 0.1
		ids.add(new Long (9));//社区代理管理费分润比率 0.1
		ids.add(new Long (10));//市级代理推广分润比率  0.2
		ids.add(new Long (11));//县级代理推广分润比率  0.3
		ids.add(new Long (12));//社区代理推广分润比率  0.4
		ids.add(new Long (13));//商户的推广师分润比率 0.05
		ids.add(new Long (30));//店面经理分润比率 0.5
		ids.add(new Long (29));//推广会员分润比例  0.02
		Result systemParameterListByIds = iSystemParameterService.getSystemParameterListByIds(ids);
		logger.info("调用系统参数接口返回参数：result={}",JSON.toJSONString(systemParameterListByIds));
		Map<Long,Object> map = (Map<Long,Object>)systemParameterListByIds.getData();
		
		Double YSign = (Double)(map.get(new Long (4)));//管理费分润比率  13
		
		Double ESign = (Double)(map.get(new Long (5)));//普惠管理费分润比率
		Double NSign = (Double)(map.get(new Long (25)));//快火管理收入比例
		Double OSign = (Double)(map.get(new Long (29)));//推广会员分润比例
		Double PSign = (Double)(map.get(new Long (6)));//易商通管理费分润比率
		Double GSign = (Double)(map.get(new Long (7)));//市级代理管理费分润比率
		Double HSign = (Double)(map.get(new Long (8)));//县级代理管理费分润比率
		Double ISign = (Double)(map.get(new Long (9)));//社区代理管理费分润比率
		Double JSign = (Double)(map.get(new Long (10)));//县级代理推广师分润比率
		Double KSign = (Double)(map.get(new Long (11)));//市级代理推广师分润比率
		Double LSign = (Double)(map.get(new Long (12)));//社区代理推广师分润比率
		Double MSign = (Double)(map.get(new Long (13)));//商户的推广师分润比率
		Double QSign = (Double)(map.get(new Long (30)));//商户的推广师分润比率
		Map<String,Double> mapNew = new  HashMap<String, Double>();
		mapNew.put("y", YSign);
		mapNew.put("n", NSign);
		mapNew.put("o", OSign);
		mapNew.put("p", PSign);
		
		mapNew.put("e", ESign);
		mapNew.put("g", GSign);
		mapNew.put("h", HSign);
		mapNew.put("i", ISign);
		mapNew.put("j", JSign);
		mapNew.put("k", KSign);
		mapNew.put("l", LSign);
		mapNew.put("m", MSign);
		mapNew.put("q", QSign);
		return mapNew;
	}
	
	/**
	 * @Title: getUnLineOrderProfitList
	 * @Description: 整理线下订单数据，并设置分润值
	 * 				  拆分成线下订单分润列表、代理商分润列表、推广师分润列表、会员分润列表
	 * 		
	 * 		线下订单分润列表	，用于保存数据库分润记录表，
	 * 		代理商分润列表	，用于更新代理余额
	 * 		推广师分润列表	，用于将会员积分表中【会员分润积分】字段清空（置为0）
	 * 		会员分润列表		，用于将线下订单表中【是否分润】状态置为“2会员分润状态”
	 * 		代理商分润列表+推广师分润列表 ，用于将线下订单表中【是否分润】状态改成“1已分润 ”
	 * 		推广师分润列表+会员分润列表	，用于分润更新推广师、会员的积分（可用积分、会员分润积分、会员推广商户奖励积分）
	 * 
	 * 		备注：（1）代理商列表中数据，可能会与推广师列表中的数据重复；因为所做的操作为将【是否分润】状态改成“1已分润 ”，所以重复操作不影响
	 * 			   （2）代理商列表中数据，可能会与会员列表中的数据重复；所以将【是否分润】状态置为“2会员分润状态”的操作必须在将【是否分润】状态改成“1已分润 ”的操作之后，即操作同一条数据时，以会员分润列表为准。
	 * @author Mr.Dong
	 * @date  2017年5月23日 下午2:42:42
	 * @updater   liuy
	 * @updateTime   2017年7月25日 下午16：00
	 * @param systemParameters 系统分润相关参数
	 * @param n 需要分润的线下订单列表
	 * @return UnLineOrderProfitTempVO 
	 */
	public UnlineOrderProfit getUnLineOrderProfitList(Map<String, Double> systemParameters,UnLineOrderVO n){
		UnLineOrderProfitTempVO unLineOrderProfitTempVO = new UnLineOrderProfitTempVO();
		//线下订单分润列表（数据库对应分润流水表）
		List<UnlineOrderProfit>  unLineOrderProfitList = new ArrayList<UnlineOrderProfit>();
		//代理商分润列表
		List<UnlineOrderProfit> agentProfitList = new ArrayList<UnlineOrderProfit>(); 		
		//推广师分润列表
		List<UnlineOrderProfit> promotionProfitList =  new ArrayList<UnlineOrderProfit>();	
		//会员分润列表
		List<UnlineOrderProfit> memberProfitList =  new ArrayList<UnlineOrderProfit>();
		
		UnlineOrderProfit p = new UnlineOrderProfit();
		Double y = systemParameters.get("y");  
		Double e1 = systemParameters.get("e");  //普惠
		Double n1 = systemParameters.get("n");  //快火
		Double p1 = systemParameters.get("p");  //易商通
			
		p.setId(n.getId());
		p.setOrderNo(n.getOrderNo());
		p.setOrderMoney(n.getPayMoney());
		//得到商户的分润比率
		Long a= p.getOrderMoney();
		logger.info("###订单金额："+a+",###商户分润比例："+y+",###商户Id："+n.getMerchantId());
		Long b = Long.valueOf(Math.round(Double.valueOf((DoubleUtils.formatRound(y * a, 0))))+"");//管理费
		p.setChargeFee(b.longValue());//管理费
		Long e = Long.valueOf(Math.round(Double.valueOf((DoubleUtils.formatRound(e1 * a, 0))))+"");//管理费
		p.setPhChargeFee(e.longValue());//普惠管理费
		Long f = Long.valueOf(Math.round(Double.valueOf((DoubleUtils.formatRound(p1 * a, 0))))+"");//管理费
		p.setYstChargeFee(f.longValue());//易商通管理费
		Long kh = Long.valueOf(Math.round(Double.valueOf((DoubleUtils.formatRound(n1 * a, 0))))+"");//管理费
		p.setKhChargeFee(kh);//快火管理费
		
		
		Double o1 = systemParameters.get("o"); //推广会员分润比例
		Double g1 = systemParameters.get("g"); //市级代理管理费分润比率
		Double h1 = systemParameters.get("h"); //县级代理管理费分润比率
		Double i1 = systemParameters.get("i"); //社区代理管理费分润比率
		Double j1 = systemParameters.get("j"); //县级代理推广师分润比率
		Double k1 = systemParameters.get("k"); //市级代理推广师分润比率
		Double l1 = systemParameters.get("l"); //社区代理推广师分润比率
		Double m1 = systemParameters.get("m"); //商户的推广师分润比率
		Double q1 = systemParameters.get("q"); //店面经理分润比率

		Long pp = 0L;
		//店面经理分润
		if(n.getStoreManagerId() != null){
			Long q = Long.valueOf(Math.round(Double.valueOf((DoubleUtils.formatRound(q1 * a, 0))))+"");
			p.setStoreManagerProfit(q);//店面经理分润
			pp = pp.longValue()+q.longValue();
		}
		//代理管理费分润
		if(n.getTownAgentId() != null){
			Long i = Long.valueOf(Math.round(Double.valueOf((DoubleUtils.formatRound(i1 * a, 0))))+"");
			p.setTownAgentProfit(i);//社区代理管理分润
			pp = pp.longValue()+i.longValue();
		}
		if(n.getCityAgentId() != null){
			Long g = Long.valueOf(Math.round(Double.valueOf((DoubleUtils.formatRound(g1 * a, 0))))+"");
			p.setCityAgentProfit(g);//市级代理管理分润
			pp = pp.longValue()+g.longValue();
		}
		if(n.getCountyAgentId() != null){
			Long h = Long.valueOf(Math.round(Double.valueOf((DoubleUtils.formatRound(h1 * a, 0))))+"");
			p.setCountyAgentProfit(h);//县级代理管理分润
			pp = pp.longValue()+h.longValue();
		}
		//代理推广费分润
		if(n.getTownPromoterId() != null){
			Long l = Long.valueOf(Math.round(Double.valueOf((DoubleUtils.formatRound(l1 * a, 0))))+"");
			p.setTownPromoterProfit(l);//社区代理推广分润
			pp = pp.longValue()+l.longValue();
			if(n.getCityPromoterId() != null){
				Long k = Long.valueOf(Math.round(Double.valueOf((DoubleUtils.formatRound(k1 * a, 0))))+"");
				p.setCityPromoterProfit(k);//市级代理推广分润
				pp = pp.longValue()+k.longValue();
			}
			if(n.getCountyPromoterId() != null){
				Long j = Long.valueOf(Math.round(Double.valueOf((DoubleUtils.formatRound(j1 * a, 0))))+"");
				p.setCountyPromoterProfit(j);//县级代理推广分润
				pp = pp.longValue()+j.longValue();
			}
		} else {
			if(n.getCityPromoterId() != null){
				Long k = Long.valueOf(Math.round(Double.valueOf((DoubleUtils.formatRound((k1+0.001) * a, 0))))+"");
				p.setCityPromoterProfit(k);//市级代理推广分润
				pp = pp.longValue()+k.longValue();
			}
			if(n.getCountyPromoterId() != null){
				Long j = Long.valueOf(Math.round(Double.valueOf((DoubleUtils.formatRound((j1+0.001) * a, 0))))+"");
				p.setCountyPromoterProfit(j);//县级代理推广分润
				pp = pp.longValue()+j.longValue();
			}
		}
		//分享商户分润
		if(n.getMerchantPromoterId() != null){
			Long m = Long.valueOf(Math.round(Double.valueOf((DoubleUtils.formatRound(m1 * a, 0))))+"");
			p.setMerchantPromoterProfit(m);//分享商户分润
			pp = pp.longValue()+m.longValue();
		}
		//分享会员分润
		if(n.getMemberPromoterId() != null){
			Long o = Long.valueOf(Math.round(Double.valueOf((DoubleUtils.formatRound(o1 * a, 0))))+"");
			p.setMemberPromoterProfit(o);//分享会员分润
			pp = pp.longValue()+o.longValue();
		}
			p.setChargeProfitTotal(pp.longValue());//管理费支出合计
			Long nn = f.longValue() - pp.longValue();
			p.setChargeProfitRemain(nn.longValue());//管理费剩余
			p.setCityAgentId(n.getCityAgentId());
			p.setCityAgentName(n.getCityAgentName());
			p.setCountyAgentId(n.getCountyAgentId());
			p.setCountyAgentName(n.getCountyAgentName());
			p.setTownAgentId(n.getTownAgentId());
			p.setTownAgentName(n.getTownAgentName());
			p.setCityPromoterId(n.getCityPromoterId());
			p.setCityPromoterName(n.getCityPromoterName());
			p.setCountyPromoterId(n.getCountyPromoterId());
			p.setCountyPromoterName(n.getCountyPromoterName());
			p.setTownPromoterId(n.getTownPromoterId());
			p.setTownPromoterName(n.getTownPromoterName());
			p.setMerchantPromoterId(n.getMerchantPromoterId());
			p.setMerchantPromoterName(n.getMerchantPromoterName());
			p.setMemberPromoterId(n.getMemberPromoterId());
			p.setMemberPromoterName(n.getMemberPromoterName());
			p.setOrderTime(n.getOrderTime());
			p.setMerchantName(n.getMerchantName());
			p.setMerchantId(n.getMerchantId());
			p.setStoreManagerId(n.getStoreManagerId());
			p.setStoreManagerName(n.getStoreManagerName());
			p.setIsMarketing(n.getIsMarketing());//是否推广师
			p.setIsProfit(n.getIsProfit());//是否分润
			//分润状态为0：线下订单分润列表、 代理商列表
			if(n.getIsProfit().equals(OrderIsProfitEnum.IS_PROFIT_NOT.getIsProfit())){
				unLineOrderProfitList.add(p);
				agentProfitList.add(p);
			}
			// 是推广师 ： 推广师列表   
			if (n.getIsMarketing() != null
					&& n.getIsMarketing().equals(MemberIsMarketingEnum.IS_MARKETING_BYYES.getCode())) {
				promotionProfitList.add(p);
			}
			//是会员，并且分润状态为0 ：会员列表
			if (n.getIsMarketing() != null
					&& n.getIsMarketing().equals(MemberIsMarketingEnum.IS_MARKETING_BYNO.getCode())
					&& n.getIsProfit().equals(OrderIsProfitEnum.IS_PROFIT_NOT.getIsProfit()) ) {
				memberProfitList.add(p);
			}
		unLineOrderProfitTempVO.setUnLineOrderProfitList(unLineOrderProfitList);
		unLineOrderProfitTempVO.setAgentProfitList(agentProfitList);
		unLineOrderProfitTempVO.setPromotionProfitList(promotionProfitList);
		unLineOrderProfitTempVO.setMemberProfitList(memberProfitList);
		return p;
	}
	
	/**
	 * 
	* @Title: StartAgentProfit
	* @Description: TODO(代理分润)
	* @author Mr.Dong
	* @date  2017年5月24日 上午11:48:25
	* @param unLineOrderProfitList
	* @throws Exception
	 */
	public void StartAgentProfit(List<UnlineOrderProfit> unLineOrderProfitList) throws  Exception{
		List<UserBalanceVO> userBalanceVoList = new ArrayList<UserBalanceVO>();
		List<UserBalanceRecordVO> userBalanceRecordVoList = new ArrayList<UserBalanceRecordVO>();
		//循环线下订单分润 
		for(UnlineOrderProfit m : unLineOrderProfitList){
			if(m.getCityAgentId()!= null){
				UserBalanceVO  userBalanceVo = new UserBalanceVO();
				userBalanceVo.setManageId(m.getCityAgentId());
				userBalanceVo.setBalance(m.getCityAgentProfit());
				userBalanceVoList.add(userBalanceVo);
				
				UserBalanceRecordVO userBalanceRecordVO = new UserBalanceRecordVO();
				userBalanceRecordVO.setUserId(m.getCityAgentId());
				userBalanceRecordVO.setOrderNo(m.getOrderNo());
				userBalanceRecordVO.setMoney(m.getCityAgentProfit());
				userBalanceRecordVO.setTransCode(String.valueOf(TransCodeEnum.UNLINE_ORDER_PROFIT_TRANSCODE.getCode()));
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
				userBalanceRecordVO.setTransCode(String.valueOf(TransCodeEnum.UNLINE_ORDER_PROFIT_TRANSCODE.getCode()));
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
				userBalanceRecordVO.setTransCode(String.valueOf(TransCodeEnum.UNLINE_ORDER_PROFIT_TRANSCODE.getCode()));
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
	 * 
	* @Title: StartMemberProfit
	* @Description: TODO(推广师分润加可用积分/会员分润加会员分润积分)  注意可能是代理推广师、商户推广师
	* @author Mr.Dong
	* @date  2017年5月24日 上午11:48:44
	* @param unLineOrderProfitList
	 */
	public void  StartMemberProfit(List<UnlineOrderProfit> unLineOrderProfitList) throws Exception {
		List<MemberScoreVO> memberScoreVOList = new ArrayList<MemberScoreVO>();
		for(UnlineOrderProfit m : unLineOrderProfitList){
			//1.设置公共字段部分
			MemberScoreVO memberScoreVO = new  MemberScoreVO();
			memberScoreVO.setOrderNo(m.getOrderNo());
			memberScoreVO.setIsProfit(m.getIsProfit());//是否分润
			memberScoreVO.setTransCode(String.valueOf(TransCodeEnum.UNLINE_ORDER_PROFIT_ADD_SCORE_TRANSCODE.getCode()));//推广师分润
			//设置市、县、区、商户分润数据
			if(m.getCityPromoterId() !=null){//市推
				MemberScoreVO memberScore = new MemberScoreVO();
				BeanUtils.copyProperties(memberScoreVO, memberScore);
				memberScore.setMemberId(m.getCityPromoterId());
				memberScore.setScore(m.getCityPromoterProfit());
				memberScoreVOList.add(memberScore);
			}
			if(m.getCountyPromoterId() != null){//县推
				MemberScoreVO memberScore = new MemberScoreVO();
				BeanUtils.copyProperties(memberScoreVO, memberScore);
				memberScore.setMemberId(m.getCountyPromoterId());
				memberScore.setScore(m.getCountyPromoterProfit());
				memberScoreVOList.add(memberScore);
			}
			if(m.getTownPromoterId() != null){//社区推
				MemberScoreVO memberScore = new MemberScoreVO();
				BeanUtils.copyProperties(memberScoreVO, memberScore);
				memberScore.setMemberId(m.getTownPromoterId());
				memberScore.setScore(m.getTownPromoterProfit());
				memberScoreVOList.add(memberScore);
			}
			if(m.getMerchantPromoterId() != null){//商推
				MemberScoreVO memberScore = new MemberScoreVO();
				BeanUtils.copyProperties(memberScoreVO, memberScore);
				memberScore.setMemberId(m.getMerchantPromoterId());
				memberScore.setScore(m.getMerchantPromoterProfit());
				memberScore.setIsReward(MemberIsRewardRecordEnum.IS_YES.getCode());
				if(m.getIsMarketing().equals(MemberIsMarketingEnum.IS_MARKETING_BYYES.getCode())){
					memberScore.setIsMarketing(MemberIsMarketingEnum.IS_MARKETING_BYYES.getCode());
				}else{
					memberScore.setTransCode(String.valueOf(TransCodeEnum.UNLINE_ORDER_PROFIT_MEMBER_ADD_SCORE_TRANSCODE.getCode()));//会员分润
					memberScore.setIsMarketing(MemberIsMarketingEnum.IS_MARKETING_BYNO.getCode());
				}
				memberScoreVOList.add(memberScore);
			}
		}
		
		if(memberScoreVOList.size() > 0){
			for(MemberScoreVO m : memberScoreVOList){
				int updateMemberScore = 0;
				//1.
				//代理推广师：为代理推广师且订单是否分润状态为0 
				//商户推广师：为商户推广师且是否推广师字段为是，订单是否分润状态为0、2均可
				//以上两种情况，更新可用积分
				if((m.getIsReward()==null && m.getIsProfit().equals(OrderIsProfitEnum.IS_PROFIT_NOT.getIsProfit()) 
						|| (m.getIsReward()!=null && m.getIsMarketing().equals(MemberIsMarketingEnum.IS_MARKETING_BYYES.getCode()))) ){
					updateMemberScore = iUnlineOrderProfitMapper.updateMemberScore(m);//推广师更新可用积分
					if(updateMemberScore < 0){
						throw new Exception("更新会员可用积分异常");
					}
				}
				
				//2.
				//会员推广商户：为会员且订单是否分润状态为0 
				if(m.getIsProfit().equals(OrderIsProfitEnum.IS_PROFIT_NOT.getIsProfit()) 
						&& m.getIsReward()!=null && m.getIsMarketing().equals(MemberIsMarketingEnum.IS_MARKETING_BYNO.getCode())){
					updateMemberScore = iUnlineOrderProfitMapper.updateMemberProfitScore(m);//会员更新分润积分
					if(updateMemberScore < 0){
						throw new Exception("更新会员分润积分异常");
					}
				}
				
				//3.
				//更新流水记录表 ：需要排除“订单是否分润状态为2”的“代理推广师分润”流水。
				if(!(m.getIsReward()==null && m.getIsProfit().equals(OrderIsProfitEnum.IS_MEMBER_PROFIT.getIsProfit()))){
					int insertMemberScoreTotal = iUnlineOrderProfitMapper.insertMemberScoreTotal(m);
					if(insertMemberScoreTotal < 0){
						throw new Exception("插入会员积分总表异常");
					}
					int insertMemberScoreIncome = iUnlineOrderProfitMapper.insertMemberScoreIncome(m);
					if(insertMemberScoreIncome < 0){
						throw new Exception("插入会员积分入账流水表异常");
					}
				}
						
				//4.
				//更新会员推广商户奖励积分，并写积分入账总流水、积分入账明细流水
				//(只有订单【是否分润】状态为0时执行，为2执行会有重复数据)
				//只有会员或者推广师推广商户的时候执行，会员不会推广代理商
				if(m.getIsProfit().equals(OrderIsProfitEnum.IS_PROFIT_NOT.getIsProfit()) 
						&& m.getIsReward()!=null && MemberIsRewardRecordEnum.IS_YES.getCode().equals(m.getIsReward()) ){
					MemberScoreVO mCopy = new MemberScoreVO();
					BeanUtils.copyProperties(m, mCopy);
					mCopy.setTransCode(String.valueOf(TransCodeEnum.RECOMMEND_MERCHANT_UNLINE_ORDER_REWARD_SCORE.getCode()));//会员推广商户
					updateMemberScore = iUnlineOrderProfitMapper.updateMerchantRewardScore(mCopy);//更新会员推广商户奖励积分
					if(updateMemberScore < 0){
						throw new Exception("更新会员推广商户奖励积分异常");
					}
					int insertMemberScoreTotal = iUnlineOrderProfitMapper.insertMemberScoreTotal(mCopy);
					if(insertMemberScoreTotal < 0){
						throw new Exception("插入会员积分总表异常");
					}
					int insertMemberScoreIncome = iUnlineOrderProfitMapper.insertMemberScoreIncome(mCopy);
					if(insertMemberScoreIncome < 0){
						throw new Exception("插入会员积分入账流水表异常");
					}
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
	public void  insertPromoterProfitRecord(List<UnlineOrderProfit> unLineOrderProfitList) throws Exception{
		List<PromoterProfitRecord>   ppr = new ArrayList<PromoterProfitRecord>(); //推广师分润
		List<PromoterProfitRecord>   mpr = new ArrayList<PromoterProfitRecord>(); //会员分润
		//获取需要入推广师分润的记录的list
		for(UnlineOrderProfit  m : unLineOrderProfitList){
			if(m.getCityPromoterName() !=null ){
				PromoterProfitRecord p = new  PromoterProfitRecord();
				p.setOrderNo(m.getOrderNo());
				p.setDeliveryDate(m.getOrderTime());
				p.setOrderMoney(m.getOrderMoney());
				p.setProProfit(m.getCityPromoterProfit());
				p.setEnterpriseType(RoleEnum.CITY_AGENT.getCode());
				
				p.setEnterpriseName(m.getCityAgentName());
				p.setUserId(m.getCityAgentId());
				
				p.setPromoterId(m.getCityPromoterId());
				if(m.getIsMarketing().equals(MemberIsMarketingEnum.IS_MARKETING_BYYES.getCode())){//推广师
					ppr.add(p);
				}else{//会员
					mpr.add(p);
				}
			}
			if(m.getCountyPromoterName() !=null ){
				PromoterProfitRecord p = new  PromoterProfitRecord();
				p.setOrderNo(m.getOrderNo());
				p.setDeliveryDate(m.getOrderTime());
				p.setOrderMoney(m.getOrderMoney());
				p.setProProfit(m.getCountyPromoterProfit());
				p.setEnterpriseType(RoleEnum.COUNTY_AGENT.getCode());
				
				p.setEnterpriseName(m.getCountyAgentName());
				p.setUserId(m.getCountyAgentId());
				
				p.setPromoterId(m.getCountyPromoterId());
				if(m.getIsMarketing().equals(MemberIsMarketingEnum.IS_MARKETING_BYYES.getCode())){//推广师
					ppr.add(p);
				}else{//会员
					mpr.add(p);
				}
			}
			
			if(m.getTownPromoterName() !=null ){
				PromoterProfitRecord p = new  PromoterProfitRecord();
				p.setOrderNo(m.getOrderNo());
				p.setDeliveryDate(m.getOrderTime());
				p.setOrderMoney(m.getOrderMoney());
				p.setProProfit(m.getTownPromoterProfit());
				p.setEnterpriseType(RoleEnum.COMMUNITY_AGENT.getCode());
				
				p.setEnterpriseName(m.getTownAgentName());
				p.setUserId(m.getTownAgentId());
				
				p.setPromoterId(m.getTownPromoterId());
				if(m.getIsMarketing().equals(MemberIsMarketingEnum.IS_MARKETING_BYYES.getCode())){//推广师
					ppr.add(p);
				}else{//会员
					mpr.add(p);
				}
			}
			
			if(m.getMerchantPromoterName() !=null ){
				PromoterProfitRecord p = new  PromoterProfitRecord();
				p.setOrderNo(m.getOrderNo());
				p.setDeliveryDate(m.getOrderTime());
				p.setOrderMoney(m.getOrderMoney());
				p.setProProfit(m.getMerchantPromoterProfit());
				p.setEnterpriseType(RoleEnum.MERCHANT.getCode());
				
				p.setEnterpriseName(m.getMerchantName());
				p.setUserId(m.getMerchantId());
				
				p.setPromoterId(m.getMerchantPromoterId());
				if(m.getIsMarketing().equals(MemberIsMarketingEnum.IS_MARKETING_BYYES.getCode())){//推广师
					ppr.add(p);
				}else{//会员
					mpr.add(p);
				}
			}
			
		}
		//批量入推广师分润记录表
		if(ppr.size() > 0){
			iUnlineOrderProfitMapper.insertPromoterProfitRecord(ppr);
		}	
		//批量入会员分润记录表
		if(mpr.size() > 0){
			iUnlineOrderProfitMapper.insertMemberProfitRecord(mpr);
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
	public void insertUserProfitRecord(List<UnlineOrderProfit> unLineOrderProfitList) throws Exception {
		List<SupplyProfitTotal> spt = new ArrayList<SupplyProfitTotal>();
		for(UnlineOrderProfit m : unLineOrderProfitList){
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
	
	@Transactional
	public Result OrderProfit(String orderNo){
		Result result = ResultUtil.getResult(RespCode.Code.FAIL);
		logger.info("订单"+orderNo+"开始分润");
		try{
			UnLineOrderVO m = this.iUnlineOrderProfitMapper.getOrderByOrderNo(orderNo);
			if(m == null){
				logger.info("订单"+orderNo+"不存在，无法分润");
				return result;
			}
			if(m.getMerchantPositionId() == null){
				logger.info("商户的区域positionId为null");
				return result;
			}
			List<AgentVO> agentPositionList = this.getPositionListById(m.getMerchantPositionId());
			//管理代理
			if(agentPositionList.size() > 0 ){
				logger.info("循环代理数据");
				for(AgentVO n : agentPositionList){//循环返回的代理
					if(n.getAgentLevelId() == 1){//市级代理
						m.setCityAgentId(n.getUserId());
						m.setCityAgentName(n.getAgentName());
					}
					if(n.getAgentLevelId() == 2){//县级代理
						m.setCountyAgentId(n.getUserId());
						m.setCountyAgentName(n.getAgentName());
					}
					if(n.getAgentLevelId() == 3){//社区代理
						m.setTownAgentId(n.getUserId());
						m.setTownAgentName(n.getAgentName());
					}
				}
			}
			//分享用户分润相关
			if(m.getMemberPromoterId() != null){
				Member member = (Member) this.iMemberService.getMemberInfoById(m.getMemberPromoterId()).getData();
				m.setMemberPromoterName(member.getMemberName());
				m.setMemberPromoterId(member.getId());
			}
			//商户推广师分润相关
			if(m.getMerchantId() != null){//商户id存在
				MerchantDTO merchantDTO =new MerchantDTO();
				merchantDTO.setUserId(m.getMerchantId());
				/*merchantDTO.setStatus((byte)1);// 1：审核通过*/
				merchantDTO.setIsFrozen((byte)0);//0 ：未冻结
				merchantDTO.setIsDelete((byte)0);//0: 未删除
				List<MerchantVO> merchantList = iMerchantService.getMerchantList(merchantDTO);
				logger.info("调用商户接口判断商户是否正常,返回参数"+ JSON.toJSONString(merchantList));
				if(merchantList.size() > 0){//证明商户是正常的
					Long userId = merchantList.get(0).getUserId();
					Map<String, Object> param = this.iUnlineOrderProfitMapper.getStoreManagerByUserId(userId);
					if(param != null && param.size() >0) {
						String telPhone = param.get("telPhone").toString();
						Map<String, Object> param1 = this.iUnlineOrderProfitMapper.getMemberByStoreManager(telPhone);
						m.setStoreManagerId(Long.valueOf(param1.get("id").toString()));
						m.setStoreManagerName(param1.get("memberName")==null?"":param1.get("memberName").toString());
					}else {
						m.setStoreManagerId(null);
						m.setStoreManagerName(null);
					}
					if(m.getMerchantPromoterId() != null){//商户推广师存在 判断是否冻结
						MemberDTO memberDTO = new MemberDTO();
						memberDTO.setId(m.getMerchantPromoterId());
						Result promotionIsCanProfit = iMemberService.getPromotionIsCanProfit(memberDTO);
						logger.info("调用会员接口判断推广师是否正常,返回参数"+ JSON.toJSONString(promotionIsCanProfit));
						Member member = (Member)promotionIsCanProfit.getData();
						if(member != null){
							m.setMerchantPromoterId(member.getId());;//商户的推广师id
							m.setMerchantPromoterName(member.getMemberName());;//商户的推广师名字
							m.setIsMarketing(member.getIsMarketing());//是否推广师
							if(member.getIsMarketing() == 1){
								Agent s = iAgentService.getAgentById(member.getAgentId());
								if(s != null ) {
									if (s.getAgentLevelId().longValue() == 1) {
										m.setCityPromoterId(s.getUserId());
										m.setCityPromoterName(s.getAgentName());
									}
									if (s.getAgentLevelId().longValue() == 2) {//县级代理
										Agent ss = this.iUnlineOrderProfitMapper.getAgentByCityId(s.getCityId());//获取上级市级代理
										if (ss != null) {
											m.setCityPromoterId(ss.getUserId());
											m.setCityPromoterName(ss.getAgentName());
										}
										m.setCountyPromoterId(s.getUserId());
										m.setCountyPromoterName(s.getAgentName());
									}
									if (s.getAgentLevelId().longValue() == 3) {//社区代理
										Agent ss = this.iUnlineOrderProfitMapper.getAgentByCityId(s.getCityId());//获取上级市级代理
										if(ss != null) {
											m.setCityPromoterId(ss.getUserId());
											m.setCityPromoterName(ss.getAgentName());
										}
										Agent sss = this.iUnlineOrderProfitMapper.getAgentByCountyId(s.getCountyId());//获取上级县级代理
										if(sss != null){
											m.setCountyPromoterId(sss.getUserId());
											m.setCountyPromoterName(sss.getAgentName());
										}
										m.setTownPromoterId(s.getUserId());
										m.setTownPromoterName(s.getAgentName());
									}
								}
							}
						}else{
							m.setMerchantPromoterId(null);;//商户的推广师id
							m.setMerchantPromoterName(null);;//商户的推广师名字
							m.setIsMarketing(null);
						}
					}
				}else{
					m.setMerchantId(null);//商户id
					m.setMerchantName(null);//商户名字
					m.setMerchantPromoterId(null);;//商户的推广师id
					m.setMerchantPromoterName(null);;//商户的推广师名字
					m.setIsMarketing(null);
				}
			}else{
				m.setMerchantId(null);//商户id
				m.setMerchantName(null);//商户名字
				m.setMerchantPromoterId(null);;//商户的推广师id
				m.setMerchantPromoterName(null);;//商户的推广师名字
				m.setIsMarketing(null);
			}
			//获取系统参数
			Map<String, Double> systemParameters = getSystemParameters();
			//整理线下订单数据列表，并封装分润值
			UnlineOrderProfit unLineOrderProfit = getUnLineOrderProfitList(systemParameters,m);
			//线下订单分润记录：操作线下订单分润列表
			List<UnlineOrderProfit> unLineOrderProfitList = new ArrayList<UnlineOrderProfit>();
			unLineOrderProfitList.add(unLineOrderProfit);
			if(unLineOrderProfitList.size()>0){
				int insertUnlineOrderProfitBatch = iUnlineOrderProfitMapper.insertUnlineOrderProfitBatch(unLineOrderProfitList);
				if(insertUnlineOrderProfitBatch < 0){
					throw new Exception("批量插入线下订单分润记录表异常");
				}
			}

			//导致死锁 在结算完成后更新
			/*if(unLineOrderProfit.getIsMarketing() == null){
				//将线下订单表中【是否分润】状态改成“1已分润 ”：操作代理商分润列表+推广师分润列表
				int updateUnlineOrderBonus = iUnlineOrderProfitMapper.updateUnlineOrderBonus(unLineOrderProfitList);
				if(updateUnlineOrderBonus < 0){
					throw new Exception("更改线下子订单表中的分润状态为已分润异常");
				}
			}else{
				//将线下订单表中【是否分润】状态置为“2会员分润状态”，操作会员分润列表
				//注意！！！这一步一定要在上一步【是否分润】状态改成“1已分润 ”之后做！！！因为代理商分润列表和会员分润列表可能会有重复数据，以会员分润列表为准
				int updateIsProfitToMemberProfit = iUnlineOrderProfitMapper.updateIsProfitToMemberProfit(unLineOrderProfitList);
				if(updateIsProfitToMemberProfit < 0){
					throw new Exception("更改线下子订单表中的分润状态为会员分润异常");
				}
				
			}*/
			//分润管理费
			StartAgentManageProfit(unLineOrderProfit); //分润记录查询余额明细
			//分润推广费
			if(unLineOrderProfit.getIsMarketing() != null && unLineOrderProfit.getIsMarketing().intValue() == 1){
				//业务员分享商户  分润代理推广费
				StartAgentReommondProfit(unLineOrderProfit); //分润记录查询余额明细
			}
			//分润分享收入
			if(unLineOrderProfit.getMerchantPromoterId() != null || unLineOrderProfit.getMemberPromoterId() != null || unLineOrderProfit.getStoreManagerId() != null){
				StartMemberShareProfit(unLineOrderProfit);   //分润记录查询余额明细
			}
			logger.info("订单"+orderNo+"分润结束");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("订单"+orderNo+"分润异常"+e.getMessage());
			throw new ProfitException(ProfitExceptionEnum.UNLINE_ORDERP_ROFIT_EXCEPTION);
		}
		
	}
	
	/**
	 * @param
	 *
	 * @throws Exception
	 */
	@Transactional
	private void StartMemberShareProfit(UnlineOrderProfit unlineOrderProfit) throws Exception{
		List<MemberScoreVO> memberScoreVOList = new ArrayList<MemberScoreVO>();
		MemberScoreVO memberScoreVO = new  MemberScoreVO();
		memberScoreVO.setOrderNo(unlineOrderProfit.getOrderNo());
		memberScoreVO.setIsProfit(unlineOrderProfit.getIsProfit());//是否分润
		if(unlineOrderProfit.getMerchantPromoterId() != null){
			MemberScoreVO memberScore = new MemberScoreVO();
			BeanUtils.copyProperties(memberScoreVO, memberScore);
			memberScore.setMemberId(unlineOrderProfit.getMerchantPromoterId());
			memberScore.setScore(unlineOrderProfit.getMerchantPromoterProfit());
			memberScore.setTransCode(String.valueOf(TransCodeEnum.ORDER_SHARE_MERCHANT_PROFIT_TRANSCODE.getCode()));
			memberScoreVOList.add(memberScore);
		}
		if(unlineOrderProfit.getStoreManagerId() != null){
			MemberScoreVO memberScore = new MemberScoreVO();
			BeanUtils.copyProperties(memberScoreVO, memberScore);
			memberScore.setMemberId(unlineOrderProfit.getStoreManagerId());
			memberScore.setScore(unlineOrderProfit.getStoreManagerProfit());
			memberScore.setTransCode(String.valueOf(TransCodeEnum.ORDER_STORE_MANAGER_PROFIT_TRANSCODE.getCode()));
			memberScoreVOList.add(memberScore);
		}
		if(unlineOrderProfit.getMemberPromoterId() != null){
			MemberScoreVO memberScore = new MemberScoreVO();
			BeanUtils.copyProperties(memberScoreVO, memberScore);
			memberScore.setMemberId(unlineOrderProfit.getMemberPromoterId());
			memberScore.setScore(unlineOrderProfit.getMemberPromoterProfit());
			memberScore.setTransCode(String.valueOf(TransCodeEnum.ORDER_SHARE_MEMBER_PROFIT_TRANSCODE.getCode()));
			memberScoreVOList.add(memberScore);
		}

		if(memberScoreVOList.size() > 0){
			for(MemberScoreVO m : memberScoreVOList){
				logger.info("=========="+ m.getId());
				Map<String, Object> map = iUnlineOrderProfitMapper.getMemberScoreById(m.getMemberId());
				logger.info("========="+JSONUtils.toJSONString(map));
				logger.info("==========="+map.get("shareMerchantScore")+"========="+map.get("shareMemberScore"));
				if(m.getTransCode().equals(String.valueOf(TransCodeEnum.ORDER_SHARE_MERCHANT_PROFIT_TRANSCODE.getCode()))){
					m.setTotelScore(Long.valueOf((map.get("shareMerchantScore")==null?0:map.get("shareMerchantScore")).toString()).longValue()+m.getScore().longValue());
				}
				if(m.getTransCode().equals(String.valueOf(TransCodeEnum.ORDER_STORE_MANAGER_PROFIT_TRANSCODE.getCode()))){
					m.setTotelScore(Long.valueOf((map.get("storeManagerScore")==null?0:map.get("storeManagerScore")).toString()).longValue()+m.getScore().longValue());
				}
				if(m.getTransCode().equals(String.valueOf(TransCodeEnum.ORDER_SHARE_MEMBER_PROFIT_TRANSCODE.getCode()))){
					m.setTotelScore(Long.valueOf((map.get("shareMemberScore")==null?0:map.get("shareMemberScore")).toString()).longValue()+m.getScore().longValue());
				}


				//用户维护分润记录
				int insertMemberScoreTotal = iUnlineOrderProfitMapper.insertMemberScoreTotal(m);
				if(insertMemberScoreTotal < 0){
					throw new Exception("插入会员积分总表异常");
				}
				int insertMemberScoreIncome = iUnlineOrderProfitMapper.insertMemberScoreIncome(m);
				if(insertMemberScoreIncome < 0){
					throw new Exception("插入会员积分入账流水表异常");
				}
				//用户维护余额
				if(m.getTransCode().equals(String.valueOf(TransCodeEnum.ORDER_SHARE_MERCHANT_PROFIT_TRANSCODE.getCode()))){
					int updateMemberScoreByTransCode = iUnlineOrderProfitMapper.updateMemberScoreByTransCode1(m);
					if(updateMemberScoreByTransCode < 0){
						throw new Exception("更新商户推广人分润积分异常");
					}
				}
				if(m.getTransCode().equals(String.valueOf(TransCodeEnum.ORDER_STORE_MANAGER_PROFIT_TRANSCODE.getCode()))){
					int updateMemberScoreByTransCode = iUnlineOrderProfitMapper.updateMemberScoreByTransCode3(m);
					if(updateMemberScoreByTransCode < 0){
						throw new Exception("更新店面经理分润积分异常");
					}
				}
				if(m.getTransCode().equals(String.valueOf(TransCodeEnum.ORDER_SHARE_MEMBER_PROFIT_TRANSCODE.getCode()))){
					int updateMemberScoreByTransCode = iUnlineOrderProfitMapper.updateMemberScoreByTransCode2(m);
					if(updateMemberScoreByTransCode < 0){
						throw new Exception("更新用户推广人分润积分异常");
					}
				}
			}
		}
	}
	/**
	 * @param m
	 * 分润推广费
	 * @throws Exception
	 */
	@Transactional
	private void StartAgentReommondProfit(UnlineOrderProfit m) throws Exception{
		List<UserBalanceVO> userBalanceVoList = new ArrayList<UserBalanceVO>();
		List<UserBalanceRecordVO> userBalanceRecordVoList = new ArrayList<UserBalanceRecordVO>();
		List<SupplyProfitTotal> spt = new ArrayList<SupplyProfitTotal>();
		if(m.getCityPromoterId()!= null){
			UserBalanceVO userBalanceVo = new UserBalanceVO();
			userBalanceVo.setManageId(m.getCityPromoterId());
			userBalanceVo.setBalance(m.getCityPromoterProfit());
			userBalanceVoList.add(userBalanceVo);
			
			UserBalanceRecordVO userBalanceRecordVO = new UserBalanceRecordVO();
			userBalanceRecordVO.setUserId(m.getCityPromoterId());
			userBalanceRecordVO.setOrderNo(m.getOrderNo());
			userBalanceRecordVO.setMoney(m.getCityPromoterProfit());
			userBalanceRecordVO.setTransCode(String.valueOf(TransCodeEnum.ORDER_RECOMMOND_PROFIT_TRANSCODE.getCode()));
			userBalanceRecordVO.setUserType(String.valueOf(RoleEnum.CITY_AGENT.getCode()));
			userBalanceRecordVoList.add(userBalanceRecordVO);
			
			SupplyProfitTotal sp = new SupplyProfitTotal();
			sp.setProfited(m.getCityPromoterProfit());
			sp.setUserId(m.getCityPromoterId());
			sp.setUserName(m.getCityPromoterName());
			sp.setUserType(RoleEnum.CITY_AGENT.getCode());
			spt.add(sp);
		}
		if(m.getCountyPromoterId() != null){
			UserBalanceVO  userBalanceVo = new UserBalanceVO();
			userBalanceVo.setManageId(m.getCountyPromoterId());
			userBalanceVo.setBalance(m.getCountyPromoterProfit());
			userBalanceVoList.add(userBalanceVo);
			
			UserBalanceRecordVO userBalanceRecordVO = new UserBalanceRecordVO();
			userBalanceRecordVO.setUserId(m.getCountyPromoterId());
			userBalanceRecordVO.setOrderNo(m.getOrderNo());
			userBalanceRecordVO.setMoney(m.getCountyPromoterProfit());
			userBalanceRecordVO.setTransCode(String.valueOf(TransCodeEnum.ORDER_RECOMMOND_PROFIT_TRANSCODE.getCode()));
			userBalanceRecordVO.setUserType(String.valueOf(RoleEnum.COUNTY_AGENT.getCode()));
			userBalanceRecordVoList.add(userBalanceRecordVO);
			
			SupplyProfitTotal sp = new SupplyProfitTotal();
			sp.setProfited(m.getCountyPromoterProfit());
			sp.setUserId(m.getCountyPromoterId());
			sp.setUserName(m.getCountyPromoterName());
			sp.setUserType(RoleEnum.COUNTY_AGENT.getCode());
			spt.add(sp);
		}
		if(m.getTownPromoterId()  != null){
			UserBalanceVO  userBalanceVo = new UserBalanceVO();
			userBalanceVo.setManageId(m.getTownPromoterId());
			userBalanceVo.setBalance(m.getTownPromoterProfit());
			userBalanceVoList.add(userBalanceVo);
			
			UserBalanceRecordVO userBalanceRecordVO = new UserBalanceRecordVO();
			userBalanceRecordVO.setUserId(m.getTownPromoterId());
			userBalanceRecordVO.setOrderNo(m.getOrderNo());
			userBalanceRecordVO.setMoney(m.getTownPromoterProfit());
			userBalanceRecordVO.setTransCode(String.valueOf(TransCodeEnum.ORDER_RECOMMOND_PROFIT_TRANSCODE.getCode()));
			userBalanceRecordVO.setUserType(String.valueOf(RoleEnum.COMMUNITY_AGENT.getCode()));
			userBalanceRecordVoList.add(userBalanceRecordVO);
			
			SupplyProfitTotal sp = new SupplyProfitTotal();
			sp.setProfited(m.getTownPromoterProfit());
			sp.setUserId(m.getTownPromoterId());
			sp.setUserName(m.getTownPromoterName());
			sp.setUserType(RoleEnum.COMMUNITY_AGENT.getCode());
			spt.add(sp);
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
		//批量入用户分润记录表
		if(spt.size() > 0){
			iUnlineOrderProfitMapper.insertUserProfitRecord(spt);
		}
	}
	/**
	 * @param m
	 *  分润代理管理费
	 * @throws Exception
	 */
	@Transactional
	private void StartAgentManageProfit(UnlineOrderProfit m) throws Exception{
		List<UserBalanceVO> userBalanceVoList = new ArrayList<UserBalanceVO>();
		List<UserBalanceRecordVO> userBalanceRecordVoList = new ArrayList<UserBalanceRecordVO>();
		List<SupplyProfitTotal> spt = new ArrayList<SupplyProfitTotal>();
		if(m.getCityAgentId()!= null){
			UserBalanceVO userBalanceVo = new UserBalanceVO();
			userBalanceVo.setManageId(m.getCityAgentId());
			userBalanceVo.setBalance(m.getCityAgentProfit());
			userBalanceVoList.add(userBalanceVo);
			
			UserBalanceRecordVO userBalanceRecordVO = new UserBalanceRecordVO();
			userBalanceRecordVO.setUserId(m.getCityAgentId());
			userBalanceRecordVO.setOrderNo(m.getOrderNo());
			userBalanceRecordVO.setMoney(m.getCityAgentProfit());
			userBalanceRecordVO.setTransCode(String.valueOf(TransCodeEnum.ORDER_MANAGE_PROFIT_TRANSCODE.getCode()));
			userBalanceRecordVO.setUserType(String.valueOf(RoleEnum.CITY_AGENT.getCode()));
			userBalanceRecordVoList.add(userBalanceRecordVO);
			
			SupplyProfitTotal sp = new SupplyProfitTotal();
			sp.setProfited(m.getCityAgentProfit());
			sp.setUserId(m.getCityAgentId());
			sp.setUserName(m.getCityAgentName());
			sp.setUserType(RoleEnum.CITY_AGENT.getCode());
			spt.add(sp);
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
			userBalanceRecordVO.setTransCode(String.valueOf(TransCodeEnum.ORDER_MANAGE_PROFIT_TRANSCODE.getCode()));
			userBalanceRecordVO.setUserType(String.valueOf(RoleEnum.COUNTY_AGENT.getCode()));
			userBalanceRecordVoList.add(userBalanceRecordVO);
			
			SupplyProfitTotal sp = new SupplyProfitTotal();
			sp.setProfited(m.getCountyAgentProfit());
			sp.setUserId(m.getCountyAgentId());
			sp.setUserName(m.getCountyAgentName());
			sp.setUserType(RoleEnum.COUNTY_AGENT.getCode());
			spt.add(sp);
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
			userBalanceRecordVO.setTransCode(String.valueOf(TransCodeEnum.ORDER_MANAGE_PROFIT_TRANSCODE.getCode()));
			userBalanceRecordVO.setUserType(String.valueOf(RoleEnum.COMMUNITY_AGENT.getCode()));
			userBalanceRecordVoList.add(userBalanceRecordVO);
			
			SupplyProfitTotal sp = new SupplyProfitTotal();
			sp.setProfited(m.getTownAgentProfit());
			sp.setUserId(m.getTownAgentId());
			sp.setUserName(m.getTownAgentName());
			sp.setUserType(RoleEnum.COMMUNITY_AGENT.getCode());
			spt.add(sp);
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
		//批量入用户分润记录表
		if(spt.size() > 0){
			iUnlineOrderProfitMapper.insertUserProfitRecord(spt);
		}
	}
	private List<AgentVO> getPositionListById(Long id) {
		PositionVO positionById = purchaseOrderProfitMapper.getPositionById(id);
		AgentDTO agentDTO = new AgentDTO();
		agentDTO.setProvinceId(positionById.getProvinceId());
		agentDTO.setCityId(positionById.getCityId());
		agentDTO.setCountyId(positionById.getCountyId());
		agentDTO.setTownId(positionById.getTownId());
		
		agentDTO.setStatus(SPMEnum.agentStatus.ONE.getIndex());//审核通过
		agentDTO.setIsDelete(SPMEnum.agentIsDelete.ZERO.getIndex());//没有被删除
		agentDTO.setIsFormSystem((byte)0);//快火代理
		agentDTO.setIsFrozen(SPMEnum.agentIsFrozen.ONE.getIndex());//冻结的 传过去代理直接非冻结
		List<AgentVO> agentPositionList = iAgentService.getAgentPositionList(agentDTO);
		logger.info("通过区域查询管理代理返回参数："+JSON.toJSONString(agentPositionList));
		return agentPositionList;
	}
	
	@Override
	@Transactional
	public Result doBackScore(){
		Result result = ResultUtil.getResult(RespCode.Code.FAIL);
		List<Map<String, Object>> list = this.iUnlineOrderProfitMapper.getRecordByOrder();
		String puHuiFenUrl = "";//维护普惠分润地址
		for (Map<String, Object> e : list) {
			Long id = Long.parseLong(e.get("id").toString());
			Long orderMoney = Long.valueOf(e.get("orderMoney").toString());
			String orderNo = e.get("orderNo").toString();
			String token = (e.get("token")==null?"":e.get("token")).toString();

            if(token==null || token.equals("")){
                logger.info("========================订单号"+orderNo+"用户token为空，返积分失败");
                result = ResultUtil.getResult(RespCode.Code.FAIL);
                continue;
            }
            PuhuiScoreDTO puhuiScoreDTO = new PuhuiScoreDTO();
            puhuiScoreDTO.setOrderMoney(new BigDecimal(orderMoney));
            puhuiScoreDTO.setOrderNo(orderNo);
            puhuiScoreDTO.setToken(token);
            puhuiScoreDTO.setSourceType(1);
            logger.info("========================"+puHuiFenUrl);
            Boolean flag = puhuiUtil.saveScore(puhuiScoreDTO);
            if (flag){
                this.iUnlineOrderProfitMapper.updateOrderBackScore(id);
                logger.info(orderNo+":订单普惠分润接口调用成功");
                result = ResultUtil.getResult(RespCode.Code.SUCCESS);
            }else {
                logger.info(orderNo + ":订单普惠分润接口返回失败");
                result = ResultUtil.getResult(RespCode.Code.FAIL);
            }
        }
        return result;
    }

}
