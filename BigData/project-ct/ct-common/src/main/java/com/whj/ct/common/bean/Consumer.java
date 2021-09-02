package com.whj.ct.common.bean;

import java.io.Closeable;
import java.io.IOException;

public interface Consumer extends Closeable {
//    消费数据
    public void consume() throws Exception;
}
