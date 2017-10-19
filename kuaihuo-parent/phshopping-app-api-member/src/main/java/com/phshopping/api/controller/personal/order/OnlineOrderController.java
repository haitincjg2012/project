package com.phshopping.api.controller.personal.order;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.ph.shopping.common.core.config.properties.WebProperties;
import com.ph.shopping.common.core.customenum.CacheKeyTypeEnum;
import com.ph.shopping.common.core.customenum.PayTypeEnum;
import com.ph.shopping.common.core.other.annotation.Token;
import com.ph.shopping.common.util.container.ContainerUtil;
import com.ph.shopping.common.util.core.RespCode;
import com.ph.shopping.common.util.core.ResultUtil;
import com.ph.shopping.common.util.date.DateUtil;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.common.util.rsa.MD5;
import com.ph.shopping.common.util.transform.MoneyTransUtil;
import com.ph.shopping.facade.member.menum.MemberResultEnum;
import com.ph.shopping.facade.member.service.IMemberService;
import com.ph.shopping.facade.member.service.IMessageService;
import com.ph.shopping.facade.member.service.IPayPasswordService;
import com.ph.shopping.facade.score.service.IScoreService;
import com.ph.shopping.facade.spm.dto.MerchantDTO;
import com.ph.shopping.facade.spm.service.IMerchantService;
import com.ph.shopping.facade.spm.spmEnum.SPMEnum;
import com.ph.shopping.facade.spm.vo.MerchantVO;
import com.phshopping.api.controller.dto.AppAddOrderOnlineDTO;
import com.phshopping.api.controller.dto.AppAddOrderUnlineDTO;
import com.phshopping.api.controller.personal.order.OrderController.OrderDataService;
import com.phshopping.api.controller.vo.AddOrderVO;
import com.phshopping.api.controller.vo.OrderVO;
import com.phshopping.api.entity.MemberInfo;
import com.phshopping.api.uitl.UserCacheUtil;

import cm.ph.shopping.facade.order.constant.OrderResultEnum;
import cm.ph.shopping.facade.order.dto.AddMemberOrderOnlineDTO2;
import cm.ph.shopping.facade.order.dto.AddMemberOrderUnlineDTO;
import cm.ph.shopping.facade.order.entity.PhMemberOrderOnlineSku;
import cm.ph.shopping.facade.order.entity.ShopCart;
import cm.ph.shopping.facade.order.service.IOnlineOrderService;
import cm.ph.shopping.facade.order.vo.PhMemberOrderUnlineVO;

/**
 * 
 * @ClassName:  OnlineOrderController   
 * @Description:线上订单相关接口   
 * @author: 李治桦
 * @date:   2017年5月23日 下午3:17:01     
 * @Copyright: 2017
 */
@RestController
@RequestMapping("api/personal/onlineorder")
public class OnlineOrderController {

	/**
	 * 日志对象
	 */
	private static final Logger logger = LoggerFactory.getLogger(OrderController.class);
	
	/**线上订单接口*/
    @Reference(version = "1.0.0")
    private IOnlineOrderService onlineOrderService;
    /**积分接口*/
    @Reference(version = "1.0.0")
    private IScoreService scoreService;
    /**会员接口*/
    @Reference(version = "1.0.0")
	private IMemberService memberService;
    /**消息推送*/
    @Reference(version = "1.0.0", retries = 0, timeout = 30000)
	private IMessageService messageService;
    /**支付密码服务*/
    @Reference(version = "1.0.0", retries = 0)
	private IPayPasswordService payPasswordService;
    /**商户接口*/
    @Reference(version = "1.0.0")
    private IMerchantService iMerchantService;
    /**properties 操作服务*/
    @Autowired
	private WebProperties webConfig;
    /**
     * 订单相关数据封装
     */
    private final OrderDataService orderData = new OrderDataService();
    
