package com.alqsoft.rpc.mobile.impl;

import org.alqframework.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alqsoft.entity.member.Member;
import com.alqsoft.rpc.mobile.RpcUpdateLogoService;
import com.alqsoft.service.updatelogo.UpdateLogoService;
@Service
@Transactional
public class RpcUpdateLogoServiceImpl implements RpcUpdateLogoService{
	
	@Autowired
	private UpdateLogoService updateLogoService;

	@Override
	public Result updateLogo(Long attachmentId, Member member) {
		// TODO Auto-generated method stub
		return updateLogoService.updateLogo( attachmentId,  member);
	}

	@Override
	public Result updatebusiness( String service, Member member) {
		// TODO Auto-generated method stub
		return updateLogoService.updatebusiness(service,member);
	}

	@Override
	public Result updatememberlogo(Long attachmentId, Member member) {
		// TODO Auto-generated method stub
		return updateLogoService.updatememberlogo(attachmentId,member);
	}

	@Override
	public Result updatemajor(String major, Member member) {
		// TODO Auto-generated method stub
		return updateLogoService.updatemajor(major,member);
	}

	@Override
	public Result updateservicedigest(String servicedigest, Member member) {
		// TODO Auto-generated method stub
		return updateLogoService.updateservicedigest(servicedigest,member);
	}

}
