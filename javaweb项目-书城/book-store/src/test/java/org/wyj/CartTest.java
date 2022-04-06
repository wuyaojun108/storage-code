package org.wyj;

import com.bookstore.beans.Cart;
import com.bookstore.beans.CartItem;
import org.junit.Test;

public class CartTest {

    @Test
    public void addItemTest(){
        Cart cart = new Cart();
        cart.addItem(new CartItem(1, "aaa", 100.2, 2, 100.2 * 2));
        cart.addItem(new CartItem(1, "aaa", 100.2, 3, 100.2 * 3));
        cart.addItem(new CartItem(2, "bbb", 100.3, 5, 100.3 * 5));

        System.out.println(cart);
    }


    @Test
    public void clearTest(){
        Cart cart = new Cart();
        cart.addItem(new CartItem(1, "aaa", 100.2, 2, 100.2 * 2));
        cart.addItem(new CartItem(1, "aaa", 100.2, 3, 100.2 * 3));
        cart.addItem(new CartItem(2, "bbb", 100.3, 5, 100.3 * 5));

        System.out.println(cart);

        cart.deleteItem(1);
        System.out.println(cart);

        cart.clearCart();
        System.out.println(cart);
    }

    @Test
    public void updateCountTest(){
        Cart cart = new Cart();
        cart.addItem(new CartItem(1, "aaa", 100.2, 2, 100.2 * 2));
        cart.addItem(new CartItem(1, "aaa", 100.2, 3, 100.2 * 3));
        cart.addItem(new CartItem(2, "bbb", 100.3, 5, 100.3 * 5));
        cart.updateCount(3, 20);
        System.out.println(cart);
    }

}
