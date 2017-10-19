package com.ph.shopping.facade.accountsettle.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ph.shopping.common.core.cache.redis.impl.RedisCacheService;
import com.ph.shopping.common.core.customenum.MessageTemplateEnum;
import com.ph.shopping.common.core.customenum.PushTypeEnum;
import com.ph.shopping.common.core.customenum.TransCodeEnum;
import com.ph.shopping.common.util.container.ContainerUtil;
import com.ph.shopping.common.util.core.RespCode;
import com.ph.shopping.common.util.core.ResultUtil;
import com.ph.shopping.common.util.date.DateUtil;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.common.util.string.StringHelper;
import com.ph.shopping.common.util.transform.MoneyTransUtil;
import com.ph.shopping.facade.mapper.IMemberDrawCashMapper;
import com.ph.shopping.facade.mapper.IScoreDetailMapper;
import com.ph.shopping.facade.member.dto.message.JPushSendDTO;
import com.ph.shopping.facade.member.dto.message.JPushUserDTO;
import com.ph.shopping.facade.member.entity.Member;
import com.ph.shopping.facade.member.menum.message.PushMessageEnum;
import com.ph.shopping.facade.member.service.IMemberBankCardBindService;
import com.ph.shopping.facade.member.service.IMemberService;
import com.ph.shopping.facade.member.service.IMessageService;
import com.ph.shopping.facade.member.vo.MemberBankCardBindInfoVO;
import com.ph.shopping.facade.member.vo.MessageVO;
import com.ph.shopping.facade.pay.dto.DefrayDTO;
import com.ph.shopping.facade.pay.service.ICashService;
import com.ph.shopping.facade.pay.utils.union.UniqueUtils;
import com.ph.shopping.facade.permission.constant.RoleIDEnum;
import com.ph.shopping.facade.profit.dto.AuditDTO;
import com.ph.shopping.facade.profit.dto.MemberDrawCashDTO;
import com.ph.shopping.facade.profit.exception.ProfitException;
import com.ph.shopping.facade.profit.exception.ProfitExceptionEnum;
import com.ph.shopping.facade.profit.service.IMemberDrawCashService;
import com.ph.shopping.facade.profit.vo.BackMemberDrawCashVO;
import com.ph.shopping.facade.profit.vo.DrawCashVO;
import com.ph.shopping.facade.profit.vo.MemberDrawCashDataVO;
import com.ph.shopping.facade.profit.vo.MemberDrawCashVO;
import com.ph.shopping.facade.score.service.IScoreService;
import com.ph.shopping.facade.score.vo.MemberScoreVO2;

/**
 * 
 * @ClassName: MemberDrawCashServiceImpl
 * @Description: 会员提现实现类
 * @author 王强
 * @date 2017年6月14日 上午9:21:25
 */
@Component
@Service(version = "1.0.0")
public class MemberDrawCashServiceImpl implements IMemberDrawCashService {

	private Logger logger = LoggerFactory.getLogger(MemberDrawCashServiceImpl.class);

	@Autowired
	private IMemberDrawCashMapper iMemberDrawCashMapper;

	@Autowired
	IScoreDetailMapper scoreDetailMapper;

	@Reference(version = "1.0.0")
	private ICashService iCashService;

	@Reference(version = "1.0.0")
	private IScoreService iScoreService;

	@Reference(version = "1.0.0")
	private IMessageService iMessageService;

	@Reference(version = "1.0.0")
	private IMemberService iMemberService;

	@Reference(version = "1.0.0")
	private IMemberBankCardBindService iMemberBankCardBindService;

	@Autowired
	private RedisCacheService<String, String> redisCashe;

