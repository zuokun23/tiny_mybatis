package com.zuokun.mybatis.sqlsession;

/**
 * 自定义mybatis中和数据库交互的核心类
 * 它里面可以创建dao接口的代理对象
 */
public interface SqlSession {

    <T> T getMapper(Class<T> daoInterfaceClass);

    void close();
}
