package com.ph.order.api.controller;

import cm.ph.shopping.facade.order.constant.OrderPurchaseType;
import cm.ph.shopping.facade.order.constant.OrderStatusEnum;
import cm.ph.shopping.facade.order.dto.*;
import cm.ph.shopping.facade.order.vo.PurchaseSubOrderDetailVO;
import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.ph.base.BaseController;
import com.ph.order.api.controller.qo.PurchaseOrderQO;
import com.ph.order.api.controller.vo.PurchasePageVO;
import com.ph.order.api.controller.vo.PurchaseUserVO;
import com.ph.order.api.service.ProductService;
import com.ph.order.api.service.PurchaseOrderDataService;
import com.ph.order.api.service.PurchaseService;
import com.ph.order.api.service.SpmService;
import com.ph.queue.OrderTask;
import com.ph.queue.request.OrderTaskRequest;
import com.ph.shopping.common.core.cache.redis.ICacheService;
import com.ph.shopping.common.core.config.pay.PayConfiguration;
import com.ph.shopping.common.core.customenum.TransCodeEnum;
import com.ph.shopping.common.util.excel.ExcelBean;
import com.ph.shopping.common.util.excel.ExcelUtil;
import com.ph.shopping.common.util.page.PageBean;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.pay.dto.PayBalanceDTO;
import com.ph.shopping.facade.pay.service.IBalanceService;
import com.ph.shopping.facade.permission.vo.SessionUserVO;
import com.ph.shopping.facade.product.entity.ProductSku;
import com.ph.shopping.facade.spm.constant.SupplierTypeConstantEnum;
import com.ph.shopping.facade.spm.vo.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 供应链进货订单
 *
 * @author 郑朋
 * @create 2017/5/18
 **/
@Controller
@RequestMapping("web/order/purchase/")
public class PurchaseOrderController extends BaseController {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    PurchaseOrderDataService purchaseOrderDataService;

    @Autowired
    SpmService spmService;

    @Autowired
    PurchaseService purchaseService;

    @Autowired
    ProductService productService;

    @Autowired
    OrderTask orderTask;

    @Autowired
    ICacheService<String,String> cacheService;

    @Autowired
    PayConfiguration payConfiguration;

    @Reference(version = "1.0.0" ,retries = 0)
    IBalanceService balanceService;


    /**
     * @methodname listPage 的描述：进入进货订单列表
     * @param model
     * @return java.lang.String
     * @author 郑朋
     * @create 2017/5/23
     */
    @RequestMapping(value = "toListPage", method = {RequestMethod.GET})
    public String purchaseListPage(Model model) {
        listPage(model);
        return "order/purchase/list";
    }

    /**
     * @methodname refundListPage 的描述：进入退款订单列表
     * @param model
     * @return java.lang.String
     * @author 郑朋
     * @create 2017/5/23
     */
    @RequestMapping(value = "refund/toListPage", method = {RequestMethod.GET})
    public String refundListPage(Model model) {
        listPage(model);
        return "order/purchase/refundList";
    }


    private void listPage(Model model) {
        SessionUserVO userVO = getLoginUser();
        Long userId = userVO.getId();
        Byte roleCode = userVO.getSessionRoleVo().get(0).getRoleCode();
        List<SupplierVO> list = new ArrayList<>();
        List<PurchaseUserVO> purchaseUserVoList = new ArrayList<>();
        PurchaseUserVO purchaseUserVo = new PurchaseUserVO();
        switch (roleCode) {
            case 1 :
                //平台
                //供应商:查询所有的供应商
                list = spmService.getSupplierByUser(null);
                //进货商:查询所有的商户和市级代理商
                purchaseUserVoList = spmService.getPurchaseUserVo();
                break;
            case 3 :
                //市级代理
                //供应商:平台添加+自己添加
                list = spmService.getSupplierByType(Byte.valueOf(SupplierTypeConstantEnum.SUPPLIER_TYPE_TOTAL.getCode()));
                list.addAll(spmService.getSupplierByCreateId(userId));
                //进货商:市级代理
                List<AgentVO> agentVO = spmService.getAgentByUser(userId);
                purchaseUserVo.setId(agentVO.get(0).getUserId());
                purchaseUserVo.setName(agentVO.get(0).getAgentName());
                purchaseUserVoList.add(purchaseUserVo);
                break;
            case 6 :
                //商户
                //供应商:平台添加+自己添加
                list = spmService.getSupplierByType(Byte.valueOf(SupplierTypeConstantEnum.SUPPLIER_TYPE_TOTAL.getCode()));
                list.addAll(spmService.getSupplierByCreateId(userId));
                //进货商:商户
                List<MerchantVO> merchantVO = spmService.getMerchant(userId);
                purchaseUserVo.setId(merchantVO.get(0).getUserId());
                purchaseUserVo.setName(merchantVO.get(0).getMerchantName());
                purchaseUserVoList.add(purchaseUserVo);
                break;
        }
        model.addAttribute("roleCode",roleCode);
        model.addAttribute("supplier",list);
        model.addAttribute("purchaseUser",purchaseUserVoList);
    }