    /**
   	 * 
   	 * @Title: onceMoreOnlineOrder   
   	 * @Description: 再来一单
   	 * @param: @return      
   	 * @return: Result
   	 * @author：李治桦
   	 * @throws
   	 */
    @Token(key = "token", isCache = true, cacheType = CacheKeyTypeEnum.HASH)
	@RequestMapping(value = "/onceMoreOnlineOrder",method = RequestMethod.POST)
	@ResponseBody
	public Result onceMoreOnlineOrder(HttpServletRequest request,AddMemberOrderOnlineDTO2 order){
    	
    	logger.info("再来一单：订单ID = {} ", JSON.toJSONString(order.getOrderOnlineId()));
		Result result = ResultUtil.getResult(RespCode.Code.REQUEST_DATA_ERROR);
		Long orderId=order.getOrderOnlineId();	//订单ID
		if (orderId==null||"".equals(orderId)) {
			result.setMessage("订单ID不能为空");
			return result;
		}
			result=onlineOrderService.onceMoreOnlineOrder(orderId);
		logger.info("再来一单：返回参数 = {} ", JSON.toJSONString(result));
		return result;
    	
    }	
    /**
   	 * 
   	 * @Title: toAddOnLineOrder   
   	 * @Description: 去预定页面
   	 * @param: @return      
   	 * @return: Result
   	 * @author：李治桦
   	 * @throws
   	 */
    @Token(key = "token", isCache = true, cacheType = CacheKeyTypeEnum.HASH)
	@RequestMapping(value = "/toAddOnLineOrder",method = RequestMethod.POST)
	@ResponseBody
	public Result toAddOnLineOrder(HttpServletRequest request,ShopCart shopCart){
    	
    	logger.info("取预定页面处理参数：购物车信息 = {} ", JSON.toJSONString(shopCart));
		Result result = ResultUtil.getResult(RespCode.Code.REQUEST_DATA_ERROR);
		//验证参数
		if (shopCart.getMemberId()==null||"".equals(shopCart.getMemberId())) {
			result.setMessage("会员ID不能为空");
			return result;
		}
		if (shopCart.getMerchantId()==null||"".equals(shopCart.getMerchantId())) {
			result.setMessage("商户ID不能为空");
			return result;
		}
			result=onlineOrderService.toAddOnLineOrder(shopCart);
		logger.info("取预定页面结果：购物车信息 = {} ", JSON.toJSONString(result));
		return result;
    	
    }	
	/**
   	 * 
   	 * @Title: cleanShopCart   
   	 * @Description: 清空购物车接口(用户重新选择时间清空餐位)
   	 * @param: @return      
   	 * @return: Result
   	 * @author：李治桦
   	 * @throws
   	 */
    @Token(key = "token", isCache = true, cacheType = CacheKeyTypeEnum.HASH)
	@RequestMapping(value = "/cleanShopCart",method = RequestMethod.POST)
	@ResponseBody
	public Result cleanShopCart(HttpServletRequest request,ShopCart shopCart){
    	
    	logger.info("清空购物车接口参数： = {} ", JSON.toJSONString(shopCart));
		Result result = ResultUtil.getResult(RespCode.Code.REQUEST_DATA_ERROR);
		//验证参数
		if (shopCart.getMemberId()==null||"".equals(shopCart.getMemberId())) {
			result.setMessage("会员ID不能为空");
			return result;
		}
		if (shopCart.getMerchantId()==null||"".equals(shopCart.getMerchantId())) {
			result.setMessage("商户ID不能为空");
			return result;
		}
			result=onlineOrderService.cleanShopCart(shopCart);
		logger.info("清空购物车接口： = {} ", JSON.toJSONString(result));
		return result;
    	
    }	
    /**
	 * 
	 * @Title: addUnLineOrder   
	 * @Description: 创建线上订单  
	 * @param: @return      
	 * @return: Result
	 * @author：李治桦
	 * @throws
	 */
	@Token(key = "token", isCache = true, cacheType = CacheKeyTypeEnum.HASH)
	@RequestMapping(value = "/addOnLineOrder",method = RequestMethod.POST)
	@ResponseBody
	public Result addOnLineOrder(HttpServletRequest request, AppAddOrderOnlineDTO dto,String dishs,String dCount,String subscriptionMoney,Long shopCartId) {
		logger.info("预定订单处理参数：AddOrderUnlineDTO = {} ", JSON.toJSONString(dto), JSON.toJSONString(dCount),JSON.toJSONString(dishs), JSON.toJSONString(subscriptionMoney));
		Result result = ResultUtil.getResult(RespCode.Code.REQUEST_DATA_ERROR);
	
			
			//转换时间类型
			Date hopeServiceDate=null;
			try {
				hopeServiceDate = DateUtil.parseDateTime(dto.getHopeServiceDate());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// 创建订单
			AddMemberOrderOnlineDTO2 addDto = new AddMemberOrderOnlineDTO2();
			addDto.setMerchantId(dto.getMerchantId());
			addDto.setMemberId(dto.getMemberId());
			addDto.setMerchantId(dto.getMerchantId());
			addDto.setMessage(dto.getMessage());
			addDto.setHopeServiceDate(hopeServiceDate);
			result=onlineOrderService.addOnLineOrder(addDto,dishs,dCount,subscriptionMoney,shopCartId);
			// 封装返回数据
			result.setData(orderData.getAddOrderVO(result));
		logger.info("创建订单返回数据 result = {} ", JSON.toJSONString(result));
		return result;
	}
	/**
	 * 
	 * @Title: getMerchantVO   
	 * @Description: 根据商户ID得到商户信息   
	 * @param: @param merchantId
	 * @param: @return      
	 * @return: MerchantVO
	 * @author：李杰      
	 * @throws
	 */
	private MerchantVO getMerchantVO(Long merchantId){
		MerchantDTO merchantTypeDTO = new MerchantDTO();
        merchantTypeDTO.setId(merchantId);
        merchantTypeDTO.setStatus(SPMEnum.merchantStatus.ONE.getIndex());
        List<MerchantVO> merchantVOList = iMerchantService.getMerchantListDetail(merchantTypeDTO);
        if(null != merchantVOList && !merchantVOList.isEmpty()){
        	return merchantVOList.get(0);
        }
        return null;
	}
	/**
	 * 
	 * @ClassName:  OrderDataService   
	 * @Description:订单数据服务  
	 * @author: 李杰
	 * @date:   2017年6月19日 下午3:31:44     
	 * @Copyright: 2017
	 */
	protected class OrderDataService {
		/**
		 * @throws
		 * @Title: getOrders
		 * @Description: 封装订单相关返回数据
		 * @param: @param list
		 * @param: @return
		 * @return: List<OrderVO>
		 * @author：李杰
		 */
		public List<OrderVO> getOrders(Result result) {
			List<OrderVO> orders = null;
			if (null != result && result.isSuccess()) {
				Object obj = result.getData();
				if (obj instanceof List) {
					@SuppressWarnings("unchecked")
					List<PhMemberOrderUnlineVO> list = (List<PhMemberOrderUnlineVO>) obj;
					orders = ContainerUtil.lList();
					for (PhMemberOrderUnlineVO vo : list) {
						OrderVO order = new OrderVO();
						order.setId(vo.getId());
						order.setCreateTime(vo.getCreateTime());
						order.setMerchantName(vo.getMerchantName());
						order.setMerchantTel(vo.getMerchantTelPhone());
						order.setOrderNo(vo.getOrderNo());
						order.setPayMoney(MoneyTransUtil.format(vo.getOrderMoney()));
						order.setStatus(vo.getStatus().byteValue());
						orders.add(order);
					}
				}
			}
			return orders;
		}

		/**
		 * @throws
		 * @Title: getAddOrderVO
		 * @Description: 封装下单返回结果数据
		 * @param: @param result
		 * @param: @return
		 * @return: AddOrderVO
		 * @author：李杰
		 */
		@SuppressWarnings("rawtypes")
		public AddOrderVO getAddOrderVO(Result result) {
			if (null != result && result.isSuccess()) {
				Object obj = result.getData();
				if (obj instanceof Map) {
					Map map = (Map) obj;
					Object id = map.get("id");
					Object orderNo = map.get("orderNo");
					AddOrderVO vo = new AddOrderVO();
					vo.setId(id == null ? null : Long.valueOf(id.toString()));
					vo.setOrderNo(orderNo == null ? null : orderNo.toString());
					return vo;
				}
			}
			return null;
		}
	}
	/*=======================================快火二期==========================================*/
	/**
	 *
	 * @Title: addOnLineOrderTwo
	 * @Description: 创建线上订单 (二次开发)  预定包间接口
	 * @param: @return   (此处的商户ID为userId)
	 * @return: Result
	 * @author：李治桦
	 * @throws
	 */
//	@Token(key = "token", isCache = true, cacheType = CacheKeyTypeEnum.HASH)
	@RequestMapping(value = "/addOnLineOrderTwo",method = RequestMethod.POST)
	@ResponseBody
	public Result addOnLineOrderTwo(HttpServletRequest request, AppAddOrderOnlineDTO dto,Long dishId) {
		logger.info("预定包间订单处理参数：AddOrderUnlineDTO = {} ", JSON.toJSONString(dto), JSON.toJSONString(dishId));
		Result result = ResultUtil.getResult(RespCode.Code.REQUEST_DATA_ERROR);
		if(dto.getHopeServiceDate()==null){
			return  result;
		}
		//截取字符串获得预计到店时间和预计离店时间
		String[] split = dto.getHopeServiceDate().split(" ");
		String[] m=split[1].split("-");  //截取时间    //0是日期  1是时间
		String hope=split[0]+" "+m[0];  //期望时间是日期+截取字符串前时间
		String leave=split[0]+" "+m[1];	//离店时间时间是日期+截取字符串后时间
		//转换时间类型
		Date hopeServiceDate=null;
		Date predictServiceDate=null;
		try {
			hopeServiceDate = DateUtil.parseDateTime(hope);		//到店时间
			predictServiceDate=DateUtil.parseDateTime(leave);  //离店时间
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 创建订单
		AddMemberOrderOnlineDTO2 addDto = new AddMemberOrderOnlineDTO2();
		addDto.setMerchantId(dto.getMerchantId());
		addDto.setMemberId(dto.getMemberId());
		addDto.setMerchantId(dto.getMerchantId());
		addDto.setMessage(dto.getMessage());
		addDto.setHopeServiceDate(hopeServiceDate);
		addDto.setPredictServiceDate(predictServiceDate);
		result=onlineOrderService.addOnLineOrderTwo(addDto,dishId);
		// 封装返回数据
		result.setData(orderData.getAddOrderVO(result));
		logger.info("预定包间订单返回数据 result = {} ", JSON.toJSONString(result));
		return result;
	}
	/**
	 * 注释
	 * @Title: addOnLineDishOrder
	 * @Description: 创建线上订单 (二次开发)  预定菜品接口
	 * @param: @return  orderId dishs memberId merchantId(此处为userId)
	 * @return: Result
	 * @author：李治桦
	 * @throws
	 */
	//@Token(key = "token", isCache = true, cacheType = CacheKeyTypeEnum.HASH)
	@RequestMapping(value = "/addOnLineDishOrder",method = RequestMethod.POST)
	@ResponseBody
	public Result addOnLineDishOrder(String dishs,Long orderId,Long memberId,Long merchantId){
		logger.info("预定菜品订单处理参数：dishs = {} ", JSON.toJSONString(dishs), JSON.toJSONString(orderId));
		Result result = ResultUtil.getResult(RespCode.Code.REQUEST_DATA_ERROR);
		if(dishs==null||orderId==null||memberId==null||merchantId==null){
			return  result;
		}
		result=onlineOrderService.addOnLineDishOrder(dishs,orderId,memberId,merchantId);
		logger.info("预定菜品订单返回数据 result = {} ", JSON.toJSONString(result));
		return result;
	}
	/**
	 *
	 * @Title: onceMoreOnlineOrderTwo
	 * @Description: 再来一单(快火二期)
	 * @param: @return
	 * @return: Result
	 * @author：李治桦
	 * @throws
	 */
	//@Token(key = "token", isCache = true, cacheType = CacheKeyTypeEnum.HASH)
	@RequestMapping(value = "/onceMoreOnlineOrderTwo",method = RequestMethod.POST)
	@ResponseBody
	public Result onceMoreOnlineOrderTwo(HttpServletRequest request,AddMemberOrderOnlineDTO2 order){

		logger.info("再来一单：订单ID = {} ", JSON.toJSONString(order.getOrderOnlineId()));
		Result result = ResultUtil.getResult(RespCode.Code.REQUEST_DATA_ERROR);
		Long orderId=order.getOrderOnlineId();	//订单ID
		if (orderId==null||"".equals(orderId)) {
			result.setMessage("订单ID不能为空");
			return result;
		}
		result=onlineOrderService.onceMoreOnlineOrderTwo(orderId);
		logger.info("再来一单：返回参数 = {} ", JSON.toJSONString(result));
		return result;

	}
}