package com.ph.shopping.facade.pay.service.impl.order;

import cm.ph.shopping.facade.order.constant.OrderRefundStatusEnum;
import cm.ph.shopping.facade.order.constant.OrderStatusEnum;
import cm.ph.shopping.facade.order.constant.RefundStatusEnum;
import cm.ph.shopping.facade.order.entity.*;
import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import com.ph.shopping.common.util.core.RespCode;
import com.ph.shopping.common.util.core.ResultUtil;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.common.util.transform.MoneyTransUtil;
import com.ph.shopping.facade.mapper.*;
import com.ph.shopping.facade.pay.constant.CashConstant;
import com.ph.shopping.facade.pay.entity.PurchaseRefundTrade;
import com.ph.shopping.facade.pay.utils.union.SystemRetCode;
import com.ph.shopping.facade.spm.entity.ManageBankCardInfo;
import com.ph.shopping.facade.spm.service.IManageBankCardService;
import com.ph.shopping.facade.spm.vo.ManageBankCardInfoVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 进货订单修改service
 *
 * @author 郑朋
 * @create 2017/6/6
 **/
@Service
public class PurchaseOrderService {

    private static final Logger log = LoggerFactory.getLogger(PurchaseOrderService.class);


    @Autowired
    PurchaseOrderMapper purchaseOrderMapper;

    @Autowired
    SubOrderMdyHisMapper subOrderMdyHisMapper;

    @Autowired
    PurchaseOrderRefundAppliMapper purchaseOrderRefundAppliMapper;

    @Autowired
    PurchaseRefundTradeMapper purchaseRefundTradeMapper;

    @Reference(version = "1.0.0")
    IManageBankCardService manageBankCardService;

    @Autowired
    PayProductMapper payProductMapper;


    /**
     * @methodname updateOrderByMainOrder 的描述：通过主订单修改订单状态(付款成功)
     * @param purchaseMainOrder
     * @param payment
     * @return int
     * @author 郑朋
     * @create 2017/6/6
     */
    public int updateOrderByMainOrder(PurchaseMainOrder purchaseMainOrder,Byte payment) {
        int count = 0;
        purchaseMainOrder.setPayTime(new Date());
        purchaseOrderMapper.updatePurMainOrder(purchaseMainOrder);
        //支付成功,更新订单为待发货状态
        PurchaseSubOrder updatePurSubOrder = getSubOrder(purchaseMainOrder.getId(),
                purchaseMainOrder.getPurchaserId(), null, payment);
        count = purchaseOrderMapper.updatePurSubOrder(updatePurSubOrder);
        //记录修改订单状态流水
        List<PurchaseSubOrder> list = purchaseOrderMapper.selPurSubOrderByMainOrd(purchaseMainOrder.getId());
        addSubOrderMdyHis(list, OrderStatusEnum.UNDELIVERED_ORDER, CashConstant.OPERATE_TYPE, CashConstant.OPERATE_DESCRIPTION);
        return count;
    }





    /**
     * @methodname updateOrderBySubOrder 的描述：通过子订单修改订单状态(支付成功)
     * @param purchaseSubOrder
     * @param payment
     * @return int
     * @author 郑朋
     * @create 2017/6/6
     */
    public int updateOrderBySubOrder(PurchaseSubOrder purchaseSubOrder,Byte payment) {
        int count;
        //支付成功,更新订单为待发货状态
        PurchaseSubOrder updateSubOrder = getSubOrder(purchaseSubOrder.getMainOrderId(),
                purchaseSubOrder.getPurchaserId(), purchaseSubOrder.getId(), payment);
        count = purchaseOrderMapper.updateByPrimaryKeySelective(updateSubOrder);
        //记录修改订单状态流水
        List<PurchaseSubOrder> list = new ArrayList<>();
        list.add(purchaseSubOrder);
       addSubOrderMdyHis(list, OrderStatusEnum.UNDELIVERED_ORDER, CashConstant.OPERATE_TYPE, CashConstant.OPERATE_DESCRIPTION);
       return count;
    }


