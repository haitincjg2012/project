package com.alqsoft.entity.area;

import java.io.Serializable;

import org.alqframework.orm.hibernate.IdEntity;

/**
 * 区域实体
 * 
 * @author 张靠勤
 * @e-mail 627658539@qq.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2013-11-4 上午10:37:39
 * 
 */

public class Area extends IdEntity implements Serializable{
	private static final long serialVersionUID = -141264236292173992L;
	private Integer parentId = 0;// 0为省级，1为市级，2为县级  3小区
	private Long areaId = 0L;// 父级id，0为顶级
	private String name;// 名称
	private String letter;//字母
	private Integer num;//数量，有些业务表需要维护相关单位的区域数量
	
	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public String getLetter() {
		return letter;
	}

	public void setLetter(String letter) {
		this.letter = letter;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public Long getAreaId() {
		return areaId;
	}

	public void setAreaId(Long areaId) {
		this.areaId = areaId;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
