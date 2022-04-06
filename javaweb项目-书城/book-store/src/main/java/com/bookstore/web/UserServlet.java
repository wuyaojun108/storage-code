package com.bookstore.web;

import com.bookstore.beans.User;
import com.bookstore.service.UserService;
import com.bookstore.service.impl.UserServiceImpl;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 用户模块的servlet
 */
public class UserServlet extends BaseServlet {

    private UserService userService = new UserServiceImpl();
    private Logger logger = Logger.getLogger(UserServlet.class);


    // 注册
    protected void register(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession();
        resp.setContentType("html/text; charset=UTF-8");
        PrintWriter writer = resp.getWriter();

        // 解析请求
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String email = req.getParameter("email");
        String code = req.getParameter("code");
/*
        谷歌验证码 kaptcha 使用步骤
          导入依赖
          配置servlet，生成验证码: KaptchaServlet
          在表单中使用img标签去显示验证码
          在服务器获取谷歌生成的验证码和客户端发送过来的验证码 KAPTCHA_SESSION_KEY
*/
        String verificationCode = (String) session.getAttribute("KAPTCHA_SESSION_KEY");
        if (code != null && !code.equals(verificationCode)) {
            // 验证码错误
            writer.write("验证码[" + code + "]错误");
        } else if (userService.existsUsername(username)) {
            // 注册失败，用户名已存在于数据库中
            writer.write("用户名存在于数据库中");
        } else {
            // 注册成功
            // 移除会话中的验证码
            session.removeAttribute("KAPTCHA_SESSION_KEY");
            User user = new User(null, username, password, email);
            userService.registerUser(user);
            writer.write("注册成功，请前往登录页面");

            logger.info(user.getUsername() + " 注册成功");
        }
    }

    // 判断用户输入的用户名是否存在
    public void isUsernameExists(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String username = req.getParameter("username");
        resp.setContentType("html/text; charset=UTF-8");
        PrintWriter writer = resp.getWriter();
        if (userService.existsUsername(username)) {
            writer.write("用户名已存在");
        } else {
            writer.write("用户名不存在");
        }

        logger.info(username + ": 调用isUsernameExists方法，判断用户名是否存在");
    }

    // 处理登录请求
    public void login(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        HttpSession session = req.getSession();
        resp.setContentType("html/text; charset=UTF-8");
        PrintWriter writer = resp.getWriter();
        // 解析请求中的参数
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        // 使用cookie在前端页面回填用户名时遇到一个问题，这个功能的本意是让用户在第一次打开页面时，
        // 浏览器可以根据之前的cookie，自动在表单中输入用户名，但是有缓存，如果用户名前后不一致，会有bug
        // resp.addCookie(new Cookie("username", username));

        // 调用service层的登录方法，判断用户名和密码是否正确
        User loginUser = userService.login(new User(null, username, password, null));
        // 如果返回的对象不为null，表示用户名和密码正确，登录成功
        if (loginUser == null) {
            writer.write("用户名或密码不正确");
        } else {
            // 把用户信息保存到session域中，方便在前台展示
            session.setAttribute("user", loginUser);
            writer.write("登录成功");
            logger.info(loginUser.getUsername() + " 登录成功");
        }
    }

    // 退出登录
    public void logout(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        session.removeAttribute("user");
        session.removeAttribute("lastAddedBook");
        session.removeAttribute("cart");
        resp.sendRedirect("/book-store/index.jsp");

        // 记录日志
        logger.info(user.getUsername() + " 退出登录");
    }


}
