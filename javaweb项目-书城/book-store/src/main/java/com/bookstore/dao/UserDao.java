package com.bookstore.dao;

import com.bookstore.beans.User;

public interface UserDao {

    /**
     * 根据用户名来获取用户信息
     *
     * @param username 用户名
     * @return 返回一个User对象，如果对象为空，表示用户名不存在于数据库中
     */
    User getUserByUsername(String username);

    /**
     * 根据用户名和密码来获取用户信息
     *
     * @param username 用户名
     * @param password 密码
     * @return 返回一个User对象，如果对象为空，表示用户名或密码为空
     */
    User getUserByUsernameAndPassword(String username, String password);

    /**
     * 把User对象中的数据保存到数据库中
     *
     * @param user User对象
     * @return 返回数据库中影响的行数，如果返回-1，表示执行失败
     */
    int saveUser(User user);
}
