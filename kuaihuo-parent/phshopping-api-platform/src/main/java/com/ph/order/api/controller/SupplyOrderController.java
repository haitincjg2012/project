package com.ph.order.api.controller;

import cm.ph.shopping.facade.order.dto.CheckRefundAppliDTO;
import cm.ph.shopping.facade.order.dto.QueryPurchaseOrderDTO;
import cm.ph.shopping.facade.order.dto.SendProductDTO;
import cm.ph.shopping.facade.order.dto.UpdatePurOrderFreDTO;
import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import com.ph.base.BaseController;
import com.ph.order.api.controller.qo.PurchaseOrderQO;
import com.ph.order.api.controller.vo.PurchasePageVO;
import com.ph.order.api.controller.vo.PurchaseUserVO;
import com.ph.order.api.service.PurchaseOrderDataService;
import com.ph.order.api.service.PurchaseService;
import com.ph.order.api.service.SpmService;
import com.ph.shopping.common.core.customenum.RoleEnum;
import com.ph.shopping.common.util.excel.ExcelBean;
import com.ph.shopping.common.util.excel.ExcelUtil;
import com.ph.shopping.common.util.page.PageBean;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.permission.vo.SessionUserVO;
import com.ph.shopping.facade.spm.constant.SupplierTypeConstantEnum;
import com.ph.shopping.facade.spm.vo.AgentVO;
import com.ph.shopping.facade.spm.vo.MerchantVO;
import com.ph.shopping.facade.spm.vo.SupplierVO;
import com.ph.shopping.facade.system.service.ILogisticsService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 供货订单controller
 *
 * @author 郑朋
 * @create 2017/5/25
 **/
@Controller
@RequestMapping("web/order/supply/")
public class SupplyOrderController extends BaseController {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    PurchaseOrderDataService purchaseOrderDataService;

    @Autowired
    SpmService spmService;

    @Autowired
    PurchaseService purchaseService;

    @Reference(version = "1.0.0")
    ILogisticsService logisticsService;


    /**
     * @methodname listPage 的描述：进入供货订单列表
     * @param model
     * @return java.lang.String
     * @author 郑朋
     * @create 2017/5/23
     */
    @RequestMapping(value = "toListPage", method = {RequestMethod.GET})
    public String listPage(Model model) {
        toListPage(model);
        return "order/supply/list";
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
        toListPage(model);
        return "order/supply/refundList";
    }

    private void toListPage(Model model) {
        SessionUserVO userVO = getLoginUser();
        Long userId = userVO.getId();
        Byte roleCode = userVO.getSessionRoleVo().get(0).getRoleCode();
        List<SupplierVO> list = new ArrayList<>();
        List<PurchaseUserVO> purchaseUserVoList = new ArrayList<>();
        switch (roleCode) {
            case 1 :
                //平台
                //供应商:查询所有的供应商
                list = spmService.getSupplierByUser(null);
                //进货商:查询所有的商户和市级代理商
                purchaseUserVoList = spmService.getPurchaseUserVo();
                break;
            case 2 :
                //供应商
                //供应商:自己
                List<SupplierVO> supplierVOList = spmService.getSupplierByUser(userId);
                list.addAll(supplierVOList);
                Byte supplierType = null;
                if (CollectionUtils.isNotEmpty(supplierVOList)) {
                    supplierType = supplierVOList.get(0).getSupplierType();
                }
                /**
                 * 本地供应：进货商--添加自己的代理商
                 * 全国供应：进货商--所有市级代理
                 */
                if (supplierType == 1) {
                    List<AgentVO> agentVOList = spmService.getCityAgent(1L);
                    purchaseUserVoList = SpmService.getPurchaseUserVo1(agentVOList);
                } else if (supplierType == 2) {
                    //查询出当前供应商的创建人
                    List<SupplierVO> supplierVOList1 = spmService.getSupplierByUser(userId);
                    //通过userId查询市级代理
                    if (CollectionUtils.isNotEmpty(supplierVOList1)) {
                        purchaseUserVoList = SpmService.getPurchaseUserVo1(spmService.getAgentByUser(supplierVOList1.get(0).getCreaterId()));
                    }
                }
                break;
            case 3 :
                //市级代理
                getPageSupplierAndAgent(list,purchaseUserVoList,userId);
                break;
            case 4:
                //县级代理商
                getPageSupplierAndAgent(list,purchaseUserVoList,userId);
                break;
            case 5:
                //社区代理商
                getPageSupplierAndAgent(list,purchaseUserVoList,userId);
                break;
        }
        model.addAttribute("roleCode",roleCode);
        model.addAttribute("supplier",list);
        model.addAttribute("purchaseUser",purchaseUserVoList);
    }