    /**
     * @methodname list 的描述：进货订单列表
     * @param pageBean
     * @param purchaseOrderQO
     * @return java.lang.String
     * @author 郑朋
     * @create 2017/5/23
     */
    @RequestMapping(value = "list", method = {RequestMethod.POST})
    @ResponseBody
    public Object list(PageBean pageBean, PurchaseOrderQO purchaseOrderQO) throws ParseException {
        return getList(pageBean,purchaseOrderQO,0);
    }

    /**
     * @methodname refundList 的描述：进货退换订单列表
     * @param pageBean
     * @param purchaseOrderQO
     * @return java.lang.String
     * @author 郑朋
     * @create 2017/5/23
     */
    @RequestMapping(value = "refund/list", method = {RequestMethod.POST})
    @ResponseBody
    public Object refundList(PageBean pageBean, PurchaseOrderQO purchaseOrderQO) throws ParseException {
        return getList(pageBean,purchaseOrderQO,0);
    }


    /**
     * @methodname export 的描述：进货订单列表导出
     * @param purchaseOrderQO
     * @return java.lang.String
     * @author 郑朋
     * @create 2017/5/23
     */
    @RequestMapping(value = "export", method = {RequestMethod.POST})
    @SuppressWarnings("unchecked")
    public void export(HttpServletRequest request, HttpServletResponse response, PurchaseOrderQO purchaseOrderQO) throws Exception {
        Result result = getList(null, purchaseOrderQO,1);
        List<PurchasePageVO> purchasePageVOList = (List<PurchasePageVO>) result.getData();
        String[] tableHeads = { "订单号","下单时间","供应商", "进货商", "商品金额(元)", "物流费用(元)", "订单总额(元)", "付款方式", "订单状态"};
        ExcelBean excelBean = purchaseOrderDataService.getPurchaseOrder(purchasePageVOList,"进货订单数据列表",1,tableHeads);
        ExcelUtil.download(request, response, excelBean);
    }


    private Result getList(PageBean pageBean, PurchaseOrderQO purchaseOrderQO,int type) throws ParseException {
        QueryPurchaseOrderDTO queryPurchaseOrderDTO = new QueryPurchaseOrderDTO();
        BeanUtils.copyProperties(purchaseOrderQO,queryPurchaseOrderDTO);
        if (StringUtils.isNotEmpty(purchaseOrderQO.getCreateTimeStr())) {
            queryPurchaseOrderDTO.setCreateTimeStr(sdf.parse(purchaseOrderQO.getCreateTimeStr() + " 00:00:00"));
        }
        if (StringUtils.isNotEmpty(purchaseOrderQO.getCreateTimeEnd())) {
            queryPurchaseOrderDTO.setCreateTimeEnd(sdf.parse(purchaseOrderQO.getCreateTimeEnd() + " 23:59:59"));
        }
        return purchaseService.getPurchaseOrder(pageBean,queryPurchaseOrderDTO,type);
    }

