package com.alqsoft.controller.mobile.after.hunterall;

/**
 * Created by ywj on 2017/10/12.
 */

import com.alqsoft.anno.MemberAnno;
import com.alqsoft.entity.member.Member;
import com.alqsoft.service.hunterall.HunterAllService;
import org.alqframework.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author yangwenjie
 *
 */
@RequestMapping("mobile/after/hunterall")
@Controller
public class HunterAllAfterController {
    @Autowired
    private HunterAllService hunterAllService;

    @RequestMapping(value = "hunterall_gitude2", method = RequestMethod.POST)
    @ResponseBody
    public Result getAllHunter(@RequestParam(value = "currentPage", defaultValue = "1") Integer currentPage,
                               @RequestParam(value = "numPage", defaultValue = "10") Integer numPage,
                               @MemberAnno Member member)
    {
        Result result = new Result();
        if(member.getHunter()!=null){
            result.setCode(0);
            result.setMsg("该用户是批发商");
            return  result;
        }
        Result allHunter = hunterAllService.getAllHunterByArea(member.getPhone(),currentPage, numPage);
        return allHunter;

    }

}
