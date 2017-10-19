package com.alqsoft.solr.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.alqsoft.solr.entity.Home;
import com.alqsoft.solr.repository.HomeRepository;
import com.alqsoft.solr.service.SolrHomeService;

@Service
public class SolrHomeServiceImpl implements SolrHomeService{
	
	@Autowired
	HomeRepository homeRepository;
	
	@Override
	@Transactional
	public Home saveAndModify(Home h){
		return homeRepository.save(h);
	}
	@Override
	public Page<Home> findByTitle(String title,int page,int row){
		return homeRepository.findByTitleUsingAnnotatedQuery(title, new PageRequest(page,row));
	}
}	
