package com.alqsoft.controller.mobile.view.ordercomment;

import org.alqframework.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alqsoft.service.comment.OrderCommentService;

@RequestMapping("mobile/view/ordercomment")
@Controller
public class OrderCommentViewController {
	
	@Autowired
	private OrderCommentService orderCommentService;
	
	/**
	 * 商品评价列表
	 * @param id	商品id
	 * @param type  全部，好评1，中评2  差评3    好评=5星；中评3-4星；差评0-2星
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(value="productcommentlist",method=RequestMethod.POST)
	@ResponseBody
	public Result hunterCommentList(
			@RequestParam(value="id") Long id,
			@RequestParam(value="type",defaultValue="") Integer type,
			@RequestParam(value="page",defaultValue="1")Integer page,
			@RequestParam(value="rows",defaultValue="10")Integer rows){
		
		return orderCommentService.findOrderCommentList(id,type,page,rows,null);
	}
	
	/**
	 * 属于该批发商订单评价列表
	 * @param id	批发商id
	 * @param type  全部，好评1，中评2  差评3    好评=5星；中评3-4星；差评0-2星
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(value="hunterordercommentlist",method=RequestMethod.POST)
	@ResponseBody
	public Result hunterordercommentlist(
			@RequestParam(value="id") Long id,
			@RequestParam(value="type",defaultValue="") Integer type,
			@RequestParam(value="page",defaultValue="1")Integer page,
			@RequestParam(value="rows",defaultValue="10")Integer rows){
		
		return orderCommentService.hunterOrderCommentList(id,type,page,rows,null);
	}
}
