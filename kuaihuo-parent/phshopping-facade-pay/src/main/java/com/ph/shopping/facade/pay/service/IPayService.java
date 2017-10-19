package com.ph.shopping.facade.pay.service;

import java.math.BigDecimal;

import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.pay.vo.PayecoOrderVo;
import com.ph.shopping.facade.pay.vo.UnlineOrderVO;

/**
 * 
 * @项目：phshopping-facade-pay
 *
 * @描述：支付、提现接口
 *
 * @作者： liuy
 *
 * @创建时间：2017年3月23日
 *
 * @Copyright @2017 by liuy
 */
public interface IPayService {

    /**
     * 回调更新订单
     * @param orderNum
     * @param payType
     * @param payStatus
     * @param tradeCode
     * @return
     * @author Mr.Chang
     * @param score 
     */
    public Result checkAndUpdateOrder(String orderNum,int payType,int payStatus,String tradeCode);

    /**
     * 根据订单号获取订单信息
     * @param orderNum
     * @return
     * @author Mr.Chang
     */
    public PayecoOrderVo getOrderByOrderNo(String orderNum);
    
    /**
     * 更新mac值到对应订单表
     * @param orderNum
     * @param mac
     * @author Mr.Chang
     */
    public void updateOrderMD5Sign(String orderNum,String mac);


    /**
     * @methodname dealT503Status 的描述：处理易联支付订单超时问题
     * @param orderNum
     * @param payType
     * @param payStatus
     * @param tradeCode
     * @return com.ph.shopping.common.util.result.Result
     * @author 郑朋
     * @create 2017/6/29
     */
    Result dealT503Status(String orderNum,int payType,int payStatus,String tradeCode);
    
    /**
     * @methodname dealT503Status 会员支付完成回调更新数据
     * @param orderNum
     * @param payType
     * @param payStatus
     * @param tradeCode
     * @return com.ph.shopping.common.util.result.Result
     * @author 李治桦
     * @create 2017/6/29
     */
    public Result updateTradOrder(String orderNum, Long orderMoney, int payType, int payStatus, String tradeCode);
    /**
     * 创建人：王雪洋   
     * 创建时间：2017年8月27日 下午12:03:29    
     * @param id
     * @desc: 根据订单id查询支付订单
     */
    public UnlineOrderVO queryUnlineOrderVO(Long id);
    /**
     * @methodname dealT503Status 会员支付完成回调更新数据
     * @param orderNum
     * @param payType
     * @param payStatus
     * @param tradeCode
     * @return com.ph.shopping.common.util.result.Result
     * @author 李治桦
     * @create 2017/6/29
     */
    public Result updateTradOrderForFail(String orderNum, Long orderMoney, int payType, int payStatus, String tradeCode); 
    
}
