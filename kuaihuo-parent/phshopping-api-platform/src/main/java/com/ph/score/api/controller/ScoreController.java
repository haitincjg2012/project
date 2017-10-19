package com.ph.score.api.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ph.base.BaseController;
import com.ph.shopping.common.util.transform.MoneyTransUtil;
import com.ph.shopping.facade.score.request.ScoreDTO;
import com.ph.shopping.facade.score.service.IScoreService;
import com.ph.shopping.facade.score.vo.MemberScoreVO;
import com.ph.shopping.facade.score.vo.MemberScoreVO2;

@Controller
@RequestMapping("web/score")
public class ScoreController extends BaseController {

	// 创建日志
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@Reference(version = "1.0.0")
	private IScoreService iScoreService;

	/**
	 * 
	 * @Title: memberScoreTrade
	 * @Description: 会员积分流水
	 * @author WQiang
	 * @date 2017年3月18日 下午1:26:16
	 * @param scoreRequest
	 * @return
	 * 
	 */
	@RequestMapping("socretrade")
	@ResponseBody
	public long memberScoreTrade(ScoreDTO scoreRequest) {
		try {
			return iScoreService.memberScoreTrade(scoreRequest.getMemberId(), scoreRequest.getCodeEnum(),
					MoneyTransUtil.transMultiDouble(scoreRequest.getScore()), scoreRequest.getOrderNo(),
					MoneyTransUtil.transMultiDouble(scoreRequest.getHandingCharge()));

		} catch (Exception e) {
			logger.error("交易失败!");
			e.printStackTrace();
			return 0L;
		}
	}

	/**
	 * 
	 * @Title: getMemberScore
	 * @Description: 获取用户待用、可用、已提现积分
	 * @author WQiang
	 * @date 2017年3月22日 下午5:54:23
	 * @param memberId
	 * @return
	 */
	@RequestMapping("getmemberscore")
	@ResponseBody
	public MemberScoreVO getMemberScore(long memberId) {
		try {
			MemberScoreVO2 scoreVo2 = iScoreService.getMemberScore(memberId);
			MemberScoreVO scoreVo = new MemberScoreVO();
			scoreVo.setStandbyscore1(MoneyTransUtil.transDivisDouble(scoreVo2.getStandbyscore()));
			scoreVo.setEnableScore1(MoneyTransUtil.transDivisDouble(scoreVo2.getStandbyscore()));
			scoreVo.setDrawcashScore1(MoneyTransUtil.transDivisDouble(scoreVo2.getDrawcashScore()));
			return scoreVo;
		} catch (Exception e) {
			logger.error("获取积分异常!");
			e.printStackTrace();
			return null;
		}
	}

}