    private void  getPageSupplierAndAgent(List<SupplierVO> list, List<PurchaseUserVO> purchaseUserVoList, Long userId) {
        //供应商:平台+自己添加的
        list.addAll(spmService.getSupplierByType(Byte.valueOf(SupplierTypeConstantEnum.SUPPLIER_TYPE_TOTAL.getCode())));
        list.addAll(spmService.getSupplierByCreateId(userId));
        //进货商:归属商户
        List<AgentVO> agentVOS = spmService.getAgentByUser(userId);
        if (CollectionUtils.isNotEmpty(agentVOS)) {
            List<MerchantVO> merchantVOList = spmService.getMerchantByAgent(agentVOS.get(0).getId());
            purchaseUserVoList.addAll(SpmService.getPurchaseUserVo(merchantVOList));
        }
    }


    /**
     * @methodname list 的描述：供货订单列表
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
     * @methodname refundList 的描述：供货退换订单列表
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
     * @methodname export 的描述：供货订单列表导出
     * @param request
     * @param response
     * @param purchaseOrderQO
     * @return void
     * @author 郑朋
     * @create 2017/5/26
     */
    @RequestMapping(value = "export", method = {RequestMethod.POST})
    @SuppressWarnings("unchecked")
    public void export(HttpServletRequest request, HttpServletResponse response, PurchaseOrderQO purchaseOrderQO) throws Exception {
        Result result = getList(null, purchaseOrderQO,1);
        List<PurchasePageVO> purchasePageVOList = (List<PurchasePageVO>) result.getData();
        String[] tableHeads = { "订单号","下单时间","供应商", "进货商", "商品金额(元)", "物流费用(元)", "订单总额(元)", "付款方式", "订单状态"};
        ExcelBean excelBean = purchaseOrderDataService.getPurchaseOrder(purchasePageVOList,"供货订单数据列表",1,tableHeads);
        ExcelUtil.download(request, response, excelBean);
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
        ExcelBean excelBean = purchaseOrderDataService.getPurchaseOrder(purchasePageVOList,"供货退换订单数据列表",2,tableHeads);
        ExcelUtil.download(request, response, excelBean);
    }


    private Result getList(PageBean pageBean, PurchaseOrderQO purchaseOrderQO,int type) throws ParseException {
        SessionUserVO userVO = getLoginUser();
        Long userId = userVO.getId();
        Byte roleCode = userVO.getSessionRoleVo().get(0).getRoleCode();
        QueryPurchaseOrderDTO queryPurchaseOrderDTO = new QueryPurchaseOrderDTO();
        BeanUtils.copyProperties(purchaseOrderQO,queryPurchaseOrderDTO);
        if (StringUtils.isNotEmpty(purchaseOrderQO.getCreateTimeStr())) {
            queryPurchaseOrderDTO.setCreateTimeStr(sdf.parse(purchaseOrderQO.getCreateTimeStr() + " 00:00:00"));
        }
        if (StringUtils.isNotEmpty(purchaseOrderQO.getCreateTimeEnd())) {
            queryPurchaseOrderDTO.setCreateTimeEnd(sdf.parse(purchaseOrderQO.getCreateTimeEnd() + " 23:59:59"));
        }
        //如果是市级代理和供应商，设置发货人是登录人
        if (RoleEnum.CITY_AGENT.getCode() == roleCode || RoleEnum.SUPPLIER.getCode() == roleCode) {
            queryPurchaseOrderDTO.setSenderId(userId);
        }

        //如果是县级代理和社区代理，设置进货人是归属商户
        if (RoleEnum.COUNTY_AGENT.getCode() == roleCode || RoleEnum.COMMUNITY_AGENT.getCode() == roleCode) {
            queryPurchaseOrderDTO.setPurchaseIds(getPurchaseIds(userId));
        }
        return purchaseService.getPurchaseOrder(pageBean,queryPurchaseOrderDTO,type);
    }

