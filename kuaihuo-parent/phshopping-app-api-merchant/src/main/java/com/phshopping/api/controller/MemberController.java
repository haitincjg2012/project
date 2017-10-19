package com.phshopping.api.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.util.StringUtil;
import com.ph.shopping.common.util.core.RespCode;
import com.ph.shopping.common.util.core.ResultUtil;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.member.entity.Member;
import com.ph.shopping.facade.member.menum.MemberResultEnum;
import com.ph.shopping.facade.member.service.IAdAttachment;
import com.ph.shopping.facade.member.service.IMemberService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 * @ClassName:  MemberController   
 * @Description:会员相关操作   
 * @author: liuy
 * @date:   2017年05月15日 下午3:20:58     
 * @Copyright: 2017
 */

@RestController
@RequestMapping("api/member")
public class MemberController extends BaseMerchantController{

	//会员接口
	@Reference(version = "1.0.0")
	private IMemberService memberService;

	/**
	 * 根据会员/推广师电话自动获取会员/推广师姓名
	 *
	 * @return
	 * @备注  商户可以由会员或者推广师推广
	 * @createTime 2017年05月15日
	 */
	@RequestMapping(value="/getPromoterNameByTel/{telPhone}", method = { RequestMethod.GET})
	public Result getPromoterNameByTel(@PathVariable String telPhone){
		Result result = memberService.getMemberInfoByMobile(telPhone);
		if(result!=null && result.getData()!=null){
			Member member = (Member) result.getData();
			if(StringUtil.isEmpty(member.getMemberName())){//会员或推广师存在，但没有名称
				return ResultUtil.getResult(MemberResultEnum.MEMBER_CERTIFICATE_NON);
			}else{
				return ResultUtil.getResult(RespCode.Code.SUCCESS,member);
			}
		}
		return ResultUtil.getResult(MemberResultEnum.PROMOTER_NON_EXISTENT);
	}


}
