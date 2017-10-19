package com.ph.order.api.service;

import cm.ph.shopping.facade.order.dto.*;
import cm.ph.shopping.facade.order.service.IPurchaseOrderService;
import cm.ph.shopping.facade.order.vo.PurchaseMainOrderVO;
import cm.ph.shopping.facade.order.vo.PurchaseSubOrderDetailVO;
import cm.ph.shopping.facade.order.vo.PurchaseSubOrderPageVO;
import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.ph.order.api.controller.vo.PurchasePageVO;
import com.ph.shopping.common.core.constant.PageConstant;
import com.ph.shopping.common.util.core.RespCode;
import com.ph.shopping.common.util.core.ResultUtil;
import com.ph.shopping.common.util.page.PageBean;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.common.util.transform.MoneyTransUtil;
import com.ph.shopping.facade.spm.vo.AgentVO;
import com.ph.shopping.facade.spm.vo.MerchantVO;
import com.ph.shopping.facade.spm.vo.SupplierVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import java.util.ArrayList;
import java.util.List;

/**
 * 供应链订单service
 *
 * @author 郑朋
 * @create 2017/5/26
 **/
@Service
public class PurchaseService {

    private static final Logger logger = LoggerFactory.getLogger(PurchaseService.class);

    @Reference(version = "1.0.0",retries = 0)
    IPurchaseOrderService purchaseOrderService;

    @Autowired
    SpmService spmService;

