package com.alqsoft.dao.huntercomment;

import javax.persistence.LockModeType;

import org.alqframework.orm.hibernate.BaseDao;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.alqsoft.entity.huntercomment.HunterComment;

/**
 * 批发商评论
 * @author Administrator
 *
 */
public interface HunterCommentDao extends BaseDao<HunterComment>{

	/**
	 * app首页批发商评论列表--查询该会员是否已有评价过此批发商
	 * @param hunterid
	 * @param memberid
	 * @return
	 */
	@Query(value="SELECT COUNT(*) FROM alq_hunter_comment hc WHERE  hc.hunter_id=:hunterid AND hc.member_id=:memberid AND hc.parent_id IS NOT NULL",nativeQuery=true)
	public int getDirectHunterCommentCount(@Param("hunterid") Long hunterid,@Param("memberid") Long memberid);
	
	
	@Lock(value = LockModeType.PESSIMISTIC_WRITE)
	@Query("from HunterComment as h where h.id=:id")
	public HunterComment getRowLock(@Param("id")Long id);
}
