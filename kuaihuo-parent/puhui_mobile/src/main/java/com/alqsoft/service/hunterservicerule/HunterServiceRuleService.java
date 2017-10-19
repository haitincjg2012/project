package com.alqsoft.service.hunterservicerule;

import org.alqframework.result.Result;
import org.springframework.web.multipart.MultipartFile;

import com.alqsoft.entity.member.Member;

public interface HunterServiceRuleService {

	public Result delServiceruleById(Long id, Member member);

	public Result updateServicerule(Long id, String attachmentids, String content, Member member);

	public Result getHunterServiceRuleList(Long id, Member member);

	public Result mobileUploadAttachment(MultipartFile urlfile, Object[] objects, String module, String[] extendFile);
	
}