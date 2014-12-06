package net.wecash.common.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * 日期格式化类
 *
 * @author franklin.li
 */
public class DateUtils {

	private  final SimpleDateFormat shortSdf = new SimpleDateFormat("yyyy-MM-dd");
	private  final SimpleDateFormat longSdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/**
	 * 获得本天的开始时间
	 * 
	 */
	public  Date getCurrentDayStartTime() {
		Date now = new Date();
		try {
			now = shortSdf.parse(shortSdf.format(now));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return now;
	}

	/**
	 * 获得本天的结束时间
	 * 
	 */
	public Date getCurrentDayEndTime() {
		Date now = new Date();
		try {
			now = longSdf.parse(shortSdf.format(now) + " 23:59:59");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return now;
	}

	/**
	 * 获得本月的开始时间，
	 * 
	 * @return
	 */
	public Date getCurrentMonthStartTime() {
		Calendar c = Calendar.getInstance();
		Date now = null;
		try {
			c.set(Calendar.DATE, 1);
			now = shortSdf.parse(shortSdf.format(c.getTime()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return now;
	}

	/**
	 * 当前月的结束时间，
	 * 
	 * @return
	 */
	public Date getCurrentMonthEndTime() {
		Calendar c = Calendar.getInstance();
		Date now = null;
		try {
			c.set(Calendar.DATE, 1);
			c.add(Calendar.MONTH, 1);
			c.add(Calendar.DATE, -1);
			now = longSdf.parse(shortSdf.format(c.getTime()) + " 23:59:59");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return now;
	}

	/**
	 * 当前年的开始时间，
	 * 
	 * @return
	 */
	public Date getCurrentYearStartTime() {
		Calendar c = Calendar.getInstance();
		Date now = null;
		try {
			c.set(Calendar.MONTH, 0);
			c.set(Calendar.DATE, 1);
			now = shortSdf.parse(shortSdf.format(c.getTime()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return now;
	}

	/**
	 * 当前年的结束时间，
	 * 
	 * @return
	 */
	public Date getCurrentYearEndTime() {
		Calendar c = Calendar.getInstance();
		Date now = null;
		try {
			c.set(Calendar.MONTH, 11);
			c.set(Calendar.DATE, 31);
			now = longSdf.parse(shortSdf.format(c.getTime()) + " 23:59:59");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return now;
	}
	public static long getUTC() {
		Calendar calendar = Calendar.getInstance();
		TimeZone tz = TimeZone.getTimeZone("GMT");
		calendar.setTimeZone(tz);
		return calendar.getTimeInMillis();
	}

	public static String dateFormat(long time, String ID) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		TimeZone zone = TimeZone.getTimeZone(ID);
		format.setTimeZone(zone);
		return format.format(time);
	}

	public static String dateFormat(long time) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		TimeZone zone = TimeZone.getDefault();
		format.setTimeZone(zone);
		return format.format(time);
	}

	public static String dateFormat(long time, String ID, String format) {
		DateFormat df = new SimpleDateFormat(format);
		TimeZone zone = TimeZone.getTimeZone(ID);
		df.setTimeZone(zone);
		return df.format(time);
	}

	public static int getDateByUTC(long utc) {
		Calendar calendar = Calendar.getInstance();
		TimeZone tz = TimeZone.getTimeZone("GMT");
		calendar.setTimeZone(tz);
		return calendar.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 
	 * @param date
	 * @param num 负数减，正数加
	 * @return
	 */
	public static Date changeYear(Date date, int num){
		Calendar cal = Calendar.getInstance();
		if(date == null){
			cal.setTime(new Date());
		}else{
			cal.setTime(date);
		}
		cal.add(Calendar.YEAR,-num);
		return cal.getTime();
	}

	public static int getYear(Date date){
		Calendar c = Calendar.getInstance();
		if(date == null){
			c.setTime(new Date());
		}else{
			c.setTime(date);
		}
		return c.get(Calendar.YEAR);
	}
	public static int getDateGap(Date startDate, Date endDate){
		return getYear(endDate) - getYear(startDate);
	}
	public static String formatDate(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(date);
	}
	public static Date formateDate(String dateString) throws ParseException{
	    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
	    return sdf.parse(dateString);
	}
	public static String formatTimestamp(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(date);
	}
	public static Long getUTC(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.getTimeInMillis();
	}
	public static Date getDate(){
		TimeZone time = TimeZone.getTimeZone("GMT");
		TimeZone.setDefault(time);// 设置时区

		Calendar calendar = Calendar.getInstance();// 获取实例
		Date date = calendar.getTime();
		return date;
	}
	public static String getConstellation(Integer month,Integer day) 
	{    
		String s="魔羯座水瓶座双鱼座牡羊座金牛座双子座巨蟹座狮子座处女座天秤座天蝎座射手座魔羯座"; 
		Integer[] arr={20, 19, 21, 21, 21, 22, 23, 23, 23, 23, 22, 22};   
		Integer num = month * 3 - ( day < arr[ month - 1 ] ? 3 : 0); 
		return s.substring(num, num + 3);    
	}
	public static String getConstellation(String key) 
    { 
        Integer month=Integer.valueOf(key.substring(5,7)); 
        Integer day = Integer.valueOf(key.substring(8,10));	
        return getConstellation(month,day);  
    } 
	
}
