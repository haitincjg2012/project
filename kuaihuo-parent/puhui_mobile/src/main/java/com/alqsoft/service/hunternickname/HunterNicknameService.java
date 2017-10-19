package com.alqsoft.service.hunternickname;

import org.alqframework.result.Result;

import com.alqsoft.entity.member.Member;

public interface HunterNicknameService {


	public Result updateNickname(Integer type, String nickname,Member member);

}