	@Override
	public Result getMemberDrawCashes(MemberDrawCashDTO memberDrawCashDTO) {
		try {
			PageHelper.startPage(memberDrawCashDTO.getPageNum(), memberDrawCashDTO.getPageSize());
			List<MemberDrawCashVO> memberDrawCashVOs = (Page<MemberDrawCashVO>) iMemberDrawCashMapper
					.queryMemberDrawCashes(memberDrawCashDTO);
			PageInfo<MemberDrawCashVO> pageInfo = new PageInfo<MemberDrawCashVO>(memberDrawCashVOs);
			for (MemberDrawCashVO memberDrawCashVO : pageInfo.getList()) {
				memberDrawCashVO.setScore(
						MoneyTransUtil.stringFormat(MoneyTransUtil.transDivisDouble(memberDrawCashVO.getScore1())));
				memberDrawCashVO.setHandingCharge(MoneyTransUtil
						.stringFormat(MoneyTransUtil.transDivisDouble(memberDrawCashVO.getHandingCharge1())));
				// 平衡差
				memberDrawCashVO.setBalanceDifference("0.00");
			}

			return ResultUtil.getResult(RespCode.Code.SUCCESS, pageInfo.getList(), pageInfo.getTotal());
		} catch (Exception e) {
			logger.error("查询会员提现失败", e);
			return ResultUtil.getResult(RespCode.Code.FAIL, null, 0l);
		}
	}

	@Override
	public List<MemberDrawCashVO> getExportData(MemberDrawCashDTO memberDrawCashDTO) {
		List<MemberDrawCashVO> memberDrawCashVOs = iMemberDrawCashMapper.queryMemberDrawCashes(memberDrawCashDTO);
		for (MemberDrawCashVO memberDrawCashVO : memberDrawCashVOs) {
			memberDrawCashVO.setScore(
					MoneyTransUtil.stringFormat(MoneyTransUtil.transDivisDouble(memberDrawCashVO.getScore1())));
			memberDrawCashVO.setHandingCharge(
					MoneyTransUtil.stringFormat(MoneyTransUtil.transDivisDouble(memberDrawCashVO.getHandingCharge1())));
			memberDrawCashVO.setCreateTime1(
					DateUtil.dateToStr(memberDrawCashVO.getCreateTime(), DateUtil.DATETIME_FORMAR_STRING));
			memberDrawCashVO.setOperatorCheckTime1(
					DateUtil.dateToStr(memberDrawCashVO.getOperatorCheckTime(), DateUtil.DATETIME_FORMAR_STRING));
			memberDrawCashVO.setTreasurerCheckTime1(
					DateUtil.dateToStr(memberDrawCashVO.getTreasurerCheckTime(), DateUtil.DATETIME_FORMAR_STRING));
			// 平衡差
			memberDrawCashVO.setBalanceDifference("0.00");
		}
		return memberDrawCashVOs;
	}

