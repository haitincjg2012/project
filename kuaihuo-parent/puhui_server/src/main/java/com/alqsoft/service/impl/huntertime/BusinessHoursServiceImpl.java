package com.alqsoft.service.impl.huntertime;

import com.alqsoft.dao.huntertime.BusinessHoursDao;
import com.alqsoft.dto.HunterTime;
import com.alqsoft.entity.hunter.Hunter;
import com.alqsoft.service.huntertime.BusinessHoursService;
import org.alqframework.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 营业时间的设置和修改
 */
@Service
@Transactional
public class BusinessHoursServiceImpl implements BusinessHoursService{

    @Autowired
    private BusinessHoursDao businessHoursDao;

    /**
     * 营业时间的设置和修改
     * @param
     * @return
     */
    @Override
    public Result updateBusinessHoursByHunterId(HunterTime hunterTime){
        Result result = new Result();
        if (hunterTime!=null){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

            Long id = hunterTime.getId();

            String agreeStartTime = hunterTime.getAgreeStartTime();

            String agreeEndTime = hunterTime.getAgreeEndTime();

            String sendStartTime = hunterTime.getSendStartTime();

            String sendEndTime = hunterTime.getSendEndTime();

            Long beiHuoTime = hunterTime.getBeiHuoTime();

            Long startMoney = hunterTime.getStartMoney();

            String closeStartTimey =  hunterTime.getCloseStartTime();
            Date closeStartTime = null;
            try {
                closeStartTime = sdf.parse(closeStartTimey);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            String closeEndTimey = hunterTime.getCloseEndTime();
            Date closeEndTime = null;
            try {
                closeEndTime = sdf.parse(closeEndTimey);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            businessHoursDao.updateHunterByHunterId(id,agreeStartTime, agreeEndTime,sendStartTime,
                    sendEndTime,beiHuoTime, startMoney,closeStartTime,closeEndTime);
            result.setCode(1);
            result.setMsg("修改成功");
            return result;
        }else {
            result.setCode(0);
            result.setMsg("修改失败");
            return result;
        }
    }



    @Override
    public HunterTime saveAndModify(HunterTime hunterTime) {
        return null;
    }

    @Override
    public boolean delete(Long aLong) {
        return false;
    }

    @Override
    public HunterTime get(Long aLong) {
        return null;
    }


}
