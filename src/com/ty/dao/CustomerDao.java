package com.ty.dao;

import com.ty.Customers;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;

public interface CustomerDao {
    /**
     * 将cust对象添加到数据库之中
     * @param conn
     * @param cust
     */
    void insert(Connection conn, Customers cust);

    /**
     * 根据id号删除数据库中customer数据
     * @param conn
     * @param id
     */
    void deleteById(Connection conn, int id);

    /**
     * 根据id号修改数据库中customer数据
     * @param conn
     * @param cust
     * @param id
     */
    void updateById(Connection conn,Customers cust, int id);

    /**
     * 根据id号查詢数据库中customer数据
     * @param conn
     * @param id
     */
    Customers getCustomerById(Connection conn, int id);

    /**
     * 查询数据库customer表中所有的数据
     * @param conn
     */
    List<Customers> getAll(Connection conn);

    /**
     * 查询数据库customer表中所有的行数
     * @param conn
     * @return
     */
    Object getCount(Connection conn);

    /**
     * 查询数据库customer表中年龄最大的生日
     * @param conn
     */
    Date getMaxBirth(Connection conn);
}