package com.alqsoft.service.hunternotice;

import com.alqsoft.entity.hunternotice.HunterNotice;
import com.alqsoft.entity.member.Member;
import org.alqframework.orm.BaseService;
import org.alqframework.result.Result;

import java.util.Date;

/**
 * 批发商公告业务层接口
 * @author xuxin
 */
public interface HunterNoticeService extends BaseService<HunterNotice> {

    /**
     * 增加公告接口
     * @param hunterId
     * @param content
     * @param releaseTime
     * @return
     */
    public Result addHunterNotice(Long hunterId,String content,Date releaseTime,Member member);

    /**
     * 根据id删除公告接口
     * @param id
     * @return
     */
    public Result deleteHunterNoticeById(Long id,Member member);

    /**
     * 根据id更改公告发布状态接口
     * @param id
     * @return
     */
    public Result updateHunterNoticeStatusById(Long id,Member member);

    /**
     * 根据id更新公告内容接口
     * @param id
     * @param content
     * @return
     */
    public Result updateHunterNoticeById(Long id,String content,Member member);


}
