package com.alqsoft.solr.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.repository.Query;

import com.alqsoft.solr.entity.Home;

public interface HomeRepository extends SolrCrudRepository<Home, String>{
	
	  @Query(value="address:*?0*")
	  Page<Home> findByTitleUsingAnnotatedQuery(String address, Pageable page);
}
