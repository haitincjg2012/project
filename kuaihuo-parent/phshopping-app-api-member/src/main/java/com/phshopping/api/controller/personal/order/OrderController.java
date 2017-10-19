package com.phshopping.api.controller.personal.order;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import cm.ph.shopping.facade.order.dto.RewardUnlineOrderDTO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
import com.ph.shopping.common.util.page.PageBean;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.common.util.rsa.MD5;
import com.ph.shopping.common.util.transform.MoneyTransUtil;
import com.ph.shopping.facade.member.dto.PayPasswordQueryDTO;
import com.ph.shopping.facade.member.entity.TradersPassword;
import com.ph.shopping.facade.member.menum.MemberResultEnum;
import com.ph.shopping.facade.member.menum.paypwd.PayPwdEnum;
import com.ph.shopping.facade.member.service.IMemberService;
import com.ph.shopping.facade.member.service.IMessageService;
import com.ph.shopping.facade.member.service.IPayPasswordService;
import com.ph.shopping.facade.score.service.IScoreService;
import com.ph.shopping.facade.spm.dto.MerchantDTO;
import com.ph.shopping.facade.spm.service.IMerchantService;
import com.ph.shopping.facade.spm.spmEnum.SPMEnum;
import com.ph.shopping.facade.spm.vo.MerchantVO;
import com.phshopping.api.controller.dto.AppAddOrderUnlineDTO;
import com.phshopping.api.controller.vo.AddOrderVO;
import com.phshopping.api.controller.vo.OrderVO;
import com.phshopping.api.entity.MemberInfo;
import com.phshopping.api.uitl.UserCacheUtil;

import cm.ph.shopping.facade.order.constant.OrderResultEnum;
import cm.ph.shopping.facade.order.dto.AddMemberOrderUnlineDTO;
import cm.ph.shopping.facade.order.dto.PhMemberOrderUnlineDTO;
import cm.ph.shopping.facade.order.service.IUnlineOrderService;
import cm.ph.shopping.facade.order.vo.PhMemberOrderUnlineVO;
/**
 * 
 * @ClassName:  OrderController   
 * @Description:订单相关接口   
 * @author: 李杰
 * @date:   2017年5月23日 下午3:17:01     
 * @Copyright: 2017
 */
@RestController
@RequestMapping("api/personal/order")
public class OrderController {

	/**
	 * 日志对象
	 */
	private static final Logger logger = LoggerFactory.getLogger(OrderController.class);
	
