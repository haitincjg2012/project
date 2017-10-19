package com.ph.shopping.common.util.transform;

import com.ph.shopping.common.util.verifycode.VerifyUtil;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * 金额转换工具类
 * 
 * @author 王强
 * 
 */
public class MoneyTransUtil {
	/**
	 * double转换成int类型,乘以10000
	 * 
	 * @param dou
	 * @return
	 */
	public static int transMultiInt(Double dou) {
		if (VerifyUtil.isNotEmpty(dou)) {
			BigDecimal bdou = new BigDecimal(Double.toString(dou));
			BigDecimal lon = new BigDecimal(Long.toString(10000));
			return bdou.multiply(lon).intValue();
		} else {
			return 0;
		}
	}

	/**
	 * int转换成double类型,除以10000
	 * 
	 * @param dou
	 * @return
	 */
	public static double transDivisInt(Integer dou) {
		if (VerifyUtil.isNotEmpty(dou)) {
			BigDecimal bdou = new BigDecimal(Integer.toString(dou));
			BigDecimal lon = new BigDecimal(Long.toString(10000));
			return bdou.divide(lon).doubleValue();
		} else {
			return 0.00d;
		}
	}

	/**
	 * double转换成long类型,乘以10000
	 * 
	 * @param dou
	 * @return
	 */
	public static long transMultiDouble(Double dou) {
		if (VerifyUtil.isNotEmpty(dou)) {
			BigDecimal bdou = new BigDecimal(Double.toString(dou));
			BigDecimal lon = new BigDecimal(Long.toString(10000));
			return bdou.multiply(lon).longValue();
		} else {
			return 0l;
		}
	}
	/**
	 * long转换成double类型,除以10000
	 * 返回2位小数
	 * @param dou
	 * @return
	 */
	public static double transDivisDouble(Long dou) {
		if (VerifyUtil.isNotEmpty(dou)) {
			BigDecimal bdou = new BigDecimal(dou);
			BigDecimal lon = new BigDecimal(Long.toString(10000));
			return Double.valueOf(format(bdou.divide(lon).doubleValue()));
		} else {
			return 0.00d;
		}
	}
	/**
	 * BigDecimal转换成BigDecimal类型,乘以10000
	 * 
	 * @param bigDecimal
	 * @return
	 */
	public static BigDecimal transMulti(BigDecimal bigDecimal) {
		if (VerifyUtil.isNotEmpty(bigDecimal)) {
			return bigDecimal.multiply(new BigDecimal(10000));
		} else {
			return new BigDecimal(0);
		}
	}

	/**
	 * BigDecimal转换成BigDecimal类型,除以10000
	 * 
	 * @param bigDecimal
	 * @return
	 */
	public static BigDecimal transMulti1(BigDecimal bigDecimal) {
		if (VerifyUtil.isNotEmpty(bigDecimal)) {
			return bigDecimal.divide(new BigDecimal(10000));
		} else {
			return new BigDecimal(0);
		}
	}

	/**
	 * BigDecimal转换成BigDecimal类型,乘以10000
	 * 
	 * @param bigDecimal
	 * @return
	 */
	public static long transMultiToBig1(BigDecimal bigDecimal) {
		if (VerifyUtil.isNotEmpty(bigDecimal)) {
			return bigDecimal.multiply(new BigDecimal("10000")).longValue();
		} else {
			return 0l;
		}
	}

	/**
	 * long转换成BigDecimal类型,除以10000
	 * 
	 * @param lbig
	 * @return
	 */
	public static BigDecimal transDivis1(Long lbig) {
		if (VerifyUtil.isNotEmpty(lbig)) {
			return new BigDecimal(lbig / 10000);
		} else {
			return new BigDecimal(0);
		}
	}

	/**
	 * BigDecimal转换成BigDecimal类型,除以10000
	 * 
	 * @param bigDecimal
	 * @return
	 */
	public static BigDecimal transDivis(BigDecimal bigDecimal) {
		if (VerifyUtil.isNotEmpty(bigDecimal)) {
			return bigDecimal.divide(new BigDecimal(10000));
		} else {
			return new BigDecimal(0);
		}
	}

	/**
	 * BigDecimal转换成int类型,乘以10000
	 * 
	 * @param bigDecimal
	 * @return
	 */
	public static int transMultiToInt(BigDecimal bigDecimal) {
		if (VerifyUtil.isNotEmpty(bigDecimal)) {
			return bigDecimal.intValue() * 10000;
		} else {
			return 0;
		}
	}

