package com.ph.shopping.facade.score.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

import com.alibaba.fastjson.JSONObject;
import com.ph.shopping.common.core.sms.HttpClientUtils;
import com.ph.shopping.common.util.http.HttpResult;
import com.ph.shopping.facade.mapper.ScoreDetailsMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.dubbo.container.Main;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ph.shopping.common.core.customenum.TransCodeEnum;
import com.ph.shopping.common.util.constants.Constants;
import com.ph.shopping.common.util.core.RespCode;
import com.ph.shopping.common.util.core.ResultUtil;
import com.ph.shopping.common.util.date.DateUtil;
import com.ph.shopping.common.util.date.DoubleUtils;
import com.ph.shopping.common.util.page.PageBean;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.common.util.transform.MoneyTransUtil;
import com.ph.shopping.facade.score.entity.PhScoreTotalTrade;
import com.ph.shopping.facade.score.exception.ScoreException;
import com.ph.shopping.facade.score.exception.ScoreExceptionEnum;
import com.ph.shopping.facade.score.request.QuerySingleScoreInfoDTO;
import com.ph.shopping.facade.score.request.ScorePageDTO;
import com.ph.shopping.facade.score.senum.ScoreTypeEnum;
import com.ph.shopping.facade.score.service.IScoreService;
import com.ph.shopping.facade.score.vo.MemberCostScoreVO;
import com.ph.shopping.facade.score.vo.MemberScoreRecordVO;
import com.ph.shopping.facade.score.vo.MemberScoreShopVO;
import com.ph.shopping.facade.score.vo.MemberScoreVO1;
import com.ph.shopping.facade.score.vo.MemberScoreVO2;
import com.ph.shopping.facade.score.vo.MemberSingleScoreVO;
import com.ph.shopping.util.OrderResultEnum;


@Component
@Service(version = "1.0.0")
public class ScoreServiceImpl implements IScoreService {

	// 创建日志
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ScoreDetailsMapper scoreDetailsMapper;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public long memberScoreTrade(long memberId, TransCodeEnum codeEnum, long score, String orderNo, long handingCharge)
			throws Exception {
		score = codeEnum.getMark() * score;
		handingCharge = codeEnum.getMark() * handingCharge;
		logger.info("积分业务参数: memberId=" + memberId + ",codeEnum=" + codeEnum.toString() + ",score=" + score
				+ ",orderNo=" + orderNo + ",handingCharge=" + handingCharge);
		switch (codeEnum) {
		case MEMBER_CONSUME:
			return onlineOrderScoreTrade(memberId, codeEnum, score, orderNo);
		case MEMBER_BACK_SCORE:
			return returnMemberScoreTrade(memberId, codeEnum, score, orderNo, handingCharge);
		case MEMBER_DRAWCASH:
			return memberDrawCashTrade(memberId, codeEnum, score, orderNo, handingCharge);
		case MEMBER_DRAWCASH_FAIL:
			return memberDrawCashTrade(memberId, codeEnum, score, orderNo, handingCharge);
		case MEMBER_DRAWCASH_FAIL_REDUCE:
			return memberDrawCashFailTrade(memberId, codeEnum, score, orderNo, handingCharge);
		case MEMBER_DRAWCASHADD:
			return memberDrawCashAddTrade(memberId, codeEnum, score, orderNo, handingCharge);
		case MEMBER_COST_STANDBYSCORE:
			return costMemberStandByScore(memberId, codeEnum, score, orderNo);
		case MEMBER_RETURN_ENABLESCORE:
			return returnMemberEnableScre(memberId, codeEnum, score, orderNo, handingCharge);
		case MEMBER_UNLINE_ORDER:
			return onlineOrderScoreTrade(memberId, codeEnum, score, orderNo);
		case ONLINE_ORDER_REFUND:
			return onlineOrderScoreTrade(memberId, codeEnum, score, orderNo);
		case HUNTER_ORDER_RETURN_SCORE:
			return returnMemberScoreHunterTrade(memberId, codeEnum, score, orderNo, handingCharge);
		case MEMBER_RETURN_STANDBYSCORE:
			return returnMemberStandByScoreTrade(memberId, codeEnum, score, orderNo, handingCharge);
		case RECOMMEND_MEMBER_UNLINE_ORDER_REWARD_SCORE:
			return retuenMemberRewardScore(memberId, codeEnum, score, orderNo);
		default:
			throw new Exception("未知交易码!");
		}
	}

