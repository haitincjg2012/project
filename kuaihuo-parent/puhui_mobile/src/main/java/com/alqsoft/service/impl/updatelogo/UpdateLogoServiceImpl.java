package com.alqsoft.service.impl.updatelogo;

import org.alqframework.result.Result;
import org.alqframework.result.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alqsoft.entity.hunter.Hunter;
import com.alqsoft.entity.member.Member;
import com.alqsoft.rpc.mobile.RpcUpdateLogoService;
import com.alqsoft.service.updatelogo.UpdateLogoService;
@Service
@Transactional(readOnly=true)
public class UpdateLogoServiceImpl implements UpdateLogoService{
	
	@Autowired
	private RpcUpdateLogoService rpcUpdateLogoService;

	@Override
	public Result updateLogo(Long attachmentId, Member member) {
		Hunter hunter = member.getHunter();
		if(hunter==null){
			return ResultUtils.returnError("没有对应的批发商会员");
		}
		return rpcUpdateLogoService.updateLogo( attachmentId,  member);
	}

	@Override
	public Result updatememberlogo(Long attachmentId,Member member) {
		// TODO Auto-generated method stub
		return rpcUpdateLogoService.updatememberlogo(attachmentId,member);
	}
	
	@Override
	public Result updatebusiness( String service, Member member) {
		
		return rpcUpdateLogoService.updatebusiness(service , member);
	}

	@Override
	public Result updatemajor( String major, Member member) {
		// TODO Auto-generated method stub
		return rpcUpdateLogoService.updatemajor(major,member);
	}

	@Override
	public Result updateservicedigest(String servicedigest, Member member) {
		// TODO Auto-generated method stub
		return rpcUpdateLogoService.updateservicedigest(servicedigest,member);
	}

	

}
