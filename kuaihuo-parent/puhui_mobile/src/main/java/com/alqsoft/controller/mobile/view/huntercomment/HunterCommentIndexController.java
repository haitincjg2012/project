package com.alqsoft.controller.mobile.view.huntercomment;

import org.alqframework.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alqsoft.service.comment.HunterCommentService;

/**
 * 批发商评论接口
 * @author Administrator
 *
 */
@RequestMapping("mobile/view/comment")
@Controller
public class HunterCommentIndexController {
	
	@Autowired
	private HunterCommentService hunterCommentService;
	
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
			@RequestParam(value="rows",defaultValue="10")Integer rows){
		
		return hunterCommentService.findHunterCommentList(id,type,page,rows,null);
	}

}
