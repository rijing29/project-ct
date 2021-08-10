package com.whj.ct.producer.bean;

import com.whj.ct.common.bean.Data;
import com.whj.ct.common.bean.DataIn;
import com.whj.ct.common.bean.DataOut;
import com.whj.ct.common.bean.Producer;

import java.io.IOException;
import java.util.List;

/**
* Description:
* date: 2021/8/9 19:48
* @author: whj
* @method:本地数据文件生产者
*/

public class LocalFileProducer implements Producer {
    private DataIn in;
    private DataOut out;
    private volatile boolean flg;
    public void setIn(DataIn in) {
        this.in = in;
    }

    public void setOut(DataOut out) {
        this.out = out;
    }

//    生产数据

    @Override
    public void produce() throws IOException, InstantiationException, IllegalAccessException {
//        读取通讯录数据
        List<Contact> contacts = in.read(Contact.class);
        for (Contact contact : contacts) {
            System.out.println(contact);
            
        }
//        while(flg){
////        从通讯录中随即查找两个电话号码
////        生成随机通话时间
////        生成随机通话时长
////        生成通话记录
////        将通话记录刷写到数据文件中
//        }
    }
//关闭生产者
    @Override
    public void close() throws IOException {
        if(in!=null){
            in.close();
        }
        if(out!=null){
            out.close();
        }
    }
}
