package com.alqsoft.solr.repository;

import java.io.Serializable;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.solr.repository.SolrRepository;
@NoRepositoryBean
public interface SolrCrudRepository<T, ID extends Serializable> extends SolrRepository<T, ID>,PagingAndSortingRepository<T, ID> {
} 