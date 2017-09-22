package com.hzjytech.operation.utils;

import android.text.TextUtils;

import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by dblr4287 on 2016/5/13.
 */
public class StringUtil {
    private StringUtil()
    {

    }

    public static int parseInt(String str, int ev)
    {
        try
        {
            return Integer.parseInt(str);
        }
        catch (NumberFormatException e)
        {
            return ev;
        }
    }

    public static int parseInt(String str)
    {
        return parseInt(str, -1);
    }

    public static long parseLong(String str, long ev)
    {
        try
        {
            return Long.parseLong(str);
        }
        catch (NumberFormatException e)
        {
            return ev;
        }
    }

    public static long parseLong(String str)
    {
        return parseLong(str, -1L);
    }

    public static double parseDouble(String str, double ev)
    {
        try
        {
            return Double.parseDouble(str);
        }
        catch (Exception e)
        {
            return ev;
        }
    }

    public static boolean isNullOrEmpty(String str)
    {
        if (str == null || str.trim().length() == 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public static String trim(String str)
    {
        try
        {
            return str.trim();
        }
        catch (Exception e)
        {
            return "";
        }
    }

    public static String InsertEscChar(String str, char escapeChar)
    {
        if (escapeChar == '%')
        {
            if (str.indexOf(String.valueOf('%')) != -1)
            {
                str = str.replaceAll("%", "\n%");
            }
        }
        else if (escapeChar == '_')
        {
            if (str.indexOf(String.valueOf('_')) != -1)
            {
                str = str.replaceAll("_", "\n_");
            }
        }
        else if (escapeChar == '\'')
        {
            if (str.indexOf(String.valueOf('\'')) != -1)
            {
                str = str.replaceAll("\'", "\''");
            }
        }
        return str;
    }

    public static String unitConversion(String priceInfo)
    {
        try
        {
            double price = 0.0;
            if (priceInfo == null || "0".equalsIgnoreCase(priceInfo) || priceInfo.trim().length() == 0)
            {
                return "0.00";
            }
            if (priceInfo != null && priceInfo.trim().length() != 0)
            {
                price = StringUtil.parseDouble(priceInfo, 0.0) / 100.0;
            }

            DecimalFormat format = new DecimalFormat("###.00");

            return format.format(price);
        }
        catch (Exception e)
        {
            return priceInfo;
        }
    }


    public static String replaceCharWithEntity(String data)
    {

        data = data.replaceAll("&", "&amp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("'", "&apos;").replaceAll("\"", "&quot;");
        return data;
    }


    public static String restoreEntityToChar(String data)
    {

        data = data.replaceAll("&lt;", "<").replaceAll("&gt;", ">").replaceAll("&apos;", "'").replaceAll("&quot;", "\"").replaceAll("&nbsp;", " ")
                .replaceAll("&amp;", "&");

        return data;
    }

    public static String replaceAllCharWithEntity(String data)
    {
        data = data.replaceAll("<(.*?)>", "");
        return data;
    }

    public static String convertEscapeSequence(String orig)
    {
        if (orig == null)
            return null;
        orig = orig.replaceAll("&ldquo;", "\u201c");
        orig = orig.replaceAll("&rdquo;", "\u201d");
        orig = orig.replaceAll("&hellip;", "\u2026");
        orig = orig.replaceAll("&mdash;", "\u2014");
        orig = orig.replaceAll("&lsquo;", "\u2018");
        orig = orig.replaceAll("&rsquo;", "\u2019");
        orig = orig.replaceAll("&middot;", "\u00b7");
        return orig;
    }



    public static String getNumFromStr(String str){
        String regex = "\\d*";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(str);
        while (m.find()) {
            if (!"".equals(m.group()))
                return  m.group();
        }
        return "" ;
    }


    public static boolean isNumeric(String str)
    {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches())
        {
            return false;
        }
        return true;
    }


    public static String setTextWithoutNull(String text)
    {
        if (null == text || "null".equals(text))
        {
            return "";
        }
        return text;
    }


    public static boolean isMobile(String mobile) {

        if (TextUtils.isEmpty(mobile)) {
            return false;
        }

        Pattern pattern = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$");
        Matcher matcher = pattern.matcher(mobile);

        return matcher.matches();
    }



    public static boolean isMobile2(String text) {

        if (TextUtils.isEmpty(text)) {
            return false;
        }
        Pattern p = Pattern.compile("^[0-9]{11}$");
        Matcher m = p.matcher(text);

        return m.matches();
    }
    public static boolean isMoney(String money) {
        String regex = "(^[1-9][0-9]{0,7}(\\.[0-9]{0,2})?)|(^0(\\.[0-9]{0,2})?)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(money);
        return matcher.matches();
    }
    public static boolean isPassword(String password) {

        if (TextUtils.isEmpty(password)) {
            return false;
        }

        Pattern pattern = Pattern.compile("^^[a-zA-Z][a-zA-Z0-9_]{5,17}$");
        Matcher matcher = pattern.matcher(password);

        return matcher.matches();
    }

}
