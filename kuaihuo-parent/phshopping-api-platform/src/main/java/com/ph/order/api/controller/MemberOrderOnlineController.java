package com.ph.order.api.controller;

import cm.ph.shopping.facade.order.constant.OrderOnlineStatusEnum;
import cm.ph.shopping.facade.order.constant.OrderOnlineUpdateContentEnum;
import cm.ph.shopping.facade.order.constant.OrderResultEnum;
import cm.ph.shopping.facade.order.dto.QueryMemberOrderOnlineDTO;
import cm.ph.shopping.facade.order.dto.SendOnlineOrderDTO;
import cm.ph.shopping.facade.order.dto.UpdateOnlineOrderStatusDTO;
import cm.ph.shopping.facade.order.service.IOnlineOrderService;
import cm.ph.shopping.facade.order.vo.PhMemberSubOrderOnlinePageVO;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ph.base.BaseController;
import com.ph.order.api.service.MemberOnlineOrderDataService;
import com.ph.shopping.common.core.customenum.RoleEnum;
import com.ph.shopping.common.core.other.deliver.AliDeliver;
import com.ph.shopping.common.util.core.RespCode;
import com.ph.shopping.common.util.core.ResultUtil;
import com.ph.shopping.common.util.excel.ExcelBean;
import com.ph.shopping.common.util.excel.ExcelUtil;
import com.ph.shopping.common.util.page.PageBean;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.permission.vo.SessionUserVO;
import com.ph.shopping.facade.spm.dto.AgentDTO;
import com.ph.shopping.facade.spm.service.IAgentService;
import com.ph.shopping.facade.spm.service.IWarehouseAddressService;
import com.ph.shopping.facade.spm.vo.AgentVO;
import com.ph.shopping.facade.spm.vo.WarehouseAddressVO;
import com.ph.shopping.facade.system.entity.Logistics;
import com.ph.shopping.facade.system.service.ILogisticsService;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @项目：phshopping-facade-order
 * @描述：线上订单控制层
 * @作者： 张霞
 * @创建时间： 14:50 2017/6/16
 * @Copyright @2017 by zhangxia
 */
