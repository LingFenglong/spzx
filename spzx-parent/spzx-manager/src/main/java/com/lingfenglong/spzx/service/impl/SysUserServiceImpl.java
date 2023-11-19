package com.lingfenglong.spzx.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lingfenglong.spzx.model.dto.system.SysUserDto;
import com.lingfenglong.spzx.util.AuthContextUtil;
import com.lingfenglong.spzx.util.RedisPrefix;
import com.lingfenglong.spzx.common.exception.SysUserException;
import com.lingfenglong.spzx.mapper.SysUserMapper;
import com.lingfenglong.spzx.model.dto.system.LoginDto;
import com.lingfenglong.spzx.model.entity.system.SysUser;
import com.lingfenglong.spzx.model.vo.common.SysUserResultCode;
import com.lingfenglong.spzx.model.vo.system.LoginVo;
import com.lingfenglong.spzx.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class SysUserServiceImpl implements SysUserService {
    private final SysUserMapper sysUserMapper;
    private final StringRedisTemplate redisTemplate;

    @Autowired
    public SysUserServiceImpl(SysUserMapper sysUserMapper, StringRedisTemplate redisTemplate) {
        this.sysUserMapper = sysUserMapper;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public LoginVo login(LoginDto loginDto) {
        // 获取验证码
        String captcha = loginDto.getCaptcha();
        String codeKey = loginDto.getCodeKey();
        String redisCaptcha = redisTemplate.opsForValue().get(RedisPrefix.VALIDATE_CODE + codeKey);

        // 未输入验证码、redis无验证码（过期）、验证码错误 --> 刷新验证码，抛出异常
        if (!StringUtils.hasText(captcha) || !StringUtils.hasText(redisCaptcha) || !captcha.equalsIgnoreCase(redisCaptcha)) {
            // 要求前端主动刷新验证码
            throw new SysUserException("验证码错误", SysUserResultCode.VALIDATE_CODE_ERROR);
        }

        // 获取用户名 （主键）
        String userName = loginDto.getUserName();

        // 查询MySQL
        SysUser sysUser = sysUserMapper.selectByUserName(userName);

        // SysUser 是否有为空
        // 无 抛异常 用户名或密码错误
        if (userName == null) {
            throw new SysUserException("用户名或密码错误", SysUserResultCode.LOGIN_ERROR);
        }

        // 获得密码
        String inputPassword = loginDto.getPassword();
        String dbPassword = sysUser.getPassword();

        // 有 验证密码
        // 密码错误 抛异常 用户名或密码错误
        if (!StringUtils.hasText(inputPassword) || !inputPassword.equals(dbPassword)) {
            throw new SysUserException("用户名或密码错误", SysUserResultCode.LOGIN_ERROR);
        }

        // 正确 登录成功 生成token存入redis 删除验证码
        String sysUserInfo = JSON.toJSONString(sysUser);
        String token = UUID.randomUUID().toString().replaceAll("-", "");
        redisTemplate.opsForValue().set(RedisPrefix.USER_LOGIN + token, sysUserInfo, 30, TimeUnit.MINUTES);
        redisTemplate.delete(RedisPrefix.VALIDATE_CODE + codeKey);

        // 返回结果 LoginVo
        LoginVo loginVo = new LoginVo();
        loginVo.setToken(token);
        loginVo.setRefresh_token("");

        return loginVo;
    }

    @Override
    public SysUser getUserInfo(String token) {
        //// 直接从redis中获取用户的信息（json 格式）
        //String json = redisTemplate.opsForValue().get(RedisPrefix.USER_LOGIN + token);
        //return JSON.parseObject(json, SysUser.class);

        // 直接从ThreadLocal中获取
        return AuthContextUtil.get();
    }

    @Override
    public void logout(String token) {
        // 从 redis 删除token
        redisTemplate.delete(RedisPrefix.USER_LOGIN + token);
    }

    @Override
    public PageInfo<SysUser> findPage(Integer pageNum, Integer pageSize, SysUserDto sysUserDto) {
        PageHelper.startPage(pageNum, pageSize);
        List<SysUser> sysUserList = sysUserMapper.findPage(sysUserDto);
        return PageInfo.of(sysUserList);
    }

    @Override
    public void remove(Long userId) {
        sysUserMapper.remove(userId);
    }

    @Override
    public void save(SysUser sysUser) {
        sysUserMapper.save(sysUser);
    }

    @Override
    public void update(SysUser sysUser) {
        sysUserMapper.update(sysUser);
    }
}
