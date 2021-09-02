package com.whj.ct.analysis.kv;

import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
/**
* Description:
* date: 2021/8/30 10:04
* @author: whj
* @method:自定义分析数据value
*/


public class AnalysisValue implements Writable {
    private String sumCall;
    private String sumDuration;

    public AnalysisValue(String sumCall, String sumDuration) {
        this.sumCall = sumCall;
        this.sumDuration = sumDuration;
    }

    public String getSumCall() {
        return sumCall;
    }

    public void setSumCall(String sumCall) {
        this.sumCall = sumCall;
    }

    public String getSumDuration() {
        return sumDuration;
    }

    public void setSumDuration(String sumDuration) {
        this.sumDuration = sumDuration;
    }

    public AnalysisValue() {
    }
    public void write(DataOutput out) throws IOException {
        out.writeUTF(sumCall);
        out.writeUTF(sumDuration);
    }
    public void readFields(DataInput in) throws IOException {
        sumCall=in.readUTF();
        sumDuration=in.readUTF();
    }
}
