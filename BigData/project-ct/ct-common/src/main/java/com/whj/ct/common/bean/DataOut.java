package com.whj.ct.common.bean;

import java.io.Closeable;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

public interface DataOut extends Closeable {
    public void setPath(String path) throws FileNotFoundException, UnsupportedEncodingException;
    public void write(Object data);
    public void write(String data);
}
