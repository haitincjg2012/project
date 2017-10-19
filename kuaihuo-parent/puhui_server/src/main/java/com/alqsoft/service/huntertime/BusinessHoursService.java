package com.alqsoft.service.huntertime;

import com.alqsoft.dto.HunterTime;
import com.alqsoft.entity.hunter.Hunter;
import org.alqframework.orm.BaseService;
import org.alqframework.result.Result;

import java.text.ParseException;

public interface BusinessHoursService extends BaseService<HunterTime>{

    /**
     * 修改营业时间
     * @param
     * @return
     */
    public Result updateBusinessHoursByHunterId(HunterTime hunterTime);
}