	@Override
	public Result getMerchantScoreDeatil(Long userId,Integer pageNo,Integer pageSize) {

		if(pageNo == null || pageNo == 0){
			pageNo = 1;
		}
		if(pageSize == null || pageSize == 0){
			pageNo = 10;
		}

		int index = (pageNo - 1) * pageSize;

		RowBounds rowBounds = new RowBounds(index,pageSize);


		List<Map<String,Object>> merchantScoreDetailsList = scoreDetailsMapper.getMerchantScoreDetail(userId,rowBounds);

		if(merchantScoreDetailsList == null || merchantScoreDetailsList.isEmpty()){
			return ResultUtil.setResult(false,"暂无明细");
		}

		for(Map<String,Object> merchantScore : merchantScoreDetailsList){
			merchantScore.put("createTime",DateUtil.dateToStr((Date)merchantScore.get("createTime"),DateUtil.DATE_TIME_LINE));
		}

		return ResultUtil.getResult(RespCode.Code.SUCCESS,merchantScoreDetailsList);
	}



	/**
	 * @param memberId
	 * @param codeEnum
	 * @param score
	 * @param orderNo
	 * @return
	 * @throws Exception
	 * @Title: returnMemberEnableScre
	 * @Description: 增加可用积分
	 * @author 王强
	 * @date 2017年3月24日 上午9:49:50
	 */
	private long returnMemberEnableScre(long memberId, TransCodeEnum codeEnum, long score, String orderNo,
			long handingCharge) {
		try {
			updateMemberScore(memberId, 0, score, 0);

			long setId = getSetId(memberId, score, orderNo, codeEnum.getCode(), 0l);

			insertIncomeTrade(memberId, score, codeEnum.getCode(), setId, orderNo, handingCharge);

			return setId;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ScoreException(ScoreExceptionEnum.SCORE_EXCEPTION);
		}

	}

	/**
	 *
	 * @param memberId
	 * @param codeEnum
	 * @param score
	 * @param orderNo
	 * @return
	 */
	private long costMemberStandByScore(long memberId, TransCodeEnum codeEnum, long score, String orderNo) {
		try {
			int level = scoreDetailsMapper.getMemberLevel(memberId);
			long enableScore = 0;// 可用积分
			if (level == 1) {// 普通会员
				enableScore = (long) ((score * Constants.COMM_MEMBER) / Constants.BASE_MEMBER);
			} else if (level == 2) {// VIP
				enableScore = (long) ((score * Constants.VIP_MEMBER) / Constants.BASE_MEMBER);
			}

			updateMemberScore(memberId, enableScore, 0, 0);

			long setId = getSetId(memberId, enableScore, orderNo, codeEnum.getCode(), 0l);

			insertCostTrade(memberId, enableScore, codeEnum.getCode(), setId, orderNo, 0l);

			long ret = memberScoreTrade(memberId, TransCodeEnum.MEMBER_RETURN_ENABLESCORE, -enableScore, orderNo, 0l);
			if (ret < 0) {
				throw new ScoreException(ScoreExceptionEnum.UPDATE_MEMBER_SCORE_FAIL);
			}

			return setId;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ScoreException(ScoreExceptionEnum.SCORE_EXCEPTION);
		}

	}

	/**
	 *
	 * @param memberId
	 * @param codeEnum
	 * @param score
	 * @param orderNo
	 * @param handingCharge
	 * @return
	 */
	private long memberDrawCashAddTrade(long memberId, TransCodeEnum codeEnum, long score, String orderNo,
			long handingCharge) {
		try {

			updateMemberScore(memberId, 0, 0, score);
			long setId = getSetId(memberId, score, orderNo, codeEnum.getCode(), 0l);

			insertIncomeTrade(memberId, score, codeEnum.getCode(), setId, orderNo, handingCharge);

			return setId;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ScoreException(ScoreExceptionEnum.SCORE_EXCEPTION);
		}

	}

	/**
	 * @param memberId
	 * @param codeEnum
	 * @param score
	 * @return
	 * @throws Exception
	 * @Title: memberDrawCashTrade
	 * @Description: 提现消费
	 * @author 王强
	 * @date 2017年3月22日 下午4:20:45
	 */
	public long memberDrawCashTrade(long memberId, TransCodeEnum codeEnum, long score, String orderNo,
			long handingCharge) {
		try {
			logger.info("=====会员提现失败开始返回可用积分=====");
			long scoreEx = score + handingCharge;

			updateMemberScore(memberId, 0, scoreEx, 0);

			long setId = getSetId(memberId, score, orderNo, codeEnum.getCode(), handingCharge);

			insertCostTrade(memberId, score, codeEnum.getCode(), setId, orderNo, handingCharge);
			logger.info("=====会员提现失败开始返回可用积分=====");
			
//			memberScoreTrade(memberId, TransCodeEnum.MEMBER_DRAWCASH_FAIL_REDUCE, scoreEx, orderNo, handingCharge);
			logger.info("=====会员提现失败积分处理流程完成=====");
			return setId;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ScoreException(ScoreExceptionEnum.SCORE_EXCEPTION);
		}

	}
	
