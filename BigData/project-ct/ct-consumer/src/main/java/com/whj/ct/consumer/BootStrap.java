package com.whj.ct.consumer;

import com.whj.ct.consumer.bean.CalllogConsumer;

import java.io.IOException;

public class BootStrap {

    public final static String ABC="ABC";
    public static void main(String[] args) throws Exception {

//        创建消费者
        CalllogConsumer consumer = new CalllogConsumer();
//消费数据
        consumer.consume();
//        关闭资源
        consumer.close();

    }
}
