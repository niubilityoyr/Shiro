package com.oyr.shiro.utils;

import java.io.*;

/**
 * Create by 欧阳荣
 * 2018/6/2 17:33
 */
public class SerializeUtil {

    /** 序列化对象
     * ＠throws IOException */
    public static byte[] serializeObject(Object object){
        ByteArrayOutputStream saos = new ByteArrayOutputStream();
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(saos);
            oos.writeObject(object);
            oos.flush();
        } catch (IOException e) {
            System.out.println("序列化失败");
            e.printStackTrace();
            return null;
        }
        return saos.toByteArray();

    }

    /** 反序列化对象
     * ＠throws IOException
     * ＠throws ClassNotFoundException */
    public static Object deserializeObject(byte[] buf){
        Object object=null;
        ByteArrayInputStream sais=new ByteArrayInputStream(buf);
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(sais);
            object = ois.readObject();
        } catch (Exception e) {
            System.out.println("反序列化失败");
            e.printStackTrace();
            return null;
        }
        return object;
    }

}

