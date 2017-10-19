package com.ph.shopping.facade.pay.service.impl;

import cm.ph.shopping.facade.order.entity.PurchaseMainOrder;
import cm.ph.shopping.facade.order.entity.PurchaseSubOrder;
import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.ph.shopping.common.util.core.RespCode;
import com.ph.shopping.common.util.core.ResultUtil;
import com.ph.shopping.common.util.order.OrderBizCode;
import com.ph.shopping.common.util.order.OrderUtil;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.common.util.transform.MoneyTransUtil;
import com.ph.shopping.facade.mapper.PurchaseOrderMapper;
import com.ph.shopping.facade.pay.constant.CashConstant;
import com.ph.shopping.facade.pay.dto.PayBalanceDTO;
import com.ph.shopping.facade.pay.service.IBalanceService;
import com.ph.shopping.facade.pay.service.impl.order.PurchaseOrderService;
import com.ph.shopping.facade.pay.utils.union.SystemRetCode;
import com.ph.shopping.facade.permission.service.IRoleService;
import com.ph.shopping.facade.permission.vo.RoleVO;
import com.ph.shopping.facade.spm.service.IUserBalanceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.List;

/**
 * 余额支付接口实现类
 *
 * @author 郑朋
 * @create 2017/6/5
 **/
@Component
@Service(version="1.0.0")
public class BalanceServiceImpl implements IBalanceService {

    private static Logger logger = LoggerFactory.getLogger(BalanceServiceImpl.class);

    @Reference(version = "1.0.0",retries = 0)
    IUserBalanceService userBalanceService;

    @Autowired
    PurchaseOrderMapper purchaseOrderMapper;

    @Autowired
    PurchaseOrderService purchaseOrderService;

    @Reference(version = "1.0.0")
    IRoleService roleService;

    @Transactional
    @Override
    public Result payByBalance(PayBalanceDTO payBalanceDTO) {
        /**
         * 修改订单状态
         * 余额支付
         */
        logger.info("余额支付入参，payBalanceDTO={}", JSON.toJSONString(payBalanceDTO));
        Result result = ResultUtil.getResult(RespCode.Code.FAIL);
        result.setMessage("余额支付失败");
        try {
            String message = payBalanceDTO.validateForm();
            if (StringUtils.isEmpty(message)) {
                String orderNo = payBalanceDTO.getOrderNo();
                String bizCode = OrderUtil.getOrderBizCode(orderNo);
                int count = 0;
                switch (bizCode) {
                    case OrderBizCode.PURCHASE_GOODS:
                        //供应链子订单支付回调处理
                        PurchaseSubOrder purchaseSubOrder = purchaseOrderMapper.selPurSubOrderByOrderNo(orderNo);
                        //订单状态为待付款状态才会进行订单更新
                        count = purchaseOrderService.updateOrderBySubOrder(purchaseSubOrder, CashConstant.BALANCE);
                        break;
                    case OrderBizCode.MAIN_PURCHASE_GOODS :
                        //供应链用总订单支付回调处理
                        //查询主订单信息
                        PurchaseMainOrder purchaseMainOrder = purchaseOrderMapper.selPurMainOrderByOrderNo(orderNo);
                        //修改主订单支付时间
                        count = purchaseOrderService.updateOrderByMainOrder(purchaseMainOrder, CashConstant.BALANCE);
                        break;
                }
                if (count <= 0) {
                    throw new RuntimeException("修改供应链订单状态失败");
                }
                //调用余额扣款接口
                userBalanceTrade(payBalanceDTO);
                result.setSuccess(true);
                result.setMessage("余额支付成功");
            }
        } catch (Exception e) {
            logger.error("余额支付异常，e={}", e);
            //事物手动回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return result;
    }

    @Transactional
    @Override
    public Result backBalance(PayBalanceDTO payBalanceDTO) {
        Result result = ResultUtil.getResult(RespCode.Code.FAIL);
        result.setMessage("余额退款失败");
        try {
            logger.info("供应链余额退款入参，payBalanceDTO={}", JSON.toJSONString(payBalanceDTO));
            String message = payBalanceDTO.validateForm();
            if (StringUtils.isEmpty(message)) {
                purchaseOrderService.orderRefund(payBalanceDTO.getOrderId().toString(),SystemRetCode.SUCCESS.toString(),null,null);
                List<RoleVO> list = roleService.getRoleByUserId(payBalanceDTO.getUserId());
                if (CollectionUtils.isNotEmpty(list)) {
                    payBalanceDTO.setUserType(list.get(0).getRoleCode());
                }
                //调用余额扣款接口
                userBalanceTrade(payBalanceDTO);
                result.setSuccess(true);
                result.setMessage("余额退款成功");
            }
        } catch (Exception e) {
            logger.error("余额退款异常，e={}", e);
            //事物手动回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return result;
    }

    /**
     * @methodname payByBalance 的描述：余额扣款接口
     * @param payBalanceDTO
     * @return void
     * @author 郑朋
     * @create 2017/6/5
     */
    private void userBalanceTrade(PayBalanceDTO payBalanceDTO) throws Exception {
        logger.info("用户余额扣款接口入参: payBalanceDTO={}",JSON.toJSONString(payBalanceDTO));
        //调用余额扣款接口
        int result = userBalanceService.userBalanceTrade(payBalanceDTO.getUserId(),payBalanceDTO.getTransCodeEnum(),
                MoneyTransUtil.transMultiDouble(Double.valueOf(payBalanceDTO.getAmount())),payBalanceDTO.getOrderNo(),
                0L,payBalanceDTO.getUserType());
        logger.info("用户余额扣款接口返回值: warehouseAddressVOS={}",JSON.toJSONString(result));
        if (result <= 0) {
            throw new RuntimeException("用户余额扣款失败");
        }
    }
}
