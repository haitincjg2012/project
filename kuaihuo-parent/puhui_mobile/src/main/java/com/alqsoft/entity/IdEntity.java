package com.alqsoft.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;

public abstract class IdEntity {

	protected Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	
	
}
