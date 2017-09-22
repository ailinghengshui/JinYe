package com.hzjytech.operation.utils;

import android.util.Log;

import org.joda.time.DateTime;
import org.joda.time.DateTimeFieldType;
import org.joda.time.Days;
import org.joda.time.format.DateTimeFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;


public class TimeUtil {
    public static final String DATE_FORMAT_LONG = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FORMAT_LONG2 = "yyyy-MM-dd HH:mm";
    public static final String DATE_FORMAT_LONG3 = "yyyy/MM/dd   HH:mm:ss";
    public static final String DATE_FORMAT_LONG4 = "yyyy/MM/dd HH:mm";
    public static final String DATE_FORMAT_SHORT = "yyyy/MM/dd";
    public static final String DATE_FORMAT_SHORT2 = "yyyy-MM-dd";
    public static final String DATE_FORMAT_SHORT3 = "MM-dd HH:mm";
    public static final String DATE_FORMAT_SHORT4 = "MM/dd";
    public static final String DATE_FORMAT_SHORT5 = "MM.dd HH:mm";
    public static final String DATE_FORMAT_SHORT6 = "HH:mm";

    public static synchronized long getCurrentTime() {
        long timeStamp = System.currentTimeMillis() + new Random().nextInt(30);
        //        LogUtil.d("TimeUtil", String.valueOf(timeStamp));
        return timeStamp;
    }

    public static synchronized String getCurrentTimeString() {
        String timeStamp = String.valueOf(System.currentTimeMillis()) + "." + new Random().nextInt(
                300);
        Log.d("TimeUtils", timeStamp);
        return timeStamp;


    }