    /**
     * @methodname updateOrderByMainOrderT503 的描述：通过主订单修改订单状态(处理T503的情况)
     * @param purchaseMainOrder
     * @return int
     * @author 郑朋
     * @create 2017/6/6
     */
    public int updateOrderByMainOrderT503(PurchaseMainOrder purchaseMainOrder) {
        int count;
        //支付成功,更新订单为待发货状态
        PurchaseSubOrder updatePurSubOrder = getSubOrder(purchaseMainOrder.getId(),
                purchaseMainOrder.getPurchaserId(), null, OrderStatusEnum.CANCEL_ORDER);
        count = purchaseOrderMapper.updatePurSubOrder(updatePurSubOrder);
        //记录修改订单状态流水
        List<PurchaseSubOrder> list = purchaseOrderMapper.selPurSubOrderByMainOrd(purchaseMainOrder.getId());
        addSubOrderMdyHis(list, OrderStatusEnum.CANCEL_ORDER, CashConstant.OPERATE_TYPE, CashConstant.OPERATE_DESCRIPTION_CANCEL_TIME);
        return count;
    }


    /**
     * @methodname updateOrderBySubOrderT503 的描述：通过子订单修改订单状态(处理T503的情况)
     * @param purchaseSubOrder
     * @return int
     * @author 郑朋
     * @create 2017/6/6
     */
    public int updateOrderBySubOrderT503(PurchaseSubOrder purchaseSubOrder) {
        int count = 0;
        //支付成功,更新订单为待发货状态
        PurchaseSubOrder updateSubOrder = getSubOrder(purchaseSubOrder.getMainOrderId(),
                purchaseSubOrder.getPurchaserId(), purchaseSubOrder.getId(),OrderStatusEnum.CANCEL_ORDER);
        count = purchaseOrderMapper.updateByPrimaryKeySelective(updateSubOrder);
        //记录修改订单状态流水
        List<PurchaseSubOrder> list = new ArrayList<>();
        list.add(purchaseSubOrder);
        addSubOrderMdyHis(list, OrderStatusEnum.CANCEL_ORDER, CashConstant.OPERATE_TYPE, CashConstant.OPERATE_DESCRIPTION_CANCEL_TIME);
        return count;
    }

    private PurchaseSubOrder getSubOrder(Long mainOrderId,Long updaterId,Long subOrderId,Byte payment) {
        PurchaseSubOrder updatePurSubOrder = new PurchaseSubOrder();
        updatePurSubOrder.setMainOrderId(mainOrderId);
        updatePurSubOrder.setId(subOrderId);
        updatePurSubOrder.setStatus(OrderStatusEnum.UNDELIVERED_ORDER.getStatus());
        updatePurSubOrder.setPayTime(new Date());
        updatePurSubOrder.setPayment(payment);
        updatePurSubOrder.setUpdateTime(new Date());
        updatePurSubOrder.setUpdaterId(updaterId);
        return updatePurSubOrder;
    }


    private PurchaseSubOrder getSubOrder(Long mainOrderId, Long updaterId, Long subOrderId, OrderStatusEnum orderStatusEnum) {
        PurchaseSubOrder updatePurSubOrder = new PurchaseSubOrder();
        updatePurSubOrder.setMainOrderId(mainOrderId);
        updatePurSubOrder.setId(subOrderId);
        updatePurSubOrder.setStatus(orderStatusEnum.getStatus());
        updatePurSubOrder.setUpdateTime(new Date());
        updatePurSubOrder.setUpdaterId(updaterId);
        return updatePurSubOrder;
    }

