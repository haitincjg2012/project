package com.ph.shopping.facade.pay.service.impl;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.ph.shopping.common.core.customenum.PayTypeEnum;
import com.ph.shopping.common.util.order.OrderBizCode;
import com.ph.shopping.common.util.order.OrderUtil;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.common.util.transform.MoneyTransUtil;
import com.ph.shopping.facade.pay.dto.PayScoreDTO;
import com.ph.shopping.facade.pay.service.IScorePayService;
import com.ph.shopping.facade.pay.service.impl.order.OnlineOrderService;
import com.ph.shopping.facade.score.service.IScoreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

/**
 * 积分支付实现类
 *
 * @author 郑朋
 * @create 2017/6/19
 **/
@Component
@Service(version = "1.0.0")
public class ScorePayServiceImpl implements IScorePayService {

    private static final Logger logger = LoggerFactory.getLogger(ScorePayServiceImpl.class);

    @Reference(version = "1.0.0", retries = 0)
    IScoreService scoreService;

    @Autowired
    OnlineOrderService onlineOrderService;

    @Transactional
    @Override
    public Result payByScore(PayScoreDTO payScoreDTO) {
        /**
         * 修改订单状态
         * 积分支付
         */
        logger.info("积分支付入参，payScoreDTO={}", JSON.toJSONString(payScoreDTO));
        Result result = new Result();
        result.setSuccess(false);
        result.setMessage("积分支付失败");
        try {
            String message = payScoreDTO.validateForm();
            if (StringUtils.isEmpty(message)) {
                String orderNo = payScoreDTO.getOrderNo();
                String bizCode = OrderUtil.getOrderBizCode(orderNo);
                int count = 0;
                //修改订单状态
                switch (bizCode) {
                    case OrderBizCode.SUB_MEMBER_ORDER:
                        count = onlineOrderService.updateOrder(payScoreDTO.getOrderId(),payScoreDTO.getMemberId(), PayTypeEnum.PAY_TYPE_SCORE.getPayType());
                        break;
                }
                if (count <= 0) {
                    throw new RuntimeException("修改会员订单状态失败");
                }
                //积分支付
                scoreTrade(payScoreDTO);
                result.setSuccess(true);
                result.setMessage("积分支付成功");
            }
        } catch (Exception e) {
            logger.error("积分支付异常，e={}", e);
            //事物手动回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        logger.info("积分支付返回值，result={}", JSON.toJSONString(result));
        return result;
    }

    private void scoreTrade(PayScoreDTO payScoreDTO) throws Exception {
        logger.info("会员积分扣款接口入参: payScoreDTO={}",JSON.toJSONString(payScoreDTO));
        long count = scoreService.memberScoreTrade(payScoreDTO.getMemberId(),payScoreDTO.getTransCodeEnum(),
                MoneyTransUtil.transMultiDouble(Double.valueOf(payScoreDTO.getAmount())),payScoreDTO.getOrderNo(),0L);
        logger.info("会员积分扣款接口返回值：count={}",count);
        if (count <= 0) {
            throw new RuntimeException("会员积分扣款失败");
        }
    }
}
