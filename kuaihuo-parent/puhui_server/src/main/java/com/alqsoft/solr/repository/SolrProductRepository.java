package com.alqsoft.solr.repository;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.repository.Query;

import com.alqsoft.solr.entity.User;


public interface SolrProductRepository extends SolrCrudRepository<User, String> {
	
	 @Query(value="address:*?0*")
	 Page<User> findByAddressUsingAnnotatedQuery(String address, Pageable page);
	 
	 @Query(value="address:?0",fields={"id","address","email"})
	 public List<User> findByAddress(String address);
	
}
