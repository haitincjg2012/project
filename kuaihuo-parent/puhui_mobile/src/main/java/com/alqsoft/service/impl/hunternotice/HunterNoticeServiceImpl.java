package com.alqsoft.service.impl.hunternotice;

import com.alqsoft.dao.hunternotice.HunterNoticeDao;
import com.alqsoft.entity.member.Member;
import com.alqsoft.rpc.mobile.RpcHunterNoticeService;
import com.alqsoft.service.hunternotice.HunterNoticeService;
import org.alqframework.result.Result;
import org.alqframework.result.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
public class HunterNoticeServiceImpl implements HunterNoticeService {

    //批发商公告增删改走rpc接口
    @Autowired
    private RpcHunterNoticeService rpcHunterNoticeService;

    //批发商公告查询走本地dao接口
    @Autowired
    private HunterNoticeDao hunterNoticeDao;

    @Override
    public Result addHunterNotice(Long hunterId, String content, Date releaseTime, Member member) {
        return rpcHunterNoticeService.addHunterNotice(hunterId,content,releaseTime,member);
    }

    @Override
    public Result deleteHunterNoticeById(Long id, Member member) {
        return rpcHunterNoticeService.deleteHunterNoticeById(id,member);
    }

    @Override
    public Result updateHunterNoticeStatusById(Long id, Member member) {
        return rpcHunterNoticeService.updateHunterNoticeStatusById(id,member);
    }

    @Override
    public Result updateHunterNoticeById(Long id, String content, Member member) {
        return rpcHunterNoticeService.updateHunterNoticeById(id,content,member);
    }

    @Override
    public Result hunterNoticeList(Member member) {
        return null;




    }



}

