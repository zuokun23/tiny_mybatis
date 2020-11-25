package com.zuokun.mybatis.sqlsession.proxy;

import com.zuokun.mybatis.cfg.Mapper;
import com.zuokun.mybatis.sqlsession.utils.Executor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.util.Map;

public class MapperProxy implements InvocationHandler {
    //map的key是全限定类名+方法名
    private Map<String, Mapper> mappers;
    private Connection conn;

    public MapperProxy(Map<String, Mapper> mappers, Connection conn) {
        this.mappers = mappers;
        this.conn = conn;
    }

    /**
     * 对方法进行增强，我们这里就是调用selectAll
     * @param proxy
     * @param method
     * @param args
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        //1.获取方法名
        String methodName = method.getName();//findAll
        //2.获取方法所在的类名
        String className = method.getDeclaringClass().getName();//得到的是IUserDao接口，不是实现类
        //3.组合key
        String key = className + "." + methodName;
        //4.获取mappers对象中的Mapper对象
        Mapper mapper = mappers.get(key);
        //5.判断是否有mapper
        if(mapper == null){
            throw new IllegalArgumentException("传入参数有误");
        }

        //6.调用工具类执行查询所有
        return new Executor().selectList(mapper, conn);
    }
}
