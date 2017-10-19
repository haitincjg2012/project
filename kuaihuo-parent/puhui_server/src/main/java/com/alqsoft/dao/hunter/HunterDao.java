package com.alqsoft.dao.hunter;

import com.alqsoft.entity.hunter.Hunter;
import com.alqsoft.entity.industrytype.IndustryType;
import com.alqsoft.entity.subject.Subject;
import org.alqframework.orm.hibernate.BaseDao;
import org.alqframework.result.Result;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.LockModeType;
import java.util.List;

public interface HunterDao extends BaseDao<Hunter>{
	
	@Lock(value = LockModeType.PESSIMISTIC_WRITE)
	@Query("from Hunter as h where h.id=:id")
	public Hunter getRowLock(@Param("id")Long id);


	@Query(value = "UPDATE alq_hunter h SET h.subject_id = null WHERE h.subject_id =:sid ",nativeQuery = true)
	@Modifying
    void setSubjectNull(@Param("sid") Long id);

	@Query(value = "from Hunter where subject = ?1 and industryType=?2 ")
	List<Hunter> findHuntersBySubjectId(Subject subject, IndustryType industryType);
	
	@Query(value = "UPDATE alq_hunter h SET h.industry_type_id = ?1 WHERE h.id =?2 ",nativeQuery = true)
	@Modifying
	public  void upateIndustryClassify(Long cId,Long hId);

	@Query(value = "select * from alq_hunter where  industry_type_id=?1 and nickname like ?2 ",nativeQuery = true)
	List<Hunter> getHunterInfo(String inIds, String name);

	@Query(value = "select * from alq_hunter where  subject_id=?1",nativeQuery = true)
	List<Hunter> getAllHunterInfo(String inIds);

	Hunter getHunterBySubject(Subject subject);

	@Query(value = "UPDATE alq_hunter  SET district_id = ?1 ,district_type=3 , districts=?2 WHERE id =?3 ",nativeQuery = true)
	@Modifying
	public void updatehunterarearang(@Param("districtId") Long districtId, @Param("districts") String districts, @Param("hunterId") Long hunterId);
}
