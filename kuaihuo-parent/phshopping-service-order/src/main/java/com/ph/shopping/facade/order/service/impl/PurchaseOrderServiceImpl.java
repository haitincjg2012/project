package com.ph.shopping.facade.order.service.impl;

import cm.ph.shopping.facade.order.constant.OrderIsSettleEnum;
import cm.ph.shopping.facade.order.constant.OrderRefundStatusEnum;
import cm.ph.shopping.facade.order.constant.OrderStatusEnum;
import cm.ph.shopping.facade.order.constant.RefundStatusEnum;
import cm.ph.shopping.facade.order.dto.*;
import cm.ph.shopping.facade.order.entity.*;
import cm.ph.shopping.facade.order.exception.OrderException;
import cm.ph.shopping.facade.order.exception.OrderExceptionEnum;
import cm.ph.shopping.facade.order.service.IPurchaseOrderService;
import cm.ph.shopping.facade.order.vo.PurchaseMainOrderVO;
import cm.ph.shopping.facade.order.vo.PurchaseProductVO;
import cm.ph.shopping.facade.order.vo.PurchaseSubOrderDetailVO;
import cm.ph.shopping.facade.order.vo.PurchaseSubOrderPageVO;
import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ph.shopping.common.core.customenum.TransCodeEnum;
import com.ph.shopping.common.util.core.ResultUtil;
import com.ph.shopping.common.util.order.OrderBizCode;
import com.ph.shopping.common.util.order.OrderUtil;
import com.ph.shopping.common.util.page.PageBean;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.common.util.transform.MoneyTransUtil;
import com.ph.shopping.facade.mapper.*;
import com.ph.shopping.facade.order.service.constant.OrderOperateEnum;
import com.ph.shopping.facade.pay.dto.DefrayDTO;
import com.ph.shopping.facade.pay.dto.PayBalanceDTO;
import com.ph.shopping.facade.pay.service.IBalanceService;
import com.ph.shopping.facade.pay.service.ICashService;
import com.ph.shopping.facade.pay.utils.union.UniqueUtils;
import com.ph.shopping.facade.product.entity.ProductSku;
import com.ph.shopping.facade.product.service.IProductSkuService;
import com.ph.shopping.facade.spm.entity.ManageBankCardInfo;
import com.ph.shopping.facade.spm.service.IManageBankCardService;
import com.ph.shopping.facade.spm.vo.ManageBankCardInfoVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.math.BigDecimal;
import java.util.*;

/**
 * 供应链订单接口实现
 *
 * @author 郑朋
 * @create 2017/5/15
 **/
@Component
@Service(version = "1.0.0")
public class PurchaseOrderServiceImpl implements IPurchaseOrderService {
    private static final Logger logger = LoggerFactory.getLogger(PurchaseOrderServiceImpl.class);

    @Autowired
    PurchaseSubOrderMapper purchaseSubOrderMapper;

    @Autowired
    PurchaseSubOrderProductMapper purchaseSubOrderProductMapper;

    @Autowired
    SubOrderMdyHisMapper subOrderMdyHisMapper;

    @Autowired
    PurchaseOrderRefundMapper purchaseOrderRefundMapper;

    @Autowired
    OrderProductMapper orderProductMapper;

    @Reference(version = "1.0.0")
    IProductSkuService productSkuService;

    @Autowired
    PurchaseMainOrderMapper purchaseMainOrderMapper;

    @Reference(version = "1.0.0")
    IManageBankCardService manageBankCardService;

    @Reference(version = "1.0.0")
    ICashService cashService;

    @Reference(version = "1.0.0")
    IBalanceService balanceService;