	/**
	 * @param memberId
	 * @param codeEnum
	 * @param score
	 * @return
	 * @throws Exception
	 * @Title: memberDrawCashTrade
	 * @Description: 提现失败返回可用积分，删掉已提现积分
	 * @author 王强
	 * @date 2017年3月22日 下午4:20:45
	 */
	public long memberDrawCashFailTrade(long memberId, TransCodeEnum codeEnum, long score, String orderNo,
			long handingCharge) {
		try {
			logger.info("=====会员提现失败开始扣除累计已提现积分=====");
			long scoreEx = score + handingCharge;

			updateMemberScore(memberId, 0, 0, scoreEx);

			long setId = getSetId(memberId, score, orderNo, codeEnum.getCode(), handingCharge);

			insertCostTrade(memberId, score, codeEnum.getCode(), setId, orderNo, handingCharge);
			logger.info("=====会员提现失败开始扣除累计已提现积分=====");
			return setId;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ScoreException(ScoreExceptionEnum.SCORE_EXCEPTION);
		}

	}
	

	/**
	 * @param memberId
	 * @param codeEnum
	 * @param score
	 * @return
	 * @throws Exception
	 * @Title: returnMemberScore
	 * @Description: 返回积分(待返积分)
	 * @author 王强
	 * @date 2017年3月22日 下午4:16:28
	 */
	public long returnMemberScoreTrade(long memberId, TransCodeEnum codeEnum, long score, String orderNo,
			long handingCharge) {
		try {
			int res = updOnLineOrderIsBackScore(orderNo);
			if (res != 1) {
				if (updUnLineOrderIsBackScore(orderNo) != 1) {
					throw new ScoreException(ScoreExceptionEnum.UPDATE_SCORE);
				}
			}

			updateMemberScore(memberId, score, 0, 0);

			long setId = getSetId(memberId, score, orderNo, codeEnum.getCode(), 0l);

			insertIncomeTrade(memberId, score, codeEnum.getCode(), setId, orderNo, handingCharge);

			return setId;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ScoreException(ScoreExceptionEnum.SCORE_EXCEPTION);
		}

	}

	/**
	 * @param memberId
	 * @param codeEnum
	 * @param score
	 * @return
	 * @Title: memberScoreTrade
	 * @Description: 线上订单消费积分流水
	 * @author 王强
	 * @date 2017年3月17日 下午4:31:08
	 */
	private long onlineOrderScoreTrade(long memberId, TransCodeEnum codeEnum, long score, String orderNo) {
		try {
			// 更新用户积分
			updateMemberScore(memberId, 0, score, 0);

			long setId = getSetId(memberId, score, orderNo, codeEnum.getCode(), 0l);// 插入总流水表，返回相应的业务ID

			insertCostTrade(memberId, score, codeEnum.getCode(), setId, orderNo, 0l);

			return setId;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ScoreException(ScoreExceptionEnum.SCORE_EXCEPTION);
		}

	}

	/**
	 *
	 * @param memberId
	 * @param score
	 * @param orderNo
	 * @param transCode
	 * @param handingCharge
	 * @return
	 * @throws Exception
	 */
	private long getSetId(long memberId, long score, String orderNo, int transCode, long handingCharge)
			throws Exception {
		PhScoreTotalTrade totalTrade = new PhScoreTotalTrade();
		totalTrade.setMemberId(memberId);
		totalTrade.setScore(score);
		totalTrade.setOrderNo(orderNo);
		totalTrade.setTransCode(transCode);
		totalTrade.setHandingCharge(handingCharge);

		insertMemberTrade(totalTrade);

		return totalTrade.getId();
	}

	/**
	 * 更新用户积分
	 */
	private void updateMemberScore(long memberId, long standByScore, long enableScore, long drawcashScore)
			throws Exception {
		if (scoreDetailsMapper.updateMemberScore(memberId, standByScore, enableScore, drawcashScore) != 1) {
			throw new Exception("更新用户积分失败!");
		}
	}

	/**
	 * 记入总流水
	 */
	private int insertMemberTrade(PhScoreTotalTrade totalTrade) throws Exception {
		int ret = scoreDetailsMapper.insertMemberTrade(totalTrade);
		if (ret != 1l) {
			throw new Exception("记入总流水失败!");
		}

		return ret;
	}

	/**
	 * 记入收入流水
	 */
	private void insertIncomeTrade(long memberId, long score, int transCode, long setId, String orderNo,
			long handingCharge) throws Exception {
		if (scoreDetailsMapper.insertIncomeTrade(memberId, transCode, score, setId, orderNo, handingCharge) != 1) {
			throw new Exception("记入收入流水失败!");
		}

	}

