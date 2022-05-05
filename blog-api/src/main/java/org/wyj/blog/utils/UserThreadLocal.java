package org.wyj.blog.utils;

public class UserThreadLocal {
    private static final ThreadLocal<Object> LOCAL = new ThreadLocal<>();

    private UserThreadLocal() { }

    public static void put(Object object) {
        LOCAL.set(object);
    }

    public static Object get() {
        return LOCAL.get();
    }

    public static void remove() {
        LOCAL.remove();
    }
}
