package com.ph.shopping.common.util.date;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 
* @ClassName: ThreadLocalDateUtil  
* @Description: 时间转换 
* @author lijie  
* @date 2017年3月23日  
*
 */
public class SyncDateUtil {
	/**
	 * 私有构造方法
	 */
	private SyncDateUtil() {
	}

	/**
	 * 日志log
	 */
	private static final Logger logger = LoggerFactory.getLogger(SyncDateUtil.class);
	/**
	 * 构造线程隔离DateFormat
	 */
	private static final ThreadLocal<DateFormat> threadLocal = new ThreadLocal<DateFormat>();
	/**
	 * 构造时间格式化格式（使用线程隔离）
	 */
	private static final ThreadLocal<String> patternLocal = new ThreadLocal<String>();

	/**
	 * 根据时间格式获取DateFormat
	 * 
	 * @param pattern
	 * @return
	 */
	public static DateFormat getDateFormat(String pattern) {
		if (null == pattern) {
			throw new NullPointerException("generate DateFormat pattern is null");
		}
		DateFormat df = threadLocal.get();
		if (df == null) {
			df = new SimpleDateFormat(pattern);
			threadLocal.set(df);
			patternLocal.set(pattern);
		} else {
			String parm = patternLocal.get();
 			if (!pattern.equals(parm)) {
				df = new SimpleDateFormat(pattern);
				threadLocal.set(df);
				patternLocal.set(pattern);
			}
		}
		return df;
	}
	/**
	 * string转date
	 * 
	 * @return
	 */
	public static Date strToDate(String pattern, String dateStr) {
		Date date = null;
		if (StringUtils.isBlank(dateStr) || StringUtils.isBlank(pattern))
			return date;
		try {
			date = getDateFormat(pattern).parse(dateStr);
		} catch (Exception e) {
			logger.error("String to date error", e);
		}
		return date;
	}

	/**
	 * string转date
	 * 
	 * @return
	 */
	public static Date strToDate(String dateStr) {
		Date date = null;
		if (StringUtils.isBlank(dateStr))
			return date;
		try {
			date = strToDateByYmdHms(dateStr);
		} catch (Exception e) {
			logger.error("String to date error", e);
			date = strToDateByYmd(dateStr);
		}
		return date;
	}
	/**
	 * 
	* @Title: strToDateByYmdHms  
	* @Description: yyyy-MM-dd HH:mm:ss
	* @param @param dateStr
	* @param @return    参数  
	* @return Date    返回类型  
	* @throws
	 */
	public static Date strToDateByYmdHms(String dateStr) {
		Date date = null;
		if (StringUtils.isBlank(dateStr))
			return date;
		try {
			date = getDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateStr);
		} catch (Exception e) {
			logger.error("String to date error yyyy-MM-dd HH:mm:ss ", e);
		}
		return date;
	}
	/**
	 * 
	* @Title: strToDateByYmd  
	* @Description: yyyy-MM-dd  
	* @param @param dateStr
	* @param @return    参数  
	* @return Date    返回类型  
	* @throws
	 */
	public static Date strToDateByYmd(String dateStr) {
		Date date = null;
		if (StringUtils.isBlank(dateStr))
			return date;
		try {
			date = getDateFormat("yyyy-MM-dd").parse(dateStr);
		} catch (ParseException e) {
			logger.error("String to date error yyyy-MM-dd ", e);
		}
		return date;
	}
	/**
	 * date转string
	 * 
	 * @param sdf
	 * @param date
	 * @return
	 */
	public static String dateToString(String pattern, Date date) {
		String str = null;
		if (null == date || StringUtils.isBlank(pattern))
			return str;
		try {
			str = getDateFormat(pattern).format(date);
		} catch (Exception e) {
			logger.error("date to string error ", e);
		}
		return str;
	}

	/**
	 * 将时间字符串格式化成制定格式
	 * 
	 * @param dateStr
	 *            pattern格式参数
	 * @return
	 */
	public static String strToFormat(String dateStr, String pattern) {

		return dateToString(pattern, strToDate(dateStr));
	}
	
}
