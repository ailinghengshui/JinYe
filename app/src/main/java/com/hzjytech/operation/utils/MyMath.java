package com.hzjytech.operation.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * Created by hehongcan on 2017/3/29.
 */
public class MyMath {
    private static   DecimalFormat df=  new DecimalFormat("0.00");
    public static double add(double d1, double d2) {        // 进行加法运算
        BigDecimal b1 = new BigDecimal(d1);
        BigDecimal b2 = new BigDecimal(d2);
        return b1.add(b2).doubleValue();
    }

    public static double sub(double d1, double d2) {        // 进行减法运算
        BigDecimal b1 = new BigDecimal(d1);
        BigDecimal b2 = new BigDecimal(d2);
        return b1.subtract(b2).doubleValue();
    }

    public static double mul(double d1, double d2) {        // 进行乘法运算
        BigDecimal b1 = new BigDecimal(d1);
        BigDecimal b2 = new BigDecimal(d2);
        return b1.multiply(b2).doubleValue();
    }

    public static double div(double d1,
                             double d2, int len) {// 进行除法运算
        BigDecimal b1 = new BigDecimal(d1);
        BigDecimal b2 = new BigDecimal(d2);
        return b1.divide(b2, len, BigDecimal.
                ROUND_HALF_UP).doubleValue();
    }

    public static double round(double d,
                               int len) {
        // 进行四舍五入
        BigDecimal b1 = new BigDecimal(d + "");
        BigDecimal b2 = new BigDecimal(1);
        // 任何一个数字除以1都是原数字
        // ROUND_HALF_UP是BigDecimal的一个常量，表示进行四舍五入的操作
        return b1.divide(b2, len, BigDecimal.
                ROUND_HALF_UP).doubleValue();
    }

    public static String getNormalString(String s) {
        BigDecimal db = new BigDecimal(s);
        String ii = db.toPlainString();
        return ii;
    }
    public static String getTwoRoundNum(double b){
       return df.format(b);
    }

    /**
     * 禁用科学计数法
     * 根据是否是20.0、20.1区别返回20和20.1
     * @param d
     * @return
     */
    public static String getIntOrDouble(double d) {
        java.text.NumberFormat nf = java.text.NumberFormat.getInstance();
        nf.setGroupingUsed(false);
        String s = nf.format(d);
        //nf已经可以完成区分小数和整数的功能
      /*  double aDouble = Double.valueOf(s);
        String priceInfo = String.valueOf(aDouble > Double.valueOf(aDouble).intValue() ? (float) aDouble+"" :(int)aDouble+"");*/
        return s;
    }
}
