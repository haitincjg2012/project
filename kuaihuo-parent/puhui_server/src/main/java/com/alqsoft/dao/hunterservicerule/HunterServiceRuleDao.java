package com.alqsoft.dao.hunterservicerule;

import org.alqframework.orm.hibernate.BaseDao;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.alqsoft.entity.hunterrule.HunterRule;

public interface HunterServiceRuleDao extends BaseDao<HunterRule>{
	
	/**
	 * 批发商法则删除（逻辑删除）
	 * @param id
	 * @return
	 */
	@Query(value="UPDATE alq_hunter_rule SET is_deleted=1 WHERE id= ?1 ",nativeQuery=true)
	@Modifying
	int delServiceruleById(@Param("id")Long id);
	
	/**批发商法则添加
	 * @param attachmentids
	 * @param content
	 * @return
	 */
	@Query(value="INSERT INTO alq_hunter_rule set id=?1 and content=?2",nativeQuery=true)
	@Modifying
	int addHunterRule(@Param("id")String attachmentids, @Param("content")String content);
	/**
	 * 批发商法则编辑
	 * @param id
	 * @param content 
	 * @return
	 */
	@Query(value="UPDATE alq_hunter_rule SET content=?2 WHERE id= ?1",nativeQuery=true)
	@Modifying
	int updateServiceruleContent(@Param("id")Long id, @Param("content")String content);
	/**
	 * 删除多余的ID
	 * @param deleteAttachment
	 * DELETE FROM MyTable WHERE ID IN (1,2);
	 */
	@Query(value="DELETE from alq_hunter_rule_attachment WHERE id in (:ids)",nativeQuery=true)
	@Modifying
	int deleteAttachmentids(@Param("ids") String[] ids);
	
	/**保存现有上传的ID
	 * @param ids2
	 * @return
	 */
	@Query(value="update alq_hunter_rule_attachment set hunter_rule_id= :hunterRuleId where id in (:ids2) ",nativeQuery=true)
	@Modifying
	int updateAttachmentids(@Param("ids2")String[] ids2,@Param("hunterRuleId")Long hunterRuleId);

}
