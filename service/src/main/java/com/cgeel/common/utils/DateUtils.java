package com.cgeel.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created with IntelliJ IDEA. User: ZXW Date: 14-4-15 Time: 上午10:22 To change
 * this template use File | Settings | File Templates.
 */
public class DateUtils {

	public static Date parseDate(String str, String pattern) {
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		try {
			return df.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String formatDate(Date date, String pattern) {
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		return df.format(date);
	}

	public static String formatMillisecond(Long time, String pattern) {
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		return df.format(new Date(time));
	}

    public static Long string2Timestamp(String time, String pattern){
        Date date = parseDate(time, pattern);
        return date.getTime()/1000;
    }

    public static Long timeStr2Timestamp(String time){
        return string2Timestamp(time, "yyyy-MM-dd HH:mm:ss");
    }

    public static String addDays(String date, int d){
        Calendar cal = Calendar.getInstance();
        Date dd = parseDate(date, "yyyyMMdd");
        cal.setTime(dd);
        cal.add(Calendar.DATE, d);
        return formatDate(cal.getTime(), "yyyyMMdd");
    }

    public static int subtract(String d1, String d2){
        Date dd1 = parseDate(d1, "yyyyMMdd");
        Date dd2 = parseDate(d2, "yyyyMMdd");
        int d = (int)((dd2.getTime() - dd1.getTime())/(1000*3600*24));
        return d;
    }

    /**
     * @description 日期秒数转换成日期
     * @param time
     * @return String
     */
    public static String getDatebyTimeMillis(Long time, String pattern) {
        if (time != null) {
            SimpleDateFormat sf = new SimpleDateFormat(pattern);
            String newdate = sf.format(time * 1000);
            return newdate;
        }
        return null;
    }

    public static String getToday(String pattern){
        return formatDate(new Date(), pattern);
    }

    public static String getYesterday(String pattern){
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        return formatDate(cal.getTime(), pattern);
    }

    /**
     * @description 返回当前时间的秒数
     * @return Long
     * @author don
     * @time 2014年9月1日 上午10:29:27
     */
    public static Long getTimeMillisNow() {
        return (Long) (System.currentTimeMillis() / 1000);
    }


    public static Long getTimeMillisbyDate(String date, String pattern) throws ParseException
    {
        if (org.apache.commons.lang3.StringUtils.isNotBlank(date)) {
            SimpleDateFormat sf = new SimpleDateFormat(pattern);
            try {
                Date newdate = sf.parse(date);
                return (Long) (newdate.getTime() / 1000);
            } catch (ParseException e) {
                throw e;
            }
        }
        return null;
    }


}
