package com.whj.ct.common.bean;

import java.io.Closeable;
import java.io.IOException;
import java.text.ParseException;

/**
* Description:
* date: 2021/8/9 19:36
* @author: whj
* @method:生产者接口
*/

public interface Producer extends Closeable {
    public void setIn(DataIn in);
    public void setOut(DataOut out);
//    生产数据
    public void produce() throws IOException, InstantiationException, IllegalAccessException, ParseException, InterruptedException;
}
