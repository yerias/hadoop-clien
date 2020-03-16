package com.tunan.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class jdbcUtil {
    public static String url = "jdbc:mysql://hadoop:3306/access_dw?useUnicode=true&characterEncoding=utf-8";
    public static String driver = "com.mysql.jdbc.Driver";
    public static String username = "root";
    public static String password = "root";
    public static Connection connection;
    public static PreparedStatement ps;
    public static ResultSet resultSet;

    private static Logger logger = LoggerFactory.getLogger(jdbcUtil.class);

    //创建连接的方法
    public static void getConnection() {
        //加载驱动程序包
        try {
            Class.forName(driver);
            //获取连接对象
            connection = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            e.printStackTrace();
        }

        logger.info("连接创建成功！");
    }

    //执行增删改的方法
    public static int executeUpdate(String sql, Object... objects) {
        try {
            ps = connection.prepareStatement(sql);
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    //执行查询的方法
    public static ResultSet exexuteQuery(String sql, Object... objects) {
        try {
            ps = connection.prepareStatement(sql);
            for (int i = 0; i < objects.length; i++) {
                ps.setObject(i + 1, objects[i]);
            }
            resultSet = ps.executeQuery();
            return resultSet;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    //释放资源的方法
    public static void closeAll() {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}