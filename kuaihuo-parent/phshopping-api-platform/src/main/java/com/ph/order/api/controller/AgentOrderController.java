//package com.ph.order.api.controller;
//
//import cm.ph.shopping.facade.order.dto.PhAgentOrderListDTO;
//import cm.ph.shopping.facade.order.dto.QueryOrderDetailDTO;
//import cm.ph.shopping.facade.order.entity.PhAgentOrder;
//import cm.ph.shopping.facade.order.exception.OrderExceptionEnum;
//import cm.ph.shopping.facade.order.service.IAgentOrderService;
//import cm.ph.shopping.facade.order.service.IPhManagerOrderAddressService;
//import cm.ph.shopping.facade.order.vo.PhLogisticsVO;
//import cm.ph.shopping.facade.order.vo.QueryOrderDetailVO;
//import com.alibaba.dubbo.config.annotation.Reference;
//import com.ph.base.BaseController;
//import com.ph.shopping.common.util.page.PageBean;
//import com.ph.shopping.common.util.result.Result;
//import com.ph.shopping.facade.permission.vo.SessionRoleVo;
//import com.ph.shopping.facade.permission.vo.SessionUserVo;
//import com.ph.shopping.facade.spm.entity.Agent;
//import com.ph.shopping.facade.spm.entity.Supplier;
//import com.ph.shopping.facade.spm.service.IAgentService;
//import com.ph.shopping.facade.spm.service.ISupplierService;
//import com.ph.shopping.facade.spm.vo.AgentVO;
//import com.ph.shopping.facade.spm.vo.SupplierVO;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.servlet.ModelAndView;
//
//import javax.servlet.http.HttpServletRequest;
//import java.util.List;
///**
// * 
// * @project：phshopping-api-platform
// * @description: 代理商进货
// * @author 李超
// * @date 2017年3月27日
// */
//@Controller
//@RequestMapping("/web/order/agent")
//public class AgentOrderController extends BaseController {
//
//	@Reference(version = "1.0.0")
//	private IAgentOrderService iAgentOrderService;
//
//	@Reference(version = "1.0.0")
//	private ISupplierService iSupplierService;
//
//	@Reference(version = "1.0.0")
//	private IAgentService iAgentService;
//
//    @Reference(version = "1.0.0")
//    private IPhManagerOrderAddressService iPhManagerOrderAddressService;
//
//	/**
//	 * @描述：跳转至进货订单详情
//	 *
//	 * @param: queryOrderDetailVO
//	 *
//	 * @return: java.lang.Object
//	 *
//	 * @作者： Mr.Shu
//	 *
//	 * @创建时间：2017/4/25 11:26
//	 */
//	@RequestMapping(value = "/orderEnterDetailPage", method = RequestMethod.GET)
//    public Object toOrderEnterDetailPage(QueryOrderDetailDTO queryOrderDetailDTO) {
//		ModelAndView mv = new ModelAndView("/agent/order/orderEnterDetail");
//		Result result = this.getOrderDetail(queryOrderDetailDTO);
//		mv.addObject("orderDetailVo", result.getData());
//		return mv;
//    }
//
//	/**
//	 * @描述：跳转至供货订单详情
//	 *
//	 * @param: queryOrderDetailVO
//	 *
//	 * @return: java.lang.Object
//	 *
//	 * @作者： Mr.Shu
//	 *
//	 * @创建时间：2017/4/25 11:27
//	 */
//	@RequestMapping(value = "/orderSendDetailPage", method = RequestMethod.GET)
//    public Object toOrderSendDetailPage(QueryOrderDetailDTO queryOrderDetailDTO) {
//		ModelAndView mv = new ModelAndView("/agent/order/orderSendDetail");
//		Result result = this.getOrderDetail(queryOrderDetailDTO);
//		mv.addObject("orderDetailVo", result.getData());
//		return mv;
//    }
//
//	/**
//	 * @描述：跳转至平台订单详情
//	 *
//	 * @param: queryOrderDetailVO
//	 *
//	 * @return: java.lang.Object
//	 *
//	 * @作者： Mr.Shu
//	 *
//	 * @创建时间：2017/4/25 11:28
//	 */
//	@RequestMapping(value = "/orderDetailPage", method = RequestMethod.GET)
//    public Object toOrderDetailPage(QueryOrderDetailDTO queryOrderDetailDTO) {
//		ModelAndView mv = new ModelAndView("/agent/order/orderDetail");
//		Result result = this.getOrderDetail(queryOrderDetailDTO);
//		mv.addObject("orderDetailVo", result.getData());
//		return mv;
//    }
//
//	/**
//	 * 
//	 * @description: 跳转至进货单页面
//	 * @param model
//	 * @param ide 用于表格显示时的标识 根据入口来传值, 值为1表示进货单(只显示未审核)
//	 * @return String
//	 * @author 李超
//	 * @date 2017年3月27日
//	 */
//	@RequestMapping(value="/list",method=RequestMethod.GET)
//	public String toAgentOrderList(Model model, String ide){
//		//获取供应商列表
//		Supplier supplier = new Supplier();
//		supplier.setStatus(1);
//		List<SupplierVO> supplierList = iSupplierService.getSupplierVoList(supplier);
//		logger.debug("供应商列表: " + supplierList);
//		model.addAttribute("supplierList", supplierList);
//		model.addAttribute("ide", ide == null ? "-1" : "1" );
//		return "agent/order/list";
//	}
//	
//	/**
//	 * 
//	 * @description: 获取进货单列表分页 ajax
//	 * @param pageBean
//	 * @return
//	 * @throws Exception
//	 * @author 李超
//	 * @date 2017年3月27日
//	 */
//	@ResponseBody 
//	@RequestMapping(value="/getAgentOrderList",method=RequestMethod.GET)
//	public Object getAgentOrderList(Model model, HttpServletRequest request, PageBean pageBean,
//				PhAgentOrderListDTO phAgentOrderListDTO) {
//		if(phAgentOrderListDTO.getIde().equals("1")){//只显示未审核
//			phAgentOrderListDTO.setStatus("0");
//		}
//		//RoleCode 1,"管理员"; 2,"供应商"; 3,"市级代理商"; 4,"县级代理商"; 5,"社区代理商";6,"商户"
//		//获取登录者信息
//		SessionUserVo user = getLoginUser(request);
//		SessionRoleVo role = user.getSessionRoleVo().get(0);
//		if(role.getRoleCode() == 2){
//			//登录者是供货者供应商(显示供货单)
//			phAgentOrderListDTO.setSupplerId(user.getId());
//			phAgentOrderListDTO.setPersonIde(5);
//		}else if(role.getRoleCode() == 5){
//			/*
//			 * 如果登录者是区代理 会看见自己所对应的市代的进货单
//			 * 获取对应的市级代理的id
//			 */
//			//自己
//			Agent agent = new Agent();
//			agent.setId(user.getId());
//			AgentVO agentVO = iAgentService.getAgentDetail(agent);
//			//县级
//			Agent agentParent = new Agent();
//			agentParent.setId(agentVO.getParentId());
//			AgentVO agentVOParent = iAgentService.getAgentDetail(agentParent);
//			//市级
//			Agent agentCity = new Agent();
//			agentCity.setId(agentVOParent.getParentId());
//			AgentVO agentVOCity = iAgentService.getAgentDetail(agentCity);
//			phAgentOrderListDTO.setAgentId(agentVOCity.getId());
//			phAgentOrderListDTO.setPersonIde(3);//设置当前登录者级别
//		}else if(role.getRoleCode() == 4){//查看是直接上级的市级代理
//			/*
//			 * 如果登录者是县级代理 会看见自己所对应的市代的供货单
//			 * 获取对应的市级代理的id
//			 */
//			//自己
//			Agent agent = new Agent();
//			agent.setId(user.getId());
//			AgentVO agentVO = iAgentService.getAgentDetail(agent);
//			//县级
//			Agent agentParent = new Agent();
//			agentParent.setId(agentVO.getParentId());
//			AgentVO agentVOParent = iAgentService.getAgentDetail(agentParent);
//			phAgentOrderListDTO.setAgentId(agentVOParent.getId());
//			phAgentOrderListDTO.setPersonIde(3);//设置当前登录者级别
//		}else if(role.getRoleCode() == 3){//市级代理
//			phAgentOrderListDTO.setAgentId(user.getId());
//			phAgentOrderListDTO.setPersonIde(2);//设置当前登录者级别
//		}else if(role.getRoleCode() == 1){
//			//如果是管理员登录 去掉状态(status)查询条件即所有订单都会展示
//			phAgentOrderListDTO.setIde("1");
//			phAgentOrderListDTO.setPersonIde(4);//设置当前登录者级别
//		}
//		model.addAttribute("phAgentOrderListVo", phAgentOrderListDTO);
//		return iAgentOrderService.getAgentOrderList(pageBean, phAgentOrderListDTO);
//	}
//
//	/**
//	 * @描述：获取订单详情
//	 *
//	 * @param: queryOrderDetailVO
//	 *
//	 * @return: com.ph.shopping.common.util.result.Result
//	 *
//	 * @作者： Mr.Shu
//	 *
//	 * @创建时间：2017/4/25 11:28
//	 */
//	@ResponseBody
//	@RequestMapping(value = "/getOrderDetail", method = RequestMethod.GET)
//    public Result getOrderDetail(QueryOrderDetailDTO queryOrderDetailDTO){
//		QueryOrderDetailVO queryOrderDetailVO1 = iAgentOrderService.getOrderDetail(queryOrderDetailDTO);
//		if (queryOrderDetailVO1.getLogisticsId() != null) {
//			PhLogisticsVO phLogisticsVo = iPhManagerOrderAddressService.findLogisticsById(queryOrderDetailVO1.getLogisticsId());
//			if (phLogisticsVo != null) {
//				queryOrderDetailVO1.setLogisticsCompany(phLogisticsVo.getLogisticsName());
//			}
//		}
//		return new Result(true,"", queryOrderDetailVO1);
//    }
//
//	/**
//	 * @描述：修改物流费用
//	 *
//	 * @param: phAgentOrder
//	 *
//	 * @return: java.lang.Object
//	 *
//	 * @作者： Mr.Shu
//	 *
//	 * @创建时间：2017/4/25 11:30
//	 */
//	@ResponseBody
//	@RequestMapping(value = "/updateLogistics", method = RequestMethod.POST)
//    public Object updateLogistics(PhAgentOrder phAgentOrder, HttpServletRequest request) throws Exception {
//		SessionUserVo userVo = this.getLoginUser(request);//判断当前登录中是否是该订单的代理商
//		if (userVo.getId().equals(phAgentOrder.getSupplerId())) {
//			return iAgentOrderService.updateLogistics(phAgentOrder);
//		}else{
//            return new Result(false, OrderExceptionEnum.USER_ERROR.getCode(), OrderExceptionEnum.USER_ERROR.getMsg());
//        }
//    }
//}
