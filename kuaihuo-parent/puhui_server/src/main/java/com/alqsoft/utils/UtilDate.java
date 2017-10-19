
package com.alqsoft.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/* *
 *类名：UtilDate
 *功能：自定义订单类
 *详细：工具类，可以用作获取系统日期、订单编号等
 *版本：3.3
 *日期：2012-08-17
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */
public class UtilDate {
	private static Log logger = LogFactory.getLog(UtilDate.class);
    /** 年月日时分秒毫秒(无下划线) yyyyMMddHHmmssSSS */
    public static final String dtOrder                  = "yyyyMMddHHmmssSSS";
    
    /** 年月日时分秒(无下划线) yyyyMMddHHmmss */
    public static final String dtLong                  = "yyyyMMddHHmmss";
    /** 完整时间 yyyy-MM-dd HH:mm:ss */
    public static final String simple                  = "yyyy-MM-dd HH:mm:ss";
    
    /** 年月日(无下划线) yyyyMMdd */
    public static final String dtShort                 = "yyyyMMdd";
	
    /** 年月日(无下划线) yyyyMMdd */
    public static final String dtTime                 = "HHmmss";
    
    /**
     * 返回系统当前时间(精确到毫秒),作为一个唯一的订单编号
     * @return
     *      以yyyyMMddHHmmssSSS为格式的当前系统时间
     */
	public synchronized static String getOrderNum(){
		Date date=new Date();
		DateFormat df=new SimpleDateFormat(dtOrder);
		return df.format(date);
	}
	
	/*public static void main(String[] args) {
		Date now_date = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(now_date);
		int week = c.get(Calendar.DAY_OF_WEEK);
		System.out.println(week);
		if ((week == 5)) {
			System.out.println("每周五才能转换股权");
		}
	}*/
	/**
     * 返回系统当前时间(精确到秒)
     * @return
     *      以yyyyMMddHHmmss为格式的当前系统时间
     */
	public static String getDateLong(){
		Date date=new Date();
		DateFormat df=new SimpleDateFormat(dtLong);
		return df.format(date);
	}
	
	/* public static void main(String[] args) {
		System.out.println(getOrderNum()+"|"+getThree());
	} */
	/**
	 * 获取系统当前日期(精确到毫秒)，格式：yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public  static String getDateFormatter(){
		Date date=new Date();
		DateFormat df=new SimpleDateFormat(simple);
		return df.format(date);
	}
	
	/**
	 * 获取系统当期年月日(精确到天)，格式：yyyyMMdd
	 * @return
	 */
	public static String getDate(){
		Date date=new Date();
		DateFormat df=new SimpleDateFormat(dtShort);
		return df.format(date);
	}
	
	/**
	 * 产生随机的三位数
	 * @return
	 */
	public static String getThree(){
		ThreadLocalRandom rad=ThreadLocalRandom.current();
		int num=rad.nextInt(100,999);
		return num+"";
	}
	
	public  static String getDateFormatter(Date date){
		DateFormat df=new SimpleDateFormat(simple);
		return df.format(date);
	}
	
	/**
	 * 获取当前日期星期几
	 * @param dt
	 * @return
	 */
	public static String getWeekOfDate(Date dt) {
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }
	/**
	 * 计算获取当前时间到上周时间范围
	 * @return
	 */
	public static String getNowToLastWeekDate(){
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		Calendar calendar = Calendar.getInstance();   
		calendar.setTime(new Date());
		//获取当前星期几
		String weekStr = getWeekOfDate(new Date());
		//开始日期
		String startDate = "";
		switch(weekStr)
        {
          case "星期一":
        	  calendar.set(Calendar.DAY_OF_MONTH,calendar.get(Calendar.DAY_OF_MONTH)-7);
        	  startDate = df.format(calendar.getTime());
        	  break;
          case "星期二":
        	  calendar.set(Calendar.DAY_OF_MONTH,calendar.get(Calendar.DAY_OF_MONTH)-8);
        	  startDate = df.format(calendar.getTime());
        	  break;
          case "星期三":
        	  calendar.set(Calendar.DAY_OF_MONTH,calendar.get(Calendar.DAY_OF_MONTH)-9);
        	  startDate = df.format(calendar.getTime());
        	  break;
          case "星期四":
        	  calendar.set(Calendar.DAY_OF_MONTH,calendar.get(Calendar.DAY_OF_MONTH)-10);
        	  startDate = df.format(calendar.getTime());
        	  break;
          case "星期五":
        	  calendar.set(Calendar.DAY_OF_MONTH,calendar.get(Calendar.DAY_OF_MONTH)-11);
        	  startDate = df.format(calendar.getTime());
        	  break;
          case "星期六":
        	  calendar.set(Calendar.DAY_OF_MONTH,calendar.get(Calendar.DAY_OF_MONTH)-12);
        	  startDate = df.format(calendar.getTime());
        	  break;
          case "星期日":
        	  calendar.set(Calendar.DAY_OF_MONTH,calendar.get(Calendar.DAY_OF_MONTH)-13);
        	  startDate = df.format(calendar.getTime());
        	  break;
          default:
        	  
        }
		return startDate;
	}


/**
  * 得到本周周一
  *
  * @return yyyy-MM-dd
  */
 public static String getMondayOfThisWeek() {
  Calendar c = Calendar.getInstance();
  SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
  int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
  if (day_of_week == 0)
   day_of_week = 7;
  c.add(Calendar.DATE, -day_of_week + 1);
  return df2.format(c.getTime());
 }
 

 /**
  * 得到本周周日
  *
  * @return yyyy-MM-dd
  */
 public static String getSundayOfThisWeek() {
  SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
  Calendar c = Calendar.getInstance();
  int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
  if (day_of_week == 0)
   day_of_week = 7;
  c.add(Calendar.DATE, -day_of_week + 7);
  return df2.format(c.getTime());
 }

 /**
  * 得到上周日期
  *
  * @return yyyy-MM-dd 
  */
 public static String getDayByBeforeWeek() {
	 Calendar calendar = Calendar.getInstance();
     Date date = new Date(System.currentTimeMillis());
     calendar.setTime(date);
     calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - 7); 
     date = calendar.getTime();
     return new SimpleDateFormat(simple).format(date);
 }

}
