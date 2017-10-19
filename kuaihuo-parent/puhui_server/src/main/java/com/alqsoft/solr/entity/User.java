package com.alqsoft.solr.entity;

import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.annotation.Id;

public class User {
	@Field
	@Id
	private String id;
	@Field
	private String address;
	@Field
	private String email;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
}
