package com.bookstore.utils;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * jdbc工具类：用于获取数据库中的连接和关闭数据库连接
 */
public class JDBCUtils {

    /**
     * 一个用于获取数据库连接的对象
     */
    private static DataSource dataSource;
    private static ThreadLocal<Connection> conns = new ThreadLocal<>();

    // 在静态代码块中初始化DataSource的对象，在类被加载时，dataSource对象就会被初始化
    static {
        Properties prop = new Properties();
        InputStream inputStream
                = JDBCUtils.class.getClassLoader().getResourceAsStream("druid.properties");
        try {
            prop.load(inputStream);
            dataSource = DruidDataSourceFactory.createDataSource(prop);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取数据库中的连接
     *
     * @return 返回数据库连接
     */
    public static Connection getConnection() {
        // 第一种实现
//        Connection conn = null;
//        try {
//            conn =  dataSource.getConnection();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return conn;

        // 第二种实现，确保同一个线程是使用同一个连接
        Connection conn = conns.get();
        if (conn == null) {
            try {
                conn = dataSource.getConnection();
                conns.set(conn);
                conn.setAutoCommit(false); // 设置为手动管理事务
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return conn;
    }

    /**
     * 提交事务并且关闭连接
     */
    public static void commitAndClose() {
        Connection connection = conns.get();
        if (connection != null) {
            try {
                connection.commit();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    connection.close();
                    conns.remove();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 回滚事务并且关闭连接
     */
    public static void rollbackAndClose() {
        Connection connection = conns.get();
        if (connection != null) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    connection.close();
                    conns.remove();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * 关闭数据库连接
     *
     * @param connection
     */
    public static void close(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
