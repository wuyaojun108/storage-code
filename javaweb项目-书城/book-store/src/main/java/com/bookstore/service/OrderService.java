package com.bookstore.service;

import com.bookstore.beans.Cart;
import com.bookstore.beans.Order;
import com.bookstore.beans.OrderItem;

import java.util.List;

public interface OrderService {
    String createOrder(Cart cart, String userId);

    List<Order> queryOrdersByUserId(int userId);

    List<OrderItem> queryOrderItemsByOrderId(String orderId);

    List<Order> queryAllOrders();

}
