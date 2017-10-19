package com.alqsoft.utils;

import java.math.BigDecimal;

public class ValidateMoneyUtils {

	/**
	 * 验证金额
	 * @param money
	 * @param erroword
	 * @return
	 */
	 public static String validateMoney(String money,String erroword){
		  	erroword=erroword==null?"":erroword;
			if(money==null||"".equals(money.replaceAll("\\s*", ""))){
				return "请填写"+erroword+"金额";
			}
			BigDecimal dj=null;
			try{
				dj=new BigDecimal(money);
			}catch(Exception e){
				return erroword+"金额格式不正确";
			}
			BigDecimal zero = new BigDecimal(0);
			if(zero.compareTo(dj)==0){//等于0
				return erroword+"金额不能等于0";
			}
			if(zero.compareTo(dj)==1){//小于0
				return erroword+"金额值非法";
			}
			if(money.indexOf(".")!=-1){//有小数点，小数点后位数不能大于2
				if(money.substring(money.indexOf(".")+1, money.length()).length()>2){
					return erroword+"金额,最多两位小数";
				}
			}
		return "SUCCESS";
	}
}
