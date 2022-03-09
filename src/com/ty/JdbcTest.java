package com.ty;


import org.junit.Test;

import java.lang.reflect.Field;
import java.sql.*;

public class JdbcTest {
    @Test
    public void test1() throws Exception {
        String url = "jdbc:mysql://101.43.146.252:3306/test";
        String user = "root";
        String password = "5251021";
        String driverName = "com.mysql.jdbc.Driver";
        Class.forName(driverName);
        Connection conn = DriverManager.getConnection(url, user, password);
        System.out.println(conn);
    }

    @Test
    public void test2(){
        String sql="select id,name,email,birth from `customers` where id=?";
        Customers customers = queryForCommons(Customers.class, sql, 1);
        System.out.println(customers);
    }

    @Test
    public void tx1() throws Exception {
            Connection connection = JDBCUtils.getConnection();
            //设置数据库的隔离级别
            connection.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
            //获取数据库的隔离级别
            int transactionIsolation = connection.getTransactionIsolation();
            System.out.println(transactionIsolation);
            String sql="select user,password,balance from user_table where user = ? ";
            //开启手动提交事务
            connection.setAutoCommit(false);
            User cc = queryForTx(connection, User.class, sql, "CC");
            System.out.println(cc);

    }
    @Test
    public void tx2() throws Exception {
        String sql="update user_table set balance = 5000 where user = ?";
        Connection connection = JDBCUtils.getConnection();
        //开启手动提交事务
        connection.setAutoCommit(false);
        update(connection,sql,"CC");
        connection.commit();
        Thread.sleep(15000);
    }

    //通用的增、删、改操作（体现一：增、删、改 ； 体现二：针对于不同的表）
    public void update(Connection conn,String sql,Object ... args) throws Exception {
        PreparedStatement ps = null;
            //2.获取PreparedStatement的实例 (或：预编译sql语句)
            ps = conn.prepareStatement(sql);
            //3.填充占位符 ，注意这里是i+1, java都是从1开始
            for(int i = 0;i < args.length;i++){
                ps.setObject(i + 1, args[i]);
            }
            //4.执行sql语句
            ps.execute();

    }
    public <T> T queryForCommons(Class<T> clazz, String sql, Object... args){
        Connection connection = null;
        try {
            connection = JDBCUtils.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            //封装参数
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i+1,args[i]);
            }
            //执行查询方法
            ResultSet rs = ps.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();
            T t = clazz.newInstance();
            if (rs.next()){
                //得到查询的列数
                for (int i = 0; i < columnCount; i++) {
                    String columnLabel = rsmd.getColumnLabel(i + 1);
                    Field field = Customers.class.getDeclaredField(columnLabel);

                    field.setAccessible(true);
                    Object object = rs.getObject(i+1);
                    field.set(t,object);

                }
            }
            return t;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                JDBCUtils.getConnection();
            } catch (Exception e) {

                e.printStackTrace();
            }
        }
        return null;
    }

    public <T> T queryForTx(Connection connection,Class<T> clazz, String sql, Object... args) throws Exception {

            PreparedStatement ps = connection.prepareStatement(sql);
            //封装参数
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i+1,args[i]);
            }
            //执行查询方法
            ResultSet rs = ps.executeQuery();

            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();
            T t = clazz.newInstance();
            if (rs.next()){
                //得到查询的列数
                for (int i = 0; i < columnCount; i++) {
                    String columnLabel = rsmd.getColumnLabel(i + 1);
                    Field field = t.getClass().getDeclaredField(columnLabel);

                    field.setAccessible(true);
                    Object object = rs.getObject(i+1);
                    field.set(t,object);

                }
            }
            return t;
    }


}
