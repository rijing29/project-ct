package com.whj.ct.common.bean;


import com.whj.ct.common.api.Column;
import com.whj.ct.common.api.Rowkey;
import com.whj.ct.common.api.TableRef;
import com.whj.ct.common.constant.Names;
import com.whj.ct.common.constant.ValueConstant;
import com.whj.ct.common.util.DateUtil;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.util.*;

/**
* Description:
* date: 2021/8/17 12:45
* @author: whj
* @method:基础数据访问对象
*/

public abstract class BaseDao {
    private ThreadLocal<Connection> connHolder=new ThreadLocal<Connection>();
    private ThreadLocal<Admin> adminHolder=new ThreadLocal<Admin>();

    protected void start() throws IOException {
        getConnection();
        getAdmin();
    }
    protected void end() throws IOException {
        Admin admin=getAdmin();
        if(admin!=null){
            admin.close();
            adminHolder.remove();
        }
        Connection conn = getConnection();
        if(conn!=null){
            conn.close();
            connHolder.remove();
        }
    }
//创建命名空间 meixiewan
    protected void createNamespaceNX(String namespace) throws Exception  {
        Admin admin = getAdmin();
        try {
            admin.getNamespaceDescriptor(namespace);
        } catch ( NamespaceNotFoundException e ) {
            //e.printStackTrace();
            NamespaceDescriptor namespaceDescriptor = NamespaceDescriptor.create(namespace).build();
            admin.createNamespace(namespaceDescriptor);
        }
    }

//创建表
    protected void createTableXX(String name,String... families) throws Exception {
        createTableXX(name,null,null,families);
    }
    protected void createTableXX(String name, String coprocessorClass,Integer regionCount,String... families) throws Exception {
        Admin admin = getAdmin();
        TableName tableName = TableName.valueOf(name);
        if(admin.tableExists(tableName)){
//            则删除
            deleteTable(name);
        }
        createTable(name,coprocessorClass, regionCount,families);
    }
    private void createTable( String name,String coprocessorClass,Integer regionCount,String... families ) throws Exception {
        Admin admin = getAdmin();
        TableName tableName = TableName.valueOf(name);

        HTableDescriptor tableDescriptor =
                new HTableDescriptor(tableName);

        if ( families == null || families.length == 0 ) {
            families = new String[1];
            families[0] = Names.CF_INFO.getValue();
        }

        for (String family : families) {
            HColumnDescriptor columnDescriptor =
                    new HColumnDescriptor(family);
            tableDescriptor.addFamily(columnDescriptor);
        }

        if(coprocessorClass!=null && !"".equals(coprocessorClass)){
            tableDescriptor.addCoprocessor(coprocessorClass);
        }
//        增加预分区
//        分区键
        if(regionCount==null||regionCount<=1){
            admin.createTable(tableDescriptor);
        }else{
            byte[][] splitKeys=genSplitKeys(regionCount);
            admin.createTable(tableDescriptor,splitKeys);
        }
    }


    protected static List<String[]> getStartStorRowkeys(String tel,String start,String end) throws ParseException {
        List<String[]> rowkeyes=new ArrayList<String[]>();

        String startTime = start.substring(0, 6);
        String endTime = start.substring(0, 6);
        Calendar startCal = Calendar.getInstance();
        startCal.setTime(DateUtil.parse(startTime,"yyyyMM"));

        Calendar endCal = Calendar.getInstance();
        endCal.setTime(DateUtil.parse(endTime,"yyyyMM"));

        while(startCal.getTimeInMillis()<=endCal.getTimeInMillis()){
            String currentTime = DateUtil.format(startCal.getTime(), "yyyyMM");
            int regionNum = genRegionNum(tel, startTime);
            String startRow=regionNum+"_"+tel+"_"+startTime;
            String stopRow=startRow+"|";
            String[] rowkeys= {startRow,stopRow};
            rowkeyes.add(rowkeys);
            startCal.add(Calendar.MONTH,1);
        }
        return rowkeyes;
    }



//
    protected static int genRegionNum(String tel, String date){
        String usercode = tel.substring(tel.length()-4);
        String year = date.substring(0,6);
        int userCodeHash = usercode.hashCode();
        int yearMonthHash = year.hashCode();
//        crc异或校验
        int crc = Math.abs(userCodeHash ^ yearMonthHash);
        int regionNum = crc % ValueConstant.REGION_COUNT;
        return regionNum;
    }

    private static byte[][] genSplitKeys(int regionCount){
        int splitKeyCount=regionCount-1;
        byte[][] bs = new byte[splitKeyCount][];
//        0,1,2,3
        List<byte[]> bsList = new ArrayList<byte[]>();
        for (int i=0;i<splitKeyCount;i++){
            String splitKey=i+"|";
            System.out.println(splitKey);
            bsList.add(Bytes.toBytes(splitKey));
        }
        bsList.toArray(bs);
        return bs;
    }
//    增加对象自动封装数据将数据直接保存到hbase中去
    protected void putData(Object obj) throws IOException, IllegalAccessException {
//        反射
        Class clazz = obj.getClass();
        TableRef tableRef = (TableRef) clazz.getAnnotation(TableRef.class);
        String tableName = tableRef.value();
        Field[] fs = clazz.getDeclaredFields();
        String stringRowkey="";
        for (Field f : fs) {
            Rowkey rowkey = f.getAnnotation(Rowkey.class);
            if(rowkey!=null){
                f.setAccessible(true);
                stringRowkey= (String) f.get(obj);
                break;
            }
        }
        Connection connection = getConnection();
        Table table = connection.getTable(TableName.valueOf(tableName));
        Put put = new Put(Bytes.toBytes(stringRowkey));

        for (Field f : fs) {
            Column column = f.getAnnotation(Column.class);
            if(column!=null){
                String family = column.family();
                String colName = column.column();
                if(colName==null || "".equals(colName)){
                    colName=f.getName();
                }
                f.setAccessible(true);
                String value = (String) f.get(obj);
                put.addColumn(Bytes.toBytes(family),Bytes.toBytes(colName),Bytes.toBytes(value));
            }
        }

        table.put(put);
        table.close();
    }

//    增加多条数据
    protected void putData(String name,List<Put> puts) throws IOException {
    //        获取表对象
        Connection connection = getConnection();
        Table table = connection.getTable(TableName.valueOf(name));
    //        增加数据
        table.put(puts);
    //        关闭表
        table.close();
    }



//增加数据
    protected void putData(String name,Put put) throws IOException {
//        获取表对象
        Connection connection = getConnection();
        Table table = connection.getTable(TableName.valueOf(name));
//        增加数据
        table.put(put);
//        关闭表
        table.close();
    }
//删除表格
    protected void deleteTable(String name) throws IOException {
        TableName tableName = TableName.valueOf(name);
        Admin admin = getAdmin();
        admin.disableTable(tableName);
        admin.deleteTable(tableName );
    }

    //获取连接对象
    protected synchronized Admin getAdmin() throws IOException {
        Admin admin = adminHolder.get();
        if(admin==null){
            admin = getConnection().getAdmin();
            adminHolder.set(admin);
        }
        return admin;
    }
    //获取连接对象
    protected synchronized Connection getConnection() throws IOException {
        Connection conn = connHolder.get();
        if(conn==null){
            Configuration conf = HBaseConfiguration.create();
            conn = ConnectionFactory.createConnection(conf);
            connHolder.set(conn);
        }
        return conn;

    }
    public static void main(String[] args) throws ParseException {
        for (String[] strings : getStartStorRowkeys("13325698741", "201803", "201808")) {
            System.out.println(strings[0]+"~"+strings[1]);
        }
    }

}
