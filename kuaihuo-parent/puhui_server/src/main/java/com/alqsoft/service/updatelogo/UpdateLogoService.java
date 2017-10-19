package com.alqsoft.service.updatelogo;

import org.alqframework.result.Result;

import com.alqsoft.entity.member.Member;

public interface UpdateLogoService {

	public Result updateLogo(Long attachmentId, Member member);

	public Result updatebusiness(String service, Member member);

	public Result updatememberlogo(Long attachmentId, Member member);

	public Result updatemajor( String major, Member member);

	public Result updateservicedigest(String servicedigest, Member member);

}
