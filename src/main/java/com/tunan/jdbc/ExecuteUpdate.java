package com.tunan.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @description:
 * @author: tunan
 * @create: 2020-03-09 14:02
 * @since: 1.0.0
 **/
public class ExecuteUpdate {
    static Connection connection;
    private static Logger logger = LoggerFactory.getLogger(ExecuteUpdate.class);

    public static void main(String[] args) {
        jdbcUtil.getConnection();

        //String table = args[0];
        String table = "dws_advertising_app_sum_day";

        String sql = "delete from " + table;
        jdbcUtil.executeUpdate(sql);
        logger.info(table + " :删除成功");
        jdbcUtil.closeAll();
    }
}

