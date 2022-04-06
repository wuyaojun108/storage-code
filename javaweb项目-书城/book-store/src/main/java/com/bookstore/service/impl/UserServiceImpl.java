package com.bookstore.service.impl;

import com.bookstore.beans.User;
import com.bookstore.dao.UserDao;
import com.bookstore.dao.impl.UserDaoImpl;
import com.bookstore.service.UserService;

public class UserServiceImpl implements UserService {

    private UserDao userDao = new UserDaoImpl();

    @Override
    public User login(User user) {
        return userDao.getUserByUsernameAndPassword(user.getUsername(), user.getPassword());
    }

    @Override
    public int registerUser(User user) {
        return userDao.saveUser(user);
    }

    @Override
    public boolean existsUsername(String username) {
        return userDao.getUserByUsername(username) != null;
    }
}