    public static String getLongTime(long time) {
        Date date = new Date(time);
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_LONG);
        String format = dateFormat.format(date);
        return format;
    }

    /**
     * 获取当天零点的时间戳
     *
     * @return
     */
    public static String getTimesmorning() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return getLongTime(cal.getTimeInMillis());
    }

    /**
     * 获取当前时间戳
     *
     * @return
     */
    public static String getTimeCurrent() {
        String nowTime = getLongTime(System.currentTimeMillis());
        return nowTime;


    }

    public static String getCorrectTimeString(String wrongTime) {
        if (wrongTime == "") {
            return "";
        }
        SimpleDateFormat OldDateFormat = new SimpleDateFormat(DATE_FORMAT_LONG);
        Date date = null;
        try {
            date = OldDateFormat.parse(wrongTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String dateString = new SimpleDateFormat(DATE_FORMAT_LONG).format(date);
        return dateString;
    }

    public static String getLong2TimeFromLong(String wrongTime) {
        if (wrongTime == null || wrongTime.equals("")) {
            return "";
        }
        SimpleDateFormat OldDateFormat = new SimpleDateFormat(DATE_FORMAT_LONG);
        Date date = null;
        try {
            date = OldDateFormat.parse(wrongTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String dateString = new SimpleDateFormat(DATE_FORMAT_LONG2).format(date);
        return dateString;
    }

    public static String getShortTimeFromLong(String oldTime) {
        SimpleDateFormat OldDateFormat = new SimpleDateFormat(DATE_FORMAT_LONG);
        SimpleDateFormat NewFormat = new SimpleDateFormat(DATE_FORMAT_SHORT);
        String date = null;
        try {
            date = NewFormat.format(OldDateFormat.parse(oldTime));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String getShort4TimeFromShort2(String oldTime) {
        SimpleDateFormat OldDateFormat = new SimpleDateFormat(DATE_FORMAT_SHORT2);
        SimpleDateFormat NewFormat = new SimpleDateFormat(DATE_FORMAT_SHORT4);
        String date = null;
        try {
            date = NewFormat.format(OldDateFormat.parse(oldTime));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String getLong3TimeFromLong(String oldTime) {
        if(oldTime==null){
            return "";
        }
        SimpleDateFormat OldDateFormat = new SimpleDateFormat(DATE_FORMAT_LONG);
        SimpleDateFormat NewFormat = new SimpleDateFormat(DATE_FORMAT_LONG3);
        String date = null;
        try {
            date = NewFormat.format(OldDateFormat.parse(oldTime));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String getLong4TimeFromLong(String oldTime) {
        if(oldTime==null){
            return "";
        }
        SimpleDateFormat OldDateFormat = new SimpleDateFormat(DATE_FORMAT_LONG);
        SimpleDateFormat NewFormat = new SimpleDateFormat(DATE_FORMAT_LONG4);
        String date = null;
        try {
            date = NewFormat.format(OldDateFormat.parse(oldTime));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String getLongTimeFromLong4(String oldTime) {

        String s = null;
        try {
            s = DateTime.parse(oldTime, DateTimeFormat.forPattern(DATE_FORMAT_LONG4))
                    .toString(DATE_FORMAT_LONG);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }

    /**
     * 2017-08-40 06:30
     *
     * @param oldTime
     * @return
     */
    public static String getLong2FromLong(String oldTime) {
        SimpleDateFormat OldDateFormat = new SimpleDateFormat(DATE_FORMAT_LONG);
        SimpleDateFormat NewFormat = new SimpleDateFormat(DATE_FORMAT_LONG2);
        String date = null;
        try {
            date = NewFormat.format(OldDateFormat.parse(oldTime));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String getShort5FromLong(String oldTime) {
        DateTime dateTime = null;
        try {
            dateTime = DateTime.parse(oldTime.replace(".0",""), DateTimeFormat.forPattern(DATE_FORMAT_LONG));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dateTime.toString(DATE_FORMAT_SHORT5);
    }


    /**
     * 根据是否是当天区别显示时间
     *
     * @param oldTime
     * @return
     */
    public static String getShort6OrShort3FromLong(String oldTime) {
        if (oldTime == null || oldTime.equals("")) {
            return "";
        }
        DateTime time = null;
        try {
            time = DateTime.parse(oldTime, DateTimeFormat.forPattern(DATE_FORMAT_LONG));
        } catch (Exception e) {
            e.printStackTrace();
        }
        DateTime now = DateTime.now();
        Days days = Days.daysBetween(time, now);
        int daysDays = days.getDays();
        if (daysDays == 0) {
            return time.toString(DATE_FORMAT_SHORT6);
        } else {
            return time.toString(DATE_FORMAT_SHORT3);
        }
    }
    /**
     * 根据是否是当天区别显示时间
     *
     * @param
     * @return
     */
    public static String getShort6OrShort3FromTime(long l) {
        String oldTime=getLongTime(l);
        DateTime time = null;
        DateTime zeroTime=null;
        try {
            time = DateTime.parse(oldTime, DateTimeFormat.forPattern(DATE_FORMAT_LONG));
            zeroTime= time.withField(DateTimeFieldType.secondOfDay(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //凌晨0点
        DateTime now = DateTime.now() .withField(DateTimeFieldType.secondOfDay(),0);
        Days days = Days.daysBetween(zeroTime, now);
        int daysDays = days.getDays();
        if (daysDays == 0) {
            return time.toString(DATE_FORMAT_SHORT6);
        } else {
            return time.toString(DATE_FORMAT_SHORT3);
        }
    }

    //获得当天的日期
    public static String lastDay() {
        return DateTime.now()
                .withField(DateTimeFieldType.secondOfDay(),0)
                .toString(DATE_FORMAT_LONG);
    }

    //获得一周前的日期
    public static String lastWeek() {
        return DateTime.now()
                .minusWeeks(1)
                .withField(DateTimeFieldType.secondOfDay(),0)
                .toString(DATE_FORMAT_LONG);

    }

    public static String getThreeDaysBefore() {
        return DateTime.now()
                .minusDays(3)
                .withField(DateTimeFieldType.secondOfDay(),0)
                .toString(DATE_FORMAT_LONG);
    }

    //获得一月前的日期
    public static String lastMonth() {
        return DateTime.now()
                .minusMonths(1)
                .withField(DateTimeFieldType.secondOfDay(),0)
                .toString(DATE_FORMAT_LONG);
    }

    //获取三月前的日期
    public static String getThreeMonthBefore() {
        return DateTime.now()
                .minusMonths(3)
                .withField(DateTimeFieldType.secondOfDay(),0)
                .toString(DATE_FORMAT_LONG);

    }

    //获取六月前的日期
    public static String getSixMonthBefore() {
        return DateTime.now()
                .minusMonths(6)
                .withField(DateTimeFieldType.secondOfDay(),0)
                .toString(DATE_FORMAT_LONG);
    }

    //获得一年前的日期
    public static String lastYear() {
        return DateTime.now()
                .minusYears(1)
                .withField(DateTimeFieldType.secondOfDay(),0)
                .toString(DATE_FORMAT_LONG);

    }

    public static long stringToLong(String strTime, String formatType) throws ParseException {
        Date date = stringToDate(strTime, formatType); // String类型转成date类型
        if (date == null) {
            return 0;
        } else {
            long currentTime = dateToLong(date); // date类型转成long类型
            return currentTime;
        }
    }

    // date类型转换为long类型
    // date要转换的date类型的时间
    public static long dateToLong(Date date) {
        return date.getTime();
    }

    // strTime的时间格式必须要与formatType的时间格式相同
    public static Date stringToDate(String strTime, String formatType) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(formatType);
        Date date = null;
        date = formatter.parse(strTime);
        return date;
    }
}