	/**
	 * 记入支出流水
	 */
	private void insertCostTrade(long memberId, long score, int transCode, long setId, String orderNo,
			long handingCharge) throws Exception {
		if (scoreDetailsMapper.insertCostTrade(memberId, transCode, score, setId, orderNo, handingCharge) != 1) {
			throw new Exception("记入支出流水失败!");
		}
	}

	// 统计会员线上订单金额
	public long getOnLineOrderMoney(long memberId) {
		return scoreDetailsMapper.countOnLineMoney(memberId);
	}

	// 统计会员线下订单金额
	public long getUnLineOrderMoney(long memberId) {
		return scoreDetailsMapper.countUnLineMoney(memberId);
	}

	/**
	 * Title: getMemberScore Description: 获取用户积分
	 *
	 * @param memberId
	 * @return
	 * @throws Exception
	 * @author 王强
	 * @date 2017年3月22日 下午5:45:50
	 * @see com.ph.shopping.facade.score.service.IScoreService#getMemberScore(long)
	 */
	@Override
	public MemberScoreVO2 getMemberScore(long memberId) {
		return scoreDetailsMapper.getScore(memberId);
	}

	@Override
	public List<MemberScoreVO1> getMemberScores() {
		return scoreDetailsMapper.getMemberScores();
	}

	@Override
	public Result getMemberScorePage(PageBean page, ScorePageDTO scorePageRequest) {
		try {
			logger.info("商城个人积分流水分页查询入参，page={}，scorePageRequest={}", JSON.toJSONString(page),
					JSON.toJSONString(scorePageRequest));
			PageHelper.startPage(page.getPageNum(), page.getPageSize());
			List<MemberScoreShopVO> list = scoreDetailsMapper.getMemberScorePage(scorePageRequest.getMemberId());
			PageInfo<MemberScoreShopVO> pageInfo = new PageInfo<>(list);
			if (CollectionUtils.isNotEmpty(pageInfo.getList())) {
				for (MemberScoreShopVO vo : pageInfo.getList()) {
					vo.setFee(MoneyTransUtil.formatBigDecimalByDivisFive(vo.getFee()));
					vo.setScore(MoneyTransUtil.formatBigDecimalByDivisFive(vo.getScore()));
					//应需求修改2017-7-17
					vo.setRemark("");
				}
			}
			return ResultUtil.getResult(RespCode.Code.SUCCESS, pageInfo.getList(), pageInfo.getTotal());
		} catch (Exception e) {
			logger.error("商城个人积分流水分页查询异常，e={}", e);
			throw new RuntimeException("个人积分流水查询异常");
		}
	}

	/**
	 * Title: getMemberCostScore Description: 会员相关信息
	 *
	 * @return
	 * @author 王强
	 * @date 2017年4月24日 下午4:17:33
	 * @see com.ph.shopping.facade.score.service.IScoreService#getMemberCostScore()
	 */
	@Override
	public List<MemberCostScoreVO> getMemberCostScore() {
		return scoreDetailsMapper.getMemberCostScore();
	}

	/**
	 * @param orderNo
	 * @return
	 * @throws Exception
	 * @Title: updOnLineOrderIsBackScore
	 * @Description: 更新线上订单状态
	 * @author 王强
	 * @date 2017年4月24日 下午4:18:31
	 */
	public int updOnLineOrderIsBackScore(String orderNo) throws Exception {
		int res = scoreDetailsMapper.updateOrderIsBackScore1(orderNo);

		return res;
	}

	/**
	 * @param orderNo
	 * @return
	 * @throws Exception
	 * @Title: updUnLineOrderIsBackScore
	 * @Description: 更新线下订单状态
	 * @author 王强
	 * @date 2017年4月24日 下午4:18:50
	 */
	public int updUnLineOrderIsBackScore(String orderNo) throws Exception {
		int res = scoreDetailsMapper.updateOrderIsBackScore2(orderNo);
		return res;
	}

	/**
	 * @param memberId
	 * @param codeEnum
	 * @param score
	 * @return
	 * @throws Exception
	 * @Title: returnMemberScore
	 * @Description: 返回积分(待返积分) 批发商分润专用
	 * @author 王强
	 * @date 2017年3月22日 下午4:16:28
	 */
	public long returnMemberScoreHunterTrade(long memberId, TransCodeEnum codeEnum, long score, String orderNo,
			long handingCharge) {
		try {
			updateMemberScore(memberId, score, 0, 0);

			long setId = getSetId(memberId, score, orderNo, codeEnum.getCode(), 0l);

			insertIncomeTrade(memberId, score, codeEnum.getCode(), setId, orderNo, handingCharge);

			return setId;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ScoreException(ScoreExceptionEnum.SCORE_EXCEPTION);
		}

	}

