package com.alqsoft.controller.mobile.after.hunterstore;

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
import com.alqsoft.service.hunterstore.HunterStoreService;

/**
 * 批发商个人中心-批发商店铺管理
 * @author Administrator
 *
 */
@RequestMapping("mobile/after/hunterstore")
@Controller
public class HunterStoreController {

	@Autowired
	private HunterStoreService hunterStoreService;
	/**
	 * 批发商店铺列表
	 * @param member
	 * @return
	 */
	@RequestMapping(value="hunterstorelist",method=RequestMethod.POST)
	@ResponseBody
	public Result findHunterStoreList(@MemberAnno Member member){
		if(member.getHunter()==null||member.getHunter().getId()==null){
			return ResultUtils.returnError("身份非法，您还未成为批发商");
		}
	return hunterStoreService.findHunterStoreList(member.getHunter().getId());

	}
	
	/**
	 * 店铺编辑时的回显详情
	 * @param member
	 * @param id
	 * @return
	 */
	@RequestMapping(value="hunterstoredetail",method=RequestMethod.POST)
	@ResponseBody
	public Result getHunterStoreById(@MemberAnno Member member,
			@RequestParam(value="id")Long id){
		if(member.getHunter()==null||member.getHunter().getId()==null){
			return ResultUtils.returnError("身份非法，您还未成为批发商");
		}
		return hunterStoreService.getHunterStoreById(id);
	}
	
	/**
	 * 店铺的添加和编辑接口
	 * @param member
	 * @param id  编辑时需要传id
	 * @param address   图片地址
	 * @param content	文本详情
	 * @return
	 */
	@RequestMapping(value="savehunterstore",method=RequestMethod.POST)
	@ResponseBody
	public Result saveHunterStore(@MemberAnno Member member,
			@RequestParam(value="id",defaultValue="0")Long id,
			@RequestParam(value="address")String address,
			@RequestParam(value="content")String content){
		if(member.getHunter()==null||member.getHunter().getId()==null){
			return ResultUtils.returnError("身份非法，您还未成为批发商");
		}
		return hunterStoreService.saveHunterStore(id, member.getHunter().getId(),address, content);
	}
	
	/**
	 * 根据店铺id删除逻辑删除
	 * @param member
	 * @param id
	 * @return
	 */
	@RequestMapping(value="deletehunterstore",method=RequestMethod.POST)
	@ResponseBody
	public Result deleteHunterStore(@MemberAnno Member member,@RequestParam(value="id")Long id){
		if(member.getHunter()==null||member.getHunter().getId()==null){
			return ResultUtils.returnError("身份非法，您还未成为批发商");
		}
		return hunterStoreService.deleteHunterStore(id, member.getHunter().getId());
	}
}