    /**
     * @methodname refundExport 的描述：进货订单列表
     * @param purchaseOrderQO
     * @return java.lang.String
     * @author 郑朋
     * @create 2017/5/23
     */
    @RequestMapping(value = "refund/export", method = {RequestMethod.POST})
    @SuppressWarnings("unchecked")
    public void refundExport(HttpServletRequest request, HttpServletResponse response, PurchaseOrderQO purchaseOrderQO) throws Exception {
        Result result = getList(null, purchaseOrderQO,1);
        List<PurchasePageVO> purchasePageVOList = (List<PurchasePageVO>) result.getData();
        String[] tableHeads = { "订单号","下单时间","供应商", "进货商", "商品金额(元)", "物流费用(元)", "订单总额(元)", "付款方式", "退款状态"};
        ExcelBean excelBean = purchaseOrderDataService.getPurchaseOrder(purchasePageVOList,"进货退换订单数据列表",2,tableHeads);
        ExcelUtil.download(request, response, excelBean);
    }


    /**
     * @methodname detail 的描述：查询订单详情
     * @param model
     * @param subOrderId
     * @param type
     * @return java.lang.String
     * @author 郑朋
     * @create 2017/5/23
     */
    @RequestMapping(value = "detail", method = {RequestMethod.GET, RequestMethod.POST})
    public String detail(Model model, Long subOrderId,Integer type) {
        purchaseService.detail(model,subOrderId);
        model.addAttribute("type",type);
        return "order/purchase/detail";
    }



    /**
     * @methodname refundDetail 的描述：查询订单详情
     * @param model
     * @param subOrderId
     * @param type
     * @return java.lang.String
     * @author 郑朋
     * @create 2017/5/23
     */
    @RequestMapping(value = "refund/OrderDetail", method = {RequestMethod.GET, RequestMethod.POST})
    public String refundDetail(Model model, Long subOrderId,Integer type) {
        purchaseService.detail(model,subOrderId);
        model.addAttribute("type",type);
        return "order/purchase/detail";
    }

    /**
     * @methodname sku 的描述：查询商品sku详情
     * @param productId
     * @return java.lang.String
     * @author 郑朋
     * @create 2017/5/23
     */
    @RequestMapping(value = "sku", method = {RequestMethod.POST})
    @ResponseBody
    public Result sku(Long subOrderId,Long productId) {
        return purchaseService.sku(subOrderId,productId);
    }

    /**
     * @methodname refundDetail 的描述：查看退款申请详情
     * @param orderId
     * @return com.ph.shopping.common.util.result.Result
     * @author 郑朋
     * @create 2017/5/26
     */
    @RequestMapping("refund/detail")
    @ResponseBody
    public Result refundDetail(Long orderId) {
        return purchaseService.refundDetail(orderId);
    }


    /**
     * @methodname cancelOrder 的描述：取消订单
     * @param updateOrderStatusDTO
     * @return com.ph.shopping.common.util.result.Result
     * @author 郑朋
     * @create 2017/5/26
     */
    @RequestMapping("cancelOrder")
    @ResponseBody
    public Result cancelOrder(UpdateOrderStatusDTO updateOrderStatusDTO) {
        SessionUserVO userVO = getLoginUser();
        Long userId = userVO.getId();
        updateOrderStatusDTO.setUpdaterId(userId);
        return purchaseService.cancelOrder(updateOrderStatusDTO);
    }


    /**
     * @methodname receipt 的描述：确认收货
     * @param updateOrderStatusDTO
     * @return com.ph.shopping.common.util.result.Result
     * @author 郑朋
     * @create 2017/5/26
     */
    @RequestMapping("receipt")
    @ResponseBody
    public Result receipt(UpdateOrderStatusDTO updateOrderStatusDTO) {
        SessionUserVO userVO = getLoginUser();
        Long userId = userVO.getId();
        updateOrderStatusDTO.setUpdaterId(userId);
        return purchaseService.confirmReceipt(updateOrderStatusDTO);
    }

