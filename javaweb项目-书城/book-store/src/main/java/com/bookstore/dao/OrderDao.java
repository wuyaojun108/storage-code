package com.bookstore.dao;

import com.bookstore.beans.Order;

import java.util.List;

public interface OrderDao {

    /**
     * 保存订单
     */
    void saveOrder(Order order);

    /**
     * 根据用户ID查询订单
     */
    List<Order> queryOrdersByUserId(int userId);

    /**
     * 查询所有订单
     */
    List<Order> queryAllOrders();
}
