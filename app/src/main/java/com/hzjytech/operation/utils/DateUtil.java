package com.hzjytech.operation.utils;

import com.google.gson.internal.bind.util.ISO8601Utils;

import java.text.ParseException;
import java.text.ParsePosition;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Hades on 2016/5/3.
 */
public class DateUtil {

    public static Calendar ISO8601toCalendar(String isoStr) throws ParseException {
        Date date = ISO8601Utils.parse(isoStr, new ParsePosition(0));
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    public static int getDay(Calendar calendar) {
        if(calendar!=null){
            return calendar.get(Calendar.DAY_OF_MONTH);
        }
        return -1;
    }

    public static int getMonth(Calendar calendar) {
        if(calendar!=null){
            return calendar.get(Calendar.MONTH)+1;
        }
        return -1;
    }

    public static int getYear(Calendar calendar) {
        if(calendar!=null){
            return calendar.get(Calendar.YEAR);
        }
        return -1;
    }

    public static int getHour(Calendar calendar) {
        if(calendar!=null){
            return calendar.get(Calendar.HOUR_OF_DAY);
        }
        return -1;
    }

    public static String getMinute(Calendar calendar) {
        if(calendar!=null){
            if(calendar.get(Calendar.MINUTE)<10){
                return "0"+calendar.get(Calendar.MINUTE);
            }else{

                return String.valueOf(calendar.get(Calendar.MINUTE));
            }
        }
        return "";
    }
}
