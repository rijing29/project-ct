package com.whj.ct.analysis.kv;

import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
/**
* Description: 
* date: 2021/8/30 10:03
* @author: whj 
* @method:自定义分析数据的key
*/

public class AnalysisKey implements WritableComparable<AnalysisKey> {

    private String tel;
    private String date;


    public AnalysisKey() {
    }

    public AnalysisKey(String tel, String date) {
        this.tel = tel;
        this.date = date;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

//    写数据
    public int compareTo(AnalysisKey key) {
        int result = tel.compareTo(key.getTel());
        if (result==0){
            result=date.compareTo(key.getDate());
        }
        return result;
    }
//写数据
    public void write(DataOutput out) throws IOException {
        out.writeUTF(tel);
        out.writeUTF(date);
    }
//读数据
    public void readFields(DataInput in) throws IOException {
        tel=in.readUTF();
        date=in.readUTF();
    }
}
