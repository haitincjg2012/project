package com.alqsoft.rpc.mobile.impl;

import com.alqsoft.entity.member.Member;
import com.alqsoft.rpc.mobile.RpcHunterNoticeService;
import com.alqsoft.service.hunternotice.HunterNoticeService;
import org.alqframework.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Date;
@Controller
public class RpcHunterNoticeServiceImpl implements RpcHunterNoticeService {

    @Autowired
    private HunterNoticeService hunterNoticeService;
    @Override
    public Result addHunterNotice(Long hunterId, String content, Date releaseTime, Member member) {
        return hunterNoticeService.addHunterNotice(hunterId,content,releaseTime,member);
    }

    @Override
    public Result deleteHunterNoticeById(Long id, Member member) {
        return hunterNoticeService.deleteHunterNoticeById(id,member);
    }

    @Override
    public Result updateHunterNoticeStatusById(Long id, Member member) {
        return hunterNoticeService.updateHunterNoticeStatusById(id,member);
    }

    @Override
    public Result updateHunterNoticeById(Long id, String content, Member member) {
        return hunterNoticeService.updateHunterNoticeById(id,content,member);
    }
}
