package com.bookstore.dao.impl;

import com.bookstore.beans.OrderItem;
import com.bookstore.dao.OrderItemDao;

import java.util.List;

public class OrderItemDaoImpl extends BaseDao implements OrderItemDao {
    @Override
    public int saveOrderItem(OrderItem orderItem) {
        String sql = "insert into t_order_item values (?, ?, ?, ?, ?, ?)";
        return update(sql, orderItem.getId(), orderItem.getName(), orderItem.getCount(), orderItem.getPrice()
                , orderItem.getTotalPrice(), orderItem.getOrderId());
    }

    @Override
    public List<OrderItem> queryOrderItemsByOrderId(String orderId) {
        String sql = "SELECT id, name, count, price, total_price as totalPrice, order_id orderId" +
                " FROM t_order_item where order_id = ?";
        return queryForList(OrderItem.class, sql, orderId);
    }
}