	/**
	 * int转换成BigDecimal类型,除以10000
	 * 
	 * @param lbig
	 * @return
	 */
	public static BigDecimal transDivisToBigDecimal(Integer ibig) {
		if (VerifyUtil.isNotEmpty(ibig)) {
			return new BigDecimal(ibig / 10000);
		} else {
			return new BigDecimal(0.00);
		}
	}

	/**
	 * 
	 * @Title: transDoubleTOBigDecimal
	 * @Description: Double乘以10000转BigDecimal
	 * @author 王强
	 * @date 2017年5月17日 上午11:13:12
	 * @param d
	 * @return
	 */
	public static BigDecimal transDoubleToBigDecimal(Double d) {
		if (VerifyUtil.isNotEmpty(d)) {
			return new BigDecimal(d * 10000);
		} else {
			return new BigDecimal(0.00);
		}
	}

	/**
	 * 
	 * @Title: transBigdecimalToDouble
	 * @Description: BigDecimal除以10000转Double
	 * @author 王强
	 * @date 2017年5月17日 下午1:36:34
	 * @param big
	 * @return
	 */
	public static Double transBigdecimalToDouble(BigDecimal big) {
		if (VerifyUtil.isNotEmpty(big)) {
			return big.doubleValue() / 10000;
		} else {
			return 0.0d;
		}
	}

	/**
	 * 
	 * @Title: transLongToBigdecimal
	 * @Description: Long乘以10000转BigDecimal
	 * @author 王强
	 * @date 2017年6月20日 上午10:04:51
	 * @param l
	 * @return
	 */
	public static BigDecimal transLongToBigdecimal(Long l) {
		if (VerifyUtil.isNotEmpty(l)) {
			return new BigDecimal(l * 10000);
		} else {
			return new BigDecimal(0.00);
		}
	}
	/**
	 * 
	 * @Title: stringFormat
	 * @Description: BigDecimal格式化成字符串
	 * @author 王强
	 * @date 2017年6月19日 下午2:09:11
	 * @param big
	 * @return
	 */
	public static String stringFormat(BigDecimal big) {
		return format(big.doubleValue());
	}

	/**
	 * 
	 * @Title: stringFormat
	 * @Description: 返回字符串保留两位小数(四舍五入)double格式化成字符串
	 * @author 王强
	 * @date 2017年6月6日 下午4:21:15
	 * @param d
	 * @return
	 */
	public static String stringFormat(double d) {
		return format(d);
	}
	
	/**
	 * 
	* @Title: stringFormat
	* @Description: long转字符串保留00
	* @author 王强
	* @date  2017年6月20日 下午3:03:40
	* @param l
	* @return
	 */
	public static String stringFormat(long l) {
		return format(l);
	}
	
	/**
	 * 
	* @Title: stringFormat
	* @Description: int转字符串保留00
	* @author 王强
	* @date  2017年6月20日 下午3:03:40
	* @param l
	* @return
	 */
	public static String stringFormat(int i) {
		return format(i);
	}

	/**
	 * 
	* @Title: format
	* @Description: 格式化金额
	* @author 王强
	* @date  2017年6月20日 下午2:52:54
	* @param value
	* @return
	 */
	public static String format(double value) {
		DecimalFormat df = new DecimalFormat("#0.00");
		df.setMaximumFractionDigits(2);
		df.setGroupingSize(0);
		df.setRoundingMode(RoundingMode.FLOOR);
		String st = df.format(value);
		return st;
	}

	/**
	 *
	 * @Title: formativeFiveLength
	 * @Description: 格式化金额 5位小数
	 * @author 郑朋
	 * @date  2017年6月20日 下午2:52:54
	 * @param value
	 * @return
	 */
	public static String formativeFiveLength(Object value) {
		DecimalFormat df = new DecimalFormat("#0.00000");
		df.setMaximumFractionDigits(5);
		df.setGroupingSize(0);
		df.setRoundingMode(RoundingMode.FLOOR);
		String st = df.format(value);
		return st;
	}


	/**
	 *
	 * @Title: formatObj
	 * @Description: 格式化
	 * @param: @param obj
	 * @param: @return
	 * @return: String
	 * @author：李杰
	 * @throws
	 */
	public static String formatObjTwoLength(Object obj){
		DecimalFormat df = new DecimalFormat("#0.00");
		df.setMaximumFractionDigits(2);
		df.setGroupingSize(0);
		df.setRoundingMode(RoundingMode.FLOOR);
		return df.format(obj);
	}

