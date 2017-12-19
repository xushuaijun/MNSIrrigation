package com.xsyj.irrigation.utils;


import android.annotation.SuppressLint;
import android.util.Log;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@SuppressLint("SimpleDateFormat") public class TimeUtil {
	public static String TIME = "06:00:00--08:00:00";
	public static int SPANTIME = 2;
	public static int SPANDAY = 3;

	/**
	 * @param time
	 *            设定的时�?
	 * 
	 * @return对比时间 如果选择的time在当前日期SPANTIME个小时之�? 返回�?
	 */
	public static boolean timeCompare(String time) {

		Date setdate = parseTime(time.trim());
		Date currentdate = new Date();

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(currentdate);
		calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY)
				+ SPANTIME);

		currentdate = calendar.getTime();

		if (setdate.after(currentdate)) {

			return true;
		}
		Log.e("测试1", "f");
		return false;

	}

	/**
	 * @param time
	 *            设定的时�?
	 * 
	 * @return对比时间 如果选择的time没有超过从当前时间算起的3�? 返回�?
	 */
	public static boolean timeCompare3Days(String time) {

		Date setdate = parseTime(time);
		Date currentdate = new Date();

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(setdate);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH)
				- SPANDAY);

		setdate = calendar.getTime();
		Log.e("测试2", "currentdate" + currentdate.toGMTString() + "      "
				+ setdate.toGMTString());

		if (setdate.before(currentdate)) {
			Log.e("测试2", " 如果选择的time没有超过从当前时间算起的3�? 返回�?");
			return true;

		}
		Log.e("测试2", "f");
		return false;

	}

	/**
	 * @return解析当前时间为date
	 */
	public static Date parseTime(String time) {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		Date date = null;
		try {
			date = sdf.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * @return格式化当前日期和时间为字符串
	 */
	public static String mCurrentTime() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String currenttime = df.format(new Date());
		return currenttime;
	}
	
	/**
	 * @return格式化当前日期和时间为字符串
	 */
	public static String mCurrentTimeWithoutSecond() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String currenttime = df.format(new Date());
		return currenttime;
	}


	/**
	 * @return格式化当前日期为字符�?
	 */
	public static String getCurrentTime() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd ");
		String currenttime = df.format(new Date());
		return currenttime;
	}
	
	/**
	 * 字符串转化为 HH:mm
	 */
	public static String stringtoTime(String date){
		SimpleDateFormat df = new SimpleDateFormat("HH:mm");
		String time=df.format(stringtoDateTime(date));
		return time;
	}
	
	/**
	 * 字符串转化为 HH:mm:ss
	 */
	public static String stringtoTimess(String date){
		SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
		String time=df.format(stringtoDateTime2(date));
		return time;
	}
	
	/**
	 * 字符�? 日期转换�? 日期时间
	 * @param sdate
	 * @return
	 */
	public static Date stringtoDateTime(String sdate){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date d=null;
		try {
			d = df.parse(sdate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return d;
	}

	public static String getFormatedDateTime(String dateTime) {
		SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sDateFormat.format(new Date(dateTime));
	}

	public static Date stringtoDatetTime(String sdate){

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

		Date d=null;
		try {
			d = df.parse(sdate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return d;
	}

	public static long stringtimetolongdata(String time) {
		SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
				Locale.CHINA);
		Date date;
		String times = null;
		try {
			date = sdr.parse(time);
			long l = date.getTime();
			String stf = String.valueOf(l);
			times = stf.substring(0, 10);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Long.valueOf(times);
	}

	/**
	 *
	 * @param stamp
	 * @return
	 */
	public static String stampTodateString(String stamp){

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		try {
			return  df.format(df1.parse(stamp));
		} catch (ParseException e) {

			e.printStackTrace();
			return stamp;
		}


	}
	
	
	/**
	 * 字符�? 日期转换�? 日期时间
	 * @param sdate
	 * @return
	 */
	public static Date stringtoDateTime2(String sdate){
		SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date d=null;
		try {
			d = df.parse(sdate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return d;
	}
		
	

	/**
	 * 字符�? 日期时间 转换�? 字符�? 日期
	 */
	public static String Stringdatetimetodate2(String datetime){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String d=df.format(stringtoDateTime2(datetime));
		return d;
	}
	
	
	/**
	 * 字符�? 日期时间 转换�? 字符�? 日期
	 */
	public static String Stringdatetimetodate(String datetime){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String d=df.format(stringtoDateTime(datetime));
		return d;
	}
	
	/**
	 * 比较两个字符串日期大�?
	 * 
	 * 返回1 ,表示第一个参数日期大�?
	 * 返回0 ,表示两个参数相等
	 * 返回-1,表示第二个参数日期大
	 */
	public static int compareDate(String before,String after){
		long beforedate=stringtoDateTime(before).getTime();
		long afterdate=stringtoDateTime(after).getTime();
		
		if(beforedate-afterdate>0){
			return 1;
		}else{
			if(beforedate-afterdate<0){
				return -1;
			}else{
				return 0;
			}
		}
		
	}


	/**
	 * 获得当前时间的相隔n个月的时间   n为正则月份加，为负则月份减
	 * @return
	 */
	public static String getYearTime(int n){
		Calendar ca = Calendar.getInstance();//得到一个Calendar的实例
		ca.setTime(new Date()); //设置时间为当前时间
		ca.add(Calendar.YEAR,  n); //月份加 n
		Date lastMonth = ca.getTime(); //结果
		SimpleDateFormat sf = new SimpleDateFormat("yyyy");
		String lmt=sf.format(lastMonth);
		return lmt;

	}
	
	/**
	 * 获得当前时间的相隔n个月的时间   n为正则月份加，为负则月份减
	 * @return
	 */
	public static String getMonthTime(int n){
		Calendar ca = Calendar.getInstance();//得到一个Calendar的实例 
		ca.setTime(new Date()); //设置时间为当前时间 
		ca.add(Calendar.MONTH,  n); //月份加 n
		Date lastMonth = ca.getTime(); //结果 
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm"); 
		String lmt=sf.format(lastMonth);
		return lmt;
		
	}
	
	/**
	 * 获取前或者后n天的时间字符串， n为正则月份加，为负则月份减
	 */
	public static String getdayTime(int n){
		Calendar ca = Calendar.getInstance();//得到一个Calendar的实例 
		ca.setTime(new Date()); //设置时间为当前时间 
		ca.add(Calendar.DAY_OF_MONTH, n); //日期增加或减少 
		//ca.add(Calendar.HOUR_OF_DAY, n);
		Date lastoneday = ca.getTime(); //结果 
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		String lmt=sf.format(lastoneday);
		return lmt;
	}
	
	/**
	 * data to datestring
	 */
	
	public static String datetostring(Date d){
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd"); 
		String lmt=sf.format(d);
		return lmt;
	}
	
	/**
	 * data to datetimestring
	 */
	
	public static String datetimestring(Date d){
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		String lmt=sf.format(d);
		return lmt;
	}
	
	/**
	 * 返回某年某月 这个月里最多天数
	 * @param month
	 * @param year
	 * @return
	 */
	public static int daysinmonth(int month,int year){
		// 添加大小月月份并将其转换为list,方便之后的判断
				String[] months_big = { "1", "3", "5", "7", "8", "10", "12" };
				String[] months_little = { "4", "6", "9", "11" };
				 List<String> list_big = Arrays.asList(months_big);
				 List<String> list_little = Arrays.asList(months_little);
				 
				// 判断大小月及是否闰年,用来确定"日"的数据
					if (list_big.contains(String.valueOf(month))) {
						return 31;
					} else if (list_little.contains(String.valueOf(month))) {
						return 30;
					} else {
						// 闰年
						if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0)
							return 29;
						else
							return 28;
					}
		
	}

	public static String getStrTime(String strTime){
        String resultTime = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long ltime = Long.valueOf(strTime);
        resultTime = sdf.format(new Date(ltime + 0));
        return resultTime;
    }


}
