package com.alqsoft.controller.pay.yinlian;

import javax.servlet.http.HttpServletResponse;

import org.alqframework.utils.UniqueUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alqsoft.service.unipay.UnipayService;

/**
 * 银联支付控制器
 * @author zc
 *
 */
@RequestMapping("pay")
@Controller
public class UnipayController {
	
	@Autowired
	private UnipayService unipayService;
	
	/**
	 * 跳转至支付输入金额页面
	 * @return
	 */
	@RequestMapping("yinlian")
	public String toPay(){
		return "unipay/unipay-index";
	}
	/**
	 * 返回银联封装的表单，跳转到银联支付页面；
	 * @param paynum
	 * @return
	 */
	@RequestMapping("tounipay")
	@ResponseBody
	public String toUnipay(@RequestParam(value="paynum",defaultValue="100")Long paynum,HttpServletResponse response){
		response.setHeader("Content-Type", "text/html; charset=utf-8");
		//订单号
		String orderNum = UniqueUtils.getOrder();
		return unipayService.getHtml(paynum,orderNum);
	}
}
