package com.alqsoft.entity.home;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.alqframework.orm.hibernate.IdEntity;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@Entity
@Table(name="alq_home", indexes = {})
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler", "fieldHandler" })
@Document(collection="collection1")
public class Home extends IdEntity{
	
	private String title;//标题  满五唯一 免税学区房
	
	private String vlillageName;//小区名称   珠江帝景
	
	private Double minSquare;//平方米 117平方米 
	
	private Double maxSquare;//平方米 140平方米 
	
	private String houseType;//户型 2室2厅
	
	private String time;//年代 2004年建板楼
	
	private String feature;//特点 满5年唯一 独家 有钥匙
	
	private String areaName;// 东城
	
	private Double minPrice;//100w
	
	private Double maxPrice;//400w
	
	private String roomNum;//1室 2室

	public Double getMinPrice() {
		return minPrice;
	}

	public Double getMaxPrice() {
		return maxPrice;
	}

	public String getRoomNum() {
		return roomNum;
	}

	public Double getMinSquare() {
		return minSquare;
	}

	public void setMinSquare(Double minSquare) {
		this.minSquare = minSquare;
	}

	public Double getMaxSquare() {
		return maxSquare;
	}

	public void setMaxSquare(Double maxSquare) {
		this.maxSquare = maxSquare;
	}

	public void setMinPrice(Double minPrice) {
		this.minPrice = minPrice;
	}

	public void setMaxPrice(Double maxPrice) {
		this.maxPrice = maxPrice;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public void setRoomNum(String roomNum) {
		this.roomNum = roomNum;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getVlillageName() {
		return vlillageName;
	}

	public void setVlillageName(String vlillageName) {
		this.vlillageName = vlillageName;
	}

	public String getHouseType() {
		return houseType;
	}

	public void setHouseType(String houseType) {
		this.houseType = houseType;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getFeature() {
		return feature;
	}

	public void setFeature(String feature) {
		this.feature = feature;
	}
	
}
