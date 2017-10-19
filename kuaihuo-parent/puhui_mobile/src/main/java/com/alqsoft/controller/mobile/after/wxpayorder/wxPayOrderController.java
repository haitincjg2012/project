package com.alqsoft.controller.mobile.after.wxpayorder;

import org.alqframework.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alqsoft.service.wxpay.WxPayService;

@RestController
@RequestMapping("mobile/after/wxpayorder")
public class wxPayOrderController {

	@Autowired
	private WxPayService wxPayService;
	
	/**
	 * 统一下单
	 * @param wxOrderNum   生成的支付订单唯一标识
	 * @param money   支付金额(分)
	 * @param ip
	 * @return
	 */
		@RequestMapping(value="sendunifiedorder", method=RequestMethod.POST)
		@ResponseBody
		public Result sendUnifiedOrder( @RequestParam("wxOrderNum") String wxOrderNum,
										@RequestParam("money") Long money,
										@RequestParam("ip") String ip
				){
			return this.wxPayService.sendCodeUnifiedOrder(wxOrderNum,money,ip);
			
		}
	
}
