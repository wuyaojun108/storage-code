package com.bookstore.dao;

import com.bookstore.beans.OrderItem;

import java.util.List;

public interface OrderItemDao {

    /**
     * 保存订单项
     */
    int saveOrderItem(OrderItem orderItem);

    /**
     * 查询一个订单ID的所有订单项
     */
    List<OrderItem> queryOrderItemsByOrderId(String orderId);


}
