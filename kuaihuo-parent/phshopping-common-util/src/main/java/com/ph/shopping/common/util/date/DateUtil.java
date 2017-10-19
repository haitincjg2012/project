package com.ph.shopping.common.util.date;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 
 * @Title: DateUtil.java
 * @Description:时间处理
 * @author sgsp
 * @date 上午11:25:53
 * @version V1.0
 */
public class DateUtil {

	public static final int DAYOFMONTH = 0;// 一月中的天

	public static final int HOUROFDAY = 1; // 一天中的小时

	public static final int MINUTE = 2;// 分钟

	public static final int SECOND = 3;// 秒

	public static final long DATE_BASE = 946828800000l;// 基础时间 取 2000-01-03
	// 00:00:00 星期1

	public static final String DATE_FORMAR_STRING = "yyyy-MM-dd";// 时间格式化字符串
	public static final String DATE_MM_DD_FORMAR_STRING = "MM月dd日";// 时间格式化字符串
	public static final String DATETIME_FORMAR_STRING = "yyyy-MM-dd HH:mm";// 时间格式化字符串
	public static final String TIME_FORMAR_STRING = "HH:mm:ss";// 时间格式化字符串
	public static final String DATE_NOLINE_STRING = "yyyyMMdd";// 时间格式化字符串
	public static final String DATE_YEAL_MM_DD_STRING = "yyyy年MM月dd日HH:mm";// 时间格式化字符串
	public static final String YYYYMMDDHHMMDD = "yyyyMMddHHmmss";

