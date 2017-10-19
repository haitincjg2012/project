/**  
 * @Title:  FixedParamEnum.java   
 * @Package com.ph.shopping.facade.system.senum   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 李杰    
 * @date:   2017年6月27日 下午5:49:52   
 * @version V1.0 
 * @Copyright: 2017
 */  
package com.ph.shopping.facade.system.senum;

import java.util.List;

import com.ph.shopping.common.util.container.ContainerUtil;

/**   
 * @ClassName:  FixedParamEnum   
 * @Description:固定参数枚举   
 * @author: 李杰
 * @date:   2017年6月27日 下午5:49:52     
 * @Copyright: 2017
 */
public enum FixedParamEnum {
	YST_MANAGE_SCALE(6l,4l,"易商管理收入比例",null),
	PH_MANAGE_SCALE(5l,4l,"普惠管理收入比例",null),
	YST_SCM(22l,3l,"易商通供链收入比例",null),
	PH_SCM(21l,3l,"普惠供链收入比例",null)
	;
	/**
	 * 参数改变的ID
	 */
	private Long changeId;
	/**
	 * 参数名称
	 */
	private String paramName;
	/**
	 * 参数改变影响的固定ID
	 */
	private Long fixedId;
	/**
	 * 修改后的值
	 */
	private Double changeValue;
	
	private FixedParamEnum(Long changeId,Long fixedId,String paramName,Double changeValue){
		this.changeId = changeId;
		this.fixedId = fixedId;
		this.paramName = paramName;
		this.changeValue = changeValue;
	}

	public Long getChangeId() {
		return changeId;
	}

	public void setChangeId(Long changeId) {
		this.changeId = changeId;
	}

	public Long getFixedId() {
		return fixedId;
	}

	public void setFixedId(Long fixedId) {
		this.fixedId = fixedId;
	}

	public String getParamName() {
		return paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	public Double getChangeValue() {
		return changeValue;
	}

	public void setChangeValue(Double changeValue) {
		this.changeValue = changeValue;
	}
	/**
	 * 
	 * @Title: getChangeIds   
	 * @Description: 得到   
	 * @param: @return      
	 * @return: List<Long>
	 * @author：李杰      
	 * @throws
	 */
	public static List<Long> getChangeIds(){
		List<Long> ids = ContainerUtil.lList();
		for(FixedParamEnum fp : values()){
			ids.add(fp.getChangeId());
		}
		return ids;
	}
	
	public static FixedParamEnum getFixedParamEnumByChangeId(Long changeId){
		for(FixedParamEnum fp : values()){
			if(fp.getChangeId().equals(changeId)){
				return fp;
			}
		}
		return null;
	}
}
