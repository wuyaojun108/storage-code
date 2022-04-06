package org.wyj;

import com.bookstore.dao.OrderDao;
import com.bookstore.dao.impl.OrderDaoImpl;
import com.bookstore.beans.Order;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class OrderDaoTest {
    private OrderDao orderDao = new OrderDaoImpl();

    @Test
    public void saveOrderTest(){
        Order order = new Order("123", new Date(), 100d, 0, "106");
        orderDao.saveOrder(order);

    }

    @Test
    public void queryOrderByUserIdTest(){
        List<Order> orders = orderDao.queryOrdersByUserId(106);
        for (Order order : orders) {
            System.out.println(order);
        }
    }

    @Test
    public void queryAllOrdersTest(){
        List<Order> orders = orderDao.queryAllOrders();
        for (Order order : orders) {
            System.out.println(order);
        }
    }
}
