//package com.ph.order.api.controller;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//import javax.servlet.http.HttpServletRequest;
//
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import com.alibaba.dubbo.config.annotation.Reference;
//import com.ph.base.BaseController;
//import com.ph.shopping.common.util.result.Result;
//import com.ph.shopping.facade.permission.vo.SessionUserVo;
//import com.ph.shopping.facade.product.dto.PhProductDTO;
//import com.ph.shopping.facade.product.service.IProductService;
//import com.ph.shopping.facade.product.vo.PhProductVO;
//
//import cm.ph.shopping.facade.order.dto.PhAgentOrderProductsDTO;
//import cm.ph.shopping.facade.order.dto.PhManagerOrderAddressDTO;
//import cm.ph.shopping.facade.order.dto.PhProductForOderUseDTO;
//import cm.ph.shopping.facade.order.entity.PhAgentOrder;
//import cm.ph.shopping.facade.order.service.IAgentOrderService;
//import cm.ph.shopping.facade.order.service.IPhManagerOrderAddressService;
//import cm.ph.shopping.facade.order.vo.PhAgentOrderProductsVO;
//import cm.ph.shopping.facade.order.vo.PhManagerOrderAddressVO;
//
///**
// * 
// * @项目：phshopping-facade-
// *
// * @描述：代理商发货、取消订单控制层
// *
// * 					@作者： 杨颜光
// *
// * @创建时间：2017年3月28日 下午1:42:47
// *
// * @Copyright by 杨颜光
// */
//@Controller
//@RequestMapping("web/forAgentOrder")
//public class AgentChargeBackController extends BaseController {
//
//	@Reference(version = "1.0.0")
//	private IPhManagerOrderAddressService iPhManagerOrderAddressService;
//
//	@Reference(version = "1.0.0")
//	private IProductService iProductService;
//
//	@Reference(version = "1.0.0")
//	private IAgentOrderService iAgentOrderService;
//
//	/**
//	 * 取消订单方法
//	 * @param orderId
//	 * @param request
//	 * @author 杨颜光
//	 * @ 
//	 * @date 2017年4月24日 下午1:42:47
//	 *
//	 */
//	@RequestMapping("/chargeBack")
//	@ResponseBody
//	public Result agentChargeBack(Long orderId, HttpServletRequest request)  {
//		Result  result=new Result();
//		// 获取session，判断是商户取消订单还是代理商 PhAgentOrder PhAgentOrderProducts
//		SessionUserVo info = getLoginUser(request);
//			// 查询订单下的商品
//			PhAgentOrderProductsDTO agentOrderProductsDto = new PhAgentOrderProductsDTO();
//			agentOrderProductsDto.setAgentOrderId(orderId);
//			List<PhAgentOrderProductsVO> list = this.iPhManagerOrderAddressService
//					.findAgentOrderProducts(agentOrderProductsDto);
//			// 商品集合
//			List<PhProductForOderUseDTO> productDtoList = new ArrayList<PhProductForOderUseDTO>();
//			// 查询订单下的商品对应商品表数据 进行商品数量 计算操作
//			for (PhAgentOrderProductsVO phAgentOrderProductsVo : list) {
//				PhProductDTO phProductDtoForQuery = new PhProductDTO();
//				phProductDtoForQuery.setId(phAgentOrderProductsVo.getProductId());
//				PhProductVO phProductVo = this.iProductService.getPhProduct(phProductDtoForQuery);
//				// 当前退单商品数量加上现有商品数量 为商品数量
//				phProductVo.setProductCount(phAgentOrderProductsVo.getNum() + phProductVo.getProductCount());
//
//				PhProductForOderUseDTO forOderUseDto = new PhProductForOderUseDTO();
//				forOderUseDto.setId(phProductVo.getId());
//				forOderUseDto.setProductCount(phProductVo.getProductCount());
//				productDtoList.add(forOderUseDto);
//			}
//
//			PhAgentOrder order = new PhAgentOrder();
//			order.setId(orderId);
//			order.setCancelUserId(info.getId());
//			order.setUpdateTime(new Date());
//			order.setStatus(5);
//			result=this.iPhManagerOrderAddressService.agentOrderProductBack(order, productDtoList);
//			return result;
//	}
//
//	/***
//	 * 发货方法
//	 * 
//	 * @param request
//	  * @author 杨颜光
//	 * @date 2017年4月24日 下午17:18:42
//	 */
//	@RequestMapping("/shipping")
//	@ResponseBody
//	public Result shipping(@ModelAttribute PhAgentOrder phAgentOrder, HttpServletRequest request)   {
//		Result  result=new Result();
//		// 获取session
//		SessionUserVo info = getLoginUser(request);
//		
//		PhManagerOrderAddressDTO addressDto = new PhManagerOrderAddressDTO();
//		addressDto.setUserId(info.getId());
//		addressDto.setId(phAgentOrder.getSendAddressId());
//		PhManagerOrderAddressVO addressVoQuery = this.iPhManagerOrderAddressService.findAddress(addressDto).get(0);
//		//给phAgentOrder赋值
//		phAgentOrder.setSendAddress(addressVoQuery.getProvinceName()+addressVoQuery.getCityName()+addressVoQuery.getAreaName()+addressVoQuery.getAddress());
//		phAgentOrder.setSendTelPhone(addressVoQuery.getTelPhone());
//		phAgentOrder.setSendContacts(addressVoQuery.getContacts());
//		
//		phAgentOrder.setUpdateTime(new Date());
//		phAgentOrder.setStatus(3);
//		result=	this.iAgentOrderService.updateForShipping(phAgentOrder);
//		return result;
//	}
//
//}
