package com.whj.ct.analysis.mapper;

import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableMapper;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.Text;

import java.io.IOException;

/**
* Description:
* date: 2021/8/27 18:27
* @author: whj
* @method:分析数据的mapper
*/

public class AnalysisTextMapper extends TableMapper<Text,Text> {
    @Override
    protected void map(ImmutableBytesWritable key, Result value, Context context) throws IOException, InterruptedException {
        String rowkey= Bytes.toString(key.get());
        String[] values = rowkey.split("_");
        String call1=values[1];
        String call2=values[2];
        String calltime=values[3];
        String duration=values[4];

        String year=calltime.substring(0,4);
        String month=calltime.substring(0,6);
        String date=calltime.substring(0,8);

//        主叫用户
        context.write(new Text(call1+"_"+year),new Text(duration));
        context.write(new Text(call1+"_"+month),new Text(duration));
        context.write(new Text(call1+"_"+date),new Text(duration));
//        被叫用户
        context.write(new Text(call2+"_"+year),new Text(duration));
        context.write(new Text(call2+"_"+month),new Text(duration));
        context.write(new Text(call2+"_"+date),new Text(duration));
    }
}
