package com.ph.shopping.facade.accountsettle.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ph.shopping.common.util.core.RespCode;
import com.ph.shopping.common.util.core.ResultUtil;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.common.util.transform.MoneyTransUtil;
import com.ph.shopping.facade.mapper.IMemberScoreMapper;
import com.ph.shopping.facade.profit.dto.MemberScoreDetailedDTO;
import com.ph.shopping.facade.profit.entity.MemberScoreDetailed;
import com.ph.shopping.facade.profit.exception.ProfitExceptionEnum;
import com.ph.shopping.facade.profit.service.IMemberScoreService;
import com.ph.shopping.facade.profit.service.impl.PurchaseOrderProfitServiceImpl;
import com.ph.shopping.facade.profit.vo.MemberScoreDetailedVO;
import org.springframework.stereotype.Component;

/**
 * 
* @ClassName: MemberScoreServiceImpl
* @Description: 会员余额实现类 账户结算下会员余额
* @author 王强
* @date 2017年6月12日 上午9:59:03
 */
@Component
@Service(version = "1.0.0")
public class MemberScoreServiceImpl implements IMemberScoreService {

	private Logger logger = LoggerFactory.getLogger(PurchaseOrderProfitServiceImpl.class);
	
	@Autowired
	private  IMemberScoreMapper iMemberScoreMapper;
	
