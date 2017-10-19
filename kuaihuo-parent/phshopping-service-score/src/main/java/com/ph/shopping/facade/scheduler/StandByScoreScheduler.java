package com.ph.shopping.facade.scheduler;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ph.shopping.common.core.customenum.TransCodeEnum;
import com.ph.shopping.facade.score.service.IScoreService;
import com.ph.shopping.facade.score.vo.MemberCostScoreVO;

@Component
public class StandByScoreScheduler {
    // 创建日志
    protected Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private IScoreService iScoreService;

	// 返回待用积分每天凌晨1点 测试为每5分钟执行一次
//    @Scheduled(cron = "0 */3 * * * ?")
	@Scheduled(cron = "0 0 1 * * ?")  
    public void returnStandByScore() {
        List<MemberCostScoreVO> list = iScoreService.getMemberCostScore();
        if (list != null && list.size() != 0) {
			try {
				for (MemberCostScoreVO costScore : list) {
					iScoreService.memberScoreTrade(costScore.getMemberId(), TransCodeEnum.MEMBER_BACK_SCORE,
							costScore.getPayMoney(), costScore.getOrderNo(), 0l);
					logger.info("会员返回待用积分成功!");
				}
			} catch (Exception e) {
				logger.error("返回待用积分失败!");
				e.printStackTrace();
			}
		} else {
			logger.info("暂无会员需返回待用积分!");
		}
	}
}
