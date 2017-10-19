package com.ph.shopping.facade.pay.service;

import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.pay.dto.PayBalanceDTO;

/**
 * 余额支付serivce
 *
 * @author 郑朋
 * @create 2017/6/5
 **/
public interface IBalanceService {

    /**
     * @methodname payByBalance 的描述：订单余额支付
     * @param payBalanceDTO
     * @return com.ph.shopping.common.util.result.Result
     * @author 郑朋
     * @create 2017/6/5
     */
    Result payByBalance(PayBalanceDTO payBalanceDTO);

    /**
     * @methodname backBalance 的描述：余额退款
     * @param payBalanceDTO
     * @return com.ph.shopping.common.util.result.Result
     * @author 郑朋
     * @create 2017/6/7
     */
    Result backBalance(PayBalanceDTO payBalanceDTO);
}
