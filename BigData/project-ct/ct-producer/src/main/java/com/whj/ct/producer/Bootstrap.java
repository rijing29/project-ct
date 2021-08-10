package com.whj.ct.producer;

import com.whj.ct.common.bean.Producer;
import com.whj.ct.producer.bean.LocalFileProducer;
import com.whj.ct.producer.io.LocalFileDataIn;
import com.whj.ct.producer.io.LocalFileDataOut;

import java.io.IOException;

/**
* Description:
* date: 2021/8/9 19:45
* @author: whj
* @method:启动对象
*/

public class Bootstrap {
    public static void main(String[] args) throws IOException, IllegalAccessException, InstantiationException {
//        构建生产者对象
        Producer producer = new LocalFileProducer();
        producer.setIn(new LocalFileDataIn("D:\\temp\\尚硅谷电信客服大数据项目的文档资料\\contact.log"));
        producer.setOut(new LocalFileDataOut("D:\\temp\\尚硅谷电信客服大数据项目的文档资料\\call.log"));
//        生产数据
        producer.produce();
//        关闭生产者对象
        producer.close();




    }
}
