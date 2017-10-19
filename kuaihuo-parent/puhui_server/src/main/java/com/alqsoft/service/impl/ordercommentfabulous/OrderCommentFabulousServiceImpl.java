package com.alqsoft.service.impl.ordercommentfabulous;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alqsoft.dao.ordercommentfabulous.OrderCommentFabulousDao;
import com.alqsoft.entity.ordercommentfabulous.OrderCommentFabulous;
import com.alqsoft.service.ordercommentfabulous.OrderCommentFabulousService;

@Service
@Transactional(readOnly=true)
public class OrderCommentFabulousServiceImpl implements OrderCommentFabulousService{

	
	@Autowired
	private OrderCommentFabulousDao orderCommentFabulousDao;
	
	@Override
	@CacheEvict(key="'alq_order_comment_fabulous'+#arg0",value="alq_order_comment_fabulous")
	public boolean delete(Long arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	@Cacheable(key="'alq_order_comment_fabulous'+#arg0",value="alq_order_comment_fabulous")
	public OrderCommentFabulous get(Long arg0) {
		// TODO Auto-generated method stub
		return orderCommentFabulousDao.findOne(arg0);
	}

	@Override
	@CacheEvict(key="'alq_order_comment_fabulous'+#arg0.id",value="alq_order_comment_fabulous")
	public OrderCommentFabulous saveAndModify(OrderCommentFabulous arg0) {
		// TODO Auto-generated method stub
		return orderCommentFabulousDao.save(arg0);
	}

	@Override
	public OrderCommentFabulous getOrderCommentFabulousByCommentIdAndMemberId(Long commentid, Long memberid) {
		// TODO Auto-generated method stub
		return orderCommentFabulousDao.getOrderCommentFabulousByCommentIdAndMemberId(commentid, memberid);
	}



	
	
}
