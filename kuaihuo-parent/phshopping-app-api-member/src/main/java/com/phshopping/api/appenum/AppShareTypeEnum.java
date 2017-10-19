/**  
 * @Title:  AppShareTypeEnum.java   
 * @Package com.phshopping.api.appenum   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 李杰    
 * @date:   2017年7月27日 上午9:48:55   
 * @version V1.0 
 * @Copyright: 2017
 */  
package com.phshopping.api.appenum;

/**   
 * @ClassName:  AppShareTypeEnum   
 * @Description:二维码分享类型枚举   
 * @author: 李杰
 * @date:   2017年7月27日 上午9:48:55     
 * @Copyright: 2017
 */
public enum AppShareTypeEnum {

	MERCHANT {
		@Override
		public Byte code() {
			return (byte)1;
		}

		@Override
		public String desc() {
			return "商户";
		}

		@Override
		public boolean isMatching(Byte code) {
			return this.code().equals(code);
		}
	},MEMBER {
		@Override
		public Byte code() {
			return (byte)0;
		}

		@Override
		public String desc() {
			return "会员";
		}

		@Override
		public boolean isMatching(Byte code) {
			
			return this.code().equals(code);
		}
	};
	/**
	 * 
	 * @Title: code   
	 * @Description: 编码   
	 * @param: @return      
	 * @return: Byte
	 * @author：李杰      
	 * @throws
	 */
	public abstract Byte code();
	/**
	 * 
	 * @Title: desc   
	 * @Description: 描述   
	 * @param: @return      
	 * @return: String
	 * @author：李杰      
	 * @throws
	 */
	public abstract String desc();
	
	public abstract boolean isMatching(Byte code);
}
