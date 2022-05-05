package org.wyj.blog;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;

import java.util.Arrays;

public class MyTest {

    @Test
    public void timestamp(){
        System.out.println("System.currentTimeMillis() = " + System.currentTimeMillis());
    }

    @Test
    public void md5Test(){
        String salt = "orgwjt@#$%";
        String password = "admin";
        String s = DigestUtils.md5Hex(password + salt);
        System.out.println("s = " + s);
    }

    public static void main(String[] args) {
        int [] arr = {1, 2, 3};
        System.out.println("arr = " + Arrays.toString(arr));
        arr1(arr);
        System.out.println("arr = " + Arrays.toString(arr));

    }

    public static void arr1(int[] arr){
        arr = null;
    }
}
