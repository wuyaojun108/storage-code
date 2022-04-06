package org.wyj;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

public class JDBCUtilsTest {

    /**
     * 测试数据库连接池Druid的功能
     */
    @org.junit.Test
    public void druidTest() {
        Properties properties = new Properties();
        InputStream druidResources
                = JDBCUtilsTest.class.getClassLoader().getResourceAsStream("druid.properties");

        try {
            properties.load(druidResources);
            DataSource dataSource = DruidDataSourceFactory.createDataSource(properties);
            Connection connection = dataSource.getConnection();
            String sql = "select * from bookstore.user";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                String email = resultSet.getString("email");
                System.out.println(id + "\t" + username + "\t" + password + "\t" + email);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



}
