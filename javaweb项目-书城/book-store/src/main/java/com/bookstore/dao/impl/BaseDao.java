package com.bookstore.dao.impl;

import com.bookstore.utils.JDBCUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * 封装了所有DAO实现类需要的公共方法
 */
public abstract class BaseDao {

    /**
     * 通过QueryRunner类的实例来调用DBUtils类库中的API
     */
    private QueryRunner queryRunner = new QueryRunner();

    /**
     * 执行sql中的insert/update/delete语句
     *
     * @param sql：要被执行的sql
     * @param args：sql所需要的参数
     * @return 返回被影响的行数，如果执行失败，返回-1
     */
    public int update(String sql, Object... args) {
        Connection connection = JDBCUtils.getConnection();
        try {
            return queryRunner.update(connection, sql, args);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * 执行返回一条记录的sql语句
     *
     * @param type 目标对象的数据类型的类对象
     * @param sql  要执行的sql
     * @param args 如果sql是预处理语句，sql的参数
     * @param <T>  泛型，目标对象的数据类型
     * @return 返回封装了数据的对象
     */
    public <T> T queryForOne(Class<T> type, String sql, Object... args) {
        Connection connection = JDBCUtils.getConnection();
        try {
            return queryRunner.query(connection, sql, new BeanHandler<T>(type), args);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * 执行返回多条语句的sql
     *
     * @param type 目标对象的数据类型的类对象
     * @param sql  要执行的sql
     * @param args 如果sql是预处理语句，sql的参数
     * @param <T>  泛型，目标对象的数据类型
     * @return 返回封装了数据的集合
     */
    public <T> List<T> queryForList(Class<T> type, String sql, Object... args) {
        Connection connection = JDBCUtils.getConnection();
        try {
            return queryRunner.query(connection, sql, new BeanListHandler<T>(type), args);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * 执行返回单个值的sql
     *
     * @param sql  要执行的sql
     * @param args 如果sql是预处理语句，sql的参数
     * @return 返回封装了数据的对象
     */
    public Object queryForSingleValue(String sql, Object... args) {
        Connection connection = JDBCUtils.getConnection();
        try {
            return queryRunner.query(connection, sql, new ScalarHandler<>(), args);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