	@Override
	@Transactional
	public Result doAuditOperator(AuditDTO auditDTO) {

		try {
			logger.info("会员提现审核操作入参:auditDTO = " + JSON.toJSONString(auditDTO));
			// 查询会员提现数据
			MemberDrawCashDataVO memberDrawCashVO = iMemberDrawCashMapper
					.getMemberDrawCashData(auditDTO.getDrawCashId());
			// 查询银卡名称
			Result result = iMemberBankCardBindService.selectBindMemberBankCardByUserId(memberDrawCashVO.getMemberId());
			MemberBankCardBindInfoVO bindVO = (MemberBankCardBindInfoVO) result.getData();

			logger.info("会员相关数据:memberDrawCashVO = " + JSON.toJSONString(memberDrawCashVO) + ",bindVO = "
					+ JSON.toJSONString(bindVO));
			// 审核状态为4并且角色ID为财务的才能发起提现请求
			if (auditDTO.getAuditState() == 4 && auditDTO.getAuditRgint() == RoleIDEnum.TREASURER.getId()) {
				String bathNo = UniqueUtils.getBathNo();
				DefrayDTO defrayDTO = new DefrayDTO(auditDTO.getDrawCashId().toString(), bathNo,
						memberDrawCashVO.getBankCardNo(), memberDrawCashVO.getMemberName(),
						memberDrawCashVO.getScore().toString(), bindVO.getBankName(), memberDrawCashVO.getOrderNo());
				logger.info("会员提现入参，defrayDTO={}", JSON.toJSONString(defrayDTO));
				Result result1 = iCashService.defray(defrayDTO);
				logger.info("会员提现返回值，result1={}", JSON.toJSONString(result1));
				if (result1 != null) {
					if (!result1.isSuccess()) {
						return result1;
					}
				}
			}

			int res = iMemberDrawCashMapper.auditOperator(auditDTO);
			if (res != 1) {
				return ResultUtil.getResult(ProfitExceptionEnum.AUDIT_FAIL);
			} else {
				logger.info("会员提现审核不通过操作:" + auditDTO.getType());
				if (auditDTO.getType() == 2) {
					backMemberScore(auditDTO.getOrderNo());
				}
				return ResultUtil.getResult(ProfitExceptionEnum.AUDIT_SUCCESS);
			}
		} catch (Exception e) {
			logger.error("审核异常", e);
			throw new ProfitException(ProfitExceptionEnum.AUDIT_EXCEPTION);
		}
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public Result updStatus(int status, String orderNo, String tradeState) {
		try {
			// 判断提现记录状态是否成功
			logger.info("------------会员提现状态status:" + status + ",orderNo:" + orderNo + ",tradeState:" + tradeState);
			int total = iMemberDrawCashMapper.countDrawCashTotal(orderNo);
			logger.info("校验数据total：" + total);
			if (total == 1) {
				logger.info("=====会员提现记录已处理=====");
				return ResultUtil.getResult(RespCode.Code.SUCCESS);
			}

			int res = iMemberDrawCashMapper.updateStatus(status, orderNo, tradeState);
			if (res == 1) {
				Object boj = redisCashe.get(orderNo);
				if (boj != null) {
					logger.info("=====进入会员提现缓存处理=====");
					return ResultUtil.getResult(RespCode.Code.SUCCESS);
				}
				if (status == 1) {
					try {
						logger.info("=====会员提现成功开始累加会员已提现积分=====");
						BackMemberDrawCashVO backMemberDrawCashVO = iMemberDrawCashMapper.getBackDrawCashData(orderNo);
						logger.info("增加会员累计提现积分入参:backMemberDrawCashVO=" + JSON.toJSONString(backMemberDrawCashVO));
						iScoreService.memberScoreTrade(backMemberDrawCashVO.getMemberId(),
								TransCodeEnum.MEMBER_DRAWCASHADD, backMemberDrawCashVO.getScore(), orderNo, 0l);
						// 添加缓存中
						redisCashe.set(orderNo, orderNo, 3L, TimeUnit.DAYS);
						logger.info("=====会员提现成功完成累加会员已提现积分=====");
						pushMessage(orderNo);
					} catch (Exception e) {
						logger.error("推送消息失败", e);
					}
					logger.info("-----------------------推送消息完成---------------------");
					return ResultUtil.getResult(RespCode.Code.SUCCESS);
				} else if(status == 2) {
					logger.info("=====会员提现失败开始返回会员积分=====");
					backMemberScore(orderNo);
					redisCashe.set(orderNo, orderNo, 3L, TimeUnit.DAYS);
					logger.info("=====会员提现失败完成返回会员积分=====");
					return ResultUtil.getResult(RespCode.Code.SUCCESS);
				} else {
					logger.info("=====会员提现无status值=====");
					return ResultUtil.getResult(RespCode.Code.FAIL);
				}
			} else {
				logger.info("=====更新会员提现订单状态失败=====");
				return ResultUtil.getResult(RespCode.Code.FAIL);
			}
		} catch (Exception e) {
			logger.error("更新会员提现订单状态异常", e);
			throw new ProfitException(ProfitExceptionEnum.UPDATE_DRAWCHAS_EXCEPTION);
		}

	}

	@Override
	public void backMemberScore(String orderNo) {
		try {
			logger.info("会员提现失败返回可用积分:" + orderNo);
			BackMemberDrawCashVO backMemberDrawCashVO = iMemberDrawCashMapper.getBackDrawCashData(orderNo);
			iScoreService.memberScoreTrade(backMemberDrawCashVO.getMemberId(), TransCodeEnum.MEMBER_DRAWCASH_FAIL,
					backMemberDrawCashVO.getScore(), orderNo, 0l);
		} catch (Exception e) {
			logger.error("返回用户余额失败", e);
			throw new ProfitException(ProfitExceptionEnum.BACK_SCORE_FAIL);
		}
	}

	/**
	 * 
	 * @Title: pushMessage
	 * @Description: 推送信息
	 * @author 王强
	 * @date 2017年6月27日 上午10:28:37
	 * @param memberId
	 * @param orderNo
	 * @param mercantName
	 */
	private void pushMessage(final String orderNo) throws Exception {
		logger.info("推送消息入参orderNo:" + orderNo);
		JPushSendDTO dto = null;
		DrawCashVO vo = iMemberDrawCashMapper.isHaveDrawCash(orderNo);
		if (vo == null) {
			logger.warn("根据提现单ID = " + orderNo + " 没有得到该单的消息 ");
			return;
		}

		logger.info("查询会员信息入参：" + vo.getMemberId());
		Result res = iMemberService.getMemberInfoById(vo.getMemberId());
		Member member = (Member) res.getData();
		if (member == null) {
			logger.warn("会员不存在 ");
			return;
		}

		if (member.getEquipmentId() == null) {
			logger.warn("非法设备不予推送信息");
			return;
		}

		// 得到消息模板ID
		final Long templateId = MessageTemplateEnum.MEMBER_DRAW_CASH.getTemplateId();
		// 得到消息模板信息
		MessageVO messageInfo = getMessageInfo(templateId);
		if (null == messageInfo) {
			logger.warn("根据模板ID = " + templateId + " 没有得到消息模板消息 ");
			return;
		}

		logger.info("查询会员积分入参：" + vo.getMemberId());
		MemberScoreVO2 scoreVO2 = iScoreService.getMemberScore(vo.getMemberId());

		// 存模板信息
		Map<String, String> map = ContainerUtil.map();
		// map.put("merchantName", mercantName);
		String money = MoneyTransUtil.stringFormat(MoneyTransUtil.transDivisDouble(vo.getScore()));
		String enableScore = MoneyTransUtil
				.formativeFiveLength(MoneyTransUtil.transDivisDouble(scoreVO2.getEnablescore()));
		map.put("money", money);
		map.put("brandSlogan", messageInfo.getBrandSlogan());
		map.put("time", new SimpleDateFormat("yyyy年MM月dd日 HH点").format(new Date()));
		map.put("enablescore", enableScore);
		// 转换信息
		logger.info("推送模板转换入参：" + messageInfo.getMessageTemplate() + ",map:" + JSON.toJSONString(map));
		final String messageContent = StringHelper.renderString(messageInfo.getMessageTemplate(), map);
		dto = new JPushSendDTO();
		dto.setCreaterId(vo.getMemberId());
		dto.setMessageContent(messageContent);
		dto.setSendType(PushMessageEnum.ALL.getCode());
		dto.setTemplateId(templateId);
		List<JPushUserDTO> users = ContainerUtil.aList();
		logger.info("新增一个推送DTO");
		JPushUserDTO jd = new JPushUserDTO();
		jd.setEquipmentId(member.getEquipmentId());
		jd.setUserId(vo.getMemberId());
		jd.setUserType(PushMessageEnum.MEMBER.getCode());
		users.add(jd);
		dto.setUsers(users);
		dto.setPushType(PushTypeEnum.PEER_TO_PEER);
		Result pushResult = iMessageService.pushMessage(dto);
		logger.info("线下订单消息推送返回数据 pushResult = {}", JSON.toJSONString(pushResult));
	}

	private MessageVO getMessageInfo(final Long templateId) {
		Result result = iMessageService.getMessageSendInfoByTmplateId(templateId);
		logger.info("查询消息模板返回数据：Result = {} ", JSON.toJSONString(result));
		if (result.isSuccess()) {
			Object data = result.getData();
			if (data instanceof MessageVO) {
				return (MessageVO) data;
			}
		}
		return null;
	}
}
