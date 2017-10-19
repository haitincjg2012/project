package com.alqsoft.service.hunternotice;

import com.alqsoft.entity.member.Member;
import org.alqframework.result.Result;

import java.util.Date;

public interface HunterNoticeService {

    public Result addHunterNotice(Long hunterId, String content, Date releaseTime, Member member);


    public Result deleteHunterNoticeById(Long id,Member member);


    public Result updateHunterNoticeStatusById(Long id,Member member);


    public Result updateHunterNoticeById(Long id,String content,Member member);

    public Result hunterNoticeList(Member member);

}
