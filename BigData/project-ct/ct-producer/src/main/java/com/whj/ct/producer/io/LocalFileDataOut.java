package com.whj.ct.producer.io;

import com.whj.ct.common.bean.DataOut;

import java.io.IOException;

/**
* Description:
* date: 2021/8/9 19:53
* @author: whj
* @method:本地文件输出
*/

public class LocalFileDataOut implements DataOut {
    public LocalFileDataOut(String path){
        setPath(path);
    }
    @Override
    public void setPath(String path) {

    }

    @Override
    public void close() throws IOException {

    }
}
