package com.alqsoft.controller.mobile.after.huntertime;
import com.alqsoft.anno.MemberAnno;
import com.alqsoft.dto.HunterTime;
import com.alqsoft.entity.hunter.Hunter;
import com.alqsoft.entity.member.Member;
import com.alqsoft.service.huntertime.BusinessHoursService;
import org.alqframework.result.Result;
import org.alqframework.result.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/**
 * 商户营业设置
 */
@Controller
@RequestMapping("mobile/after/huntertime")
public class BusinessHoursController {

     @Autowired
    private BusinessHoursService businessHoursService;

    /**
     * 查询营业时间并还回显
     * @param
     * @return
     */
     @ResponseBody
     @RequestMapping(value = "selectBusinessHours",method = RequestMethod.POST)
     public Result selectBusinessHoursByHunterId(@MemberAnno Member member){

         Hunter hunter = member.getHunter();
         if (hunter!=null){
             Long id = hunter.getId();
             return businessHoursService.selectBusinessHoursByHunterId(id);
         }
         return ResultUtils.returnError("查询失败",0);
     }

    /**
     * 修改营业时间设置
     * @param
     * @return
     */
     @ResponseBody
     @RequestMapping(value = "updateBusinessHours",method = RequestMethod.POST)
    public Result updateBusinessHoursByHunterId(@MemberAnno Member member,HunterTime hunterTime){
         return businessHoursService.updateBusinessHoursByHunterId(hunterTime);
    }
}

