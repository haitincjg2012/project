package com.alqsoft.dao.huntertime;

import com.alqsoft.entity.hunter.Hunter;
import com.alqsoft.entity.member.Member;
import org.alqframework.orm.mybatis.MyBatisRepository;

@MyBatisRepository
public interface BusinessHoursDao {
    /**
     * 根据 Hunter 的 id 来查找 Hunter  的信息
     */
    public Hunter selectHunterByHunterId(Long hunterId);

}
