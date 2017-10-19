package com.alqsoft.controller.mobile.after.huntercomment;

import org.alqframework.result.Result;
import org.alqframework.result.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alqsoft.anno.MemberAnno;
import com.alqsoft.entity.member.Member;
import com.alqsoft.service.comment.HunterCommentService;

/**
 * app首页批发商评论
 * @author Administrator
 *
 */
@RequestMapping("mobile/after/comment")
@Controller
public class HunterCommentController {

	@Autowired
	private HunterCommentService hunterCommentService;
	
	/**
	 * app首页批发商评论列表--游客给父级评论者评论  //只有该批发商能评论
	 * @param hunterid   批发商id
	 * @param masterid	  父级评论id
	 * @param visitorid	  给父级评论的会员id
	 * @param content
	 * @return
	 */
	@RequestMapping(value="visitorhuntercomment",method=RequestMethod.POST)
	@ResponseBody
	public Result visitorhuntercomment(
			@RequestParam(value="masterid") Long masterid,
			@RequestParam(value="content",defaultValue="") String content,
			@MemberAnno Member member
		){
		if(member.getHunter()==null||member.getHunter().getId()==null){
			return ResultUtils.returnError("身份非法，您还未成为批发商");
		}
		return hunterCommentService.saveVisitorHunterComment(member.getHunter().getId(),masterid,member.getId(),content);
	}
	
	/**
	 *  app首页批发商评论列表--直接评论批发商
	 * @param hunterid  批发商id
	 * @param memberid	评论人id
	 * @param start		评论星级
	 * @param content	评论内容
	 * @return
	 */
	@RequestMapping(value="directHunterComment",method=RequestMethod.POST)
	@ResponseBody
	public Result directHunterComment(
			@RequestParam(value="hunterid") Long hunterid,
			@RequestParam(value="start",defaultValue="0") Integer start,
			@RequestParam(value="content") String content,
			@MemberAnno Member member){
		
		return hunterCommentService.savaDirectHunterComment(hunterid,member.getId(),start,content);
	}

	/**
	 *  app首页批发商评论列表--用户给批发商评论列表父级评论点赞
	 * @param commentid   要点赞的评论id
	 * @param type			1点赞   2取消赞
	 * @param memberid	          点赞的用户id
	 * @return
	 */
	@RequestMapping(value="fabulousHunterComment",method=RequestMethod.POST)
	@ResponseBody
	public Result fabulousHunterComment(
			@RequestParam(value="commentid") Long commentid,
			@RequestParam(value="type") Integer type,
			@MemberAnno Member member
			){
		return hunterCommentService.saveFabulousHunterComment(commentid,member.getId(),type);
		
	}
	
	
	/**
	 * 批发商评论列表   登入前，与登入后的区别在于关联了这个登入用户是否对评论点赞
	 * @param id	 批发商id
	 * @param type  全部，好评1，中评2  差评3    好评=5星；中评3-4星；差评0-2星
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(value="huntercomment",method=RequestMethod.POST)
	@ResponseBody
	public Result hunterCommentList(
			@RequestParam(value="id") Long id,
			@RequestParam(value="type",defaultValue="") Integer type,
			@RequestParam(value="page",defaultValue="1")Integer page,
			@RequestParam(value="rows",defaultValue="10")Integer rows,
			@MemberAnno Member member){
		
		return hunterCommentService.findHunterCommentList(id,type,page,rows,member.getId());
	}
	
	
}
