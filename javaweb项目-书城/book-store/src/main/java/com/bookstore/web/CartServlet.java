package com.bookstore.web;

import com.bookstore.beans.Book;
import com.bookstore.beans.Cart;
import com.bookstore.beans.CartItem;
import com.bookstore.beans.User;
import com.bookstore.service.BookService;
import com.bookstore.service.impl.BookServiceImpl;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class CartServlet extends BaseServlet {

    private BookService bookService = new BookServiceImpl();
    private Logger logger = Logger.getLogger(CartServlet.class);

    // 实现修改购物车中商品数量的功能
    public void updateCount(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();
        String referer = req.getHeader("referer");
        User user = (User) session.getAttribute("user");
        int bookId = Integer.parseInt(req.getParameter("bookId"));
        int count = Integer.parseInt(req.getParameter("count"));
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart != null) {
            cart.updateCount(bookId, count);
        }

        resp.sendRedirect(referer);

        logger.info(user.getUsername() + " 修改了购物车中 " + bookId + " 的数量为 " + count);
    }

    // 实现删除购物车中的商品项功能
    public void deleteItem(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String referer = req.getHeader("Referer");
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        Cart cart = (Cart) session.getAttribute("cart");
        int bookId = 0;
        if (cart != null) {
            bookId = Integer.parseInt(req.getParameter("id"));
            cart.deleteItem(bookId);
            session.setAttribute("cart", cart);
        }
        resp.sendRedirect("/book-store/pages/cart/cart.jsp");

        logger.info(user.getUsername() + " 删除了购物车中的 " + bookId);
    }

    // 实现清空购物车功能
    public void clearCart(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String referer = req.getHeader("Referer");
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart != null) {
            cart.clearCart();
        }
        session.setAttribute("cart", cart);
        session.removeAttribute("lastAddedBook");

        logger.info(user.getUsername() + " 清空了他的购物车");
    }

    /*
        // 使用ajax实现添加图书到购物车的功能
        public void ajaxAddBookToItem(HttpServletRequest req, HttpServletResponse resp) throws IOException {
            // 设置响应的样式
            resp.setContentType("html/text; charset=UTF-8");

            // 需要从session域中存取数据
            HttpSession session = req.getSession();
            // 获取用户信息，前端页面已经对username作过非空判断
            String username = req.getParameter("username");

            // 获取要加入购物车的图书的ID，根据id，从数据库中取出图书信息
            int bookId = Integer.parseInt(req.getParameter("bookId"));
            Book book = bookService.queryBookById(bookId);

            // 从会话域中取出购物车对象，如果为空，创建一个
            Cart cart = (Cart)session.getAttribute("cart");
            if(cart == null){
                cart = new Cart();
            }
            // 执行添加购物车功能
            cart.addItem(new CartItem(bookId, book.getName(), book.getPrice(), 1, book.getPrice()));
            // 把购物车对象重新放回session域
            session.setAttribute("cart", cart);
            session.setAttribute("lastAddedBook", book.getName());

            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("totalCount", cart.getTotalCount());
            resultMap.put("lastAddedBook", book.getName());
            Gson gson = new Gson();
            String resultMapJson = gson.toJson(resultMap);
            resp.getWriter().write(resultMapJson);

            logger.info(username + "将 " + book.getName() + " 添加到购物车中");
        }
        */
    // 实现加入购物车功能
    public void addBookToItem(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // 需要从session域中存取数据
        HttpSession session = req.getSession();
        // 获取用户信息，前端页面已经对username作过非空判断
        String username = req.getParameter("username");

        // 获取要加入购物车的图书的ID，根据id，从数据库中取出图书信息
        int bookId = Integer.parseInt(req.getParameter("bookId"));
        Book book = bookService.queryBookById(bookId);

        // 从会话域中取出购物车对象，如果为空，创建一个
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
        }
        // 执行添加购物车功能
        cart.addItem(new CartItem(bookId, book.getName(), book.getPrice(), 1, book.getPrice()));
        // 把购物车对象重新放回session域
        session.setAttribute("cart", cart);
        session.setAttribute("lastAddedBook", book.getName());

        // 根据referer字段，重定向回原来的页面
        String referer = req.getHeader("Referer");
        resp.sendRedirect(referer);

        logger.info(username + "将 " + book.getName() + " 添加到购物车中");
    }
}