	/**线下订单接口*/
    @Reference(version = "1.0.0")
    private IUnlineOrderService iUnLineOrerService;
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
     * @Title: getOfflineOrderList   
     * @Description: 查询线下订单   
     * @param: @param rerquest
     * @param: @param token
     * @param: @return      
     * @return: Result
     * @author：李杰      
     * @throws
     */
	@RequestMapping(value = "/findUnlineOrders/{token}/{status}/{pageNum}/{pageSize}", method = RequestMethod.GET)
	public Result findUnlineOrders(HttpServletRequest rerquest, @PathVariable String token, @PathVariable Byte status,
			@PathVariable Integer pageNum, @PathVariable Integer pageSize) {
		logger.info("加载线下订单参数 token ： " + token);
		Result result = UserCacheUtil.getUser(token);
		if (result.isSuccess()) {
			MemberInfo memberInfo = (MemberInfo) result.getData();
			status = status == null ? 1 : status;
			pageNum = pageNum == null ? 1 : pageNum;
			pageSize = pageSize == null ? 10 : pageSize;
			PhMemberOrderUnlineDTO dto = new PhMemberOrderUnlineDTO();
			dto.setMemberId(memberInfo.getId());
			dto.setStatus(status);
			PageBean pagebean = new PageBean();
			pagebean.setPageNum(pageNum);
			pagebean.setPageSize(pageSize);
			result = this.iUnLineOrerService.getUnLineOrderVoList(dto, pagebean);
			// 封装返回数据
			result.setData(orderData.getOrders(result));
		}
		logger.info("查询订单返回数据 result = {} ", JSON.toJSONString(result));
		return result;
	}
	/**
	 * 
	 * @Title: addUnLineOrder   
	 * @Description: 创建线下订单  
	 * @param: @return      
	 * @return: Result
	 * @author：李杰      
	 * @throws
	 */
	@Token(key = "token", isCache = true, cacheType = CacheKeyTypeEnum.HASH)
	@RequestMapping(value = "/addUnLineOrder", method = RequestMethod.POST)
	public Result addUnLineOrder(HttpServletRequest request, AppAddOrderUnlineDTO dto)  throws Exception{
		logger.info("创建线下订单处理参数：AddOrderUnlineDTO = {} ", JSON.toJSONString(dto));
		Result result = ResultUtil.getResult(RespCode.Code.REQUEST_DATA_ERROR);
		String errorStr = dto.validateForm();
		if (StringUtils.isNotBlank(errorStr)) {
			result.setMessage(errorStr);
			return result;
		}
		result = UserCacheUtil.getUser(dto.getToken());
		if (result.isSuccess()) {
			MemberInfo memberInfo = (MemberInfo) result.getData();
			// 校验金额签名
			String sign = getMoneySign(dto.getMerchantId(), dto.getOrderMoney());
			if (!sign.equalsIgnoreCase(dto.getSign())) {
				return ResultUtil.getResult(OrderResultEnum.ORDER_MONEY_NOTMATCH);
			}
			// 支付类型
			final Byte payType = dto.getPayType();
			// 判断是否是积分支付
			final boolean isScorePay = (PayTypeEnum.PAY_TYPE_SCORE.getPayType() == payType);
			// 积分支付时校验支付密码
			if (isScorePay) {
				if (!verifyPayPwd(memberInfo.getId(), dto.getPayPwd())) {
					return ResultUtil.getResult(MemberResultEnum.PAY_PWD_ERROR);
				}
			}
			// 创建线下订单
			AddMemberOrderUnlineDTO addDto = new AddMemberOrderUnlineDTO();
			addDto.setCreaterId(memberInfo.getId());
			addDto.setMemberId(memberInfo.getId());
			// 此处的商户ID 为商户表的userId
			MerchantVO merchantVO = getMerchantVO(dto.getMerchantId());
			if (null == merchantVO) {
				return ResultUtil.getResult(OrderResultEnum.NO_MERCHANT_DATA);
			}
			addDto.setMerchantId(merchantVO.getUserId());
			addDto.setOrderMoney(Double.valueOf(dto.getOrderMoney()));
			addDto.setPayPassWord(dto.getPayPwd());
			addDto.setPayType(dto.getPayType());
			addDto.setBarcodeMark(dto.getBarcodeMark());
			if (isScorePay) {// 积分支付
				result = iUnLineOrerService.addUnlineOrder(addDto, true);
			} else if (PayTypeEnum.PAY_TYPE_ALIPAY.getPayType() == payType) {// 支付宝支付
				result = iUnLineOrerService.addUnlineOrder(addDto, false);
			} else {
				logger.info("添加线下订单支付类型不匹配");
				return ResultUtil.getResult(OrderResultEnum.ADD_UNLINEORDER_FAIL);
			}
			// 封装返回数据
			result.setData(orderData.getAddOrderVO(result));
		}
		logger.info("创建线下订单返回数据 result = {} ", JSON.toJSONString(result));
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
	 * @Title: getMoneySign   
	 * @Description: 得到签名字符串    merchantId > moneySign > money
	 * @param: @param merchantId
	 * @param: @param money
	 * @param: @return      
	 * @return: String
	 * @author：李杰      
	 * @throws
	 */
	private String getMoneySign(Long merchantId, String money) {
		StringBuilder sbud = new StringBuilder();
		sbud.append(money).append(merchantId).append(webConfig.getAppUnlineOrderMoneySign());
		return MD5.getMD5Str(sbud.toString());
	}
	/**
	 * 
	 * @Title: verifyPayPwd   
	 * @Description: 校验支付密码   
	 * @param: @param relatedId
	 * @param: @param pwd
	 * @param: @return      
	 * @return: boolean
	 * @author：李杰      
	 * @throws
	 */
	private boolean verifyPayPwd(Long relatedId, String pwd) {
		PayPasswordQueryDTO dto = new PayPasswordQueryDTO();
		dto.setUserId(relatedId);
		dto.setCustomerType(PayPwdEnum.MEMBER_PAY_PWD.getCode());
		Result result = payPasswordService.getPayPwdInfo(dto);
		if (result.isSuccess()) {
			Object obj = result.getData();
			if (obj instanceof TradersPassword) {
				TradersPassword pwdInfo = (TradersPassword) obj;
				return pwdInfo.getPayPwd().equals(MD5.getMD5Str(pwd));
			}
		}
		return false;
	}
	/**
	 * 
	 * @ClassName:  OrderDataService   
	 * @Description:订单数据服务  
	 * @author: 李杰
	 * @date:   2017年6月19日 下午3:31:44     
	 * @Copyright: 2017
	 */
	protected class OrderDataService{
		/**
		 * 
		 * @Title: getOrders   
		 * @Description: 封装订单相关返回数据   
		 * @param: @param list
		 * @param: @return      
		 * @return: List<OrderVO>
		 * @author：李杰      
		 * @throws
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
		 * 
		 * @Title: getAddOrderVO   
		 * @Description: 封装下单返回结果数据   
		 * @param: @param result
		 * @param: @return      
		 * @return: AddOrderVO
		 * @author：李杰      
		 * @throws
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

	/**
	 * 天天返、刮红包返回金额
	 * @param rewardUnlineOrderDTO
	 * @return
	 */
	@RequestMapping(value = "reward",method = RequestMethod.POST)
	public Result reward(RewardUnlineOrderDTO rewardUnlineOrderDTO){
		String message = rewardUnlineOrderDTO.validateForm();
		if (Objects.nonNull(message)){
           return ResultUtil.setResult(false,"参数错误");
		}
		try {
			return iUnLineOrerService.rewardUnlineOrder(rewardUnlineOrderDTO);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return ResultUtil.setResult(false,"天天返/刮红包异常");
		}
	}

	/**
	 * 刮红包确认
	 * @param rewardUnlineOrderDTO
	 * @return
	 */
	@RequestMapping(value = "confirmReward",method = RequestMethod.POST)
	public Result confirm(RewardUnlineOrderDTO rewardUnlineOrderDTO){
		String message = rewardUnlineOrderDTO.validateForm();
		if (Objects.nonNull(message)){
			return ResultUtil.setResult(false,"参数错误");
		}
		if (Objects.isNull(rewardUnlineOrderDTO.getMoney())){
			return ResultUtil.setResult(false,"参数错误");
		}
		try {
			return iUnLineOrerService.confirmRewardUnlineOrder(rewardUnlineOrderDTO);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return ResultUtil.setResult(false,"天天返/刮红包异常");
		}
	}
}
