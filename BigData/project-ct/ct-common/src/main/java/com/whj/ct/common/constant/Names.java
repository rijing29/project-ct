package com.whj.ct.common.constant;

import com.whj.ct.common.bean.Val;
/**
* Description:
* date: 2021/8/9 19:43
* @author: whj
* @method:名称常量枚举类
*/

public enum Names implements Val {
    NAMESPACE("ct"),
    TOPIC("ct"),
    TABLE("ct:calllog"),
    CF_CALLER("caller"),
    CF_CALLEE("callee"),
    CF_INFO("info");

    private String name;

    private Names(String name) {
        this.name=name;
    }


    @Override
    public void setValue(Object val) {
        this.name=(String)val;
    }

    @Override
    public String getValue() {
        return name;
    }
}
