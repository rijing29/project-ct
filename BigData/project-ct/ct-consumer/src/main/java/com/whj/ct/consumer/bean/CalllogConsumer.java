package com.whj.ct.consumer.bean;

import com.whj.ct.common.bean.Consumer;
import com.whj.ct.common.constant.Names;
import com.whj.ct.consumer.dao.HbaseDao;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;

/**
* Description:
* date: 2021/8/15 16:14
* @author: whj
* @method:通话日志消费者对象
*/

public class CalllogConsumer implements Consumer {
//    消费资源
    public void consume() throws Exception {
//        创建配置对象
        Properties prop = new Properties();
        prop.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("consumer.properties"));
//        采集flume数据
        KafkaConsumer<String ,String> consumer=new KafkaConsumer<String, String>(prop);
//        关注主题
        consumer.subscribe(Arrays.asList(Names.TOPIC.getValue()));
        HbaseDao hbaseDao = new HbaseDao();
        hbaseDao.init();
//        消费数据
        while (true){
            ConsumerRecords<String, String> poll = consumer.poll(100);
            for (ConsumerRecord<String, String> stringStringConsumerRecord : poll) {
                System.out.println(stringStringConsumerRecord.value());
                Calllog log = new Calllog(stringStringConsumerRecord.value());
                hbaseDao.insertData(stringStringConsumerRecord.value());
                hbaseDao.inserData(log);
            }
        }
    }

    @Override
    public void close() throws IOException {

    }
}
