package com.zuokun;

import com.zuokun.dao.IUserDao;
import com.zuokun.domain.User;
import com.zuokun.mybatis.io.Resources;
import com.zuokun.mybatis.sqlsession.SqlSession;
import com.zuokun.mybatis.sqlsession.SqlSessionFactory;
import com.zuokun.mybatis.sqlsession.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class MybatisTest {

    public static void main(String[] args) throws IOException {
        //1读取配置文件
        InputStream in = Resources.getResourceAsStream("SqlMapConfig.xml");
        //2创建SqlSeesionFactory的构建对象
        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
        //3使用构建者创建工厂对象SqlSessionFactory
        SqlSessionFactory factory = builder.build(in);
        SqlSession session = factory.openSession();

        IUserDao userDao = session.getMapper(IUserDao.class);
        List<User> users = userDao.findAll();
        for(User user : users)
            System.out.println(user);

        session.close();
        in.close();
    }
}
