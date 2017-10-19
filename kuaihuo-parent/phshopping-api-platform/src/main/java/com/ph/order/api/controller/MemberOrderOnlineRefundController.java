package com.ph.order.api.controller;

import cm.ph.shopping.facade.order.dto.CheckMemberSubOrderRefundDTO;
import cm.ph.shopping.facade.order.dto.QueryMemberSubOrderRefundDTO;
import cm.ph.shopping.facade.order.service.IOnlineOrderService;
import cm.ph.shopping.facade.order.vo.PhMemberSubOrderRefundPageVO;
import com.alibaba.dubbo.config.annotation.Reference;
import com.ph.base.BaseController;
import com.ph.order.api.service.MemberOnlineOrderRefundDataService;
import com.ph.shopping.common.util.core.RespCode;
import com.ph.shopping.common.util.core.ResultUtil;
import com.ph.shopping.common.util.excel.ExcelBean;
import com.ph.shopping.common.util.excel.ExcelUtil;
import com.ph.shopping.common.util.page.PageBean;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.permission.vo.SessionUserVO;
import com.ph.shopping.facade.spm.dto.AgentDTO;
import com.ph.shopping.facade.spm.service.IAgentService;
import com.ph.shopping.facade.spm.vo.AgentVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @项目：phshopping-facade-order
 * @描述：线上订单申请退款
 * @作者： 张霞
 * @创建时间： 18:27 2017/6/19
 * @Copyright @2017 by zhangxia
 */
@Controller
@RequestMapping("web/orderOnlineRefund")
public class MemberOrderOnlineRefundController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(MemberOrderOnlineRefundController.class);

    @Reference(version = "1.0.0")
    IOnlineOrderService iOnlineOrderService;

    @Reference(version="1.0.0")
    IAgentService iAgentService;

    @Autowired
    MemberOnlineOrderRefundDataService memberOnlineOrderRefundDataService;
    /**
     * @author: 张霞
     * @description：跳转到线上申请退款页面
     * @createDate: 19:46 2017/6/19
     * @param
     * @return: java.lang.String
     * @version: 2.1
     */
    @RequestMapping(value = "toListPage")
    public String toListPage(){
        return "order/online/refundList";
    }
    /**
     * @author: 张霞
     * @description：获取线上订单申请退款的列表
     * @createDate: 19:47 2017/6/19
     * @param pageBean
     * @param qdto
     * @return: com.ph.shopping.common.util.result.Result
     * @version: 2.1
     */
    @RequestMapping(value = "list",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public Result getList(PageBean pageBean, QueryMemberSubOrderRefundDTO qdto){
        return getOnlineOrderRefundList(pageBean,qdto);
    }

    /**
     * @author: 张霞
     * @description：通过id获取线上订单退款信息
     * @createDate: 9:55 2017/6/20
     * @param qdto
     * @return: com.ph.shopping.common.util.result.Result
     * @version: 2.1
     */
    @RequestMapping(value = "getRefundOrderById",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public Result getRefundOrderById(QueryMemberSubOrderRefundDTO qdto){
        Result result;
        result = iOnlineOrderService.getOnlineOrderRefundById( qdto );
        return result;
    }

    /**
     * @author: 张霞
     * @description：审核申请退款订单
     * @createDate: 11:04 2017/6/20
     * @param cdto
     * @return: com.ph.shopping.common.util.result.Result
     * @version: 2.1
     */
    @RequestMapping(value = "checkRefundOrder",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public Result checkRefundOrder(CheckMemberSubOrderRefundDTO cdto){
        Result result;
        SessionUserVO userVO = getLoginUser();
        cdto.setUpdaterId( userVO.getId() );
        result = iOnlineOrderService.checkOnlineOrderRefund( cdto );
        return result;
    }

    /**
     * @author: 张霞
     * @description：查看线上订单申请退款详情
     * @createDate: 14:55 2017/6/20
     * @param qdto
     * @return: com.ph.shopping.common.util.result.Result
     * @version: 2.1
     */
    @RequestMapping(value = "refundOrderDetail",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public Result refundOrderDetail(QueryMemberSubOrderRefundDTO qdto){
        Result result;
        try {
            result = iOnlineOrderService.getOnlineOrderRefundById( qdto );
        }catch (Exception e){
            result = ResultUtil.getResult( RespCode.Code.FAIL );
        }
        return result;
    }

    /**
     * @author: 张霞
     * @description：线上申请退款订单导出数据
     * @createDate: 15:26 2017/6/20
     * @param request
     * @param response
     * @param qdto
     * @return: void
     * @version: 2.1
     */
    @RequestMapping(value = "export", method = {RequestMethod.POST})
    public void export(HttpServletRequest request, HttpServletResponse response, QueryMemberSubOrderRefundDTO qdto) throws Exception {
        Result result = getOnlineOrderRefundList(null,qdto  );
        List<PhMemberSubOrderRefundPageVO> orderRefundPageVOList = (List<PhMemberSubOrderRefundPageVO>) result.getData();
        ExcelBean excelBean = memberOnlineOrderRefundDataService.getOnlineOrderRefund( orderRefundPageVOList,"线上订单申请退款列表" );
        ExcelUtil.download(request, response, excelBean);
    }
    /**
     * @author: 张霞
     * @description：查询线上订单申请退款列表
     * @createDate: 20:07 2017/6/19
     * @param pageBean
     * @param qdto
     * @return: com.ph.shopping.common.util.result.Result
     * @version: 2.1
     */
    public Result getOnlineOrderRefundList(PageBean pageBean,QueryMemberSubOrderRefundDTO qdto){

        Result result;
        SessionUserVO userVO  = getLoginUser();
        Long userId = userVO.getId();
        Byte roleCode = userVO.getSessionRoleVo().get(0).getRoleCode();
        AgentDTO agentDTO = new AgentDTO();
        agentDTO.setUserId(userVO.getId());
        AgentVO agentVO;
        switch (roleCode){
            case 1://管理员
                return iOnlineOrderService.getOnlineOrderRefundVoList( qdto,pageBean );
            case 2://供应商
                qdto.setSupplierId( userId );
                return iOnlineOrderService.getOnlineOrderRefundVoList( qdto,pageBean );
            case 3://市级代理
                agentVO = iAgentService.getAgentVODateilById( agentDTO );
                qdto.setShippingProvinceId( agentVO.getProvinceId() );
                qdto.setShippingCityId( agentVO.getCityId() );
            case 4://县级代理
                agentVO = iAgentService.getAgentVODateilById( agentDTO );
                qdto.setShippingProvinceId( agentVO.getProvinceId() );
                qdto.setShippingCityId( agentVO.getCityId() );
                qdto.setShippingCountyId( agentVO.getCountyId() );
            case 5://社区代理
                agentVO = iAgentService.getAgentVODateilById( agentDTO );
                qdto.setShippingProvinceId( agentVO.getProvinceId() );
                qdto.setShippingCityId( agentVO.getCityId() );
                qdto.setShippingCountyId( agentVO.getCountyId() );
                qdto.setShippingTownId( agentVO.getTownId() );
            case 6://商户
                qdto.setMerchantId( userId );
            default://会员
                qdto.setMemberId( userId );
        }
        try {
            result = iOnlineOrderService.getOnlineOrderRefundVoList( qdto,pageBean );
        }catch (Exception e){
            result = ResultUtil.getResult( RespCode.Code.FAIL );
        }
        return result;

    }
}
