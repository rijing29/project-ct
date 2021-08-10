package com.whj.ct.common.bean;



/**
* Description:数据对象
* date: 2021/8/9 19:39
* @author: whj
* @method:
*/

public abstract class Data implements Val{

    public String content;

    @Override
    public void setValue(Object val) {
        content= (String) val;
    }

    @Override
    public String getValue() {
        return content;
    }
}
