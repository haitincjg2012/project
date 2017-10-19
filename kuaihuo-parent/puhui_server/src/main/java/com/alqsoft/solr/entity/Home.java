package com.alqsoft.solr.entity;


import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.annotation.Id;

public class Home {
	@Field
	@Id
	private String id;
	@Field
	private String address;
	@Field
	private String email;
	@Field
	private String houseType;
	@Field
	private String mytitle;

	public String getMytitle() {
		return mytitle;
	}

	public void setMytitle(String mytitle) {
		this.mytitle = mytitle;
	}

	public String getHouseType() {
		return houseType;
	}

	public void setHouseType(String houseType) {
		this.houseType = houseType;
	}

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
