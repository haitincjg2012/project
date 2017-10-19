package com.alqsoft.controller.mobile.after.payorder;

import org.alqframework.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alqsoft.service.payservice.PayService;

@RestController
@RequestMapping("mobile/after/payorder")
public class PayOrderController {
	
	@Autowired
	private PayService payService;
	
	/**
	 * 
	 * @param orderids		订单id,多个以逗号拼接
	 * @param type  		0：微信，1：支付宝
	 * @param ip			手机端ip
	 * @return
	 */
	@RequestMapping(value="sendunifiedorder", method=RequestMethod.POST)
	@ResponseBody
	public Result sendUnifiedOrder( @RequestParam("orderids") String orderids,
									@RequestParam("type") Integer type,
									@RequestParam("ip") String ip
			){
		return this.payService.sendCodeUnifiedOrder(orderids,type,ip);
		
	}

}
