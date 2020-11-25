package com.zuokun.mybatis.cfg;

/**
 * @author carlos
 * 用于封装执行的SQL语句和结果类型的全限定类名
 */
public class Mapper {

    private String queryString;//sql
    private String resultTye;//实体类的全限定类名

    public String getQueryString() {
        return queryString;
    }

    public void setQueryString(String queryString) {
        this.queryString = queryString;
    }

    public String getResultTye() {
        return resultTye;
    }

    public void setResultTye(String resultTye) {
        this.resultTye = resultTye;
    }
}
