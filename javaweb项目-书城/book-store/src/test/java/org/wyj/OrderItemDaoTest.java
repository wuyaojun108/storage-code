package org.wyj;

import com.bookstore.dao.OrderItemDao;
import com.bookstore.dao.impl.OrderItemDaoImpl;
import com.bookstore.beans.OrderItem;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

public class OrderItemDaoTest {
    private OrderItemDao orderItemDao = new OrderItemDaoImpl();

    @Test
    public void saveOrderItemTest(){
        OrderItem orderItem = new OrderItem(123456, "java-从入门到精通", 1, 100D, 100D
                , "123");
        orderItemDao.saveOrderItem(orderItem);

    }

    @Test
    public void queryOrderItemsByOrderIdTest(){
        List<OrderItem> orderItems = orderItemDao.queryOrderItemsByOrderId("1648515555444_106");
        for (OrderItem orderItem : orderItems) {
            System.out.println(orderItem);
        }
    }
}
