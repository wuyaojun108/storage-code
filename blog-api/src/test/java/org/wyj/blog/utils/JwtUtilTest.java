package org.wyj.blog.utils;

import org.junit.Test;

import java.util.Map;

public class JwtUtilTest{

    @Test
    public void createTokenTest(){
        String token = JWTUtil.createToken(1648416405L);
        System.out.println("token = " + token);
    }

    @Test
    public void checkTokenTest(){
        // "eyJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2NTAzMzMyMjcsInVzZXJJZCI6MTAwMCwiaWF0IjoxNjUwMjQ2ODI3fQ.tB7GYHi3uaz7kG8wT5xTeikhG-e9GKGidPgVQB8XeIY";
        String token = JWTUtil.createToken(1000L);
        Map<String, Object> o = (Map<String, Object>)JWTUtil.checkToken(token);
        assert o != null;
        System.out.println("o = " + o.get("userId"));
    }

}
