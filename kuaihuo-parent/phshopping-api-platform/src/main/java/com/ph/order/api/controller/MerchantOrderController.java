//package com.ph.order.api.controller;
//
//import cm.ph.shopping.facade.order.dto.PhMerchantOrderListDTO;
//import cm.ph.shopping.facade.order.dto.QueryOrderDetailDTO;
//import cm.ph.shopping.facade.order.entity.PhMerchantOrder;
//import cm.ph.shopping.facade.order.exception.OrderExceptionEnum;
//import cm.ph.shopping.facade.order.service.IMerchantOrderService;
//import cm.ph.shopping.facade.order.service.IPhManagerOrderAddressService;
//import cm.ph.shopping.facade.order.vo.PhLogisticsVO;
//import cm.ph.shopping.facade.order.vo.QueryOrderDetailVO;
//import com.alibaba.dubbo.config.annotation.Reference;
//import com.ph.base.BaseController;
//import com.ph.shopping.common.util.page.PageBean;
//import com.ph.shopping.common.util.result.Result;
//import com.ph.shopping.facade.permission.vo.SessionRoleVo;
//import com.ph.shopping.facade.permission.vo.SessionUserVo;
//import com.ph.shopping.facade.spm.dto.AgentDTO;
//import com.ph.shopping.facade.spm.entity.Agent;
//import com.ph.shopping.facade.spm.entity.Supplier;
//import com.ph.shopping.facade.spm.service.IAgentService;
//import com.ph.shopping.facade.spm.service.ISupplierService;
//import com.ph.shopping.facade.spm.vo.AgentVO;
//import com.ph.shopping.facade.spm.vo.SupplierVO;
//import org.apache.commons.lang.StringUtils;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.servlet.ModelAndView;
//
//import javax.servlet.http.HttpServletRequest;
//import java.util.List;
//
///**
// * 
// * @project：phshopping-api-platform
// * @description: 商户进货 
// * @author 李超
// * @date 2017年3月23日
// */
//@Controller
//@RequestMapping("/web/order/merchant")
//public class MerchantOrderController extends BaseController {
//
//	@Reference(version = "1.0.0")
//	private IMerchantOrderService iMerchantOrderService;
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
//	 * @创建时间：2017/4/25 11:32
//	 */
//	@RequestMapping(value = "/orderEnterDetailPage", method = RequestMethod.GET)
//	public Object toOrderEnterDetailPage(QueryOrderDetailDTO queryOrderDetailDTO) {
//		ModelAndView mv = new ModelAndView("/merchant/order/orderEnterDetail");
//		Result result = this.getOrderDetail(queryOrderDetailDTO);
//		mv.addObject("orderDetailVo", result.getData());
//		return mv;
//	}
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
//	 * @创建时间：2017/4/25 11:33
//	 */
//	@RequestMapping(value = "/orderSendDetailPage", method = RequestMethod.GET)
//	public Object toOrderSendDetailPage(QueryOrderDetailDTO queryOrderDetailDTO) {
//		ModelAndView mv = new ModelAndView("/merchant/order/orderSendDetail");
//		Result result = this.getOrderDetail(queryOrderDetailDTO);
//		mv.addObject("orderDetailVo", result.getData());
//		return mv;
//	}
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
//	 * @创建时间：2017/4/25 11:33
//	 */
//	@RequestMapping(value = "/orderDetailPage", method = RequestMethod.GET)
//	public Object toOrderDetailPage(QueryOrderDetailDTO queryOrderDetailDTO) {
//		ModelAndView mv = new ModelAndView("/merchant/order/orderDetail");
//		Result result = this.getOrderDetail(queryOrderDetailDTO);
//		mv.addObject("orderDetailVo", result.getData());
//		return mv;
//	}
//
//	
//	/**
//	 * 
//	 * @description: 跳转至进货单页面
//	 * @param model
//	 * @param ide 用于表格显示时的标识 根据入口来传值, 值为1表示进货单(只显示未审核)
//	 * @return
//	 * @author 李超
//	 * @date 2017年3月23日
//	 */
//	@RequestMapping(value="/list",method=RequestMethod.GET)
//	public String toMerchantOrderList(Model model, String ide){
//		//获取供应商列表
//		Supplier supplier = new Supplier();
//		supplier.setStatus(Byte.valueOf(String.valueOf(1)));
//		List<SupplierVO> supplierList = iSupplierService.getSupplierVoList(supplier);
//		logger.debug("供应商列表: " + supplierList);
//		model.addAttribute("supplierList", supplierList);
//		model.addAttribute("ide", ide == null ? "-1" : "1" );
//		return "merchant/order/list";
//	}
//	
//	/**
//	 * 
//	 * @description: 获取进货单列表分页 ajax
//	 * @param pageBean
//	 * @param phMerchantOrderListDTO
//	 * @return Object
//	 * @throws Exception
//	 * @author 李超
//	 * @date 2017年3月23日
//	 */
//	@ResponseBody 
//	@RequestMapping(value="/getMerchantOrderList",method=RequestMethod.GET)
//	public Object getMerchantOrderList(Model model, HttpServletRequest request, PageBean pageBean, PhMerchantOrderListDTO phMerchantOrderListDTO) throws Exception{
//		if(phMerchantOrderListDTO.getIde().equals("1")){//只显示未审核
//			phMerchantOrderListDTO.setStatus("0");
//		}
//		//获取登录者信息
//		SessionUserVo user =  getLoginUser(request);
//		SessionRoleVo role =user.getSessionRoleVo().get(0);
//		if(role.getRoleCode() == 6){
//			//登录者是商户
//			phMerchantOrderListDTO.setMerchantId(user.getId());
//			phMerchantOrderListDTO.setPersonIde(1);
//		}else if(role.getRoleCode() == 5){
//			/*
//			 * 如果登录者是区代理 会看见自己所对应的市代的供货单
//			 * 获取对应的市级代理的id
//			 */
//			//自己
//			AgentDTO agentDTO = new AgentDTO();
//			agentDTO.setId(user.getId());
//			AgentVO agentVO = iAgentService.getAgentVOListById(agentDTO);
//			//县级
//			AgentDTO agentParent = new AgentDTO();
//			agentParent.setId(agentVO.getParentId());
//			AgentVO agentVOParent = iAgentService.getAgentVOListById(agentParent);
//			//市级
//			AgentDTO agentCity = new AgentDTO();
//			agentCity.setId(agentVOParent.getParentId());
//			AgentVO agentVOCity = iAgentService.getAgentVOListById(agentCity);
//			phMerchantOrderListDTO.setAgentId(agentVOCity.getId());
//			phMerchantOrderListDTO.setPersonIde(3);//设置当前登录者级别
//		}else if(role.getRoleCode() == 4){//查看是直接上级的市级代理
//			/*
//			 * 如果登录者是县级代理 会看见自己所对应的市代的供货单
//			 * 获取对应的市级代理的id
//			 */
//			//自己
//			AgentDTO agent = new AgentDTO();
//			agent.setId(user.getId());
//			AgentVO agentVO = iAgentService.getAgentVOListById(agent);
//			//县级
//			AgentDTO agentParent = new AgentDTO();
//			agentParent.setId(agentVO.getParentId());
//			AgentVO agentVOParent = iAgentService.getAgentVOListById(agentParent);
//			phMerchantOrderListDTO.setAgentId(agentVOParent.getId());
//			phMerchantOrderListDTO.setPersonIde(3);//设置当前登录者级别
//		}else if(role.getRoleCode() == 3){//市级代理
//			phMerchantOrderListDTO.setAgentId(user.getId());
//			phMerchantOrderListDTO.setPersonIde(2);//设置当前登录者级别
//		}else if(role.getRoleCode() == 1){
//			//如果是管理员登录 去掉状态(status)查询条件即所有订单都会展示
//			phMerchantOrderListDTO.setIde("1");
//			phMerchantOrderListDTO.setPersonIde(4);//设置当前登录者级别
//		}
//		model.addAttribute("phMerchantOrderListVo", phMerchantOrderListDTO);
//		return iMerchantOrderService.getMerchantOrderList(pageBean, phMerchantOrderListDTO);
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
//	 * @创建时间：2017/4/25 11:33
//	 */
//	@ResponseBody
//	@RequestMapping(value = "/getOrderDetail", method = RequestMethod.GET)
//    public Result getOrderDetail(QueryOrderDetailDTO queryOrderDetailDTO) {
//		QueryOrderDetailVO queryOrderDetailVO1 = iMerchantOrderService.getOrderDetail(queryOrderDetailDTO);
//		if (queryOrderDetailVO1.getLogisticsId() != null) {
//			PhLogisticsVO phLogisticsVo = iPhManagerOrderAddressService.findLogisticsById(queryOrderDetailVO1.getLogisticsId());
//			if (phLogisticsVo != null) {
//				queryOrderDetailVO1.setLogisticsCompany(phLogisticsVo.getLogisticsName());
//			}
//		}
//		return new Result(true, "", queryOrderDetailVO1);
//    }
//
//
//	/**
//	 * @描述：修改物流费用
//	 *
//	 * @param: phMerchantOrder
//	 *
//	 * @return: java.lang.Object
//	 *
//	 * @作者： Mr.Shu
//	 *
//	 * @创建时间：2017/4/25 11:36
//	 */
//	@ResponseBody
//	@RequestMapping(value = "/updateLogistics", method = RequestMethod.POST)
//	public Object updateLogistics(PhMerchantOrder phMerchantOrder, HttpServletRequest request) throws Exception {
//		SessionUserVo userVo = this.getLoginUser(request);//判断当前登录中是否是该订单的代理商
//		if (userVo.getId().equals(phMerchantOrder.getAgentId())) {
//			return iMerchantOrderService.updateLogistics(phMerchantOrder);
//		}else{
//            return new Result(false, OrderExceptionEnum.USER_ERROR.getCode(), OrderExceptionEnum.USER_ERROR.getMsg());
//        }
//    }
//}
