package com.alqsoft.service.impl.home;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alqsoft.dao.home.HomeDao;
import com.alqsoft.entity.feedback.FeedBack;
import com.alqsoft.entity.home.Home;
import com.alqsoft.service.home.HomeService;

@Service
@Transactional(readOnly=true)
public class HomeServiceImpl implements HomeService{
	
	private static Logger logger=LoggerFactory.getLogger(HomeServiceImpl.class);
	
	@Autowired
	private HomeDao homeDao;
	
	@Override
	@Transactional
	public Home saveAndModify(Home arg0) {
		return homeDao.save(arg0);
	}
}