    /**
     * @methodname addRefund 的描述：新增订单退款申请
     * @param refundApplicationDTO
     * @return com.ph.shopping.common.util.result.Result
     * @author 郑朋
     * @create 2017/5/26
     */
    @RequestMapping("refund/add")
    @ResponseBody
    public Result addRefund(RefundApplicationDTO refundApplicationDTO) {
        SessionUserVO userVO = getLoginUser();
        Long userId = userVO.getId();
        refundApplicationDTO.setCreaterId(userId);
        return purchaseService.addRefund(refundApplicationDTO);
    }

    /**
     * @methodname getAddress 的描述：通过userId查询仓库地址
     * @param
     * @return com.ph.shopping.common.util.result.Result
     * @author 郑朋
     * @create 2017/5/26
     */
    @RequestMapping("address")
    @ResponseBody
    public List<WarehouseAddressVO> getAddress() {
        SessionUserVO userVO = getLoginUser();
        Long userId = userVO.getId();
        return spmService.getAddress(userId);
    }


    /**
     * @methodname toAdd 的描述：进入新增订单页面
     * @param model
     * @param ids
     * @return java.lang.String
     * @author 郑朋
     * @create 2017/5/27
     */
    @RequestMapping(value = {"toLocalOneAdd","toLocalAdd","toCountryOneAdd","toCountryAdd"},method = RequestMethod.POST)
    public String toAdd(Model model,String ids) {
        SessionUserVO userVO = getLoginUser();
        Long userId = userVO.getId();
        List<WarehouseAddressVO> warehouseAddressVOS = spmService.getAddress(userId);
        model.addAttribute("addressList", warehouseAddressVOS);
        model.addAttribute("roleCode", userVO.getSessionRoleVo().get(0).getRoleCode());
        if (StringUtils.isNotEmpty(ids)) {
				String[] productIds = ids.split(",");
				List<Long> paramsList = new ArrayList<>();
				for (String id : productIds) {
					paramsList.add(Long.valueOf(id));
				}
            //查询商品信息
            model.addAttribute("productList",productService.getProduct(paramsList));
        }
        return "order/purchase/add";
    }


    /**
     * @methodname productSku 的描述：通过商品id查询商品sku信息
     * @param productId
     * @return java.lang.String
     * @author 郑朋
     * @create 2017/5/27
     */
    @RequestMapping(value = "productSku",method = RequestMethod.POST)
    @ResponseBody
    public List<ProductSku> productSku(Long productId) {
        return productService.getProductSku(productId);
    }


    /**
     * @methodname addPurchaseOrder 的描述：新增订单
     * @param addPurchaseOrderDTO
     * @return com.ph.shopping.common.util.result.Result
     * @author 郑朋
     * @create 2017/6/2
     */
    @RequestMapping(value = "add",method = RequestMethod.POST)
    @ResponseBody
    public Result addPurchaseOrder(@RequestBody AddPurchaseOrderDTO addPurchaseOrderDTO) {
        SessionUserVO userVO = getLoginUser();
        Long userId = userVO.getId();
        Byte roleCode = userVO.getSessionRoleVo().get(0).getRoleCode();
        Long senderId = null;
        Byte purchaseType = (byte)0;
        /**
         *  如果是商户进货 供货人为商户市级代理
         *  如果是代理商进货 供货人为代理商
         */
        if (roleCode == 3) {
            purchaseType = OrderPurchaseType.AGENT_PURCHASE;
        } else if (roleCode == 6) {
            AgentVO vo = spmService.getCityAgentVo(userId);
            if (null != vo && null != vo.getUserId()) {
                senderId = vo.getUserId();
            } else {
                return new Result(false,"300","商户市级代理不存在");
            }
        }
        if (CollectionUtils.isNotEmpty(addPurchaseOrderDTO.getPurchaseOrderDTO())) {
            for (PurchaseOrderDTO purchaseOrderDTO :addPurchaseOrderDTO.getPurchaseOrderDTO()) {
                if (roleCode == 3) {
                    senderId = purchaseOrderDTO.getSupplerId();
                }
                purchaseOrderDTO.setPurchaseType(purchaseType);
                purchaseOrderDTO.setPurchaserId(userId);
                purchaseOrderDTO.setSenderId(senderId);
                purchaseOrderDTO.setStatus(OrderStatusEnum.UNPAID_ORDER.getStatus());
            }
        }
        //下单队列
        OrderTaskRequest<AddPurchaseOrderDTO> queueRequest = new OrderTaskRequest<>();
        queueRequest.setData(addPurchaseOrderDTO);
        return orderTask.callSubmit(queueRequest);
    }

