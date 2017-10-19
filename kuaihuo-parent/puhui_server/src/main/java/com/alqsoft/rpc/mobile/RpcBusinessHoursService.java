package com.alqsoft.rpc.mobile;

import com.alqsoft.dto.HunterTime;
import com.alqsoft.entity.hunter.Hunter;
import org.alqframework.result.Result;

public interface RpcBusinessHoursService {
    /**
     * 修改营业时间 Rpc接口
     */
    public Result updateBusinessHoursByHunterId(HunterTime hunterTime);
}
