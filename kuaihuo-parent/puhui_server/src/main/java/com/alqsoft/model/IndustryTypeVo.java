package com.alqsoft.model;

import java.util.ArrayList;
import java.util.List;

import com.alqsoft.entity.industrytype.IndustryType;

/**
 * TODO
 */
public class IndustryTypeVo {
	
	private IndustryType firstType;   		//一级分类名称
	private List<IndustryType> secondType = new ArrayList<IndustryType>();	//二级分类名称
	
	public IndustryType getFirstType() {
		return firstType;
	}
	public void setFirstType(IndustryType firstType) {
		this.firstType = firstType;
	}
	public List<IndustryType> getSecondType() {
		return secondType;
	}
	public void setSecondType(List<IndustryType> secondType) {
		this.secondType = secondType;
	} 
	
	
	
}
