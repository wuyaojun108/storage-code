package com.bookstore.web;

import com.bookstore.beans.Cart;
import com.bookstore.beans.Order;
import com.bookstore.beans.OrderItem;
import com.bookstore.beans.User;
import com.bookstore.service.OrderService;
import com.bookstore.service.impl.OrderServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class OrderServlet extends BaseServlet {

    private OrderService orderService = new OrderServiceImpl();

    /**
     * 创建订单
     */
    public void createOrder(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        HttpSession session = req.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        User user = (User) session.getAttribute("user");
        if (user == null) {
            resp.sendRedirect("/book-store/pages/user/login.jsp");
            return;
        }
        String orderId = orderService.createOrder(cart, user.getId().toString());

        session.setAttribute("orderId", orderId);
        resp.sendRedirect("/book-store/pages/cart/checkout.jsp");
    }

    /**
     * 根据用户名查询订单
     */
    public void queryOrdersByUserId(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();
        String userId = req.getParameter("userId");
        List<Order> orders = orderService.queryOrdersByUserId(Integer.parseInt(userId));
        session.setAttribute("orders", orders);
        resp.sendRedirect("/book-store/pages/order/order.jsp");
    }

    /**
     * 根据订单ID查询订单项
     */
    public void queryOrderItemsByOrderId(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();
        String orderId = req.getParameter("orderId");
        List<OrderItem> orderItems = orderService.queryOrderItemsByOrderId(orderId);
        session.setAttribute("orderItems", orderItems);
        resp.sendRedirect("/book-store/pages/order/order_message.jsp");
    }

    /**
     * 查询所有订单，用于管理员的订单管理
     */
    public void queryAllOrders(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();
        String userId = req.getParameter("userId");
        List<Order> orders = orderService.queryAllOrders();
        session.setAttribute("allOrders", orders);
        resp.sendRedirect("/book-store/pages/manager/admin_order_manager.jsp");
    }

}
