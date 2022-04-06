package com.bookstore.beans;

import java.util.HashMap;
import java.util.Map;

/**
 * 购物车对象
 */
public class Cart {
    private Map<Integer, CartItem> items;

    // 在构造购物车对象的时候实例化用于存储商品项的集合
    public Cart() {
        this.items = new HashMap<>();
    }

    /**
     * 获取购物车中商品的总数量
     *
     * @return 返回商品的总数量
     */
    public Integer getTotalCount() {
        Integer totalCount = 0;
        for (Map.Entry<Integer, CartItem> entry : items.entrySet()) {
            totalCount += entry.getValue().getCount();
        }
        return totalCount;
    }

    /**
     * 获取购物车中商品的总金额
     *
     * @return 返回商品的总金额
     */
    public Double getTotalPrice() {
        Double totalPrice = 0.0;
        for (Map.Entry<Integer, CartItem> entry : items.entrySet()) {
            totalPrice += entry.getValue().getTotalPrice();
        }
        return totalPrice;
    }

    /**
     * 返回购物车中商品项的集合
     *
     * @return
     */
    public Map<Integer, CartItem> getItems() {
        return items;
    }


    /**
     * 向购物车中添加商品。
     *
     * @param item 商品项
     */
    public void addItem(CartItem item) {
        // 根据要添加的商品的ID在购物车中进行查找，如果能够查找出一个商品，说明这个商品之前已经被添加到购物车
        // 中，接下来只要做数量上的累加即可；如果没于查找到商品，则向购物车中添加该商品
        CartItem cartItem = items.get(item.getId());
        if (cartItem == null) {
            // 购物车中没有该商品
            items.put(item.getId(), item);
        } else {
            // 购物车中有该商品，只需要设置该商品的购买数量即可。
            cartItem.setCount(cartItem.getCount() + item.getCount());
            cartItem.setTotalPrice(cartItem.getPrice() * cartItem.getCount());
        }
    }

    /**
     * 删除商品项
     *
     * @param id 要删除的商品的ID
     */
    public void deleteItem(Integer id) {
        items.remove(id);
    }

    /**
     * 清空购物车
     */
    public void clearCart() {
        items.clear();
    }

    /**
     * 修改购物车中某件商品的数量
     *
     * @param id    要修改数量的商品的ID
     * @param count 修改后的数量
     */
    public void updateCount(Integer id, Integer count) {
        CartItem cartItem = items.get(id);
        if (cartItem != null) {
            cartItem.setCount(count);
        }
    }

    @Override
    public String toString() {
        return "Cart{" +
                "totalCount=" + getTotalCount() +
                ", totalPrice=" + getTotalPrice() +
                ", items=" + items +
                '}';
    }
}
