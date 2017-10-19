package com.alqsoft.dao.hunternotice;

import com.alqsoft.entity.hunternotice.HunterNotice;
import org.alqframework.orm.hibernate.BaseDao;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;


public interface HunterNoticeDao extends BaseDao<HunterNotice> {
    /**
     * 批发商公告添加
     * @param hunterId
     * @param content
     * @param releaseTime
     * @return
     */
    @Query(value = "INSERT INTO alq_hunter_notice SET hunter_id = ?1,content = ?2,release_time = ?3",nativeQuery = true)
    @Modifying
    public int addHunterNotice(@Param("hunterId") Long hunterId,@Param("content") String content,@Param("releaseTime") Date releaseTime);

    /**
     * 批发商公告删除(逻辑删除)
     * @param id
     * @return
     */
    @Query(value = "UPDATE alq_hunter_notice SET is_deleted = 1 where id = ?1",nativeQuery = true)
    @Modifying
    public int deleteHunterNoticeById(@Param("id") Long id);

    /**
     * 批发商公告修改发布状态
     * @param id
     * @return
     */
    @Query(value = "UPDATE alq_hunter_notice SET is_release = 1 where id = ?1",nativeQuery = true)
    @Modifying
    public int updateHunterNoticeStatusById(@Param("id") Long id);

    @Query(value = "UPDATE alq_hunter_notice SET content = ?2 where id = ?1",nativeQuery = true)
    @Modifying
    public int updateHunterNoticeById(@Param("id") Long id,@Param("content") String content);
}
