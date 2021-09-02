package com.whj.ct.producer;

import com.whj.ct.common.bean.Producer;
import com.whj.ct.producer.bean.LocalFileProducer;
import com.whj.ct.producer.io.LocalFileDataIn;
import com.whj.ct.producer.io.LocalFileDataOut;

import java.io.IOException;
import java.text.ParseException;

/**
* Description:
* date: 2021/8/9 19:45
* @author: whj
* @method:启动对象
*/

public class Bootstrap {
    public static void main(String[] args) throws IOException, IllegalAccessException, InstantiationException, ParseException, InterruptedException {
        if(args.length<2){
            System.out.println("系统参数不正确,请以jar包方式运行：java -jar Produce.jar path1 path2 ");
            System.exit(1);
        }

//        构建生产者对象
        Producer producer = new LocalFileProducer();
//        producer.setIn(new LocalFileDataIn("D:\\temp\\尚硅谷电信客服大数据项目的文档资料\\contact.log"));
//        producer.setOut(new LocalFileDataOut("D:\\temp\\尚硅谷电信客服大数据项目的文档资料\\call.log"));
//        生产数据
        producer.setIn(new LocalFileDataIn(args[0]));
        producer.setOut(new LocalFileDataOut(args[1]));
        producer.produce();
//        关闭生产者对象
        producer.close();




    }
}
