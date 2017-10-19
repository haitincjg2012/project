/**  
 * @Title:  MemberShareTypeEnum.java   
 * @Package com.ph.shopping.facade.member.menum.member   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 李杰    
 * @date:   2017年7月25日 上午11:48:15   
 * @version V1.0 
 * @Copyright: 2017
 */  
package com.ph.shopping.facade.member.menum.member;

/**   
 * @ClassName:  MemberShareTypeEnum   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: 李杰
 * @date:   2017年7月25日 上午11:48:15     
 * @Copyright: 2017
 */
public enum MemberShareTypeEnum {
	
	SHARE_MEMBER((byte)0,"会员") ,SHARE_MERCHANT((byte)1,"商户");
	/**
	 * 编码
	 */
	private Byte code;
	/**
	 * 描述
	 */
	private String remark;
	
	MemberShareTypeEnum(Byte code,String remark){
		this.code = code;
		this.remark = remark;
	}

	public Byte getCode() {
		return code;
	}

	public void setCode(Byte code) {
		this.code = code;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public static boolean isExists(Byte code) {
		for (MemberShareTypeEnum type : values()) {
			if (type.getCode().equals(code)) {
				return true;
			}
		}
		return false;
	}
}
