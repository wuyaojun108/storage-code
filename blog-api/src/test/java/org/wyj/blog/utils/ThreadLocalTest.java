package org.wyj.blog.utils;

public class ThreadLocalTest {

    private static final ThreadLocal<String> threadLocal = new ThreadLocal<>();

    public static void main(String[] args) {


        for(int i = 0; i < 10; i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    ThreadLocal<String> local = new ThreadLocal<String>();
                    local.set("aa ");
                    String name = Thread.currentThread().getName();
                    System.out.printf("%s: %s\n", name, local.get());
                    local.remove();
                }
            }).start();
        }

        threadLocal.remove();

    }
}
