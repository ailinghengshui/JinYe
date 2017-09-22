package com.hzjytech.operation.utils;

/**
 * Created by Hades on 2016/8/3.
 * This class is used to joint int/float to String
 */
public class StringJointUtil {

    /**
     * this function tranvel obj to String
     * @param obj(int/float/double)
     * @param fix(前缀/后缀)
     * @param isFront if true,add fix front else add fix end
     * @return
     */
    public static String obj2String(Object obj, String fix, boolean isFront){

        StringBuffer stringBuffer=new StringBuffer();
        if(isFront){
            stringBuffer.append(fix);
            stringBuffer.append(obj);
        }else{
            stringBuffer.append(obj);
            stringBuffer.append(fix);
        }
        return stringBuffer.toString();
    }

}
