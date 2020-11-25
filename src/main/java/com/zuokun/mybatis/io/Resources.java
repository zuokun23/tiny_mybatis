package com.zuokun.mybatis.io;

//使用类加载器读取配置问价的类

import java.io.InputStream;

public class Resources {

    public static InputStream getResourceAsStream(String filePath){
        return Resources.class.getClassLoader().getResourceAsStream(filePath);
    }

}
