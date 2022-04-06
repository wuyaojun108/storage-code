package com.bookstore.service.impl;

import com.bookstore.beans.*;
import com.bookstore.dao.BookDao;
import com.bookstore.dao.OrderDao;
import com.bookstore.dao.OrderItemDao;
import com.bookstore.dao.impl.BookDaoImpl;
import com.bookstore.dao.impl.OrderDaoImpl;
import com.bookstore.dao.impl.OrderItemDaoImpl;
import com.bookstore.service.OrderService;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class OrderServiceImpl implements OrderService {

    private OrderDao orderDao = new OrderDaoImpl();
    private OrderItemDao orderItemDao = new OrderItemDaoImpl();
    private BookDao bookDao = new BookDaoImpl();

    @Override
    public String createOrder(Cart cart, String userId) {
        String orderId = System.currentTimeMillis() + "_" + userId;
        Order order = new Order(orderId, new Date(), cart.getTotalPrice(), 0, userId);
        orderDao.saveOrder(order);
        for (Map.Entry<Integer, CartItem> entry : cart.getItems().entrySet()) {
            CartItem cartItem = entry.getValue();
            OrderItem orderItem = new OrderItem(null, cartItem.getName(), cartItem.getCount()
                    , cartItem.getPrice(), cartItem.getTotalPrice(), orderId);
            orderItemDao.saveOrderItem(orderItem);
            Book book = bookDao.queryBookById(cartItem.getId());
            book.setSales(book.getSales() + cartItem.getCount());
            book.setStock(book.getStock() - cartItem.getCount());
            bookDao.updateBook(book);
        }
        cart.clearCart();
        return orderId;
    }

    @Override
    public List<Order> queryOrdersByUserId(int userId) {
        return orderDao.queryOrdersByUserId(userId);
    }

    @Override
    public List<OrderItem> queryOrderItemsByOrderId(String orderId) {
        return orderItemDao.queryOrderItemsByOrderId(orderId);
    }

    @Override
    public List<Order> queryAllOrders() {
        return orderDao.queryAllOrders();
    }


}