	/**
	 * 
	 * @Title: returnMemberStandByScoreTrade
	 * @Description: 返回待用积分
	 * @author 王强
	 * @date 2017年7月5日 下午5:57:43
	 * @param memberId
	 * @param codeEnum
	 * @param score
	 * @param orderNo
	 * @param handingCharge
	 * @return
	 * @throws Exception
	 */
	public long returnMemberStandByScoreTrade(long memberId, TransCodeEnum codeEnum, long score, String orderNo,
			long handingCharge) throws Exception {

		updateMemberScore(memberId, score, 0, 0);

		long setId = getSetId(memberId, score, orderNo, codeEnum.getCode(), handingCharge);

		insertIncomeTrade(memberId, score, codeEnum.getCode(), setId, orderNo, handingCharge);

		return setId;
	}

	@Override
	public void updateMemberScoreByReward(Long memberId, long rewardScore) {
		int num = scoreDetailsMapper.updateMemberScoreByReward(memberId,rewardScore);
		if(num != 1){
			logger.error("修改会员奖励积分失败");
			throw new ScoreException(ScoreExceptionEnum.UPDATE_REWARDSCORE);
		}
	}
	/**
	 * 
	 * @Title: retuenMemberRewardScore   
	 * @Description: 返回会员奖励积分   
	 * @param: @param memberId
	 * @param: @param codeEnum
	 * @param: @param score
	 * @param: @param orderNo
	 * @param: @return      
	 * @return: long
	 * @author：李杰      
	 * @throws
	 */
	public long retuenMemberRewardScore(long memberId, TransCodeEnum codeEnum, long score, String orderNo){
		try {
			updateMemberScoreByReward(memberId, score);

			long setId = getSetId(memberId, score, orderNo, codeEnum.getCode(), 0l);

			insertIncomeTrade(memberId, score, codeEnum.getCode(), setId, orderNo, 0);

			return setId;
		} catch (Exception e) {
			throw new ScoreException(ScoreExceptionEnum.SCORE_EXCEPTION);
		}
	}

