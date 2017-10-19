package com.ph.shopping.facade.pay.service;

import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.pay.dto.PayScoreDTO;

/**
 * 积分支付
 *
 * @author 郑朋
 * @create 2017/6/19
 **/
public interface IScorePayService {

    /**
     * @methodname payByScore 的描述：积分支付
     * @param payScoreDTO
     * @return com.ph.shopping.common.util.result.Result
     * @author 郑朋
     * @create 2017/6/19
     */
    Result payByScore(PayScoreDTO payScoreDTO);
}
