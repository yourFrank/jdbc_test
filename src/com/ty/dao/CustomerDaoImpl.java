package com.ty.dao;

import com.ty.Customers;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.Date;
import java.util.List;

public class CustomerDaoImpl extends BaseDao<Customers> implements CustomerDao{

    //反射获取泛型类型

    @Override
    public void insert(Connection conn, Customers cust) {
        String sql = "insert into customers(name,email,birth) values(?,?,?)";
        getUpdate(conn,sql,cust.getName(),cust.getEmail(),cust.getBirth());
    }

    @Override
    public void deleteById(Connection conn, int id) {
        String sql = "delete from customers where id = ?";
        getUpdate(conn,sql,id);
    }

    @Override
    public void updateById(Connection conn, Customers cust, int id) {
        String sql = "update customers set name = ?, email = ?, birth = ? where id = ?";
        getUpdate(conn,sql,cust.getName(),cust.getEmail(),cust.getBirth(),id);
    }

    @Override
    public Customers getCustomerById(Connection conn, int id) {
        String sql = "select name, email, birth from customers where id = ?";
        List<Customers> list = getInstance(conn, sql, id);
        return list.size() > 0 ? list.get(0) :null;
    }

    @Override
    public List<Customers> getAll(Connection conn) {
        String sql = "select name, email, birth from customers";
        List<Customers> list = getInstance(conn, sql);
        return list;
    }

    @Override
    public Long getCount(Connection conn) {
        String sql = "select count(*) from customers";
        return  getValue(conn,sql);
    }
	
    @Override
    public Date getMaxBirth(Connection conn) {
        String sql = "select max(birth) from customers";
        return  getValue(conn,sql);
    }
}