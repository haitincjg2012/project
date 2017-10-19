package com.alqsoft.solr.service;

import org.springframework.data.domain.Page;

import com.alqsoft.solr.entity.Home;


public interface SolrHomeService {
	/**
	 * 添加home
	 * @param h
	 * @return
	 */
	Home saveAndModify(Home h);
	/**
	 * 分页
	 * @param title
	 * @param page
	 * @param row
	 * @return
	 */
	Page<Home> findByTitle(String title, int page, int row);

}
