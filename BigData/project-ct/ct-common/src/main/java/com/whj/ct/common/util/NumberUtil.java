package com.whj.ct.common.util;

import java.text.DecimalFormat;
/**
* Description:
* date: 2021/8/10 15:52
* @author: whj
* @method:将数字格式化为字符串
*/

public class NumberUtil {
    public static String format(int number,int length){
        StringBuilder stringBuilder = new StringBuilder();
        for(int i=0;i<=length;i++){
            stringBuilder.append("0");
        }
        DecimalFormat df=new DecimalFormat(stringBuilder.toString());
        return df.format(number);
    }

    public static void main(String[] args) {
        NumberUtil numberUtil = new NumberUtil();
        System.out.println(numberUtil.format(10,10));
    }
}