	public static final SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAR_STRING);
	public static final SimpleDateFormat datetimeFormat = new SimpleDateFormat(DATETIME_FORMAR_STRING);
	public static final SimpleDateFormat timeFormat = new SimpleDateFormat(TIME_FORMAR_STRING);
	public static final SimpleDateFormat timeFormatMD = new SimpleDateFormat(DATE_MM_DD_FORMAR_STRING);
	public static final SimpleDateFormat dateNoLineFormat = new SimpleDateFormat(DATE_NOLINE_STRING);
	public static final SimpleDateFormat dateFormatYMD = new SimpleDateFormat(DATE_YEAL_MM_DD_STRING);
	public static final SimpleDateFormat dateFormatYYYYMMDDHHMMDD = new SimpleDateFormat(YYYYMMDDHHMMDD);

	/**
	 * 变量：日期格式化类型 - 格式:yyyy/MM/dd
	 */
	public static final int DEFAULT = 0;
	public static final int YM = 1;

	/**
	 * 变量：日期格式化类型 - 格式:yyyy-MM-dd
	 * 
	 */
	public static final int YMR_SLASH = 11;

	/**
	 * 变量：日期格式化类型 - 格式:yyyyMMdd
	 * 
	 */
	public static final int NO_SLASH = 2;

	/**
	 * 变量：日期格式化类型 - 格式:yyyyMM
	 * 
	 */
	public static final int YM_NO_SLASH = 3;

	/**
	 * 变量：日期格式化类型 - 格式:yyyy/MM/dd HH:mm:ss
	 * 
	 */
	public static final int DATE_TIME = 4;

	/**
	 * 变量：日期格式化类型 - 格式:yyyyMMddHHmmss
	 * 
	 */
	public static final int DATE_TIME_NO_SLASH = 5;

	/**
	 * 变量：日期格式化类型 - 格式:yyyy/MM/dd HH:mm
	 * 
	 */
	public static final int DATE_HM = 6;

	/**
	 * 变量：日期格式化类型 - 格式:HH:mm:ss
	 * 
	 */
	public static final int TIME = 7;

	/**
	 * 变量：日期格式化类型 - 格式:HH:mm
	 * 
	 */
	public static final int HM = 8;

	/**
	 * 变量：日期格式化类型 - 格式:HHmmss
	 * 
	 */
	public static final int LONG_TIME = 9;
	/**
	 * 变量：日期格式化类型 - 格式:HHmm
	 * 
	 */

	public static final int SHORT_TIME = 10;

	/**
	 * 变量：日期格式化类型 - 格式:yyyy-MM-dd HH:mm:ss
	 */
	public static final int DATE_TIME_LINE = 12;

	public static String dateToStr(Date date, int type) {
		switch (type) {
		case DEFAULT:
			return dateToStr(date);
		case YM:
			return dateToStr(date, "yyyy/MM");
		case NO_SLASH:
			return dateToStr(date, "yyyyMMdd");
		case YMR_SLASH:
			return dateToStr(date, "yyyy-MM-dd");
		case YM_NO_SLASH:
			return dateToStr(date, "yyyyMM");
		case DATE_TIME:
			return dateToStr(date, "yyyy/MM/dd HH:mm:ss");
		case DATE_TIME_NO_SLASH:
			return dateToStr(date, "yyyyMMddHHmmss");
		case DATE_HM:
			return dateToStr(date, "yyyy/MM/dd HH:mm");
		case TIME:
			return dateToStr(date, "HH:mm:ss");
		case HM:
			return dateToStr(date, "HH:mm");
		case LONG_TIME:
			return dateToStr(date, "HHmmss");
		case SHORT_TIME:
			return dateToStr(date, "HHmm");
		case DATE_TIME_LINE:
			return dateToStr(date, "yyyy-MM-dd HH:mm:ss");
		default:
			throw new IllegalArgumentException("Type undefined : " + type);
		}
	}

	/**
	 * 
	 * @Title: dateToStr
	 * @Description: 日期转换成自定义字符串格式
	 * @author 王强
	 * @date 2017年4月27日 下午2:11:49
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String dateToStr(Date date, String pattern) {
		if (date == null || date.equals(""))
			return null;
		SimpleDateFormat formatter = new SimpleDateFormat(pattern);
		return formatter.format(date);
	}

	public static String dateToStr(Date date) {
		return dateToStr(date, "yyyy/MM/dd");
	}

	public static int OneDay() {
		int seconds = 24 * 60 * 60;
		return seconds;
	}

	public static boolean getDay() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, Calendar.HOUR);
		Date d = cal.getTime();
		System.out.println(new SimpleDateFormat().format(d));
		Date date = new Date();
		if (date.after(d)) {
			return true;
		} else {
			return false;
		}
	}

	public static String getTheDate() {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.DAY_OF_WEEK_IN_MONTH, Calendar.WEEK_OF_MONTH);
		c.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
		return new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
	}

	public static long betweenDays(Date start, Date end) {
		Calendar c = Calendar.getInstance();
		c.setTime(start);
		long startMils = c.getTimeInMillis();
		c.setTime(end);
		long endMils = c.getTimeInMillis();

		long days = (endMils - startMils) / (24 * 60 * 60 * 1000);

		return days;
	}

	public static long betweenDays(String start, String end) {
		try {
			Calendar c = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			c.setTime(sdf.parse(start));
			long startMils = c.getTimeInMillis();
			c.setTime(sdf.parse(end));
			long endMils = c.getTimeInMillis();

			long days = (endMils - startMils) / (24 * 60 * 60 * 1000);
			return days;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0L;
	}

	/**
	 * yyyy年MM月dd日HH:mm
	 * 
	 * @param date
	 * @return String
	 */
	public static String formatDateYMD(Date date) {
		if (date == null) {
			return "";
		} else {
			return dateFormatYMD.format(date);
		}
	}

	/**
	 * yyyyMMdd
	 * 
	 * @param date
	 * @return String
	 */
	public static String formatNoLineDate(Date date) {
		if (date == null) {
			return "";
		} else {
			return dateNoLineFormat.format(date);
		}
	}

	public static String formatDate(Date date, String format) {
		if (date == null) {
			return "";
		} else {
			return new SimpleDateFormat(format).format(date);
		}
	}

	/**
	 * yyyy-MM-dd
	 * 
	 * @param date
	 * @return String
	 */
	public static String formatDate(Date date) {
		if (date == null) {
			return "";
		} else {
			return dateFormat.format(date);
		}
	}

	/**
	 * MM月dd日
	 * 
	 * @param date
	 * @return String
	 */
	public static String formatDateMD(Date date) {
		if (date == null) {
			return "";
		} else {
			return timeFormatMD.format(date);
		}
	}

	/**
	 * yyyy-MM-dd HH:mm:ss
	 * 
	 * @param date
	 * @return String
	 */
	public static String formatDateTime(Date date) {
		if (date == null) {
			return "";
		} else {
			return datetimeFormat.format(date);
		}
	}

	/**
	 * HH:mm:ss
	 * 
	 * @param date
	 * @return String
	 */
	public static String formatTime(Date date) {
		if (date == null) {
			return "";
		} else {
			return timeFormat.format(date);
		}
	}

	/**
	 * 时间转换
	 * 
	 * @param date
	 *            时间
	 * @param format
	 *            格式
	 * @return String
	 */
	public static String format(Date date, String format) {
		if (date == null) {
			return "";
		} else {
			SimpleDateFormat dateFormat = new SimpleDateFormat(format);
			return dateFormat.format(date);
		}

	}

	/**
	 * 字符串转换成时间类型 转换后的时间格式 yyyy-MM-dd
	 * 
	 * @param dateString
	 * @return Date
	 * @throws ParseException
	 */
	public static Date parseDate(String dateString) throws ParseException {
		return dateFormat.parse(dateString);
	}

	/**
	 * 字符串转换成时间类型 转换后的时间格式 HH:mm:ss
	 * 
	 * @param dateString
	 * @return Date
	 * @throws ParseException
	 */
	public static Date parseTime(String dateString) throws ParseException {
		return timeFormat.parse(dateString);
	}

	/**
	 * 字符串转换成时间类型 转换后的时间格式 yyyy-MM-dd HH:mm
	 * 
	 * @param dateString
	 * @return Date
	 * @throws ParseException
	 */
	public static Date parseDateTime(String dateString) throws ParseException {
		return datetimeFormat.parse(dateString);
	}

	/**
	 * 字符串转换成时间类型
	 * 
	 * @param dateString
	 *            时间
	 * @param format
	 *            格式
	 * @return Date
	 * @throws ParseException
	 */
	public static Date parse(String dateString, String format) throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		return dateFormat.parse(dateString);
	}

	/**
	 * 通过传入时间与当前时间比较，获得时间差值形成文字
	 * 
	 * @param date
	 *            传入时间
	 * @return 串
	 */
	public static String getStringForDate(Date date) {

		String result = "";
		Calendar inputdate = Calendar.getInstance();
		inputdate.setTime(date);
		Calendar now = Calendar.getInstance();

		if (now.get(Calendar.YEAR) == inputdate.get(Calendar.YEAR)
				&& now.get(Calendar.MONTH) == inputdate.get(Calendar.MONTH)) {
			int day = now.get(Calendar.DATE) - inputdate.get(Calendar.DATE);
			switch (day) {
			case 0:
				break;
			case 1:
				result = "昨天";
				break;
			default:
				result = format(inputdate.getTime(), "yyyy年MM月dd日");
				break;
			}
			result = result + format(inputdate.getTime(), "HH:mm");
		} else {
			result = format(inputdate.getTime(), "yyyy年MM月dd日HH:mm");

		}

		return result;
	}

	/**
	 * 通过传入时间与当前时间比较，获得时间差值形成文字(简易)
	 * 
	 * @param date
	 *            传入时间
	 * @return String
	 */
	public static String getSimpleStringForDate(Date date) {
		String result = "";
		Calendar inputdate = Calendar.getInstance();
		inputdate.setTime(date);
		Calendar now = Calendar.getInstance();

		int day = now.get(Calendar.DATE) - inputdate.get(Calendar.DATE);
		switch (day) {
		case 0:
			result = format(inputdate.getTime(), "HH:mm");
			break;
		default:
			result = format(inputdate.getTime(), "yyyy-MM-dd");
			break;
		}

		return result;
	}

	/**
	 * 获得与当前系统时间的相差天数
	 * 
	 * @param date
	 * @return 相差天数 如果传入时间大于当前系统时间为负数
	 */
	public static long compareDate(long date) {
		long result = 0;
		long now = System.currentTimeMillis() - DateUtil.DATE_BASE;// 系统时间 -
		// 基数时间
		long inputdate = date - DateUtil.DATE_BASE;// 最后天数时间

		long day = 1000 * 3600 * 24;
		result = now / day - inputdate / day;
		return result;

	}

	/**
	 * 获得传入两个时间的相差天数
	 * 
	 * @param date
	 * @return 相差天数
	 */
	public static int compareDate(long startdate, long enddate) {
		long starttime = startdate - DateUtil.DATE_BASE;// 系统时间 -
		long endtime = enddate - DateUtil.DATE_BASE;// 最后天数时间

		long day = 1000 * 3600 * 24;
		return (int) (endtime / day - starttime / day);
	}

	/**
	 * 获得与当前系统时间的相差周数
	 * 
	 * @param date
	 * @return 相差周数 如果传入时间大于当前系统时间为负数
	 */
	public static long compareWeek(long date) {
		long result = 0;
		long now = System.currentTimeMillis() - DateUtil.DATE_BASE;// 系统时间 -
		// 基数时间
		long inputdate = date - DateUtil.DATE_BASE;// 最后天数时间

		long week = 1000 * 3600 * 24 * 7;
		result = now / week - inputdate / week;
		return result;
	}

	/**
	 * 获得与当前系统时间的相差月数
	 * 
	 * @param date
	 * @return 相差月数 如果传入时间大于当前系统时间为负数
	 * 
	 * @date 2011-05-18
	 */
	public static long compareMonth(long date) {
		Calendar now = Calendar.getInstance();
		Calendar input = Calendar.getInstance();
		input.setTimeInMillis(date);
		int yearnow = now.get(Calendar.YEAR);
		int monthnow = now.get(Calendar.MONTH);
		int yearinput = input.get(Calendar.YEAR);
		int monthinput = input.get(Calendar.MONTH);
		return yearnow * 12 + monthnow - yearinput * 12 - monthinput;

	}

	/**
	 * 计算年龄
	 * 
	 * @param date
	 *            出生日期
	 * @return
	 */
	public static int calcAge(Date date) {
		if (date == null) {
			return 0;
		}
		Calendar now = Calendar.getInstance();
		int age = now.get(Calendar.YEAR);
		now.setTime(date);
		return age - now.get(Calendar.YEAR);
	}

	/**
	 * 根据传入时间 获得本周第一天（周一)的时间
	 * 
	 * @param date
	 * @return 返回时间 时分秒毫秒为0
	 */
	public static long getFristDayForWeek(long date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(date);
		int tempday = calendar.get(Calendar.DAY_OF_WEEK);
		switch (tempday) {
		case 1:
			// 星期天
			calendar.add(Calendar.DATE, -6);
			break;
		default:
			// 周一到周六
			calendar.add(Calendar.DATE, -(tempday - 2));
			break;
		}
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime().getTime();
	}

	/**
	 * 根据传入时间 获得本周第一天（周一)的时间
	 * 
	 * @return 返回时间 时分秒毫秒为0
	 */
	public static long getFristDayForWeek() {
		return getFristDayForWeek(System.currentTimeMillis());
	}

	/**
	 * 根据传入时间 获得本周最后一天（周日)的时间
	 * 
	 * @param date
	 * @return 返回时间 时分秒毫秒为23:59:59 999
	 */
	public static long getLastDayForWeek(long date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(date);
		int tempday = calendar.get(Calendar.DAY_OF_WEEK);
		switch (tempday) {
		case 1:
			// 星期天
			break;
		default:
			// 周一到周六
			calendar.add(Calendar.DATE, 8 - tempday);
			break;
		}
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);
		return calendar.getTime().getTime();
	}

	/**
	 * 根据传入时间 获得本周最后一天（周日)的时间
	 * 
	 * @return 返回时间 时分秒毫秒为23:59:59 999
	 */
	public static long getLastDayForWeek() {
		return getLastDayForWeek(System.currentTimeMillis());
	}

	/**
	 * 根据传入时间 获得此时间所在月的 第一天的日期
	 * 
	 * @param date
	 * @return
	 */
	public static Date getMonthFristDay(long date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(date);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
		calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMinimum(Calendar.HOUR));
		calendar.set(Calendar.MINUTE, calendar.getActualMinimum(Calendar.MINUTE));
		calendar.set(Calendar.SECOND, calendar.getActualMinimum(Calendar.SECOND));
		calendar.set(Calendar.MILLISECOND, calendar.getActualMinimum(Calendar.MILLISECOND));
		return calendar.getTime();
	}

	/**
	 * 根据传入时间 获得此时间所在月的 最后一天的日期
	 * 
	 * @param date
	 * @return
	 */
	public static Date getMonthLastDay(long date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(date);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		calendar.set(Calendar.HOUR, calendar.getActualMaximum(Calendar.HOUR));
		calendar.set(Calendar.MINUTE, calendar.getActualMaximum(Calendar.MINUTE));
		calendar.set(Calendar.SECOND, calendar.getActualMaximum(Calendar.SECOND));
		calendar.set(Calendar.MILLISECOND, calendar.getActualMaximum(Calendar.MILLISECOND));
		return calendar.getTime();
	}

	/**
	 * 根据传入时间 获得此时间所在年的 第一天的日期
	 * 
	 * @param date
	 * @return
	 */
	public static Date getYearFristDay(long date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(date);
		calendar.set(Calendar.MONTH, calendar.getActualMinimum(Calendar.MONTH));
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
		calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMinimum(Calendar.HOUR));
		calendar.set(Calendar.MINUTE, calendar.getActualMinimum(Calendar.MINUTE));
		calendar.set(Calendar.SECOND, calendar.getActualMinimum(Calendar.SECOND));
		calendar.set(Calendar.MILLISECOND, calendar.getActualMinimum(Calendar.MILLISECOND));
		return calendar.getTime();
	}

	/**
	 * 根据传入时间 获得此时间所在年的 最后一天的日期
	 * 
	 * @param date
	 * @return
	 */
	public static Date getYearLastDay(long date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(date);
		calendar.set(Calendar.MONTH, calendar.getActualMaximum(Calendar.MONTH));
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		calendar.set(Calendar.HOUR, calendar.getActualMaximum(Calendar.HOUR));
		calendar.set(Calendar.MINUTE, calendar.getActualMaximum(Calendar.MINUTE));
		calendar.set(Calendar.SECOND, calendar.getActualMaximum(Calendar.SECOND));
		calendar.set(Calendar.MILLISECOND, calendar.getActualMaximum(Calendar.MILLISECOND));
		return calendar.getTime();
	}

	/**
	 * 返回查询的开始时间
	 * 
	 * @param date
	 *            查询时间
	 * @return
	 */
	public static String getBeginTime(Date date) {
		return date == null ? "2011-12-01 00:00:00" : datetimeFormat.format(date);
	}

	/**
	 * 返回查询的结束时间
	 * 
	 * @param date
	 *            查询时间
	 * @return
	 */
	public static String getEndTime(Date date) {
		return date == null ? datetimeFormat.format(System.currentTimeMillis()) : datetimeFormat.format(date);
	}

	public static String currentTime() {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(new Date());
	}

	/**
	 * 两个时间比较，是否相差24小时
	 * 
	 * @param old
	 * @param news
	 * @return true 超过24小时 false 未超过
	 */
	public static boolean compleDate(Date old, Date news) {
		if (old != null && news != null) {
			long oldLong = old.getTime();
			long newsLong = news.getTime();
			long cs = newsLong - oldLong;
			int s = 24 * 60 * 60 * 1000;
			if (cs > s) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 获取路径
	 * 
	 * @return
	 */
	public static String endFileDir() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		return sdf.format(date);
	}

	/**
	 * 当前时间到下个月1号的小时数
	 * 
	 * @return
	 */
	public static int getSeconds() {
		Calendar cal = Calendar.getInstance();
		// 当前毫秒数
		long currMills = cal.getTimeInMillis();

		GregorianCalendar calendar = new GregorianCalendar(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, 1, 0, 0,
				0);
		long nextMills = calendar.getTimeInMillis();

		int seconds = (int) ((nextMills - currMills) / (1000));
		return seconds;
	}

	/**
	 * 
	* @Title: toTheDate
	* @Description: 计算指定时间后的日期
	* @author 王强
	* @date  2017年4月27日 下午4:23:22
	* @param type
	* @param date
	* @param amount
	* @return
	* @throws Exception
	 */
	public static Date toTheDate(int type, Date date, int amount) throws Exception {
		switch (type) {
		case SECOND:
			return toTheDate1(Calendar.SECOND, date, amount);
		case MINUTE:
			return toTheDate1(Calendar.MINUTE ,date, amount);
		case HOUROFDAY:
			return toTheDate1(Calendar.HOUR_OF_DAY ,date, amount);
		case DAYOFMONTH:
			return toTheDate1(Calendar.DAY_OF_MONTH, date, amount);
		default:
			throw new Exception("无效的类型");
		}
	}

	protected static Date toTheDate1(int field, Date date, int amount) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(field, amount);
		return cal.getTime();
	}
	/**
	 * 计算两个日期相差秒数
	 * @param lastTime
	 * @return
	 * @author Mr.Chang
	 * 
	 */
	public static int dateDifferSeconds(Date lastTime){
		long a = new Date().getTime();
		long b = lastTime.getTime();
		int c = (int)((a - b) / 1000);
		return c;
	}
	
	public static void main(String[] args) throws Exception {  
		System.out.println(getStartTime());
		System.out.println(getEndTime());
    }  
	
    public static String getStartTime() {  
    	Date date = new Date();
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    	String str = sdf.format(date);
        return str + " 00:00:00";  
    }  
  
    public static String getEndTime() {  
    	Date date = new Date();
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    	String str = sdf.format(date);
        return str + " 23:59:59";  
    }  
    /**
     * 当前日期秒钟相加或相减所得日期（+,-）操作,输入一个日期得到分钟加减后的日期。
     * 
     * @param months
     * @return
     */
    public static Date DsDay_Second(Date date, Long second) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int seconds = calendar.get(Calendar.SECOND);
        calendar.set(Calendar.SECOND, (int) (seconds + second));
        return calendar.getTime();
    }
	public static Date JDay_Second(Date date, Long second) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int seconds = calendar.get(Calendar.SECOND);
		calendar.set(Calendar.SECOND, (int) (seconds - second));
		return calendar.getTime();
	}
    /**
     * 判断两个时间的大小.
     * 
     * @param startTime
     * @param endTime
     * @return
     */
    public static boolean isCompareTime(Date startTime, Date endTime) {
        if (endTime.getTime() > startTime.getTime()) {
            return true;
        } else {
            return false;
        }
    }
    /**
     * 判断time是否在from，to之内
     *
     * @param time 指定日期
     * @param from 开始日期
     * @param to   结束日期
     * @return
     */
    public static boolean belongCalendar(Date time, Date from, Date to) {
        Calendar date = Calendar.getInstance();
        date.setTime(time);

        Calendar after = Calendar.getInstance();
        after.setTime(from);

        Calendar before = Calendar.getInstance();
        before.setTime(to);
        if(date.equals(after)){
        	return true;
        }else if (date.after(after) && date.before(before)) {
            return true;
        } else {
            return false;
        }
    }
}
