package com.bookstore.dao.impl;

import com.bookstore.beans.Order;
import com.bookstore.dao.OrderDao;

import java.util.List;

public class OrderDaoImpl extends BaseDao implements OrderDao {
    @Override
    public void saveOrder(Order order) {
        String sql = "insert into t_order values(?, ?, ?, ?, ?)";
        update(sql, order.getOrderId(), order.getCreateTime(), order.getPrice()
                , order.getStatus(), order.getUserId());
    }

    @Override
    public List<Order> queryOrdersByUserId(int userId) {
        String sql = "SELECT order_id as orderId, create_time as createTime, price, status" +
                ", user_id as userId FROM t_order WHERE user_id = ? ORDER BY createTime DESC";
        return queryForList(Order.class, sql, userId);
    }

    @Override
    public List<Order> queryAllOrders() {
        String sql = "SELECT order_id as orderId, create_time as createTime, price, status" +
                ", user_id as userId FROM t_order ORDER BY user_id, createTime DESC";
        return queryForList(Order.class, sql);
    }


}