    /**
     * @methodname getPurchaseOrder 的描述：查询订单列表
     * @param pageBean
     * @param queryPurchaseOrderDTO
     * @param type
     * @return com.ph.shopping.common.util.result.Result
     * @author 郑朋
     * @create 2017/5/26
     */
    @SuppressWarnings("unchecked")
	public Result getPurchaseOrder(PageBean pageBean, QueryPurchaseOrderDTO queryPurchaseOrderDTO, int type) {
        Result result = new Result();
        logger.info("供货订单列表接口调用入参: pageBean={},queryPurchaseOrderDTO={}", JSON.toJSONString(pageBean),JSON.toJSONString(queryPurchaseOrderDTO));
        if (type == 0) {
            pageBean.setPageSize(pageBean.getPageSize() == 0 ? PageConstant.pageSize : pageBean.getPageSize());
            pageBean.setPageNum(pageBean.getPageNum() == 0 ? PageConstant.pageNum : pageBean.getPageNum());
            //列表
            result = purchaseOrderService.getPurchaseOrderListByPage(pageBean,queryPurchaseOrderDTO);
        } else if (type == 1) {
            //导出
            result = purchaseOrderService.getPurchaseOrderList(queryPurchaseOrderDTO);
        }
        logger.info("供货订单列表接口调用返回值: result={}",JSON.toJSONString(result));
        List<PurchaseSubOrderPageVO> list =  (List<PurchaseSubOrderPageVO>)result.getData();
        PurchasePageVO purchasePageVO;
        List<PurchasePageVO> purchasePageVOList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(list)) {
            String purchaseName = "";
            for (int i = 0; i < list.size(); i++) {
                PurchaseSubOrderPageVO purchaseSubOrderPageVO = list.get(i);
                purchaseSubOrderPageVO.setMoney(MoneyTransUtil.transMulti1(purchaseSubOrderPageVO.getMoney()));
                purchaseSubOrderPageVO.setFreight(MoneyTransUtil.transMulti1(purchaseSubOrderPageVO.getFreight()));
                purchaseSubOrderPageVO.setTotalCost(MoneyTransUtil.transMulti1(purchaseSubOrderPageVO.getTotalCost()));
                purchasePageVO = new PurchasePageVO();
                BeanUtils.copyProperties(purchaseSubOrderPageVO,purchasePageVO);
                purchaseName = spmService.getPurchaseName(purchaseSubOrderPageVO.getPurchaseType(),purchaseSubOrderPageVO.getPurchaserId());
                purchasePageVO.setPurchaseName(purchaseName);
                List<SupplierVO> temp = spmService.getSupplierByUser(purchaseSubOrderPageVO.getSupplerId());
                if (CollectionUtils.isNotEmpty(temp)) {
                    purchasePageVO.setSupplierName(temp.get(0).getSupplierName());
                }
                purchasePageVOList.add(purchasePageVO);
            }
        }
        return ResultUtil.getResult(RespCode.Code.SUCCESS,purchasePageVOList,result.getCount());
    }


    /**
     * @methodname detail 的描述：查询订单详情
     * @param model
     * @param subOrderId
     * @return java.lang.String
     * @author 郑朋
     * @create 2017/5/23
     */
    public void detail(Model model, Long subOrderId) {
        PurchaseSubOrderDetailVO vo = getDetail(subOrderId);
        if (null != vo) {
            Long purchaserId = vo.getPurchaserId();
            Long senderId = vo.getSenderId();
            Long supplerId = vo.getSupplerId();
            if (vo.getPurchaseType() == 0) {
                //商户进货
                List<MerchantVO> list = spmService.getMerchant(purchaserId);
                if (CollectionUtils.isNotEmpty(list)) {
                    model.addAttribute("purchaseName",list.get(0).getMerchantName());
                }
                List<AgentVO> agentVOList = spmService.getAgentByUser(senderId);
                if (CollectionUtils.isNotEmpty(agentVOList)) {
                    model.addAttribute("senderName",agentVOList.get(0).getAgentName());
                }
            } else if (vo.getPurchaseType() == 1) {
                //市级代理进货
                List<AgentVO> agentVOList = spmService.getAgentByUser(purchaserId);
                if (CollectionUtils.isNotEmpty(agentVOList)) {
                    model.addAttribute("purchaseName",agentVOList.get(0).getAgentName());
                }
                List<SupplierVO> supplierVOList = spmService.getSupplierByUser(senderId);
                if (CollectionUtils.isNotEmpty(supplierVOList)) {
                    model.addAttribute("senderName",supplierVOList.get(0).getSupplierName());
                }
            }
            List<SupplierVO> supplierVOList = spmService.getSupplierByUser(supplerId);
            if (CollectionUtils.isNotEmpty(supplierVOList)) {
                model.addAttribute("supplerName",supplierVOList.get(0).getSupplierName());
            }
        }
        model.addAttribute("purchaseOrder", vo);
    }

    /**
     * @methodname getDetail 的描述：查询订单详情
     * @param subOrderId
     * @return cm.ph.shopping.facade.order.vo.PurchaseSubOrderDetailVO
     * @author 郑朋
     * @create 2017/6/2
     */
    public PurchaseSubOrderDetailVO getDetail(Long subOrderId) {
        logger.info("查询订单详情接口入参：subOrderId={}", subOrderId);
        Result result = purchaseOrderService.getPurchaseOrderDetail(subOrderId);
        PurchaseSubOrderDetailVO vo = (PurchaseSubOrderDetailVO) result.getData();
        logger.info("查询订单详情接口返回值：result={}", JSON.toJSONString(result));
        return vo;
    }

    /**
     * @methodname getMainOrder 的描述：查询主订单详情
     * @param mainOrderId
     * @return cm.ph.shopping.facade.order.vo.PurchaseMainOrderVO
     * @author 郑朋
     * @create 2017/6/2
     */
    public PurchaseMainOrderVO getMainOrder(Long mainOrderId) {
        logger.info("查询主订单详情接口入参：mainOrderId={}", mainOrderId);
        Result result = purchaseOrderService.getMainOrderById(mainOrderId);
        PurchaseMainOrderVO vo = (PurchaseMainOrderVO) result.getData();
        logger.info("查询主订单详情接口返回值：result={}", JSON.toJSONString(result));
        return vo;
    }

    /**
     * @methodname sku 的描述：查询商品sku详情
     * @param productId
     * @return java.lang.String
     * @author 郑朋
     * @create 2017/5/23
     */
    public Result sku(Long subOrderId,Long productId) {
        logger.info("查询订单详情中商品sku接口入参：productId={}", productId);
        Result result = purchaseOrderService.getPurchaseOrderProductSku(subOrderId,productId);
        logger.info("查询订单详情中商品sku接口返回值：result={}", JSON.toJSONString(result));
        return result;
    }

    /**
     * @methodname refundDetail 的描述：查看退款申请详情
     * @param orderId
     * @return com.ph.shopping.common.util.result.Result
     * @author 郑朋
     * @create 2017/5/26
     */
    public Result refundDetail(Long orderId) {
        logger.info("查看退款申请详情接口入参：orderId={}", orderId);
        Result result = purchaseOrderService.getRefundApplication(orderId);
        logger.info("查看退款申请详情返回值：result={}", JSON.toJSONString(result));
        return result;
    }

    /**
     * @methodname refundDetail 的描述：取消订单
     * @param updateOrderStatusDTO
     * @return com.ph.shopping.common.util.result.Result
     * @author 郑朋
     * @create 2017/5/26
     */
    public Result cancelOrder(UpdateOrderStatusDTO updateOrderStatusDTO) {
        logger.info("取消订单接口入参：updateOrderStatusDTO={}", JSON.toJSONString(updateOrderStatusDTO));
        Result result = purchaseOrderService.cancelOrder(updateOrderStatusDTO);
        logger.info("取消订单返回值：result={}", JSON.toJSONString(result));
        return result;
    }


    /**
     * @methodname confirmReceipt 的描述：确认收货
     * @param updateOrderStatusDTO
     * @return com.ph.shopping.common.util.result.Result
     * @author 郑朋
     * @create 2017/5/26
     */
    public Result confirmReceipt(UpdateOrderStatusDTO updateOrderStatusDTO) {
        logger.info("确认收货接口入参：updateOrderStatusDTO={}", JSON.toJSONString(updateOrderStatusDTO));
        Result result = purchaseOrderService.confirmReceipt(updateOrderStatusDTO);
        logger.info("确认收货返回值：result={}", JSON.toJSONString(result));
        return result;
    }
    /**
     * @methodname addRefund 的描述：新增订单退款申请
     * @param refundApplicationDTO
     * @return com.ph.shopping.common.util.result.Result
     * @author 郑朋
     * @create 2017/5/26
     */
    public Result addRefund(RefundApplicationDTO refundApplicationDTO) {
        logger.info("新增订单退款申请接口入参：refundApplicationDTO={}", JSON.toJSONString(refundApplicationDTO));
        Result result = purchaseOrderService.addRefundApplication(refundApplicationDTO);
        logger.info("新增订单退款申请返回值：result={}", JSON.toJSONString(result));
        return result;
    }

    /**
     * @methodname checkRefund 的描述：审核订单退款申请
     * @param checkRefundAppliDTO
     * @return com.ph.shopping.common.util.result.Result
     * @author 郑朋
     * @create 2017/5/26
     */
    public Result checkRefund(CheckRefundAppliDTO checkRefundAppliDTO) {
        logger.info("审核订单退款申请接口入参：checkRefundAppliDTO={}", JSON.toJSONString(checkRefundAppliDTO));
        Result result = purchaseOrderService.checkRefundApplication(checkRefundAppliDTO);
        logger.info("审核订单退款申请返回值：result={}", JSON.toJSONString(result));
        return result;
    }

    /**
     * @methodname updateFreight 的描述：修改物流费用
     * @param updatePurOrderFreDTO
     * @return com.ph.shopping.common.util.result.Result
     * @author 郑朋
     * @create 2017/5/26
     */
    public Result updateFreight(UpdatePurOrderFreDTO updatePurOrderFreDTO) {
        logger.info("修改物流费用接口入参：updatePurOrderFreDTO={}", JSON.toJSONString(updatePurOrderFreDTO));
        Result result = purchaseOrderService.updatePurchaseOrderFreight(updatePurOrderFreDTO);
        logger.info("修改物流费用返回值：result={}", JSON.toJSONString(result));
        return result;
    }

    /**
     * @methodname sendProduct 的描述：订单立即发货
     * @param sendProductDTO
     * @return com.ph.shopping.common.util.result.Result
     * @author 郑朋
     * @create 2017/6/2
     */
    public Result sendProduct(SendProductDTO sendProductDTO ) {
        logger.info("立即发货接口入参：updatePurOrderFreDTO={}", JSON.toJSONString(sendProductDTO));
        Result result =  purchaseOrderService.sendProduct(sendProductDTO);
        logger.info("立即发货返回值：result={}", JSON.toJSONString(result));
        return result;
    }

}
