package com.alqsoft.controller.mobile.after.hunternotice;

import com.alqsoft.anno.Explosionproof;
import com.alqsoft.anno.MemberAnno;
import com.alqsoft.entity.member.Member;
import com.alqsoft.service.hunternotice.HunterNoticeService;
import org.alqframework.result.Result;
import org.alqframework.result.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

@RequestMapping("mobile/after/hunternotice")
@Controller
public class HunterNoticeController {

    @Autowired
    private HunterNoticeService hunterNoticeService;

    /**
     * 批发商公告删除
     *
     * @param id
     * @param member
     * @return
     */
    @Explosionproof // 在需要防爆的方法上加上注解@Explosionproof
    @RequestMapping(value = "delete-hunternotice", method = RequestMethod.POST)
    @ResponseBody
    public Result deleteHunterNoticeById(@RequestParam("id") Long id, @MemberAnno Member member) {
        try {
            Result result = hunterNoticeService.deleteHunterNoticeById(id, member);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtils.returnError("批发商公告删除异常");
        }

    }

    /**
     * 批发商公告更改发布状态
     *
     * @param id
     * @param member
     * @return
     */
    @Explosionproof // 在需要防爆的方法上加上注解@Explosionproof
    @RequestMapping(value = "update-hunternoticestatus", method = RequestMethod.POST)
    @ResponseBody
    public Result updateHunterNoticeById(@RequestParam("id") Long id, @MemberAnno Member member) {
        try {
            Result result = hunterNoticeService.updateHunterNoticeStatusById(id, member);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtils.returnError("批发商公告发布异常");
        }
    }

    /**
     * p批发商公告添加
     *
     * @param hunterId
     * @param content
     * @param releaseTime
     * @param member
     * @return
     */
    @Explosionproof // 在需要防爆的方法上加上注解@Explosionproof
    @RequestMapping(value = "add-hunternotice", method = RequestMethod.POST)
    @ResponseBody
    public Result addHunterNotice(@RequestParam("hunterId") Long hunterId, @RequestParam("content") String content, @RequestParam("releaseTime") Date releaseTime, @MemberAnno Member member) {
        try {
            Result result = hunterNoticeService.addHunterNotice(hunterId, content, releaseTime, member);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtils.returnError("批发商公告添加异常");
        }
    }

    /**
     * 批发商公告内容修改
     *
     * @param id
     * @param content
     * @param member
     * @return
     */
    @Explosionproof // 在需要防爆的方法上加上注解@Explosionproof
    @RequestMapping(value = "update-hunternotice", method = RequestMethod.POST)
    @ResponseBody
    public Result addHunterNotice(@RequestParam("id") Long id, @RequestParam("content") String content, @MemberAnno Member member) {
        try {
            Result result = hunterNoticeService.updateHunterNoticeById(id, content, member);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtils.returnError("批发商公告修改异常");
        }
    }
}
