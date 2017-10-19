package com.alqsoft.service.impl.huntercommentfabulous;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alqsoft.dao.huntercommentfabulous.HunterCommentFabulousDao;
import com.alqsoft.entity.hunter.Hunter;
import com.alqsoft.entity.huntercommentfabulous.HunterCommentFabulous;
import com.alqsoft.service.huntercommentfabulous.HunterCommentFabulousService;

@Service
@Transactional(readOnly=true)
public class HunterCommentFabulousImpl implements HunterCommentFabulousService{
	
	@Autowired
	private HunterCommentFabulousDao hunterCommentFabulousDao;

	@CacheEvict(key="'alq_hunter_comment_fabulous'+#arg0",value="alq_hunter_comment_fabulous")
	@Override
	public boolean delete(Long arg0) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Cacheable(key="'alq_hunter_comment_fabulous'+#arg0",value="alq_hunter_comment_fabulous")
	@Override
	public HunterCommentFabulous get(Long arg0) {
		// TODO Auto-generated method stub
		return hunterCommentFabulousDao.findOne(arg0);
	}

	@CacheEvict(key="'alq_hunter_comment_fabulous'+#arg0.id",value="alq_hunter_comment_fabulous")
	@Override
	public HunterCommentFabulous saveAndModify(HunterCommentFabulous arg0) {
		// TODO Auto-generated method stub
		return hunterCommentFabulousDao.save(arg0);
	}

	/**
	 * 查询该父级评论是否被当前登录用户点赞
	 */
	@Override
	public HunterCommentFabulous getHunterCommentFabulousByCommentIdAndMemberId(Long commentid, Long memberid) {
		// TODO Auto-generated method stub
		return hunterCommentFabulousDao.getHunterCommentFabulousByCommentIdAndMemberId(commentid, memberid);
	}

}
