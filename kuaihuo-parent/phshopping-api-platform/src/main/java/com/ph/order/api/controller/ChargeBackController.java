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
//import cm.ph.shopping.facade.order.dto.PhLogisticsDTO;
//import cm.ph.shopping.facade.order.dto.PhManagerOrderAddressDTO;
//import cm.ph.shopping.facade.order.dto.PhMerchantOrderProductsDTO;
//import cm.ph.shopping.facade.order.dto.PhProductForOderUseDTO;
//import cm.ph.shopping.facade.order.entity.PhMerchantOrder;
//import cm.ph.shopping.facade.order.service.IMerchantOrderService;
//import cm.ph.shopping.facade.order.service.IPhManagerOrderAddressService;
//import cm.ph.shopping.facade.order.vo.PhLogisticsVO;
//import cm.ph.shopping.facade.order.vo.PhManagerOrderAddressVO;
//import cm.ph.shopping.facade.order.vo.PhMerchantOrderProductsVO;
//
///**
// * 
// * @项目：phshopping-facade-
// *
// * @描述：商户发货、取消订单控制层
// *
// * 					@作者： 杨颜光
// *
// * @创建时间：2017年3月28日 下午1:42:47
// *
// * @Copyright by 杨颜光
// */
//@Controller
//@RequestMapping("web/forOrder")
//public class ChargeBackController extends BaseController {
//
//	@Reference(version = "1.0.0")
//	private IPhManagerOrderAddressService iPhManagerOrderAddressService;
//
//	@Reference(version = "1.0.0")
//	private IProductService iProductService;
//
//	@Reference(version = "1.0.0")
//	private IMerchantOrderService iMerchantOrderService;
//
//	/**
//	 * 取消订单
//	 * 
//	 * @param orderId
//	 * @param request
//	 * @return Result
//	 * @author 杨颜光
//	 * @ 
//	 */
//	@RequestMapping("/chargeBack")
//	@ResponseBody
//	public Result chargeBack(Long orderId, HttpServletRequest request)  {
//		Result  result=new Result();
//		// 获取session，判断是商户取消订单还是代理商
//		SessionUserVo info = getLoginUser(request);
//			// 查询订单下的商品
//			PhMerchantOrderProductsDTO orderProductsDto = new PhMerchantOrderProductsDTO();
//			orderProductsDto.setMerchantOrderId(orderId);
//			List<PhMerchantOrderProductsVO> list = this.iPhManagerOrderAddressService
//					.findOrderProducts(orderProductsDto);
//			// 商品集合
//			List<PhProductForOderUseDTO> productDtoList = new ArrayList<PhProductForOderUseDTO>();
//			// 查询订单下的商品对应商品表数据 进行商品数量 计算操作
//			for (PhMerchantOrderProductsVO phMerchantOrderProductsVo : list) {
//				PhProductDTO phProductDtoForQuery = new PhProductDTO();
//				phProductDtoForQuery.setId(phMerchantOrderProductsVo.getProductId());
//				PhProductVO phProductVo = this.iProductService.getPhProduct(phProductDtoForQuery);
//				// 当前退单商品数量加上现有商品数量 为商品数量
//				phProductVo.setProductCount(phMerchantOrderProductsVo.getNum() + phProductVo.getProductCount());
//
//				PhProductForOderUseDTO forOderUseDto= new PhProductForOderUseDTO();
//				forOderUseDto.setId(phProductVo.getId());
//				forOderUseDto.setProductCount(phProductVo.getProductCount());
//				productDtoList.add(forOderUseDto);
//			}
//
//			PhMerchantOrder order = new PhMerchantOrder();
//			order.setId(orderId);
//			order.setCancelUserId(info.getId());
//			order.setUpdateTime(new Date());
//			order.setStatus(5);
//			result=this.iPhManagerOrderAddressService.orderProductBack(order, productDtoList);
//			return result;
//	}
//
//	/**
//	 * 获取未删除的物流信息（商户代理商通用）
//	 * 
//	 * @author 杨颜光
//	 */
//	@RequestMapping("/getLogistics")
//	@ResponseBody
//	public Result getLogisticsInformation() {
//		// 获取session
//		Result result = new Result();
//		PhLogisticsDTO  logisticsDto = new PhLogisticsDTO();
//		List<PhLogisticsVO> list = this.iPhManagerOrderAddressService.findLogistics(logisticsDto);
//		result.setData(list);
//		return result;
//	}
//
//	/**
//	 * 获取当前用户的是发货地址 （商户代理商通用）
//	 * @author 杨颜光
//	 */
//	@RequestMapping("/shippingAddress")
//	@ResponseBody
//	public Result getSssionUserShippingAddress(HttpServletRequest request) {
//		// 获取session
//		SessionUserVo info = getLoginUser(request);
//		Result result = new Result();
//		PhManagerOrderAddressDTO addressDto = new PhManagerOrderAddressDTO();
//		addressDto.setUserId(info.getId());
//		addressDto.setIsable(1);// 已经启用的地址
//		List<PhManagerOrderAddressVO> list = this.iPhManagerOrderAddressService.findAddress(addressDto);
//		result.setData(list);
//		return result;
//	}
//
//	/***
//	 * 发货方法
//	 * 
//	 * @param request
//	 * @return
//	 * @author 杨颜光
//	 */
//	@RequestMapping("/Shipping")
//	@ResponseBody
//	public Result Shipping(@ModelAttribute PhMerchantOrder phMerchantOrder, HttpServletRequest request)   {
//		Result  result=new Result();
//		// 获取session
//		SessionUserVo info = getLoginUser(request);
//		//先查询发货详细地址和联系人以及联系人电话
//		PhManagerOrderAddressDTO dressDto= new PhManagerOrderAddressDTO();
//		dressDto.setUserId(info.getId());
//		dressDto.setId(phMerchantOrder.getSendAddressId());
//		PhManagerOrderAddressVO addressVoQuery = this.iPhManagerOrderAddressService.findAddress(dressDto).get(0);
//		//给phMerchantOrder赋值
////		phMerchantOrder.setSendAddress(addressVoQuery.getProvinceName()+addressVoQuery.getCityName()+addressVoQuery.getAreaName()+addressVoQuery.getTownName()+addressVoQuery.getAddress());
//		phMerchantOrder.setSendAddress(addressVoQuery.getProvinceName()+addressVoQuery.getCityName()+addressVoQuery.getAreaName()+addressVoQuery.getAddress());
//		phMerchantOrder.setSendTelPhone(addressVoQuery.getTelPhone());
//		phMerchantOrder.setSendContacts(addressVoQuery.getContacts());
//		
//		phMerchantOrder.setUpdateTime(new Date());
//		phMerchantOrder.setStatus(3);
//		result=this.iMerchantOrderService.updateForShipping(phMerchantOrder);
//		return result;
//	}
//
//}
//