	@Override
	public Result getMemberSingleScoreInfo(QuerySingleScoreInfoDTO dto, PageBean page) {
		logger.info("查询相关积分数据参数：QuerySingleScoreInfoDTO = {},PageBean={} ", JSON.toJSONString(dto),
				JSON.toJSONString(page));
		Result result = ResultUtil.getResult(RespCode.Code.REQUEST_DATA_ERROR);
		if (null != dto) {
			String errorStr = dto.validateForm();
			if (StringUtils.isNotBlank(errorStr)) {
				result.setMessage(errorStr);
				return result;
			}
			final ScoreTypeEnum scoreType = dto.getScoreType();
			final Long memberId = dto.getMemberId();
			// 查询会员积分详情数据
			MemberScoreVO2 memberScore = scoreDetailsMapper.getScore(memberId);
			if (null == memberScore) {
				logger.warn("根据会员ID" + memberId + "查询会员相关积分失败");
				throw new RuntimeException("根据会员ID" + memberId + "查询会员相关积分失败");
			}
			// 返回结果实体
			MemberSingleScoreVO vo = null;
			// 查询编码，可用于统计总数（累计相关信息）
			List<Integer> codes = null;
			// 用于只统计记录
			List<Integer> recordCodes = null;
			switch (scoreType) {
			case CASH_BALANCE:
				codes = new ArrayList<>();
				codes.add(1004);// 统计累计可用积分数据
				codes.add(1036);// 会员提现失败返回可用积分
				// 判断该会员是否是推广师
				final boolean isPromotion = isPromotion(memberId);
				if (isPromotion) {
					codes.add(1051);// 线下订单分润增加推广师可用积分
				} else {
					codes.add(1063);// 线下订单分润增加会员分润积分
				}
				recordCodes = new ArrayList<>();
				recordCodes.add(1002);// 提现消费积分
				recordCodes.add(1009);// 线下消费积分
				// 得到可用积分
				vo = getMemberSingleScore(memberId, page, codes, dto.isQueryList(), recordCodes);
				if (null != vo && !dto.isQueryList()) {
					// 得到会员分润积分
					long memberProfitScore = memberScore.getMemberProfitScore();
					String mprofit = MoneyTransUtil
							.formatBigDecimalByDivisFiveStr(new BigDecimal(Long.toString(memberProfitScore)));
					BigDecimal accbd = new BigDecimal(vo.getAccumulativeScore()).add(new BigDecimal(mprofit));
					// 设置累计积分（累计积分+分润积分）
					vo.setAccumulativeScore(MoneyTransUtil.formatObj(accbd));
					// 设置现金余额（会员可用积分+会员分润积分）
					vo.setScore(MoneyTransUtil.formatBigDecimalByDivisFiveStr(
							new BigDecimal(memberScore.getEnablescore() + memberScore.getMemberProfitScore())));
				}
				break;
			case SCORE_BALANCE:
				codes = new ArrayList<>();
				codes.add(1001);// 统计累计待用积分数据
				codes.add(1019);// 返回待用积分
				recordCodes = new ArrayList<>();
				recordCodes.add(1005);// 消耗待用积分
				vo = getMemberSingleScore(memberId, page, codes, dto.isQueryList(), recordCodes);
				// 设置待用积分
				if (null != vo && !dto.isQueryList()) {
					vo.setScore(MoneyTransUtil
							.formatBigDecimalByDivisFiveStr(new BigDecimal(memberScore.getStandbyscore())));
				}
				break;
			case DRWN_BALANCE:
				vo = new MemberSingleScoreVO();
				if(!dto.isQueryList()){
					// 可提现积分（可用积分）
					vo.setDrawnScore(
							MoneyTransUtil.formatBigDecimalByDivisFiveStr(new BigDecimal(memberScore.getEnablescore())));
					// 累计提现积分
					vo.setAccumulativeDraw(
							MoneyTransUtil.formatBigDecimalByDivisFiveStr(new BigDecimal(memberScore.getDrawcashScore())));
				}
				// 提现记录
				codes = new ArrayList<>();
				codes.add(1002);// 查询提现记录（此处只需要查询列表）
				codes.add(1036);// 会员提现失败返回可用积分
				MemberSingleScoreVO vs = getMemberSingleScore(memberId, page, codes, true, recordCodes);
				if (null != vs) {
					vo.setScoreRecord(vs.getScoreRecord());
				}
				break;
			case MEMBER_REWARD:
				codes = new ArrayList<>();
				codes.add(1064);// 统计会员推广会员奖励积分
				vo = getMemberSingleScore(memberId, page, codes, dto.isQueryList(), recordCodes);
				// 设置推广会员奖励积分
				if (null != vo && !dto.isQueryList()) {
					vo.setScore(MoneyTransUtil
							.formatBigDecimalByDivisFiveStr(new BigDecimal(memberScore.getMemberRewardScore())));
				}
				break;
			case MERCHANT_REWARD:
				codes = new ArrayList<>();
				codes.add(1065);// 统计会员推广商户奖励积分
				vo = getMemberSingleScore(memberId, page, codes, dto.isQueryList(), recordCodes);
				// 设置推广会员奖励积分
				if (null != vo && !dto.isQueryList()) {
					vo.setScore(MoneyTransUtil
							.formatBigDecimalByDivisFiveStr(new BigDecimal(memberScore.getMerchantRewardScore())));
				}
				break;
			default:
				break;
			}
			result.setData(vo);
			ResultUtil.setResult(result, RespCode.Code.SUCCESS);
		}
		return result;
	}
	/**
	 * 
	 * @Title: isPromotion   
	 * @Description: 判断是否是推广师   
	 * @param: @param memberId
	 * @param: @return      
	 * @return: boolean
	 * @author：李杰      
	 * @throws
	 */
	private boolean isPromotion(Long memberId){
		
		return scoreDetailsMapper.isPromotionByMemberId(memberId) > 0;
	}
	/**
	 * 
	 * @Title: getMemberSingleScore   
	 * @Description: 查询积分信息  
	 * @param: @param memberId 会员ID
	 * @param: @param page 分页信息
	 * @param: @param codes 统计编码
	 * @param: @param isList // 是否只查询列表信息
	 * @param: @param recordCodes // 统计记录编码
	 * @param: @return      
	 * @return: MemberSingleScoreVO
	 * @author：李杰      
	 * @throws
	 */
	private MemberSingleScoreVO getMemberSingleScore(final Long memberId, PageBean page, List<Integer> codes,
			boolean isList, List<Integer> recordCodes) {
		MemberSingleScoreVO vo = new MemberSingleScoreVO();
		Map<String, Object> map = new HashMap<String, Object>();
		if (codes == null) {
			codes = new ArrayList<>();
		}
		map.put("codes", codes);
		map.put("memberId", memberId);
		// 是否只查询列表
		if (!isList) {
			// 查询累计到账积分
			Long accScore = scoreDetailsMapper.getAccumulativeScore(map);
			vo.setAccumulativeScore(MoneyTransUtil.formatBigDecimalByDivisFiveStr(new BigDecimal(accScore)));
			// 查询当日到账积分
			map.put("startTime", DateUtil.getStartTime());
			map.put("endTime", DateUtil.getEndTime());
			Long todayScore = scoreDetailsMapper.getTodayScore(map);
			vo.setTodayScore(MoneyTransUtil.formatBigDecimalByDivisFiveStr(new BigDecimal(todayScore)));
		}
		if (null != recordCodes && !recordCodes.isEmpty()) {
			codes.addAll(recordCodes);// 添加查询记录code
		}
		// 查询可用积分流水
		PageHelper.startPage(page.getPageNum(), page.getPageSize());
		List<MemberScoreRecordVO> scoreRecords = scoreDetailsMapper.selectScoreRecordsByList(map);
		PageInfo<MemberScoreRecordVO> pages = new PageInfo<MemberScoreRecordVO>(scoreRecords);
		List<MemberScoreRecordVO> list = pages.getList();
		vo.setScoreRecord(formatList(list));
		return vo;
	}
	
