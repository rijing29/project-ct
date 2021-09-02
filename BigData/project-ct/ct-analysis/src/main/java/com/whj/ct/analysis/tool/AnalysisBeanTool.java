package com.whj.ct.analysis.tool;

import com.whj.ct.analysis.io.MySQLBeanOutputFormat;
import com.whj.ct.analysis.io.MySQLTextOutputFormat;
import com.whj.ct.analysis.kv.AnalysisKey;
import com.whj.ct.analysis.kv.AnalysisValue;
import com.whj.ct.analysis.mapper.AnalysisBeanMapper;
import com.whj.ct.analysis.mapper.AnalysisTextMapper;
import com.whj.ct.analysis.reducer.AnalysisBeanReducer;
import com.whj.ct.analysis.reducer.AnalysisTextReducer;
import com.whj.ct.common.constant.Names;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.JobStatus;
import org.apache.hadoop.util.Tool;

/**
* Description:
* date: 2021/8/27 17:36
* @author: whj
* @method:分析数据的工具栏
*/

public class AnalysisBeanTool implements Tool {

    public int run(String[] args) throws Exception {

        Job job = Job.getInstance();
        job.setJarByClass(AnalysisBeanTool.class);

        Scan scan = new Scan();
        scan.addFamily(Bytes.toBytes(Names.CF_CALLER.getValue()));

        // mapper
        TableMapReduceUtil.initTableMapperJob(
                Names.TABLE.getValue(),
                scan,
                AnalysisBeanMapper.class,
                AnalysisKey.class,
                Text.class,
                job
        );

        // reducer
        job.setReducerClass(AnalysisBeanReducer.class);
        job.setOutputKeyClass(AnalysisKey.class);
        job.setOutputValueClass(AnalysisValue.class);

        // outputformat
        job.setOutputFormatClass(MySQLBeanOutputFormat.class);

        boolean flg = job.waitForCompletion(true);
        if ( flg ) {
            return JobStatus.State.SUCCEEDED.getValue();
        } else {
            return JobStatus.State.FAILED.getValue();
        }
    }

    public void setConf(Configuration conf) {

    }

    public Configuration getConf() {
        return null;
    }
}
