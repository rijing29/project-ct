package com.whj.ct.producer.io;

import com.whj.ct.common.bean.Data;
import com.whj.ct.common.bean.DataIn;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
* Description:
* date: 2021/8/9 19:52
* @author: whj
* @method:本地文件数据输入
*/

public class LocalFileDataIn implements DataIn {
    private BufferedReader reader=null;

    public LocalFileDataIn(String path) throws FileNotFoundException, UnsupportedEncodingException {
        setPath(path);
    }
    @Override
    public void setPath(String path) throws FileNotFoundException, UnsupportedEncodingException {
        reader=new BufferedReader(new InputStreamReader( new FileInputStream(path),"UTF-8"));
    }

//    读取数据返回数据集合
    public <T extends Data> List<T> read(Class<T> clazz) throws IOException, IllegalAccessException, InstantiationException {
        List<T> ts = new ArrayList<>();
//        从文件中读取所有数据
        String line=null;
        while((line=reader.readLine())!=null){
//            将数据转换为指定类型对象，封装为集合返回
            T t = clazz.newInstance();
            t.setValue(line);
            ts.add(t);
        }
//        将数据转换为指定类型的对象，封装为集合返回
        return ts;
    }
//关闭资源
    @Override
    public void close() throws IOException {
        if(reader!=null){
            reader.close();
        }
    }
}
