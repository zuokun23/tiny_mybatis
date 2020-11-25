package com.zuokun.mybatis.sqlsession.utils;

import com.zuokun.mybatis.cfg.Mapper;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

/**
 * @author carlos
 * 负责执行SQL语句，并且封装结果集
 */
public class Executor {

    public <E> List<E> selectList(Mapper mapper, Connection conn){

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try{
            //1.取出mapper中的数据
            String queryString = mapper.getQueryString();//select * from user
            String resultType = mapper.getResultTye();//com.zuokun.domain.User
            Class domainClass = Class.forName(resultType);
            //2.获取PreparedStatement对象
            preparedStatement = conn.prepareStatement(queryString);
            //3.执行SQL语句，获取结果集
            resultSet = preparedStatement.executeQuery();
            //4.封装结果集
            List<E> list = new ArrayList<E>();//定义返回值
            while(resultSet.next()){
                //示例化要封装的实体类对象
                E obj = (E)domainClass.getConstructor().newInstance();
                //取出的结果集的元信息：ResultSetMetaData
                ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
                //取出总列数
                int columnCounnt = resultSetMetaData.getColumnCount();
                //遍历总列数
                for(int i = 1 ; i <= columnCounnt ; i++){
                    //获取每列的名称，列名的序号是从1开始的
                    String columnName = resultSetMetaData.getColumnName(i);
                    //根据得到列名，获取每列的值
                    Object columnValue = resultSet.getObject(columnName);
                    //给obj赋值：使用java内省机制（借助PropertyDescriptor实现属性的封装）
                    PropertyDescriptor pd = new PropertyDescriptor(columnName, domainClass);//要求：实体类的属性和数据库列表的列名保持一致
                    //获取它的写入方法
                    Method writeMethod = pd.getWriteMethod();
                    //把获取的列的值，给对象赋值
                    writeMethod.invoke(obj, columnValue);

                }
                //把赋值的对象加入集合中
                list.add(obj);
            }
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }finally {
            release(preparedStatement, resultSet);
        }

    }

    private void release(PreparedStatement preparedStatement, ResultSet resultSet){
        if(resultSet != null){
            try {
                resultSet.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        if(preparedStatement != null){
            try{
                preparedStatement.close();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
}
