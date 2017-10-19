package com.alqsoft.dao.huntertime;

import com.alqsoft.entity.hunter.Hunter;
import org.alqframework.orm.hibernate.BaseDao;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;

public interface BusinessHoursDao extends BaseDao<Hunter>{

    @Modifying
    @Query(value = "update alq_hunter set agree_start_time =:agreeStartTime," +
            "agree_end_time=:agreeEndTime," +
            "send_start_time =:sendStartTime," +
            "send_end_time =:sendEndTime," +
            "start_money =:startMoney," +
            "bei_huo_time =:beiHuoTime," +
            "close_start_time =:closeStartTime," +
            "close_end_time =:closeEndTime where id =:id",nativeQuery = true)
    public void updateHunterByHunterId(@Param("id") Long id,
                                       @Param("agreeStartTime") String agreeStartTime,
                                       @Param("agreeEndTime") String agreeEndTime,
                                       @Param("sendStartTime") String sendStartTime,
                                       @Param("sendEndTime") String sendEndTime,
                                       @Param("beiHuoTime") Long beiHuoTime,
                                       @Param("startMoney") Long startMoney,
                                       @Param("closeStartTime") Date closeStartTime,
                                       @Param("closeEndTime") Date closeEndTime);

}