    /**
     * @methodname addSubOrderMdyHis 的描述：新增订单修改状态历史记录
     * @param list
     * @param orderStatusEnum
     * @param operateType
     * @param description
     * @return void
     * @author 郑朋
     * @create 2017/5/16
     */
    private void addSubOrderMdyHis(List<PurchaseSubOrder> list, OrderStatusEnum orderStatusEnum,
                                   Byte operateType, String description){
        List<SubOrderMdyHis> SubOrderMdyHisList = new ArrayList<>();
        SubOrderMdyHis subOrderMdyHis;
        for (PurchaseSubOrder purchaseSubOrder : list) {
            subOrderMdyHis = new SubOrderMdyHis();
            subOrderMdyHis.setCreateId(purchaseSubOrder.getPurchaserId());
            subOrderMdyHis.setCreateTime(new Date());
            subOrderMdyHis.setDealStatus(orderStatusEnum.getStatus());
            subOrderMdyHis.setOperateType(operateType);
            subOrderMdyHis.setStatus(purchaseSubOrder.getStatus());
            subOrderMdyHis.setSubOrderId(purchaseSubOrder.getId());
            subOrderMdyHis.setDescription(description);
            SubOrderMdyHisList.add(subOrderMdyHis);
        }
        subOrderMdyHisMapper.insertList(SubOrderMdyHisList);
    }


    /**
     * @methodname orderRefund 的描述：订单退款
     * @param orderId
     * @param retCode
     * @param bankNo
     * @param payNo
     * @return com.ph.shopping.common.util.result.Result
     * @author 郑朋
     * @create 2017/6/7
     */
    public Result orderRefund(String orderId,String retCode,String bankNo, String payNo) {
        Result result = ResultUtil.getResult(RespCode.Code.FAIL);
        //获取订单信息
        PurchaseSubOrder purchaseSubOrder = purchaseOrderMapper.selectByPrimaryKey(Long.valueOf(orderId));
        String orderNo = purchaseSubOrder.getOrderNo();
        PurchaseSubOrder updatePurchaseSubOrder = new PurchaseSubOrder();
        updatePurchaseSubOrder.setId(purchaseSubOrder.getId());
        updatePurchaseSubOrder.setUpdaterId(purchaseSubOrder.getPurchaserId());
        updatePurchaseSubOrder.setUpdateTime(new Date());
        //退款申请信息
        Long appliId = purchaseOrderRefundAppliMapper.selectRefund(purchaseSubOrder.getId());
        PurchaseOrderRefundAppli purchaseOrderRefundAppli = new PurchaseOrderRefundAppli();
        purchaseOrderRefundAppli.setId(appliId);
        purchaseOrderRefundAppli.setUpdateTime(new Date());
        purchaseOrderRefundAppli.setUpdaterId(purchaseSubOrder.getPurchaserId());
        log.info("处理退款 订单号:" + purchaseSubOrder.getOrderNo() + ",请求返回状态:" + retCode);
        if (SystemRetCode.SUCCESS.toString().equals(retCode)) {
            log.info("===========================进入供应链退款成功业务处理==================================");
            //修改订单退款状态为退款成功
            updatePurchaseSubOrder.setRefundStatus(OrderRefundStatusEnum.DONE_REFUND.getStatus());
            updatePurchaseSubOrder.setCancelTime(new Date());
            updatePurchaseSubOrder.setStatus(OrderStatusEnum.CANCEL_ORDER.getStatus());
            //审核通过
            purchaseOrderRefundAppli.setAppliStatus(RefundStatusEnum.PASS.getStatus());
            //退换商品
            restore(Long.valueOf(orderId));
            //订单修改状态记录
            List<PurchaseSubOrder> list = new ArrayList<>();
            list.add(purchaseSubOrder);
            addSubOrderMdyHis(list, OrderStatusEnum.CANCEL_ORDER, CashConstant.OPERATE_TYPE, CashConstant.OPERATE_DESCRIPTION_CANCEL);
            result.setSuccess(true);
            result.setMessage("供应链进货订单退款成功");
        } else if ("FAIL".equals(retCode) || "TIMEOUT".equals(retCode) || "EXCPETION".equals(retCode)) {
            log.info("===========================进入供应链退款失败业务处理==================================");
            //修改订单退款状态为退款取消
            updatePurchaseSubOrder.setRefundStatus(OrderRefundStatusEnum.CANCEL_REFUND.getStatus());
            //申请记录状态为退款失败
            purchaseOrderRefundAppli.setAppliStatus(RefundStatusEnum.UN_PASS.getStatus());
            purchaseOrderRefundAppli.setRejectedReason(CashConstant.REFUND_REASON);
            log.info("贵州银联代付交易失败订单号:" + orderNo);
            result.setSuccess(true);
            result.setMessage("供应链进货订单退款失败");
        } else {
            log.info("===========================进入供应链退款未知业务处理==================================");
            //修改订单退款状态为退款中
            updatePurchaseSubOrder.setRefundStatus(OrderRefundStatusEnum.REFUNDING.getStatus());
            //申请记录状态为通过
            purchaseOrderRefundAppli.setAppliStatus(RefundStatusEnum.PASS.getStatus());
            log.info("贵州银联代付交易失败订单号:" + orderNo + "交易金额：" + MoneyTransUtil.transDivis(purchaseSubOrder.getTotalCost()).toString());
            result.setMessage("供应链进货订单退款中");
        }
        purchaseOrderMapper.updateByPrimaryKeySelective(updatePurchaseSubOrder);
        purchaseOrderRefundAppliMapper.updateByPrimaryKeySelective(purchaseOrderRefundAppli);
        //退款流水
        PurchaseRefundTrade purchaseRefundTrade = new PurchaseRefundTrade();
        purchaseRefundTrade.setCreaterId(purchaseSubOrder.getPurchaserId());
        purchaseRefundTrade.setCreateTime(new Date());
        purchaseRefundTrade.setMoney(purchaseSubOrder.getTotalCost());
        purchaseRefundTrade.setPayeeBankNo(bankNo);
        purchaseRefundTrade.setPayeeId(purchaseSubOrder.getPurchaserId());
        purchaseRefundTrade.setPayeeName(purchaseSubOrder.getContacts());
        purchaseRefundTrade.setPayment(purchaseSubOrder.getPayment());
        purchaseRefundTrade.setRefundAppliId(appliId);
        purchaseRefundTrade.setPayNo(payNo);
        purchaseRefundTrade.setPayStatus(retCode);
        purchaseRefundTradeMapper.insert(purchaseRefundTrade);
        return result;
    }


