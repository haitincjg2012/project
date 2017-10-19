package com.alqsoft.service.impl.huntertime;

import com.alqsoft.dao.huntertime.BusinessHoursDao;
import com.alqsoft.dto.HunterTime;
import com.alqsoft.entity.hunter.Hunter;
import com.alqsoft.entity.member.Member;
import com.alqsoft.rpc.mobile.RpcBusinessHoursService;
import com.alqsoft.service.huntertime.BusinessHoursService;
import org.alqframework.result.Result;
import org.alqframework.result.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 商户营业设置
 */
@Service
public class BusinessHoursServiceImpl implements BusinessHoursService{

    @Autowired
    private BusinessHoursDao businessHoursDao;

    @Autowired
    private RpcBusinessHoursService rpcBusinessHoursService;

    /**
     * 根据上传的 Hunter 的 id 来查找 Hunter 的营业时间设置
     * @param id
     * @return
     */
    @Override
    public Result selectBusinessHoursByHunterId(Long id) {
        Result result = new Result();
        if (id==null){
            result.setCode(0);
            result.setMsg("查询失败");
            return result;
        }else{
            result.setCode(1);
            result.setMsg("查询成功");
            Hunter hunter = businessHoursDao.selectHunterByHunterId(id);
            result.setContent(hunter);
            return result;
        }
    }

    /**
     * 根据上传的 Hunter 来修改 Hunter 的营业时间设置
     * @param
     */
    @Override
    public Result updateBusinessHoursByHunterId(HunterTime hunterTime) {
          if (hunterTime!=null){

              return rpcBusinessHoursService.updateBusinessHoursByHunterId(hunterTime);
          }else{
              return ResultUtils.returnError("设置失败",0);
          }

    }
}