	private List<MemberScoreRecordVO> formatList(List<MemberScoreRecordVO> list) {
		if (null != list && !list.isEmpty()) {
			for (MemberScoreRecordVO vo : list) {
				String feeStr = MoneyTransUtil.formatBigDecimalByDivisFiveStr(new BigDecimal(vo.getFee()));
				if (feeStr.indexOf("-") == -1) {
					vo.setFee("-" + feeStr);
				} else {
					vo.setFee(feeStr);
				}
				vo.setScore(
						getFormatBySign(MoneyTransUtil.formatBigDecimalByDivisFiveStr(new BigDecimal(vo.getScore()))));
			}
		}
		return list;
	}
	
	public static String getFormatBySign(String str){
		if(StringUtils.isNotBlank(str)){
			if(str.indexOf("-") == -1){
				str = "+" + str;
			}
		}
		return str;
	}
	/**
	 * @throws Exception 
	 * 
	 * @Title: getAndupdateMemberScore   
	 * @Description: 积分改造--获取并更新会员积分 
	 * @param: @param memberId 会员ID
	 * @param: @return      
	 * @return: 
	 * @author：李治桦   
	 * @throws
	 */
	@SuppressWarnings("unused")
	@Override
	@Transactional
	public Result getAndupdateMemberScore(Long memberId, Long orderMoney, String orderNo) throws Exception {
		Result result = ResultUtil.getResult( RespCode.Code.SUCCESS );
		//获取会员积分
		MemberScoreVO2 memberScore=getMemberScore(memberId);
		if(memberScore == null){
			logger.error("没有查询到用户的积分");
			return  ResultUtil.getResult(OrderResultEnum.NO_MEMBERSCORCE);
		}
		if(memberScore.getEnablescore() < orderMoney){//积分不足
			return  ResultUtil.getResult(OrderResultEnum.NOENOUGH_MEMBER_SCORE);
		}
		//这里跟新用户的可用积分减少  待会积分增加(改为待用积分不增加了 积分模块统一处理反积分)
		long updateMemberScore = memberScoreTrade( memberId,
				TransCodeEnum.MEMBER_UNLINE_ORDER,orderMoney,orderNo,0);
		System.out.println(updateMemberScore);
		if (updateMemberScore<0){
			result.setMessage( "操作失败" );
			logger.info("更新会员积分和记入总流水失败");
			throw new Exception("更新会员积分和记入总流水失败");
		}
		if(memberScore != null){
			return ResultUtil.getResult(RespCode.Code.SUCCESS,memberScore);
		}else{
			return ResultUtil.getResult(RespCode.Code.FAIL);
		}
		
	}

