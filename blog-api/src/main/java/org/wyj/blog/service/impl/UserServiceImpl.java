package org.wyj.blog.service.impl;

import com.alibaba.fastjson.JSON;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.wyj.blog.dao.mapper.SysUserMapper;
import org.wyj.blog.dao.pojo.SysUser;
import org.wyj.blog.service.SysUserService;
import org.wyj.blog.service.UserService;
import org.wyj.blog.utils.JWTUtil;
import org.wyj.blog.vo.ErrorCode;
import org.wyj.blog.vo.LoginUserVo;
import org.wyj.blog.vo.RegisterUserVo;
import org.wyj.blog.vo.Result;
import org.wyj.blog.vo.params.LoginParams;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements UserService {

    /**
     * 加密盐
     */
    private static final String salt = "orgwjt@#$%";
    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 实现登录功能
     * 1. 校验用户名和密码是否为空，然后根据用户名和密码去user表中查询
     * 2. 如果用户不存在，登录失败
     * 3. 如果存在，使用jwt，生成token，返回给客户端
     * 4. token作为键放入redis，值是用户信息，设置过期时间。登录认证的时候，
     *    先认证token字符串是否正确，然后去redis认证是否存在
     * @param loginParams 用户登录时携带的参数
     * @return 登录结果
     */
    @Override
    @Transactional
    public Result login(LoginParams loginParams) {
        String account = loginParams.getAccount();
        String password = loginParams.getPassword();
        // 用户提供的用户名和密码为空
        if (account == null || password == null
                || "".equals(account) || "".equals(password)) {
            return Result.fail(ErrorCode.PARAMS_ERROR.getCode()
                    , ErrorCode.PARAMS_ERROR.getMsg());
        }
        // 数据库中的密码是加密的，所以需要加密后再进行比对
        password = DigestUtils.md5Hex(password + salt);
        SysUser sysUser = sysUserService.findUser(account, password);
        // 用户名或密码错误
        if (sysUser == null) {
            return Result.fail(ErrorCode.ACCOUNT_PWD_NOT_EXISTS.getCode()
                    , ErrorCode.ACCOUNT_PWD_NOT_EXISTS.getMsg());
        }
        // 生成token
        String token = JWTUtil.createToken(sysUser.getId());
        redisTemplate.opsForValue().set("TOKEN_" + token, JSON.toJSONString(sysUser)
                , 1, TimeUnit.DAYS);
        return Result.success(token);
    }

    @Override
    public Result findUserByToken(String token) {
        // 如果用户传入的token为空
        if (token == null || "".equals(token)) {
            return Result.fail(ErrorCode.TOKEN_ERROR.getCode()
                    , ErrorCode.TOKEN_ERROR.getMsg());
        }
        // 校验token
        Object map = JWTUtil.checkToken(token);
        if (map == null) {
            return Result.fail(ErrorCode.TOKEN_ERROR.getCode()
                    , ErrorCode.TOKEN_ERROR.getMsg());
        }
        String userJson = redisTemplate.opsForValue().get("TOKEN_" + token);
        // 如果根据token无法获取到用户，登录失败
        if (userJson == null || "".equals(userJson)) {
            return Result.fail(ErrorCode.TOKEN_ERROR.getCode()
                    , ErrorCode.TOKEN_ERROR.getMsg());
        }
        // 从redis中成功获取用户信息并且返回给用户
        SysUser sysUser = JSON.parseObject(userJson, SysUser.class);
        LoginUserVo loginUserVo
                = new LoginUserVo(sysUser.getId(), sysUser.getAccount()
                , sysUser.getNickname(), sysUser.getAvatar());
        return Result.success(loginUserVo);
    }

    @Override
    public Result logout(String token) {
        redisTemplate.delete("TOKEN_" + token);
        return Result.success(null);
    }

    /**
     * 实现注册功能
     * 1. 判断参数是否正确
     * 2. 判断账户是否存在，存在，返回账户已经被注册
     * 3. 不存在，注册用户
     * 4. 生成token
     * 5. 存入redis，并返回
     * 6. 加上事务，一旦中间任何过程出现问题，需要回滚
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result register(RegisterUserVo registerUserVo){
        String account = registerUserVo.getAccount();
        String password = registerUserVo.getPassword();
        String nickname = registerUserVo.getNickname();
        // 判断参数是否正确
        if (account == null || password == null || nickname == null
                || "".equals(account) || "".equals(password) || "".equals(nickname)) {
            return Result.fail(ErrorCode.PARAMS_ERROR.getCode()
                    , ErrorCode.PARAMS_ERROR.getMsg());
        }
        // 判断账号是否已经存在
        SysUser sysUser = sysUserService.findUserByAccount(account);
        if (sysUser != null) {
            return Result.fail(ErrorCode.ACCOUNT_EXISTS.getCode()
                    , ErrorCode.ACCOUNT_EXISTS.getMsg());
        }
        sysUser = new SysUser();
        sysUser.setId( sysUserMapper.getMaxId() + 1 );
        sysUser.setNickname(nickname);
        sysUser.setAccount(account);
        sysUser.setPassword(DigestUtils.md5Hex(password + salt));
        sysUser.setCreateDate(System.currentTimeMillis());
        sysUser.setLastLogin(System.currentTimeMillis());
        sysUser.setAdmin(0);
        sysUser.setDeleted(0);
        sysUser.setSalt(salt);
        sysUser.setStatus("");
        sysUser.setEmail("");
        sysUserService.save(sysUser);
        String token = JWTUtil.createToken(sysUser.getId());
        redisTemplate.opsForValue().set("TOKEN_" + token
                , JSON.toJSONString(sysUser), 1, TimeUnit.DAYS);
        return Result.success(token);
    }
}
