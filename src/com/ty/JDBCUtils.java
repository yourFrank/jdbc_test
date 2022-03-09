package com.ty;

import java.sql.*;

public class JDBCUtils {


    public static Connection getConnection() throws Exception{
        String url = "jdbc:mysql://101.43.146.252:3306/test";
        String user = "root";
        String password = "5251021";
        String driverName = "com.mysql.jdbc.Driver";
        Class.forName(driverName);
        //通过反射加载驱动类
        //连接数据库
        Connection con = DriverManager.getConnection(url, user, password);
        return con;
    }

    public static void closeResource(Connection con, Statement ps) {
        try {
            if (ps != null)
                ps.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            if (con != null)
                con.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void closeResource(Connection con, Statement ps, ResultSet rs) {
        try {
            if (ps != null)
                ps.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            if (con != null)
                con.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        try {
            if (rs != null)
                con.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
