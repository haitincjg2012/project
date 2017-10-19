package com.ph.shopping.facade.pay.service.impl.score;

import com.alibaba.fastjson.JSON;
import com.ph.shopping.common.core.customenum.TransCodeEnum;
import com.ph.shopping.facade.mapper.MemberDrawCashRecordMapper;
import com.ph.shopping.facade.mapper.ScoreMapper;
import com.ph.shopping.facade.pay.exception.CashException;
import com.ph.shopping.facade.profit.vo.BackMemberDrawCashVO;
import com.ph.shopping.facade.score.entity.PhScoreTotalTrade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 积分提现service
 *
 * @author 郑朋
 * @create 2017/7/11
 **/
@Service
public class ScoreService {

    private static final Logger logger = LoggerFactory.getLogger(ScoreService.class);

    @Autowired
    ScoreMapper scoreMapper;

    @Autowired
    MemberDrawCashRecordMapper memberDrawCashRecordMapper;


    /**
     * @methodname memberDrawCashTrade 的描述：积分提现
     * @param memberId
     * @param codeEnum
     * @param score
     * @param orderNo
     * @param handingCharge
     * @return long
     * @author 郑朋
     * @create 2017/7/11
     */
    public long memberDrawCashTrade(long memberId, TransCodeEnum codeEnum, long score, String orderNo, long handingCharge) {
        score = codeEnum.getMark() * score;
        handingCharge = codeEnum.getMark() * handingCharge;
        long scoreEx = score + handingCharge;
        updateMemberScore(memberId, 0, scoreEx, 0);
        long setId = getSetId(memberId, score, orderNo, codeEnum.getCode(), handingCharge);
        insertCostTrade(memberId, score, codeEnum.getCode(), setId, orderNo, handingCharge);
        return setId;
    }

    /**
     * 更新用户积分
     */
    private void updateMemberScore(long memberId, long standByScore, long enableScore, long drawcashScore){
        if (scoreMapper.updateMemberScore(memberId, standByScore, enableScore, drawcashScore) != 1) {
            throw new CashException("更新用户积分失败!");
        }
    }


    /**
     * @methodname getSetId 的描述：获取setId
     * @param memberId
     * @param score
     * @param orderNo
     * @param transCode
     * @param handingCharge
     * @return long
     * @author 郑朋
     * @create 2017/7/11
     */
    private long getSetId(long memberId, long score, String orderNo, int transCode, long handingCharge) {
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
     * 记入总流水
     */
    private int insertMemberTrade(PhScoreTotalTrade totalTrade) {
        int ret = scoreMapper.insertMemberTrade(totalTrade);
        if (ret != 1) {
            throw new CashException("记入总流水失败!");
        }
        return ret;
    }


    /**
     * 记入支出流水
     */
    private void insertCostTrade(long memberId, long score, int transCode, long setId, String orderNo,long handingCharge){
        if (scoreMapper.insertCostTrade(memberId, transCode, score, setId, orderNo, handingCharge) != 1) {
            throw new CashException("记入支出流水失败!");
        }
    }

    public void updStatus(int status, String orderNo, String tradeState) {
        // 判断提现记录状态是否成功
        logger.info("------------会员提现状态status:" + status + ",orderNo:" + orderNo + ",tradeState:" + tradeState);
        int total = memberDrawCashRecordMapper.countDrawCashTotal(orderNo);
        logger.info("校验数据total：" + total);
        if (total == 1) {
            logger.info("-----------------------已支付订单无需再支付---------------------");
            return;
        }
        int res = memberDrawCashRecordMapper.updateStatus(status, orderNo, tradeState);
        if (res <= 0) {
            throw new CashException("修改批发商会员提现状态失败");
        }
        if (res == 1 && status == 1) {
            BackMemberDrawCashVO backMemberDrawCashVO = memberDrawCashRecordMapper.getBackDrawCashData(orderNo);
            logger.info("增加累计提现积分入参:backMemberDrawCashVO=" + JSON.toJSONString(backMemberDrawCashVO));
            memberDrawCashAddTrade(backMemberDrawCashVO.getMemberId(), TransCodeEnum.MEMBER_DRAWCASHADD,backMemberDrawCashVO.getScore(), orderNo, 0l);
        }
    }

    private long memberDrawCashAddTrade(long memberId, TransCodeEnum codeEnum, long score, String orderNo, long handingCharge) {
        score = codeEnum.getMark() * score;
        handingCharge = codeEnum.getMark() * handingCharge;
        updateMemberScore(memberId, 0, 0, score);
        long setId = getSetId(memberId, score, orderNo, codeEnum.getCode(), 0l);
        insertIncomeTrade(memberId, score, codeEnum.getCode(), setId, orderNo, handingCharge);
        return setId;
    }

    /**
     * 记入收入流水
     */
    private void insertIncomeTrade(long memberId, long score, int transCode, long setId, String orderNo,long handingCharge){
        if (scoreMapper.insertIncomeTrade(memberId, transCode, score, setId, orderNo, handingCharge) != 1) {
            throw new CashException("记入收入流水失败!");
        }
    }


    public void backMemberScore(String orderNo) {
        logger.info("会员提现失败返回可用积分:" + orderNo);
        BackMemberDrawCashVO backMemberDrawCashVO = memberDrawCashRecordMapper.getBackDrawCashData(orderNo);
        memberDrawCashTrade(backMemberDrawCashVO.getMemberId(), TransCodeEnum.MEMBER_DRAWCASH_FAIL,backMemberDrawCashVO.getScore(), orderNo, 0l);
    }
}
