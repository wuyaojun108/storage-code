package org.wyj.blog.dao;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.wyj.blog.BaseTest;
import org.wyj.blog.dao.mapper.SysUserMapper;
import org.wyj.blog.dao.pojo.SysUser;

import java.util.List;

public class SysUserMapperTest extends BaseTest {
    // SysUserMapper测试
    @Autowired
    private SysUserMapper sysUserMapper;

    @Test
    public void sysUserMapperTest() {
        List<SysUser> sysUsers = sysUserMapper.selectList(null);
        sysUsers.forEach(System.out::println);
    }
}
