package org.wyj;

import com.bookstore.beans.User;
import com.bookstore.service.UserService;
import com.bookstore.service.impl.UserServiceImpl;

public class UserServiceTest {

    @org.junit.Test
    public void userServiceTest1() {
        UserService userService = new UserServiceImpl();
        User root = userService.login(new User(null, "root", "123456", null));
        if (root == null) {
            System.out.println("登录失败");
        } else {
            System.out.println("登录成功");
        }
    }

    @org.junit.Test
    public void userServiceTest2() {
        UserService userService = new UserServiceImpl();
        userService.registerUser(new User(null, "user3", "123456", "user3@qq.com"));
    }

    @org.junit.Test
    public void userServiceTest3() {
        UserService userService = new UserServiceImpl();
        System.out.println("userService.existsUsername(\"root\") = " + userService.existsUsername("root"));
    }

}
