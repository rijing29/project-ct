package com.whj.ct.producer.io;

import com.whj.ct.common.bean.DataOut;

import java.io.*;

/**
* Description:
* date: 2021/8/9 19:53
* @author: whj
* @method:本地文件输出
*/

public class LocalFileDataOut implements DataOut {
    private PrintWriter writer=null;
    public LocalFileDataOut(String path) throws FileNotFoundException, UnsupportedEncodingException {
        setPath(path);
    }
    @Override
    public void setPath(String path) throws FileNotFoundException, UnsupportedEncodingException {
        writer=new PrintWriter(new OutputStreamWriter(new FileOutputStream(path),"UTF-8"));
    }

    @Override
    public void write(Object data) {
        write(data.toString());
    }

//    将数据字符串生成到文件中
    public void write(String data) {
        writer.println(data);
        writer.flush();
    }

    @Override
    public void close() throws IOException {
        if(writer!=null)
            writer.close();
    }
}
