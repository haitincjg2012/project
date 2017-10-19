//package com.ph.order.api.controller;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import javax.servlet.http.HttpServletRequest;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.ResponseBody;
//import com.alibaba.dubbo.common.utils.CollectionUtils;
//import com.alibaba.dubbo.common.utils.StringUtils;
//import com.alibaba.dubbo.config.annotation.Reference;
//import com.alibaba.fastjson.JSON;
//import com.ph.base.BaseController;
//import com.ph.queue.OrderTask;
//import com.ph.queue.request.OrderTaskRequest;
//import com.ph.shopping.common.util.result.Result;
//import com.ph.shopping.facade.permission.vo.SessionUserVo;
//import com.ph.shopping.facade.product.service.IProductService;
//import com.ph.shopping.facade.spm.entity.Merchant;
//import com.ph.shopping.facade.spm.entity.Position;
//import com.ph.shopping.facade.spm.service.IMerchantService;
//import com.ph.shopping.facade.spm.vo.AgentVO;
//import cm.ph.shopping.facade.order.dto.PhManagerOrderAddressDTO;
//import cm.ph.shopping.facade.order.dto.ProductOrderDTO;
//import cm.ph.shopping.facade.order.entity.PhManagerOrderAddress;
//import cm.ph.shopping.facade.order.request.OrderRequest;
//import cm.ph.shopping.facade.order.service.IPhManagerOrderAddressService;
//import cm.ph.shopping.facade.order.vo.PhManagerOrderAddressVO;
//
///**
// * @项目：phshopping-facade-
// *
// * @描述：商户结算控制层
// *
// * 			@作者： 杨颜光
// *
// * @创建时间：2017年3月24日 上午9:17:40
// *
// * @Copyright by 杨颜光
// */
//@Controller
//@RequestMapping("/web/merchantBalanceAccounts")
//public class MerchantBalanceAccountsController extends BaseController {
//	private static Logger logger = LoggerFactory.getLogger(MerchantBalanceAccountsController.class);
//
//	@Reference(version = "1.0.0")
//	public IPhManagerOrderAddressService iPhManagerOrderAddressService;
//
//	@Reference(version = "1.0.0")
//	public IMerchantService iMerchantService;
//
//	@Reference(version = "1.0.0")
//	IProductService productService;
//
//	@Autowired
//	OrderTask orderTask;
//
//	/**
//	 * 调转到结算页面 ps:其中查询商户进货明细由郑朋 完成 ，新增收货地址有杨颜光/完成
//	 * 
//	 * @param model
//	 * @param ids
//	 * @return String
//	 * @author 杨颜光  郑鹏
//	 */
//	@RequestMapping(value = "/balanceList", method = RequestMethod.GET)
//	public String toBalanceAccounts(Model model, String ids, HttpServletRequest request) {
//			SessionUserVo info = getLoginUser(request); // 获取当前用户
//			Result result =new Result();
//			// 查询当前进货用户是否有收货地址
//			logger.info("商户结算页面入参，ids={}", ids);
//			PhManagerOrderAddressDTO addressDto = new PhManagerOrderAddressDTO();
//			addressDto.setUserId(info.getId());
//			addressDto.setIsable(1);// 查询未删除的地址
//			List<PhManagerOrderAddressVO> list = this.iPhManagerOrderAddressService.findAddress(addressDto);
//			model.addAttribute("addressList", list);
//			// 查询进货商品详情 参数Ids(多个用逗号分隔)
//			if (StringUtils.isNotEmpty(ids)) {
//				String[] productIds = ids.split(",");
//				List<Long> paramsList = new ArrayList<>();
//				for (String id : productIds) {
//					paramsList.add(Long.valueOf(id));
//				}
//			result = productService.getPhProductList(paramsList);
//				logger.info("通过商品ids查询商品信息接口返回值，result={}", JSON.toJSONString(result));
//				if (null != result && result.isSuccess()) {
//					model.addAttribute("productList", result.getData());
//				}
//			}
//			model.addAttribute("roleCode", info.getSessionRoleVo().get(0).getRoleCode());
//		return "/merchant/balanceAccountsList";
//	}
//
//
//	/**
//	 * 添加收货地址方法
//	 * 
//	 * @param address
//	 * @param request
//	 * @author 杨颜光
//	 * @date 2017年4月24日 上午17:2:40
//	 */
//	@RequestMapping(value = "/addAddress", method = RequestMethod.POST)
//	@ResponseBody
//	public Result addOrderAddress(@ModelAttribute PhManagerOrderAddress address, HttpServletRequest request) {
//		SessionUserVo info = getLoginUser(request); // 获取当前用户
//		Result result = new Result();
//			// 获取隐藏域的type值的值，看用户是否是第一次添加收货地址
//			String type = request.getParameter("type");
//			if (type == "" || type == null) {
//				address.setIsDefault(2);// 非默认地址
//			}
//			if (type.equals("1")) {
//				address.setIsDefault(1);// 默认地址
//			}
//			address.setUserId(info.getId());
//			address.setIsable(1);
//			//通过positionID查询省市区ID
//			Position position = iMerchantService.getPositionById(address.getPositionId());
//			address.setProvinceId(position.getProvinceId());
//			address.setCityId(position.getCityId());
//			address.setAreaId(position.getCountyId());
//			address.setCreateTime(new Date());
//			result = this.iPhManagerOrderAddressService.addOrderAddress(address);
//		if(result.isSuccess()){
//		// 查询当前添加成功的地址信息放在result.data
//		PhManagerOrderAddressDTO addressDto = new PhManagerOrderAddressDTO();
//		addressDto.setUserId(info.getId());
//		addressDto.setIsable(1);
//		addressDto.setId(Long.valueOf(result.getData().toString()));
//		List<PhManagerOrderAddressVO> list = this.iPhManagerOrderAddressService.findAddress(addressDto);
//		result.setData(list.get(0));
//		}
//		return result;
//	}
//
//	/**
//	 * 方法名：addOrder 方法描述：商户新增下货单
//	 * 
//	 * @param vo
//	 * @param request
//	 * @author 郑朋
//	 * @date 2017年4月24日 上午17:2:40
//	 */
//	@RequestMapping("addOrder")
//	@ResponseBody
//	public Object addOrder(@RequestBody OrderRequest vo, HttpServletRequest request) {
//		logger.info("新增下货单页面参数,vo={}", JSON.toJSONString(vo));
//		Result result = new Result(false,"0405","新增订单失败");
//		try {
//			SessionUserVo info = getLoginUser(request); // 获取当前用户
//			Byte roleCode = info.getSessionRoleVo().get(0).getRoleCode();
//			Integer orderType = 0;
//			Long agentId = null;
//			if (roleCode == 6) {
//				// 查询商户所属代理商id
//				orderType = 1;
//				agentId = getAgentId(info.getId());
//			} else if (roleCode == 3) {
//				// 市级代理商向供应商进货
//				orderType = 2;
//			}
//			if (null != vo && CollectionUtils.isNotEmpty(vo.getProductOrderDTOList())) {
//				vo.setOrderType(orderType);
//				for (ProductOrderDTO productOrderDTO : vo.getProductOrderDTOList()) {
//					if (roleCode == 6) {
//						productOrderDTO.setMerchantId(info.getId());
//						productOrderDTO.setAgentId(agentId);
//					} else if (roleCode == 3) {
//						productOrderDTO.setAgentId(info.getId());
//					}
//				}
//				OrderTaskRequest<OrderRequest> queueRequest = new OrderTaskRequest<>();
//				queueRequest.setData(vo);
//				result = orderTask.callSubmit(queueRequest);
//				result.setData(orderType);
//				logger.info("新增下货单返回值,result={}", JSON.toJSONString(result));
//			}
//		} catch (Exception e) {
//			logger.error("新增下货单异常", e);
//		}
//		return result;
//	}
//
//	/**
//	 * 通过商户id查询市级代理商id
//	 * 
//	 * @return
//	 */
//	private Long getAgentId(Long merchantId) {
//		logger.info("通过商户id查询市级代理商id入参，merchantId={}", merchantId);
//		Long agentId = null;
//		Merchant merchant = new Merchant();
//		merchant.setId(merchantId);
//		Result result = iMerchantService.getCityAgentByMerchant(merchant);
//		logger.info("通过商户id查询市级代理商id返回值，result={}", JSON.toJSONString(result));
//		if (null != result && result.isSuccess() && null != result.getData()) {
//			agentId = ((AgentVO) result.getData()).getId();
//		}
//		if (null == agentId) {
//			throw new RuntimeException("获取市级代理商id为空");
//		}
//		return agentId;
//	}
//}
