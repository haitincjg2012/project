package com.alqsoft.controller.mobile.after.ordercomment;

import org.alqframework.result.Result;
import org.alqframework.result.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alqsoft.anno.Explosionproof;
import com.alqsoft.anno.MemberAnno;
import com.alqsoft.entity.member.Member;
import com.alqsoft.service.comment.OrderCommentService;

/**
 * 订单商品评论
 * @author Administrator
 *
 */
@RequestMapping("mobile/after/ordercomment")
@Controller
public class OrderCommentController {
	
	@Autowired
	private OrderCommentService orderCommentService;
	
	/**
	 * 商品订单评论列表--游客给父级评论者评论
	 * @param masterid	  父级评论id
	 * @param visitorid	  给父级评论的会员id
	 * @param content
	 * @return
	 */
	@RequestMapping(value="ordervisitorcomment",method=RequestMethod.POST)
	@ResponseBody
	public Result ordervisitorcomment(
			@RequestParam(value="masterid") Long masterid,
			@RequestParam(value="content",defaultValue="") String content,
			@MemberAnno Member member
		){
		if(member.getHunter()==null||member.getHunter().getId()==null){
			return ResultUtils.returnError("身份非法，您还未成为批发商");
		}
		return orderCommentService.saveVisitorOrderComment(member.getHunter().getId(), masterid, member.getId(), content);
	}

	
	/**
	 *  直接评论商品
	 * @param hunterid  订单id
	 * @param memberid	评论人id
	 * @param start		评论星级
	 * @param content	评论内容
	 * @return
	 */
	@RequestMapping(value="orderdirectcomment",method=RequestMethod.POST)
	@ResponseBody
	public Result orderdirectcomment(
			@RequestParam(value="orderid") Long orderid,
			@RequestParam(value="start",defaultValue="0") Integer start,
			@RequestParam(value="content") String content,
			@MemberAnno Member member){
		
		return orderCommentService.savaDirectOrderComment(orderid,member.getId(),start,content);
	}
	
	/**
	 * 商品评价列表    登入前，与登入后的区别在于关联了这个登入用户是否对评论点赞
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
			@RequestParam(value="rows",defaultValue="10")Integer rows,
			@MemberAnno Member member){
		
		return orderCommentService.findOrderCommentList(id,type,page,rows,member.getId());
	}
	
	/**
	 *  商品评论列表--用户给商品评论列表父级评论点赞
	 * @param commentid   要点赞的评论id
	 * @param type			1点赞   2取消赞
	 * @param memberid	          点赞的用户id
	 * @return
	 */
	@Explosionproof
	@RequestMapping(value="fabulousOrderComment",method=RequestMethod.POST)
	@ResponseBody
	public Result fabulousHunterComment(
			@RequestParam(value="commentid") Long commentid,
			@RequestParam(value="type") Integer type,
			@MemberAnno Member member
			){
		return orderCommentService.saveFabulousOrderComment(commentid,member.getId(),type);
		
	}
	
	/**
	 * 查询某一个订单商品的评价详情  byid
	 * @param orderid
	 * @param member
	 * @return
	 */
	@RequestMapping(value="ordercommentdetail",method=RequestMethod.POST)
	@ResponseBody
	public Result orderCommentDetail(
			@RequestParam(value="orderid") Long orderid,
			@MemberAnno Member member){
		
		return orderCommentService.getOrderCommentDetail(orderid,member.getId());
	}

	/**
	 * 查询某一个订单商品的评价详情,通过订单号 byOrderNo
	 * @author wudi
	 * @param orderno 订单号
	 * @return
	 */
	@RequestMapping(value="ordercommentdetailbyorderno",method=RequestMethod.POST)
	@ResponseBody
	public Result getOrderCommentDetailByOrderNo(@RequestParam(value="orderno") String orderno,
											  @MemberAnno Member member){
		return orderCommentService.getOrderCommentDetailByOrderNo(orderno,member.getId());
	}
	
	/**
	 * 属于该批发商订单评价列表   与登入后的区别在于关联了这个登入用户是否对评论点赞
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
			@RequestParam(value="rows",defaultValue="10")Integer rows,
			@MemberAnno Member member){
		
		return orderCommentService.hunterOrderCommentList(id,type,page,rows,member.getId());
	}
}