    @Override
    public Result getPurchaseOrderListByPage(PageBean pageBean, QueryPurchaseOrderDTO queryPurchaseOrderDTO) {
        try {
            logger.info("供应链进货订单分页查询入参，pageBean={},queryPurchaseOrderDTO={}", JSON.toJSONString(pageBean) ,
                    JSON.toJSON(queryPurchaseOrderDTO));
            PageHelper.startPage(pageBean.getPageNum(), pageBean.getPageSize());
            List<PurchaseSubOrderPageVO> list = purchaseSubOrderMapper.selectPurchaseOrderListByPage(queryPurchaseOrderDTO);
            PageInfo<PurchaseSubOrderPageVO> pageInfo = new PageInfo<>(list);
            Result result = ResultUtil.getResult(OrderExceptionEnum.Code.SUCCESS, pageInfo.getList(), pageInfo.getTotal());
            logger.info("供应链进货订单分页查询返回值，result={}", JSON.toJSONString(result));
            return result;
        } catch (Exception e) {
            logger.error("供应链进货订单分页查询异常，e={}", e);
            throw new OrderException(OrderExceptionEnum.Code.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Result getPurchaseOrderList(QueryPurchaseOrderDTO queryPurchaseOrderDTO) {
        try {
            logger.info("供应链进货订单查询入参，queryPurchaseOrderDTO={}", JSON.toJSON(queryPurchaseOrderDTO));
            List<PurchaseSubOrderPageVO> list = purchaseSubOrderMapper.selectPurchaseOrderListByPage(queryPurchaseOrderDTO);
            Result result = ResultUtil.getResult(OrderExceptionEnum.Code.SUCCESS, list);
            logger.info("供应链进货订单查询返回值，result={}", JSON.toJSONString(result));
            return result;
        } catch (Exception e) {
            logger.error("供应链进货订单查询异常，e={}", e);
            throw new OrderException(OrderExceptionEnum.Code.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Result getPurchaseOrderDetail(Long orderId) {
        try {
            logger.info("供应链进货订单详情入参，orderId={}", orderId);
            Result result = ResultUtil.getResult(OrderExceptionEnum.Code.FAIL);
            if (null != orderId) {
                PurchaseSubOrderDetailVO purchaseSubOrderDetailVO = new PurchaseSubOrderDetailVO();
                PurchaseSubOrder purchaseSubOrder =  purchaseSubOrderMapper.selectByPrimaryKey(orderId);
                BeanUtils.copyProperties(purchaseSubOrder,purchaseSubOrderDetailVO);
                purchaseSubOrderDetailVO.setTotalCost(MoneyTransUtil.transMulti1(purchaseSubOrderDetailVO.getTotalCost()));
                purchaseSubOrderDetailVO.setMoney(MoneyTransUtil.transMulti1(purchaseSubOrderDetailVO.getMoney()));
                purchaseSubOrderDetailVO.setFreight(MoneyTransUtil.transMulti1(purchaseSubOrderDetailVO.getFreight()));
                purchaseSubOrderDetailVO.setReferenceFreight(MoneyTransUtil.transMulti1(purchaseSubOrderDetailVO.getReferenceFreight()));
                //查询订单对应的商品信息
                List<PurchaseSubOrderProduct> list = purchaseSubOrderProductMapper.selPurSubOrdProBySubIdGroup(orderId);
                List<PurchaseProductVO> PurchaseProductList = new ArrayList<>();
                PurchaseProductVO vo;
                for (PurchaseSubOrderProduct product : list) {
                    vo = new PurchaseProductVO();
                    BeanUtils.copyProperties(product,vo);
                    vo.setTotalMoney(MoneyTransUtil.transMulti1(vo.getTotalMoney()));
                    vo.setTotalFreight(MoneyTransUtil.transMulti1(vo.getTotalFreight()));
                    PurchaseProductList.add(vo);
                }
                purchaseSubOrderDetailVO.setList(PurchaseProductList);
                result = ResultUtil.getResult(OrderExceptionEnum.Code.SUCCESS,purchaseSubOrderDetailVO);
            } else {
                result.setMessage("订单id不能为空");
            }
            logger.info("供应链进货订单详情返回值，result={}", JSON.toJSONString(result));
            return result;
        } catch (Exception e) {
            logger.error("供应链进货订单详情异常，e={}", e);
            throw new OrderException(OrderExceptionEnum.Code.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Result getPurchaseOrderProductSku(Long subOrderId, Long productId) {
        try {
            logger.info("查询订单商品的的sku信息入参，subOrderId={},productId={}", subOrderId,productId);
            Result result = ResultUtil.getResult(OrderExceptionEnum.Code.FAIL);
            if (null != productId) {
                //查询订单对应的商品信息
                List<PurchaseSubOrderProduct> list = purchaseSubOrderProductMapper.selectPurSubOrderSkuBySubId(subOrderId,productId);
                result = ResultUtil.getResult(OrderExceptionEnum.Code.SUCCESS,list);
            } else {
                result.setMessage("商品id不能为空");
            }
            logger.info("查询订单商品的的sku信息返回值，result={}", JSON.toJSONString(result));
            return result;
        } catch (Exception e) {
            logger.error("查询订单商品的的sku信息异常，e={}", e);
            throw new OrderException(OrderExceptionEnum.Code.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    @Override
    public Result confirmReceipt(UpdateOrderStatusDTO updateOrderStatusDTO) {
        try {
            logger.info("供应链进货订单确认收货入参，confirmReceiptDTO={}", JSON.toJSONString(updateOrderStatusDTO));
            Result result;
            String message = updateOrderStatusDTO.validateForm();
            if (StringUtils.isEmpty(message)) {
                //修改订单状态为3，完成时间，更新时间，修改人
                PurchaseSubOrder purchaseSubOrder = new PurchaseSubOrder();
                Date date = new Date();
                purchaseSubOrder.setId(updateOrderStatusDTO.getOrderId());
                purchaseSubOrder.setDoneTime(date);
                purchaseSubOrder.setUpdaterId(updateOrderStatusDTO.getUpdaterId());
                purchaseSubOrder.setUpdateTime(date);
                purchaseSubOrder.setStatus(OrderStatusEnum.DONE_ORDER.getStatus());
                purchaseSubOrderMapper.updateByPrimaryKeySelective(purchaseSubOrder);
                //插入修改子订单变更历史
                addSubOrderMdyHis(updateOrderStatusDTO,OrderStatusEnum.DONE_ORDER,
                        OrderOperateEnum.UPDATE_STATUS,"确认收货操作");
                result = ResultUtil.getResult(OrderExceptionEnum.Code.SUCCESS);
            } else {
                result = ResultUtil.getResult(OrderExceptionEnum.Code.FAIL);
                result.setMessage(message);
            }
            logger.info("供应链进货订单确认收货返回值，result={}", JSON.toJSONString(result));
            return result;
        } catch (Exception e) {
            logger.error("供应链进货订单确认收货异常，e={}", e);
            throw new OrderException(OrderExceptionEnum.Code.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    @Override
    public Result addRefundApplication(RefundApplicationDTO refundApplicationDTO) {
        try {
            logger.info("供应链进货订单申请退款入参，refundApplicationDTO={}", JSON.toJSONString(refundApplicationDTO));
            Result result;
            String message = refundApplicationDTO.validateForm();
            if (StringUtils.isEmpty(message)) {
                // 删除申请记录
                purchaseOrderRefundMapper.deleteRefundByOrderId(refundApplicationDTO.getSubOrderId());
                PurchaseOrderRefundAppli purchaseOrderRefundAppli = new PurchaseOrderRefundAppli();
                BeanUtils.copyProperties(refundApplicationDTO,purchaseOrderRefundAppli);
                purchaseOrderRefundAppli.setAppliStatus(RefundStatusEnum.PENDING_AUDIT.getStatus());
                purchaseOrderRefundAppli.setCreateTime(new Date());
                purchaseOrderRefundMapper.insert(purchaseOrderRefundAppli);
                //修改子订单退款状态
                PurchaseSubOrder purchaseSubOrder = new PurchaseSubOrder();
                purchaseSubOrder.setId(refundApplicationDTO.getSubOrderId());
                purchaseSubOrder.setRefundStatus(OrderRefundStatusEnum.CHECKING.getStatus());
                purchaseSubOrderMapper.updateByPrimaryKeySelective(purchaseSubOrder);
                result = ResultUtil.getResult(OrderExceptionEnum.Code.SUCCESS);
            } else {
                result = ResultUtil.getResult(OrderExceptionEnum.Code.FAIL);
                result.setMessage(message);
            }
            logger.info("供应链进货订单申请退款返回值，result={}", JSON.toJSONString(result));
            return result;
        } catch (Exception e) {
            logger.error("供应链进货订单申请退款异常，e={}", e);
            throw new OrderException(OrderExceptionEnum.Code.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Result getRefundApplication(Long orderId) {
        try {
            logger.info("供应链进货订单申请退款详情入参，orderId={}", orderId);
            Result result = ResultUtil.getResult(OrderExceptionEnum.Code.SUCCESS,
                    purchaseOrderRefundMapper.selectApplicationByOrderId(orderId).get(0));
            logger.info("供应链进货订单申请退款详情返回值，result={}", JSON.toJSONString(result));
            return result;
        } catch (Exception e) {
            logger.error("供应链进货订单申请退款详情异常，e={}", e);
            throw new OrderException(OrderExceptionEnum.Code.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    @Override
    public Result cancelOrder(UpdateOrderStatusDTO updateOrderStatusDTO) {
        try {
            logger.info("供应链进货订单取消订单入参，updateOrderStatusDTO={}", JSON.toJSONString(updateOrderStatusDTO));
            String message = updateOrderStatusDTO.validateForm();
            Result result;
            if (StringUtils.isEmpty(message)) {
                //改变订单状态为4，修改时间，修改人，取消人id，取消时间
                PurchaseSubOrder purchaseSubOrder = new PurchaseSubOrder();
                Date date = new Date();
                purchaseSubOrder.setId(updateOrderStatusDTO.getOrderId());
                purchaseSubOrder.setCancelUserId(updateOrderStatusDTO.getUpdaterId());
                purchaseSubOrder.setCancelTime(date);
                purchaseSubOrder.setUpdaterId(updateOrderStatusDTO.getUpdaterId());
                purchaseSubOrder.setUpdateTime(date);
                purchaseSubOrder.setStatus(OrderStatusEnum.CANCEL_ORDER.getStatus());
                purchaseSubOrderMapper.updateByPrimaryKeySelective(purchaseSubOrder);
                //新增修改状态记录
                addSubOrderMdyHis(updateOrderStatusDTO,OrderStatusEnum.CANCEL_ORDER,OrderOperateEnum.UPDATE_STATUS,"订单取消操作");
                //归还商品数量
                restore(updateOrderStatusDTO.getOrderId());
                result = ResultUtil.getResult(OrderExceptionEnum.Code.SUCCESS);
            } else {
                result = ResultUtil.getResult(OrderExceptionEnum.Code.FAIL);
                result.setMessage(message);
            }
            logger.info("供应链进货订单取消订单返回值，result={}", JSON.toJSONString(result));
            return result;
        } catch (Exception e) {
            logger.error("供应链进货订单取消订单异常，e={}", e);
            throw new OrderException(OrderExceptionEnum.Code.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    @Override
    public Result addPurchaseOrder(AddPurchaseOrderDTO addPurchaseOrderDTO) {
        Result result = ResultUtil.getResult(OrderExceptionEnum.Code.FAIL);
        long beginTime = System.currentTimeMillis();
        try {
            logger.info("供应链新增进货订单入参，addPurchaseOrderDTO={},开始时间={}", JSON.toJSONString(addPurchaseOrderDTO),beginTime);
            String message = addPurchaseOrderDTO.validateForm();
            if (StringUtils.isEmpty(message)) {
                List<PurchaseOrderDTO> list = addPurchaseOrderDTO.getPurchaseOrderDTO();
                //校验和扣除库存
                checkStock(list);
                //订单数据新增
                result = ResultUtil.getResult(OrderExceptionEnum.Code.SUCCESS,addOrder(list));
            } else {
                result = ResultUtil.getResult(OrderExceptionEnum.Code.FAIL);
                result.setMessage(message);
            }
        } catch (OrderException e) {
            logger.error("供应链新增进货订单异常，e={}", e);
            result.setMessage(e.getMessage());
            //事物手动回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        } catch (Exception e) {
            logger.error("供应链新增进货订单异常，e={}", e);
            result.setMessage("系统繁忙，请重试！");
            //事物手动回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        logger.info("供应链新增进货订单返回值，result={}, 耗时ms={}", JSON.toJSONString(result),System.currentTimeMillis() - beginTime);
        return result;
    }

    @Transactional
    @Override
    public Result updatePurchaseOrderFreight(UpdatePurOrderFreDTO updatePurOrderFreDTO) {
        try {
            logger.info("供应链修改订单物流费用入参，updatePurOrderFreDTO={}", JSON.toJSONString(updatePurOrderFreDTO));
            String message = updatePurOrderFreDTO.validateForm();
            Result result;
            if (StringUtils.isEmpty(message)) {
                PurchaseSubOrder purchaseSubOrderTemp = purchaseSubOrderMapper.selectByPrimaryKey(updatePurOrderFreDTO.getOrderId());
                //修改子订单总金额，物流费用，修改时间，修改人
                BigDecimal updateFre = MoneyTransUtil.transMulti(updatePurOrderFreDTO.getFreight());
                PurchaseSubOrder purchaseSubOrder = new PurchaseSubOrder();
                purchaseSubOrder.setId(updatePurOrderFreDTO.getOrderId());
                purchaseSubOrder.setUpdaterId(updatePurOrderFreDTO.getUpdaterId());
                purchaseSubOrder.setUpdateTime(new Date());
                purchaseSubOrder.setFreight(updateFre);
                BigDecimal totalCost =purchaseSubOrderTemp.getMoney().add(updateFre);
                purchaseSubOrder.setTotalCost(totalCost);
                purchaseSubOrderMapper.updateByPrimaryKeySelective(purchaseSubOrder);
                //修改主订单总金额
                PurchaseMainOrder purchaseMainOrder = purchaseMainOrderMapper.selectByPrimaryKey(purchaseSubOrderTemp.getMainOrderId());
                PurchaseMainOrder updateMainOrder = new PurchaseMainOrder();
                totalCost = purchaseMainOrder.getMoney().add(updateFre);
                updateMainOrder.setTotalCost(totalCost);
                updateMainOrder.setId(purchaseSubOrderTemp.getMainOrderId());
                purchaseMainOrderMapper.updateByPrimaryKeySelective(updateMainOrder);
                //新增子订单修改物流费用记录
                addSubOrderMdyHis(updatePurOrderFreDTO,OrderOperateEnum.UPDATE_FREIGHT,updateFre);
                result = ResultUtil.getResult(OrderExceptionEnum.Code.SUCCESS);
            } else {
                result = ResultUtil.getResult(OrderExceptionEnum.Code.FAIL);
                result.setMessage(message);
            }
            logger.info("供应链修改订单物流费用返回值，result={}", JSON.toJSONString(result));
            return result;
        } catch (Exception e) {
            logger.error("供应链修改订单物流费用异常，e={}", e);
            throw new OrderException(OrderExceptionEnum.Code.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    @Override
    public Result sendProduct(SendProductDTO sendProductDTO) {
        try {
            logger.info("供应链订单发货入参，updatePurOrderFreDTO={}", JSON.toJSONString(sendProductDTO));
            String message = sendProductDTO.validateForm();
            Result result;
            if (StringUtils.isEmpty(message)) {
                // 修改订单退款状态为2，修改人id，修改时间,状态为2和发货相关的数据
                PurchaseSubOrder purchaseSubOrder = new PurchaseSubOrder();
                BeanUtils.copyProperties(sendProductDTO,purchaseSubOrder);
                purchaseSubOrder.setStatus(OrderStatusEnum.RECEIVED_ORDER.getStatus());
                purchaseSubOrder.setUpdaterId(sendProductDTO.getSenderId());
                purchaseSubOrder.setUpdateTime(new Date());
                purchaseSubOrder.setSendTime(new Date());
                //新增状态修改记录
                UpdateOrderStatusDTO  updateOrderStatusDTO = new UpdateOrderStatusDTO();
                updateOrderStatusDTO.setCurrentOrderStatus(sendProductDTO.getCurrentOrderStatus());
                updateOrderStatusDTO.setOrderId(sendProductDTO.getId());
                updateOrderStatusDTO.setUpdaterId(sendProductDTO.getSenderId());

                //存在申请，则需要修改申请记录
                List<PurchaseOrderRefundAppli> list = purchaseOrderRefundMapper.selectApplicationByOrderId(sendProductDTO.getId());
                if (CollectionUtils.isNotEmpty(list) && list.get(0).getAppliStatus() != 1) {
                    //如果存在申请记录，则修改退款状态为拒绝
                    purchaseSubOrder.setRefundStatus(OrderRefundStatusEnum.CANCEL_REFUND.getStatus());
                    // 修改退款申请记录状态为1，驳回原因，修改人id，修改时间，驳回人联系电话
                    PurchaseOrderRefundAppli purchaseOrderRefundAppli = new PurchaseOrderRefundAppli();
                    purchaseOrderRefundAppli.setAppliStatus(RefundStatusEnum.UN_PASS.getStatus());
                    purchaseOrderRefundAppli.setRejectedReason("订单已发货");
                    purchaseOrderRefundAppli.setTelPhone(sendProductDTO.getSendTelPhone());
                    purchaseOrderRefundAppli.setSubOrderId(sendProductDTO.getId());
                    purchaseOrderRefundAppli.setUpdaterId(sendProductDTO.getSenderId());
                    purchaseOrderRefundAppli.setUpdateTime(new Date());
                    purchaseOrderRefundAppli.setId(list.get(0).getId());
                    purchaseOrderRefundMapper.updateByPrimaryKeySelective(purchaseOrderRefundAppli);
                }
                purchaseSubOrderMapper.updateByPrimaryKeySelective(purchaseSubOrder);
                addSubOrderMdyHis(updateOrderStatusDTO,OrderStatusEnum.RECEIVED_ORDER,
                        OrderOperateEnum.UPDATE_STATUS,"订单发货");
                result = ResultUtil.getResult(OrderExceptionEnum.Code.SUCCESS);
            } else {
                result = ResultUtil.getResult(OrderExceptionEnum.Code.FAIL);
                result.setMessage(message);
            }
            logger.info("供应链订单发货返回值，result={}", JSON.toJSONString(result));
            return result;
        } catch (Exception e) {
            logger.error("供应链订单发货异常，e={}", e);
            throw new OrderException(OrderExceptionEnum.Code.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    @Override
    public Result checkRefundApplication(CheckRefundAppliDTO checkRefundAppliDTO) {
        Result result = ResultUtil.getResult(OrderExceptionEnum.Code.FAIL);
        try {
            logger.info("供应链订单退款审核入参，checkRefundAppliDTO={}", JSON.toJSONString(checkRefundAppliDTO));
            String message = checkRefundAppliDTO.validateForm();
            if (StringUtils.isEmpty(message)) {
                /**
                 * 如果是审核通过
                 *     调用退款
                 * 如果审核不通过
                 *     修改订单退款状态为2，修改人id，修改时间
                 *     修改退款申请记录状态为1，驳回原因，修改人id，修改时间，驳回人联系电话
                 */
                PurchaseOrderRefundAppli purchaseOrderRefundAppli = new PurchaseOrderRefundAppli();
                PurchaseSubOrder purchaseSubOrder = new PurchaseSubOrder();
                if (checkRefundAppliDTO.getAppliStatus().byteValue() == 0) {
                    //通过
                    //调用退款
                    refundMoney(checkRefundAppliDTO.getSubOrderId());
                } else if (checkRefundAppliDTO.getAppliStatus().byteValue() == 1) {
                    //不通过
                    purchaseOrderRefundAppli.setAppliStatus(RefundStatusEnum.UN_PASS.getStatus());
                    purchaseOrderRefundAppli.setRejectedReason(checkRefundAppliDTO.getRejectedReason());
                    purchaseOrderRefundAppli.setTelPhone(checkRefundAppliDTO.getTelPhone());
                    //修改订单
                    purchaseSubOrder.setRefundStatus(OrderRefundStatusEnum.CANCEL_REFUND.getStatus());
                    purchaseSubOrder.setId(checkRefundAppliDTO.getSubOrderId());
                    purchaseSubOrder.setUpdateTime(new Date());
                    purchaseSubOrder.setUpdaterId(checkRefundAppliDTO.getUpdaterId());
                    purchaseSubOrderMapper.updateByPrimaryKeySelective(purchaseSubOrder);
                }
                //修改退款申请记录
                purchaseOrderRefundAppli.setId(checkRefundAppliDTO.getId());
                purchaseOrderRefundAppli.setUpdaterId(checkRefundAppliDTO.getUpdaterId());
                purchaseOrderRefundAppli.setUpdateTime(new Date());
                purchaseOrderRefundMapper.updateByPrimaryKeySelective(purchaseOrderRefundAppli);
                result = ResultUtil.getResult(OrderExceptionEnum.Code.SUCCESS);
            } else {
                result = ResultUtil.getResult(OrderExceptionEnum.Code.FAIL);
                result.setMessage(message);
            }
        } catch (OrderException e) {
            logger.error("供应链订单退款审核异常，e={}", e);
            result.setMessage(e.getMessage());
            //事物手动回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        } catch (Exception e) {
            logger.error("供应链订单退款审核异常，e={}", e);
            result.setMessage("系统繁忙，请重试！");
            //事物手动回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        logger.info("供应链订单退款审核返回值，result={}", JSON.toJSONString(result));
        return result;
    }

    private void refundMoney(Long orderId) {
        //调用归还金额接口 代付接口（需要新增退款流水记录）
        //通过订单号查询订单信息
        Result result = null;
        PurchaseSubOrder purchaseSubOrder = purchaseSubOrderMapper.selectByPrimaryKey(orderId);
        if (purchaseSubOrder.getPayment().byteValue() == 0) {
            //余额退款
            PayBalanceDTO payBalanceDTO = new PayBalanceDTO();
            payBalanceDTO.setUserId(purchaseSubOrder.getPurchaserId());
            payBalanceDTO.setTransCodeEnum(TransCodeEnum.USER_RE_STOCK);
            payBalanceDTO.setOrderId(orderId);
            payBalanceDTO.setOrderNo(purchaseSubOrder.getOrderNo());
            payBalanceDTO.setAmount(MoneyTransUtil.transDivis(purchaseSubOrder.getTotalCost()).toString());
            logger.info("调用余额归还金额接口,payBalanceDTO={}", JSON.toJSONString(payBalanceDTO));
            result = balanceService.backBalance(payBalanceDTO);
            logger.info("调用余额归还金额接口,result={}", JSON.toJSONString(result));
        } else if (purchaseSubOrder.getPayment().byteValue() == 1) {
            //银行退款
            //查询银行卡信息
            ManageBankCardInfo manageBankCardInfo = new ManageBankCardInfo();
            manageBankCardInfo.setUserId(purchaseSubOrder.getPurchaserId());
            Result bankCard = manageBankCardService.getBindCardInfo(manageBankCardInfo);
            logger.info("查询银行卡信息返回值，bankCard={}", JSON.toJSONString(bankCard));
            if (null !=bankCard && bankCard.isSuccess()) {
                ManageBankCardInfoVO vo = (ManageBankCardInfoVO) bankCard.getData();
                if (null == vo) {
                    throw new OrderException("退款人未绑定银行卡,退款失败");
                }
                DefrayDTO defrayDTO = new DefrayDTO(orderId.toString(),UniqueUtils.getBathNo(), vo.getCardNo(), vo.getOwnerName(),
                        purchaseSubOrder.getTotalCost().toString(), vo.getBankName(),
                        purchaseSubOrder.getOrderNo());
                logger.info("调用银行卡归还金额接口,defrayDTO={}", JSON.toJSONString(defrayDTO));
                result = cashService.defray(defrayDTO);
                logger.info("调用银行卡归还金额接口,result={}", JSON.toJSONString(result));
            } else {
                throw new OrderException("查询银行卡信息失败");
            }
        }
        if (!result.isSuccess()) {
            throw new OrderException("归还金额失败");
        }
    }

    @Override
    public Result getMainOrderById(Long mainOrderId) {
        try {
            logger.info("供应链进货主订单详情入参，orderId={}", mainOrderId);
            Result result = ResultUtil.getResult(OrderExceptionEnum.Code.FAIL);
            if (null != mainOrderId) {
                PurchaseMainOrder purchaseMainOrder = purchaseMainOrderMapper.selectByPrimaryKey(mainOrderId);
                PurchaseMainOrderVO vo = new PurchaseMainOrderVO();
                BeanUtils.copyProperties(purchaseMainOrder,vo);
                result = ResultUtil.getResult(OrderExceptionEnum.Code.SUCCESS,vo);
            } else {
                result.setMessage("主订单id不能为空");
            }
            logger.info("供应链进货主订单详情返回值，result={}", JSON.toJSONString(result));
            return result;
        } catch (Exception e) {
            logger.error("供应链进货主订单详情异常，e={}", e);
            throw new OrderException(OrderExceptionEnum.Code.INTERNAL_SERVER_ERROR);
        }
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
        List<PurchaseSubOrderProduct> list = purchaseSubOrderProductMapper.selPurSubOrdProBySubId(subOrderId);
        //归还商品数量
        Long currentSkuId = null;
        if (CollectionUtils.isNotEmpty(list)) {
            for (PurchaseSubOrderProduct purchaseSubOrderProduct : list) {
                currentSkuId = orderProductMapper.selectCurrentProductSkuId(purchaseSubOrderProduct.getProductId(),
                        purchaseSubOrderProduct.getSpecificationValIds());
                //修改sku数量后才修改商品总数
                if (orderProductMapper.addSkuCount(currentSkuId,purchaseSubOrderProduct.getSkuNum()) > 0) {
                    orderProductMapper.addProductCount(purchaseSubOrderProduct.getProductId(),purchaseSubOrderProduct.getSkuNum());
                }
            }
        }
    }


    /**
     * @methodname addSubOrderMdyHis 的描述：新增订单修改状态历史记录
     * @param updatePurOrderFreDTO
     * @param orderOperateEnum
     * @param freight
     * @return void
     * @author 郑朋
     * @create 2017/5/16
     */
    private void addSubOrderMdyHis(UpdatePurOrderFreDTO updatePurOrderFreDTO, OrderOperateEnum orderOperateEnum, BigDecimal freight){
        SubOrderMdyHis subOrderMdyHis = new SubOrderMdyHis();
        subOrderMdyHis.setCreateId(updatePurOrderFreDTO.getUpdaterId());
        subOrderMdyHis.setCreateTime(new Date());
        subOrderMdyHis.setOperateType(orderOperateEnum.getType());
        subOrderMdyHis.setSubOrderId(updatePurOrderFreDTO.getOrderId());
        subOrderMdyHis.setMoney(freight);
        subOrderMdyHis.setDescription("修改物流费用为：" + freight);
        subOrderMdyHisMapper.insert(subOrderMdyHis);
    }

    /**
     * @methodname addSubOrderMdyHis 的描述：新增订单修改状态历史记录
     * @param updateOrderStatusDTO
     * @param orderStatusEnum
     * @param orderOperateEnum
     * @param description
     * @return void
     * @author 郑朋
     * @create 2017/5/16
     */
    private void addSubOrderMdyHis(UpdateOrderStatusDTO updateOrderStatusDTO, OrderStatusEnum orderStatusEnum,
                                   OrderOperateEnum orderOperateEnum, String description){
        SubOrderMdyHis subOrderMdyHis = new SubOrderMdyHis();
        subOrderMdyHis.setCreateId(updateOrderStatusDTO.getUpdaterId());
        subOrderMdyHis.setCreateTime(new Date());
        subOrderMdyHis.setDealStatus(orderStatusEnum.getStatus());
        subOrderMdyHis.setOperateType(orderOperateEnum.getType());
        subOrderMdyHis.setStatus(updateOrderStatusDTO.getCurrentOrderStatus());
        subOrderMdyHis.setSubOrderId(updateOrderStatusDTO.getOrderId());
        subOrderMdyHis.setDescription(description);
        subOrderMdyHisMapper.insert(subOrderMdyHis);
    }

    /**
     * @methodname checkStock 的描述：库存校验
     * @param list
     * @return void
     * @author 郑朋
     * @create 2017/5/16
     */
    private void checkStock(List<PurchaseOrderDTO> list) {
        //子订单集合
        for (PurchaseOrderDTO purchaseOrderDTO : list) {
            //商品集合
            for (PurchaseProductDTO purchaseProductDTO : purchaseOrderDTO.getPurchaseProductList()) {
                int count = 0;
                //sku集合
                for (PurchaseProductSkuDTO purchaseProductSkuDTO : purchaseProductDTO.getPurchaseProductSkuList()) {
                    if (!reduceSkuStock(purchaseProductSkuDTO.getSkuId(), purchaseProductSkuDTO.getSkuNum())) {
                        String skuName = orderProductMapper.selectSkuNameById(purchaseProductSkuDTO.getSkuId());
                        String message =  "新增订单失败，商品名称为：" + purchaseProductDTO.getProductName() + ",规格名称为：" +
                                skuName +"库存不足，请重新选择";
                        throw new OrderException(message);
                    }
                    count += purchaseProductSkuDTO.getSkuNum();
                }
                //扣除商品总量和商品销售数量
                if (count > 0) {
                    Long productId = purchaseProductDTO.getProductId();
                    if (orderProductMapper.reduceProductCount(productId,count) <= 0) {
                        String message =  "新增订单失败，名称为：" + purchaseProductDTO.getProductName() + "的商品目前库存不足，请重新选择";
                        throw new OrderException(message);
                    }
                }

            }
        }
    }


    /**
     * @methodname reduceSkuStock 的描述：库存扣除（减去商品数量）
     * @param skuId
     * @param skuNum
     * @return boolean
     * @author 郑朋
     * @create 2017/5/16
     */
    private boolean reduceSkuStock(Long skuId, Integer skuNum) {
        //如果商品sku数量不足，则订单不能生成，返回提示信息
        boolean flag = false;
        int num = orderProductMapper.reduceSkuCount(skuId, skuNum);
        if (num >= 1) {
            flag = true;
        }
        return flag;
    }

    /**
     * @methodname addOrder 的描述：新增订单
     * @param list
     * @return java.lang.String
     * @author 郑朋
     * @create 2017/5/16
     */
    @SuppressWarnings("unchecked")
	private PurchaseMainOrderVO addOrder(List<PurchaseOrderDTO> list) {
        //封装订单数据
        Map<String, Object> map = dataEncapsulation(list);
        logger.info("供应链订单封装后数据，map={}",JSON.toJSONString(map));
        //新增主订单
        PurchaseMainOrder purchaseMainOrder = (PurchaseMainOrder) map.get("mainOrder");
        purchaseMainOrderMapper.insertUseGeneratedKeys(purchaseMainOrder);
        Long mainOrderId = purchaseMainOrder.getId();
        Long subOrderId;
        List<PurchaseSubOrderProduct> purchaseSubOrderProductList = null;
        List<PurchaseSubOrder> purchaseSubOrderList = (List<PurchaseSubOrder>) map.get("subOrder");
        for (PurchaseSubOrder purchaseSubOrder : purchaseSubOrderList) {
            //新增子订单
            purchaseSubOrder.setMainOrderId(mainOrderId);
            purchaseSubOrderMapper.insertUseGeneratedKeys(purchaseSubOrder);
            subOrderId = purchaseSubOrder.getId();
            purchaseSubOrderProductList = (List<PurchaseSubOrderProduct>) map.get(purchaseSubOrder.getOrderNo());
            //新增商品信息
            purchaseSubOrderProductMapper.insertPurchaseOrderProduct(purchaseSubOrderProductList,subOrderId);
        }
        PurchaseMainOrderVO vo = new PurchaseMainOrderVO();
        BeanUtils.copyProperties(purchaseMainOrder,vo);
        vo.setTotalCost(MoneyTransUtil.transMulti1(vo.getTotalCost()));
        return vo;
    }


    /**
     * @methodname getProductSku 的描述：查询商品sku信息
     * @param productId
     * @return com.ph.shopping.facade.product.entity.ProductSku
     * @author 郑朋
     * @create 2017/5/16
     */
    private List<ProductSku> getProductSku(Long productId) {
        List<ProductSku> productSkuList;
        ProductSku productSkuIn = new ProductSku();
        productSkuIn.setProductId(productId);
        logger.info("查询商品sku信息接口入参，phProductVo={}", JSON.toJSONString(productSkuIn));
        productSkuList= productSkuService.getProductSkuList(productSkuIn);
        logger.info("查询商品sku信息接口返回值，phProductVo={}",JSON.toJSONString(productSkuList));
        return productSkuList;
    }

    /**
     * @methodname dataEncapsulation 的描述：订单数据封装
     * @param list
     * @return java.util.Map<java.lang.String,java.lang.Object>
     * @author 郑朋
     * @create 2017/5/17
     */
    private Map<String, Object> dataEncapsulation(List<PurchaseOrderDTO> list) {
        Map<String, Object> map = new HashMap<>();
        //创建主订单号
        String mainOrderNo = OrderUtil.createOrderCode(OrderBizCode.MAIN_PURCHASE_GOODS);
        BigDecimal  orderTotalCost,  //一个子订单总费用（商品+物流）
                    orderProductTotalCost, //一个子订单商品总费用
                    totalFreight,  //一个子订单物流用
                    productTotalCost,  //一种商品总费用
                    productFreight, //一种商品的物流费用
                    skuCost,       //一个sku价格
                    skuFreight,   //一个sku的物流费
                    mainOrderCost,  //总订单费用（商品+物流）
                    mainOrderProductCost, //总订单商品费用
                    settleMoney ,//子订单结算价
                    settlementPrice,//商品结算价
                    price,  //商品单价
                    freightPrice; //商品物流费用

        List<PurchaseProductDTO> purchaseProductDTOList; //子订单对应的商品集合

        List<PurchaseProductSkuDTO> purchaseProductSkuDTOList; //商品对应的sku集合
        List<ProductSku> productSkuList; //商品对应的sku信息
        String subOrderNo;  //子订单号
        mainOrderCost = new BigDecimal(0);
        mainOrderProductCost = new BigDecimal(0);
        settleMoney =new BigDecimal(0);
        //子订单新增集合
        List<PurchaseSubOrder> purchaseSubOrderList = new ArrayList<>();
        //子订单商品集合
        List<PurchaseSubOrderProduct> purchaseSubOrderProductList;
        //子订单同一个商品集合
        List<PurchaseSubOrderProduct> sameProductDTOList;
        //一类商品的进货数量
        int productNum = 0;
        PurchaseSubOrderProduct purchaseSubOrderProduct;
        //循环订单信息
        for (PurchaseOrderDTO purchaseOrderDTO : list) {
            //商品信息
            purchaseProductDTOList = purchaseOrderDTO.getPurchaseProductList();
            purchaseSubOrderProductList = new ArrayList<>();
            //一个供应商生成一个订单
            totalFreight = new BigDecimal(0);
            orderProductTotalCost = new BigDecimal(0);
            //创建子订单号
            subOrderNo = OrderUtil.createOrderCode(OrderBizCode.PURCHASE_GOODS);
            //同一个供应商下的所有商品
            for (PurchaseProductDTO purchaseProductDTO : purchaseProductDTOList) {
                purchaseProductSkuDTOList = purchaseProductDTO.getPurchaseProductSkuList();
                //通过productId查询商品所有sku信息，如果查询失败，订单生成失败
                productSkuList = getProductSku(purchaseProductDTO.getProductId());
                productFreight = new BigDecimal(0);
                productTotalCost = new BigDecimal(0);
                sameProductDTOList = new ArrayList<>();
                productNum = 0;
                //从商品模块获取sku的信息在和页面传入的进行比较
                for (PurchaseProductSkuDTO purchaseProductSkuDTO : purchaseProductSkuDTOList) {
                    for (ProductSku productSku : productSkuList) {
                        if (purchaseProductSkuDTO.getSkuId().equals(productSku.getId())) {
                            price = new BigDecimal(0);
                            skuFreight = new BigDecimal(0);
                            skuCost = new BigDecimal(0);
                            purchaseSubOrderProduct = new PurchaseSubOrderProduct();
                            freightPrice = productSku.getFreight();
                            settlementPrice = productSku.getSettlementPrice();
                            //如果单价为空，则用页面传入的
                            if (purchaseOrderDTO.getPurchaseType().intValue() == 0) {
                                //商户进货
                                price = productSku.getPurchasePrice();
                            } else if (purchaseOrderDTO.getPurchaseType().intValue() == 1) {
                                //代理商进货
                                price = productSku.getSettlementPrice();
                            }
                            //copy商品的基本信息
                            BeanUtils.copyProperties(purchaseProductDTO,purchaseSubOrderProduct);
                            //skuId
                            purchaseSubOrderProduct.setSkuId(purchaseProductSkuDTO.getSkuId());
                            //sku数量
                            purchaseSubOrderProduct.setSkuNum(purchaseProductSkuDTO.getSkuNum());
                            //sku零售价
                            purchaseSubOrderProduct.setRetailPrice(MoneyTransUtil.transMulti(productSku.getRetailPrice()));
                            //sku结算价
                            purchaseSubOrderProduct.setSettlementPrice(MoneyTransUtil.transMulti(productSku.getSettlementPrice()));
                            //sku单价
                            purchaseSubOrderProduct.setPurchasePrice(MoneyTransUtil.transMulti(productSku.getPurchasePrice()));
                            //sku物流费
                            purchaseSubOrderProduct.setSkuFreight(MoneyTransUtil.transMulti(productSku.getFreight()));
                            //包邮数量
                            purchaseSubOrderProduct.setNumberOfPackages(productSku.getNumberOfPackages());
                            purchaseSubOrderProduct.setSpecificationValIds(productSku.getSpecificationValIds());
                            purchaseSubOrderProduct.setSkuName(productSku.getSkuName());
                            //单个sku价格
                            skuCost = price.multiply(new BigDecimal(purchaseProductSkuDTO.getSkuNum()));
                            //单个sku物流费用
                            skuFreight = freightPrice.multiply(new BigDecimal(purchaseProductSkuDTO.getSkuNum()));
                            //下单数量大于包邮数量，邮费为0
                            if (null != productSku.getNumberOfPackages() && purchaseProductSkuDTO.getSkuNum().intValue() >= productSku.getNumberOfPackages().intValue()) {
                                skuFreight = new BigDecimal(0);
                            }
                            settleMoney = settleMoney.add(settlementPrice.multiply(new BigDecimal(purchaseProductSkuDTO.getSkuNum())));
                            //一种商品的总价
                            productTotalCost = productTotalCost.add(skuCost);
                            //一种商品物流费用
                            productFreight = productFreight.add(skuFreight);
                            //一种商品总数量
                            productNum += purchaseProductSkuDTO.getSkuNum();
                            sameProductDTOList.add(purchaseSubOrderProduct);
                        }
                    }
                }
                //子订单物流总价
                totalFreight = totalFreight.add(productFreight);
                //子订单商品的总价
                orderProductTotalCost = orderProductTotalCost.add(productTotalCost);
                if (CollectionUtils.isNotEmpty(sameProductDTOList)) {
                    //回写商品总价格，总物流费等信息
                    for (PurchaseSubOrderProduct product : sameProductDTOList) {
                        product.setTotalFreight(MoneyTransUtil.transMulti(productFreight));
                        product.setTotalMoney(MoneyTransUtil.transMulti(productTotalCost));
                        product.setProductNum(productNum);
                        purchaseSubOrderProductList.add(product);
                    }
                }
            }
            map.put(subOrderNo,purchaseSubOrderProductList); //子订单对应的商品信息
           /* logger.info("子订单供应商id = " + purchaseOrderDTO.getSupplerId() +
                            "传入商品总价格 money = " + purchaseOrderDTO.getMoney().doubleValue() +
                            "计算后的价格 orderProductTotalCost = " + orderProductTotalCost.doubleValue() +
                            "传入商品物流费用 freight = " + purchaseOrderDTO.getFreight().doubleValue() +
                            "计算后的物流费用 totalFreight = " + totalFreight.doubleValue()
                            );
            //判断后台传入总金额和物流费用是否和计算后的一致，如果不一致，订单生成出错
                if (purchaseOrderDTO.getMoney().doubleValue() != orderProductTotalCost.doubleValue() ||
                    purchaseOrderDTO.getFreight().doubleValue() != totalFreight.doubleValue() ) {
                throw new OrderException("订单选择的商品价格有修改，请重新刷新后进行下单~");
            }*/

            //子订单对象数据封装
            PurchaseSubOrder purchaseSubOrder = new PurchaseSubOrder();
            BeanUtils.copyProperties(purchaseOrderDTO,purchaseSubOrder);
            purchaseSubOrder.setMoney(MoneyTransUtil.transMulti(orderProductTotalCost));
            purchaseSubOrder.setFreight(MoneyTransUtil.transMulti(totalFreight));
            purchaseSubOrder.setReferenceFreight(MoneyTransUtil.transMulti(totalFreight));
            purchaseSubOrder.setSettleMoney(MoneyTransUtil.transMulti(settleMoney));
            orderTotalCost = orderProductTotalCost.add(totalFreight);
            purchaseSubOrder.setTotalCost(MoneyTransUtil.transMulti(orderTotalCost));
            purchaseSubOrder.setCreateTime(new Date());
            purchaseSubOrder.setCreaterId(purchaseOrderDTO.getPurchaserId());
            purchaseSubOrder.setStatus(OrderStatusEnum.UNPAID_ORDER.getStatus());
            purchaseSubOrder.setIsSettle(OrderIsSettleEnum.IS_SETTLE_NOT.getIsSettle());
            purchaseSubOrder.setIsProfit(OrderIsSettleEnum.IS_SETTLE_NOT.getIsSettle());
            purchaseSubOrder.setOrderNo(subOrderNo);
            purchaseSubOrderList.add(purchaseSubOrder);
            //主订单的总价
            mainOrderCost = mainOrderCost.add(orderTotalCost);
            //主订单的总价
            mainOrderProductCost = mainOrderProductCost.add(orderProductTotalCost);
        }
        map.put("subOrder",purchaseSubOrderList); //子订单
        //主订单数据封装
        PurchaseMainOrder purchaseMainOrder = new PurchaseMainOrder();
        purchaseMainOrder.setOrderNo(mainOrderNo);
        purchaseMainOrder.setMoney(MoneyTransUtil.transMulti(mainOrderProductCost));
        purchaseMainOrder.setTotalCost(MoneyTransUtil.transMulti(mainOrderCost));
        purchaseMainOrder.setCreateTime(new Date());
        purchaseMainOrder.setPurchaserId(list.get(0).getPurchaserId());
        map.put("mainOrder",purchaseMainOrder); //主订单
        return map;
    }
}
