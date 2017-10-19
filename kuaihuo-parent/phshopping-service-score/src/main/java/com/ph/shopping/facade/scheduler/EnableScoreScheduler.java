package com.ph.shopping.facade.scheduler;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ph.shopping.common.core.customenum.TransCodeEnum;
import com.ph.shopping.facade.score.service.IScoreService;
import com.ph.shopping.facade.score.vo.MemberScoreVO1;

@Component
public class EnableScoreScheduler {
    // 创建日志
    protected Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private IScoreService iScoreService;

	// 返回可用积分每天凌晨3点执行
//    @Scheduled(cron = "0 */5 * * * ?")
	@Scheduled(cron = "0 0 3 * * ?")
    public void returnEnableScore() {
        List<MemberScoreVO1> memberScores = iScoreService.getMemberScores();
		if (memberScores != null && memberScores.size() > 0) {
			for (MemberScoreVO1 vo1 : memberScores) {
				try {
					if (vo1.getStandbyscore() > 0) {
						iScoreService.memberScoreTrade(vo1.getMemberId(), TransCodeEnum.MEMBER_COST_STANDBYSCORE,
								vo1.getStandbyscore(), "", 0l);
					}
				} catch (Exception e) {
					logger.error(vo1.getMemberId() + ": 返回积分失败!");
					e.printStackTrace();
				}
			}
		} else {
			logger.info("暂无会员需返回可用积分!");
		}
	}
}
