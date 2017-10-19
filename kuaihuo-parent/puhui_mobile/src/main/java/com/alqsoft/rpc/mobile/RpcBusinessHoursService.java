package com.alqsoft.rpc.mobile;

import com.alqsoft.dto.HunterTime;
import org.alqframework.result.Result;

public interface RpcBusinessHoursService {

    /**
     * 修改营业时间
     * @param
     * @return
     */
    public Result updateBusinessHoursByHunterId(HunterTime hunterTime);
}