    private List<Long> getPurchaseIds(Long userId) {
        List<Long> list = null;
        List<AgentVO> agentVOS = spmService.getAgentByUser(userId);
        if (CollectionUtils.isNotEmpty(agentVOS)) {
            list = new ArrayList<>();
            List<MerchantVO> merchantVOList = spmService.getMerchantByAgent(agentVOS.get(0).getId());
            for (MerchantVO vo : merchantVOList) {
                list.add(vo.getUserId());
            }
        }
        return list;
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
    public String detail(Model model, Long subOrderId, Integer type) {
        purchaseService.detail(model,subOrderId);
        model.addAttribute("type",type);
        return "order/supply/detail";
    }


    /**
     * @methodname orderDetail 的描述：查询订单详情
     * @param model
     * @param subOrderId
     * @param type
     * @return java.lang.String
     * @author 郑朋
     * @create 2017/5/23
     */
    @RequestMapping(value = "refund/orderDetail", method = {RequestMethod.GET, RequestMethod.POST})
    public String orderDetail(Model model, Long subOrderId, Integer type) {
        purchaseService.detail(model,subOrderId);
        model.addAttribute("type",type);
        return "order/supply/detail";
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
     * @methodname check 的描述：退款申请审核
     * @param checkRefundAppliDTO
     * @return com.ph.shopping.common.util.result.Result
     * @author 郑朋
     * @create 2017/5/27
     */
    @RequestMapping("refund/check")
    @ResponseBody
    public Result check (CheckRefundAppliDTO checkRefundAppliDTO) {
        SessionUserVO userVO = getLoginUser();
        Long userId = userVO.getId();
        checkRefundAppliDTO.setUpdaterId(userId);
        return purchaseService.checkRefund(checkRefundAppliDTO);
    }


    /**
     * @methodname updateFreight 的描述：修改物流费用
     * @param updatePurOrderFreDTO
     * @return com.ph.shopping.common.util.result.Result
     * @author 郑朋
     * @create 2017/5/27
     */
    @RequestMapping("/updateFreight")
    @ResponseBody
    public Result updateFreight (UpdatePurOrderFreDTO updatePurOrderFreDTO) {
        SessionUserVO userVO = getLoginUser();
        Long userId = userVO.getId();
        updatePurOrderFreDTO.setUpdaterId(userId);
        return purchaseService.updateFreight(updatePurOrderFreDTO);
    }

    /**
     * @methodname getLogistics 的描述：通过物流公司
     * @param
     * @return com.ph.shopping.common.util.result.Result
     * @author 郑朋
     * @create 2017/5/26
     */
    @RequestMapping("getLogistics")
    @ResponseBody
    public Result getLogistics() {
        return logisticsService.getLogisticsList();
    }

    /**
     * @methodname sendProduct 的描述：订单发货
     * @param sendProductDTO
     * @return com.ph.shopping.common.util.result.Result
     * @author 郑朋
     * @create 2017/6/2
     */
    @RequestMapping("/sendProduct")
    @ResponseBody
    public Result sendProduct(SendProductDTO sendProductDTO) {
        sendProductDTO.setSenderId(getLoginUser().getId());
        return purchaseService.sendProduct(sendProductDTO);
    }
}
