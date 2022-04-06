package org.wyj;

import com.bookstore.beans.Cart;
import com.bookstore.beans.CartItem;
import com.bookstore.service.OrderService;
import com.bookstore.service.impl.OrderServiceImpl;
import org.junit.Test;

public class OrderServiceTest {

    @Test
    public void createOrderTest(){
        OrderService orderService = new OrderServiceImpl();
        Cart cart = new Cart();
        cart.addItem(new CartItem(1, "aaa", 100.2, 2, 100.2 * 2));
        cart.addItem(new CartItem(1, "aaa", 100.2, 3, 100.2 * 3));
        cart.addItem(new CartItem(2, "bbb", 100.3, 5, 100.3 * 5));

        orderService.createOrder(cart, "100");


    }
}