    /**
     * @methodname getBankInfo 的描述：查询银行卡信息
     * @param userId
     * @return com.ph.shopping.facade.spm.vo.ManageBankCardInfoVO
     * @author 郑朋
     * @create 2017/6/7
     */
    public ManageBankCardInfoVO getBankInfo(Long userId) {
        ManageBankCardInfoVO vo = new ManageBankCardInfoVO();
        try {
            //查询银行卡信息
            ManageBankCardInfo manageBankCardInfo = new ManageBankCardInfo();
            manageBankCardInfo.setUserId(userId);
            Result bankCard = manageBankCardService.getBindCardInfo(manageBankCardInfo);
            vo = (ManageBankCardInfoVO) bankCard.getData();
        } catch (Exception e) {
            log.error("查询银行卡信息异常，e={}",e);
        }
        return vo;
    }


    /**
     * @methodname restore 的描述：根据订单id退还库存
     * @param subOrderId
     * @return void
     * @author 郑朋
     * @create 2017/5/18
     */
    private void restore(Long subOrderId) {
        //根据订单id查询订单商品信息
        List<PurchaseSubOrderProduct> list = purchaseOrderMapper.selPurSubOrdProBySubId(subOrderId);
        //归还商品数量
        Long currentSkuId = null;
        if (CollectionUtils.isNotEmpty(list)) {
            //循环sku信息
            for (PurchaseSubOrderProduct purchaseSubOrderProduct : list) {
                currentSkuId = payProductMapper.selectCurrentProductSkuId(purchaseSubOrderProduct.getProductId(),
                        purchaseSubOrderProduct.getSpecificationValIds());
                //修改sku数量后才修改商品总数
                if (payProductMapper.addSkuCount(currentSkuId,purchaseSubOrderProduct.getSkuNum()) > 0) {
                    payProductMapper.addProductCount(purchaseSubOrderProduct.getProductId(),purchaseSubOrderProduct.getSkuNum());
                }
            }
        }
    }

}
