package com.frame.base.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	
	/**
	 * 判断字符串是不是指定时间格式字符串
	 * @param str
	 * @param format
	 * @return
	 */
	public static boolean isDate(String str, String format) {
		try {
			SimpleDateFormat df = new SimpleDateFormat(format);
			df.setLenient(false);	//设置lenient为false. 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
			df.parse(str);
			return true;
		} catch (ParseException e) {
			return false;
		}
	}
	
	/**
	 * 判断是不是格式为“yyyy-MM-dd”的日期字符串
	 * @param str
	 * @return
	 */
	public static boolean isDate(String str) {
		return isDate(str, "yyyy-MM-dd");
	}
		
	/**
	 * 格式化为日期
	 * @param str
	 * @param format
	 * @return
	 */
	public static Date toDate(String str, String format) {
		if ( StringUtil.isEmpty(str) )
			return null;
		
		SimpleDateFormat df = new SimpleDateFormat(format);
        Date date = null;
        try {
            date = df.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
            date = new Date();
        }
        return date;
    }

	/**
	 * 格式化为日期
	 * @param str
	 * @return
	 */
	public static Date toDate(String str) {
		return toDate(str, "yyyy-MM-dd");
	}
	
	/**
	 * 格式化为日期
	 * @param str
	 * @return
	 */
	public static Date toDateTime(String str) {
		return toDate(str, "yyyy-MM-dd HH:mm:ss");
	}
	
	/**
	 * 格式化为长整型数值
	 * @param date
	 * @return
	 */
	public static long toLong(Date date) {
		return date.getTime();
	}
	
	
	/**
	 * 格式化时间为字符串
	 * @param date
	 * @param format
	 * @return
	 */
	public static String formatDate(Date date, String format) {
		if ( date == null )
			return "";
		return new SimpleDateFormat(format).format(date);
	}
	
	
	/**
	 * 获取指定时间当前小时开始时间
	 * @param date
	 * @return
	 */
	public static Date getFirstTimeOfHour(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.HOUR_OF_DAY), 0, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}
	
	
	/**
	 * 获取指定时间当前小时结束时间
	 * @param date
	 * @return
	 */
	public static Date getLastTimeOfHour(Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.HOUR_OF_DAY), 0, 0);
		cal.set(Calendar.MILLISECOND, 0);
		cal.add(Calendar.HOUR_OF_DAY, 1);
		cal.add(Calendar.MILLISECOND, -1);
		return cal.getTime();
	}
	
	
	/**
	 * 获取指定时间当天开始时间
	 * @param date
	 * @return
	 */
	public static Date getFirstTimeOfDate(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}
	
	
	/**
	 * 获取指定时间当天结束时间
	 * @param date
	 * @return
	 */
	public static Date getLastTimeOfDate(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		cal.set(Calendar.MILLISECOND, 0);
		cal.add(Calendar.DAY_OF_MONTH, 1);
		cal.add(Calendar.MILLISECOND, -1);
		return cal.getTime();
	}

		
	/**
     * 获取指定时间当月开始时间
     * @param date
     * @return
     */
	public static Date getFirstTimeOfMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), 1, 0, 0, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}
	
	
	/**
	 * 获取指定时间当月结束时间
	 * @param date
	 * @return
	 */
	public static Date getLastTimeOfMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), 1, 0, 0, 0);
		cal.set(Calendar.MILLISECOND, 0);
		cal.add(Calendar.MONTH, 1);
		cal.add(Calendar.MILLISECOND, -1);
		return cal.getTime();
	}
	
	
	/**
	 * 结果 = 时间 + 1年
	 * @param date
	 * @param year
	 * @return
	 */
	public static Date addYear(Date date, int year) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.YEAR, year);
		return cal.getTime();
	}

	/**
	 * 结果 = 时间 + 1个月
	 * @param date
	 * @param month
	 * @return
	 */
	public static Date addMonth(Date date, int month) {
		Calendar rightNow = Calendar.getInstance();
		rightNow.setTime(date);
		rightNow.add(Calendar.MONTH, month);
		return rightNow.getTime();
	}
    
	/**
	 * 结果 = 时间 + 1天
	 * @param date
	 * @param day
	 * @return
	 */
	public static Date addDay(Date date, int day) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, day);	//日期加几天
		return cal.getTime();
	}
	
	/**
	 * 结果 = 时间 + 1小时
	 * @param date
	 * @param hour
	 * @return
	 */
	public static Date addHour(Date date, int hour) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.HOUR_OF_DAY, hour);
		return cal.getTime();
	}
	
	
	/**
	 * 结果 = 时间 + 1分钟
	 * @param date
	 * @param minute
	 * @return
	 */
	public static Date addMinute(Date date, int minute) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MINUTE, minute);
		return cal.getTime();
	}
	
    
	/**
	 * 两个时间之间相差的秒数
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public static long diffSec(Date startTime, Date endTime) {
		long diff = endTime.getTime() - startTime.getTime();
		long secs = diff / 1000;
		return secs;
	}
	
	/**
	 * 结果（秒） = 指定时间-当前时间 
	 * @param date
	 * @return
	 */
	public static long diffSec(Date date) {
		return diffSec(new Date(), date);
	}
	
	
	/**
	 * 两个时间之间相差的天数
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public static long diffDay(Date startTime, Date endTime){
		long diff = endTime.getTime() - startTime.getTime();
		long days = diff / (1000 * 60 * 60 * 24);
		return days;
	}
	
	
	/**
	 * 两个时间之间跨度的月数
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public static int diffMonth(Date startTime, Date endTime) throws Exception{
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");  
        String str1 = sdf.format(endTime);  
        String str2 = sdf.format(startTime);  
        Calendar bef = Calendar.getInstance();  
        Calendar aft = Calendar.getInstance();  
        bef.setTime(sdf.parse(str1));  
        aft.setTime(sdf.parse(str2));  
        int result = aft.get(Calendar.MONTH) - bef.get(Calendar.MONTH);  
        int month = (aft.get(Calendar.YEAR) - bef.get(Calendar.YEAR)) * 12;  
        
        return (Math.abs(month + result));
	}
	
	/**
	 * 两个时间相差多少天多少小时多少分多少秒
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public static String diff(Date startTime, Date endTime) {
		try {
			long diff = endTime.getTime() - startTime.getTime();
			long days = diff / (1000 * 60 * 60 * 24);
			long hours = (diff - days*(1000 * 60 * 60 * 24)) / (1000* 60 * 60);
			long minutes = (diff - days*(1000 * 60 * 60 * 24) - hours*(1000 * 60 * 60)) / (1000* 60);
			long second = (diff - days*(1000 * 60 * 60 * 24) - hours*(1000 * 60 * 60) - minutes*(1000*60)) / 1000;
			return String.format("%dd%dh%dm%ds", days, hours, minutes, second);
		} catch (Exception e) {
			return String.format("%dd%dh%dm%ds", 0l, 0l, 0l, 0l);
		}
	}
	
	
	/**
	 * 通过生日计算年龄
	 * @param birthday
	 * @return
	 */
	public static int getAgeByBirthday(Date birthday) {
		Calendar calendar = Calendar.getInstance();
		if (calendar.before(birthday)) {
			throw new IllegalArgumentException("The birthDay is before Now.It's unbelievable!");
		}
		int yearNow = calendar.get(Calendar.YEAR);
		int monthNow = calendar.get(Calendar.MONTH);
		int dayOfMonthNow = calendar.get(Calendar.DAY_OF_MONTH);
		
		calendar.setTime(birthday);
		int yearBirth = calendar.get(Calendar.YEAR);
		int monthBirth = calendar.get(Calendar.MONTH);
		int dayOfMonthBirth = calendar.get(Calendar.DAY_OF_MONTH);
		int age = yearNow - yearBirth;
		if ( monthNow <= monthBirth ) {
			if ( monthNow == monthBirth ) {
				if (dayOfMonthNow < dayOfMonthBirth)
					age--;
			} else {
				age--;
			}
		}
		return age;
	}
}
