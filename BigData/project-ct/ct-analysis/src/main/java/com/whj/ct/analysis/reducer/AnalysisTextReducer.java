package com.whj.ct.analysis.reducer;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapred.Reporter;

import java.io.IOException;
import java.util.Iterator;
/**
* Description:
* date: 2021/8/27 18:32
* @author: whj
* @method:分析数据
*/

public class AnalysisTextReducer extends Reducer<Text, Text, Text, Text >{
    @Override
    protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {

        int sumCall = 0;
        int sumDuration = 0;

        for (Text value : values) {
            int duration = Integer.parseInt(value.toString());
            sumDuration = sumDuration + duration;

            sumCall++;
        }

        context.write(key, new Text(sumCall + "_" + sumDuration));
    }
}