	/**
	 *
	 * @Title: formatBigDecimalByDivisTwo
	 * @Description:BigDecimal除以10000
	 * @param: @param bd
	 * @param: @return
	 * @return: BigDecimal
	 * @author：郑朋
	 * @throws
	 */
	public static BigDecimal formatBigDecimalByDivisTwo(BigDecimal bd){
		if(null != bd){
			return new BigDecimal(formatObjTwoLength(transDivis(bd)));
		}
		return bd;
	}



	/**
	 *
	 * @Title: formatObj
	 * @Description: 格式化
	 * @param: @param obj
	 * @param: @return
	 * @return: String
	 * @author：李杰
	 * @throws
	 */
	public static String formatObj(Object obj){
		DecimalFormat df = new DecimalFormat("#0.00000");
		df.setMaximumFractionDigits(5);
		df.setGroupingSize(0);
		df.setRoundingMode(RoundingMode.FLOOR);
		return df.format(obj);
	}
	/**
	 * 
	* @Title: objectToBigdecimal
	* @Description:Object转换为BigDecimal
	* @author 王强
	* @date  2017年6月20日 下午3:14:19
	* @param o
	* @return
	 */
	public static BigDecimal objectToBigdecimal(Object o) {
		if(VerifyUtil.isNotEmpty(o)) {
			return new BigDecimal(o.toString());
		} else {
			return new BigDecimal("0.00");
		}
	}
	/**
	 * 
	 * @Title: formatBigDecimal   
	 * @Description: 格式化   BigDecimal
	 * @param: @param bd
	 * @param: @return      
	 * @return: BigDecimal
	 * @author：李杰      
	 * @throws
	 */
	public static BigDecimal formatBigDecimal(BigDecimal bd) {
		if (null != bd) {
			return new BigDecimal(formatObj(bd));
		} else {
			return new BigDecimal("0.00000");
		}
	}
	/**
	 * 
	 * @Title: formatBigDecimalByDivis   
	 * @Description:BigDecimal除以10000 
	 * @param: @param bd
	 * @param: @return      
	 * @return: BigDecimal
	 * @author：李杰      
	 * @throws
	 */
	public static BigDecimal formatBigDecimalByDivis(BigDecimal bd){
		if(null != bd){
			return new BigDecimal(formatObj(transDivis(bd)));
		}
		return bd;
	}


	/**
	 *
	 * @Title: formatBigDecimalByDivisFive
	 * @Description:BigDecimal除以10000
	 * @param: @param bd
	 * @param: @return
	 * @return: BigDecimal
	 * @author：郑朋
	 * @throws
	 */
	public static BigDecimal formatBigDecimalByDivisFive(BigDecimal bd){
		if(null != bd){
			return new BigDecimal(formativeFiveLength(transDivis(bd)));
		}
		return bd;
	}
	/**
	 * 
	 * @Title: formatBigDecimalByDivisFiveStr   
	 * @Description: 将金额除以10000并返回保留5位的字符串   
	 * @param: @param bd
	 * @param: @return      
	 * @return: String
	 * @author：李杰      
	 * @throws
	 */
	public static String formatBigDecimalByDivisFiveStr(BigDecimal bd) {
		if (null != bd) {
			return formativeFiveLength(transDivis(bd));
		} else {
			return formativeFiveLength(new BigDecimal(0));
		}
	}
	/**
	 * 
	 * @Title: formatByTwo   
	 * @Description: 格式化保留两位小数   
	 * @param: @param bd
	 * @param: @return      
	 * @return: BigDecimal
	 * @author：李杰      
	 * @throws
	 */
	public static BigDecimal formatByTwo(BigDecimal bd) {
		if (null != bd) {
			DecimalFormat df = new DecimalFormat("#0.00");
			return new BigDecimal(df.format(bd));
		} else {
			return new BigDecimal("0.00");
		}
	}
	
	public static BigDecimal formatByMultiply(long resource,double coefficient){
		DecimalFormat df = new DecimalFormat("#0.00000");
		BigDecimal b1 = new BigDecimal(Long.toString(resource));
		BigDecimal b2 = new BigDecimal(Double.toString(coefficient));
		return new BigDecimal(df.format(b1.multiply(b2)));
	}
}