	/**
	 *
	 * @param id
	 * @param transCode
	 * 积分明细
	 */
	@Override
	public Result getShareMerchantScoreDetail(long id,int transCode,int pageNo,int pageSize) {
		
		if(transCode != TransCodeEnum.SHARE_MEMBER_CASH_SCORE.getCode() &&
				transCode != TransCodeEnum.SHARE_MERCHANT_CASH_SCORE.getCode() &&
				transCode != TransCodeEnum.ORDER_STORE_MANAGER_PROFIT_TRANSCODE.getCode()){
			return ResultUtil.setResult(false,"参数错误");
		}
		
		if(pageNo == 0){
			pageNo = 1;
		}
		if(pageSize == 0){
			pageSize = 10;
		}
		//会员推广
		if (transCode==1066){
           transCode = TransCodeEnum.ORDER_SHARE_MEMBER_PROFIT_TRANSCODE.getCode();
        //商户
		} else if (transCode == 1067) {
			transCode = TransCodeEnum.ORDER_SHARE_MERCHANT_PROFIT_TRANSCODE.getCode();
		} else {
			transCode = TransCodeEnum.ORDER_STORE_MANAGER_PROFIT_TRANSCODE.getCode();
		}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id", id);
		map.put("transCode", transCode);
		logger.info("会员/商户推广参数:{}",JSON.toJSON(map));
		// 当天到账金额
		//Long dayCashScore = scoreDetailsMapper.dayCashScore(map);
		Long dayCashScore = scoreDetailsMapper.getCurrentDayScore(map);
		dayCashScore = Objects.isNull(dayCashScore)?0L:dayCashScore;
		MemberScoreVO2 memberScore = scoreDetailsMapper.getScore(id);
		int index = (pageNo - 1) * pageSize;
		RowBounds rowBounds = new RowBounds(index,pageSize);
		// 积分明细
		List<Map<String,Object>> list = scoreDetailsMapper.getScoreDetail(id,transCode,rowBounds);
		if(Objects.nonNull(list)){
			for(Map m:list){
				String score = DoubleUtils.formatRound(MoneyTransUtil.transBigdecimalToDouble(new BigDecimal(m.get("score").toString())), 2);
				m.put("tradeTime",DateUtil.dateToStr((Date) m.get("createTime"),DateUtil.DATE_TIME_LINE));
				m.put("score",score );
				m.put("totelScore",DoubleUtils.formatRound(MoneyTransUtil.transBigdecimalToDouble(new BigDecimal(m.get("totelScore").toString())), 2));
			    String code = m.get("transCode").toString();
				if (code.equals(String.valueOf(TransCodeEnum.ORDER_SHARE_MEMBER_PROFIT_TRANSCODE.getCode())) && Double.valueOf(score).doubleValue() >=0){
					m.put("desc","推广会员奖励");
				} else if (code.equals(String.valueOf(TransCodeEnum.ORDER_SHARE_MERCHANT_PROFIT_TRANSCODE.getCode())) && Double.valueOf(score).doubleValue() >=0) {
			    	m.put("desc","推广商户奖励");
				} else if (code.equals(String.valueOf(TransCodeEnum.ORDER_STORE_MANAGER_PROFIT_TRANSCODE.getCode())) && Double.valueOf(score).doubleValue()>=0) {
			    	m.put("desc","订单分润");
				}else if (code.equals(String.valueOf(TransCodeEnum.ORDER_SHARE_MEMBER_WITHDRAW_FAIL_TRANSCODE.getCode()))||
						code.equals(String.valueOf(TransCodeEnum.ORDER_SHARE_MERCHANT_WITHDRAW_FAIL_TRANSCODE.getCode()))||
						code.equals(String.valueOf(TransCodeEnum.ORDER_STORE_MANAGER_WITHDRAW_FAIL_TRANSCODE.getCode()))) {
					m.put("desc", "提现失败");
				}else {
			    	m.put("desc","提现");
				}

			}
		}
		map.clear();
		map.put("today",BigDecimal.valueOf(dayCashScore).divide(BigDecimal.valueOf(10000)).setScale(2, RoundingMode.HALF_UP).toString());
		map.put("datas",list);
		if(memberScore != null) {
			if (transCode == TransCodeEnum.ORDER_SHARE_MEMBER_PROFIT_TRANSCODE.getCode()) {
				map.put("enable", DoubleUtils.formatRound(MoneyTransUtil.transBigdecimalToDouble(new BigDecimal(memberScore.getShareMemberScore())),2));
				map.put("total", DoubleUtils.formatRound(MoneyTransUtil.transBigdecimalToDouble(new BigDecimal(memberScore.getTotleShareMemberScore())),2));
			}else if (transCode == TransCodeEnum.ORDER_SHARE_MERCHANT_PROFIT_TRANSCODE.getCode()){
				map.put("enable",DoubleUtils.formatRound(MoneyTransUtil.transBigdecimalToDouble(new BigDecimal(memberScore.getShareMerchantScore())),2));
				map.put("total", DoubleUtils.formatRound(MoneyTransUtil.transBigdecimalToDouble(new BigDecimal(memberScore.getTotleShareMerchantScore())),2));
			}else {
				map.put("enable",DoubleUtils.formatRound(MoneyTransUtil.transBigdecimalToDouble(new BigDecimal(memberScore.getStoreManagerScore())),2));
				map.put("total", DoubleUtils.formatRound(MoneyTransUtil.transBigdecimalToDouble(new BigDecimal(memberScore.getTotalStoreManagerScore())),2));
			}
			return ResultUtil.getResult(RespCode.Code.SUCCESS,map,1L);

		}
		map.put("enable", 0);
		map.put("total", 0);
		return ResultUtil.setResult(false,"暂无数据");
	}

	@Override
	public String getToken(Long id) {
		
		return scoreDetailsMapper.getToken(id);
	}


}
