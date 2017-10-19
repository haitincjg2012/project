package com.alqsoft.controller.pc.after;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.alqframework.easyui.EasyuiResult;
import org.alqframework.webmvc.servlet.ServletUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alqsoft.entity.industryassociation.IndustryAssociation;
import com.alqsoft.entity.user.User;
import com.alqsoft.service.IOrderService;

@RequestMapping("order")
@Controller
public class OrderFenRunController {

	@Autowired
	private IOrderService orderService;
	
	@RequestMapping(value="order-manager", method = RequestMethod.GET)
	public String orderManager(){
		return "order/order-manager";
	}
	
	
	@RequestMapping(value="order-manager-list", method = RequestMethod.POST)
	@ResponseBody
	public EasyuiResult getOrderFenByManager(@RequestParam("page") Integer page,
			@RequestParam("rows") Integer rows, HttpServletRequest request){
		// 解析查询
		Map<String, Object> map = ServletUtils.getParametersStartingWith(request, "search_");
		return this.orderService.getOrderFenByManager(page, rows, map);
	}
	
	@RequestMapping(value="order-industryassociation", method = RequestMethod.GET)
	public String orderIndustryAssociation(HttpServletRequest request){
		User user = (User)request.getSession().getAttribute("user");
		if(user == null){
			return "";
		}
		IndustryAssociation ia = this.orderService.getIndustryAssociationByPhone(user.getUserName());
		request.setAttribute("incomeAllMoney", ia.getIncomeAllMoney()==null?0:ia.getIncomeAllMoney());
		request.setAttribute("haveDepositMoney", ia.getHaveDepositMoney()==null?0:ia.getHaveDepositMoney());
		request.setAttribute("leftDepositMoney", ia.getLeftDepositMoney()==null?0:ia.getLeftDepositMoney());
		return "order/order-industryassociation";
	}
	
	@RequestMapping(value="order-industryassociation-list", method = RequestMethod.POST)
	@ResponseBody
	public EasyuiResult getOrderFenByIndustryAssociation(@RequestParam("page") Integer page,
			@RequestParam("rows") Integer rows, HttpServletRequest request){
		User user = (User)request.getSession().getAttribute("user");
		IndustryAssociation ia = this.orderService.getIndustryAssociationByPhone(user.getUserName());
		// 解析查询
		//Map<String, Object> map = ServletUtils.getParametersStartingWith(request, "search_");
		Map<String, Object> map = new HashMap<String, Object>();
		//获取登录行业协会信息
		map.put("id", ia.getId());
		return this.orderService.getOrderFenByIndustryAssociation(page, rows, map);
	}
	
}
