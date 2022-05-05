package org.wyj.blog.service;

import org.wyj.blog.dao.pojo.SysUser;

public interface SysUserService {

    SysUser findUserById(Long authorId);

    /**
     * 根据账户和密码查询用户信息
     * @param account 账户
     * @param password 密码
     * @return 用户实例
     */
    SysUser findUser(String account, String password);

    SysUser findUserByAccount(String account);

    void save(SysUser sysUser);
}
