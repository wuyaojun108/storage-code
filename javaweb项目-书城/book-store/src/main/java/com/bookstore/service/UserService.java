package com.bookstore.service;

import com.bookstore.beans.User;

public interface UserService {

    /**
     * 用户登录功能
     *
     * @param user 传入的对象中只需要有用户名和密码即可
     * @return 如果返回一个User对象，说明登录成功，返回null，说明登录失败
     */
    User login(User user);

    /**
     * 用户注册功能
     *
     * @param user 需要注册的用户，用户id可以为null，因为用户id在数据库中是自增的
     * @return 返回数据库中影响的行数
     */
    int registerUser(User user);

    /**
     * 判断指定的用户名在数据库中是否存在
     *
     * @param username
     * @return 如果返回true，证明用户名存在于数据库中
     */
    boolean existsUsername(String username);

}
