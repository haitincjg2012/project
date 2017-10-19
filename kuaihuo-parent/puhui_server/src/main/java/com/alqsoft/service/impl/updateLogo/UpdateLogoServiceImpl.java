package com.alqsoft.service.impl.updateLogo;

import org.alqframework.result.Result;
import org.alqframework.result.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.alqsoft.entity.attachment.Attachment;
import com.alqsoft.entity.hunter.Hunter;
import com.alqsoft.entity.member.Member;
import com.alqsoft.service.hunter.HunterService;
import com.alqsoft.service.member.MemberService;
import com.alqsoft.service.updatelogo.UpdateLogoService;
@Service
@Transactional
public class UpdateLogoServiceImpl implements UpdateLogoService{
	
	@Autowired
	private HunterService hunterService;
	
	@Autowired
	private MemberService memberService;
	

	@Override
	public Result updateLogo(Long attachmentId, Member member) {
		Result result = new Result();
		//1、获取登录批发商信息
		Hunter hunter = member.getHunter();
		//2、判断所传的参数
		if(null==attachmentId){
			return ResultUtils.returnError("对不起,请上传您的图片");
		}
		if(null==hunter){
			return ResultUtils.returnError("对不起,您不是批发商,请先申请");
		}
		//3、查询当前所要修改的批发商logo
		Hunter hunterdb =hunterService.get(member.getHunter().getId());
		//4、将批发商登录的logo图像关系与上传头像的关系更改
		Attachment attachment = new Attachment();
		attachment.setId(attachmentId);
		hunterdb.setLogoAttachment(attachment);
		try {
			this.hunterService.saveAndModify(hunterdb);
			result.setCode(1);
			result.setMsg("批发商logo修改成功");
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//回滚数据
			result.setCode(0);
			result.setMsg("批发商logo修改失败");
		}	
		return result;
	}

	@Override
	public Result updatememberlogo(Long attachmentId, Member member) {
		Result result = new Result();
		//2、判断所传的参数
		if(null==attachmentId){
			return ResultUtils.returnError("对不起,请上传您的图片");
		}
		if(null==member){
			return ResultUtils.returnError("对不起,您不是会员,请先申请");
		}
		//3、查询当前所要修改的会员logo
		Member memberdb = memberService.get(member.getId());
		//4、将猎会员登录的logo图像关系与上传头像的关系更改
		Attachment attachment = new Attachment();
		attachment.setId(attachmentId);
		memberdb.setLogoAttachment(attachment);
		try {
			this.memberService.saveAndModify(memberdb);
			result.setCode(1);
			result.setMsg("会员logo修改成功");
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//回滚数据
			result.setCode(0);
			result.setMsg("会员logo修改失败");
		}	
		return result;
	}

	@Override
	public Result updatebusiness(String service, Member member) {
		Result result = new Result();
		Hunter hunter = member.getHunter();//获取登录批发商信息
		if(null==hunter){
			return ResultUtils.returnError("对不起,您不是批发商,请先申请");
		}
		//判断是否为空
		if(service==null || "".equals(service)) {
			return ResultUtils.returnError("批发商服务不能为空");
		}
		//判断字符长度
		if(service.length()>20){
	    	return ResultUtils.returnError("服务不能超过20个字");
		}
		try {
			//查询给批发商服务是否存在，不存在就添加，存在更新
			Hunter hunterdb = hunterService.get(member.getHunter().getId());
			//hunterRule.setContent(content.replaceAll("[\\ud800\\udc00-\\udbff\\udfff\\ud800-\\udfff]", ""));
			hunterdb.setService(service);
			this.hunterService.saveAndModify(hunterdb);
			result.setCode(1);
			result.setMsg("批发商服务修改成功");
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//回滚数据
			result.setCode(0);
			result.setMsg("批发商服务修改失败");
		}	
		return result;
	}

	@Override
	public Result updatemajor(String major, Member member) {
		Result result = new Result();
		Hunter hunter = member.getHunter();//获取登录批发商信息
		if(null==hunter){
			return ResultUtils.returnError("对不起,您不是批发商,请先申请");
		}
		//判断是否为空
		if(major==null || "".equals(major)) {
			return ResultUtils.returnError("批发商专业不能为空");
		}
		//判断字符长度
		if(major.length()>20){
	    	return ResultUtils.returnError("专业不能超过20个字");
		}
		/*if(major.length()>=2 && major.length()<=10){*/
		try {
			//查询给批发商专业是否存在，不存在就添加，存在更新
			Hunter hunterdb = hunterService.get(member.getHunter().getId());
			hunterdb.setMajor(major);
			this.hunterService.saveAndModify(hunterdb);
			result.setCode(1);
			result.setMsg("批发商专业修改成功");
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//回滚数据
			result.setCode(0);
			result.setMsg("批发商专业修改失败");
		}	
		/*}else{
			return ResultUtils.returnError("批发商专业长度范围在2-10个字");
		}*/
		return result;
	}
	
	@Override
	public Result updateservicedigest(String servicedigest, Member member) {
		Result result = new Result();
		Hunter hunter = member.getHunter();//获取登录批发商信息
		if(null==hunter){
			return ResultUtils.returnError("对不起,您不是批发商,请先申请");
		}
		String serviceContent=servicedigest.trim();
		//判断是否为空
		if(serviceContent==null || "".equals(serviceContent)) {
			return ResultUtils.returnError("批发商服务内容不能为空");
		}
		//判断字符长度
		if(servicedigest.length()>200){
	    	return ResultUtils.returnError("请输入在200个字以内");
		}
		try {
			//查询给批发商服务内容是否存在，不存在就添加，存在更新
			Hunter hunterdb = hunterService.get(member.getHunter().getId());
			hunterdb.setServiceDigest(servicedigest);
			this.hunterService.saveAndModify(hunterdb);
			result.setCode(1);
			result.setMsg("批发商服务内容修改成功");
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//回滚数据
			result.setCode(0);
			result.setMsg("批发商服务内容修改失败");
		}	
		return result;
	}

}
