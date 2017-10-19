package com.alqsoft.service.huntertime;

import com.alqsoft.dto.HunterTime;
import com.alqsoft.entity.hunter.Hunter;
import com.alqsoft.entity.member.Member;
import org.alqframework.result.Result;

/**
 *  商户的营业时间
 */
public interface BusinessHoursService {
    /**
     * 根据 Hunter 的 id 来查找 Hunter 的营业时间
     */
     public Result selectBusinessHoursByHunterId(Long id);
    /**
     * 根据 Hunter 的 id 来修改 HUnter 的营业时间
     */
     public Result  updateBusinessHoursByHunterId(HunterTime hunterTime);
}
