package org.wyj;

import com.bookstore.dao.UserDao;
import com.bookstore.dao.impl.BaseDao;
import com.bookstore.dao.impl.UserDaoImpl;
import com.bookstore.beans.User;
import com.bookstore.utils.JDBCUtils;
import org.junit.Test;

public class UserDaoTest {
    private UserDao userDao = new UserDaoImpl();
    private BaseDao baseDao = new UserDaoImpl();

    @Test
    public void getUserById(){
        String sql = "select id from t_user where username = ?";
        Integer id = (Integer) baseDao.queryForSingleValue(sql, "wuyaojun");
        System.out.println("id = " + id);

    }


    @Test
    public void getUserByUsernameTest() {
        User admin = userDao.getUserByUsername("wuyaojun");
        JDBCUtils.commitAndClose();
        System.out.println(admin);
    }

    @Test
    public void getUserByUsernameAndPasswordTest() {
        User user = userDao.getUserByUsernameAndPassword("wuyaojun", "1qaz!QAZ43!!");
        JDBCUtils.commitAndClose();
        System.out.println(user);
    }

    @Test
    public void saveUserTest() {
        int numberOfRowsAffected
                = userDao.saveUser(new User(null,
                "root",
                "123456",
                "root@qq.com"));
        JDBCUtils.commitAndClose();
        System.out.println(numberOfRowsAffected);
    }
}
