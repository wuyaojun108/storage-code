package org.wyj.blog.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.AllArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.wyj.blog.BaseTest;
import org.wyj.blog.dao.mapper.SysUserMapper;
import org.wyj.blog.dao.pojo.SysUser;

public class SysUserServiceTest extends BaseTest {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Test
    public void getUserByIdTest(){
        SysUser sysUser = sysUserService.findUserById(1L);
        System.out.println("sysUser = " + sysUser);
    }


    @Test
    public void findUserByAccountTest(){
        SysUser admin = sysUserService.findUserByAccount("admin");
        System.out.println("admin = " + admin);
    }

    @Test
    public void saveTest(){
        Long maxId = sysUserMapper.getMaxId();
        String salt = "orgwjt@#$%";
        SysUser sysUser = new SysUser();
        sysUser.setId(maxId + 1);
        sysUser.setNickname("王者荣耀");
        sysUser.setAccount("user1");
        sysUser.setPassword(DigestUtils.md5Hex("123456" + salt));
        sysUser.setCreateDate(System.currentTimeMillis());
        sysUser.setLastLogin(System.currentTimeMillis());
        sysUser.setAdmin(0);
        sysUser.setDeleted(0);
        sysUser.setSalt(salt);
        sysUser.setStatus("");
        sysUser.setEmail("");
        sysUserService.save(sysUser);
    }

}
