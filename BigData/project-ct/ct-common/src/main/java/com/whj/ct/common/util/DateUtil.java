package com.whj.ct.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
* Description:
* date: 2021/8/10 16:01
* @author: whj
* @method:日期工具类
*/

public class DateUtil {
    public static Date parse(String s,String format) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        Date date = dateFormat.parse(s);
        return date;
    }
    public static String format(Date date,String format){
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }
}