    /**
     * @methodname pay 的描述：跳转到预支付页面
     * @param model
     * @param orderId
     * @param totalCost
     * @param type 1 主订单 2 子订单
     * @param orderNo
     * @return java.lang.String
     * @author 郑朋
     * @create 2017/6/2
     */
    @RequestMapping(value = "toPay" ,method = RequestMethod.POST)
    public String toPay(Model model, Long orderId, BigDecimal totalCost,Byte type,String orderNo) {
        Long userId = getLoginUser().getId();
        String money = "0.00";
        BalanceVO balanceVO = spmService.getUserBalance(userId);
        if (type == 2) {
            //查询子订单信息
            PurchaseSubOrderDetailVO vo = purchaseService.getDetail(orderId);
            orderNo = vo.getOrderNo();
            money = vo.getTotalCost().toString();
        } else if (type == 1) {
            money = totalCost.toString();
        }
        String description = "供应链进货订单";
        StringBuilder sb = new StringBuilder();
        sb.append("{\"amount\":").append("\"").append(money).append("\"").append(",")
                .append("\"description\":").append("\"").append(description).append("\"")
                .append("}");
        //redis中缓存支付信息，金额和描述(第三方支付用)
        cacheService.set(orderNo,sb.toString());
        //redis中缓存支付信息，金额(余额支付)
        cacheService.set(getKey(orderNo),money);
        model.addAttribute("balance",balanceVO.getScore().toString());
        model.addAttribute("totalCost",money);
        model.addAttribute("orderNo",orderNo);
        model.addAttribute("description",description);
        model.addAttribute("pay",payConfiguration.getEcoPay());
        return "order/purchase/pay";
    }


    private String getKey(String orderNo) {
        return "pay:" + orderNo;
    }

    /**
     * @methodname pay 的描述：余额支付
     * @param orderNo
     * @param password
     * @return java.lang.String
     * @author 郑朋
     * @create 2017/6/2
     */
    @RequestMapping(value = "pay" ,method = RequestMethod.POST)
    @ResponseBody
    public Result pay(String password, String orderNo) {
        SessionUserVO sessionUserVO = getLoginUser();
        Long userId = sessionUserVO.getId();
        Byte roleCode = sessionUserVO.getSessionRoleVo().get(0).getRoleCode();
        Result result = new Result();
        result.setSuccess(false);
        result.setMessage("订单余额支付失败");
        try {
            BalanceVO balanceVO = spmService.getUserBalance(userId);
            if (!password.equals(balanceVO.getPayPwd())) {
                result.setMessage("支付密码不正确");
                return result;
            }
            String key = getKey(orderNo);
            if (!cacheService.exists(key)) {
                result.setMessage("订单号不存在");
                return result;
            }
            String cacheCost = (String) cacheService.get(key);
            Double cost = Double.valueOf(cacheCost);
            if ((balanceVO.getScore() - cost) < 0) {
                result.setMessage("余额不足");
                return result;
            }
            PayBalanceDTO payBalanceDTO = new PayBalanceDTO();
            payBalanceDTO.setAmount(cacheCost);
            payBalanceDTO.setOrderNo(orderNo);
            payBalanceDTO.setUserId(userId);
            payBalanceDTO.setUserType(roleCode);
            payBalanceDTO.setTransCodeEnum(TransCodeEnum.USER_STOCK);
            logger.info("调用余额支付接口入参：payBalanceDTO={}", JSON.toJSONString(payBalanceDTO));
            result = balanceService.payByBalance(payBalanceDTO);
            logger.info("调用余额支付接口返回值：result={}", JSON.toJSONString(result));
            //余额支付成功
            if (result.isSuccess()) {
                //删除缓存
                cacheService.remove(key);
            }
        } catch (Exception e) {
            logger.error("余额支付接口异常，e{}",e);
        }
        return result;
    }

}
