package com.whj.ct.consumer.dao;

import com.whj.ct.common.bean.BaseDao;
import com.whj.ct.common.constant.Names;
import com.whj.ct.common.constant.ValueConstant;
import com.whj.ct.consumer.bean.Calllog;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
* Description:
* date: 2021/8/17 12:46
* @author: whj
* @method:hbase数据访问对象
*/

public class HbaseDao extends BaseDao {
//    初始化
    public void init() throws Exception {
        start();
        createNamespaceNX(Names.NAMESPACE.getValue());
        createTableXX(Names.TABLE.getValue(),"com.whj.ct.processor.InsertCalleeCoprocessor",ValueConstant.REGION_COUNT,Names.CF_CALLER.getValue(),Names.CF_CALLEE.getValue());
        end();
    }
//    插入对象
    public void inserData(Calllog log) throws Exception {
        log.setRowkey(genRegionNum(log.getCall1(),log.getCalltime())+"_"+log.getCall1()
                +"_"+log.getCalltime()+"_"+log.getCall2()+"_"+log.getDuration());
        putData(log);
    }
//插入
    public void insertData(String value) throws IOException {
//        将通话日志保存到Hbase表中
//        获取通话日志数据
        String[] values = value.split("\t");
        String call1=values[0];
        String call2=values[1];
        String calltime=values[2];
        String duration=values[3];
//        创建数据对象

        String rowkey=genRegionNum(call1,calltime)+"_"+call1+"_"+calltime+"_"+call2+"_"+duration+"_1";
        //        主叫用户
        Put put = new Put(Bytes.toBytes(rowkey));
        byte[] family = Bytes.toBytes(Names.CF_CALLER.getValue());
        put.addColumn(family,Bytes.toBytes("call1"),Bytes.toBytes(call1));
        put.addColumn(family,Bytes.toBytes("call2"),Bytes.toBytes(call2));
        put.addColumn(family,Bytes.toBytes("calltime"),Bytes.toBytes(calltime));
        put.addColumn(family,Bytes.toBytes("duration"),Bytes.toBytes(duration));
        put.addColumn(family,Bytes.toBytes("flg"),Bytes.toBytes("1"));

        String calleeRowkey=genRegionNum(call2,calltime)+"_"+call2+"_"+calltime+"_"+call1+"_"+duration+"_0";
//        被叫用户
//        Put calleePut = new Put(Bytes.toBytes(calleeRowkey));
//        byte[] calleeFamily = Bytes.toBytes(Names.CF_CALLEE.getValue());
//        calleePut.addColumn(calleeFamily,Bytes.toBytes("call1"),Bytes.toBytes(call2));
//        calleePut.addColumn(calleeFamily,Bytes.toBytes("call2"),Bytes.toBytes(call1));
//        calleePut.addColumn(calleeFamily,Bytes.toBytes("calltime"),Bytes.toBytes(calltime));
//        calleePut.addColumn(calleeFamily,Bytes.toBytes("duration"),Bytes.toBytes(duration));
//        calleePut.addColumn(calleeFamily,Bytes.toBytes("flg"),Bytes.toBytes("0"));

        List<Put> puts = new ArrayList<Put>();
        puts.add(put);
//        puts.add(calleePut);
        putData(Names.TABLE.getValue(),puts);
//        putData(Names.TABLE.getValue(),calleePut);
    }

}
