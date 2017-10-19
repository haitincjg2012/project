package com.alqsoft.rpc.mobile.impl;

import com.alqsoft.dto.HunterTime;
import com.alqsoft.entity.hunter.Hunter;
import com.alqsoft.rpc.mobile.RpcBusinessHoursService;
import com.alqsoft.service.huntertime.BusinessHoursService;
import org.alqframework.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * 营业时间设置和修改
 */
@Controller
public class RpcBusinessHoursServiceImpl implements RpcBusinessHoursService{

    @Autowired
    private BusinessHoursService businessHoursService;

    @Override
    public Result updateBusinessHoursByHunterId(HunterTime hunterTime) {

          return  businessHoursService.updateBusinessHoursByHunterId(hunterTime);
    }
}