	/**
	 * 账户结算下会员余额list
	* Title: getMemberScoreDetailedList
	* Description:
	* @author Mr.Dong
	* @date 2017年6月12日 下午6:54:44
	* @param memberScoreDetailedDTO
	* @return
	* @see com.ph.shopping.facade.profit.service.IMemberScoreService#getMemberScoreDetailedList(com.ph.shopping.facade.profit.dto.MemberScoreDetailedDTO)
	 */
	@Override
	public Result getMemberScoreDetailedList(MemberScoreDetailedDTO memberScoreDetailedDTO) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String format = df.format(new Date());
		format = format +" 6:00:00";
		memberScoreDetailedDTO.setDate(format);
		PageHelper.startPage(memberScoreDetailedDTO.getPageNum(), memberScoreDetailedDTO.getPageSize());
		List<MemberScoreDetailedVO> memberScoreDetailedVOList = iMemberScoreMapper.getMemberScoreDetailedVOList(memberScoreDetailedDTO);
		PageInfo<MemberScoreDetailedVO> pageInfo = new PageInfo<MemberScoreDetailedVO>(
				memberScoreDetailedVOList);
		for(MemberScoreDetailedVO m : pageInfo.getList()){
			m.setBalanceDifference1(MoneyTransUtil.transDivisDouble(m.getBalanceDifference()));
			m.setStandbyScore1(MoneyTransUtil.transDivisDouble(m.getStandbyScore()));
			m.setEnableScore1(MoneyTransUtil.transDivisDouble(m.getEnableScore()));
			m.setDrawcashScore1(MoneyTransUtil.transDivisDouble(m.getDrawcashScore()));
			m.setPayTotalScore1(MoneyTransUtil.transDivisDouble(m.getPayTotalScore()));
			m.setProfitScore1(MoneyTransUtil.transDivisDouble(m.getProfitScore()));
			m.setReturnScore1(MoneyTransUtil.transDivisDouble(m.getReturnScore()));
			m.setReturnScoreOnline1(MoneyTransUtil.transDivisDouble(m.getReturnScoreOnline()));
		}
		return ResultUtil.getResult(RespCode.Code.SUCCESS,pageInfo.getList(),pageInfo.getTotal());
	}

	/**
	 * 导出会员余额EXCEL
	* Title: getMemberScoreDetailedEXCEL
	* Description:
	* @author Mr.Dong
	* @date 2017年6月12日 下午8:55:47
	* @param memberScoreDetailedDTO
	* @return
	* @see com.ph.shopping.facade.profit.service.IMemberScoreService#getMemberScoreDetailedEXCEL(com.ph.shopping.facade.profit.dto.MemberScoreDetailedDTO)
	 */
	@Override
	public Result getMemberScoreDetailedEXCEL(MemberScoreDetailedDTO memberScoreDetailedDTO) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String format = df.format(new Date());
		format = format +" 6:00:00";
		memberScoreDetailedDTO.setDate(format);
		List<MemberScoreDetailedVO> memberScoreDetailedVOList = iMemberScoreMapper.getMemberScoreDetailedVOList(memberScoreDetailedDTO);
		for(MemberScoreDetailedVO m : memberScoreDetailedVOList){
			m.setBalanceDifference1(MoneyTransUtil.transDivisDouble(m.getBalanceDifference()));
			m.setStandbyScore1(MoneyTransUtil.transDivisDouble(m.getStandbyScore()));
			m.setEnableScore1(MoneyTransUtil.transDivisDouble(m.getEnableScore()));
			m.setDrawcashScore1(MoneyTransUtil.transDivisDouble(m.getDrawcashScore()));
			m.setPayTotalScore1(MoneyTransUtil.transDivisDouble(m.getPayTotalScore()));
			m.setProfitScore1(MoneyTransUtil.transDivisDouble(m.getProfitScore()));
			m.setReturnScore1(MoneyTransUtil.transDivisDouble(m.getReturnScore()));
			m.setReturnScoreOnline1(MoneyTransUtil.transDivisDouble(m.getReturnScoreOnline()));
		}
		return ResultUtil.getResult(RespCode.Code.SUCCESS,memberScoreDetailedVOList);
	}

	/**
	 * 更改会员积分冻结和解冻
	* Title: updateMemberScoreStatus
	* Description:
	* @author Mr.Dong
	* @date 2017年6月13日 上午9:38:51
	* @param memberId
	* @param status
	* @return
	* @see com.ph.shopping.facade.profit.service.IMemberScoreService#updateMemberScoreStatus(java.lang.Long, java.lang.Byte)
	 */
	@Override
	public Result updateMemberScoreStatus(Long memberId, int status) {
		Result result = ResultUtil.getResult(RespCode.Code.SUCCESS);
		int updateMemberScoreStatus = iMemberScoreMapper.updateMemberScoreStatus(memberId, status);
		if(updateMemberScoreStatus < 1){
			logger.info("更改会员积分状态失败");
			return ResultUtil.getResult(ProfitExceptionEnum.UPDATE_MEMBER_SCORE_EXCEPTION);
		}
		return result;
	}

	/**
	 * 会员积分统计定时器执行
	* Title: memberScoreStatistics
	* Description:
	* @author Mr.Dong
	* @date 2017年7月5日 下午4:02:24
	* @return
	* @see com.ph.shopping.facade.profit.service.IMemberScoreService#memberScoreStatistics()
	 */
	@Override
	public Result memberScoreStatistics() throws Exception {
		List<MemberScoreDetailed> memberScoreDetailedList = iMemberScoreMapper.getMemberScoreDetailedList();
		//转换下 
		for(MemberScoreDetailed m : memberScoreDetailedList){
			MemberScoreDetailedVO mp = iMemberScoreMapper.getMemberScoreTotalParameter(m.getMemberId());
			//特殊处理 会员总的支付积分
			Long memberPayTotalScore = mp.getPayTotalScore();
			m.setPayTotalScore(memberPayTotalScore);
			//特殊处理 会员总的分润积分
			Long memberProfitScore = mp.getProfitScore();
			m.setProfitScore(memberProfitScore);
			//处理平衡差相关...........................
			//待用转可用积分
			Long returnScore = mp.getReturnScore();
			m.setReturnScore(returnScore);
			//线上订单订单退款
			Long returnScoreOnline = mp.getReturnScoreOnline();
			m.setReturnScoreOnline(returnScoreOnline);
			Long turnScoreAndRefundScore = returnScore + returnScoreOnline;
			if(m.getIsMarketing() == 1){//1 是推广师
				m.setBalanceDifference(
						turnScoreAndRefundScore + memberProfitScore-memberPayTotalScore-m.getDrawcashScore()-m.getEnableScore() 
						);
			}else{
				m.setBalanceDifference(
						turnScoreAndRefundScore - memberPayTotalScore - m.getDrawcashScore()-m.getEnableScore() 
						);
			}
		}
		//批量入会员统计表
		iMemberScoreMapper.insertMemberScoteTotalBatch(memberScoreDetailedList);
		return ResultUtil.getResult(RespCode.Code.SUCCESS);
	}
}
