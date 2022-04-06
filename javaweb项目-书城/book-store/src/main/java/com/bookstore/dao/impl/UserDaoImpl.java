package com.bookstore.dao.impl;

import com.bookstore.beans.User;
import com.bookstore.dao.UserDao;

public class UserDaoImpl extends BaseDao implements UserDao {
    @Override
    public User getUserByUsername(String username) {
        String sql = "SELECT `id`, `username`, `password`, `email` FROM t_user WHERE `username` = ?";
        User user = queryForOne(User.class, sql, username);
        // 无法成功填充ID字段，所以使用这种方法弥补一下
        String sql2 = "SELECT `id` FROM t_user WHERE `username` = ?";
        Integer id = (Integer) queryForSingleValue(sql2, username);
        user.setId(id);
        return user;
    }

    @Override
    public User getUserByUsernameAndPassword(String username, String password) {
        String sql = "SELECT `id` as userId, `username`, `password`, `email` FROM t_user WHERE username = ? AND password = ?";
        User user = queryForOne(User.class, sql, username, password);
        // 无法成功填充ID字段，所以使用这种方法弥补一下
        String sql2 = "SELECT `id` FROM t_user WHERE `username` = ?";
        Integer id = (Integer) queryForSingleValue(sql2, username);
        user.setId(id);

//        User user = null;
//        Connection connection = JDBCUtils.getConnection();
//        String sql = "SELECT id, username, password, email FROM t_user WHERE username = ? AND password = ?";
//        try (final PreparedStatement statement = connection.prepareStatement(sql)) {
//            statement.setString(1, username);
//            statement.setString(2, password);
//            final ResultSet resultSet = statement.executeQuery();
//            while (resultSet.next()) {
//                final String id = resultSet.getString("id");
//                final String username_1 = resultSet.getString("username");
//                final String password_1 = resultSet.getString("password");
//                final String email = resultSet.getString("email");
//                user = new User(Integer.parseInt(id), username_1, password_1, email);
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
        return user;
    }

    @Override
    public int saveUser(User user) {
        String sql = "INSERT INTO t_user values (null, ?, ?, ?)";
        return update(sql, user.getUsername(), user.getPassword(), user.getEmail());
    }
}