@Controller
@RequestMapping("web/orderOnline")
public class MemberOrderOnlineController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(MemberOrderOnlineController.class);

    @Reference(version = "1.0.0")
    IOnlineOrderService iOnlineOrderService;

    @Reference(version="1.0.0")
    IAgentService iAgentService;

    @Reference(version = "1.0.0")
    ILogisticsService iLogisticsService;

    @Reference(version = "1.0.0")
    IWarehouseAddressService iWarehouseAddressService;

    @Autowired
    MemberOnlineOrderDataService memberOnlineOrderDataService;
    @Autowired
    AliDeliver aliDeliver;

    /**
     * @author: 张霞
     * @description：跳转到线上订单列表页面
     * @createDate: 14:56 2017/6/16
     * @param model
     * @return: java.lang.String
     * @version: 2.1
     */
    @RequestMapping(value = "toListPage",method = {RequestMethod.GET,RequestMethod.POST})
    public String toListPage(Model model){

        return "order/online/list";
    }

    /**
     * @author: 张霞
     * @description：线上订单获取列表数据
     * @createDate: 15:37 2017/6/16
     * @param model
     * @param pageBean
     * @param qdto
     * @return: com.ph.shopping.common.util.result.Result
     * @version: 2.1
     */
    @RequestMapping(value = "list",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public Result getOnlineOrderList(Model model, PageBean pageBean, QueryMemberOrderOnlineDTO qdto){
        qdto.setShopping( false );//后台查询标识
        return getOnlineOrderList(pageBean,qdto);
    }

    /**
     * @author: 张霞
     * @description：获取某一笔订单的物流信息
     * @createDate: 10:38 2017/6/17
     * @param qdto
     * @param model
     * @return: java.lang.String
     * @version: 2.1
     */
    @RequestMapping(value = "logistics",method = {RequestMethod.GET,RequestMethod.POST})
    public String getLogisticsById(Model model,QueryMemberOrderOnlineDTO qdto){
        Result result;
        Map<String ,Object> resultData = new HashMap<>();
            try {
                //查看商品信息
                result = iOnlineOrderService.getOnlineOrderVoById( qdto );
                //查询订单物流
                if (result.isSuccess()){
                    if (result.getData() instanceof PhMemberSubOrderOnlinePageVO){
                        PhMemberSubOrderOnlinePageVO pageVO = (PhMemberSubOrderOnlinePageVO) result.getData();
                        Result logisResult = iLogisticsService.getLogisticById( pageVO.getLogisticsId() );
                        if (logisResult.isSuccess()){
                            Logistics logistics = (Logistics) logisResult.getData();
                            Result logicLogresult = aliDeliver.getAliDeliver( logistics.getLogisticsSpell(),pageVO.getLogisticsNo() );//正式环境
//                        Result logicLogresult = aliDeliver.getAliDeliver( "shunfeng","613191171759" );//测试
                            if (logicLogresult.isSuccess()){
                                JSONObject logicLog = (JSONObject) logicLogresult.getData();
                                resultData.put( "logistic",logicLog );
                            }
                        }else {
                            resultData.put( "logistic",null );
                        }
                        resultData.put( "orderResult",pageVO );
                        result.setData( resultData );
                        logger.info( "获取线上订单物流信息结果：{}",JSON.toJSON( result ) );
                    }else {
                        result = ResultUtil.getResult( OrderResultEnum.GET_ORDER_DETAIL_EXCEPTION);
                    }
                }
            }catch (Exception e){
                logger.info( "查看线上订单物流异常，参数dto：{}", JSON.toJSON( qdto ) );
                result = ResultUtil.getResult( RespCode.Code.FAIL );
            }
            model.addAttribute( "result",result );
        return "order/online/logistics";
    }
    /**
     * @author: 张霞
     * @description：线上订单详情
     * @createDate: 17:19 2017/6/17
     * @param model
     * @param qdto
     * @return: java.lang.String
     * @version: 2.1
     */
    @RequestMapping(value ={"/detail","/reDetail"} ,method = {RequestMethod.GET,RequestMethod.POST})
    public String detail(Model model,QueryMemberOrderOnlineDTO qdto){
        Result result;
        try {
            result = iOnlineOrderService.getOnlineOrderDetailVO( qdto );
            logger.info( "订单详情结果：{}",JSON.toJSON( result ) );
        }catch (Exception e){
            logger.info( "线上订单获取详情异常，qdto={}",JSON.toJSON( qdto ) );
            result = ResultUtil.getResult( RespCode.Code.FAIL );
        }
         model.addAttribute( "onlineOrder",result);
       return "order/online/detail";
    }

    /**
     * @author: 张霞
     * @description：订单发货
     * @createDate: 17:12 2017/6/17
     * @param model
     * @param sendOnlineOrderDTO
     * @return: java.lang.String
     * @version: 2.1
     */
    @RequestMapping(value = "sendOrder",method = {RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public Object sendOrder(Model model,SendOnlineOrderDTO sendOnlineOrderDTO){
        Result result;
        try {
            SessionUserVO sessionUserVO = getLoginUser();
            Long userId = sessionUserVO.getId();
            Byte roleType = sessionUserVO.getSessionRoleVo().get( 0 ).getRoleCode();
           //修改状态和添加修改订单记录
            UpdateOnlineOrderStatusDTO udto = new UpdateOnlineOrderStatusDTO();
            udto.setOrderId( sendOnlineOrderDTO.getOrderId() );
            udto.setUpdaterId( userId );
            udto.setRoleType( roleType );
            udto.setCurrentOrderStatus( OrderOnlineStatusEnum.STATUS_TODO_RECEIVED.getStatus() );
            udto.setUpdateContentType( OrderOnlineUpdateContentEnum.ONLINE_UPDATE_STATUS.getType() );
            result = iOnlineOrderService.sendOnlineOrder( udto,sendOnlineOrderDTO );
        }catch (Exception e){
            logger.info( "发送订单异常,e={}",e );
            result = ResultUtil.getResult( RespCode.Code.FAIL );
        }
        return result;
    }
    /**
     * @author: 张霞
     * @description：线上订单导出数据
     * @createDate: 11:32 2017/6/19
     * @param request
     * @param response
     * @param qdto
     * @return: void
     * @version: 2.1
     */
    @RequestMapping(value = "export", method = {RequestMethod.POST})
    public void export(HttpServletRequest request, HttpServletResponse response, QueryMemberOrderOnlineDTO qdto) throws Exception {
        Result result = getOnlineOrderList(null,qdto  );
        List<PhMemberSubOrderOnlinePageVO> orderOnlinePageVOList = (List<PhMemberSubOrderOnlinePageVO>) result.getData();
        ExcelBean excelBean = memberOnlineOrderDataService.getOnlineOrder( orderOnlinePageVOList,"线上订单列表" );
        ExcelUtil.download(request, response, excelBean);
    }
    /**
     * @author: 张霞
     * @description：获取供应商仓库地址和物流公司内容
     * @createDate: 14:56 2017/6/19
     * @param
     * @return: com.ph.shopping.common.util.result.Result
     * @version: 2.1
     */
    @RequestMapping(value = "getSendAddressAndLogistics",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public Result selectSendAddressAndLogistics(){
        Result result;
        SessionUserVO sessionUserVO = getLoginUser();
        Long userId = sessionUserVO.getId();
        byte roleType = sessionUserVO.getSessionRoleVo().get( 0 ).getRoleCode();
        if (RoleEnum.SUPPLIER.getCode()==roleType){
            try {
                result = iLogisticsService.getLogisticsList();
                List<Logistics> logisticsList = (List<Logistics>) result.getData();
                List<WarehouseAddressVO> warehouseAddressVOS = iWarehouseAddressService.getWarehouseAddressVoList(userId);
                Map<String ,Object> map = new HashedMap(  );
                map.put( "logistics",logisticsList );
                map.put( "address", warehouseAddressVOS);
                result.setData( map );
                logger.info( "获取仓库地址和物流公司的result={}",JSON.toJSON( result ) );
            }catch (Exception e){
                logger.info( "获取相关发送信息异常，e={}",e );
                result = ResultUtil.getResult( RespCode.Code.FAIL );
            }
        }else {
            result = ResultUtil.getResult( RespCode.Code.FAIL );
            result.setMessage( "非法操作，请确认身份角色" );
        }

        return result;
    }

    /**
     * @author: 张霞
     * @description：获取线上订单列表数据
     * @createDate: 14:59 2017/6/16
     * @param pageBean
     * @param qdto
     * @return: com.ph.shopping.common.util.result.Result
     * @version: 2.1
     */
	private Result getOnlineOrderList(PageBean pageBean, QueryMemberOrderOnlineDTO qdto) {
		qdto.setRefund(false);
		Result result;
		SessionUserVO userVO = getLoginUser();
		Long userId = userVO.getId();
		Byte roleCode = userVO.getSessionRoleVo().get(0).getRoleCode();
		AgentDTO agentDTO = new AgentDTO();
		agentDTO.setUserId(userVO.getId());
		AgentVO agentVO;
		switch (roleCode) {
		case 1:// 管理员
			break;
		case 2:// 供应商
			qdto.setSupplierId(userId);
			break;
		case 3:// 市级代理
			agentVO = iAgentService.getAgentVODateilById(agentDTO);
			qdto.setShippingProvinceId(agentVO.getProvinceId());
			qdto.setShippingCityId(agentVO.getCityId());
			break;
		case 4:// 县级代理
			agentVO = iAgentService.getAgentVODateilById(agentDTO);
			qdto.setShippingProvinceId(agentVO.getProvinceId());
			qdto.setShippingCityId(agentVO.getCityId());
			qdto.setShippingCountyId(agentVO.getCountyId());
			break;
		case 5:// 社区代理
			agentVO = iAgentService.getAgentVODateilById(agentDTO);
			qdto.setShippingProvinceId(agentVO.getProvinceId());
			qdto.setShippingCityId(agentVO.getCityId());
			qdto.setShippingCountyId(agentVO.getCountyId());
			qdto.setShippingTownId(agentVO.getTownId());
			break;
		case 6:// 商户
			qdto.setMerchantId(userId);
			break;
		default:
			logger.warn("没有匹配到相关的角色信息");
			return ResultUtil.getResult(RespCode.Code.FAIL);
		}
		try {
			result = iOnlineOrderService.getOnlineOrderVoList(qdto, pageBean);
		} catch (Exception e) {
			result = ResultUtil.getResult(RespCode.Code.FAIL);
		}
		return result;
	}
}
